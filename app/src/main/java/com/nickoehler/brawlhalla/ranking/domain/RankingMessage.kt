package com.nickoehler.brawlhalla.ranking.domain

import com.nickoehler.brawlhalla.core.domain.util.Message

sealed interface RankingMessage : Message {
    data class Saved(val name: String) : RankingMessage
    data class Removed(val name: String) : RankingMessage
    data class Message(val resource: Int) : RankingMessage
    data object NoResult : RankingMessage
}