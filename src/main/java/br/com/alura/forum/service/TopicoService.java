package br.com.alura.forum.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.alura.forum.dto.AtualizacaoTopicoDto;
import br.com.alura.forum.dto.DetalhesTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.dto.TopicoFormDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.repository.UsuarioRepository;

@Service
public class TopicoService {

	@Autowired
	TopicoRepository topicoRepository;

	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	public ResponseEntity<Page<TopicoDto>> listar(String nomeCurso, Pageable page) {
		
		if (nomeCurso != null) {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, page);

			if (!topicos.isEmpty())
				return ResponseEntity.ok().body(TopicoDto.converte(topicoRepository.findByCursoNome(nomeCurso, page)));
			else
				return ResponseEntity.notFound().build();

		} else {
			Page<Topico> lista = topicoRepository.findAll(page);
			return ResponseEntity.ok().body(TopicoDto.converte(lista));
		}
	}

	public ResponseEntity<DetalhesTopicoDto> detalhar(Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (!topico.isPresent())
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok().body(new DetalhesTopicoDto(topico));
	}

	public ResponseEntity<TopicoDto> salvar(@Valid TopicoFormDto form) throws URISyntaxException {
		Topico topico = topicoRepository.save(form.converte(cursoRepository, usuarioRepository));
		return ResponseEntity.created(new URI("/topicos")).body(new TopicoDto(topico));
	}

	public ResponseEntity<TopicoDto> atualizar(Long id, @Valid AtualizacaoTopicoDto form) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Topico topicoAtualizado = form.atualiza(topico);
			return ResponseEntity.ok().body(new TopicoDto(topicoAtualizado));
		}
	}

	public ResponseEntity<TopicoDto> deletar(Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);

		if (!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			topicoRepository.delete(topico.get());
			return ResponseEntity.ok().build();
		}
	}

}
