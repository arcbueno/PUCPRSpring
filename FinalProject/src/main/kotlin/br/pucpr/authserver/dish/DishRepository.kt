package br.pucpr.authserver.dish

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DishRepository: JpaRepository<Dish, Long>