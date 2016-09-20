
package control;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Banco
{
	private Connection conexao;
        private ConfigBanco cb=new ConfigBanco();
	private String driver = cb.getDriver();
	private String dbURL = cb.getDbURL();
	private String login = cb.getLogin();
        private String password = cb.getPassword();

    public Connection getConexao() {
        return conexao;
    }
        
    
    public Banco()
    {
        try
        {
    	    //ativa o driver
            Class.forName(driver);
            //realiza a conexão com o banco
            conexao = DriverManager.getConnection(dbURL, login, password);
            
    	}
    	catch(Exception e){
    		System.err.println("ERRO: FALHA CONEXAO BD.");
    		conexao = null;
    	}

    }


    /**
     * Consulta dados no banco.
     * @param sql consulta SQL
     * @return ResultSet resultante
     */
    public ResultSet pesquisar(String sql)
    {
    	Connection c = conexao;
        Statement st;
        ResultSet r = null;

        try
        {
            st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
            r = st.executeQuery(sql);
        }
       	catch(SQLException e)
        {
    		System.err.println("ERRO - QUERY: "+e);
            return null;

    	}
        
        return r;
    }

    
    /**
     * Atualiza dados no banco.
     * @param sql comando para atualização
     * @return quantidade de linhas atualizadas
     */
    public int atualizar(String sql)
    {
        Connection c = conexao;
        Statement st;
        int r=0;

        try
        {
            st = c.createStatement();
            r = st.executeUpdate(sql);
        }
       	catch(SQLException e)
        {
    		System.err.println("ERRO - UPDATE/DELETE: "+e);

    	}
        return r;

    }
    
    /**
     * Exclui dados no banco.
     * @param sql comando para exclusão
     * @return quantidade de linhas excluídas
     */
    public int excluir(String sql)
    {
        return this.atualizar(sql);
    }
    

    /**
     * Insere dados no banco.
     * @param sql consulta para inserção
     * @return o valor da chave do último dado inserido, se houver
     */
    public int max(String sql)
    {
        Connection c = conexao;
        Statement st;
        int r=0;

        try
        {
            Statement s2 = c.createStatement();
            s2.execute(sql);    
            ResultSet rs2 = s2.getResultSet(); // 
            while ( rs2.next() ){
                r = rs2.getInt(1);
            }    
            
        }
       	catch(SQLException e)
        {
    	    System.err.println("ERRO - MAX: "+e);

    	}
        return r;

    }
    
    public int inserir(String sql)
    {
        Connection c = conexao;
        Statement st;
        int r=0;

        try
        {
            st = c.createStatement();
            r = st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);            
            
            
            ResultSet resultSet = st.getGeneratedKeys(); 
            

            if ( resultSet != null && resultSet.next() ) 
            { 
                r=resultSet.getInt(1);
                  
            }
        }
       	catch(SQLException e)
        {
    	    System.err.println("ERRO - INSERT: "+e);

    	}
        return r;

    }
    
    
    
    public int inserirEndereco(String sql)
    {
        Connection c = conexao;
        Statement st;
        int r = 0;
        ResultSet resultSet = null;

        
            try {
                st = c.createStatement();
                resultSet = st.executeQuery(sql);
                r=resultSet.getInt("cod_end");
            } catch (SQLException ex) {
                Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return r;

    }

    /**
     * Fecha conexão com banco.
     */
    public void fechar()
    {
    	try
        {
    		conexao.close();
    	}
    	catch(Exception e)
        {	
            System.err.println("ERRO - CLOSE: "+e);
        }
    }


}


