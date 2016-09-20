
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class InterfaceDAO 
{
    private control.Banco b;
    
    public InterfaceDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Interface u)
    {
        int r=0;
        System.out.println(u.getNome());
        r = b.inserir("INSERT INTO Interface(cod_Interface, nome_interface) VALUES (DEFAULT,'"+u.getNome()+"')");
        
        return r;
    }
    
    public boolean alterar(Interface u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Interface SET nome_Interface='"+u.getNome()+"' WHERE cod_Interface="+u.getCod_interface()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Interface)
	{
		int r=0;
		r = b.excluir("DELETE FROM Interface WHERE cod_Interface ="+cod_Interface+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Interface> pesquisar(String sql)
	{
		ArrayList<Interface> vet = new ArrayList<Interface>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Interface u = new Interface();
                        u.setCod_interface(r.getInt("cod_Interface"));
                        u.setNome(r.getString("nome_interface"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Interface getByCod(int cod_Interface)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Interface WHERE cod_Interface ="+cod_Interface+"");
                try 
                {
                    if (r.next())
                    {
                        Interface u = new Interface();
                        u.setCod_interface(r.getInt("cod_Interface"));
                        u.setNome(r.getString("nome_interface"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
        public Interface getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Interface WHERE nome_Interface ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Interface u = new Interface();
                        u.setCod_interface(r.getInt("cod_Interface"));
                        u.setNome(r.getString("nome_interface"));
                        
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
