package id.co.ultrajaya.hrdinspection.module.report

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.co.ultrajaya.hrdinspection.R
import id.co.ultrajaya.hrdinspection.clsumum.Foto
import kotlinx.android.synthetic.main.item_report.view.*

public class ReportAdapter(private val myDataset: ArrayList<Foto>, private val mContext: Context)
    : RecyclerView.Adapter<ReportAdapter.ItemReport>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportAdapter.ItemReport {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_report, parent, false)
        return ItemReport(view)
    }

    override fun onBindViewHolder(holder: ItemReport, position: Int) {
        val fotoClass = myDataset.get(position)

        holder.txtNomor.setText((position + 1).toString())
        var aurl : String = "http://b2b.ultrajaya.co.id/laphrd/" + fotoClass.namaFile

        //ImageLoader.with(mContext).from(aurl).load(holder.imgReport);
        //Glide.with(mContext).load(aurl).into(holder.imgReport);

        Glide.with(mContext)
                .load(aurl)
                .apply(RequestOptions
                        .placeholderOf(R.drawable.ultra)
                        .error(R.drawable.ultra)
                        .centerCrop())
                .into(holder.imgReport)

        holder.txtKeterangan.setText("Ket : " + fotoClass.keterangan)
        holder.txtUsername.setText("Input by : " + fotoClass.namaUser)
        holder.txtReportDate.setText("Date : " + fotoClass.tanggal)
    }

    override fun getItemCount() = myDataset.size

    class ItemReport(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtNomor: TextView = itemView.number
        internal var imgReport: ImageView = itemView.imgReport
        internal var txtKeterangan: TextView = itemView.txtKeterangan
        internal var txtUsername: TextView = itemView.txtUsername
        internal var txtReportDate: TextView = itemView.txtReportDate
    }
}