/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [LinkCard.kt] created by Ji Sungbin on 22. 1. 23. 오후 4:09
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import style.carrot.android.domain.model.CarrotUrl

// `Card` not implemented yet: https://m3.material.io/components/cards/implementation
@Composable
fun StyledCard(modifier: Modifier, carrotUrl: CarrotUrl) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = Color(242, 240, 240),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 15.dp),
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
