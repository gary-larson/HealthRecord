package mobi.thalic.healthrecord.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mobi.thalic.healthrecord.HealthListFragment
import mobi.thalic.healthrecord.R
import mobi.thalic.healthrecord.data.HealthEntity
import mobi.thalic.healthrecord.databinding.HealthListFragmentItemBinding
import java.time.format.DateTimeFormatter

/**
 * Class to display health data
 */
class HealthListRecyclerViewAdapter(
    private var values: List<HealthEntity>,
    private val mListener : HealthListFragment.OnListFragmentInteractionListener
) : RecyclerView.Adapter<HealthListRecyclerViewAdapter.ViewHolder>() {
    private lateinit var context : Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context;
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.health_list_fragment_item, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val formatter = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm")
        val date = formatter.format(item.dateEntry)
        with(holder) {
            if (position % 2 == 0) {
                val colorValue = ContextCompat.getColor(context, R.color.colorListBackground1)
                binding.listLinearLayout.setBackgroundColor(colorValue)
            } else {
                val colorValue = ContextCompat.getColor(context, R.color.colorListBackground2)
                binding.listLinearLayout.setBackgroundColor(colorValue)
            }
            binding.date.text = date
            binding.bloodSugar.text = item.bloodSugar.toString()
            binding.bloodPressure.text = item.systolic.toString() + "/" + item.diastolic.toString()
//            binding.diastolic.text = item.diastolic.toString()
//            binding.systolic.text = item.systolic.toString()
            binding.heartRate.text = item.heartRate.toString()
            binding.weight.text = item.weight.toString()
            mView.setOnClickListener {
                mListener.onListFragmentInteraction(item)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = HealthListFragmentItemBinding.bind(view)
        val mView = view
    }
}