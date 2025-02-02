package br.pucpr.authserver.dishcategory

import br.pucpr.authserver.dish.Dish
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "tblDishCategory")
class DishCategory (
    @Id
    @GeneratedValue
    var id: Long? = null,

    @NotBlank
    var name: String,

    @ManyToMany
    @JoinTable(
        name="Dishes",
        joinColumns = [JoinColumn(name = "idDishCategory")],
        inverseJoinColumns = [JoinColumn(name = "idDish")]
    )
    val roles: MutableSet<Dish> = mutableSetOf()
)