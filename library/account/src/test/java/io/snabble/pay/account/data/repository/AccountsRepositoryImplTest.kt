package io.snabble.pay.account.data.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.snabble.pay.account.data.dto.AccountCheckDto
import io.snabble.pay.account.data.mapper.AccountCheckMapper
import io.snabble.pay.account.data.service.AccountService
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.internal.account.data.dto.AccountDto
import io.snabble.pay.internal.account.data.mapper.AccountMapper
import io.snabble.pay.shared.account.domain.model.Account

internal class AccountsRepositoryImplTest : FreeSpec({

    val service = mockk<AccountService>(relaxed = true)
    val accountCheckMapper = mockk<AccountCheckMapper>(relaxed = true)
    val accountMapper = mockk<AccountMapper>(relaxed = true)

    fun createSut() = AccountsRepositoryImpl(
        service = service,
        accountCheckMapper = accountCheckMapper,
        accountMapper = accountMapper
    )

    "::getAccount" - {

        "call the service's method w/ the given account id" {
            val sut = createSut()
            sut.getAccount(id = "a1")

            coVerify { service.getAccount(id = "a1") }
        }

        "maps the result from the service to the domain model" {
            val accountDtoMock: AccountDto = mockk()
            coEvery {
                service.getAccount(id = any())
            } returns Success(
                data = accountDtoMock,
                response = mockk()
            )

            val sut = createSut()
            sut.getAccount(id = "a1")

            coVerify { accountMapper.map(from = accountDtoMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val accountMock: Account = mockk()
                coEvery { accountMapper.map(from = any()) } returns accountMock
                coEvery {
                    service.getAccount(id = any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.getAccount(id = "a1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe accountMock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.getAccount(id = any())
                } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.getAccount(id = "a1")

                result.isSuccess.shouldBeFalse()
            }
        }

        "::getAccountCheck" - {

            "call the service's method w/ the given parameters" {
                val sut = createSut()
                sut.getAccountCheck(
                    appUri = "snabble-pay://sample",
                    city = "Bonn",
                    twoLetterIsoCountryCode = "DE"
                )

                coVerify {
                    service.getAccountCheck(
                        appUri = "snabble-pay://sample",
                        city = "Bonn",
                        twoLetterIsoCountryCode = "DE"
                    )
                }
            }

            "maps the result from the service to the domain model" {
                val accountCheckDtoMock: AccountCheckDto = mockk()
                coEvery {
                    service.getAccountCheck(
                        appUri = any(),
                        city = any(),
                        twoLetterIsoCountryCode = any()
                    )
                } returns Success(
                    data = accountCheckDtoMock,
                    response = mockk()
                )

                val sut = createSut()
                sut.getAccountCheck(
                    appUri = "snabble-pay://sample",
                    city = "Bonn",
                    twoLetterIsoCountryCode = "DE"
                )

                coVerify { accountCheckMapper.map(from = accountCheckDtoMock) }
            }

            "returns a Result" - {

                "with the mapped object" {
                    val accountCheckMock: AccountCheck = mockk()
                    coEvery { accountCheckMapper.map(from = any()) } returns accountCheckMock
                    coEvery {
                        service.getAccountCheck(
                            appUri = any(),
                            city = any(),
                            twoLetterIsoCountryCode = any()
                        )
                    } returns Success(
                        data = mockk(),
                        response = mockk()
                    )

                    val sut = createSut()
                    val result = sut.getAccountCheck(
                        appUri = "snabble-pay://sample",
                        city = "Bonn",
                        twoLetterIsoCountryCode = "DE"
                    )

                    result.isSuccess.shouldBeTrue()
                    result.getOrNull() shouldBe accountCheckMock
                }

                "that's failure if the request failed" {
                    coEvery {
                        service.getAccountCheck(
                            appUri = any(),
                            city = any(),
                            twoLetterIsoCountryCode = any()
                        )
                    } returns mockk<ApiError>(relaxed = true)

                    val sut = createSut()
                    val result = sut.getAccountCheck(
                        appUri = "snabble-pay://sample",
                        city = "Bonn",
                        twoLetterIsoCountryCode = "DE"
                    )

                    result.isSuccess.shouldBeFalse()
                }
            }
        }

        "::getAccounts" - {

            "call the service's method" {
                val sut = createSut()
                sut.getAccounts()

                coVerify { service.getAccounts() }
            }

            "maps the result from the service to the domain model" {
                val accountDtoMock: List<AccountDto> = listOf(mockk(), mockk())
                coEvery {
                    service.getAccounts()
                } returns Success(
                    data = accountDtoMock,
                    response = mockk()
                )

                val sut = createSut()
                sut.getAccounts()

                accountDtoMock.forEach { accountDto ->
                    coVerify { accountMapper.map(from = accountDto) }
                }
            }

            "returns a Result" - {

                "with the mapped object" {
                    val accountDtoListMock: List<AccountDto> = listOf(mockk(), mockk())
                    val accountListMock: List<Account> = listOf(mockk(), mockk())
                    accountDtoListMock.forEachIndexed { index, accountDto ->
                        coEvery { accountMapper.map(from = accountDto) } returns accountListMock[index]
                    }
                    coEvery {
                        service.getAccounts()
                    } returns Success(
                        data = accountDtoListMock,
                        response = mockk()
                    )

                    val sut = createSut()
                    val result = sut.getAccounts()

                    result.isSuccess.shouldBeTrue()
                    result.getOrNull() shouldContainExactly accountListMock
                }

                "that's failure if the request failed" {
                    coEvery { service.getAccounts() } returns mockk<ApiError>(relaxed = true)

                    val sut = createSut()
                    val result = sut.getAccounts()

                    result.isSuccess.shouldBeFalse()
                }
            }
        }

        "::removeAccount" - {

            "call the service's method w/ the given account id" {
                val sut = createSut()
                sut.removeAccount(id = "a1")

                coVerify { service.removeAccount(id = "a1") }
            }

            "maps the result from the service to the domain model" {
                val accountDtoMock: AccountDto = mockk()
                coEvery {
                    service.removeAccount(id = any())
                } returns Success(
                    data = accountDtoMock,
                    response = mockk()
                )

                val sut = createSut()
                sut.removeAccount(id = "a1")

                coVerify { accountMapper.map(from = accountDtoMock) }
            }

            "returns a Result" - {

                "with the mapped object" {
                    val accountMock: Account = mockk()
                    coEvery { accountMapper.map(from = any()) } returns accountMock
                    coEvery {
                        service.removeAccount(id = any())
                    } returns Success(
                        data = mockk(),
                        response = mockk()
                    )

                    val sut = createSut()
                    val result = sut.removeAccount(id = "a1")

                    result.isSuccess.shouldBeTrue()
                    result.getOrNull() shouldBe accountMock
                }

                "that's failure if the request failed" {
                    coEvery {
                        service.removeAccount(id = any())
                    } returns mockk<ApiError>(relaxed = true)

                    val sut = createSut()
                    val result = sut.removeAccount(id = "a1")

                    result.isSuccess.shouldBeFalse()
                }
            }
        }
    }
})
