
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class Item_repasseDAO 
{
    private control.Banco b;
    
    public Item_repasseDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Item_repasse u)
    {
        int r=0;
        java.util.Date dataUtil=new java.util.Date();
        java.sql.Date datasql= new java.sql.Date(dataUtil.getTime());
        r = b.inserir("INSERT INTO Item_repasse(cod_Item_repasse, cod_repasse,cod_tipo,quantidade_item_repasse) VALUES (DEFAULT,"+u.getCod_repasse()+","+u.getCod_tipo()+","+u.getQuantidade_item_repasse()+")");
        
        return r;
    }
    
    public boolean alterar(Item_repasse u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Item_repasse SET cod_tipo="+u.getCod_tipo()+",quantidade_item_repasse="+u.getQuantidade_item_repasse()+" WHERE cod_Item_repasse="+u.getCod_item_repasse()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Item_repasse)
	{
		int r=0;
		r = b.excluir("DELETE FROM Item_repasse WHERE cod_Item_repasse ="+cod_Item_repasse+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Item_repasse> pesquisar(String sql)
	{
		ArrayList<Item_repasse> vet = new ArrayList<Item_repasse>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Item_repasse u = new Item_repasse();
                        
                        u.setCod_item_repasse(r.getInt("cod_Item_repasse"));
                        u.setCod_repasse(r.getInt("cod_repasse"));
                        u.setCod_tipo(r.getInt("cod_tipo"));
                        u.setQuantidade_item_repasse(r.getInt("quantidade_item_repasse"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Item_repasse getByCod(int cod_Item_repasse)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Item_repasse WHERE cod_Item_repasse ="+cod_Item_repasse+"");
                try 
                {
                    if (r.next())
                    {
                        Item_repasse u = new Item_repasse();
                        u.setCod_item_repasse(r.getInt("cod_Item_repasse"));
                        u.setCod_repasse(r.getInt("cod_repasse"));
                        u.setCod_tipo(r.getInt("cod_tipo"));
                        u.setQuantidade_item_repasse(r.getInt("quantidade_item_repasse"));
                        
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
