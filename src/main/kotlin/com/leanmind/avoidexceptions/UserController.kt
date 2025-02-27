package com.leanmind.avoidexceptions

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping
    fun createUser(@RequestBody userDto: UserDto): ResponseEntity<*> {
        val createUserResult = userService.create(userDto.toDomain())
        return when (createUserResult.error) {
            Error.UserAlreadyExists -> status(BAD_REQUEST).body("User already exists.")
            Error.TooManyAdmins -> status(BAD_REQUEST).body("Too many admins.")
            Error.CannotCreateUser -> status(INTERNAL_SERVER_ERROR).body("Cannot create user.")
            null -> status(CREATED).build<Unit>()
        }
    }

    private fun UserDto.toDomain(): User {
        return User.from(this.username, this.password, this.role)
    }
}
