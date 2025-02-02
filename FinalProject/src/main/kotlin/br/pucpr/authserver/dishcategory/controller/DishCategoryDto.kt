package br.pucpr.authserver.dishcategory.controller

import br.pucpr.authserver.dish.Dish
import br.pucpr.authserver.dishcategory.DishCategory

data class DishCategoryDto(
    val id: Long,
    val name: String,
    val dishes: List<Dish>
) {
    constructor(dishCategory: DishCategory): this(id = dishCategory.id!!, dishes = dishCategory.dishes.toList(), name = dishCategory.name)
}
