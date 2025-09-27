package sibpardazan.gharb.satzpuzzle

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import sibpardazan.gharb.satzpuzzle.databinding.ActivityQuizBinding
import org.json.JSONObject
import java.util.*

class QuizActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var tts: TextToSpeech
    private var currentCity = ""
    private var currentLevel = 0
    private var currentQuestionIndex = 0
    private var score = 0
    private var hearts = 3
    private lateinit var questions: List<JSONObject>
    private var usedQuestions = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentCity = intent.getStringExtra(getString(R.string.extra_city_name)) ?: getString(R.string.hamburg_key)
        currentLevel = intent.getIntExtra(getString(R.string.extra_level_number), 1)

        tts = TextToSpeech(this, this)

        loadQuestions()
        setupClickListeners()
        displayNextQuestion()
        updateUI()
    }

    private fun loadQuestions() {
        try {
            val jsonString = assets.open("questions/$currentCity.json").bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val questionsArray = jsonObject.getJSONArray("questions")

            val questionList = mutableListOf<JSONObject>()
            for (i in 0 until questionsArray.length()) {
                questionList.add(questionsArray.getJSONObject(i))
            }
            questions = questionList

            loadBackgroundImage(jsonObject.getString("background"))
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.error_loading_questions), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadBackgroundImage(backgroundPath: String) {
        try {
            val assetManager = assets
            val inputStream = assetManager.open(backgroundPath)
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.backgroundImage.setImageDrawable(drawable)
            inputStream.close()

        } catch (e: Exception) {
            binding.backgroundImage.setImageResource(R.drawable.default_background)
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.option1Button.setOnClickListener { checkAnswer(0) }
        binding.option2Button.setOnClickListener { checkAnswer(1) }
        binding.option3Button.setOnClickListener { checkAnswer(2) }
        binding.option4Button.setOnClickListener { checkAnswer(3) }
        binding.speakButton.setOnClickListener { speakQuestion() }
        binding.owlButton.setOnClickListener { showHint() }
    }

    private fun displayNextQuestion() {
        if (usedQuestions.size >= questions.size) {
            showLevelComplete()
            return
        }

        val availableQuestions = questions.indices.filter { it !in usedQuestions }
        if (availableQuestions.isEmpty()) {
            showLevelComplete()
            return
        }

        currentQuestionIndex = availableQuestions.random()
        usedQuestions.add(currentQuestionIndex)

        val currentQuestion = questions[currentQuestionIndex]

        binding.questionTextView.text = currentQuestion.getString("sentence")

        val options = currentQuestion.getJSONArray("options")
        binding.option1Button.text = options.getString(0)
        binding.option2Button.text = options.getString(1)
        binding.option3Button.text = options.getString(2)
        binding.option4Button.text = options.getString(3)

        resetButtonStyles()
        enableAnswerButtons(true)
    }

    private fun checkAnswer(selectedOption: Int) {
        enableAnswerButtons(false)

        val currentQuestion = questions[currentQuestionIndex]
        val correctAnswer = currentQuestion.getString("answer")
        val options = currentQuestion.getJSONArray("options")
        val selectedAnswer = options.getString(selectedOption)

        if (selectedAnswer == correctAnswer) {
            score += 10
            updateScore()
            showCorrectAnswer(selectedOption)
            binding.questionTextView.text = currentQuestion.getString("sentence").replace("....", correctAnswer)

            binding.root.postDelayed({
                displayNextQuestion()
            }, 1500)
        } else {
            hearts--
            updateHearts()
            showWrongAnswer(selectedOption)

            if (hearts <= 0) {
                showGameOver()
            } else {
                binding.root.postDelayed({
                    displayNextQuestion()
                }, 2000)
            }
        }
    }

    private fun showCorrectAnswer(selectedOption: Int) {
        val selectedButton = when (selectedOption) {
            0 -> binding.option1Button
            1 -> binding.option2Button
            2 -> binding.option3Button
            3 -> binding.option4Button
            else -> binding.option1Button
        }

        selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
    }

    private fun showWrongAnswer(selectedOption: Int) {
        val selectedButton = when (selectedOption) {
            0 -> binding.option1Button
            1 -> binding.option2Button
            2 -> binding.option3Button
            3 -> binding.option4Button
            else -> binding.option1Button
        }

        selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        selectedButton.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.shake))
    }

    private fun resetButtonStyles() {
        val defaultColor = ContextCompat.getColor(this, android.R.color.darker_gray)
        binding.option1Button.setBackgroundColor(defaultColor)
        binding.option2Button.setBackgroundColor(defaultColor)
        binding.option3Button.setBackgroundColor(defaultColor)
        binding.option4Button.setBackgroundColor(defaultColor)
    }

    private fun enableAnswerButtons(enabled: Boolean) {
        binding.option1Button.isEnabled = enabled
        binding.option2Button.isEnabled = enabled
        binding.option3Button.isEnabled = enabled
        binding.option4Button.isEnabled = enabled
    }

    private fun updateScore() {
        binding.scoreTextView.text = getString(R.string.score, score)
    }

    private fun updateHearts() {
        binding.heart1.setImageResource(if (hearts >= 1) R.drawable.heart_full else R.drawable.heart_empty)
        binding.heart2.setImageResource(if (hearts >= 2) R.drawable.heart_full else R.drawable.heart_empty)
        binding.heart3.setImageResource(if (hearts >= 3) R.drawable.heart_full else R.drawable.heart_empty)
    }

    private fun updateUI() {
        updateScore()
        updateHearts()
        binding.levelTextView.text = getString(R.string.level, currentLevel, currentCity.replaceFirstChar { it.uppercase() })
    }

    private fun speakQuestion() {
        val currentQuestion = questions[currentQuestionIndex]
        val correctAnswer = currentQuestion.getString("answer")
        val sentence = currentQuestion.getString("sentence").replace("....", correctAnswer)
        tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun showGameOver() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_over))
            .setMessage(getString(R.string.game_over_message, score))
            .setPositiveButton(getString(R.string.restart_level)) { _, _ ->
                restartLevel()
            }
            .setNegativeButton(getString(R.string.main_menu)) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun showLevelComplete() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.level_complete))
            .setMessage(getString(R.string.level_complete_message, currentCity.replaceFirstChar { it.uppercase() }, score))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                setResult(RESULT_OK)
                finish()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun showHint() {
        val currentQuestion = questions[currentQuestionIndex]
        val hint = currentQuestion.optString("hint", getString(R.string.no_hint_available))

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.hint))
            .setMessage(hint)
            .setPositiveButton(getString(R.string.got_it)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .create()
        dialog.show()
    }

    private fun restartLevel() {
        score = 0
        hearts = 3
        usedQuestions.clear()
        updateUI()
        displayNextQuestion()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.GERMAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, getString(R.string.german_language_not_supported), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
        // Remove any pending callbacks
    }
}