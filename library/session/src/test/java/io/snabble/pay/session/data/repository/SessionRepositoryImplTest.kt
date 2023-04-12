package io.snabble.pay.session.data.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.session.data.dto.AccountIdDto
import io.snabble.pay.session.data.dto.SessionDto
import io.snabble.pay.session.data.dto.TokenDto
import io.snabble.pay.session.data.mapper.SessionMapper
import io.snabble.pay.session.data.mapper.TokenMapper
import io.snabble.pay.session.data.service.SessionService
import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken

/** @suppress Dokka */
internal class SessionRepositoryImplTest : FreeSpec({

    val service = mockk<SessionService>(relaxed = true)
    val sessionMapper = mockk<SessionMapper>(relaxed = true)
    val tokenMapper = mockk<TokenMapper>(relaxed = true)

    fun createSut() = SessionRepositoryImpl(
        service = service,
        sessionMapper = sessionMapper,
        tokenMapper = tokenMapper
    )

    beforeEach {
        clearAllMocks()
    }

    "::createSession" - {

        "calls the service's method w/ the given accountId" {
            val accountId = "a1"
            val accountIdSlot = slot<AccountIdDto>()
            coEvery { service.createSession(accountId = capture(accountIdSlot)) } returns Success(
                data = mockk(),
                response = mockk()
            )

            val sut = createSut()
            sut.createSession(accountId = accountId)

            accountIdSlot.captured.id shouldBe accountId
        }

        "maps the result from the service to the domain model" {
            val sessionDtoMock: SessionDto = mockk()
            val accountIdSlot = slot<AccountIdDto>()
            coEvery { service.createSession(accountId = capture(accountIdSlot)) } returns Success(
                data = sessionDtoMock,
                response = mockk()
            )

            val sut = createSut()
            sut.createSession(accountId = "a1")

            coVerify { sessionMapper.map(from = sessionDtoMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val sessionMock: Session = mockk()
                coEvery { sessionMapper.map(from = any()) } returns sessionMock
                coEvery {
                    service.createSession(accountId = any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.createSession(accountId = "a1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe sessionMock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.createSession(accountId = any())
                } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.createSession(accountId = "a1")

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::deleteSession" - {

        "calls the service's method w/ the given id" {
            val sessionId = "s1"

            val sut = createSut()
            sut.deleteSession(id = sessionId)

            coVerify { service.deleteSession(sessionId = sessionId) }
        }

        "maps the result from the service to the domain model" {
            val sessionDtoMock: SessionDto = mockk()
            coEvery { service.deleteSession(sessionId = any()) } returns Success(
                data = sessionDtoMock,
                response = mockk()
            )

            val sut = createSut()
            sut.deleteSession(id = "s1")

            coVerify { sessionMapper.map(from = sessionDtoMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val sessionMock: Session = mockk()
                coEvery { sessionMapper.map(from = any()) } returns sessionMock
                coEvery {
                    service.deleteSession(sessionId = any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.deleteSession(id = "s1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe sessionMock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.deleteSession(sessionId = any())
                } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.deleteSession(id = "s1")

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::getSession" - {

        "calls the service's method w/ the given id" {
            val sessionId = "s1"

            val sut = createSut()
            sut.getSession(id = sessionId)

            coVerify { service.getSession(sessionId = sessionId) }
        }

        "maps the result from the service to the domain model" {
            val sessionDtoMock: SessionDto = mockk()
            coEvery { service.getSession(sessionId = any()) } returns Success(
                data = sessionDtoMock,
                response = mockk()
            )

            val sut = createSut()
            sut.getSession(id = "s1")

            coVerify { sessionMapper.map(from = sessionDtoMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val sessionMock: Session = mockk()
                coEvery { sessionMapper.map(from = any()) } returns sessionMock
                coEvery {
                    service.getSession(sessionId = any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.getSession(id = "s1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe sessionMock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.getSession(sessionId = any())
                } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.getSession(id = "s1")

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::getSessions" - {

        "calls the service's method" {
            val sut = createSut()
            sut.getSessions()

            coVerify { service.getSessions() }
        }

        "maps the result from the service to the domain model" {
            val sessionDtoListMock: List<SessionDto> = listOf(mockk(), mockk())
            coEvery { service.getSessions() } returns Success(
                data = sessionDtoListMock,
                response = mockk()
            )

            val sut = createSut()
            sut.getSessions()

            sessionDtoListMock.forEach { sessionDto ->
                coVerify { sessionMapper.map(from = sessionDto) }
            }
        }

        "returns a Result" - {

            "with the mapped object" {
                val sessionDtoListMock: List<SessionDto> = listOf(mockk(), mockk())
                val sessionListMock: List<Session> = listOf(mockk(), mockk())
                sessionDtoListMock.forEachIndexed { index, sessionDto ->
                    coEvery { sessionMapper.map(from = sessionDto) } returns sessionListMock[index]
                }
                coEvery {
                    service.getSessions()
                } returns Success(
                    data = sessionDtoListMock,
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.getSessions()

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldContainExactly sessionListMock
            }

            "that's failure if the request failed" {
                coEvery { service.getSessions() } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.getSessions()

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::updateToken" - {

        "calls the service's method w/ the given id" {
            val sessionId = "s1"

            val sut = createSut()
            sut.updateToken(sessionId = sessionId)

            coVerify { service.updateToken(sessionId = sessionId) }
        }

        "maps the result from the service to the domain model" {
            val tokenDto: TokenDto = mockk()
            coEvery { service.updateToken(sessionId = any()) } returns Success(
                data = tokenDto,
                response = mockk()
            )

            val sut = createSut()
            sut.updateToken(sessionId = "s1")

            coVerify { tokenMapper.map(from = tokenDto) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val tokenMock: SessionToken = mockk()
                coEvery { tokenMapper.map(from = any()) } returns tokenMock
                coEvery {
                    service.updateToken(sessionId = any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.updateToken(sessionId = "s1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe tokenMock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.updateToken(sessionId = any())
                } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.updateToken(sessionId = "s1")

                result.isSuccess.shouldBeFalse()
            }
        }
    }
})
