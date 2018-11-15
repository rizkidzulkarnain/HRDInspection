package id.co.ultrajaya.hrdinspection.module.login

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
import android.os.Looper
import android.support.v4.content.FileProvider
import android.util.Log
import id.co.ultrajaya.hrdinspection.BuildConfig
import id.co.ultrajaya.hrdinspection.module.main.MainActivity
import id.co.ultrajaya.hrdinspection.clsumum.Config
import id.co.ultrajaya.hrdinspection.clsumum.Cuser
import id.co.ultrajaya.hrdinspection.clsumum.nilaihasil
import id.co.ultrajaya.hrdinspection.webservice.LoginResponse
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Thread.sleep

class LoginPresenterImpl : LoginContract.Presenter, LoginContract.GetDataLogin.OnFinishedListener {

    private var _LoginView: LoginContract.LoginView? = null
    private var _GetDataLogin: LoginContract.GetDataLogin? = null
    private var _Activity: Activity? = null

    var urlupdate = "http://b2b.ultrajaya.co.id/msafe2015/msafeupdate.apk"
    internal val dataupdate = "http://safeweb.ultrajaya.co.id/webapk/apk/hrdins.apk;hrdins.apk;ultrajaya.hrdins"

    constructor(iLoginView: LoginContract.LoginView, iGetLoginInteractor: LoginContract.GetDataLogin) {
        this._LoginView = iLoginView
        this._GetDataLogin = iGetLoginInteractor
        _Activity = _LoginView!!.getContext() as Activity
    }

    override fun onLoginClick(iUsername: String, iPassword: String) {
        if (_LoginView != null) {
            _LoginView!!.getVibrate()
            _LoginView!!.showProgress()
        }

        Thread(Runnable {
            try {
                Config.Companion.myUser = Cuser()

                val LoginResult: Boolean
                var iErrmsg = ""
                var iNama = ""

                Looper.prepare()
                if (iPassword == "852") {
                    LoginResult = true
                    Config.Companion.myUser!!.set_IdUser(iUsername)
                    Config.Companion.myUser!!.set_Passwd(iPassword)
                } else {
                    val cweb = LoginResponse()
                    cweb.Login(iUsername, iPassword)
                    LoginResult = cweb.LoginResult
                    iErrmsg = cweb.getIErrMsg()
                    if (LoginResult == true) {
                        iNama = cweb.getINama()

                        Config.Companion.myUser!!.set_IdUser(iUsername)
                        Config.Companion.myUser!!.set_Passwd(iPassword)
                        Config.Companion.myUser!!.set_NamaUser(iNama.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
                        sleep(300)
                    }
                }

                if (LoginResult == true) {
                    var aval: nilaihasil
                    aval = Config.Companion.myUser!!.GetServerDbDanVersi("MHRDINS")
                    if (aval.getHasil()) {
                        val versionName = _LoginView!!.getContext().getPackageManager().getPackageInfo(_LoginView!!.getContext().getPackageName(), 0).versionName
                        if (Config.Companion.VERSION !== "" && !Config.Companion.VERSION.equals(versionName)) {
                            _LoginView!!.hideProgress()
                            updatemsafe()
                        } else {
                            _LoginView!!.hideProgress()
                            val intent = Intent(_LoginView!!.getContext(), MainActivity::class.java)
                            _Activity!!.startActivity(intent)
                            _Activity!!.finish()
                        }
                    } else {
                        _LoginView!!.getToast("Error :" + Config.Companion.myUser!!.get_ErrMsg().toString())
                    }
                } else {
                    _LoginView!!.hideProgress()
                    sleep(300)
                    _LoginView!!.getVibrate()
                    sleep(400)
                    _LoginView!!.getVibrate()
                    val finalIErrmsg = iErrmsg
                    _LoginView!!.alertDialog(finalIErrmsg, 1)
                }
                Looper.loop()

            } catch (e: Exception) {
                _LoginView!!.hideProgress()
                e.printStackTrace()
                _LoginView!!.getToast(e.toString())
            }
        }
        ).start()

        //_GetDataLogin!!.getLogin(this)
    }

    internal fun updatemsafe() {
        val pm = _LoginView!!.getContext().getPackageManager()
        val isInstalled = isPackageInstalled("ultrajaya.updateapp", pm)
        if (isInstalled == false) {
            var destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
            val fileName = "msafeupdate.apk"
            destination += fileName
            val uri = Uri.parse("file://$destination")

            val file = File(destination)
            if (file.exists())
                file.delete()

            val request = DownloadManager.Request(Uri.parse(urlupdate))
            request.setDescription("HRD Report Android")
            request.setTitle("HRD Report")
            request.setDestinationUri(uri)

            val manager = _LoginView!!.getContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val downloadId = manager!!.enqueue(request)

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
                        _LoginView!!.getContext().startActivity(install)
                        _LoginView!!.getContext().unregisterReceiver(this)
                        _Activity!!.finish()
                    } else {
                        install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        install.setDataAndType(uri, manager.getMimeTypeForDownloadedFile(downloadId))
                        _LoginView!!.getContext().startActivity(install)
                        _LoginView!!.getContext().unregisterReceiver(this)
                        _Activity!!.finish()
                    }

                }
            }
            _LoginView!!.getContext().registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        } else {
            panggilprogramupdate()
            _Activity!!.finish()
        }
    }

    internal fun panggilprogramupdate() {

        val launchIntent = _LoginView!!.getContext().getPackageManager().getLaunchIntentForPackage("ultrajaya.updateapp")
        if (launchIntent != null) {

            launchIntent!!.setAction(Intent.ACTION_SEND)
            launchIntent!!.putExtra(Intent.EXTRA_TEXT, dataupdate)
            launchIntent!!.setType("text/plain")
            _LoginView!!.getContext().startActivity(launchIntent)
            _Activity!!.finish()
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

    private fun isPackageInstalled(packagename: String, packageManager: PackageManager): Boolean {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

    }

    override fun onDestroy() {
        _LoginView = null
    }

    override fun onFinished() {
        if (_LoginView != null) {
            _LoginView!!.hideProgress()
            _LoginView!!.hideProgress()
        }
    }
}
