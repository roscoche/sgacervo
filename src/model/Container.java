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
public class Container {
    private int cod_container,cod_tipo_container;
    private String localizacao_container;

    public int getCod_tipo_container() {
        return cod_tipo_container;
    }

    public void setCod_tipo_container(int cod_tipo_container) {
        this.cod_tipo_container = cod_tipo_container;
    }

    public int getCod_container() {
        return cod_container;
    }

    public void setCod_container(int cod_container) {
        this.cod_container = cod_container;
    }

    

    public String getLocalizacao_container() {
        return localizacao_container;
    }

    public void setLocalizacao_container(String localizacao_container) {
        this.localizacao_container = localizacao_container;
    }
}
