package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

import com.universityoflimerick.sdaa.BackendCryptoLoot.Filters.JWTAuthorizationFilter;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Filters.LoggingFilter;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Filters.MyInterceptorFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new LoggingFilter(), JWTAuthorizationFilter.class)
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .addFilterAfter(new MyInterceptorFilter(), JWTAuthorizationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
