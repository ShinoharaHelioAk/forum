package br.com.alura.forum.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;

//@DataJpaTest -> Anotação específica para execução de testes automatizados com interfaces Repository.
@RunWith(SpringRunner.class)
@DataJpaTest
public class CursoRepositoryTest {
	@Autowired
	private CursoRepository cursoRepository;

	@Test
	public void findByNomeTest01() {
		String nomeCurso = "HTML 5";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		Assert.assertNotNull(curso);
		Assert.assertEquals(nomeCurso, curso.getNome());
	}

	@Test
	public void findByNomeTest02() {
		String nomeCurso = "JPA";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		Assert.assertNull(curso);
	}
}
