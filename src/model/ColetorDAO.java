
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class ColetorDAO 
{
    private control.Banco b;
    
    public ColetorDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Coletor u)
    {
        int r=0;
        
        r = b.inserir("INSERT INTO Coletor(cod_coletor, nome_coletor,cod_tipo_coletor) VALUES (DEFAULT,'"+u.getNome()+"',"+u.getCod_tipo_coletor()+")");
        
        return r;
    }
    
    public boolean alterar(Coletor u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Coletor SET nome_coletor='"+u.getNome()+"',cod_tipo_coletor="+u.getCod_tipo_coletor()+" WHERE cod_coletor="+u.getCod_coletor()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_coletor)
	{
		int r=0;
		r = b.excluir("DELETE FROM Coletor WHERE cod_coletor ="+cod_coletor+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Coletor> pesquisar(String sql)
	{
		ArrayList<Coletor> vet = new ArrayList<Coletor>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Coletor u = new Coletor();
                        u.setCod_coletor(r.getInt("cod_coletor"));
                        u.setNome(r.getString("nome_coletor"));
                        u.setCod_tipo_coletor(r.getInt("cod_tipo_coletor"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Coletor getByCod(int cod_coletor)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Coletor WHERE cod_coletor ="+cod_coletor+"");
                try 
                {
                    if (r.next())
                    {
                        Coletor u = new Coletor();
                        u.setCod_coletor(r.getInt("cod_coletor"));
                        u.setNome(r.getString("nome_coletor"));
                        u.setCod_tipo_coletor(r.getInt("cod_tipo_coletor"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Coletor getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Coletor WHERE nome_coletor ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Coletor u = new Coletor();
                        u.setCod_coletor(r.getInt("cod_coletor"));
                        u.setNome(r.getString("nome_coletor"));
                        u.setCod_tipo_coletor(r.getInt("cod_tipo_coletor"));
                        
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
