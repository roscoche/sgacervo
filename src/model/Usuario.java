/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Felipe
 */
public class Usuario {
    private int cod_usuario;
    private String senha_usuario,nome_usuario,email,chave_encriptacao,registro_academico;

    public String getRegistro_academico() {
        return registro_academico;
    }

    public void setRegistro_academico(String registro_academico) {
        this.registro_academico = registro_academico;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChave_encriptacao() {
        return chave_encriptacao;
    }

    public void setChave_encriptacao(String chave_encriptacao) {
        this.chave_encriptacao = chave_encriptacao;
    }
    private boolean usuario_administrador;

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public boolean isUsuario_administrador() {
        return usuario_administrador;
    }

    public void setUsuario_administrador(boolean usuario_administrador) {
        this.usuario_administrador = usuario_administrador;
    }
}
