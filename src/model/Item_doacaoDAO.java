
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class Item_doacaoDAO 
{
    private control.Banco b;
    
    public Item_doacaoDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Item_doacao u)
    {
        int r=0;
        java.util.Date dataUtil=new java.util.Date();
        java.sql.Date datasql= new java.sql.Date(dataUtil.getTime());
        r = b.inserir("INSERT INTO Item_doacao(cod_Item_doacao, cod_doacao,cod_tipo,quantidade_item_doacao) VALUES (DEFAULT,"+u.getCod_doacao()+","+u.getCod_tipo()+","+u.getQuantidade_item_doacao()+")");
        
        return r;
    }
    
    public boolean alterar(Item_doacao u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Item_doacao SET cod_tipo="+u.getCod_tipo()+",quantidade_item_doacao="+u.getQuantidade_item_doacao()+" WHERE cod_Item_doacao="+u.getCod_item_doacao()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Item_doacao)
	{
		int r=0;
		r = b.excluir("DELETE FROM Item_doacao WHERE cod_Item_doacao ="+cod_Item_doacao+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Item_doacao> pesquisar(String sql)
	{
		ArrayList<Item_doacao> vet = new ArrayList<Item_doacao>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Item_doacao u = new Item_doacao();
                        
                        u.setCod_item_doacao(r.getInt("cod_Item_doacao"));
                        u.setCod_doacao(r.getInt("cod_doacao"));
                        u.setCod_tipo(r.getInt("cod_tipo"));
                        u.setQuantidade_item_doacao(r.getInt("quantidade_item_doacao"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Item_doacao getByCod(int cod_Item_doacao)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Item_doacao WHERE cod_Item_doacao ="+cod_Item_doacao+"");
                try 
                {
                    if (r.next())
                    {
                        Item_doacao u = new Item_doacao();
                        u.setCod_item_doacao(r.getInt("cod_Item_doacao"));
                        u.setCod_doacao(r.getInt("cod_doacao"));
                        u.setCod_tipo(r.getInt("cod_tipo"));
                        u.setQuantidade_item_doacao(r.getInt("quantidade_item_doacao"));
                        
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
