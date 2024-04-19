package sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

import static org.springframework.security.config.Customizer.withDefaults;

//??: 3. add security configuration
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
                .oauth2Login(e -> configureOAuth2Login(httpSecurity, e)) // adds SSO form
                .logout(logout -> logout.logoutSuccessUrl("/")) // redirect to start page after logout
                //??: 7. add (XFF-) Filter
                .addFilterAfter(new Filter(), SecurityContextHolderFilter.class) // filter before auth/logout
                .build();
        //??: continue video at 3200
    }

    //??: manually curling the request validated that the firewall isn't an issue anymore...
    // The request to "https://www.googleapis.com/oauth2/v4/token" does not reach the proxy
    // So we probably have a configuration issue here - further investigation needed:

    private void configureOAuth2Login(HttpSecurity httpSecurity,
                                      OAuth2LoginConfigurer<HttpSecurity> e) {
        try {
            e.configure(httpSecurity); //??: can we do something here?!
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //??: debug breakpoint:
    // org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient.getResponse

    //??: trace log:
    // o.s.s.authentication.ProviderManager     : Authenticating request with
    // OidcAuthorizationCodeAuthenticationProvider (2/3)

    // Caused by: org.springframework.web.client.ResourceAccessException: I/O error on POST request for "https://www.googleapis.com/oauth2/v4/token": Connection timed out: connect
    //	at org.springframework.web.client.RestTemplate.createResourceAccessException(RestTemplate.java:915) ~[spring-web-6.1.5.jar:6.1.5]
    //	at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:895) ~[spring-web-6.1.5.jar:6.1.5]
    //	at org.springframework.web.client.RestTemplate.exchange(RestTemplate.java:730) ~[spring-web-6.1.5.jar:6.1.5]
    //	at org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient.getResponse(DefaultAuthorizationCodeTokenResponseClient.java:92) ~[spring-security-oauth2-client-6.2.3.jar:6.2.3]
    //	... 76 common frames omitted

    //??: nope... there are a few github issues with similar problems/solutions
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("<proxy_host>", <proxy_port>));
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setProxy(proxy);
//
//        return NimbusJwtDecoder
//                .withJwkSetUri("https://www.googleapis.com/oauth2/v4")
//                .restOperations(new RestTemplate(requestFactory)).build();
//    }
}
