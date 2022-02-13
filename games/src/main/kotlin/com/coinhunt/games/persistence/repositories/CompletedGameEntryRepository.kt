package com.coinhunt.games.persistence.repositories

import com.coinhunt.games.persistence.domain.documents.CompletedGameEntry
import org.springframework.data.mongodb.repository.MongoRepository

interface CompletedGameEntryRepository : MongoRepository<CompletedGameEntry, String>
