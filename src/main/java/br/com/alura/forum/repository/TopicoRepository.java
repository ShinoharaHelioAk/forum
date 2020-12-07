package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//findByCursoNome -> Busca o atributo 'cursoNome' na entidade Topico.
	//Caso não encontre, ele vai procurar no relacionamento, no caso, a entidade 'Curso' e o seu atributo 'Nome'.
	//List<Topico> findByCursoNome(String nomeCurso);
	
	//Para eliminar essa possível ambiguidade caso o atributo 'cursoNome' seja criado posteriormente na entidade Topico, 
	//utiliza-se a convenção 'findByCurso_Nome', para dizer ao Spring que ele vai obrigatoriamente buscar na entidade
	//de relacionamento, ou seja, na entidade 'Curso' e o seu atributo 'Nome'.
	List<Topico> findByCurso_Nome(String nomeCurso);
	
}
