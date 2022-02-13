package com.coinhunt.games.api.mappers

import com.coinhunt.games.api.dtos.CompletedGameDto
import com.coinhunt.games.persistence.domain.documents.CompletedGame
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import java.time.Instant

@Mapper(componentModel = "spring")
abstract class CompletedGameDtoMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "startTimeEpochMilli", target = "startTime", qualifiedByName = ["epochMilliToInstant"])
    abstract fun dtoToDomain(dto: CompletedGameDto): CompletedGame

    @Named("epochMilliToInstant")
    fun epochMilliToInstant(epochMilli: Long?): Instant = Instant.ofEpochMilli(epochMilli!!)
}
