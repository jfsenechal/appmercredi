package be.marche.mercredi.sync

import be.marche.mercredi.user.UserViewModel
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RequestInterceptor(private val userViewModel: UserViewModel) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        /**
        chain.request() returns original request that you can work with(modify, rewrite)
         */
        val originalRequest: Request = chain.request()

        val user = userViewModel.getOneUser2()

        val token = user?.token

        if (token == null) {
            return chain.proceed(chain.request())
        }

        val headers: Headers = Headers.Builder()
            .add("X-AUTH-TOKEN", token)
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            // .addHeader("Authorization", "auth-value") //Adds a header with name and value.
            // .addHeader("User-Agent", "you-app-name")
            // .cacheControl(CacheControl.FORCE_CACHE) // Sets this request's Cache-Control header, replacing any cache control headers already present.
            .headers(headers) //Removes all headers on this builder and adds headers.
            //    .method(originalRequest.method(), null) // Adds request method and request body
            //   .removeHeader("Authorization") // Removes all the headers with this name
            .build();
        /*
        chain.proceed(request) is the call which will initiate the HTTP work. This call invokes the
        request and returns the response as per the request.
        */
        val response: Response = chain.proceed(newRequest);
        return response
    }
}