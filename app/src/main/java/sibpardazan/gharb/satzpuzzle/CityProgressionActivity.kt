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
        updateScoreDisplay()
    }

    private fun setupCityClickListeners() {
        binding.hamburgMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(1)) {
                startQuizActivity(getString(R.string.hamburg_key), 1)
            }
        }

        binding.berlinMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(2)) {
                startQuizActivity(getString(R.string.berlin_key), 2)
            }
        }

        binding.leipzigMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(3)) {
                startQuizActivity(getString(R.string.leipzig_key), 3)
            }
        }

        binding.dusseldorfMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(4)) {
                startQuizActivity(getString(R.string.dusseldorf_key), 4)
            }
        }

        binding.cologneMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(5)) {
                startQuizActivity(getString(R.string.cologne_key), 5)
            }
        }

        binding.frankfurtMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(6)) {
                startQuizActivity(getString(R.string.frankfurt_key), 6)
            }
        }

        binding.stuttgartMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(7)) {
                startQuizActivity(getString(R.string.stuttgart_key), 7)
            }
        }

        binding.munichMarker.setOnClickListener {
            if (progressionManager.isLevelUnlocked(8)) {
                startQuizActivity(getString(R.string.munich_key), 8)
            }
        }

        binding.aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startQuizActivity(city: String, level: Int) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(getString(R.string.extra_city_name), city)
            putExtra(getString(R.string.extra_level_number), level)
            putExtra(getString(R.string.extra_global_score), progressionManager.getGlobalScore())
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
            // Level completed successfully, unlock next level and update score
            val completedLevel = requestCode
            progressionManager.unlockLevel(completedLevel + 1)
            val finalScore = data?.getIntExtra(getString(R.string.extra_final_score), progressionManager.getGlobalScore()) ?: progressionManager.getGlobalScore()
            progressionManager.updateGlobalScore(finalScore)
            updateCityStates()
            updateScoreDisplay()
        } else if (resultCode == RESULT_CANCELED) {
            // Level was restarted or failed, update score if available
            val finalScore = data?.getIntExtra(getString(R.string.extra_final_score), progressionManager.getGlobalScore()) ?: progressionManager.getGlobalScore()
            progressionManager.updateGlobalScore(finalScore)
            updateScoreDisplay()
        }
    }

    private fun updateScoreDisplay() {
        binding.scoreTextView.text = getString(R.string.score, progressionManager.getGlobalScore())
    }
}