package br.com.alura.forum.controller;

import java.net.URI;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
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
	
	
	//@RequestParam -> Diz para o Spring que são parâmetros de url, e eles se tornam obrigatórios para serem informados.
	//@ResponseBody //Indicamos que o retorno do método deve ser serializado e devolvido no corpo da resposta.
	@GetMapping
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, 
			@RequestParam int pagina, @RequestParam int qtd) {
	//public List<TopicoDto> lista(String nomeCurso) {
		
//		Topico topico = new Topico("Duvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
//		return TopicoDto.converter(Arrays.asList(topico, topico, topico));
		
		Pageable paginacao = PageRequest.of(pagina, qtd);
		
		//List<Topico> topicos = new ArrayList<Topico>();
		Page<Topico> topicos = null;
		if (nomeCurso == null) {
			//topicos = topicoRepository.findAll();
			topicos = topicoRepository.findAll(paginacao);
		} else {
			//topicos = topicoRepository.findByCurso_Nome(nomeCurso);
			//List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
			topicos = topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
		}
		
		return TopicoDto.converter(topicos);
	}
	
	//(Linha abaixo) O @RequestBody recupera parâmetros de Request com method=POST.
	//@Transactional -> Avisar o Spring para realizar o commit no final da transação.
	@PostMapping
	@Transactional
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
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
	//public DetalhesDoTopicoDto detalhar(@PathVariable Long id) {	
		//Topico topico = topicoRepository.getOne(id);
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	//@Transactional -> Avisar o Spring para realizar o commit no final da transação.
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
		
		//Topico topico = form.atualizar(id, topicoRepository);
		//return ResponseEntity.ok().body(new TopicoDto(topico));
		//return ResponseEntity.ok(new TopicoDto(topico));
	}
	
	//@Transactional -> Avisar o Spring para realizar o commit no final da transação.
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
		
		//topicoRepository.deleteById(id);
		//return ResponseEntity.ok().build();
	}
}
