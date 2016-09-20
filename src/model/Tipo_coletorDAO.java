
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class Tipo_coletorDAO 
{
    private control.Banco b;
    
    public Tipo_coletorDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Tipo_coletor u)
    {
        int r=0;
        r = b.inserir("INSERT INTO Tipo_coletor(cod_Tipo_coletor, nome_tipo_coletor) VALUES (DEFAULT,'"+u.getNome_tipo_coletor()+"')");
        
        return r;
    }
    
    public boolean alterar(Tipo_coletor u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Tipo_coletor SET nome_Tipo_coletor='"+u.getNome_tipo_coletor()+"' WHERE cod_Tipo_coletor="+u.getCod_tipo_coletor()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Tipo_coletor)
	{
		int r=0;
		r = b.excluir("DELETE FROM Tipo_coletor WHERE cod_Tipo_coletor ="+cod_Tipo_coletor+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Tipo_coletor> pesquisar(String sql)
	{
		ArrayList<Tipo_coletor> vet = new ArrayList<Tipo_coletor>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Tipo_coletor u = new Tipo_coletor();
                        u.setCod_tipo_coletor(r.getInt("cod_Tipo_coletor"));
                        u.setNome_tipo_coletor(r.getString("nome_tipo_coletor"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Tipo_coletor getByCod(int cod_Tipo_coletor)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Tipo_coletor WHERE cod_Tipo_coletor ="+cod_Tipo_coletor+"");
                try 
                {
                    if (r.next())
                    {
                        Tipo_coletor u = new Tipo_coletor();
                        u.setCod_tipo_coletor(r.getInt("cod_Tipo_coletor"));
                        u.setNome_tipo_coletor(r.getString("nome_tipo_coletor"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Tipo_coletor getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Tipo_coletor WHERE nome_Tipo_coletor ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Tipo_coletor u = new Tipo_coletor();
                        u.setCod_tipo_coletor(r.getInt("cod_Tipo_coletor"));
                        u.setNome_tipo_coletor(r.getString("nome_tipo_coletor"));
                        
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
