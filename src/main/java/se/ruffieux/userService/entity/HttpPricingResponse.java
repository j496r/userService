package se.ruffieux.userService.entity;

import java.net.URI;
import javax.net.ssl.SSLSession;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.net.http.HttpClient.Version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class HttpPricingResponse implements HttpResponse<HttpPricingResponse.PricingResponseBody> {
    @Value("${pricing.response.status: 200}")
    private int status;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class PricingResponseBody {
        int totalCostInCents;
    }

    @Override
    public PricingResponseBody body() {
        PricingResponseBody body = new PricingResponseBody();
        body.setTotalCostInCents(1234);
        return body;
    }

    @Override
    public int statusCode() {
        return status;
    }

    @Override
    public HttpHeaders headers() {
        return null;
    }

    @Override
    public Optional<HttpResponse<PricingResponseBody>> previousResponse() {
        return Optional.empty();
    }

    @Override
    public HttpRequest request() {
        return null;
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return Optional.empty();
    }

    @Override
    public URI uri() {
        return null;
    }

    @Override
    public Version version() {
        return null;
    }

}
