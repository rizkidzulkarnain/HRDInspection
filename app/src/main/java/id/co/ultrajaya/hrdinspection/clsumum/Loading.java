package id.co.ultrajaya.hrdinspection.clsumum;

import android.app.ProgressDialog;
import android.content.Context;

public class Loading {
    static Context mContext;
    static ProgressDialog mProgreesDialog;
    public static void setLoading(Context iContext, String iMsg){
        mContext = iContext;
        mProgreesDialog = new ProgressDialog(mContext);
        mProgreesDialog.setMessage(iMsg);
    }

    public static void showLoading(){
        mProgreesDialog.show();
    }

    public static void hideLoading(){
        mProgreesDialog.dismiss();
    }
}
