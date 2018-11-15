package id.co.ultrajaya.hrdinspection.module.main

import android.os.Looper
import id.co.ultrajaya.hrdinspection.clsumum.Config
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import id.co.ultrajaya.hrdinspection.clsumum.mvitem
import id.co.ultrajaya.hrdinspection.webservice.SP_SAVE_MV
import id.co.ultrajaya.hrdinspection.webservice.GET_SP_MV_DET

public class ModelInteractorGetLastIDImpl : MainContract.GetLastID {
    override fun doInBackground(listener : MainContract.GetLastID.OnFinishedListener) {
        Thread(Runnable {
            Looper.prepare()
            val amvitem = mvitem()
            amvitem.SetData(1, "")
            amvitem.SetData(2, "")

            val aval = GET_SP_MV_DET()
            aval.get_sp(Config.Companion.ServerLog, "1", "", amvitem.Contents(), "IT.Reporting_HRD_SP", "", "READ_LAST_ID")
            listener!!.onPostExecute(aval)
            Looper.loop()
        }).start()
    }
}