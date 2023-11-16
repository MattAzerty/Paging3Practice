package com.withings.mycomposeandblepractice

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.withings.mycomposeandblepractice.ui.theme.MyComposeAndBLEPracticeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    @Inject
    lateinit var myNavigator: MyNavigator

    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.LoadingImages)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.LoadingImages -> true
                is MainActivityUiState.Success -> false
            }
        }

        splashScreen
            .setOnExitAnimationListener { splashScreenProvider ->
            // Create your custom animation.
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenProvider.view,
                View.TRANSLATION_X,
                0f,
                -splashScreenProvider.view.width.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = 500L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideLeft.doOnEnd { splashScreenProvider.remove() }

            // Run your animation.
            slideLeft.start()
        }
        setContent {
            MyComposeAndBLEPracticeTheme {
                MyNavHost(
                    myNavigator = myNavigator,
                )
            }
        }
    }
}