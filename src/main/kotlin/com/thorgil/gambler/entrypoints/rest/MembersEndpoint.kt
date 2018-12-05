package com.thorgil.gambler.entrypoints.rest

import com.thorgil.gambler.core.MemberCoreInterface
import com.thorgil.gambler.entrypoints.rest.MembersEndpoint.Companion.MEMBER_ENDPOINT_URL
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(MEMBER_ENDPOINT_URL)
class MembersEndpoint(private val gamblerCoreInterface: MemberCoreInterface) {

    companion object {
        const val MEMBER_ENDPOINT_URL: String = "/api/members"
    }

    @RequestMapping(value = ["**/{cardNumber}"],
    method = [RequestMethod.GET])
    fun getBusinessTransaction(@PathVariable("cardNumber") cardNumber: String): ResponseEntity<UserDto> {

        val coreUser = gamblerCoreInterface.getUser(cardNumber);

        return if (coreUser != null) {
           val dtoUser = UserDto(coreUser.cardNumber, coreUser.points)
            ResponseEntity.ok(dtoUser)
        } else {
            ResponseEntity.notFound().build()
        }

    }
}