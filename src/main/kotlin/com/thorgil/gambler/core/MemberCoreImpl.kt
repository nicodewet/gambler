package com.thorgil.gambler.core

import com.thorgil.gambler.dataproviders.DataProviderInterface

class MemberCoreImpl(private val dataProvider: DataProviderInterface): MemberCoreInterface {

    override fun getUser(cardNumber: String): Member? {
        if (cardNumber.isEmpty()) {
            throw IllegalArgumentException("cardNumber may not be empty")
        }
        val dataProviderUser = dataProvider.getUserDetails(cardNumber)
        return if (dataProviderUser == null) {
            null
        } else {
            Member(dataProviderUser.cardNumber, dataProviderUser.points)
        }
    }
}