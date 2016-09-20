
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class MarcaDAO 
{
    private control.Banco b;
    
    public MarcaDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Marca u)
    {
        int r=0;
        
        r = b.inserir("INSERT INTO Marca(cod_Marca, nome_marca) VALUES (DEFAULT,'"+u.getNome()+"')");
        
        return r;
    }
    
    public boolean alterar(Marca u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Marca SET nome_Marca='"+u.getNome()+"' WHERE cod_Marca="+u.getCod_marca()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Marca)
	{
		int r=0;
		r = b.excluir("DELETE FROM Marca WHERE cod_Marca ="+cod_Marca+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Marca> pesquisar(String sql)
	{
		ArrayList<Marca> vet = new ArrayList<Marca>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Marca u = new Marca();
                        u.setCod_marca(r.getInt("cod_Marca"));
                        u.setNome(r.getString("nome_marca"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Marca getByCod(int cod_Marca)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Marca WHERE cod_Marca ="+cod_Marca+"");
                try 
                {
                    if (r.next())
                    {
                        Marca u = new Marca();
                        u.setCod_marca(r.getInt("cod_Marca"));
                        u.setNome(r.getString("nome_marca"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
         public Marca getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Marca WHERE nome_Marca ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Marca u = new Marca();
                        u.setCod_marca(r.getInt("cod_Marca"));
                        u.setNome(r.getString("nome_marca"));
                        
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
