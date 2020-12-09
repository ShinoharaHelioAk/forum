package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	
	//Método que serve para configurar a parte de autenticação.
	//A parte de controle de acesso, de login, fica nesse método.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	/*public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}*/
	
	
	//Método que serve para fazer configurações de autorização.
	//A parte de URLs, quem pode acessar cada url, perfil de acesso.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.anyRequest().authenticated()
			.and().formLogin();
		;
	}
	
	
	
	
	//Método que serve para fazermos configurações de recursos estáticos.
	//São requisições para arquivo CSS, Javascript, imagens, etc.
	//Não é nosso caso, já que estamos desenvolvendo só a parte do backend.
	//O frontend fica na aplicação cliente. É separado.
	//Mas se fosse uma aplicação em que o frontend está integrado, iríamos ensinar para o Spring
	//		que as requisições devem ser ignoradas, que não é para interceptar na parte de segurança.
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
}
