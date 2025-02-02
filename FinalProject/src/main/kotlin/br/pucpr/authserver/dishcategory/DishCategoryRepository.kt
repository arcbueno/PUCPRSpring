package br.pucpr.authserver.dishcategory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DishCategoryRepository: JpaRepository<DishCategory, Long> {

    @Query("select distinct c from DishCategory c " +
            " join c.dishes d" +
            " where d.name = :category" +
            " order by d.name"
    )
    fun findByCategoryName(categoryName: String): List<DishCategory>
}