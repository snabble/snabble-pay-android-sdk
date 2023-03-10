package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import io.snabble.pay.app.R

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
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_background),
            contentDescription = ""
        )
    }
}
