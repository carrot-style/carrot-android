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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.launch
import style.carrot.android.R
import style.carrot.android.activity.main.MainViewModel
import style.carrot.android.domain.model.StyledUrl
import style.carrot.android.theme.CarrotStyleTheme
import style.carrot.android.ui.HeightSpacer
import style.carrot.android.util.extension.checkHttpAndAutoInsert
import style.carrot.android.util.extension.toast

private const val DefaultStyle = "carrot.style/"
private const val DefaultStyleLengthLimit = DefaultStyle.length + 15
private val DefaultStyleTextFieldValue = TextFieldValue(
    text = DefaultStyle,
    selection = TextRange(DefaultStyle.length)
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateStyle(modifier: Modifier, hideModalBottomSheetAction: () -> Unit, uuid: String) {
    val vm: MainViewModel = viewModel()
    logeukes { "C: $vm" }

    var styledUrlField by remember { mutableStateOf(DefaultStyleTextFieldValue) }
    var fullUrlField by remember { mutableStateOf(TextFieldValue()) }
    var memoField by remember { mutableStateOf(TextFieldValue()) }

    val styledUrlForUpdate = vm.styledUrlForUpdate
    vm.styledUrlForUpdate = null
    if (styledUrlForUpdate != null) {
        styledUrlField = styledUrlField.copy(text = styledUrlForUpdate.styled)
        fullUrlField = fullUrlField.copy(text = styledUrlForUpdate.origin)
        memoField = memoField.copy(text = styledUrlForUpdate.memo)
    }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.activity_main_component_createstyle_full_address),
                style = MaterialTheme.typography.body2
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                value = fullUrlField,
                onValueChange = { fullUrlField = it }
            )
            HeightSpacer(height = 20.dp)
            Text(
                text = stringResource(R.string.activity_main_component_createstyle_my_style),
                style = MaterialTheme.typography.body2
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                value = styledUrlField,
                onValueChange = { styledUrlFieldValue ->
                    if (styledUrlFieldValue.text.isEmpty()) {
                        styledUrlField = DefaultStyleTextFieldValue
                    } else if (styledUrlFieldValue.text.run { startsWith(DefaultStyle) && length <= DefaultStyleLengthLimit }) {
                        styledUrlField = styledUrlFieldValue
                    }
                }
            )
            HeightSpacer(height = 20.dp)
            Text(
                text = stringResource(R.string.activity_main_component_createstyle_memo),
                style = MaterialTheme.typography.body2
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                placeholder = {
                    Text(
                        text = fullUrlField.text,
                        style = LocalTextStyle.current.copy(color = Color.LightGray)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
                value = memoField,
                onValueChange = { memoField = it }
            )
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                coroutineScope.launch {
                    val fullUrl = fullUrlField.text.checkHttpAndAutoInsert()
                    val newStyledUrl = "${styledUrlField.text}.html".replaceFirst(DefaultStyle, "")
                    val memo = when (memoField.text.isEmpty()) {
                        true -> fullUrl.split("://")[1]
                        else -> memoField.text
                    }

                    when (styledUrlForUpdate == null) {
                        true -> { // 새로 추가
                            val checkAlreadyStyled =
                                vm.checkAlreadyStyled(newStyledUrl) ?: return@launch
                            if (!checkAlreadyStyled) {
                                vm.stylingUrl(
                                    styledUrl = StyledUrl(
                                        styled = newStyledUrl,
                                        origin = fullUrl,
                                        memo = memo
                                    ),
                                    sha = "",
                                    uuid = uuid
                                )
                                hideModalBottomSheetAction()
                            } else {
                                toast(
                                    context,
                                    context.getString(R.string.activity_main_component_createstyle_toast_already_used_styled)
                                )
                            }
                        }
                        else -> { // 업데이트
                            vm.stylingUrl(
                                styledUrl = StyledUrl(
                                    styled = newStyledUrl,
                                    origin = fullUrl,
                                    memo = memo
                                ),
                                sha = vm.getStyledSha(newStyledUrl) ?: return@launch,
                                uuid = uuid
                            )
                            hideModalBottomSheetAction()
                        }
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.activity_main_component_createstyle_btn_create))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCreateStyle() {
    CarrotStyleTheme {
        CreateStyle(modifier = Modifier, hideModalBottomSheetAction = {}, uuid = "")
    }
}
