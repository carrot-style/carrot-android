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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import style.carrot.android.R
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.util.Util

// `Card` not implemented yet: https://m3.material.io/components/cards/implementation
@Composable
fun StyledCard(modifier: Modifier, carrotUrl: CarrotUrl) {
    val context = LocalContext.current

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color = Color(242, 240, 240))
            .clickable {
                Util.copy(context, carrotUrl.styled)
                toast(context.getString(R.string.ui_linkcard_toast_copied))
            }
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = carrotUrl.styled, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = carrotUrl.memo,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStyledCard() {
    StyledCard(Modifier, CarrotUrl("carrot.style/test", "www.naver.com", "just carrot-style."))
}
