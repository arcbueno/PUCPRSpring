package br.pucpr.authserver.dish

import br.pucpr.authserver.utils.SortDir
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DishService(val dishRepository: DishRepository) {
    fun findAll(dir: SortDir, category: String?, isAdmin: Boolean): List<Dish> {
        var list: List<Dish> = mutableListOf()
        if (!category.isNullOrBlank())
            list = dishRepository.findAllByCategory(category)
        else {
            list = when (dir) {
                SortDir.ASC -> dishRepository.findAll(Sort.by("name"))
                SortDir.DESC -> dishRepository.findAll(Sort.by("name").descending())
            }
        }
        return if (isAdmin) list else list.filter { !it.isDisabled }
    }

    fun save(dish: Dish): Dish = dishRepository.save(dish)
    fun findByIdOrNull(id: Long) = dishRepository.findByIdOrNull(id)
    fun delete(id: Long) =
        dishRepository.deleteById(id)
}