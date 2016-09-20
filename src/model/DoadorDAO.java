
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class DoadorDAO 
{
    private control.Banco b;
    
    public DoadorDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Doador u)
    {
        int r=0;
        
        r = b.inserir("INSERT INTO Doador(cod_Doador, nome_doador) VALUES (DEFAULT,'"+u.getNome_doador()+"')");
        
        return r;
    }
    
    public boolean alterar(Doador u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Doador SET nome_Doador='"+u.getNome_doador()+"' WHERE cod_Doador="+u.getCod_doador()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Doador)
	{
		int r=0;
		r = b.excluir("DELETE FROM Doador WHERE cod_Doador ="+cod_Doador+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Doador> pesquisar(String sql)
	{
		ArrayList<Doador> vet = new ArrayList<Doador>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Doador u = new Doador();
                        u.setCod_doador(r.getInt("cod_Doador"));
                        u.setNome_doador(r.getString("nome_Doador"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Doador getByCod(int cod_Doador)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Doador WHERE cod_Doador ="+cod_Doador+"");
                try 
                {
                    if (r.next())
                    {
                        Doador u = new Doador();
                        u.setCod_doador(r.getInt("cod_Doador"));
                        u.setNome_doador(r.getString("nome_Doador"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Doador getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Doador WHERE nome_Doador ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Doador u = new Doador();
                        u.setCod_doador(r.getInt("cod_Doador"));
                        u.setNome_doador(r.getString("nome_Doador"));
                        
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
