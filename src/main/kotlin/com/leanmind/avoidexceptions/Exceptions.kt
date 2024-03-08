package com.leanmind.avoidexceptions

class EmptyDataNotAllowedException : RuntimeException()

class CannotCreateUserException(exception: Exception) : RuntimeException(exception)

class PasswordTooShortException : RuntimeException()

