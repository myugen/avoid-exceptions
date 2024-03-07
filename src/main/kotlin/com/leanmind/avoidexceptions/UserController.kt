package com.leanmind.avoidexceptions

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.*
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

        return try {
            val createUserResult = userService.create(userDto.toDomain())
            if (createUserResult.isSuccess()) {
                return status(CREATED).build<Any>()
            }
            return when (createUserResult.error) {
                is UserAlreadyExistsError -> status(BAD_REQUEST).body("User already exists.")
                is TooManyAdminsError -> status(BAD_REQUEST).body("Too many admins.")
                else -> status(INTERNAL_SERVER_ERROR).body("Something went wrong.")
            }

        } catch (exception: UserAlreadyExistsException) {
            status(BAD_REQUEST).body("User already exists.")
        } catch (exception: EmptyDataNotAllowedException) {
            status(BAD_REQUEST).body("User data is invalid.")
        } catch (exception: PasswordTooShortException) {
            status(BAD_REQUEST).body("Password is too short.")
        } catch (exception: TooManyAdminsException) {
            status(BAD_REQUEST).body("Too many admins.")
        } catch (exception: CannotCreateUserException) {
            status(INTERNAL_SERVER_ERROR).body("Cannot create user.")
        }
    }

    private fun UserDto.toDomain(): User {
        return User.from(this.username, this.password, this.role)
    }
}
