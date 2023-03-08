package io.snabble.pay.mandate.data.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.snabble.pay.api.retrofit.Error
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.retrofit.SuccessNoContent
import io.snabble.pay.mandate.data.dto.MandateDto
import io.snabble.pay.mandate.data.dto.MandateResponseDto
import io.snabble.pay.mandate.data.dto.MandateStateDto
import io.snabble.pay.mandate.data.mapper.MandateMapper
import io.snabble.pay.mandate.data.mapper.MandateResponseMapper
import io.snabble.pay.mandate.data.service.MandateService
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateResponse

internal class MandateRepositoryImplTest : FreeSpec({

    val service: MandateService = mockk(relaxed = true)
    val mandateMapper: MandateMapper = mockk(relaxed = true)
    val mandateResponseMapper: MandateResponseMapper = mockk(relaxed = true)

    fun createSut() = MandateRepositoryImpl(
        service = service,
        mandateMapper = mandateMapper,
        mandateResponseMapper = mandateResponseMapper
    )

    beforeEach {
        clearAllMocks()
    }

    "::createMandate" - {

        "calls the service's method w/ the given account id" {
            val sut = createSut()
            sut.createMandate(accountId = "a1")

            coVerify { service.createMandate(accountId = "a1") }
        }

        "maps the result from the service to the domain model" {
            val dtoMandateMock: MandateDto = mockk()
            coEvery {
                service.createMandate(any())
            } returns Success(
                data = dtoMandateMock,
                response = mockk()
            )

            val sut = createSut()
            sut.createMandate(accountId = "a1")

            coVerify { mandateMapper.map(dtoMandateMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val mandateMock: Mandate = mockk()
                coEvery { mandateMapper.map(any()) } returns mandateMock
                coEvery {
                    service.createMandate(any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.createMandate(accountId = "a1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe mandateMock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.createMandate(any())
                } returns Error(
                    message = null,
                    exception = Exception()
                )

                val sut = createSut()
                val result = sut.createMandate(accountId = "a1")

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::getMandate" - {

        "calls the service's method w/ the given account id" {
            val sut = createSut()
            sut.getMandate(accountId = "a1")

            coVerify { service.getMandate(accountId = "a1") }
        }

        "maps the result from the service to the domain model" {
            val dtoMandateMock: MandateDto = mockk()
            coEvery {
                service.getMandate(any())
            } returns Success(
                data = dtoMandateMock,
                response = mockk()
            )

            val sut = createSut()
            sut.getMandate(accountId = "a1")

            coVerify { mandateMapper.map(dtoMandateMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val mandateMock: Mandate = mockk()
                coEvery { mandateMapper.map(any()) } returns mandateMock
                coEvery {
                    service.getMandate(any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.getMandate(accountId = "a1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe mandateMock
            }

            "with null data if no mandate is available" {
                val mandateMock: Mandate = mockk()
                coEvery { mandateMapper.map(any()) } returns mandateMock
                coEvery {
                    service.getMandate(any())
                } returns SuccessNoContent(response = mockk())

                val sut = createSut()
                val result = sut.getMandate(accountId = "a1")

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe null
            }

            "that's failure if the request failed" {
                coEvery {
                    service.getMandate(any())
                } returns Error(
                    message = null,
                    exception = Exception()
                )

                val sut = createSut()
                val result = sut.createMandate(accountId = "a1")

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::respondToMandate" - {

        "calls the service's method w/ the given parameters" {
            coEvery { mandateResponseMapper.map(any()) } returns MandateStateDto.ACCEPTED

            val sut = createSut()
            sut.respondToMandate(
                accountId = "a1",
                mandateId = "m1",
                response = mockk()
            )

            coVerify {
                service.respondToMandate(
                    accountId = "a1",
                    response = MandateResponseDto(
                        mandateId = "m1",
                        state = MandateStateDto.ACCEPTED
                    )
                )
            }
        }

        "maps the response from the domain model to the service model" {
            val mandateResponse: MandateResponse = mockk()

            val sut = createSut()
            sut.respondToMandate(
                accountId = "a1",
                mandateId = "m1",
                response = mandateResponse
            )

            coVerify { mandateResponseMapper.map(mandateResponse) }
        }

        "passes the mapped response model to the service call" {
            val mandateResponseDto: MandateStateDto = mockk()
            coEvery { mandateResponseMapper.map(any()) } returns mandateResponseDto
            val mandateResponseSlot = slot<MandateResponseDto>()
            coEvery {
                service.respondToMandate(
                    accountId = any(),
                    response = capture(mandateResponseSlot)
                )
            } returns Success(data = mockk(), response = mockk())

            val sut = createSut()
            sut.respondToMandate(
                accountId = "a1",
                mandateId = "m1",
                response = mockk()
            )

            mandateResponseSlot.captured.state shouldBe mandateResponseDto
        }

        "maps the result from the service to the domain model" {
            val dtoMandateMock: MandateDto = mockk()
            coEvery {
                service.respondToMandate(any(), any())
            } returns Success(
                data = dtoMandateMock,
                response = mockk()
            )

            val sut = createSut()
            sut.respondToMandate(
                accountId = "a1",
                mandateId = "m1",
                response = mockk()
            )

            coVerify { mandateMapper.map(dtoMandateMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val mandateMock: Mandate = mockk()
                coEvery { mandateMapper.map(any()) } returns mandateMock
                coEvery {
                    service.respondToMandate(any(), any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.respondToMandate(
                    accountId = "a1",
                    mandateId = "m1",
                    response = mockk()
                )

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe mandateMock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.respondToMandate(any(), any())
                } returns Error(
                    message = null,
                    exception = Exception()
                )

                val sut = createSut()
                val result = sut.respondToMandate(
                    accountId = "a1",
                    mandateId = "m1",
                    response = mockk()
                )

                result.isSuccess.shouldBeFalse()
            }
        }
    }
})
