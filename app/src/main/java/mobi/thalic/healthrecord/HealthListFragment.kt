package mobi.thalic.healthrecord

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mobi.thalic.healthrecord.adapter.HealthListRecyclerViewAdapter
import mobi.thalic.healthrecord.data.HealthEntity
import mobi.thalic.healthrecord.viewmodel.HealthViewModel

/**
 * A fragment representing a list of Items.
 */
class HealthListFragment : Fragment() {
    private var mListener : OnListFragmentInteractionListener? = null
    private val model: HealthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.health_list_fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                // set up observer
                model.getHealthRecords().observe(viewLifecycleOwner, { items ->
                    adapter = HealthListRecyclerViewAdapter(items, mListener!!)
                })
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException("Did you forget to implement list fragment listener in activity")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * Interface for on click listener in activity containing fragment
     */
    interface  OnListFragmentInteractionListener {
        fun onListFragmentInteraction(healthRecord : HealthEntity)
    }
}