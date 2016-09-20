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
public class Destinacao {
     private String nome_destinacao;
    private int cod_destinacao;

    public String getNome_destinacao() {
        return nome_destinacao;
    }

    public void setNome_destinacao(String nome_destinacao) {
        this.nome_destinacao = nome_destinacao;
    }

    public int getCod_destinacao() {
        return cod_destinacao;
    }

    public void setCod_destinacao(int cod_destinacao) {
        this.cod_destinacao = cod_destinacao;
    }
}
