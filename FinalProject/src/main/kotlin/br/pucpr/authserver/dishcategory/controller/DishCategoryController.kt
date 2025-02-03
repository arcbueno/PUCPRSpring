package br.pucpr.authserver.dishcategory.controller

import br.pucpr.authserver.dishcategory.DishCategoryService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dish-categories")
class DishCategoryController(
    val dishCategoryService: DishCategoryService
) {

    @GetMapping("/check")
    fun ping() = "Pong"

    @PostMapping
    fun insert(@RequestBody @Valid dishCategory: CreateDishCategoryDto) =
        dishCategoryService.create(dishCategory.toDishCategory())

    @GetMapping
    fun findAll() = dishCategoryService.findAll()

    @PutMapping("/{id}/dishes/{dishId}")
    fun addDish(@PathVariable("dishId") dishId: Long, @PathVariable("id") id: Long) =
        dishCategoryService.addOrRemoveDish(dishId, id)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        dishCategoryService.findByIdOrNull(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        dishCategoryService.delete(id)
            .let { ResponseEntity.ok().build() }
}