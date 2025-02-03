package br.pucpr.authserver.dishcategory

import br.pucpr.authserver.dish.DishService
import br.pucpr.authserver.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DishCategoryService(
    val dishCategoryRepository: DishCategoryRepository,
    val dishService: DishService
) {
    fun create(dishCategory: DishCategory) =
        dishCategoryRepository.save(dishCategory)

    fun findAll(): List<DishCategory> =
        dishCategoryRepository.findAll()

    fun delete(id: Long) =
        dishCategoryRepository.deleteById(id)

    fun findByIdOrNull(id: Long) =
        dishCategoryRepository.findByIdOrNull(id)

    fun addOrRemoveDish(dishId: Long, dishCategoryId: Long) {
        val dish = dishService.findByIdOrNull(dishId) ?: throw NotFoundException("Dish ${dishId} not found")
        val dishCategory = dishCategoryRepository.findByIdOrNull(dishCategoryId)
            ?: throw NotFoundException("DishCategory ${dishCategoryId} not found")
        if (dishCategory.dishes.any { inDish -> inDish.id == dishId }) {
            dishCategory.dishes.remove(dish)
        } else {
            dishCategory.dishes.add(dish)
        }
        dishCategoryRepository.save(dishCategory)
    }
}