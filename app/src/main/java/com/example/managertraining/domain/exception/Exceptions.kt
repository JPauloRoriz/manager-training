package com.example.managertraining.domain.exception

class EmptyFildException(private val errorMessage : String) : Exception(errorMessage)
class PasswordLenghtException(private val errorMessage : String) : Exception(errorMessage)
class PasswordNotConfirmException(private val errorMessage : String) : Exception(errorMessage)
class EmailInvalidException : Exception()
class NoConnectionInternetException : Exception()
class DefaultException : Exception()
class EmailOrPasswordInvalidException() : Exception()
class EmailExistingException() : Exception()