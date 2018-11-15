package id.co.ultrajaya.hrdinspection.module.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v7.app.AppCompatActivity
import id.co.ultrajaya.hrdinspection.clsumum.Config
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import java.io.File
import id.co.ultrajaya.hrdinspection.module.report.ReportActivity
import id.co.ultrajaya.hrdinspection.utils.CameraClass
import id.co.ultrajaya.hrdinspection.utils.SendFTP
import id.co.ultrajaya.hrdinspection.webservice.GET_SP_MV_DET
import id.co.ultrajaya.hrdinspection.webservice.SP_SAVE_MV
import java.text.SimpleDateFormat
import java.util.*


class MainPresenterImpl : MainContract.Presenter, MainContract.SaveData.OnFinishedListener, MainContract.GetLastID.OnFinishedListener {
    private var _MainView: MainContract.MainView? = null
    private var _SaveData: MainContract.SaveData? = null
    private var _GetLastID: MainContract.GetLastID? = null
    private var _FragmentActivity: FragmentActivity? = null

    var _CameraClass: CameraClass? = null
    var _Foto: Foto? = Foto()
    var _PathImage: String = ""
    private var _IsSudahFoto: Boolean = false;
    var _Keterangan: String = ""

    constructor(iMainView: MainContract.MainView, iSaveInteractor: MainContract.SaveData, iGetLastIDInteractor: MainContract.GetLastID) {
        this._MainView = iMainView
        this._SaveData = iSaveInteractor
        this._GetLastID = iGetLastIDInteractor
        _FragmentActivity = _MainView!!.getContext() as FragmentActivity
    }

    override fun onDestroy() {
        _MainView = null
    }

    override fun onPostExecute(aval: SP_SAVE_MV) {
        _MainView!!.getContext().runOnUiThread(java.lang.Runnable {
            if (_MainView != null) {
                if (aval.getIErrMsg().equals("")) {
                    _MainView!!.alertDialog(aval._DispMsg, 2, true)
                    //sendFTP() //dicomment karena alurnya send ftp dulu baru save data
                } else {
                    _MainView!!.alertDialog(aval.iErrMsg, 1, false)
                }
                _MainView!!.hideProgress()
                _IsSudahFoto = false;
            }
        })
    }

    override fun onPostExecute(aval: GET_SP_MV_DET) {
        _MainView!!.getContext().runOnUiThread(java.lang.Runnable {
            var anama: String = ""
            if (_MainView != null) {
                if (aval.getIErrMsg() == null) {
                    if (aval.get_at3().split(Config.Companion.AM)[0].get(0) !== Config.Companion.VN) {
                        val ajml = aval.get_at3().split(Character.toString(Config.Companion.VM)).size
                        for (aidx in 0 until ajml) {
                            anama = aval.get_at3().split(Character.toString(Config.Companion.VM))[aidx]
                        }
                        onGoCamera(anama);
                    } else {
                        _MainView!!.alertDialog("Tidak mendapatkan Last ID", 1, false)
                    }
                } else {
                    _MainView!!.alertDialog(aval.iErrMsg, 1, false)
                }
            }
        })
    }

    fun sendFTP() {
        Thread(Runnable {
            Looper.prepare()
            var aErrMsg = ""
            aErrMsg = SendFTP.sendFotoFTP(
                    _PathImage,
                    "b2b.ultrajaya.co.id",
                    21,
                    "safefoto",
                    "ultra2006",
                    "/laphrd/",
                    _Foto!!.getNamaFile()
            )
            if (aErrMsg == "") {
                _MainView!!.getToast("Foto sudah tersimpan di server")
                onSaveData()
            } else {
                _MainView!!.alertDialog("Gagal menyimpan foto, Lakukan foto lagi", 1, false);
                _MainView!!.hideProgress()
                //_MainView!!.getToast("Gagal menyimpan foto di server")
            }
            Looper.loop()
        }).start()
    }

    fun onSaveData() {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        var asplit = _PathImage.split('/')

        _Foto!!.userID = Config.Companion.myUser!!._IdUser
        _Foto!!.namaUser = Config.Companion.myUser!!._NamaUser
        _Foto!!.tanggal = dateFormat.format(Date())
        _Foto!!.keterangan = _Keterangan
        _Foto!!.namaFile = asplit.get(asplit.size - 1);
        _SaveData!!.doInBackground(this, _Foto!!);
    }

    override fun onResultOfCamera(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK) {
            var imgFile : File ?= null
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                val afilepath = _PathImage.split("/")
                _PathImage = Environment.getExternalStorageDirectory().path + "/" + afilepath[2] + "/" + afilepath[3]
                imgFile = File(_PathImage)
            } else {
                imgFile = File(_PathImage)
            }

            CameraClass.compressImage(_PathImage)

            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
                _MainView!!.setImage(myBitmap)
                _IsSudahFoto = true
            }
        } else {
            _MainView!!.getToast("Image Capture Failed")
        }
    }

    override fun onSaveClick(iKeterangan: String) {
        _MainView!!.showProgress()
        _MainView!!.getVibrate()
        if (_IsSudahFoto) {
            //alurnya send ftp dulu kalau berhasil baru save data
            //karena dilapangan data tersimpan tapi fotonya tidak tersimpan
            _Keterangan = iKeterangan
            sendFTP()
        } else {
            _MainView!!.alertDialog("Belum melakukan foto", 1, false);
            _MainView!!.hideProgress()
        }
    }

    override fun onCameraClick() {
        _MainView!!.getVibrate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(_MainView!!.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(_MainView!!.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(_MainView!!.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                _MainView!!.getContext().requestPermissions(
                        arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        ), 100
                )
                return
            } else {
                _CameraClass = CameraClass()
                _GetLastID!!.doInBackground(this)
            }
        } else {
            _CameraClass = CameraClass()
            _GetLastID!!.doInBackground(this)
        }
    }

    fun onGoCamera(iNama: String) {
        _CameraClass!!.setNamaFileID(iNama)
        _CameraClass!!.setContext(_MainView!!.getContext())
        val aintent = _CameraClass!!.dispatchTakePictureIntent()
        if (aintent != null) {
            try {
                _PathImage = _CameraClass!!.path
                _MainView!!.startActivityForResult(aintent)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            _MainView!!.getToast("Gagal membuka intent camera")
        }
    }

    override fun onReportClick() {
        val intent = Intent(_FragmentActivity, ReportActivity::class.java)
        _FragmentActivity!!.startActivity(intent)
    }
}
