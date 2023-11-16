package com.withings.mycomposeandblepractice.ui.presentation.components.images

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.withings.mycomposeandblepractice.R
import com.withings.mycomposeandblepractice.data.local.SensorData
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.ClickableIcon
import com.withings.mycomposeandblepractice.ui.presentation.components.decorations.RoundBackgroundGradientForBox
import com.withings.mycomposeandblepractice.ui.theme.Purple40
import kotlinx.coroutines.flow.StateFlow

private val rainbowColorsBrush = Brush.sweepGradient(
    listOf(
        Purple40,
        Purple40,
        Purple40,
    )
)

@Composable
fun ParallaxImageCard(
    picturePath: String,
    sensorDataStateFlow: StateFlow<SensorData>,
    onBackIconClicked: () -> Unit,
) {

    val sensorData = sensorDataStateFlow.collectAsState()

    //GraphicLayer
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val widthInDp = configuration.screenWidthDp.dp
    val heightInDp = configuration.screenHeightDp.dp
    val widthInPx = with(density) { widthInDp.roundToPx() }
    val purple = MaterialTheme.colorScheme.primary


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purple),
        contentAlignment = Alignment.Center
    ) {


        /*RoundBackgroundGradientForBox(
            modifier = Modifier
                .requiredHeight(heightInDp / 3f)
                .requiredWidth(heightInDp / 3f)
                .align(Alignment.BottomCenter)
                .offset(
                    x = widthInDp / 2,
                    y = heightInDp / 6
                )
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                }
        )*/


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picturePath)
                .crossfade(true)
                .build(),
            contentDescription = "GlowShadow",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.2f), blendMode = BlendMode.Darken),
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = -(sensorData.value.roll * 0.5).dp.roundToPx(),
                        y = (sensorData.value.pitch * 1).dp.roundToPx()
                    )
                }
                .graphicsLayer {

                    rotationX = sensorData.value.pitch*0.3f
                    rotationY = sensorData.value.roll*0.3f

                }
                .size(width = widthInDp-120.dp, height = heightInDp-320.dp)
                .blur(radius = 24.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picturePath)
                .crossfade(true)
                .build(),
            contentDescription = "ImageCard",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .graphicsLayer {

                    rotationX = sensorData.value.pitch*0.3f
                    rotationY = sensorData.value.roll*0.3f

                }
                    .offset {
                    IntOffset(
                        x = (sensorData.value.roll).dp.roundToPx(),
                        y = -(sensorData.value.pitch).dp.roundToPx()
                    )
                }
                .clip(RoundedCornerShape(16.dp))
                .size(width = widthInDp-100.dp, height = heightInDp-300.dp),

            alignment = BiasAlignment(
                horizontalBias = (sensorData.value.roll * 0.005).toFloat(),
                verticalBias = 0f,
            )
        )

        /*Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = (sensorData.value.roll * 0.9).dp.roundToPx(),
                        y = -(sensorData.value.pitch * 0.9).dp.roundToPx()
                    )
                }
                .graphicsLayer {
                    rotationX = sensorData.value.pitch*0.3f
                    rotationY = sensorData.value.roll*0.3f
                }
                .size(width = widthInDp-120.dp, height = heightInDp-320.dp)
                .border(
                    BorderStroke(2.dp, rainbowColorsBrush),
                    shape = RoundedCornerShape(16.dp),
                )
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ),
        )*/

        Canvas(
            modifier = Modifier
                .size(width = widthInDp-120.dp, height = heightInDp-320.dp)
                .offset {
                    IntOffset(
                        x = (sensorData.value.roll * 0.9).dp.roundToPx(),
                        y = -(sensorData.value.pitch * 0.9).dp.roundToPx()
                    )
                }
                .graphicsLayer {
                    rotationX = sensorData.value.pitch*0.3f
                    rotationY = sensorData.value.roll*0.3f
                }
        ) {

            val canvasWidth = size.width
            val canvasHeight = size.height
            val border = 25f
            //val radius = canvasHeight / 2
            //val space = (canvasWidth - 4 * radius) / 2

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                drawRoundRect(
                    //blendMode = BlendMode.DstOut,
                    color = purple,
                    size = Size(canvasWidth, canvasHeight),
                    cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
                )

                drawRoundRect(
                    blendMode = BlendMode.DstOut,
                    color = Color.Red,
                    topLeft = Offset(border / 2, border / 2),
                    size = Size(canvasWidth-border, canvasHeight-border),
                    cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())

                )

                restoreToCount(checkPoint)
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            //verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ClickableIcon(
                modifier = Modifier,
                iconId = R.drawable.ic_back_24dp,
                colorTint = Color.Black,
                iconSize = 52.dp
            ) {
                onBackIconClicked()
            }
        }



    }



}