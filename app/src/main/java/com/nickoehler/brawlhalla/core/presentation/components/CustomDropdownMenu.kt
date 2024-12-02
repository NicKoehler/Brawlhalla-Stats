package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun <T> CustomDropdownMenu(
    title: String,
    selected: T,
    modifier: Modifier = Modifier,
    items: List<T> = emptyList(),
    onSelect: (T) -> Unit = {}
) {

    var isOpen by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button({ isOpen = true }
        ) {
            Text("$title: $selected")
        }
        DropdownMenu(isOpen, { isOpen = false }) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it.toString()) },
                    onClick = { onSelect(it) },
                    trailingIcon = {
                        if (it == selected) {
                            Icon(Icons.Default.Check, null)
                        }
                    }
                )
            }
        }
    }
}