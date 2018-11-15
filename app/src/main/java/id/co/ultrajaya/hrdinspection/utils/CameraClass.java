package id.co.ultrajaya.hrdinspection.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.co.ultrajaya.hrdinspection.R;

public class CameraClass extends Fragment {

    private static Context _Context;
    private static String _NamaFileID;
    private static String _Path;

    public void setNamaFileID(String iNamaFileID) {
        this._NamaFileID = iNamaFileID;
    }

    public void setContext(Context icontext) {
        this._Context = icontext;
    }

    public void setPath(String ipath) {
        this._Path = ipath;
    }

    public String getPath() {
        return _Path;
    }

    public Intent dispatchTakePictureIntent() {
        Intent takePictureIntent = null;
        PackageManager packageManager = _Context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
            Toast.makeText(_Context, "This device does not have a camera.", Toast.LENGTH_SHORT).show();
            return null;
        }
        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(_Context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                Toast toast = Toast.makeText(_Context, "There was a problem saving the photo...", Toast.LENGTH_SHORT);
                toast.show();
            }

            Uri fileUri;
            if (photoFile != null) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                   /* String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = _NamaFileID + "_" + timeStamp;*/
                    /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/foto_hrd/";
                    File file = new File(path, imageFileName + ".jpg");*/

                    fileUri = FileProvider.getUriForFile(getActivity(), "id.co.ultrajaya.hrdinspection", photoFile);
                    setPath(fileUri.getPath());

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                } else {
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        fileUri = FileProvider.getUriForFile(_Context,
                                getString(R.string.file_provider_authority),
                                photoFile);
                    } else {*/
                    fileUri = Uri.fromFile(photoFile);
                    /*}*/
                    setPath(photoFile.getAbsolutePath());
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                }
            }
        }
        return takePictureIntent;
    }

    private File createImageFile() {
        File afile = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = _NamaFileID + "_" + timeStamp + "_";

        File storageDir = null;
        storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/foto_hrd/");

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d("Foto-HRD-INS", "failed to create directory");
                return null;
            }
        }
        try {
            afile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return afile;
    }

    public static String compressImage(String imageUri) {
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight <= 0 ? 4160 : options.outHeight;
        int actualWidth = options.outWidth <= 0 ? 3120 : options.outWidth;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            return "";
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = imageUri;
        try {
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }

    /*public static void compressZelory(String iPathImage, Context iContext) {
        File compressedImageFile;
        File aActualImage = new File(iPathImage);
        try {
            compressedImageFile = new Compressor(iContext).compressToFile(aActualImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/

    protected static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    protected static String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = _Context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static String sendFotoFTP(String iFile, String iHost, int iPort, String iUsername, String iPassword, String iDestFolder, String iNamaFile) {
        String aErr = "";
        SimpleFTP ftp = new SimpleFTP();
        try {
            /* ex : ftp.connect("b2b.ultrajaya.co.id", 21, "safefoto", "ultra2006");*/
            ftp.connect(iHost, iPort, iUsername, iPassword);
            ftp.bin();
            ftp.cwd(iDestFolder);
            try {
                boolean result = ftp.stor(new File(iFile));
                if (result) {
                    aErr = "";
                }
            } catch (Exception ex) {
                aErr = "Error saat ftp 0 " + ex.toString();
            }
            ftp.disconnect();
        } catch (Exception e) {
            aErr = "Error saat ftp 1 " + e.toString();
        }
        return aErr;
    }
    /*
    public static String sendFTP4j(String iFilePath, String iHost, int iPort, String iUsername, String iPassword, String iDestFolder, String iNamaFile) {
        String aerrmsg = "";
        FTPClient client = new FTPClient();

        File afile = new File(iFilePath);
        try {
            client.connect(iHost, 21);
            client.login(iUsername, iPassword);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory(iDestFolder);
            client.upload(afile);
        } catch (Exception e) {
            aerrmsg = e.toString();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
                aerrmsg = e2.toString();
            }
        }
        return aerrmsg;
    }
    */
}
