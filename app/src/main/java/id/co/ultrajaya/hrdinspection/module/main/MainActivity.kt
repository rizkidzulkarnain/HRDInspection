package id.co.ultrajaya.hrdinspection.module.main

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.support.v4.app.FragmentActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import dmax.dialog.SpotsDialog
import id.co.ultrajaya.hrdinspection.R
import id.co.ultrajaya.hrdinspection.clsumum.AlertDialogClass2
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.MainView {
    private lateinit var _Presenter: MainContract.Presenter
    private var _Dialog: android.app.AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _Presenter = MainPresenterImpl(this, ModelInteractorSaveImpl(), ModelInteractorGetLastIDImpl())

        _Dialog = SpotsDialog.Builder().setContext(this).build()
        _Dialog!!.setMessage("Loading...");

        btCamera.setOnClickListener(View.OnClickListener {
            _Presenter.onCameraClick()
        })

        btSave.setOnClickListener(View.OnClickListener {
            var aketerangan: String = txtKeterangan.getText().toString()
            _Presenter.onSaveClick(aketerangan)
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        _Presenter.onResultOfCamera(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showProgress() {
        _Dialog!!.show()
    }

    override fun hideProgress() {
        _Dialog!!.hide()
    }

    override fun getContext(): FragmentActivity {
        return this
    }

    override fun getVibrate() {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(100)
    }

    override fun alertDialog(imsg: String, itipe: Int, isRefresh : Boolean) {
        val alertDialog = AlertDialogClass2.alertDialog(imsg, itipe, this)
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", DialogInterface.OnClickListener { dialog, id ->
            if(isRefresh){
                refreshActivity()
            }
            dialog.dismiss()
        })
        alertDialog.show()
    }

    override fun getToast(imsg: String) {
        Toast.makeText(this@MainActivity, imsg, Toast.LENGTH_LONG).show()
    }

    override fun setImage(iscaled : Bitmap) {
        iv_camera.setImageBitmap(iscaled)
    }

    override fun refreshActivity() {
        iv_camera.setImageBitmap(null)
        txtKeterangan.setText("")
    }

    override fun startActivityForResult(intent : Intent) {
        startActivityForResult(intent, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.report) {
            _Presenter.onReportClick()
        } else {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
