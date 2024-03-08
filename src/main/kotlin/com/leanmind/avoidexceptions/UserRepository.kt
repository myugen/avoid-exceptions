package com.leanmind.avoidexceptions

import org.springframework.stereotype.Service

@Service
class UserRepository {
    private val users = mutableListOf<User>()

    fun exists(user: User): Boolean = users.any { it.username == user.username }

    fun findByUsername(username: String): User? = users.find { it.username == username }

    fun save(user: User): CreateUserResult {
        try {
            users.add(user)
            return CreateUserResult.success()
        } catch (exception: Exception) {
            return CreateUserResult.cannotCreateUserError()
        }

    }

    fun countOfAdmins(): Int = users.count { it.role == UserRole.ADMIN }
}
