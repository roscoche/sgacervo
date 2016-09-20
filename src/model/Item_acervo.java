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
public class Item_acervo {
    private int cod_item_acervo,cod_usuario,cod_doador,cod_marca,cod_tipo,cod_modelo,cod_interface,cod_tecnologia,cod_container,ano,capacidade;
    private Date data_cadastro_item_acervo;
    private boolean funciona;
    private String descricao;


    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCod_doador() {
        return cod_doador;
    }

    public void setCod_doador(int cod_doador) {
        this.cod_doador = cod_doador;
    }

    public int getCod_tipo() {
        return cod_tipo;
    }

    public void setCod_tipo(int cod_tipo) {
        this.cod_tipo = cod_tipo;
    }

    public int getCod_modelo() {
        return cod_modelo;
    }

    public void setCod_modelo(int cod_modelo) {
        this.cod_modelo = cod_modelo;
    }

    public int getCod_interface() {
        return cod_interface;
    }

    public void setCod_interface(int cod_interface) {
        this.cod_interface = cod_interface;
    }

    public int getCod_tecnologia() {
        return cod_tecnologia;
    }

    public void setCod_tecnologia(int cod_tecnologia) {
        this.cod_tecnologia = cod_tecnologia;
    }

    
    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

   

    public boolean isFunciona() {
        return funciona;
    }

    public void setFunciona(boolean funciona) {
        this.funciona = funciona;
    }

    public int getCod_item_acervo() {
        return cod_item_acervo;
    }

    public void setCod_item_acervo(int cod_item_acervo) {
        this.cod_item_acervo = cod_item_acervo;
    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public int getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(int cod_marca) {
        this.cod_marca = cod_marca;
    }

    public int getCod_container() {
        return cod_container;
    }

    public void setCod_container(int cod_container) {
        this.cod_container = cod_container;
    }

   
    public Date getData_cadastro_item_acervo() {
        return data_cadastro_item_acervo;
    }

    public void setData_cadastro_item_acervo(Date data_cadastro_item_acervo) {
        this.data_cadastro_item_acervo = data_cadastro_item_acervo;
    }
    
}
