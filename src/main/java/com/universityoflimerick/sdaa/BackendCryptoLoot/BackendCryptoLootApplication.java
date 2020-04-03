package com.universityoflimerick.sdaa.BackendCryptoLoot;

import com.universityoflimerick.sdaa.BackendCryptoLoot.Config.RequestDispatcher;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Config.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendCryptoLootApplication {
	public static RequestDispatcher rd = RequestDispatcher.getRequestDispatcher();

	public static void main(String[] args) {
		SpringApplication.run(BackendCryptoLootApplication.class, args);

		RequestInterceptor myInterceptor = context -> {
			System.out.print("intercepted your request: " + context.toString());
			//do something with context object
		};
		rd.registerRequestInterceptor(myInterceptor);
	}
}
