/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class RepasseDAO {
    Banco b;
    public RepasseDAO(){
        b=new Banco();
}
    public void fechar(){
        b.fechar();
    }
    public int inserir(Repasse u)
    {
        int r=0;
        java.util.Date dataUtil=new java.util.Date();
        java.sql.Date datasql= new java.sql.Date(dataUtil.getTime());
        r = b.inserir("INSERT INTO repasse(cod_repasse,cod_usuario, cod_coletor,data_repasse,cod_destinacao) VALUES (DEFAULT,"+u.getCod_usuario()+","+u.getCod_coletor()+",'"+datasql+"',"+u.getCod_destinacao()+")");
        
        return r;
    }
    
    public boolean alterar(Repasse u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE repasse SET cod_destinacao="+u.getCod_destinacao()+" WHERE cod_repasse="+u.getCod_repasse()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_repasse)
	{
		int r=0;
		r = b.excluir("DELETE FROM repasse WHERE cod_repasse ="+cod_repasse+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Repasse> pesquisar(String sql)
	{
		ArrayList<Repasse> vet = new ArrayList<Repasse>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Repasse u = new Repasse();
                        u.setCod_repasse(r.getInt("cod_repasse"));
                        u.setCod_usuario(r.getInt("cod_usuario"));
                        u.setCod_coletor(r.getInt("cod_coletor"));
                        u.setData_repasse(r.getDate("data_repasse"));
                        u.setCod_destinacao(r.getInt("cod_destinacao"));
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Repasse getByCod(int cod_repasse)
	{
		
		ResultSet r = b.pesquisar("SELECT * from repasse WHERE cod_repasse="+cod_repasse+"");
                try 
                {
                    if (r.next())
                    {
                        Repasse u = new Repasse();
                        u.setCod_repasse(r.getInt("cod_repasse"));
                        u.setCod_usuario(r.getInt("cod_usuario"));
                        u.setCod_coletor(r.getInt("cod_coletor"));
                        u.setData_repasse(r.getDate("data_repasse"));
                        u.setCod_destinacao(r.getInt("cod_destinacao"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
}
