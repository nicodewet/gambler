package com.thorgil.gambler

import com.thorgil.gambler.core.DiceRollGameExecutorImpl
import com.thorgil.gambler.core.DiceRollGameExecutorInterface
import com.thorgil.gambler.core.MemberCoreImpl
import com.thorgil.gambler.core.MemberCoreInterface
import com.thorgil.gambler.dataproviders.DataProviderImpl
import com.thorgil.gambler.dataproviders.DataProviderInterface
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GamblerApplication {

    @Bean
    fun diceRollExecutor(businessTransactionDataProvider: DataProviderInterface): DiceRollGameExecutorInterface {
        return DiceRollGameExecutorImpl(businessTransactionDataProvider)
    }

    @Bean
    fun gamblerCore(businessTransactionDataProvider: DataProviderInterface): MemberCoreInterface {
        return MemberCoreImpl(businessTransactionDataProvider)
    }

    @Bean
    fun businessTransactionDataProvider(): DataProviderInterface {
        return DataProviderImpl()
    }
}

fun main(args: Array<String>) {
    runApplication<GamblerApplication>(*args)
}
