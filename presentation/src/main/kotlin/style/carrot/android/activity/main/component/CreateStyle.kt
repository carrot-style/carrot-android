/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CreateStyle.kt] created by Ji Sungbin on 22. 1. 25. 오후 5:18
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import style.carrot.android.R
import style.carrot.android.theme.CarrotStyleTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateStyle(modifier: Modifier = Modifier) {
    var fullAddressField by remember { mutableStateOf(TextFieldValue()) }
    var styledAddressField by remember { mutableStateOf(TextFieldValue()) }
    var memoField by remember { mutableStateOf(TextFieldValue()) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Full address:", style = MaterialTheme.typography.body2)
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                value = fullAddressField,
                onValueChange = { fullAddressField = it }
            )
            Spacer(20.dp)
            Text(text = "Styled address:", style = MaterialTheme.typography.body2)
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                value = styledAddressField,
                onValueChange = { styledAddressField = it }
            )
            Spacer(20.dp)
            Text(text = "Memo:", style = MaterialTheme.typography.body2)
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
                value = memoField,
                onValueChange = { memoField = it }
            )
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { /*TODO*/ }
        ) {
            Text(text = stringResource(R.string.activity_main_component_createstyle_btn_create))
        }
    }
}

@Suppress("SameParameterValue")
@Composable
private fun Spacer(height: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(height))
}

@Preview(showBackground = true)
@Composable
private fun PreviewCreateStyle() {
    CarrotStyleTheme {
        CreateStyle()
    }
}
