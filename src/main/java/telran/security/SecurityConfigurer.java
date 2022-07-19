package telran.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration

public class SecurityConfigurer  {
	@Value("${app.security.enable:true}")
	boolean securityEnable;
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception  {
		http.httpBasic();
		http.cors().and().csrf().disable();
		if (securityEnable) {
			http.authorizeHttpRequests().antMatchers("/login").permitAll();
			http.authorizeHttpRequests().antMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN");
			http.authorizeHttpRequests().anyRequest().hasRole("ADMIN");
		} else {
			http.authorizeHttpRequests().anyRequest().permitAll();
		}
		return http.build();
	}

}
