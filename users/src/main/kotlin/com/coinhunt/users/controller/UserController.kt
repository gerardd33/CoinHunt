package com.coinhunt.users.controller

import com.coinhunt.users.domain.UserData
import com.coinhunt.users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
  @Autowired private val userService: UserService
) {

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  fun registerUser(@RequestBody userData: UserData): UserData {
    userService.registerUser(userData)
    return userData
  }
}
