package com.withings.mycomposeandblepractice.data.local

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorDataManager @Inject constructor(
    context: Context
):SensorEventListener {

    //val data: Channel<SensorData> = Channel(Channel.UNLIMITED)

    private val sensorDataMutableFlow: MutableStateFlow<SensorData> = MutableStateFlow(SensorData())
    val sensorDataStateFlow = sensorDataMutableFlow.asStateFlow()

    var gravity: FloatArray? = null
    var geomagnetic: FloatArray? = null
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager



    fun init() {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)


        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
    }


    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_GRAVITY)
            gravity = event.values

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values

        if (gravity != null && geomagnetic != null) {
            var r = FloatArray(9)
            var i = FloatArray(9)

            if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
                var orientation = FloatArray(3)
                SensorManager.getOrientation(r, orientation)

                sensorDataMutableFlow.value = SensorData(roll = orientation[2]*30,pitch = orientation[1]*30)

            }

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //TODO("Not yet implemented")
    }

    fun cancel() {
        sensorManager.unregisterListener(this)
    }
}