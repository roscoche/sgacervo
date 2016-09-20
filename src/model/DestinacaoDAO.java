
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class DestinacaoDAO 
{
    private control.Banco b;
    
    public DestinacaoDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Destinacao u)
    {
        int r=0;
        r = b.inserir("INSERT INTO Destinacao(cod_Destinacao, nome_destinacao) VALUES (DEFAULT,'"+u.getNome_destinacao()+"')");
        
        return r;
    }
    
    public boolean alterar(Destinacao u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Destinacao SET nome_Destinacao='"+u.getNome_destinacao()+"' WHERE cod_Destinacao="+u.getCod_destinacao()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Destinacao)
	{
		int r=0;
		r = b.excluir("DELETE FROM Destinacao WHERE cod_Destinacao ="+cod_Destinacao+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Destinacao> pesquisar(String sql)
	{
		ArrayList<Destinacao> vet = new ArrayList<Destinacao>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Destinacao u = new Destinacao();
                        u.setCod_destinacao(r.getInt("cod_Destinacao"));
                        u.setNome_destinacao(r.getString("nome_destinacao"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Destinacao getByCod(int cod_Destinacao)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Destinacao WHERE cod_Destinacao ="+cod_Destinacao+"");
                try 
                {
                    if (r.next())
                    {
                        Destinacao u = new Destinacao();
                        u.setCod_destinacao(r.getInt("cod_Destinacao"));
                        u.setNome_destinacao(r.getString("nome_destinacao"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Destinacao getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Destinacao WHERE nome_Destinacao ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Destinacao u = new Destinacao();
                        u.setCod_destinacao(r.getInt("cod_Destinacao"));
                        u.setNome_destinacao(r.getString("nome_destinacao"));
                        
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
