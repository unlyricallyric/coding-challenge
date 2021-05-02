package com.shopify.store.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.shopify.store.security.ApplicationUserRole.*;
import static com.shopify.store.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*", "/img/**").permitAll()
                .antMatchers("/api/**").hasAnyRole(SELLER.name(), BUYER.name())
                /*.antMatchers(HttpMethod.DELETE, "/manage/api/**").hasAuthority(SELLER_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/manage/api/**").hasAuthority(SELLER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/manage/api/**").hasAuthority(SELLER_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/manage/api/**").hasAnyRole(SELLER.name(), BUYER.name())*/
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/all_images", true)
                .and()
                .logout().logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails seller = User.builder()
                .username("seller")
                .password(passwordEncoder.encode("password"))
                //.roles(SELLER.name())
                .authorities(SELLER.getGrantedAuthorities())
                .build();

        UserDetails buyer = User.builder()
                .username("buyer")
                .password(passwordEncoder.encode("password"))
                //.roles(BUYER.name())
                .authorities(BUYER.getGrantedAuthorities())
                .build();

        UserDetails newSeller = User.builder()
                .username("user")
                .password(passwordEncoder.encode("test"))
                //.roles(NEW_SELLER.name())
                .authorities(NEW_SELLER.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                seller,
                buyer,
                newSeller
        );
    }
}
