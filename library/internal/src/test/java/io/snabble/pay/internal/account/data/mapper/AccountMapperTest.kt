package io.snabble.pay.internal.account.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.snabble.pay.internal.account.data.dto.AccountDto
import io.snabble.pay.shared.account.domain.model.Account.MandateState
import java.time.ZonedDateTime

internal class AccountMapperTest : FreeSpec({

    val mandateStateMapper = mockk<MandateStateMapper>(relaxed = true)

    fun createSut(): AccountMapper = AccountMapper(mandateStateMapper = mandateStateMapper)

    beforeEach {
        clearAllMocks()
    }

    "AccountCheckMapperTest should correctly map" - {

        "bank" {
            val expectedBank = "A bank"
            val accountDto = mockk<AccountDto>(relaxed = true) {
                every { bank } returns expectedBank
            }

            val sut = createSut()

            sut.map(from = accountDto).bank shouldBe expectedBank
        }

        "createdAt" {
            val expectedCreatedAt = ZonedDateTime.now()
            val accountDto = mockk<AccountDto>(relaxed = true) {
                every { createdAt } returns expectedCreatedAt
            }

            val sut = createSut()

            sut.map(from = accountDto).createdAt shouldBe expectedCreatedAt
        }

        "currencyCode" {
            val expectedCurrencyCode = "EUR"
            val accountDto = mockk<AccountDto>(relaxed = true) {
                every { currencyCode } returns expectedCurrencyCode
            }

            val sut = createSut()

            sut.map(from = accountDto).currencyCode shouldBe expectedCurrencyCode
        }

        "holderName" {
            val expectedHolderName = "John Doe"
            val accountDto = mockk<AccountDto>(relaxed = true) {
                every { holderName } returns expectedHolderName
            }

            val sut = createSut()

            sut.map(from = accountDto).holderName shouldBe expectedHolderName
        }

        "iban" {
            val expectedIban = "DE89 3704 0044 0532 0130 00"
            val accountDto = mockk<AccountDto>(relaxed = true) {
                every { iban } returns expectedIban
            }

            val sut = createSut()

            sut.map(from = accountDto).iban shouldBe expectedIban
        }

        "id" {
            val expectedId = "a1"
            val accountDto = mockk<AccountDto>(relaxed = true) {
                every { id } returns expectedId
            }

            val sut = createSut()

            sut.map(from = accountDto).id shouldBe expectedId
        }

        "mandateState" {
            val mandateStateMock = mockk<MandateState>()
            every { mandateStateMapper.map(from = any()) } returns mandateStateMock

            val sut = createSut()
            val account = sut.map(from = mockk(relaxed = true))

            verify(exactly = 1) { mandateStateMapper.map(from = any()) }
            account.mandateState shouldBe mandateStateMock
        }

        "name" {
            val expectedName = "A name"
            val accountDto = mockk<AccountDto>(relaxed = true) {
                every { name } returns expectedName
            }

            val sut = createSut()

            sut.map(from = accountDto).name shouldBe expectedName
        }
    }
})
