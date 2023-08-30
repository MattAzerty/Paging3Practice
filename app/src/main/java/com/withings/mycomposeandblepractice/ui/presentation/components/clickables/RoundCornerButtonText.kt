package com.withings.mycomposeandblepractice.ui.presentation.components.clickables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.withings.mycomposeandblepractice.ui.theme.MyComposeAndBLEPracticeTheme

@Composable
fun RoundCornerButtonText(
    modifier: Modifier = Modifier,
    text:String,
    isButtonCanBeClicked:Boolean,
    onButtonClicked: () -> Unit
){

    Button(
        enabled = !isButtonCanBeClicked,
        onClick = onButtonClicked,
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor= Color.Black,
            containerColor = Color.LightGray),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(60),
        elevation =  ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        modifier = modifier
    ) {
        Text(
            text = text
        )
    }

}