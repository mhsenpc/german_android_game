package sibpardazan.gharb.satzpuzzle

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sibpardazan.gharb.satzpuzzle.databinding.ActivityCityProgressionBinding

class CityProgressionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityProgressionBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val cities = listOf(
        "hamburg", "berlin", "leipzig", "dusseldorf",
        "cologne", "frankfurt", "stuttgart", "munich"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityProgressionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SatzPuzzlePrefs", Context.MODE_PRIVATE)

        val completedCity = intent.getStringExtra("COMPLETED_CITY") ?: "hamburg"
        val finalScore = intent.getIntExtra("FINAL_SCORE", 0)

        saveCityProgress(completedCity, finalScore)
        setupCityMarkers()
        setupContinueButton()
    }

    private fun saveCityProgress(city: String, score: Int) {
        val unlockedCities = getUnlockedCities().toMutableSet()
        unlockedCities.add(city)

        sharedPreferences.edit().apply {
            putStringSet("UNLOCKED_CITIES", unlockedCities)
            putInt("SCORE_$city", score)
            apply()
        }
    }

    private fun getUnlockedCities(): Set<String> {
        val defaultUnlocked = setOf("hamburg")
        return sharedPreferences.getStringSet("UNLOCKED_CITIES", defaultUnlocked) ?: defaultUnlocked
    }

    private fun setupCityMarkers() {
        val unlockedCities = getUnlockedCities()

        binding.hamburgMarker.apply {
            setImageResource(
                if ("hamburg" in unlockedCities) R.drawable.city_marker_hamburg
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("hamburg" in unlockedCities) startQuiz("hamburg", 1) }
        }

        binding.berlinMarker.apply {
            setImageResource(
                if ("berlin" in unlockedCities) R.drawable.city_marker_berlin
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("berlin" in unlockedCities) startQuiz("berlin", 2) }
        }

        binding.leipzigMarker.apply {
            setImageResource(
                if ("leipzig" in unlockedCities) R.drawable.city_marker_leipzig
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("leipzig" in unlockedCities) startQuiz("leipzig", 3) }
        }

        binding.dusseldorfMarker.apply {
            setImageResource(
                if ("dusseldorf" in unlockedCities) R.drawable.city_marker_dusseldorf
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("dusseldorf" in unlockedCities) startQuiz("dusseldorf", 4) }
        }

        binding.cologneMarker.apply {
            setImageResource(
                if ("cologne" in unlockedCities) R.drawable.city_marker_cologne
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("cologne" in unlockedCities) startQuiz("cologne", 5) }
        }

        binding.frankfurtMarker.apply {
            setImageResource(
                if ("frankfurt" in unlockedCities) R.drawable.city_marker_frankfurt
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("frankfurt" in unlockedCities) startQuiz("frankfurt", 6) }
        }

        binding.stuttgartMarker.apply {
            setImageResource(
                if ("stuttgart" in unlockedCities) R.drawable.city_marker_stuttgart
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("stuttgart" in unlockedCities) startQuiz("stuttgart", 7) }
        }

        binding.munichMarker.apply {
            setImageResource(
                if ("munich" in unlockedCities) R.drawable.city_marker_munich
                else R.drawable.city_marker_locked
            )
            setOnClickListener { if ("munich" in unlockedCities) startQuiz("munich", 8) }
        }

        displayCityScores(unlockedCities)
    }

    private fun displayCityScores(unlockedCities: Set<String>) {
        var totalScore = 0
        val scoreText = StringBuilder()

        for (city in unlockedCities.sorted()) {
            val score = sharedPreferences.getInt("SCORE_$city", 0)
            totalScore += score
            if (score > 0) {
                scoreText.append("${city.replaceFirstChar { it.uppercase() }}: $score\n")
            }
        }

        binding.totalScoreTextView.text = "Total Score: $totalScore"
        if (scoreText.isNotEmpty()) {
            binding.cityScoresTextView.text = scoreText.toString()
        }
    }

    private fun setupContinueButton() {
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun startQuiz(city: String, level: Int) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra("CITY_NAME", city)
            putExtra("LEVEL_NUMBER", level)
        }
        startActivity(intent)
    }
}