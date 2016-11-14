package control;

public class ConfigBanco {
    private boolean scate=false,elephantsql=true;
    private String driver,dbURL,login,password;
    private int LOCAL=0,SCATE=1,ELSQL=2,AWS=3;
    
    private int BANCO=AWS;
    
	
    public ConfigBanco(){
        switch(BANCO){
            case 0:
                driver = "org.postgresql.Driver";
                dbURL = "jdbc:postgresql://localhost:5432/sgacervo";
                login = "postgres";
                password = "postgres";
                break;
            case 1:
                driver = "org.postgresql.Driver";
                dbURL = "jdbc:postgresql://177.101.27.66:5432/sgacervo";
                login = "roscoche";
                password = "museu4321";
                break;
            case 2:
                driver = "org.postgresql.Driver";
                dbURL = "jdbc:postgresql://babar.elephantsql.com:5432/wujatmmh";
                login = "wujatmmh";
                password = "XPJSfDxOX86cG4nx9iD_p9LVq9I04B-X";
                break;
            case 3:
                driver = "org.postgresql.Driver";
                dbURL = "jdbc:postgresql://52.67.40.48:5432/sgacervo";
                login = "roscoche";
                password = "sgacervo2016";
                break;
        }
                
            
    }

    public boolean isOnline() {
        return elephantsql;
    }

    public String getDriver() {
        return driver;
    }

    public String getDbURL() {
        return dbURL;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    

    
}
