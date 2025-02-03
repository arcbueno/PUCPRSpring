package br.pucpr.authserver.dish

import br.pucpr.authserver.dishcategory.DishCategoryService
import br.pucpr.authserver.utils.SortDir
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DishService(val dishRepository: DishRepository) {
    fun findAll(dir: SortDir, category: String?): List<Dish> {
        if (!category.isNullOrBlank())
            return dishRepository.findAllByCategory(category)
        return when (dir) {
            SortDir.ASC -> dishRepository.findAll(Sort.by("name"))
            SortDir.DESC -> dishRepository.findAll(Sort.by("name").descending())
        }
    }

    fun save(dish: Dish): Dish = dishRepository.save(dish)
    fun findByIdOrNull(id: Long) = dishRepository.findByIdOrNull(id)
}