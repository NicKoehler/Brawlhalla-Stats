package com.nickoehler.brawlhalla.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreenViewProvider

fun splashScreenAnimation(splashScreenView: SplashScreenViewProvider) {
    val scaleX = ObjectAnimator.ofFloat(
        splashScreenView.iconView,
        View.SCALE_X,
        1f,
        0f
    ).apply {
        interpolator = AccelerateInterpolator()
        duration = 200L
    }

    val scaleY = ObjectAnimator.ofFloat(
        splashScreenView.iconView,
        View.SCALE_Y,
        1f,
        0f
    ).apply {
        interpolator = AccelerateInterpolator()
        duration = 200L
    }

    AnimatorSet().apply {
        playTogether(listOf(scaleX, scaleY))
        doOnEnd {
            splashScreenView.remove()
        }
        start()
    }
}
