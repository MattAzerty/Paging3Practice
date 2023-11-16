package com.withings.mycomposeandblepractice.ui.presentation.screens.parallax

import androidx.lifecycle.ViewModel
import com.withings.mycomposeandblepractice.MyNavigator
import com.withings.mycomposeandblepractice.data.local.SensorDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ParallaxViewModel @Inject constructor(
    private val myNavigator: MyNavigator,
    private val sensorDataManager: SensorDataManager
    ): ViewModel() {

   val sensorDataStateFlow = sensorDataManager.sensorDataStateFlow

   fun initSensorManager(){
       sensorDataManager.init()
   }

   fun cancelSensorManager(){
       sensorDataManager.cancel()
    }

    fun onBackIconClicked() {
        myNavigator.back()
    }

}