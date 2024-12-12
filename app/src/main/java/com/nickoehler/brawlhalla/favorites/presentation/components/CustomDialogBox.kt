package com.nickoehler.brawlhalla.favorites.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nickoehler.brawlhalla.R

@Composable
fun CustomDialogBox(
    title: String,
    description: String,
    shouldShowDialog: Boolean,
    onDismissRequest: () -> Unit,
    onAcceptRequest: () -> Unit,
) {
    if (shouldShowDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title) },
            text = { Text(text = description) },
            confirmButton = {
                Button(
                    onClick = onAcceptRequest
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                    )
                }
            }, dismissButton = {
                Button(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                    )
                }
            }
        )
    }
}