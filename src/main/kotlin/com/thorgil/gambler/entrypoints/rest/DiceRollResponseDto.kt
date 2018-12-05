package com.thorgil.gambler.entrypoints.rest

data class DiceRollResponseDto(val gameType: String,
                               val cardNumber: String,
                               val resultDiceRoll: String,
                               val valueRolled: Int,
                               val pointsWon: Int,
                               val balanceBefore: Int,
                               val balanceAfter: Int) {
}