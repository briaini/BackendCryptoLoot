package com.universityoflimerick.sdaa.BackendCryptoLoot;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Config.RequestDispatcher;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Config.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

@SpringBootApplication
public class BackendCryptoLootApplication {
    public static RequestDispatcher rd = RequestDispatcher.getRequestDispatcher();

    public static void main(String[] args) {
        SpringApplication.run(BackendCryptoLootApplication.class, args);

        RequestInterceptor myInterceptor = context -> {
            String role = "";
            JwkProvider provider = new UrlJwkProvider("https://dev-4d3z8kfx.eu.auth0.com/");
            try {
                DecodedJWT jwt = JWT.decode(context.getHeaders().replace("Bearer ", ""));
                Jwk jwk = provider.get(jwt.getKeyId());

                Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("https://dev-4d3z8kfx.eu.auth0.com/")
                        .build();
                verifier.verify(context.getHeaders().replace("Bearer ", ""));

                role = jwt.getClaim("scope").asString();
            } catch (JwkException e) {
                e.toString();
            }
            if (!role.contains("admin")) {
                try {
                    File file = new File("log.txt");
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                try {
                    FileWriter myWriter = new FileWriter("log.txt");
                    myWriter.write("Request time: " + context.getRequestTime() +
                            " , server: " + context.getServerInfo() +
                            " , user: " + context.getUser() +
                            " , authenticated: " + context.isAuthenticated() +
                            " , host: " + context.getHost() +
                            " , port: " + context.getPort() +
                            " , serverStartTime: " + context.getStartUpTime() +
                            " , displayName: " + context.getDisplayName()
                    );
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        };
        rd.registerRequestInterceptor(myInterceptor);
    }
}
