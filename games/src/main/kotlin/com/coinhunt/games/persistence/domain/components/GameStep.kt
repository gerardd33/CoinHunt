package com.coinhunt.games.persistence.domain.components

data class GameStep(val direction: StepDirection, val millisecondsSinceLastStep: Long)
