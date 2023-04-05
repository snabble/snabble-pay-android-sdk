package io.snabble.pay.session.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.session.data.dto.SessionDto
import io.snabble.pay.session.domain.model.SessionToken
import io.snabble.pay.session.domain.model.Transaction
import io.snabble.pay.shared.account.data.mapper.AccountMapper
import io.snabble.pay.shared.account.domain.model.Account
import java.time.ZonedDateTime

internal class SessionMapperTest : FreeSpec({

    val tokenMapper = mockk<TokenMapper>(relaxed = true)
    val transactionMapper = mockk<TransactionMapper>(relaxed = true)
    val accountMapper = mockk<AccountMapper>(relaxed = true)

    fun createSut(): SessionMapper = SessionMapper(
        accountMapper = accountMapper,
        tokenMapper = tokenMapper,
        transactionMapper = transactionMapper
    )

    beforeEach {
        clearAllMocks()
    }

    "MandateMapper should correctly map the" - {

        "createdAt" {
            val now = ZonedDateTime.now()
            val sessionDto = mockk<SessionDto>(relaxed = true) {
                every { createdAt } returns now
            }

            val sut = createSut()

            sut.map(sessionDto).createdAt shouldBe now
        }

        "id" {
            val sessionId = "s1"
            val sessionDto = mockk<SessionDto>(relaxed = true) {
                every { id } returns sessionId
            }

            val sut = createSut()

            sut.map(sessionDto).id shouldBe sessionId
        }

        "account" {
            val account = mockk<Account>(relaxed = true)
            every { accountMapper.map(any()) } returns account

            val sut = createSut()

            sut.map(mockk(relaxed = true)).account shouldBe account
        }

        "token" {
            val sessionToken = mockk<SessionToken>()
            every { tokenMapper.map(any()) } returns sessionToken

            val sut = createSut()

            sut.map(mockk(relaxed = true)).token shouldBe sessionToken
        }

        "transaction" {
            val transaction = mockk<Transaction>()
            every { transactionMapper.map(any()) } returns transaction

            val sut = createSut()

            sut.map(mockk(relaxed = true)).transaction shouldBe transaction
        }
    }
})
