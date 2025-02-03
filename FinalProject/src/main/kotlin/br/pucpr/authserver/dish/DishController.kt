package br.pucpr.authserver.dish

import br.pucpr.authserver.utils.SortDir
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dishes")
class DishController(val dishService: DishService) {

    @PostMapping
    fun create(@RequestBody dish: Dish) =
        dishService.save(dish).let {
            ResponseEntity.status(HttpStatus.CREATED).body(it)
        }

    @GetMapping
    fun findAll(@RequestParam dir: String = "ASC", @RequestParam category: String? = null): ResponseEntity<List<Dish>> {
        val sortDir = SortDir.findOrNull(dir) ?: return ResponseEntity.badRequest().build()
        return dishService.findAll(sortDir, category).let { ResponseEntity.ok(it) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        dishService.delete(id)
            .let { ResponseEntity.ok().build() }
}