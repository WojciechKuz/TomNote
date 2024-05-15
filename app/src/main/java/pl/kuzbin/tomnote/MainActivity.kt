package pl.kuzbin.tomnote

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import pl.kuzbin.tomnote.databinding.ActivityMainBinding
import pl.kuzbin.tomnote.createNotification

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.sendButton.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.send_button).show()
            val notifiText = binding.notifiText.text.toString() ?: "Tekst powiadomienia"
            createNotification(this@MainActivity, "Tytuł powiadomienia", notifiText)
        }
    }

    // NOT USED
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // NOT USED
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    // results of permission ask. if granted or not
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // DO NOTHING
    }

    override fun onPause() {
        viewModel.storedText = binding.notifiText.text.toString()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.storedText != null) { // is this if required ???
            binding.notifiText.setText(viewModel.storedText)
        }
    }
}