package br.com.alura.forum.dto;

public class FormDto {

	private String campo;
	private String erro;

	public FormDto() {
	}

	public FormDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}

}
