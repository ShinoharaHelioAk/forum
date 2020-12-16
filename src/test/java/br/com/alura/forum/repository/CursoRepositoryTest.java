package br.com.alura.forum.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;

//@DataJpaTest -> Anotação específica para execução de testes automatizados com interfaces Repository.
//@ActiveProfiles("test") -> Força o Spring para que esta classe de testes tenha um Profile declarado como Ativo.
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) -> 
//		Diz para o Spring como você quer que sejam feitos os testes com a base de dados.
//		o replace = none diz para não substituir as configurações do banco de dados declarado no application.properties.
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CursoRepositoryTest {
	@Autowired
	private CursoRepository cursoRepository;
	
	/*@Autowired
	private TestEntityManager em;*/

	@Test
	public void findByNomeTest01() {
		String nomeCurso = "HTML 5";
		
		/*Curso html5 = new Curso();
		html5.setNome(nomeCurso);
		html5.setCategoria("Programação");
		em.persist(html5);*/
		
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
