package mobi.thalic.healthrecord.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.health_list_fragment_item.view.*
import mobi.thalic.healthrecord.HealthListFragment
import mobi.thalic.healthrecord.R
import mobi.thalic.healthrecord.data.HealthEntity
import java.time.format.DateTimeFormatter

/**
 * Class to display health data
 */
class HealthListRecyclerViewAdapter(
    private var values: List<HealthEntity>,
    private val mListener : HealthListFragment.OnListFragmentInteractionListener
) : RecyclerView.Adapter<HealthListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.health_list_fragment_item, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val formatter = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm")
        val date = formatter.format(item.dateEntry)
        holder.dateTextView.text = date
        holder.bloodSugarTextView.text = item.bloodSugar.toString()
        holder.diastolicTextView.text = item.diastolic.toString()
        holder.systolicTextView.text = item.systolic.toString()
        holder.heartRateTextView.text = item.heartRate.toString()
        holder.weightTextView.text = item.weight.toString()
        holder.mView.setOnClickListener{view ->
            mListener.onListFragmentInteraction(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mView = view
        val dateTextView: TextView = view.date
        val bloodSugarTextView: TextView = view.blood_sugar
        val weightTextView : TextView = view.weight
        val diastolicTextView : TextView = view.diastolic
        val systolicTextView : TextView = view.systolic
        val heartRateTextView : TextView = view.heart_rate
    }
}