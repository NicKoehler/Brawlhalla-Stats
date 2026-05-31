package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.PlayerDto
import com.nickoehler.brawlhalla.ranking.domain.Player

fun PlayerDto.toPlayer() = Player(id, username)