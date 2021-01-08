package mobi.thalic.healthrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import mobi.thalic.healthrecord.databinding.HealthDetailFragmentBinding
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HealthDetailFragment : Fragment() {
    val args: HealthDetailFragmentArgs by navArgs()
    private var _binding: HealthDetailFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = HealthDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val formatter = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm")
        val date = formatter.format(args.healthRecord?.dateEntry)
        binding.detailDateTextView.text = date
        binding.detailSystolicTextView.text = args.healthRecord?.systolic.toString()
        binding.detailDiastolicTextView.text = args.healthRecord?.diastolic.toString()
        binding.detailBloodSugarTextView.text = args.healthRecord?.bloodSugar.toString()
        binding.detailHeartRateTextView.text = args.healthRecord?.heartRate.toString()
        binding.detailWeightTextView.text = args.healthRecord?.weight.toString()
        binding.fab.setOnClickListener {
            binding.fab.visibility = View.INVISIBLE
            binding.detailBloodPressureEditImageButton.visibility = View.VISIBLE
            binding.detailHeartRateEditImageButton.visibility = View.VISIBLE
            binding.detailBloodSugarEditImageButton.visibility = View.VISIBLE
            binding.detailWeightEditImageButton.visibility = View.VISIBLE
            binding.detailBloodPressureEditImageButton.setOnClickListener {
                Snackbar.make(view, "Edit blood pressure clicked", Snackbar.LENGTH_LONG).show()
            }
            binding.detailHeartRateEditImageButton.setOnClickListener {
                Snackbar.make(view, "Edit heart rate clicked", Snackbar.LENGTH_LONG).show()
            }
            binding.detailBloodSugarEditImageButton.setOnClickListener {
                Snackbar.make(view, "Edit blood sugar clicked", Snackbar.LENGTH_LONG).show()
            }
            binding.detailWeightEditImageButton.setOnClickListener {
                Snackbar.make(view, "Edit weight clicked", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}