package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;

//@Controller
@RestController
public class TopicosController {
	
//	@RequestMapping("/topicos")
	//@ResponseBody //Indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
//	public List<Topico> lista() {
//		Topico topico = new Topico("Duvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
//		return Arrays.asList(topico, topico, topico);
//	}
	
	@RequestMapping("/topicos")
	//@ResponseBody //Indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
	public List<TopicoDto> lista() {
		Topico topico = new Topico("Duvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		return TopicoDto.converter(Arrays.asList(topico, topico, topico));
	}
}
