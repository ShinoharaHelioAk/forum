package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	//Não é possível fazer injeção de dependência automática (@Autowired) em classes do tipo Filtro.
	//Por isso, será feita a injeção manual da dependência, por meio do construtor.
	private TokenService tokenService;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		//System.out.println(token);
		
		boolean valido = tokenService.isTokenValid(token);
		//System.out.println(valido);
		
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.split(" ")[1];
		//return token.substring(7, token.length());
	}

}
