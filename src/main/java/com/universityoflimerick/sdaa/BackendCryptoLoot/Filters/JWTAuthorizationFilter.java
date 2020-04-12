package com.universityoflimerick.sdaa.BackendCryptoLoot.Filters;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    /**
     * doFilterInternal takes in http request. Checks if contains JWT in header
     * if no JWT, passes onwards to chain
     * else calls getAuthentication and stores result in UsernamePasswordAuthenticationToken
     * UsernamePasswordAuthenticationToken object stored in SecurityContextHolder using setAuthentication method
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * UsernamePasswordAuthenticationToken validates JWT
     * takes JWT from header and removes "Bearer "
     * Gets JWK from OAuth provider
     * uses public key in JWK to check signature and ensure JWT unaltered
     * if signature valid, ensures JWT was issued by our OAuth provider
     * @param request http request
     * @return UsernamePasswordAuthenticationToken if valid JWT
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING).substring(7);

        JwkProvider provider = new UrlJwkProvider("https://dev-4d3z8kfx.eu.auth0.com/");
        try {
            DecodedJWT jwt = JWT.decode(token);
            Jwk jwk = provider.get(jwt.getKeyId());

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://dev-4d3z8kfx.eu.auth0.com/")
                    .build();
//            System.out.println("---------------------");
//            System.out.println(jwt.getClaim("sub").asString());
            verifier.verify(token);

            return new UsernamePasswordAuthenticationToken(jwt.getClaim("sub").asString(), null, new ArrayList<>());

        } catch (JWTVerificationException e){
            System.out.println("caught JWTVerificationException");
            //Invalid signature/claims
            e.printStackTrace();
        } catch (JwkException e) {
            // invalid JWT token
            e.printStackTrace();
        }
        return null;
    }
}