package com.thorgil.gambler.dataproviders

class DataProviderImpl : DataProviderInterface {

    val users: MutableMap<String, DataProviderUser> = mutableMapOf()

    companion object {
        val ACTUAL_CARD_NUMBER_ONE = "6014355731673470"
        val ACTUAL_CARD_NUMBER_TWO = "6014355513306243"
    }

    init {
        addUser(ACTUAL_CARD_NUMBER_ONE, 1000)
        addUser(ACTUAL_CARD_NUMBER_TWO, 1000)
        addUser("333333", 1000)
        addUser("444444", 1000)
    }

    override fun debit(cardNumber: String, points: Int): DataProviderUser? {
        if (points <= 0) {
            throw IllegalArgumentException("points must be a positive number")
        } else {
            val user: DataProviderUser? = users[cardNumber]
            return if (user == null) {
                null
            } else {
                users[cardNumber] = DataProviderUser(cardNumber, user.points + points)
                users[cardNumber]
            }
        }

    }

    override fun credit(cardNumber: String, points: Int): DataProviderUser? {
        if (points <= 0) {
            throw IllegalArgumentException("points must be a positive number")
        } else {
            val user: DataProviderUser? = users[cardNumber]
            return if (user == null) {
                null
            } else {
                val prospectivePointsBalance = user.points + points
                if (prospectivePointsBalance < 0) {
                    throw IllegalArgumentException("debit will resultDiceRoll in negative balance")
                } else {
                    users[cardNumber] = DataProviderUser(cardNumber, prospectivePointsBalance)
                    users[cardNumber]
                }
            }
        }

    }

    override fun getUserDetails(cardNumber: String): DataProviderUser? {
        return users.get(cardNumber);
    }

    fun addUser(cardNumber: String, points: Int) {
        users[cardNumber] = DataProviderUser(cardNumber, points)
    }
}