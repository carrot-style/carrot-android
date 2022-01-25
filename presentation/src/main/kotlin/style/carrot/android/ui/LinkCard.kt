/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [LinkCard.kt] created by Ji Sungbin on 22. 1. 23. 오후 4:09
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import style.carrot.android.R
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.util.Util

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StyledCard(modifier: Modifier, carrotUrl: CarrotUrl) {
    val context = LocalContext.current

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(30.dp),
        backgroundColor = Color(242, 240, 240),
        onClick = {
            Util.copy(context, carrotUrl.styled)
            toast(context.getString(R.string.ui_linkcard_toast_copied))
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = carrotUrl.styled, style = MaterialTheme.typography.h6)
            Text(
                text = carrotUrl.memo,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStyledCard() {
    StyledCard(Modifier, CarrotUrl("carrot.style/test", "www.naver.com", "just carrot-style."))
}
