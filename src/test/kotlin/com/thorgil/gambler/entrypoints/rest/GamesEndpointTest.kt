package com.thorgil.gambler.entrypoints.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.thorgil.gambler.core.DiceRollGameExecutorInterface
import com.thorgil.gambler.core.DiceRollGamePlayResponse
import com.thorgil.gambler.core.DiceRollGameResult
import com.thorgil.gambler.core.DiceRollGameType
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest
// TODO Use constructor-based val property for @MockBean when supported, see issue spring-boot#13113
@AutoConfigureRestDocs
class GamesEndpointTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var diceRollGameExecutorInterface: DiceRollGameExecutorInterface

    @Test
    fun `(green path) post double-or-nothing dice roll game play request`() {

        // Given

        val cardNumber = "6014355731673470"

        val diceRollGamePlayRespone = DiceRollGamePlayResponse(DiceRollGameType.DOUBLE_POINTS_ON_EVEN,
                                                                cardNumber,
                                                                DiceRollGameResult.WIN,
                                                        6,
                                                        1000,
                                                      1000,
                                                       2000)

        whenever(diceRollGameExecutorInterface.playGame(any())).thenReturn(diceRollGamePlayRespone)

        val diceRollRequestPostBody = DiceRollRequestDto(cardNumber)

        val mapper = ObjectMapper()
        val diceRollRequestPostBodyJson = mapper.writeValueAsString(diceRollRequestPostBody)

        val game = GamesEndpoint.DOUBLE_OR_NOTHING

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/games/" + GamesEndpoint.DOUBLE_OR_NOTHING)
                .content(diceRollRequestPostBodyJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameType", Matchers.equalTo(game) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber", Matchers.equalTo(cardNumber) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultDiceRoll",
                        Matchers.equalTo(DiceRollGameResult.WIN.toString()) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueRolled",
                        Matchers.equalTo(diceRollGamePlayRespone.valueRolled) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pointsWon",
                        Matchers.equalTo(diceRollGamePlayRespone.pointsWon) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balanceBefore",
                        Matchers.equalTo(diceRollGamePlayRespone.balanceBefore) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balanceAfter",
                        Matchers.equalTo(diceRollGamePlayRespone.balanceAfter) ))
                .andDo(MockMvcRestDocumentation.document("post-double-or-nothing-game-play-request"))

    }

}