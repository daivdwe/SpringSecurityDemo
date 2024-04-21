package sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/").permitAll();
                    request.requestMatchers("/public").permitAll();
                    request.requestMatchers("/error").permitAll();
                    request.requestMatchers("/css/*").permitAll();
                    request.requestMatchers("/favicon.svg").permitAll();
                    request.anyRequest().authenticated(); // default to login needed
                })
                .formLogin(withDefaults()) // adds login form
                .oauth2Login(withDefaults()) // adds SSO form
                .logout(logout -> logout.logoutSuccessUrl("/")) // redirect to start page after logout
                .addFilterAfter(new Filter(), SecurityContextHolderFilter.class) // filter before auth/logout
                .build();
    }
}
