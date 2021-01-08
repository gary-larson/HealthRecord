package mobi.thalic.healthrecord

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mobi.thalic.healthrecord.adapter.HealthListRecyclerViewAdapter
import mobi.thalic.healthrecord.data.HealthEntity
import mobi.thalic.healthrecord.databinding.HealthListFragmentItemListBinding
import mobi.thalic.healthrecord.viewmodel.HealthViewModel

/**
 * A fragment representing a list of Items.
 */
class HealthListFragment : Fragment() {
    // declare member variables
    private var mListener : OnListFragmentInteractionListener? = null
    private val model: HealthViewModel by activityViewModels()
    private var _binding: HealthListFragmentItemListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HealthListFragmentItemListBinding.inflate(inflater, container, false)
        val view = binding.list
        // Set the adapter
        with(view) {
            layoutManager = LinearLayoutManager(context)
            // set up observer
            model.healthRecordList.observe(viewLifecycleOwner, { items ->
                adapter = HealthListRecyclerViewAdapter(items, mListener!!)
            })
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_HealthListFragment_to_healthAddDetailFragment)
        }
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}