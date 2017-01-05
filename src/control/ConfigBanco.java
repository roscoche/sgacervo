package control;

public class ConfigBanco {
    private boolean scate=false,elephantsql=true;
    private String driver,dbURL,login,password;
    private int LOCAL=0,SCATE=1,ELSQL=2,AWS=3;
    
    private int BANCO=LOCAL;
    
	
    public ConfigBanco(){
        switch(BANCO){
            case 0:
                driver = "org.postgresql.Driver";
                dbURL = "jdbc:postgresql://localhost:5432/sgacervo";
                login = "postgres";
                password = "postgres";
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
