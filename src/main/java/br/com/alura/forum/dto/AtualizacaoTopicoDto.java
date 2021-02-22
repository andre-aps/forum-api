package br.com.alura.forum.dto;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.forum.model.Topico;

public class AtualizacaoTopicoDto {

	@NotNull
	@NotEmpty
	private String titulo;
	@NotNull
	@NotEmpty
	private String mensagem;

	public AtualizacaoTopicoDto() {
	}

	public AtualizacaoTopicoDto(Topico topico) {
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public Topico atualiza(Optional<Topico> topico) {
		topico.get().setTitulo(this.getTitulo());
		topico.get().setMensagem(this.getMensagem());

		return topico.get();

	}

}
