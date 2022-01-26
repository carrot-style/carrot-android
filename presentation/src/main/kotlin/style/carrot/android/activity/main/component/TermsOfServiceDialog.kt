/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [TermsOfServiceDialog.kt] created by Ji Sungbin on 22. 1. 26. 오후 1:15
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import style.carrot.android.R
import style.carrot.android.ui.HeightSpacer
import style.carrot.android.util.Web

@Composable
fun TermsOfServiceDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    if (visible) {
        val context = LocalContext.current

        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = { onDismissRequest() },
            buttons = {},
            text = {
                Column {
                    Text(text = stringResource(R.string.activity_main_component_termsofservicedialog_notice))
                    HeightSpacer(height = 10.dp)
                    Text(
                        modifier = Modifier.clickable {
                            Web.open(context = context, url = Web.TermsOfServiceUrl)
                        },
                        text = stringResource(R.string.activity_main_component_termsofservicedialog_section_terms_of_service),
                        style = LocalTextStyle.current.copy(color = MaterialTheme.colors.primary)
                    )
                    HeightSpacer(height = 20.dp)
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = { onDismissButtonClick() }) {
                            Text(text = stringResource(R.string.activity_main_component_termsofservicedialog_btn_disagree))
                        }
                        Button(onClick = { onConfirmButtonClick() }) {
                            Text(text = stringResource(R.string.activity_main_component_termsofservicedialog_btn_agree))
                        }
                    }
                }
            }
        )
    }
}
