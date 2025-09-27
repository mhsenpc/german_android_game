package sibpardazan.gharb.satzpuzzle

import android.content.Context
import android.content.SharedPreferences

class ProgressionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SatzPuzzleProgress", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_UNLOCKED_LEVEL = "unlocked_level"
        private const val KEY_GLOBAL_SCORE = "global_score"
        private const val DEFAULT_UNLOCKED_LEVEL = 1 // Hamburg is always unlocked
        private const val DEFAULT_GLOBAL_SCORE = 0
    }

    fun getUnlockedLevel(): Int {
        return sharedPreferences.getInt(KEY_UNLOCKED_LEVEL, DEFAULT_UNLOCKED_LEVEL)
    }

    fun unlockLevel(level: Int) {
        if (level > getUnlockedLevel()) {
            sharedPreferences.edit().putInt(KEY_UNLOCKED_LEVEL, level).apply()
        }
    }

    fun isLevelUnlocked(level: Int): Boolean {
        return level <= getUnlockedLevel()
    }

    fun getGlobalScore(): Int {
        return sharedPreferences.getInt(KEY_GLOBAL_SCORE, DEFAULT_GLOBAL_SCORE)
    }

    fun updateGlobalScore(score: Int) {
        sharedPreferences.edit().putInt(KEY_GLOBAL_SCORE, score).apply()
    }

    fun addScore(points: Int) {
        val currentScore = getGlobalScore()
        sharedPreferences.edit().putInt(KEY_GLOBAL_SCORE, currentScore + points).apply()
    }

    fun resetProgress() {
        sharedPreferences.edit()
            .putInt(KEY_UNLOCKED_LEVEL, DEFAULT_UNLOCKED_LEVEL)
            .putInt(KEY_GLOBAL_SCORE, DEFAULT_GLOBAL_SCORE)
            .apply()
    }
}