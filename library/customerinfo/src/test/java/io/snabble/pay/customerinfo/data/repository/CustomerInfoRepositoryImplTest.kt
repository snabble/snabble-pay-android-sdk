package io.snabble.pay.customerinfo.data.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.customerinfo.data.dto.CustomerInfoDto
import io.snabble.pay.customerinfo.data.mapper.CustomerInfoMapper
import io.snabble.pay.customerinfo.data.service.CustomerInfoService
import io.snabble.pay.customerinfo.domain.model.CustomerInfo

internal class CustomerInfoRepositoryImplTest : FreeSpec({

    val service = mockk<CustomerInfoService>(relaxed = true)
    val mapper = mockk<CustomerInfoMapper>(relaxed = true)

    fun createSut() = CustomerInfoRepositoryImpl(
        service = service,
        mapper = mapper
    )

    beforeEach {
        clearAllMocks()
    }

    "::createCustomerInfo" - {

        "calls the service's method w/ the given parameters" {
            val expected = CustomerInfoDto(id = "c1", loyaltyId = "l1")

            val sut = createSut()
            sut.createCustomerInfo(id = expected.id, loyaltyId = expected.loyaltyId)

            coVerify { service.createCustomerInfo(expected) }
        }

        "maps the result from the service to the domain model" {
            val dtoMock: CustomerInfoDto = mockk()
            coEvery {
                service.createCustomerInfo(customerInfo = any())
            } returns Success(
                data = dtoMock,
                response = mockk()
            )

            val sut = createSut()
            sut.createCustomerInfo(id = null, loyaltyId = null)

            coVerify { mapper.map(from = dtoMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val mock: CustomerInfo = mockk()
                coEvery { mapper.map(from = any()) } returns mock
                coEvery {
                    service.createCustomerInfo(customerInfo = any())
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.createCustomerInfo(id = null, loyaltyId = null)

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe mock
            }

            "that's failure if the request failed" {
                coEvery {
                    service.createCustomerInfo(customerInfo = any())
                } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.createCustomerInfo(id = null, loyaltyId = null)

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::getCustomerInfo" - {

        "calls the service's method" {
            val sut = createSut()
            sut.getCustomerInfo()

            coVerify { service.getCustomerInfo() }
        }

        "maps the result from the service to the domain model" {
            val dtoMock: CustomerInfoDto = mockk()
            coEvery {
                service.getCustomerInfo()
            } returns Success(
                data = dtoMock,
                response = mockk()
            )

            val sut = createSut()
            sut.getCustomerInfo()

            coVerify { mapper.map(from = dtoMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val mock: CustomerInfo = mockk()
                coEvery { mapper.map(from = any()) } returns mock
                coEvery {
                    service.getCustomerInfo()
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.getCustomerInfo()

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe mock
            }

            "that's failure if the request failed" {
                coEvery { service.getCustomerInfo() } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.getCustomerInfo()

                result.isSuccess.shouldBeFalse()
            }
        }
    }

    "::removeCustomerInfo" - {

        "calls the service's method" {
            val sut = createSut()
            sut.removeCustomerInfo()

            coVerify { service.removeCustomerInfo() }
        }

        "maps the result from the service to the domain model" {
            val dtoMock: CustomerInfoDto = mockk()
            coEvery {
                service.removeCustomerInfo()
            } returns Success(
                data = dtoMock,
                response = mockk()
            )

            val sut = createSut()
            sut.removeCustomerInfo()

            coVerify { mapper.map(from = dtoMock) }
        }

        "returns a Result" - {

            "with the mapped object" {
                val mock: CustomerInfo = mockk()
                coEvery { mapper.map(from = any()) } returns mock
                coEvery {
                    service.removeCustomerInfo()
                } returns Success(
                    data = mockk(),
                    response = mockk()
                )

                val sut = createSut()
                val result = sut.removeCustomerInfo()

                result.isSuccess.shouldBeTrue()
                result.getOrNull() shouldBe mock
            }

            "that's failure if the request failed" {
                coEvery { service.removeCustomerInfo() } returns mockk<ApiError>(relaxed = true)

                val sut = createSut()
                val result = sut.removeCustomerInfo()

                result.isSuccess.shouldBeFalse()
            }
        }
    }
})
