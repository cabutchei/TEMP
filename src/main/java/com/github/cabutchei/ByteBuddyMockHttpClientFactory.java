package com.github.cabutchei;

import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

import jakarta.enterprise.context.ApplicationScoped;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

@ApplicationScoped
public class ByteBuddyMockHttpClientFactory {

    public HttpClient createJsonMockClient(String jsonPayload) {
        try {
            Class<? extends BaseHttpClientAdapter> generatedType = new ByteBuddy()
                    .subclass(BaseHttpClientAdapter.class, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
                    .method(ElementMatchers.named("send").and(ElementMatchers.takesArguments(2)))
                    .intercept(MethodDelegation.to(new SendInterceptor()))
                    .make()
                    .load(BaseHttpClientAdapter.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded();

            return generatedType.getDeclaredConstructor(String.class).newInstance(jsonPayload);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to create Byte Buddy mock HttpClient", exception);
        }
    }

    public static class SendInterceptor {

        @SuppressWarnings("unchecked")
        public <T> HttpResponse<T> send(
                @net.bytebuddy.implementation.bind.annotation.This BaseHttpClientAdapter client,
                @net.bytebuddy.implementation.bind.annotation.Argument(0) HttpRequest request)
                throws IOException {
            return (HttpResponse<T>) new MockHttpResponse(request, client.getPayload());
        }
    }

    public static class BaseHttpClientAdapter extends HttpClient {

        private final String payload;

        public BaseHttpClientAdapter(String payload) {
            this.payload = payload;
        }

        public String getPayload() {
            return payload;
        }

        @Override
        public Optional<CookieHandler> cookieHandler() {
            return Optional.empty();
        }

        @Override
        public Optional<Duration> connectTimeout() {
            return Optional.empty();
        }

        @Override
        public Redirect followRedirects() {
            return Redirect.NEVER;
        }

        @Override
        public Optional<ProxySelector> proxy() {
            return Optional.empty();
        }

        @Override
        public SSLContext sslContext() {
            throw new UnsupportedOperationException("Not required for the mock client");
        }

        @Override
        public SSLParameters sslParameters() {
            return new SSLParameters();
        }

        @Override
        public Optional<Authenticator> authenticator() {
            return Optional.empty();
        }

        @Override
        public Version version() {
            return Version.HTTP_1_1;
        }

        @Override
        public Optional<Executor> executor() {
            return Optional.empty();
        }

        @Override
        public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
                throws IOException, InterruptedException {
            throw new UnsupportedOperationException("Byte Buddy should intercept send");
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                HttpRequest request,
                HttpResponse.BodyHandler<T> responseBodyHandler) {
            return CompletableFuture.failedFuture(
                    new UnsupportedOperationException("Async send is not used in this demo"));
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                HttpRequest request,
                HttpResponse.BodyHandler<T> responseBodyHandler,
                HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
            return CompletableFuture.failedFuture(
                    new UnsupportedOperationException("Async send is not used in this demo"));
        }
    }

    public static class MockHttpResponse implements HttpResponse<String> {

        private final HttpRequest request;
        private final String payload;

        public MockHttpResponse(HttpRequest request, String payload) {
            this.request = request;
            this.payload = payload;
        }

        @Override
        public int statusCode() {
            return 200;
        }

        @Override
        public HttpRequest request() {
            return request;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return HttpHeaders.of(Map.of("Content-Type", List.of("application/json")), (name, value) -> true);
        }

        @Override
        public String body() {
            return payload;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return request.uri();
        }

        @Override
        public Version version() {
            return Version.HTTP_1_1;
        }
    }
}
