package com.leanmind.avoidexceptions

import org.springframework.stereotype.Service

@Service
class UserRepository {
    private val users = mutableListOf<User>()

    fun exists(user: User): Boolean {
        return users.find { it.username == user.username } !== null
    }

    fun findByUsername(username: String): User? {
        return users.find { it.username == username }
    }

    fun save(user: User) {
        try {
            users.add(user)
        } catch (exception: Exception) {
            throw CannotPersistUserException(exception)
        }

    }

    fun countOfAdmins(): Int {
        return users.count { it.role == UserRole.ADMIN }
    }
}
