package sibpardazan.gharb.satzpuzzle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import sibpardazan.gharb.satzpuzzle.databinding.ActivityLevelPickerBinding

class LevelPickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLevelPickerBinding
    private lateinit var progressionManager: ProgressionManager

    // Level data
    private val levels = listOf(
        Triple(1, R.string.hamburg_key, R.string.hamburg),
        Triple(2, R.string.berlin_key, R.string.berlin),
        Triple(3, R.string.leipzig_key, R.string.leipzig),
        Triple(4, R.string.dusseldorf_key, R.string.dusseldorf),
        Triple(5, R.string.cologne_key, R.string.cologne),
        Triple(6, R.string.frankfurt_key, R.string.frankfurt),
        Triple(7, R.string.stuttgart_key, R.string.stuttgart),
        Triple(8, R.string.munich_key, R.string.munich)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressionManager = ProgressionManager(this)
        setupLevelClickListeners()
        updateLevelStates()
        updateScoreDisplay()
    }

    private fun setupLevelClickListeners() {
        // Level 1 - Hamburg
        binding.level1Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(1)) {
                startQuizActivity(getString(levels[0].second), 1)
            }
        }

        // Level 2 - Berlin
        binding.level2Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(2)) {
                startQuizActivity(getString(levels[1].second), 2)
            }
        }

        // Level 3 - Leipzig
        binding.level3Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(3)) {
                startQuizActivity(getString(levels[2].second), 3)
            }
        }

        // Level 4 - Düsseldorf
        binding.level4Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(4)) {
                startQuizActivity(getString(levels[3].second), 4)
            }
        }

        // Level 5 - Cologne
        binding.level5Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(5)) {
                startQuizActivity(getString(levels[4].second), 5)
            }
        }

        // Level 6 - Frankfurt
        binding.level6Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(6)) {
                startQuizActivity(getString(levels[5].second), 6)
            }
        }

        // Level 7 - Stuttgart
        binding.level7Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(7)) {
                startQuizActivity(getString(levels[6].second), 7)
            }
        }

        // Level 8 - Munich
        binding.level8Container.setOnClickListener {
            if (progressionManager.isLevelUnlocked(8)) {
                startQuizActivity(getString(levels[7].second), 8)
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

    private fun updateLevelStates() {
        val unlockedLevel = progressionManager.getUnlockedLevel()

        // Update each level based on unlocked state
        for (i in levels.indices) {
            val levelNumber = levels[i].first
            val isUnlocked = progressionManager.isLevelUnlocked(levelNumber)

            updateLevelUI(i, isUnlocked)
        }
    }

    private fun updateLevelUI(levelIndex: Int, isUnlocked: Boolean) {
        val lockView = getLockView(levelIndex)
        val containerView = getContainerView(levelIndex)

        if (isUnlocked) {
            lockView.visibility = View.GONE
            containerView.isClickable = true
            containerView.isEnabled = true
            containerView.alpha = 1.0f
        } else {
            lockView.visibility = View.VISIBLE
            containerView.isClickable = false
            containerView.isEnabled = false
            containerView.alpha = 0.5f
        }
    }

    private fun getLockView(levelIndex: Int): View {
        return when (levelIndex) {
            0 -> binding.level1Lock
            1 -> binding.level2Lock
            2 -> binding.level3Lock
            3 -> binding.level4Lock
            4 -> binding.level5Lock
            5 -> binding.level6Lock
            6 -> binding.level7Lock
            7 -> binding.level8Lock
            else -> binding.level1Lock
        }
    }

    private fun getContainerView(levelIndex: Int): View {
        return when (levelIndex) {
            0 -> binding.level1Container
            1 -> binding.level2Container
            2 -> binding.level3Container
            3 -> binding.level4Container
            4 -> binding.level5Container
            5 -> binding.level6Container
            6 -> binding.level7Container
            7 -> binding.level8Container
            else -> binding.level1Container
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            // Level completed successfully, unlock next level and update score
            val completedLevel = requestCode
            progressionManager.unlockLevel(completedLevel + 1)
            val finalScore = data?.getIntExtra(getString(R.string.extra_final_score), progressionManager.getGlobalScore()) ?: progressionManager.getGlobalScore()
            progressionManager.updateGlobalScore(finalScore)
            updateLevelStates()
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