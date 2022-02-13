package com.coinhunt.games.api.mappers

import com.coinhunt.games.api.dtos.MazeDto
import com.coinhunt.games.persistence.domain.components.Maze
import org.mapstruct.Mapper


@Mapper(componentModel = "spring")
interface MazeDtoMapper {

    fun domainToDto(maze: Maze): MazeDto
}
