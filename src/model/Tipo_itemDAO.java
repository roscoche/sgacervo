
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class Tipo_itemDAO 
{
    private control.Banco b;
    
    public Tipo_itemDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Tipo_item u)
    {
        int r=0;
        
        r = b.inserir("INSERT INTO Tipo_item(cod_Tipo, nome_tipo) VALUES (DEFAULT,'"+u.getNome()+"')");
        
        return r;
    }
    
    public boolean alterar(Tipo_item u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Tipo_item SET nome_Tipo='"+u.getNome()+"' WHERE cod_Tipo="+u.getCod_tipo()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Tipo)
	{
		int r=0;
		r = b.excluir("DELETE FROM Tipo_item WHERE cod_Tipo ="+cod_Tipo+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Tipo_item> pesquisar(String sql)
	{
		ArrayList<Tipo_item> vet = new ArrayList<Tipo_item>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Tipo_item u = new Tipo_item();
                        u.setCod_tipo(r.getInt("cod_Tipo"));
                        u.setNome(r.getString("nome_tipo"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Tipo_item getByCod(int cod_Tipo)
	{	ResultSet r = b.pesquisar("SELECT * from Tipo_item WHERE cod_Tipo ="+cod_Tipo+"");
                try 
                {
                    if (r.next())
                    {
                        Tipo_item u = new Tipo_item();
                        u.setCod_tipo(r.getInt("cod_Tipo"));
                        u.setNome(r.getString("nome_tipo"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Tipo_item getByNome(String nome)
	{	ResultSet r = b.pesquisar("SELECT * from Tipo_item WHERE nome_Tipo ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Tipo_item u = new Tipo_item();
                        u.setCod_tipo(r.getInt("cod_Tipo"));
                        u.setNome(r.getString("nome_tipo"));
                        
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
