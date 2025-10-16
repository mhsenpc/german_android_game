package sibpardazan.gharb.satzpuzzle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sibpardazan.gharb.satzpuzzle.databinding.ActivityReferencesBinding

class ReferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        // URL text view click listeners
        binding.website1UrlTextView.setOnClickListener {
            openWebsite(getString(R.string.website_1_url))
        }

        binding.website2UrlTextView.setOnClickListener {
            openWebsite(getString(R.string.website_2_url))
        }
    }

    private fun openWebsite(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            // Handle error if no browser app is available
            e.printStackTrace()
        }
    }
}