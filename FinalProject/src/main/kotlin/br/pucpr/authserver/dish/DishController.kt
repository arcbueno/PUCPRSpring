package br.pucpr.authserver.dish

import br.pucpr.authserver.security.UserToken
import br.pucpr.authserver.utils.SortDir
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dishes")
class DishController(val dishService: DishService) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "AuthServer")
    fun create(@RequestBody dish: Dish) =
        dishService.save(dish).let {
            ResponseEntity.status(HttpStatus.CREATED).body(it)
        }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "AuthServer")
    fun findAll(@RequestParam dir: String = "ASC", @RequestParam category: String? = null): ResponseEntity<List<Dish>> {
        val userIdAdmin = true
        val sortDir = SortDir.findOrNull(dir) ?: return ResponseEntity.badRequest().build()
        var result : ResponseEntity<List<Dish>>
        try {
            result = ResponseEntity.ok(dishService.findAll(sortDir, category, userIdAdmin))
        } catch (e: Exception) {
            println(e.toString())
            result = ResponseEntity.notFound().build()
        }
        return result
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "AuthServer")
    fun findAllPost(@RequestBody dto: DishSearchDto? = null, authentication: Authentication): ResponseEntity<List<Dish>> {
        val userIdAdmin = (authentication.principal as UserToken).isAdmin
        val dir = dto?.dir ?: "ASC"
        val category = dto?.category
        val sortDir = SortDir.findOrNull(dir) ?: return ResponseEntity.badRequest().build()
        var result : ResponseEntity<List<Dish>>
        try {
            result = ResponseEntity.ok(dishService.findAll(sortDir, category, userIdAdmin))
        } catch (e: Exception) {
            println(e.toString())
            result = ResponseEntity.notFound().build()
        }
        return result
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "AuthServer")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        dishService.delete(id)
            .let { ResponseEntity.ok().build() }
}