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
public class Repasse {
    private int cod_repasse,cod_coletor,cod_usuario,cod_destinacao;
    private Date data_repasse;

    public int getCod_destinacao() {
        return cod_destinacao;
    }

    public void setCod_destinacao(int cod_destinacao) {
        this.cod_destinacao = cod_destinacao;
    }

    public int getCod_repasse() {
        return cod_repasse;
    }

    public void setCod_repasse(int cod_repasse) {
        this.cod_repasse = cod_repasse;
    }

    public int getCod_coletor() {
        return cod_coletor;
    }

    public void setCod_coletor(int cod_coletor) {
        this.cod_coletor = cod_coletor;
    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public Date getData_repasse() {
        return data_repasse;
    }

    public void setData_repasse(Date data_repasse) {
        this.data_repasse = data_repasse;
    }
}
