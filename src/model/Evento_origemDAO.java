
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class Evento_origemDAO 
{
    private control.Banco b;
    
    public Evento_origemDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Evento_origem u)
    {
        int r=0;
        r = b.inserir("INSERT INTO Evento_origem(cod_Evento_origem, nome_evento_origem) VALUES (DEFAULT,'"+u.getNome_evento_origem()+"')");
        
        return r;
    }
    
    public boolean alterar(Evento_origem u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Evento_origem SET nome_Evento_origem='"+u.getNome_evento_origem()+"' WHERE cod_Evento_origem="+u.getCod_evento_origem()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Evento_origem)
	{
		int r=0;
		r = b.excluir("DELETE FROM Evento_origem WHERE cod_Evento_origem ="+cod_Evento_origem+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Evento_origem> pesquisar(String sql)
	{
		ArrayList<Evento_origem> vet = new ArrayList<Evento_origem>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Evento_origem u = new Evento_origem();
                        u.setCod_evento_origem(r.getInt("cod_Evento_origem"));
                        u.setNome_evento_origem(r.getString("nome_evento_origem"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Evento_origem getByCod(int cod_Evento_origem)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Evento_origem WHERE cod_Evento_origem ="+cod_Evento_origem+"");
                try 
                {
                    if (r.next())
                    {
                        Evento_origem u = new Evento_origem();
                        u.setCod_evento_origem(r.getInt("cod_Evento_origem"));
                        u.setNome_evento_origem(r.getString("nome_evento_origem"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Evento_origem getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Evento_origem WHERE nome_Evento_origem ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Evento_origem u = new Evento_origem();
                        u.setCod_evento_origem(r.getInt("cod_Evento_origem"));
                        u.setNome_evento_origem(r.getString("nome_evento_origem"));
                        
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
