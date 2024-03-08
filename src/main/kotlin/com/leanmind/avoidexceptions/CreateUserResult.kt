package com.leanmind.avoidexceptions

class CreateUserResult private constructor(val error: Error?) {
    fun isSuccess(): Boolean {
        return error === null
    }

    companion object {
        fun success(): CreateUserResult = CreateUserResult(null)

        fun userAlreadyExistsError(): CreateUserResult = CreateUserResult(Error.UserAlreadyExists)

        fun tooManyAdminsError(): CreateUserResult = CreateUserResult(Error.TooManyAdmins)

        fun cannotCreateUserError(): CreateUserResult = CreateUserResult(Error.CannotCreateUser)
    }
}
