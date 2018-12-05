package com.thorgil.gambler.entrypoints.rest

import com.thorgil.gambler.core.DiceRollGameExecutorInterface
import com.thorgil.gambler.core.DiceRollGamePlayRequest
import com.thorgil.gambler.core.DiceRollGameType
import com.thorgil.gambler.entrypoints.rest.GamesEndpoint.Companion.GAMES_ENDPOINT_URL
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(GAMES_ENDPOINT_URL)
class GamesEndpoint(private val diceRollGameExecutorInterface: DiceRollGameExecutorInterface) {

    companion object {
        const val GAMES_ENDPOINT_URL: String = "/api/games/**"
        const val DOUBLE_OR_NOTHING: String = "double-or-nothing"
    }

    @PostMapping("**/{gametype}")
    fun gamePlayRequest(@PathVariable("gametype") gameType: String,
                        @RequestBody request: DiceRollRequestDto): ResponseEntity<DiceRollResponseDto> {

        if (gameType == "double-or-nothing") {

            // TODO validate input

            val gamePlayRequest = DiceRollGamePlayRequest(DiceRollGameType.DOUBLE_POINTS_ON_EVEN, request.cardNumber)

            val gamePlayResponse = diceRollGameExecutorInterface.playGame(gamePlayRequest)

            val responseDto = DiceRollResponseDto(DOUBLE_OR_NOTHING,
                                                    gamePlayResponse.cardNumber,
                                                    gamePlayResponse.resultDiceRoll.toString(),
                                                    gamePlayResponse.valueRolled,
                                                    gamePlayResponse.pointsWon,
                                                    gamePlayResponse.balanceBefore,
                                                    gamePlayResponse.balanceAfter)

            return ResponseEntity.ok(responseDto)

        } else {

            return ResponseEntity.badRequest().build()

        }

    }
}