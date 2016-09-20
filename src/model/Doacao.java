/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Felipe
 */
public class Doacao {
    private int cod_doacao,cod_usuario,cod_doador,cod_evento_origem;

    public int getCod_evento_origem() {
        return cod_evento_origem;
    }

    public void setCod_evento_origem(int cod_evento_origem) {
        this.cod_evento_origem = cod_evento_origem;
    }
    private Date data_doacao;

    public int getCod_doacao() {
        return cod_doacao;
    }

    public void setCod_doacao(int cod_doacao) {
        this.cod_doacao = cod_doacao;
    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public int getCod_doador() {
        return cod_doador;
    }

    public void setCod_doador(int cod_doador) {
        this.cod_doador = cod_doador;
    }

    public Date getData_doacao() {
        return data_doacao;
    }

    public void setData_doacao(Date data_doacao) {
        this.data_doacao = data_doacao;
    }
}
