package id.co.ultrajaya.hrdinspection.module.report

import android.os.Looper
import id.co.ultrajaya.hrdinspection.clsumum.Config
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import id.co.ultrajaya.hrdinspection.clsumum.mvitem
import id.co.ultrajaya.hrdinspection.webservice.SP_SAVE_MV
import id.co.ultrajaya.hrdinspection.module.report.ReportContract.GetDataByDate.OnFinishedListener
import id.co.ultrajaya.hrdinspection.webservice.GET_SP_MV_DET

public class ModelInteractorGetReportImpl : ReportContract.GetDataByDate {
    override fun doInBackground(listener : OnFinishedListener, iFromDate : String, iToDate : String) {
        Thread(Runnable {
            Looper.prepare()
            val amvitem = mvitem()
            amvitem.SetData(1, iFromDate)
            amvitem.SetData(2, iToDate)

            val aval = GET_SP_MV_DET()
            aval.get_sp(Config.Companion.ServerLog, "1", "", amvitem.Contents(), "IT.Reporting_HRD_SP", "", "GET_REPORT_BY_DATE")
            listener!!.onPostExecute(aval)
            Looper.loop()
        }).start()
    }
}