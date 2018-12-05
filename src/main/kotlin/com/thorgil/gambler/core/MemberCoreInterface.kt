package com.thorgil.gambler.core

interface MemberCoreInterface {

    /**
     * Fetch a user.
     *
     * @param cardNumber may not be empty
     * @throws IllegalArgumentException if the cardNumber rules are not met
     */
    fun getUser(cardNumber: String): Member?

}