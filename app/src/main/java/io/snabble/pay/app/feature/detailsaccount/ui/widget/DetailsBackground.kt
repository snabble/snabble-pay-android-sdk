package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R
import io.snabble.pay.app.ui.theme.lightGrey

@Composable
fun DetailsBackground(
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_ellipse),
            contentDescription = ""
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = lightGrey)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF708090
)
@Composable
fun BackGroundPreview() {
    DetailsBackground(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    )
}
