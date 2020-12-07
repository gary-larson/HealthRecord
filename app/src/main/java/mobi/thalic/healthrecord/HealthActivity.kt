package mobi.thalic.healthrecord

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import mobi.thalic.healthrecord.data.HealthEntity
import mobi.thalic.healthrecord.databinding.ActivityHealthBinding
import mobi.thalic.healthrecord.viewmodel.HealthViewModel

class HealthActivity : AppCompatActivity(), HealthListFragment.OnListFragmentInteractionListener {
    private lateinit var binding: ActivityHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //val model: HealthViewModel by viewModels()


        binding.fab.setOnClickListener { view ->
            findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_HealthListFragment_to_HealthDetailFragment)
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onListFragmentInteraction(healthRecord: HealthEntity) {
        val action = HealthListFragmentDirections
            .actionHealthListFragmentToHealthDetailFragment(healthRecord)
        findNavController(R.id.nav_host_fragment)
            .navigate(action)
    }
}