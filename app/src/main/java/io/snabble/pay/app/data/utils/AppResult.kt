package io.snabble.pay.app.data.utils

import io.snabble.pay.core.PayError

sealed interface AppResult<T>

data class AppSuccess<T>(val value: T) : AppResult<T>

data class AppError<T>(val value: PayError?) : AppResult<T>
