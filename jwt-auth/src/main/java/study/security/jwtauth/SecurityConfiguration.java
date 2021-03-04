package study.security.jwtauth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // swagger 나 restDocs 같이 ignore 할 paths 를 작성
    @Override
    public void configure(final WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring()
           .antMatchers("/swagger/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/*/signin", "/*/signup").permitAll()
            .antMatchers(GET, "helloworld/**").permitAll()
            .anyRequest().hasRole("USER")
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
