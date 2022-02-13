package com.coinhunt.games.persistence.repositories

import com.coinhunt.games.persistence.domain.documents.Statistics
import org.springframework.data.mongodb.repository.MongoRepository

interface StatisticsRepository : MongoRepository<Statistics, String>
