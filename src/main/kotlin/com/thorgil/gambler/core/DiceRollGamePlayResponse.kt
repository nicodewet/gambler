package com.thorgil.gambler.core

data class DiceRollGamePlayResponse(val DiceRollGameType: DiceRollGameType,
                                    val cardNumber: String,
                                    val resultDiceRoll: DiceRollGameResult,
                                    val valueRolled: Int,
                                    val pointsWon: Int,
                                    val balanceBefore: Int,
                                    val balanceAfter: Int) {
}