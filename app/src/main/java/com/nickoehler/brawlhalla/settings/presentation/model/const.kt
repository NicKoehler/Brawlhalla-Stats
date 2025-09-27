package com.nickoehler.brawlhalla.settings.presentation.model

import androidx.core.net.toUri

val uriGithubAuthor = "https://github.com/NicKoehler".toUri()
val uriGithubProject = "https://github.com/NicKoehler/Brawlhalla-Stats".toUri()

val appLanguages = listOf(
    Language("", ""), // default language
    Language("en", "English"),
    Language("it", "Italiano"),
)