package com.nickoehler.brawlhalla.ranking.domain

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class EstimatedGloryUseCase {
    operator fun invoke(games: Int, wins: Int, peakRating: Int): Int {
        if (games < 10) {
            return 0
        }

        var gloryFromRating = 0.0
        if (peakRating < 1200) gloryFromRating = 250.0
        if (peakRating in 1200..1285)
            gloryFromRating = 10 * (25 + 0.872093023 * (86 - (1286 - peakRating)))
        if (peakRating in 1286..1389)
            gloryFromRating = 10 * (100 + 0.721153846 * (104 - (1390 - peakRating)))
        if (peakRating in 1390..1679)
            gloryFromRating = 10 * (187 + 0.389655172 * (290 - (1680 - peakRating)))
        if (peakRating in 1680..1999)
            gloryFromRating = 10 * (300 + 0.428125 * (320 - (2000 - peakRating)))
        if (peakRating in 2000..2299)
            gloryFromRating = 10 * (437 + 0.143333333 * (300 - (2300 - peakRating)))
        if (peakRating >= 2300) gloryFromRating = 10 * (480 + 0.05 * (400 - (2700 - peakRating)))


        val gloryFromWins =
            if (wins <= 150) 20 * wins.toDouble() else floor(10 * (45 * log10(wins * 2.0).pow(2.0)) + 245)

        return (floor(gloryFromRating) + floor(gloryFromWins)).toInt()

    }
}