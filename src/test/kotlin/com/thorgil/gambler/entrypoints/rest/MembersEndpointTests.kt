package com.thorgil.gambler.entrypoints.rest

import com.nhaarman.mockito_kotlin.any
import com.thorgil.gambler.core.MemberCoreInterface
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import com.nhaarman.mockito_kotlin.whenever
import com.thorgil.gambler.core.Member
import org.hamcrest.Matchers
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest
// TODO Use constructor-based val property for @MockBean when supported, see issue spring-boot#13113
@AutoConfigureRestDocs
class MembersEndpointTests(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var gamblerCoreInterface: MemberCoreInterface

    @Test
    fun `(green path) get member details by card number`() {

        // Arrange
        val cardNumber = "6014355731673470"
        val user = Member(cardNumber,1000000)
        whenever(gamblerCoreInterface.getUser(any())).thenReturn(user)

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get(
                MembersEndpoint.MEMBER_ENDPOINT_URL + "/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber", Matchers.equalTo(user.cardNumber) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.points", Matchers.equalTo(user.points) ))
                .andDo(document("get-member-details"))

    }

    @Test
    fun `(invalid path) get member details by blank card number`() {

        // Arrange
        val cardNumber = ""
        val user = Member("",1000000)
        whenever(gamblerCoreInterface.getUser(any())).thenReturn(user)

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get(
                MembersEndpoint.MEMBER_ENDPOINT_URL + "/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber", Matchers.equalTo(user.cardNumber) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.points", Matchers.equalTo(user.points) ))
                .andDo(document("get-member-details"))

    }

    @Test
    fun `(invalid path - unauthorized) attempt to get another members details by card number`() {

    }
}