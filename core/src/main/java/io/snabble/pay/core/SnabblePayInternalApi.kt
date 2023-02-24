package io.snabble.pay.core

@RequiresOptIn(
    message = "Internal SnabblePay API. Do not use it outside SnabblePay.",
    level = RequiresOptIn.Level.ERROR
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY
)
annotation class SnabblePayInternalApi
