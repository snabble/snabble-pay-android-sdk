package io.snabble.pay.account.data.di

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.snabble.pay.shared.account.data.mapper.AccountMapper
import io.snabble.pay.shared.account.data.mapper.MandateStateMapper
import io.snabble.pay.shared.account.di.sharedModule
import org.koin.test.KoinTest
import org.koin.test.get

class AccountModuleTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(
            listOf(
                sharedModule
            )
        )
    )

    init {
        "Koin provides" - {

            "the mapper" - {

                "AccountMapper" {
                    get<AccountMapper>().shouldNotBeNull()
                }

                "MandateStateMapper" {
                    get<MandateStateMapper>().shouldNotBeNull()
                }
            }
        }
    }
}
