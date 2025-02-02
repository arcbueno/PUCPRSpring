package br.pucpr.authserver.dishcategory.controller

import br.pucpr.authserver.dishcategory.DishCategory
import jakarta.validation.constraints.NotBlank

data class CreateDishCategoryDto(
    @field:NotBlank
    val name: String?
) {
    fun toDishCategory() = DishCategory(
        name = name!!
    )
}