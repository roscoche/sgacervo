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
public class DoacaoDAO {
    Banco b;
    public DoacaoDAO(){
        b=new Banco();
}
    public int inserir(Doacao u)
    {
        int r=0;
        java.util.Date dataUtil=new java.util.Date();
        java.sql.Date datasql= new java.sql.Date(dataUtil.getTime());
        r = b.inserir("INSERT INTO Doacao(cod_Doacao,cod_usuario, cod_doador,cod_evento_origem,data_doacao) VALUES (DEFAULT,"+u.getCod_usuario()+","+u.getCod_doador()+","+u.getCod_evento_origem()+",'"+datasql+"')");
        
        return r;
    }
    
    public boolean alterar(Doacao u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Doacao SET cod_evento_origem="+u.getCod_evento_origem()+" WHERE cod_Doacao="+u.getCod_doacao()+"");
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Doacao)
	{
		int r=0;
		r = b.excluir("DELETE FROM Doacao WHERE cod_Doacao ="+cod_Doacao+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Doacao> pesquisar(String sql)
	{
		ArrayList<Doacao> vet = new ArrayList<Doacao>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Doacao u = new Doacao();
                        u.setCod_doacao(r.getInt("cod_Doacao"));
                        u.setCod_usuario(r.getInt("cod_usuario"));
                        u.setCod_doador(r.getInt("cod_doador"));
                        u.setCod_evento_origem(r.getInt("cod_evento_origem"));
                        u.setData_doacao(r.getDate("data_Doacao"));
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Doacao getByCod(int cod_Doacao)
	{
		
		ResultSet r = b.pesquisar("SELECT * from doacao WHERE cod_doacao="+cod_Doacao+";");
                try 
                {
                    if (r.next())
                    {
                        Doacao u = new Doacao();
                        u.setCod_doacao(r.getInt("cod_doacao"));
                        u.setCod_usuario(r.getInt("cod_usuario"));
                        u.setCod_doador(r.getInt("cod_doador"));
                        u.setCod_evento_origem(r.getInt("cod_evento_origem"));
                        u.setData_doacao(r.getDate("data_doacao"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		          
                return null;
	}
        public void fechar(){
        b.fechar();
        }
}
