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

    @Mapping(source = "startTime", target = "startTimeEpochMilli", qualifiedByName = ["instantToEpochMilli"])
    abstract fun domainToDto(domain: CompletedGame): CompletedGameDto

    @Named("epochMilliToInstant")
    fun epochMilliToInstant(epochMilli: Long?): Instant = Instant.ofEpochMilli(epochMilli!!)

    @Named("instantToEpochMilli")
    fun instantToEpochMilli(instant: Instant): Long = instant.toEpochMilli()
}
