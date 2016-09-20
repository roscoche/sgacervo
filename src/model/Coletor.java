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
public class Coletor {
    private int cod_coletor,cod_tipo_coletor;
    private String nome;

    public int getCod_tipo_coletor() {
        return cod_tipo_coletor;
    }

    public void setCod_tipo_coletor(int cod_tipo_coletor) {
        this.cod_tipo_coletor = cod_tipo_coletor;
    }

    public int getCod_coletor() {
        return cod_coletor;
    }

    public void setCod_coletor(int cod_coletor) {
        this.cod_coletor = cod_coletor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
