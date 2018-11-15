package id.co.ultrajaya.hrdinspection.module.report

import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import id.co.ultrajaya.hrdinspection.clsumum.Config
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import id.co.ultrajaya.hrdinspection.webservice.GET_SP_MV_DET
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportPresenterImpl : ReportContract.Presenter, ReportContract.GetDataByDate.OnFinishedListener {
    val _SimpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val _SimpleDateFormat2 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val _SimpleDateFormat3 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private var _ReportView: ReportContract.ReportView? = null
    private var _GetReportByDate: ReportContract.GetDataByDate? = null
    private lateinit var _FotoListReport : ArrayList<Foto>

    constructor(iReportView: ReportContract.ReportView, iGetReportByDate: ReportContract.GetDataByDate) {
        this._ReportView = iReportView
        this._GetReportByDate = iGetReportByDate
    }

    override fun onDestroy() {
        _ReportView = null
    }

    override fun onGetReportByDate(iTglAwal : String, iTglAkhir : String) {
        _ReportView!!.getVibrate()
        _ReportView!!.showProgress()
        val aTglAwal = convertDateFormat2(iTglAwal)
        val aTglAkhir = convertDateFormat2(iTglAkhir)
        _GetReportByDate!!.doInBackground(this, aTglAwal, aTglAkhir);
    }

    override fun onPostExecute(aval: GET_SP_MV_DET) {
        _ReportView!!.getContext().runOnUiThread(java.lang.Runnable {
            var anama: String = ""
            if (_ReportView != null) {
                if (aval.getIErrMsg() == null) {
                    _FotoListReport = ArrayList<Foto>()
                    if (aval.get_at1().split(Config.Companion.AM)[0].get(0) !== Config.Companion.VN) {
                        val ajml = aval.get_at1().split(Character.toString(Config.Companion.VM)).size
                        for (aidx in 0 until ajml) {
                            var afoto = Foto()
                            afoto.keterangan = aval.get_at2().split(Character.toString(Config.Companion.VM))[aidx]
                            afoto.userID = aval.get_at3().split(Character.toString(Config.Companion.VM))[aidx]
                            afoto.namaUser = aval.get_at4().split(Character.toString(Config.Companion.VM))[aidx]
                            afoto.tanggal = aval.get_at5().split(Character.toString(Config.Companion.VM))[aidx]
                            afoto.namaFile = aval.get_at6().split(Character.toString(Config.Companion.VM))[aidx]
                            _FotoListReport.add(afoto)
                        }
                        _ReportView!!.setFotoAdapter(_FotoListReport)
                        _ReportView!!.setRecyclerView()
                        _ReportView!!.showAnyData()
                    } else {
                        _ReportView!!.alertDialog("Tidak mendapatkan data report", 3, false)
                        _ReportView!!.showEmptyData()
                    }
                } else {
                    _ReportView!!.alertDialog(aval.iErrMsg, 1, false)
                    _ReportView!!.showEmptyData()
                }
            }
            _ReportView!!.hideProgress()
        })
    }

    override fun onDateClick(idate: String, itipe: Int) {
        val bdate = convertDateFormat(idate)
        val dialog = DatePickerFragmentDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            when (itipe) {
                1 -> {
                    _ReportView!!.setDateAwal(_SimpleDateFormat.format(calendar.time))
                }
                2 -> {
                    _ReportView!!.setDateAkhir(_SimpleDateFormat.format(calendar.time))
                }
            }
        }, bdate.split('/')[2].toInt() , bdate.split('/')[1].toInt() - 1, bdate.split('/')[0].toInt())
        dialog.show(_ReportView!!.getContext().supportFragmentManager, "tag")
    }

    override fun onSetDefaultDate() {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        _ReportView!!.setDateAwal(dateFormat.format(Date()))
        _ReportView!!.setDateAkhir(dateFormat.format(Date()))
    }

    fun convertDateFormat(idate: String) : String{
        val adate = _SimpleDateFormat.parse(idate)
        return _SimpleDateFormat2.format(adate)
    }

    fun convertDateFormat2(idate: String) : String{
        val adate = _SimpleDateFormat.parse(idate)
        return _SimpleDateFormat3.format(adate)
    }
}
