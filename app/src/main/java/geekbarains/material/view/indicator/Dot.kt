package geekbarains.material.view.indicator

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi

class Dot(
        private val parent: PageIndicatorView,
        private val leftOffset: Float,
        private val bottomOffset: Float,
        private val strokeWidth: Float,
        private val step: Float,
        private val radius: Float,
        isInteraction: Boolean,
        position: Int) {

    private var workRadius = 10f
    private var transparent = 255
    private var posX = 0f
    private var posY = 0f

    private val aDuration: Long = 600

    private val animationOneStep = 0.6f
    private val animationDurationStep: Long = aDuration / 6
    private val animationTransparentMax = 255
    private val animationTransparentMin = 100


    var isInteraction = false
        set(value) {
            if (field != value) {
                field = value
                reCalc(position, position)
            }
        }

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = Color.CYAN
        alpha = transparent
    }

    private lateinit var animForward:  AnimatorSet
    private lateinit var animBackward: AnimatorSet

    var position: Int = 0
        set(value) {
            if (field != value) {
                if (isInteraction) {
                    field = value
                    reCalc(value, value)
                } else {
                    reCalc(field, value)
                    field = value
                    animForward.start()
                }
            }
        }

    init {
        posX = leftOffset + step * position
        posY = bottomOffset
        this.isInteraction = isInteraction
        this.position = position
    }


    private fun onAnimatorUpdate(value: Any, animType: AnimationType) {
        when (animType) {
            AnimationType.Radius -> workRadius = value as Float
            AnimationType.Transparent -> transparent = value as Int
            AnimationType.YPos -> posY = value as Float
            AnimationType.XPos -> {posX = value as Float
            }
        }

        parent.invalidate()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun play(position: Int, positionOffset: Float){
            if (!isInteraction) {
                animForward.currentPlayTime = (aDuration * positionOffset).toLong()
            } else {
                if (position == this.position) {
                    animForward.currentPlayTime = (aDuration * positionOffset).toLong()
                } else {
                    animBackward.currentPlayTime = (aDuration * positionOffset).toLong()
                }
        }
    }


    private fun reCalc(oldValue: Int, newValue: Int) {
        val aSizeMin = ValueAnimator.ofFloat(radius, radius * animationOneStep).apply {
            duration = animationDurationStep
            addUpdateListener {
                if (it != null)
                    onAnimatorUpdate(it.animatedValue, AnimationType.Radius)
            }
        }
        val aSizePlus = ValueAnimator.ofFloat(radius * animationOneStep, radius).apply {
            duration = animationDurationStep
            addUpdateListener {
                if (it != null)
                    onAnimatorUpdate(it.animatedValue, AnimationType.Radius)
            }
        }
        val aTransparentMin = ValueAnimator.ofInt(animationTransparentMax, animationTransparentMin).apply {
            duration = animationDurationStep
            addUpdateListener {
                if (it != null)
                    onAnimatorUpdate(it.animatedValue, AnimationType.Transparent)
            }
        }
        val aTransparentPlus = ValueAnimator.ofInt(animationTransparentMin, animationTransparentMax).apply {
            duration = animationDurationStep
            addUpdateListener {
                if (it != null)
                    onAnimatorUpdate(it.animatedValue, AnimationType.Transparent)
            }
        }
        val aMoveUp = ValueAnimator.ofFloat(bottomOffset, radius + strokeWidth).apply {
            duration = animationDurationStep * 2
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                if (it != null)
                    onAnimatorUpdate(it.animatedValue, AnimationType.YPos)
            }
        }
        val aMoveDown = ValueAnimator.ofFloat(radius + strokeWidth, bottomOffset).apply {
            duration = animationDurationStep * 2
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                if (it != null)
                    onAnimatorUpdate(it.animatedValue, AnimationType.YPos)
            }
        }
        val aMoveForward =
                ValueAnimator.ofFloat(
                        leftOffset + step * oldValue,
                        leftOffset + step * (if (isInteraction) oldValue + 1 else newValue)
                ).apply {
                    duration = aDuration * 2 / 3
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener {
                        if (it != null) onAnimatorUpdate(it.animatedValue, AnimationType.XPos)
                    }
                }
        val aMoveBackward =
                ValueAnimator.ofFloat(
                        leftOffset + step * (oldValue - 1),
                        leftOffset + step * (oldValue)).apply {
                    duration = aDuration * 2 / 3
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener {
                        if (it != null)
                            onAnimatorUpdate(it.animatedValue, AnimationType.XPos)
                    }
                }

        animForward = AnimatorSet()
        animForward.playTogether(aSizeMin, aTransparentMin)
        animForward.playTogether(aMoveUp, aMoveForward)
        animForward.playTogether(aSizePlus, aTransparentPlus)
        animForward.playSequentially(aSizeMin, aMoveUp, aMoveDown, aSizePlus)
        if (isInteraction) {
            animBackward = AnimatorSet()
            animBackward.playTogether(aSizeMin, aTransparentMin)
            animBackward.playTogether(aMoveUp, aMoveBackward)
            animBackward.playTogether(aSizePlus, aTransparentPlus)
            animBackward.playSequentially(aSizeMin, aMoveUp, aMoveDown, aSizePlus)
        }
    }


    fun draw(canvas: Canvas) {
        paint.alpha = transparent
        canvas.drawCircle(posX, posY, workRadius, paint)
    }

}