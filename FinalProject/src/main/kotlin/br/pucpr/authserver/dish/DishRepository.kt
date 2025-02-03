package br.pucpr.authserver.dish

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DishRepository : JpaRepository<Dish, Long> {
    @Query(
        "select distinct d from DishCategory c" +
                " join c.dishes d" +
                " where c.name = :category" +
                " order by d.name"
    )
    fun findAllByCategory(category: String): List<Dish>
}