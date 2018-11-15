package id.co.ultrajaya.hrdinspection.module.main

import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.app.FragmentActivity
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import id.co.ultrajaya.hrdinspection.webservice.GET_SP_MV_DET
import id.co.ultrajaya.hrdinspection.webservice.SP_SAVE_MV
import java.util.*

interface MainContract {
    interface MainView {
        fun showProgress()
        fun hideProgress()
        fun getContext() : FragmentActivity
        fun getVibrate()
        fun alertDialog(imsg : String, itipe : Int, isRefresh : Boolean)
        fun getToast(imsg : String)
        fun setImage(iscaled :Bitmap)
        fun refreshActivity();
        fun startActivityForResult(intent : Intent);
    }

    interface Presenter {
        fun onSaveClick(iKeterangan : String)
        fun onDestroy()
        fun onCameraClick()
        fun onResultOfCamera(requestCode: Int, resultCode: Int, data: Intent?)
        fun onReportClick()
    }

    interface SaveData {
        interface OnFinishedListener {
            fun onPostExecute(aval : SP_SAVE_MV)
        }
        fun doInBackground(listener : OnFinishedListener, ifoto : Foto)
    }

    interface GetLastID{
        interface  OnFinishedListener{
            fun onPostExecute(aval : GET_SP_MV_DET)
        }
        fun doInBackground(listener : OnFinishedListener)
    }
}
