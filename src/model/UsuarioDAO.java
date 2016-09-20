
package model;


import control.Banco;

import java.util.*;
import java.sql.*;


public class UsuarioDAO 
{
    private control.Banco b;
    
    public UsuarioDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Usuario u)
    {
        int r=0;
        
        r = b.inserir("INSERT INTO Usuario(cod_Usuario, nome_Usuario,senha_usuario,usuario_administrador,chave_encriptacao,email,registro_academico) VALUES (DEFAULT,'"+u.getNome_usuario()+"','"+u.getSenha_usuario()+"',"+u.isUsuario_administrador()+",'"+u.getChave_encriptacao()+"','"+u.getEmail()+"','"+u.getRegistro_academico()+"')");
        
        return r;
    }
    
    public boolean alterar(Usuario u)
    {
        int r=0;
        
        r = b.atualizar("UPDATE Usuario SET nome_Usuario='"+u.getNome_usuario()+"',usuario_administrador="+u.isUsuario_administrador()+",senha_usuario='"+u.getSenha_usuario()+"',email='"+u.getEmail()+"',registro_academico='"+u.getRegistro_academico()+"',chave_encriptacao='"+u.getChave_encriptacao()+"' WHERE cod_Usuario="+u.getCod_usuario()+"");
        
        
        if (r <= 0)
            return false;
        
        return true;
    }

	public boolean excluir(int cod_Usuario)
	{
		int r=0;
		r = b.excluir("DELETE FROM Usuario WHERE cod_Usuario ="+cod_Usuario+"");
		
		if (r <= 0)
			return false;
		
		return true;
		
	}
	
 
    public ArrayList<Usuario> pesquisar(String sql)
	{
		ArrayList<Usuario> vet = new ArrayList<Usuario>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Usuario u = new Usuario();
                        u.setCod_usuario(r.getInt("cod_Usuario"));
                        u.setNome_usuario(r.getString("nome_Usuario"));
                        u.setSenha_usuario(r.getString("senha_Usuario"));
                        u.setUsuario_administrador(r.getBoolean("usuario_administrador"));
                        u.setChave_encriptacao(r.getString("chave_encriptacao"));
                        u.setEmail(r.getString("email"));
                        u.setRegistro_academico(r.getString("registro_academico"));
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Usuario getByCod(int cod_Usuario)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Usuario WHERE cod_Usuario ="+cod_Usuario+"");
                try 
                {
                    if (r.next())
                    {
                        Usuario u = new Usuario();
                       u.setCod_usuario(r.getInt("cod_Usuario"));
                        u.setNome_usuario(r.getString("nome_Usuario"));
                        u.setSenha_usuario(r.getString("senha_Usuario"));
                        u.setUsuario_administrador(r.getBoolean("usuario_administrador"));
                        u.setChave_encriptacao(r.getString("chave_encriptacao"));
                        u.setEmail(r.getString("email"));
                        u.setRegistro_academico(r.getString("registro_academico"));
                        
                        return u;

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return null;
	}
	public Usuario getByNome(String nome)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Usuario WHERE nome_Usuario ='"+nome+"'");
                try 
                {
                    if (r.next())
                    {
                        Usuario u = new Usuario();
                       u.setCod_usuario(r.getInt("cod_Usuario"));
                        u.setNome_usuario(r.getString("nome_Usuario"));
                        u.setSenha_usuario(r.getString("senha_Usuario"));
                        u.setUsuario_administrador(r.getBoolean("usuario_administrador"));
                        u.setChave_encriptacao(r.getString("chave_encriptacao"));
                        u.setEmail(r.getString("email"));
                        u.setRegistro_academico(r.getString("registro_academico"));
                        
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
