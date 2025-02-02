package br.pucpr.authserver.dish

import br.pucpr.authserver.roles.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "tblDish")
class Dish (
    @Id
    @GeneratedValue
    var id: Long? = null,

    @NotBlank
    var name: String,

    var description: String,

    @NotBlank
    var price: Double,

    var discount: Int?,
)