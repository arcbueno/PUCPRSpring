package br.pucpr.authserver.dish

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dishes")
class DishController(val dishService: DishService) {

    @PostMapping
    fun create(@RequestBody dish: Dish) =
        dishService.save(dish).let {
            ResponseEntity.status(HttpStatus.CREATED).body(it)
        }

    @GetMapping
    fun findAll() = dishService.findAll()
}