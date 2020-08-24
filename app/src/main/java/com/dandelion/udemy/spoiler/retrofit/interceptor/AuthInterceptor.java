package com.dandelion.udemy.spoiler.retrofit.interceptor;
// [Imports]
import com.dandelion.udemy.spoiler.common.APICommon;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

// [Authentication Interceptor]: intercepts request in order to attach authentication data
public class AuthInterceptor implements Interceptor {
    // (interception)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();  // Catches original request
        HttpUrl url = request.url();        // Gets request destination url
        HttpUrl authUrl = url.newBuilder()  // Remakes url attaching api key parameter data
                .addQueryParameter(APICommon.API_AUTH_KEY_PARAM_LABEL, APICommon.API_AUTH_KEY)
                .build();
        // Finally get authenticated url request
        Request authRequest = request.newBuilder()
                .url(authUrl)
                .build();
        // Returns chain request with authentication attached
        return chain.proceed(authRequest);
    }
}
