package br.com.alura.forum.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.dto.TopicoFormDto;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping(path = "/topicos")
public class TopicoController {

	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDto> listaTopicos(String nomeCurso) {
		if (nomeCurso != null) {
			return TopicoDto.converte(topicoRepository.findByCursoNome(nomeCurso));
		} else {
			List<Topico> lista = topicoRepository.findAll();
			return TopicoDto.converte(lista);
		}
	}

	@PostMapping
	public ResponseEntity<TopicoDto> salvarTopico(@RequestBody TopicoFormDto form) throws URISyntaxException {
		Topico topico = topicoRepository.save(form.converte(cursoRepository));
		return ResponseEntity.created(new URI("/topicos")).body(new TopicoDto(topico));
	}

}
