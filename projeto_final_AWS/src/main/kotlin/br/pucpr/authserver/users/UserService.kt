package br.pucpr.authserver.users

import br.pucpr.authserver.exception.BadRequestException
import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.roles.RoleRepository
import br.pucpr.authserver.security.Jwt
import br.pucpr.authserver.users.controller.responses.LoginResponse
import br.pucpr.authserver.users.controller.responses.UserResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    val avatarService: AvatarService,
    val repository: UserRepository,
    val roleRepository: RoleRepository,
    val jwt: Jwt,
    val avatarHelperService: AvatarHelperService
) {
    fun insert(user: User): User {
        if (repository.findByEmail(user.email) != null) {
            throw BadRequestException("User already exists")
        }

        val newUser = repository.save(user)
        var finalUser = insertNewAvatar(newUser)
        log.info("User inserted: {}", newUser.id)
        finalUser = repository.save(finalUser!!)
        return finalUser ?: newUser
    }

    private fun insertNewAvatar(user: User): User? {
        val imageResult = avatarHelperService.fetchAvatarImage(user.email, user.name)
        if(imageResult != null) {
            user.avatar = avatarService.save(user,imageResult)
        }
        else {
            user.avatar=AvatarService.DEFAULT_AVATAR
        }
        return repository.findByEmail(user.email)
    }

    fun update(id: Long, name: String): User? {
        val user = findByIdOrThrow(id)
        if (user.name == name) return null
        user.name = name
        return repository.save(user)
    }

    fun findAll(dir: SortDir = SortDir.ASC): List<User> {
        var users = findAllUsers()
        // Update users that does not have avatar set
        var anyUserUpdated = false;
        if(users.any { it -> it.avatar.isEmpty() }) {
            users.forEach {
                if(it.avatar.isEmpty()) {
                    val userResult = insertNewAvatar(it)
                    repository.save(userResult!!)
                    anyUserUpdated = true
                }
            }
        }
        // If any user was updated, fetch all users again to get the updated avatars
        if(anyUserUpdated) {
          users = findAllUsers(dir)
        }

        return users
    }

    private fun findAllUsers(dir: SortDir = SortDir.ASC) = when (dir) {
        SortDir.ASC -> repository.findAll(Sort.by("name").ascending())
        SortDir.DESC -> repository.findAll(Sort.by("name").descending())
    }

    fun findByRole(role: String): List<User> = repository.findByRole(role)

    fun findByIdOrNull(id: Long) = repository.findById(id).getOrNull()
    private fun findByIdOrThrow(id: Long) =
        findByIdOrNull(id) ?: throw NotFoundException(id)

    fun delete(id: Long): Boolean {
        val user = findByIdOrNull(id) ?: return false
        if (user.roles.any { it.name == "ADMIN" }) {
            val count = repository.findByRole("ADMIN").size
            if (count == 1) throw BadRequestException("Cannot delete the last system admin!")
        }
        repository.delete(user)
        log.info("User deleted: {}", user.id)
        return true
    }

    fun deleteAvatar(id: Long):Boolean {
        val user = findByIdOrThrow(id)
        avatarService.delete(user.avatar)
        repository.save(user)
        val userResult = insertNewAvatar(user)
        repository.save(userResult!!)
        return true
    }

    fun addRole(id: Long, roleName: String): Boolean {
        val user = findByIdOrThrow(id)
        if (user.roles.any { it.name == roleName }) return false

        val role = roleRepository.findByName(roleName) ?: throw BadRequestException("Invalid role: $roleName")

        user.roles.add(role)
        repository.save(user)
        log.info("Granted role {} to user {}", role.name, user.id)
        return true
    }

    fun login(email: String, password: String): LoginResponse? {
        val user = repository.findByEmail(email) ?: return null
        if (user.password != password) return null

        log.info("User logged in. id={}, name={}", user.id, user.name)
        return LoginResponse(
            token = jwt.createToken(user),
            user = toResponse(user)
        )
    }

    fun saveAvatar(id: Long, avatar: MultipartFile) {
        val user = findByIdOrThrow(id)
        user.avatar = avatarService.save(user, avatar)
        repository.save(user)
    }

    fun toResponse(user: User): UserResponse =
        UserResponse(user, avatarService.urlFor(user.avatar))

    companion object {
        private val log = LoggerFactory.getLogger(UserService::class.java)
    }
}
