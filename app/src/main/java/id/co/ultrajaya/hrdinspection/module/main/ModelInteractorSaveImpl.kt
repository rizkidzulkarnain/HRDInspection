package id.co.ultrajaya.hrdinspection.module.main

import android.os.Looper
import id.co.ultrajaya.hrdinspection.clsumum.Config
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import id.co.ultrajaya.hrdinspection.clsumum.mvitem
import id.co.ultrajaya.hrdinspection.webservice.SP_SAVE_MV
import id.co.ultrajaya.hrdinspection.module.main.MainContract.SaveData.OnFinishedListener

public class ModelInteractorSaveImpl : MainContract.SaveData {
    override fun doInBackground(listener : OnFinishedListener, ifoto : Foto) {
        Thread(Runnable {
            Looper.prepare()
            val amvitem = mvitem()
            amvitem.SetData(1, ifoto.getUserID())
            amvitem.SetData(2, ifoto.namaUser)
            amvitem.SetData(3, ifoto.getTanggal())
            amvitem.SetData(4, ifoto.getKeterangan())
            amvitem.SetData(5, ifoto.namaFile)

            val aval = SP_SAVE_MV()
            aval.save_sp(Config.Companion.ServerLog, "1", "", amvitem.Contents(), "", "IT.Reporting_HRD_SP", "SAVE_REPORTING")
            listener!!.onPostExecute(aval)
            Looper.loop()
        }).start()
    }
}