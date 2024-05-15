package com.example.pokeapi_koin.common

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation

class LogInterceptor(
    private val eventTracker: EventTracker
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val invocation = chain
            .request()
            .tag(Invocation::class.java) ?: return chain.proceed(chain.request())

        invocation.method().annotations.forEach { annotation ->
            interceptAnnotation(annotation, request)
        }
        return chain.proceed(request)
    }

    private fun interceptAnnotation(
        annotation: Annotation,
        request: Request
    ) {
        if(annotation is Logger) {
            Log.d("Request",
                "Method: ${request.method()} Body: ${request.body()} Headers: ${request.headers()}"
            )
        } else if(annotation is TrackEvent) {
            val eventObj = annotation as? TrackEvent
            val eventName = eventObj?.event ?: ""
            val attributes = eventObj?.attributes ?: arrayOf()
            eventTracker.trackEvent(eventName, attributes)
        }
    }
}