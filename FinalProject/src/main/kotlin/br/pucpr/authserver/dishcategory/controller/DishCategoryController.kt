package br.pucpr.authserver.dishcategory.controller

import br.pucpr.authserver.dishcategory.DishCategoryService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dish-categories")
class DishCategoryController(
    val dishCategoryService: DishCategoryService
) {

    @GetMapping("/check")
    fun ping() = "Pong"

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "AuthServer")
    fun insert(@RequestBody @Valid dishCategory: CreateDishCategoryDto) =
        dishCategoryService.create(dishCategory.toDishCategory())

    @GetMapping
    fun findAll() = dishCategoryService.findAll()

    @PutMapping("/{id}/dishes/{dishId}")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "AuthServer")
    fun addDish(@PathVariable("dishId") dishId: Long, @PathVariable("id") id: Long) =
        dishCategoryService.addOrRemoveDish(dishId, id)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        dishCategoryService.findByIdOrNull(id)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "AuthServer")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        dishCategoryService.delete(id)
            .let { ResponseEntity.ok().build() }
}