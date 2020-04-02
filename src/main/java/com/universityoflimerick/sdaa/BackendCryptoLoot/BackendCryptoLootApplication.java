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
			System.out.print("i have intercepted your request: " + context.toString());
//				context.setPort (8080);
//				System.out.println("onPreMarshalRequest() " + context.getPort());
		};

		rd.registerRequestInterceptor(myInterceptor);
	}
}
