package io.snabble.pay.app.ui.accountscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.R
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Destination
@Composable
fun NewAccountScreen(
    navigator: DestinationsNavigator?,
) {
    AppBarLayout(
        title = "Bankkonto",
        navigator = navigator
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                NewAccountWidget(
                    modifier = Modifier
                        .padding(top = 32.dp)
                )
                Spacer(modifier = Modifier.height(96.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "SEPA-Lastschriftmandat",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    InfoText(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        string = "Ich ermächtige Snabble Pay die Zahlungen von meinem Konto mittels Lastschrift einzuziehen."
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                ElevatedButton(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = { }) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Zustimmen und loslegen",
                    )

                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun NewAccountScreenPreview() {
    SnabblePayTheme {
        NewAccountScreen(navigator = null)
    }
}

@Composable
fun NewAccountWidget(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        EditTextField()
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Divider()
            TableRow(descriptor = "KontoInhaber", value = "Peter Mustermann")
            Divider()
            TableRow(descriptor = "IBAN", value = "DE12 3456 7891 0111 2131 41")
            Divider()
            TableRow(descriptor = "Bank", value = "Muster Bank")
        }
    }
}

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
) {
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        value = "",
        onValueChange = {},
        placeholder = {
            Text(
                text = "Mein Bankkonto"
            )
        },
        trailingIcon = {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                painter = painterResource(id = R.drawable.ic_snabble_edit),
                contentDescription = ""
            )
        }
    )
}

@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    descriptor: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = TextFieldDefaults.MinHeight
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = descriptor,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            modifier = Modifier
                .weight(2f)
                .padding(start = 8.dp),
            text = value,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
        )

    }
}

@Preview(
    showBackground = true
)
@Composable
fun TableRowPreview() {
    TableRow(
        descriptor = "Bank",
        value = "Muster Bank"
    )
}
