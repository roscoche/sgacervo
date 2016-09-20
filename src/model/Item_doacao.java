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
public class Item_doacao {
    private int cod_item_doacao;
    private int cod_doacao;
    private int cod_tipo;
    private int quantidade_item_doacao;
    
    public int getCod_tipo() {
        return cod_tipo;
    }

    public void setCod_tipo(int cod_tipo) {
        this.cod_tipo = cod_tipo;
    }
    
    

    
    
    public int getCod_item_doacao() {
        return cod_item_doacao;
    }

    public void setCod_item_doacao(int cod_item_doacao) {
        this.cod_item_doacao = cod_item_doacao;
    }

    public int getCod_doacao() {
        return cod_doacao;
    }

    public void setCod_doacao(int cod_doacao) {
        this.cod_doacao = cod_doacao;
    }

    public int getQuantidade_item_doacao() {
        return quantidade_item_doacao;
    }

    public void setQuantidade_item_doacao(int quantidade_item_doacao) {
        this.quantidade_item_doacao = quantidade_item_doacao;
    }

    
}
