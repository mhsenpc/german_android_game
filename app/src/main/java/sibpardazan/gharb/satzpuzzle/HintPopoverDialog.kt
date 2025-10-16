package sibpardazan.gharb.satzpuzzle

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.cardview.widget.CardView
import sibpardazan.gharb.satzpuzzle.databinding.HintPopoverBinding

/**
 * A beautiful custom dialog for displaying hints with a popover-style appearance.
 * Features smooth animations, owl icon, and an elegant card design.
 */
class HintPopoverDialog(context: Context, private val hint: String) : Dialog(context, R.style.Theme_SatzPuzzle) {

    private lateinit var binding: HintPopoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the custom layout
        binding = HintPopoverBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        // Setup the dialog
        setupDialog()
        setupClickListeners()
        setupAnimations()

        // Set the hint text
        binding.hintTextView.text = hint
    }

    private fun setupDialog() {
        // Make dialog full screen with transparent background
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )

        // SetCancelable to true so user can tap outside to dismiss
        setCancelable(true)
    }

    private fun setupClickListeners() {
        // Close button click
        binding.closeButton.setOnClickListener {
            dismissWithAnimation()
        }

        // Got it button click
        binding.gotItButton.setOnClickListener {
            dismissWithAnimation()
        }

        // Overlay click to dismiss
        binding.overlayView.setOnClickListener {
            dismissWithAnimation()
        }
    }

    private fun setupAnimations() {
        // Create scale animation for the card
        val scaleAnimation = ScaleAnimation(
            0.0f, 1.0f,  // From X to X
            0.0f, 1.0f,  // From Y to Y
            Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot X (center)
            Animation.RELATIVE_TO_SELF, 0.5f   // Pivot Y (center)
        ).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Start animation when dialog is shown
        binding.hintCard.startAnimation(scaleAnimation)

        // Fade in overlay
        binding.overlayView.alpha = 0f
        binding.overlayView.animate()
            .alpha(1f)
            .setDuration(200)
            .start()
    }

    private fun dismissWithAnimation() {
        // Scale down animation
        val scaleAnimation = ScaleAnimation(
            1.0f, 0.0f,  // From X to X
            1.0f, 0.0f,  // From Y to Y
            Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot X (center)
            Animation.RELATIVE_TO_SELF, 0.5f   // Pivot Y (center)
        ).apply {
            duration = 250
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Fade out overlay
        binding.overlayView.animate()
            .alpha(0f)
            .setDuration(200)
            .start()

        // Animate card and then dismiss
        binding.hintCard.startAnimation(scaleAnimation)

        // Dismiss after animation completes
        binding.hintCard.postDelayed({
            dismiss()
        }, 250)
    }

    /**
     * Creates and shows a new HintPopoverDialog with the provided hint text.
     */
    companion object {
        fun show(context: Context, hint: String) {
            val dialog = HintPopoverDialog(context, hint)
            dialog.show()
        }
    }
}