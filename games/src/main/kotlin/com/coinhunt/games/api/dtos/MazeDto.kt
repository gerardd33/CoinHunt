package com.coinhunt.games.api.dtos

import com.coinhunt.games.persistence.domain.components.FieldContent

data class MazeDto(val grid: List<List<FieldContent>>)
