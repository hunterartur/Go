package com.simbirsoft.gateway.filter;

import com.simbirsoft.gateway.exception.UnauthorizedException;
import com.simbirsoft.gateway.service.JwtTokenProcessor;
import com.simbirsoft.gocommonlib.constant.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ILLEGAL_HEADER_EXCEPTION = "Не верное значение заголовка: " + AUTHORIZATION_HEADER;
    private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "Не авторизован!";
    private static final String BEARER = "Bearer ";
    private static final String TOKEN_EXPIRED = "Токен протух! Обновите токен!";
    private static final String TOKEN_NOT_FOUND = "Токен не найден!";

    private final JwtTokenProcessor jwtTokenProcessor;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            var token = getTokenFromRequest(exchange.getRequest());
            if (!jwtTokenProcessor.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                exchange.getResponse().getHeaders().add(CommonConstant.X_ERROR_MESSAGE, TOKEN_EXPIRED);
                return exchange.getResponse().setComplete();
            }
            var modifiedExchange = exchange.mutate()
                    .request(builder -> builder
                            .header(CommonConstant.X_JWT_TOKEN, token)
                            .header(CommonConstant.X_USER_ID, jwtTokenProcessor.getIdUserFromToken(token)))
                    .build();
            return chain.filter(modifiedExchange);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        var authHeader = request.getHeaders().get(AUTHORIZATION_HEADER);
        if (authHeader == null) throw new UnauthorizedException(UNAUTHORIZED_EXCEPTION_MESSAGE);
        var tokenWithBearer = authHeader.get(0);
        if (!tokenWithBearer.startsWith(BEARER)) throw new IllegalArgumentException(ILLEGAL_HEADER_EXCEPTION);
        if (tokenWithBearer.length() <= BEARER.length()) throw new IllegalArgumentException(TOKEN_NOT_FOUND);
        return tokenWithBearer.substring(BEARER.length());
    }
}
