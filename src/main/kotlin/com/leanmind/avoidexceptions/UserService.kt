package com.leanmind.avoidexceptions

import com.leanmind.avoidexceptions.UserRole.ADMIN
import org.springframework.stereotype.Service
import java.util.logging.Level
import java.util.logging.Logger

private const val MAX_NUMBER_OF_ADMINS = 2

@Service
class UserService(private val userRepository: UserRepository) {
    val logger: Logger = Logger.getLogger(UserService::class.java.name)

    fun create(user: User): CreateUserResult {
        try {
            if (userRepository.exists(user)) {
                return CreateUserResult.userAlreadyExistsError()
            }
            if (user.isAdmin() && cannotExistsMoreAdmins()) {
                return CreateUserResult.tooManyAdminsError()
            }
            userRepository.save(user)
            logger.log(Level.INFO, "User created.")
            return CreateUserResult.success()
        } catch (exception: UserAlreadyExistsException) {
            logger.log(Level.WARNING, "User already exists.", exception)
            throw exception
        } catch (exception: TooManyAdminsException) {
            logger.log(Level.WARNING, "Too many admins.", exception)
            throw exception
        } catch (exception: Exception) {
            logger.log(Level.SEVERE, "Cannot create user.", exception)
            throw CannotCreateUserException(exception)
        }
    }

    private fun cannotExistsMoreAdmins() = userRepository.countOfAdmins() >= MAX_NUMBER_OF_ADMINS
}
