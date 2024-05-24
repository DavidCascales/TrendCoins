package com.trendCoins.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxWithText(
    label: String,
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    enabledState: Boolean = true,
    onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = modifier
           /* .then(
                Modifier

                    .height(56.dp)
                    .toggleable(
                        value = checkedState,
                        onValueChange = { onStateChange(it) },
                        role = Role.Checkbox
                    )
            )*/
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = onCheckedChange,
            enabled = enabledState
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckBoxCommonTest() {

    var checkedState by remember { mutableStateOf(false) }

    Column {
        CheckboxWithText(
            label = "Prueba",
            modifier = Modifier.fillMaxWidth(),
            checkedState = checkedState,
            onCheckedChange = { checkedState = it }
        )
    }
}