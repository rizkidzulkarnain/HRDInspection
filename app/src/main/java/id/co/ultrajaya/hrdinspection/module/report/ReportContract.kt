package id.co.ultrajaya.hrdinspection.module.report

import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.app.FragmentActivity
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import id.co.ultrajaya.hrdinspection.webservice.GET_SP_MV_DET
import id.co.ultrajaya.hrdinspection.webservice.SP_SAVE_MV
import java.util.*
import kotlin.collections.ArrayList

interface ReportContract {
    interface ReportView {
        fun showProgress()
        fun hideProgress()
        fun getContext() : FragmentActivity
        fun getVibrate()
        fun alertDialog(imsg : String, itipe : Int, isRefresh : Boolean)
        fun getToast(imsg : String)
        fun startActivityForResult(intent : Intent);
        fun setDateAwal(itglAwal : String)
        fun setDateAkhir(itglAkhir : String)
        fun setFotoAdapter(iListFoto : ArrayList<Foto>)
        fun setRecyclerView()
        fun showEmptyData()
        fun showAnyData()
    }

    interface Presenter {
        fun onDestroy()
        fun onSetDefaultDate()
        fun onDateClick(idate : String, itipe: Int)
        fun onGetReportByDate(iTglAwal : String, iTglAkhir : String)
    }

    interface GetDataByDate {
        interface OnFinishedListener {
            fun onPostExecute(aval : GET_SP_MV_DET)
        }
        fun doInBackground(listener : OnFinishedListener, iFromDate : String, iToDate : String)
    }
}
