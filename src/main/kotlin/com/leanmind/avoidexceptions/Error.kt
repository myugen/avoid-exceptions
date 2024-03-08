package com.leanmind.avoidexceptions

sealed interface Error {
    object UserAlreadyExists : Error
    object TooManyAdmins : Error
    object CannotCreateUser : Error
}