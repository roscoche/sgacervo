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
public class Imagem {
    private int cod_imagem,cod_item_acervo;
    private String link;

    public int getCod_imagem() {
        return cod_imagem;
    }

    public void setCod_imagem(int cod_imagem) {
        this.cod_imagem = cod_imagem;
    }

    public int getCod_item_acervo() {
        return cod_item_acervo;
    }

    public void setCod_item_acervo(int cod_item_acervo) {
        this.cod_item_acervo = cod_item_acervo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
