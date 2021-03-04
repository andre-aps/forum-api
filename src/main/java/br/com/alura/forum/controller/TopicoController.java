package br.com.alura.forum.controller;

import java.net.URISyntaxException;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import br.com.alura.forum.dto.AtualizacaoTopicoDto;
import br.com.alura.forum.dto.DetalhesTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.dto.TopicoFormDto;
import br.com.alura.forum.service.TopicoService;

@RestController
@RequestMapping(path = "/topicos")
public class TopicoController {

	@Autowired
	TopicoService topicoService;

	@GetMapping
	@Cacheable(cacheNames = "listaDeTopicos")
	public ResponseEntity<Page<TopicoDto>> listar(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page) {
		return topicoService.listar(nomeCurso, page);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
		return topicoService.detalhar(id);
	}

	@PostMapping
	@Transactional
	@CacheEvict(cacheNames = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> salvar(@RequestBody @Valid TopicoFormDto form) throws URISyntaxException {
		return topicoService.salvar(form);
	}

	@PutMapping(path = "/{id}")
	@Transactional
	@CacheEvict(cacheNames = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoDto form) {
		return topicoService.atualizar(id, form);
	}

	@DeleteMapping(path = "/{id}")
	@Transactional
	@CacheEvict(cacheNames = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> deletar(@PathVariable Long id) {
		return topicoService.deletar(id);
	}

}
