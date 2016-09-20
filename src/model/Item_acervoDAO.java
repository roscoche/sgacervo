
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class Item_acervoDAO 
{
    private control.Banco b;
    
    public Item_acervoDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Item_acervo u)
    {
        int r=0;
        java.util.Date dataUtil=new java.util.Date();
        java.sql.Date datasql= new java.sql.Date(dataUtil.getTime());
        r = b.inserir("INSERT INTO Item_acervo(cod_Item_acervo, cod_usuario,cod_doador,data_cadastro_item_acervo,descricao_item_acervo,capacidade_mb,ano,funciona,cod_tipo,cod_marca,cod_modelo,cod_interface,cod_tecnologia,cod_container) VALUES (DEFAULT,"+u.getCod_usuario()+","+u.getCod_doador()+",'"+datasql+"','"+u.getDescricao()+"',"+u.getCapacidade()+","+u.getAno()+","+u.isFunciona()+","+u.getCod_tipo()+","+u.getCod_marca()+","+u.getCod_modelo()+","+u.getCod_interface()+","+u.getCod_tecnologia()+","+u.getCod_container()+")");
        
        return r;
    }
    
    public boolean alterar(Item_acervo u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Item_acervo SET cod_tipo="+u.getCod_tipo()+",cod_interface="+u.getCod_interface()+",cod_tecnologia="+u.getCod_tecnologia()+",cod_marca="+u.getCod_marca()+",cod_container="+u.getCod_container()+",cod_modelo="+u.getCod_modelo()+",ano="+u.getAno()+",funciona="+u.isFunciona()+",capacidade_mb="+u.getCapacidade()+",descricao_item_acervo='"+u.getDescricao()+"' WHERE cod_Item_acervo="+u.getCod_item_acervo()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Item_acervo)
	{
		int r=0;
		r = b.excluir("DELETE FROM Item_acervo WHERE cod_Item_acervo ="+cod_Item_acervo+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Item_acervo> pesquisar(String sql)
	{
		ArrayList<Item_acervo> vet = new ArrayList<Item_acervo>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Item_acervo u = new Item_acervo();
                        u.setCod_item_acervo(r.getInt("cod_Item_acervo"));
                        u.setCod_usuario(r.getInt("cod_usuario"));
                        u.setCod_doador(r.getInt("cod_doador"));
                        u.setData_cadastro_item_acervo(r.getDate("data_cadastro_item_acervo"));
                        u.setDescricao(r.getString("descricao_item_acervo"));
                        u.setCapacidade(r.getInt("capacidade_mb"));
                        u.setAno(r.getInt("ano"));
                        u.setFunciona(r.getBoolean("funciona"));
                        u.setCod_marca(r.getInt("cod_marca"));
                        u.setCod_tipo(r.getInt("cod_tipo"));
                        u.setCod_marca(r.getInt("cod_marca"));
                        u.setCod_modelo(r.getInt("cod_modelo"));
                        u.setCod_interface(r.getInt("cod_interface"));
                        u.setCod_tecnologia(r.getInt("cod_tecnologia"));
                        u.setCod_container(r.getInt("cod_container"));
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Item_acervo getByCod(int cod_Item_acervo)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Item_acervo WHERE cod_Item_acervo ="+cod_Item_acervo+"");
                try 
                {
                    if (r.next())
                    {
                        Item_acervo u = new Item_acervo();
                        u.setCod_item_acervo(r.getInt("cod_Item_acervo"));
                        u.setCod_usuario(r.getInt("cod_usuario"));
                        u.setCod_doador(r.getInt("cod_doador"));
                        u.setData_cadastro_item_acervo(r.getDate("data_cadastro_item_acervo"));
                        u.setDescricao(r.getString("descricao_item_acervo"));
                        u.setCapacidade(r.getInt("capacidade_mb"));
                        u.setAno(r.getInt("ano"));
                        u.setFunciona(r.getBoolean("funciona"));
                        u.setCod_marca(r.getInt("cod_marca"));
                        u.setCod_tipo(r.getInt("cod_tipo"));
                        u.setCod_marca(r.getInt("cod_marca"));
                        u.setCod_modelo(r.getInt("cod_modelo"));
                        u.setCod_interface(r.getInt("cod_interface"));
                        u.setCod_tecnologia(r.getInt("cod_tecnologia"));
                        u.setCod_container(r.getInt("cod_container"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
	
	public void fechar()
	{	
	b.fechar();
	}
}
