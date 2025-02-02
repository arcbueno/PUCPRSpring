package br.pucpr.authserver.dish

import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DishService(val dishRepository: DishRepository) {
    fun findAll(): List<Dish> = dishRepository.findAll(Sort.by("name"))
    fun save(dish: Dish): Dish = dishRepository.save(dish)
    fun findByIdOrNull(id: Long) = dishRepository.findByIdOrNull(id)
}