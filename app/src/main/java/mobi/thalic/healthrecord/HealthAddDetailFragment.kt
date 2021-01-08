package mobi.thalic.healthrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mobi.thalic.healthrecord.data.HealthEntity
import mobi.thalic.healthrecord.databinding.HealthAddDetailFragmentBinding

import mobi.thalic.healthrecord.viewmodel.HealthViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Class to handle adding a health record
 */
class HealthAddDetailFragment : Fragment() {
    // declare member variables
    private val model: HealthViewModel by activityViewModels()
    private var _binding: HealthAddDetailFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = HealthAddDetailFragmentBinding.inflate(inflater, container, false)
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm")
        binding.addDateTextView.text = date.format(formatter)
        binding.addSubmitButton.setOnClickListener { view ->
            val healthEntity = HealthEntity(0L,
                LocalDateTime.parse(binding.addDateTextView.text.toString(), formatter),
                binding.addBloodSugarEditText.text.toString().toInt(),
                binding.addWeightEditText.text.toString().toFloat(),
                binding.addSystolicEditText.text.toString().toInt(),
                binding.addDiastolicEditText.text.toString().toInt(),
                binding.addHeartRateEditText.text.toString().toInt())
            model.insertHealthRecord(healthEntity)
            findNavController().navigate(R.id.action_healthAddDetailFragment_to_HealthListFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}