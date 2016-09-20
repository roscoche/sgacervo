
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class Tipo_containerDAO 
{
    private control.Banco b;
    
    public Tipo_containerDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Tipo_container u)
    {
        int r=0;
        r = b.inserir("INSERT INTO Tipo_container(cod_Tipo_container, nome_tipo_container) VALUES (DEFAULT,'"+u.getNome_tipo_container()+"')");
        
        return r;
    }
    
    public boolean alterar(Tipo_container u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Tipo_container SET nome_Tipo_container='"+u.getNome_tipo_container()+"' WHERE cod_Tipo_container="+u.getCod_tipo_container()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Tipo_container)
	{
		int r=0;
		r = b.excluir("DELETE FROM Tipo_container WHERE cod_Tipo_container ="+cod_Tipo_container+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Tipo_container> pesquisar(String sql)
	{
		ArrayList<Tipo_container> vet = new ArrayList<Tipo_container>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Tipo_container u = new Tipo_container();
                        u.setCod_tipo_container(r.getInt("cod_Tipo_container"));
                        u.setNome_tipo_container(r.getString("nome_tipo_container"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Tipo_container getByCod(int cod_Tipo_container)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Tipo_container WHERE cod_Tipo_container ="+cod_Tipo_container+"");
                try 
                {
                    if (r.next())
                    {
                        Tipo_container u = new Tipo_container();
                        u.setCod_tipo_container(r.getInt("cod_Tipo_container"));
                        u.setNome_tipo_container(r.getString("nome_tipo_container"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Tipo_container getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Tipo_container WHERE nome_Tipo_container ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Tipo_container u = new Tipo_container();
                        u.setCod_tipo_container(r.getInt("cod_Tipo_container"));
                        u.setNome_tipo_container(r.getString("nome_tipo_container"));
                        
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
