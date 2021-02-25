package br.com.alura.forum.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.UsuarioRepository;

public class TopicoFormDto {

	@NotNull
	@NotEmpty
	private String titulo;
	@NotNull
	@NotEmpty
	private String mensagem;
	@NotNull
	@NotEmpty
	private String nomeCurso;
	@NotNull
	@NotEmpty
	private String autor;

	public TopicoFormDto() {
	}

	public TopicoFormDto(String titulo, String mensagem, String nomeCurso, String autor) {
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.nomeCurso = nomeCurso;
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public Topico converte(CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
		return new Topico(titulo, mensagem, cursoRepository.findByNome(nomeCurso), usuarioRepository.findByNome(autor));
	}

	public String getAutor() {
		return autor;
	}

}
