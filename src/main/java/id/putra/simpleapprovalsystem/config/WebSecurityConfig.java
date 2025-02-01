package id.putra.simpleapprovalsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.requestMatchers("/static/**").permitAll()
                .requestMatchers("/item/approve").hasAnyRole("SUPERVISOR", "MANAGER")
                .anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        http.exceptionHandling(Customizer.withDefaults());
        return http.build();
    }

    //    Set predefined user here
    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder().username("user").password(passwordEncoder.encode("user123")).roles("USER").build();
        UserDetails supervisor = User.builder().username("admin").password(passwordEncoder.encode("admin123")).roles("SUPERVISOR").build();
        UserDetails manager = User.builder().username("manager").password(passwordEncoder.encode("manager123")).roles("MANAGER").build();

        return new InMemoryUserDetailsManager(user, supervisor, manager);
    }
}
