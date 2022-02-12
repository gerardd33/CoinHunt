package com.coinhunt.games.api.mappers

import com.coinhunt.games.api.dtos.LevelInfoDto
import com.coinhunt.games.persistence.domain.documents.LevelInfo
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface LevelInfoDtoMapper {

    fun domainToDto(domainObject: LevelInfo): LevelInfoDto
}
