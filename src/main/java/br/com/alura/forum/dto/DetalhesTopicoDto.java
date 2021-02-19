package br.com.alura.forum.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;

public class DetalhesTopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDto> respostas = new ArrayList<>();
	
	public DetalhesTopicoDto() {}

	public DetalhesTopicoDto(Optional<Topico> topico) {
		this.id = topico.get().getId();
		this.titulo = topico.get().getTitulo();
		this.mensagem = topico.get().getMensagem();
		this.dataCriacao = topico.get().getDataCriacao();
		this.nomeAutor = topico.get().getAutor().getNome();
		this.status = topico.get().getStatus();
		this.respostas.addAll(topico.get().getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}

}
