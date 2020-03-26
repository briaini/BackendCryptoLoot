package com.universityoflimerick.sdaa.BackendCryptoLoot;

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

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

//        String token = request.getHeader(HEADER_STRING);
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJrVTFOalE0TTBJNE5ERkVSakpCUkVReU9UQTBSRVl4TXpKQ00wRTFORFExUWpaQ1JrUTNOQSJ9.eyJpc3MiOiJodHRwczovL2Rldi00ZDN6OGtmeC5ldS5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NWU3YjAxMjIxYjE1NDAwYzkyOWQwZDBhIiwiYXVkIjoiaHR0cHM6Ly9jcnlwdG9sb290L2FwaSIsImlhdCI6MTU4NTE5NTU2NiwiZXhwIjoxNTg1MjgxOTY2LCJhenAiOiJ2bHg0SnI0Q3hXcUlhTUduNXdmMGxPVHgyOXVrYng4RSIsInNjb3BlIjoicmVhZDptZXNzYWdlcyJ9.pHTnctNxnIKuFxlhCpv_u8qGryl4nx0B-c1sSvnWNVeu31wKKwmp2qedFBKgr5jqnVygoNNfUriFL9SMTysUiai4_wJ1XMGYM1r4HwqTSq5s3cs8VNernr9IshDDmk_xuXSHhdwQNyRUJio9Rg3flMzRuxRvLyYgcAU_FLXcbsOOTXkqweCzk1cbv63IPWtoyIoZqd6lFYBBgrjRAZeZJLcevxH4AVsfAwk5u3tT11p3zdI-9HUysw09Ye8EK9k_3GW_yCCc7tByRYsZxu60JYzp0JFnbInxwRBsZtevLeatMBNlVuS0msJEkiVFG0SRX-ll04_snhRAXmMh3jVlzA";



        JwkProvider provider = new UrlJwkProvider("https://dev-4d3z8kfx.eu.auth0.com/");
        try {
            DecodedJWT jwt = JWT.decode(token);
            Jwk jwk = provider.get(jwt.getKeyId());

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://dev-4d3z8kfx.eu.auth0.com/")
                    .build();

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