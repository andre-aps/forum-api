package br.com.alura.forum.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	public ResponseEntity<List<TopicoDto>> listar(String nomeCurso) {
		if (nomeCurso != null) {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);

			if (!topicos.isEmpty())
				return ResponseEntity.ok().body(TopicoDto.converte(topicoRepository.findByCursoNome(nomeCurso)));
			else
				return ResponseEntity.notFound().build();

		} else {
			List<Topico> lista = topicoRepository.findAll();
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
