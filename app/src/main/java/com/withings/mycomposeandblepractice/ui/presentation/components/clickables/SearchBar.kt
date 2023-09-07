package com.withings.mycomposeandblepractice.ui.presentation.components.clickables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.withings.mycomposeandblepractice.R
import com.withings.mycomposeandblepractice.ui.theme.DefaultTextSize
import com.withings.mycomposeandblepractice.ui.theme.MyComposeAndBLEPracticeTheme

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    focusManager: FocusManager
) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val clearFocusSearchBar = {
        // Hide the keyboard after submitting the search
        keyboardController?.hide()
        //and clear focus on the "view"
        focusManager.clearFocus()
    }


    TextField(
        value = text,
        textStyle = TextStyle(
            fontSize = DefaultTextSize,
        ),
        onValueChange = {
            text = it
        },
        label = { Text(stringResource(R.string.image_search_hint)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onSearch(text)
                        clearFocusSearchBar()
                    },
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onSurface,
            containerColor = if (text.isBlank()) MaterialTheme.colorScheme.primary.copy(0.5f) else MaterialTheme.colorScheme.primary.copy(
                alpha = 0.8f
            ),
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            cursorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
                clearFocusSearchBar()
            })
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    MyComposeAndBLEPracticeTheme {
        SearchBar(
            onSearch = {},
            focusManager = LocalFocusManager.current
        )
    }
}
