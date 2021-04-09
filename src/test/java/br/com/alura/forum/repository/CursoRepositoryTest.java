package br.com.alura.forum.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.com.alura.forum.model.Curso;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CursoRepositoryTest {

	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveCarregarUmCursoPeloNome() {
		String nomeCurso = "CSS";
		Curso novoCurso = new Curso();
		novoCurso.setNome(nomeCurso);
		novoCurso.setCategoria("Front-end");
		
		entityManager.persist(novoCurso);
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		assertNotNull(curso);
		assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void naoDeveCarregarUmCursoCujoNomeNaoExista() {
		String nomeCurso = "JPA";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		assertNull(curso);
	}

}
