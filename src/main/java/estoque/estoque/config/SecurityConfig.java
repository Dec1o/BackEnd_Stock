package estoque.estoque.config;

import estoque.estoque.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) CORS padrão
                .cors(Customizer.withDefaults())
                // 2) CSRF desabilitado (stateless JWT)
                .csrf(csrf -> csrf.disable())
                // 3) Sessão stateless
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 4) Desabilita HTTP Basic (não vamos usar)
                .httpBasic(basic -> basic.disable())
                // 5) Registra o provider de autenticação (DAO + BCrypt)
                .authenticationProvider(authenticationProvider())
                // 6) Se não autenticado, retorna 401 (sem redirect pra /error)
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                // 7) Regras de autorização
                .authorizeHttpRequests(auth -> auth
                        // libera apenas estes POSTs
                        .requestMatchers(
                                HttpMethod.POST,
                                "/auth/login",
                                "/auth/refresh",
                                "/register"
                        ).permitAll()
                        // libera endpoint de erro (interno do Spring)
                        .requestMatchers("/error").permitAll()
                        // qualquer outra rota exige JWT válido
                        .anyRequest().authenticated()
                )
                // 8) Filtro JWT antes do processamento de UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // seu UserDetailsServiceImpl
        provider.setPasswordEncoder(passwordEncoder());     // BCryptPasswordEncoder
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
