package br.com.alura.forum.controller;

import java.net.URI;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
//import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

//@Controller
@RestController
@RequestMapping("/topicos")
public class TopicosController {
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
//	@RequestMapping("/topicos")
	//@ResponseBody //Indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
//	public List<Topico> lista() {
//		Topico topico = new Topico("Duvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
//		return Arrays.asList(topico, topico, topico);
//	}
	
	@GetMapping
	//@ResponseBody //Indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
	public List<TopicoDto> lista(String nomeCurso) {
		System.out.println(nomeCurso);
		
//		Topico topico = new Topico("Duvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
//		return TopicoDto.converter(Arrays.asList(topico, topico, topico));
		
		List<Topico> topicos = new ArrayList<Topico>();
		if (nomeCurso == null) {
			topicos = topicoRepository.findAll();
		} else {
			topicos = topicoRepository.findByCurso_Nome(nomeCurso);
		}
		
		return TopicoDto.converter(topicos);
	}
	
	//(Linha abaixo) O @RequestBody recupera parâmetros de Request com method=POST.
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	//@PathVariable
	//Por padrão, as requisições pegam os parâmetros do request por meio do padrão ...?id=999&...
	//Para avisar que o parâmetro que o request deve pegar é parte do caminho do EndPoint, utiliza-se o @PathVariable.
	
	//Podemos utilizar o mapeamento desta forma também:
	//@GetMapping("/{id}")
	//public DetalhesDoTopicoDto detalhar(@PathVariable("id") Long codigo) {}
		
	@GetMapping("/{id}")
	public DetalhesDoTopicoDto detalhar(@PathVariable Long id) {
		Topico topico = topicoRepository.getOne(id);
		return new DetalhesDoTopicoDto(topico);
	}
}
