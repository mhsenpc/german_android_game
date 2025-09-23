package sibpardazan.gharb.satzpuzzle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sibpardazan.gharb.satzpuzzle.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}