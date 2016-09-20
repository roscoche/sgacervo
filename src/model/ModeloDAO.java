
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class ModeloDAO 
{
    private control.Banco b;
    
    public ModeloDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Modelo u)
    {
        int r=0;
        r = b.inserir("INSERT INTO Modelo(cod_Modelo, nome_modelo) VALUES (DEFAULT,'"+u.getNome()+"')");
        
        return r;
    }
    
    public boolean alterar(Modelo u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Modelo SET nome_Modelo='"+u.getNome()+"' WHERE cod_Modelo="+u.getCod_modelo()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Modelo)
	{
		int r=0;
		r = b.excluir("DELETE FROM Modelo WHERE cod_Modelo ="+cod_Modelo+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Modelo> pesquisar(String sql)
	{
		ArrayList<Modelo> vet = new ArrayList<Modelo>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Modelo u = new Modelo();
                        u.setCod_modelo(r.getInt("cod_Modelo"));
                        u.setNome(r.getString("nome_modelo"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Modelo getByCod(int cod_Modelo)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Modelo WHERE cod_Modelo ="+cod_Modelo+"");
                try 
                {
                    if (r.next())
                    {
                        Modelo u = new Modelo();
                        u.setCod_modelo(r.getInt("cod_Modelo"));
                        u.setNome(r.getString("nome_modelo"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Modelo getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Modelo WHERE nome_Modelo ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Modelo u = new Modelo();
                        u.setCod_modelo(r.getInt("cod_Modelo"));
                        u.setNome(r.getString("nome_modelo"));
                        
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
