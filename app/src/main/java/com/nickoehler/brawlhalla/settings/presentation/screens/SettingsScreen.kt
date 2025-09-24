package com.nickoehler.brawlhalla.settings.presentation.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.BuildConfig
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.domain.model.Theme
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.CustomModalBottomSheet
import com.nickoehler.brawlhalla.core.presentation.components.CustomSettingTile
import com.nickoehler.brawlhalla.core.presentation.components.ModalBottomSheetItem
import com.nickoehler.brawlhalla.core.presentation.components.SettingTile
import com.nickoehler.brawlhalla.core.presentation.util.ObserveAsEvents
import com.nickoehler.brawlhalla.settings.presentation.model.Language
import com.nickoehler.brawlhalla.settings.presentation.model.SettingType
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsAction
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsEvent
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsState
import com.nickoehler.brawlhalla.settings.presentation.model.appLanguages
import com.nickoehler.brawlhalla.settings.presentation.model.uriGithubAuthor
import com.nickoehler.brawlhalla.settings.presentation.model.uriGithubProject
import com.nickoehler.brawlhalla.settings.presentation.screens.components.BouncyLogo
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.Spacing
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


    if (settingsState.currentSetting != null) {
        CustomModalBottomSheet(
            currentSettingValue = when (settingsState.currentSetting) {
                SettingType.Theme -> settingsState.currentTheme
                SettingType.Language -> settingsState.currentLanguage
            },
            onDismissRequest = { onSettingsAction(SettingsAction.SelectSetting(null)) },
            onItemClick = { value ->
                when (settingsState.currentSetting) {
                    SettingType.Theme -> onSettingsAction(SettingsAction.SetTheme(value as Theme))
                    SettingType.Language -> onSettingsAction(SettingsAction.SetLanguage(value as Language))
                }
            },
            items =
                when (settingsState.currentSetting) {
                    SettingType.Theme -> Theme.entries.map { theme ->
                        ModalBottomSheetItem(
                            value = theme,
                            stringValue = when (theme) {
                                Theme.Dark -> stringResource(R.string.settings_theme_dark)
                                Theme.Light -> stringResource(R.string.settings_theme_light)
                                Theme.System -> stringResource(R.string.settings_system)
                            },
                            icon = when (theme) {
                                Theme.System -> ImageVector.vectorResource(R.drawable.routine_24px)
                                Theme.Light -> Icons.Default.LightMode
                                Theme.Dark -> Icons.Default.DarkMode
                            }
                        )
                    }

                    SettingType.Language -> appLanguages.map { language ->
                        ModalBottomSheetItem(
                            value = language,
                            stringValue =
                                if (language.code.isBlank()) {
                                    stringResource(R.string.settings_system)
                                } else {
                                    language.displayLanguage
                                }
                        )
                    }
                }
        )
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
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BouncyLogo()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Brawlhalla", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    CustomCard(contentPadding = PaddingValues(4.dp)) {
                        Text(
                            BuildConfig.VERSION_NAME,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
            Spacer(Modifier)
            CustomSettingTile(
                listOf(
                    SettingTile(
                        title = stringResource(R.string.settings_theme_title),
                        description = stringResource(
                            when (settingsState.currentTheme) {
                                Theme.System -> R.string.settings_system
                                Theme.Light -> R.string.settings_theme_light
                                Theme.Dark -> R.string.settings_theme_dark
                            }
                        ),
                        icon = when (settingsState.currentTheme) {
                            Theme.System -> ImageVector.vectorResource(
                                R.drawable.routine_24px
                            )

                            Theme.Light -> Icons.Default.LightMode
                            Theme.Dark -> Icons.Default.DarkMode
                        },
                        onClick = { onSettingsAction(SettingsAction.SelectSetting(SettingType.Theme)) },
                    ),
                    SettingTile(
                        title = stringResource(R.string.settings_language_title),
                        description =
                            if (settingsState.currentLanguage.code.isBlank()) {
                                stringResource(R.string.settings_system)
                            } else {
                                settingsState.currentLanguage.displayLanguage
                            },
                        icon = Icons.Default.Language,
                        onClick = { onSettingsAction(SettingsAction.SelectSetting(SettingType.Language)) }
                    ),
                ),
                modifier = Modifier.padding(horizontal = Spacing.scaffoldWindowInsets)
            )

            CustomSettingTile(
                listOf(
                    SettingTile(
                        title = stringResource(R.string.developer),
                        icon = Icons.Default.DeveloperMode,
                        onClick = {
                            onSettingsAction(SettingsAction.ViewIntent(uriGithubAuthor))
                        }
                    ),
                    SettingTile(
                        title = stringResource(R.string.source_code),
                        icon = Icons.Default.Code,
                        onClick = {
                            onSettingsAction(SettingsAction.ViewIntent(uriGithubProject))
                        }
                    ),
                    SettingTile(
                        title = stringResource(R.string.licenses),
                        icon = Icons.AutoMirrored.Filled.Article,
                        onClick = onLicensesPressed
                    )
                ),
                modifier = Modifier.padding(horizontal = Spacing.scaffoldWindowInsets)
            )
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