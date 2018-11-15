package id.co.ultrajaya.hrdinspection.module.report

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Vibrator
import android.support.v4.app.FragmentActivity
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.module.AppGlideModule
import dmax.dialog.SpotsDialog
import id.co.ultrajaya.hrdinspection.R
import id.co.ultrajaya.hrdinspection.clsumum.AlertDialogClass2
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import kotlinx.android.synthetic.main.activity_report.*
import id.co.ultrajaya.hrdinspection.module.main.MainActivity



class ReportActivity : AppCompatActivity(), ReportContract.ReportView, View.OnClickListener{
    private lateinit var _Presenter: ReportContract.Presenter
    private var _Dialog: android.app.AlertDialog? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myFotoAdapter : ArrayList<Foto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

        _Presenter = ReportPresenterImpl(this, ModelInteractorGetReportImpl())
        _Presenter.onSetDefaultDate()

        _Dialog = SpotsDialog.Builder().setContext(this).build()
        _Dialog!!.setMessage("Loading...");

        tglAwal.setOnClickListener(this)
        tglAkhir.setOnClickListener(this)
        btGetData.setOnClickListener(this)
        _Presenter.onGetReportByDate(tglAwal.text.toString(), tglAkhir.text.toString())
    }

    override fun setRecyclerView() {
        if(myFotoAdapter.size > 0) {
            viewManager = LinearLayoutManager(this)
            viewAdapter = ReportAdapter(myFotoAdapter, this)

            recyclerView = rvReport.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }else{
            alertDialog("Tidak ada data", 1, false)
        }
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

    override fun alertDialog(imsg: String, itipe: Int, isRefresh: Boolean) {
        val alertDialog = AlertDialogClass2.alertDialog(imsg, itipe, this)
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", DialogInterface.OnClickListener { dialog, id ->
            if (isRefresh) {
            }
            dialog.dismiss()
        })
        alertDialog.show()
    }

    override fun getToast(imsg: String) {
        Toast.makeText(this, imsg, Toast.LENGTH_LONG).show()
    }

    override fun setDateAwal(iTglAwal: String) {
        tglAwal.setText(iTglAwal)
    }

    override fun setDateAkhir(iTglAkhir: String) {
        tglAkhir.setText(iTglAkhir)
    }

    override fun setFotoAdapter(iListFoto: ArrayList<Foto>) {
        this.myFotoAdapter = iListFoto
    }

    override fun showEmptyData() {
        txtError.visibility = View.VISIBLE
        rvReport.visibility = View.GONE
    }

    override fun showAnyData() {
        txtError.visibility = View.GONE
        rvReport.visibility = View.VISIBLE
    }

    override fun startActivityForResult(intent: Intent) {
        startActivityForResult(intent, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == android.R.id.home){
            onBackPressed();
            return true
        }else {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v!!.getId()) {
            R.id.tglAwal -> {
                _Presenter.onDateClick(tglAwal.text.toString(), 1)
            }
            R.id.tglAkhir -> {
                _Presenter.onDateClick(tglAkhir.text.toString(), 2)
            }
            R.id.btGetData ->{
                _Presenter.onGetReportByDate(tglAwal.text.toString(), tglAkhir.text.toString())
            }
            else -> {
                getToast("Tidak ada click menu")
            }
        }
    }


}