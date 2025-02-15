package com.nickoehler.brawlhalla.ranking.domain

import kotlin.math.floor

class EstimatedEloResetUseCase {
    operator fun invoke(currentRating: Int): Int {
        return if (currentRating >= 1400) floor(1400 + (currentRating - 1400.0) / (3.0 - (3000 - currentRating) / 800.0))
            .toInt() else currentRating
    }
}