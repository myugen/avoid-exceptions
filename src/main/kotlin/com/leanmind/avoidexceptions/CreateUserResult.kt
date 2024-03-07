package com.leanmind.avoidexceptions

open class Error
class UserAlreadyExistsError : Error()
class TooManyAdminsError : Error()

class CreateUserResult private constructor(
        val error: Error?,
) {
    fun isSuccess(): Boolean {
        return error === null
    }

    companion object {
        fun success(): CreateUserResult {
            return CreateUserResult(null)
        }

        fun userAlreadyExistsError(): CreateUserResult {
            return CreateUserResult(UserAlreadyExistsError())
        }

        fun tooManyAdminsError(): CreateUserResult {
            return CreateUserResult(TooManyAdminsError())
        }
    }
}
