package com.thorgil.gambler.core

interface DiceRollGameExecutorInterface {

    /**
     *
     * @throws IllegalArgumentException if there is not user with the specified card
     * TODO @throws AccessDeniedException if the user is not authorized to use the specified card
     */
    fun playGame(diceRollGamePlayRequest: DiceRollGamePlayRequest): DiceRollGamePlayResponse

}