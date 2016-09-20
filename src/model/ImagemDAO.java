package model;

import control.Banco;
import java.util.*;
import java.sql.*;

public class ImagemDAO 
{
    private control.Banco b;
    
    public ImagemDAO()
    {
        b = new Banco();
    }
    
    public int inserir(Imagem u)
    {
        int r=0;
        r = b.inserir("INSERT INTO imagem(cod_imagem, cod_item_acervo,link) VALUES (DEFAULT,"+u.getCod_item_acervo()+",'"+u.getLink()+"')");
        return r;
    }
    
    public boolean alterar(Imagem u)
    {
        int r=0;
        r = b.atualizar("UPDATE imagem SET link='"+u.getLink()+"' WHERE cod_imagem="+u.getCod_imagem()+"");
        if (r <= 0) return false;
        return true;
    }

	public boolean excluir(int cod_imagem)
	{
		int r=0;
		r = b.excluir("DELETE FROM imagem WHERE cod_imagem ="+cod_imagem+"");
		if (r <= 0) return false;
		return true;
		
	}
	
 
    public ArrayList<Imagem> pesquisar(String sql)
	{
		ArrayList<Imagem> vet = new ArrayList<Imagem>();
		
		ResultSet r = b.pesquisar(sql);
                try 
                {
                    while(r.next())
                    {
                        Imagem u = new Imagem();
                        
                        u.setCod_imagem(r.getInt("cod_imagem"));
                        u.setCod_item_acervo(r.getInt("cod_item_acervo"));
                        u.setLink(r.getString("link"));
                        
                        vet.add(u);

                    }
                } 
                catch (SQLException ex) {
                    System.err.println("Erro pesquisa: "+ex);
                }
		return vet;
	}
    
        public Imagem getByCod(int cod_Imagem)
	{
		
		ResultSet r = b.pesquisar("SELECT * from Imagem WHERE cod_Imagem ="+cod_Imagem+"");
                try 
                {
                    if (r.next())
                    {
                        Imagem u = new Imagem();
                        u.setCod_imagem(r.getInt("cod_imagem"));
                        u.setCod_item_acervo(r.getInt("cod_item_acervo"));
                        u.setLink(r.getString("link"));
                        
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
