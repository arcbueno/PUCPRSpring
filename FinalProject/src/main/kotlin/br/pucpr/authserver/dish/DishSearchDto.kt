package br.pucpr.authserver.dish

data class DishSearchDto(
    val dir: String = "ASC",
    val category: String?,
)