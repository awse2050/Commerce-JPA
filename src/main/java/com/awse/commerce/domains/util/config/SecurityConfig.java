package com.awse.commerce.domains.util.config;

import com.awse.commerce.domains.member.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final OAuth2Service oAuth2Service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/mypage/**", "/api/**", "/mylike/**", "/mycart/**", "/checkout", "/order/**")
                .authenticated();

        http.authorizeRequests().antMatchers("/", "/index", "/item/**").permitAll();

        http.formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password");
        http.logout().logoutSuccessUrl("/");

        http.oauth2Login().loginPage("/login").userInfoEndpoint().userService(oAuth2Service);
    }

    // PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
