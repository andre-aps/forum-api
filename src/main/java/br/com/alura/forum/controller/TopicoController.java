package br.com.alura.forum.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.AtualizacaoTopicoDto;
import br.com.alura.forum.dto.DetalhesTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.dto.TopicoFormDto;
import br.com.alura.forum.model.Topico;
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
	public List<TopicoDto> listar(String nomeCurso) {
		if (nomeCurso != null) {
			return TopicoDto.converte(topicoRepository.findByCursoNome(nomeCurso));
		} else {
			List<Topico> lista = topicoRepository.findAll();
			return TopicoDto.converte(lista);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if(!topico.isPresent())
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok().body(new DetalhesTopicoDto(topico));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> salvar(@RequestBody @Valid TopicoFormDto form) throws URISyntaxException {
		Topico topico = topicoRepository.save(form.converte(cursoRepository));
		return ResponseEntity.created(new URI("/topicos")).body(new TopicoDto(topico));
	}
	
	@PutMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,
			@RequestBody @Valid AtualizacaoTopicoDto form) {
		
		Optional<Topico> topico = topicoRepository.findById(id);
		if(!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Topico topicoAtualizado = form.atualiza(topico);
			return ResponseEntity.ok().body(new TopicoDto(topicoAtualizado));
		}
		
	}
	
	@DeleteMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> deletar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if(!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			topicoRepository.delete(topico.get());
			return ResponseEntity.ok().build();
		}
	}
	
}
