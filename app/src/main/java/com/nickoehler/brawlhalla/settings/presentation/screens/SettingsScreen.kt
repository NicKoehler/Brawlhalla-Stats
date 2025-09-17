package com.nickoehler.brawlhalla.settings.presentation.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.BuildConfig
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.util.ObserveAsEvents
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsAction
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsEvent
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsState
import com.nickoehler.brawlhalla.settings.presentation.model.uriGithubAuthor
import com.nickoehler.brawlhalla.settings.presentation.model.uriGithubProject
import com.nickoehler.brawlhalla.settings.presentation.screens.components.SettingCardTheme
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onSettingsAction: (SettingsAction) -> Unit,
    onBack: () -> Unit,
    onLicensesPressed: () -> Unit,
    events: Flow<SettingsEvent>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    ObserveAsEvents(events) { event ->
        when (event) {
            is SettingsEvent.ViewIntent -> context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    event.uri
                )
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.settings_title), fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                }
            )
        },

        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(R.drawable.logo),
                    "logo",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(vertical = 20.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Brawlhalla", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    CustomCard(contentPadding = 4.dp) {
                        Text(
                            BuildConfig.VERSION_NAME,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
            Spacer(Modifier)

            SettingCardTheme(
                settingsState.currentTheme,
                { onSettingsAction(SettingsAction.SetTheme(it)) }
            )
            CustomCard(
                onClick = { onSettingsAction(SettingsAction.ViewIntent(uriGithubAuthor)) },
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.DeveloperMode, null)
                Text("NicKoehler")
            }

            CustomCard(
                onClick = { onSettingsAction(SettingsAction.ViewIntent(uriGithubProject)) },
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Code, null)
                Text("GitHub")
            }

            CustomCard(
                onClick = onLicensesPressed,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Article, null)
                Text(stringResource(R.string.licenses))
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun InfoScreenPreview() {
    BrawlhallaTheme {
        Surface {
            SettingsScreen(
                SettingsState(),
                {},
                {},
                {},
                emptyFlow(),
            )
        }
    }
}