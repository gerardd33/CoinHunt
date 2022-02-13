package com.coinhunt.games.api.mappers

import com.coinhunt.games.api.dtos.MazeDto
import com.coinhunt.games.persistence.domain.components.Maze
import org.mapstruct.Mapper
import org.mapstruct.Mapping


@Mapper(componentModel = "spring")
interface MazeDtoMapper {

    @Mapping(ignore = true, target = "copy")
    fun domainToDto(maze: Maze): MazeDto
}
