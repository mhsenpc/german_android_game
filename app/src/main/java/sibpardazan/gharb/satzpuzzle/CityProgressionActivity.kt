package sibpardazan.gharb.satzpuzzle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import sibpardazan.gharb.satzpuzzle.databinding.ActivityCityProgressionBinding

class CityProgressionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityProgressionBinding
    private lateinit var progressionManager: ProgressionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityProgressionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressionManager = ProgressionManager(this)
        setupCityClickListeners()
        updateCityStates()
    }

    private fun setupCityClickListeners() {
        binding.hamburgMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(1)) {
                startQuizActivity("hamburg", 1)
            }
        }

        binding.berlinMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(2)) {
                startQuizActivity("berlin", 2)
            }
        }

        binding.leipzigMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(3)) {
                startQuizActivity("leipzig", 3)
            }
        }

        binding.dusseldorfMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(4)) {
                startQuizActivity("dusseldorf", 4)
            }
        }

        binding.cologneMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(5)) {
                startQuizActivity("cologne", 5)
            }
        }

        binding.frankfurtMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(6)) {
                startQuizActivity("frankfurt", 6)
            }
        }

        binding.stuttgartMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(7)) {
                startQuizActivity("stuttgart", 7)
            }
        }

        binding.munichMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(8)) {
                startQuizActivity("munich", 8)
            }
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
        startActivityForResult(intent, level)
    }

    private fun updateCityStates() {
        val unlockedLevel = progressionManager.getUnlockedLevel()

        // Hamburg (Level 1) - always unlocked
        binding.hamburgLock.visibility = View.GONE
        binding.hamburgMarker.isEnabled = true

        // Berlin (Level 2)
        binding.berlinLock.visibility = if (unlockedLevel >= 2) View.GONE else View.VISIBLE
        binding.berlinMarker.isEnabled = unlockedLevel >= 2

        // Leipzig (Level 3)
        binding.leipzigLock.visibility = if (unlockedLevel >= 3) View.GONE else View.VISIBLE
        binding.leipzigMarker.isEnabled = unlockedLevel >= 3

        // Düsseldorf (Level 4)
        binding.dusseldorfLock.visibility = if (unlockedLevel >= 4) View.GONE else View.VISIBLE
        binding.dusseldorfMarker.isEnabled = unlockedLevel >= 4

        // Cologne (Level 5)
        binding.cologneLock.visibility = if (unlockedLevel >= 5) View.GONE else View.VISIBLE
        binding.cologneMarker.isEnabled = unlockedLevel >= 5

        // Frankfurt (Level 6)
        binding.frankfurtLock.visibility = if (unlockedLevel >= 6) View.GONE else View.VISIBLE
        binding.frankfurtMarker.isEnabled = unlockedLevel >= 6

        // Stuttgart (Level 7)
        binding.stuttgartLock.visibility = if (unlockedLevel >= 7) View.GONE else View.VISIBLE
        binding.stuttgartMarker.isEnabled = unlockedLevel >= 7

        // Munich (Level 8)
        binding.munichLock.visibility = if (unlockedLevel >= 8) View.GONE else View.VISIBLE
        binding.munichMarker.isEnabled = unlockedLevel >= 8
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            // Level completed successfully, unlock next level
            val completedLevel = requestCode
            progressionManager.unlockLevel(completedLevel + 1)
            updateCityStates()
        }
    }
}