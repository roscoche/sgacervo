/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class ContainerDAO {
    Banco b;
    public ContainerDAO(){
        b=new Banco();
}
    public void fechar(){
    b.fechar();
    }
    public int inserir(Container u)
    {
        int r=0;
        
        r = b.inserir("INSERT INTO Container(cod_Container, cod_tipo_container, localizacao_container) VALUES (DEFAULT,"+u.getCod_tipo_container()+", '"+u.getLocalizacao_container()+"')");
        
        return r;
    }
    
    public boolean alterar(Container u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Container SET cod_tipo_Container="+u.getCod_tipo_container()+",localizacao_Container='"+u.getLocalizacao_container()+"' WHERE cod_Container="+u.getCod_container()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_container)
	{
		int r=0;
		r = b.excluir("DELETE FROM Container WHERE cod_Container ="+cod_container+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Container> pesquisar(String sql)
	{
		ArrayList<Container> vet = new ArrayList<Container>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Container u = new Container();
                        u.setCod_container(r.getInt("cod_Container"));
                        u.setCod_tipo_container(r.getInt("cod_tipo_container"));
                        u.setLocalizacao_container(r.getString("localizacao_container"));
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Container getByCod(int cod_Container)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Container WHERE cod_Container="+cod_Container+"");
                try 
                {
                    if (r.next())
                    {
                        Container u = new Container();
                        u.setCod_container(r.getInt("cod_Container"));
                        u.setCod_tipo_container(r.getInt("cod_tipo_container"));
                        u.setLocalizacao_container(r.getString("localizacao_container"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Container getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Container WHERE localizacao_Container='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Container u = new Container();
                        u.setCod_container(r.getInt("cod_Container"));
                        u.setCod_tipo_container(r.getInt("cod_tipo_container"));
                        u.setLocalizacao_container(r.getString("localizacao_container"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
}
