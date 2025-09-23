package sibpardazan.gharb.satzpuzzle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sibpardazan.gharb.satzpuzzle.databinding.ActivityCityProgressionBinding

class CityProgressionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityProgressionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityProgressionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCityClickListeners()
    }

    private fun setupCityClickListeners() {
        binding.hamburgMarker.setOnClickListener {
            startQuizActivity("hamburg", 1)
        }

        binding.berlinMarker.setOnClickListener {
            startQuizActivity("berlin", 2)
        }

        binding.leipzigMarker.setOnClickListener {
            startQuizActivity("leipzig", 3)
        }

        binding.dusseldorfMarker.setOnClickListener {
            startQuizActivity("dusseldorf", 4)
        }

        binding.cologneMarker.setOnClickListener {
            startQuizActivity("cologne", 5)
        }

        binding.frankfurtMarker.setOnClickListener {
            startQuizActivity("frankfurt", 6)
        }

        binding.stuttgartMarker.setOnClickListener {
            startQuizActivity("stuttgart", 7)
        }

        binding.munichMarker.setOnClickListener {
            startQuizActivity("munich", 8)
        }

        binding.aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startQuizActivity(city: String, level: Int) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra("CITY_NAME", city)
            putExtra("LEVEL_NUMBER", level)
        }
        startActivity(intent)
    }
}