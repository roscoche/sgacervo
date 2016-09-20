
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class TecnologiaDAO 
{
    private control.Banco b;
    
    public TecnologiaDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Tecnologia u)
    {
        int r=0;
        System.out.println(u.getNome());
        r = b.inserir("INSERT INTO Tecnologia(cod_Tecnologia, nome_tecnologia) VALUES (DEFAULT,'"+u.getNome()+"')");
        
        return r;
    }
    
    public boolean alterar(Tecnologia u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Tecnologia SET nome_Tecnologia='"+u.getNome()+"' WHERE cod_Tecnologia="+u.getCod_tecnologia()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Tecnologia)
	{
		int r=0;
		r = b.excluir("DELETE FROM Tecnologia WHERE cod_Tecnologia ="+cod_Tecnologia+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Tecnologia> pesquisar(String sql)
	{
		ArrayList<Tecnologia> vet = new ArrayList<Tecnologia>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Tecnologia u = new Tecnologia();
                        u.setCod_tecnologia(r.getInt("cod_Tecnologia"));
                        u.setNome(r.getString("nome_tecnologia"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Tecnologia getByCod(int cod_Tecnologia)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Tecnologia WHERE cod_Tecnologia ="+cod_Tecnologia+"");
                try 
                {
                    if (r.next())
                    {
                        Tecnologia u = new Tecnologia();
                        u.setCod_tecnologia(r.getInt("cod_Tecnologia"));
                        u.setNome(r.getString("nome_tecnologia"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Tecnologia getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Tecnologia WHERE nome_Tecnologia ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Tecnologia u = new Tecnologia();
                        u.setCod_tecnologia(r.getInt("cod_Tecnologia"));
                        u.setNome(r.getString("nome_tecnologia"));
                        
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
