package id.co.ultrajaya.hrdinspection.clsumum

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.FileProvider
import android.util.Log
import id.co.ultrajaya.hrdinspection.BuildConfig
import id.co.ultrajaya.hrdinspection.webservice.Get_SP_MV
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

public class CheckUpdate() {

    var urlupdate = "http://b2b.ultrajaya.co.id/msafe2015/msafeupdate.apk"
    internal val dataupdate = "http://safeweb.ultrajaya.co.id/webapk/apk/scanaset.apk;scanaset.apk;ultrajaya.scanaset"

    var mContext: Context? = null
        get() = field
        set(value) {
            field = value
        }
    var mActivity : Activity ?= null

    //Program Update
    public fun updatemsafe() {
        mActivity = mContext as Activity?
        val pm = mContext!!.packageManager
        val isInstalled = isPackageInstalled("ultrajaya.updateapp", pm)
        if (isInstalled == false) {
            var destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
            val fileName = "msafeupdate.apk"
            destination += fileName
            val uri = Uri.parse("file://" + destination)

            val file = File(destination)
            if (file.exists())
                file.delete()

            val request = DownloadManager.Request(Uri.parse(urlupdate))
            request.setDescription("MSAFE-Visit Android")
            request.setTitle("msafeupdate")

            request.setDestinationUri(uri)

            val manager = mContext!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = manager.enqueue(request)

            val finalDestination = destination
            val onComplete = object : BroadcastReceiver() {
                override fun onReceive(ctxt: Context, intent: Intent) {
                    writeToFile(dataupdate)
                    val install = Intent(Intent.ACTION_VIEW)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        val contentUri = FileProvider.getUriForFile(ctxt, BuildConfig.APPLICATION_ID + ".provider", File(finalDestination))
                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        install.data = contentUri
                        mContext!!.startActivity(install)
                        mContext!!.unregisterReceiver(this)
                        mActivity!!.finish()
                    } else {
                        install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        install.setDataAndType(uri, manager.getMimeTypeForDownloadedFile(downloadId))
                        mContext!!.startActivity(install)
                        mContext!!.unregisterReceiver(this)
                        mActivity!!.finish()
                    }
                }
            }

            mContext!!.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        } else {
            panggilprogramupdate()
            mActivity!!.finish()
        }
    }

    internal fun panggilprogramupdate() {

        val launchIntent = mContext!!.packageManager.getLaunchIntentForPackage("ultrajaya.updateapp")
        if (launchIntent != null) {

            launchIntent.action = Intent.ACTION_SEND
            launchIntent.putExtra(Intent.EXTRA_TEXT, dataupdate)
            launchIntent.type = "text/plain"

            mContext!!.startActivity(launchIntent)
            mActivity!!.finish()
        }
    }

    private fun isPackageInstalled(packagename: String, packageManager: PackageManager): Boolean {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

    }

    private fun writeToFile(data: String) {
        try {
            val sdCard = Environment.getExternalStorageDirectory()
            val dir = File(sdCard.absolutePath + "/safeupdate")
            dir.mkdirs()
            val file = File(dir, "config.txt")
            val stream = FileOutputStream(file)
            try {
                stream.write(data.toByteArray())
            } finally {
                stream.close()
            }

        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    public fun GetServerDbDanVersi(): nilaihasil {
        val aval = nilaihasil()
        aval.setHasil(false)

        var aerrmsg : String? = ""
        var adispmsg : String? = ""

        var amv = ""
        var aData = ""

        try {
            val aspget = Get_SP_MV()
            amv = Character.toString(Config.Companion.AM)

            aspget.get_sp("MSAFE", "1", "SCANASET", amv, "[dbo].[GETVERSI]", "OTH", "")
            aerrmsg = aspget.getIErrMsg()
            if (aerrmsg == null) {
                aData = aspget.getIHasil()
                val amvitem = mvitem()
                amvitem.SetData(aData)
                Config.VERSION = amvitem.GetData(2)
                aval.setHasil(true)
            } else {
                aval.setErrMsg(aspget.iErrMsg!!)
            }
        } catch (ex: Exception) {
            aval.setErrMsg(ex.toString())
        }
        return aval
    }
}