package br.pucpr.authserver.dishcategory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DishCategoryRepository: JpaRepository<DishCategory, Long> {
    fun findByName(categoryName: String): DishCategory?
}