package id.co.ultrajaya.hrdinspection.module.login

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import dmax.dialog.SpotsDialog
import id.co.ultrajaya.hrdinspection.R
import id.co.ultrajaya.hrdinspection.clsumum.AlertDialogClass2
import id.co.ultrajaya.hrdinspection.clsumum.Config
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.LoginView {
    private lateinit var _Presenter: LoginContract.Presenter
    private var _Dialog: AlertDialog? = null
    private var _Context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtVersi.text = "Versi : " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        _Presenter = LoginPresenterImpl(this, GetLoginInteractorImpl())

        _Dialog = SpotsDialog.Builder().setContext(this).build()
        _Dialog!!.setMessage("Authenticating...");

        btLogin.setOnClickListener(View.OnClickListener {
            var ausername: String = txt_username.getText().toString()
            var apassword: String = txt_password.getText().toString()
            _Presenter.onLoginClick(ausername, apassword)
        })
    }

    override fun showProgress() {
        _Dialog!!.show()
    }

    override fun hideProgress() {
        _Dialog!!.dismiss()
    }

    override fun setUsername() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPassword() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setVersi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContext(): Context {
        return _Context;
    }

    override fun getVibrate() {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(100)
    }

    override fun alertDialog(imsg: String, itipe: Int) {
        val alertDialog = AlertDialogClass2.alertDialog(imsg, itipe, this)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        })
        alertDialog.show()
    }

    override fun getToast(imsg: String) {
        Toast.makeText(this, "Error :" + Config.Companion.myUser!!.get_ErrMsg().toString(), Toast.LENGTH_SHORT).show()
    }
}