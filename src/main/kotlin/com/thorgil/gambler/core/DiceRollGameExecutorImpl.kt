package com.thorgil.gambler.core

import com.thorgil.gambler.dataproviders.DataProviderInterface
import java.lang.IllegalArgumentException
import kotlin.random.Random

class DiceRollGameExecutorImpl(private val dataProvider: DataProviderInterface): DiceRollGameExecutorInterface {

    override fun playGame(diceRollGamePlayRequest: DiceRollGamePlayRequest): DiceRollGamePlayResponse {

        val user = dataProvider.getUserDetails(diceRollGamePlayRequest.cardNumber)

        System.out.println(user)

        if (user == null) {

            throw IllegalArgumentException()

        }

        // TODO authorization check

        if (diceRollGamePlayRequest.DiceRollGameType == DiceRollGameType.DOUBLE_POINTS_ON_EVEN) {

            val rollResult = Random.nextInt(1,6)

            if (rollResult % 2 == 0) {

                // even so a WIN

                if (user.points == 0) {

                    return DiceRollGamePlayResponse(DiceRollGameType.DOUBLE_POINTS_ON_EVEN,
                            user.cardNumber,
                            DiceRollGameResult.WIN,
                            rollResult,
                            0,
                            user.points,
                            user.points)

                } else {

                    // WARNING: because we are not using transactions it is probably possible that we have an outdated
                    // credit balance here (the user could have spent all their points by this time with extremely
                    // unlucky timing), perhaps the dataprovider could ask us to supply the credit balance we think
                    // it should be when doing a credit of this kind (if it is not then it will not accept the credit)

                    val userAfterCredit = dataProvider.credit(user.cardNumber, user.points)

                    if (userAfterCredit != null) {

                        System.out.println(userAfterCredit.toString())

                        return DiceRollGamePlayResponse(DiceRollGameType.DOUBLE_POINTS_ON_EVEN,
                                user.cardNumber,
                                DiceRollGameResult.WIN,
                                rollResult,
                                user.points,
                                user.points,
                                userAfterCredit.points)
                    } else {

                        // should get rid of this case, or perhaps not because the dataProvider may
                        // in fact not return anything if there is an underlying remote service
                        // we are abstracted away from these realities here
                        throw RuntimeException()

                    }
                }

            } else {

                // odd so a LOSE
                return DiceRollGamePlayResponse(DiceRollGameType.DOUBLE_POINTS_ON_EVEN,
                                        user.cardNumber,
                                        DiceRollGameResult.LOSE,
                                        rollResult,
                              0,
                                        user.points,
                                        user.points)

            }


        } else {

            throw IllegalArgumentException("Only support DOUBLE_POINTS_ON_EVEN at present")

        }

    }

}