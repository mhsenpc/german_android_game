package sibpardazan.gharb.satzpuzzle

import android.content.Context
import android.content.SharedPreferences

class ProgressionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SatzPuzzleProgress", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_UNLOCKED_LEVEL = "unlocked_level"
        private const val DEFAULT_UNLOCKED_LEVEL = 1 // Hamburg is always unlocked
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

    fun resetProgress() {
        sharedPreferences.edit().putInt(KEY_UNLOCKED_LEVEL, DEFAULT_UNLOCKED_LEVEL).apply()
    }
}