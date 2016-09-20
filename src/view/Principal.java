package view;

import control.Banco;
import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.*;
import control.ConfigBanco;
import control.Encryptor;
import java.awt.HeadlessException;
import java.util.UUID;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class Principal extends javax.swing.JFrame {
    
    public Principal() {
        
        this.setIconImage(icone);
        this.setVisible(false);
        initComponents();    
        //this.setVisible(true);
        //this.setVisible(false);
        
        JDLogin.setIconImage(icone);
        JDLogin.setModal(true);
        
        LAguarde.setVisible(false);
        LCarregandoGif.setVisible(false);
        //JDLogin.remove(LAguarde);
        //JDLogin.remove(LCarregandoGif);
        JDLogin.pack();
        JDLogin.setLocationRelativeTo(null);
        atualizarCBUsuario();
        /*Graphics g=new Graphics();
        ImageIcon fundo = new ImageIcon(Principal.class.getResource("imagens/fundo.png"));
        g.create();
        g.drawImage(fundo.getImage(), fundo.getIconHeight(),fundo.getIconWidth(), this);
        JDLogin.paint(fundo.);*/
        JDLogin.setVisible(true);
        
    }

    @SuppressWarnings("unchecked")
    
    private void atualizarCamposCadastrarDoacao(){
        campoUsuarioDoacao.setText(nomeUsuario);
        java.util.Date data=new java.util.Date();
        campoDataDoacao.setText(data.getDate()+"/"+(data.getMonth()+1)+"/"+(1900+data.getYear()));
        Banco b=new Banco();
        int doacao=b.max("SELECT MAX(cod_doacao) from doacao;");
        b.fechar();
        doacao++;
        campoDoacao.setText(""+doacao);
    }
    private void atualizarCamposCadastrarItemDoacao(Boolean tudo){
        Banco b=new Banco();
        int itens=b.max("SELECT MAX(cod_item_doacao) from item_doacao;");
        b.fechar();
        
        itens++;
        campoUsuarioItemDoacao.setText(nomeUsuario);
        campoItemDoacao.setText(""+itens);
        campoQuantidadeItemDoacao.setText("");
        if(tudo) {
                campoDoacaoItemDoacao.setText("");
                campoDoadorItemDoacao.setText("");
            }
        else{
                Doador doador;
                DoadorDAO daodoador;
                Doacao doacao;
                DoacaoDAO daodoacao=new DoacaoDAO();
                
                doacao=daodoacao.getByCod(Integer.parseInt(campoDoacaoItemDoacao.getText()));
                daodoacao.fechar();
                if(doacao==null) JOptionPane.showMessageDialog(CadastrarItemDoacao,"Doação Inexistente","Erro",0);
                else {
                    daodoador=new DoadorDAO();
                    doador=daodoador.getByCod(doacao.getCod_doador());
                    daodoador.fechar();
                    campoDoadorItemDoacao.setText(doador.getNome_doador());
                }
        }
    }
    private void atualizarCamposCadastrarDoador(){
        Banco b=new Banco();
        campoCodigoDoador.setText(""+(b.max("SELECT MAX(cod_doador) from doador;")+1));
        b.fechar();
        campoNomeDoador.setText("");
    }
    private void atualizarCamposCadastrarEventoOrigem(){
        Banco b=new Banco();
        campoCodigoEventoOrigem.setText(""+(b.max("SELECT MAX(cod_evento_origem) from evento_origem;")+1));
        b.fechar();
        campoNomeEventoOrigem.setText("");
    }
    private void atualizarCamposDoacao(){
        atualizarCamposCadastrarDoacao();
        atualizarCamposCadastrarItemDoacao(true);
        atualizarCamposCadastrarDoador();
        atualizarCamposCadastrarEventoOrigem();
    }
    private void atualizarCamposCadastrarRepasse(){
        Banco b=new Banco();
        int max=b.max("SELECT MAX(cod_repasse) from repasse");
        b.fechar();
        max++;
        campoRepasse.setText(""+max);
        campoUsuarioRepasse.setText(nomeUsuario);
        java.util.Date data=new java.util.Date();
        campoDataRepasse.setText(data.getDate()+"/"+(data.getMonth()+1)+"/"+(1900+data.getYear()));
    }
    private void atualizarCamposCadastrarDestinacao(){
        Banco b=new Banco();
        int max=b.max("SELECT MAX(cod_destinacao) from destinacao");
        b.fechar();
        max++;
        campoCodigoDestinacao.setText(""+max);
        campoNomeDestinacao.setText("");
    }
    private void atualizarCamposCadastrarItemRepasse(Boolean tudo){
        Banco b=new Banco();
        int itens=b.max("SELECT MAX(cod_item_repasse) from item_repasse;");
        b.fechar();
        itens++;
        campoItemRepasse.setText(""+itens);
        campoUsuarioItemRepasse.setText(nomeUsuario);
        if(tudo) {
                campoRepasseItemRepasse.setText("");
                campoColetorItemRepasse.setText("");
            }
        else{   
                Coletor coletor;
                ColetorDAO daocoletor;
                Repasse Repasse;
                RepasseDAO daoRepasse=new RepasseDAO();
                Repasse=daoRepasse.getByCod(Integer.parseInt(campoRepasseItemRepasse.getText()));
                daoRepasse.fechar();
                if(Repasse==null) JOptionPane.showMessageDialog(CadastrarItemRepasse,"Repasse Inexistente.","Erro",0);
                else {
                    daocoletor=new ColetorDAO();
                    coletor=daocoletor.getByCod(Repasse.getCod_coletor());
                    campoColetorItemRepasse.setText(coletor.getNome());
                }
        }
        campoQuantidadeItemRepasse.setText("");
}
    private void atualizarCamposCadastrarColetor(){
        Banco b=new Banco();
        campoCodigoColetor.setText(""+(b.max("SELECT MAX(cod_coletor) from coletor;")+1));
        b.fechar();
        campoNomeColetor.setText("");
    }
    private void atualizarCamposCadastrarTipoColetor(){
        Banco b=new Banco();
        campoCodigoTipoColetor.setText(""+(b.max("SELECT MAX(cod_tipo_coletor) from tipo_coletor;")+1));
        b.fechar();
        campoTipoColetor.setText("");
    }
    private void atualizarCamposRepasse(){
        atualizarCamposCadastrarRepasse();
        atualizarCamposCadastrarDestinacao();
        atualizarCamposCadastrarItemRepasse(true);
        atualizarCamposCadastrarColetor();
        atualizarCamposCadastrarTipoColetor();
    }
    private void atualizarCamposCadastrarItemAcervo(){
           Banco b=new Banco();
           int max=b.max("SELECT MAX(cod_item_acervo) from item_acervo");
           b.fechar();
           max++;
           campoCodigoItemAcervo.setText(""+max);
           campoUsuarioItemAcervo.setText(nomeUsuario);
           java.util.Date data=new java.util.Date();
           campoDataItemAcervo.setText(data.getDate()+"/"+(data.getMonth()+1)+"/"+(1900+data.getYear()));
           campoDescricaoItemAcervo.setText("");
           campoAnoItemAcervo.setText("");
           CBFunciona.setSelected(false);
           campoCapacidadeItemAcervo.setText("");
           campoLink.setText("");
           LFotoAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png"))); // NOI18N
    }
    private void atualizarCamposAcervo(){
           atualizarCamposCadastrarItemAcervo();
           atualizarCamposCadastrarImagem();
           atualizarCamposCadastrarContainer();
           
    }
    private void atualizarCamposCadastrarUsuario(){
        Banco b=new Banco();
        campoCodigoNovoUsuario.setText(""+(b.max("Select MAX(cod_usuario) from usuario")+1));
        b.fechar();
        campoNomeNovoUsuario.setText("");
        campoSenhaCadastrarUsuario.setText("");
        CBAdministrador.setSelected(false);
        
    }
    private void atualizarCamposUsuario(){
        atualizarCamposCadastrarUsuario();
    }
    private void atualizarCamposAlterarUsuario(){
        UsuarioDAO daou=new UsuarioDAO();
        Usuario u=daou.getByCod(codigoUsuario);
        daou.fechar();
        
        campoCodigoAlterarUsuario.setText(""+codigoUsuario);
        campoSenhaAtualAlterarUsuario.setText("");
        campoNovaSenhaAlterarUsuario.setText("");
        campoRepetirSenhaAlterarUsuario.setText("");
        campoNomeAlterarUsuario.setText(nomeUsuario);
        campoEmailAlterarUsuario.setText(u.getEmail());
        campoRegistroAcademicoAlterarUsuario.setText(u.getRegistro_academico());
        campoAdministradorAlterarUsuario.setSelected(administrador);
        
    }
    private void atualizarCamposAbaUsuario(){
        atualizarCamposAlterarUsuario();
    }
    private void atualizarCamposCadastrarTipoItem(){
        Banco b=new Banco();
        campoCodigoTipoItem.setText(""+(b.max("Select max(cod_tipo) from tipo_item;")+1));
        b.fechar();
        campoTipoItem.setText("");
    }
    private void atualizarCamposCadastrarMarca(){
        Banco b=new Banco();
        campoCodigoMarca.setText(""+(b.max("Select max(cod_marca) from marca;")+1));
        b.fechar();
        campoNomeMarca.setText("");
    }
    private void atualizarCamposCadastrarModelo(){
        Banco b=new Banco();
        campoCodigoModelo.setText(""+(b.max("Select max(cod_modelo) from modelo;")+1));
        b.fechar();
        campoNomeModelo.setText("");
    }
    private void atualizarCamposCadastrarImagem(){
        Banco b=new Banco();
        campoCodImagemCadastrarImagem.setText(""+(b.max("Select max(cod_imagem) from imagem;")+1));
        b.fechar();
        campoUsuarioCadastrarImagem.setText(nomeUsuario);
        campoItemAcervoCadastrarImagem.setText("");
        campoLink.setText("");
    }
    private void atualizarCamposCadastrarContainer(){
        Banco b=new Banco();
        campoCodContainerCadastrarContainer.setText(""+(b.max("Select max(cod_container) from container;")+1));
        b.fechar();
        campoUsuarioCadastrarContainer.setText(nomeUsuario);
        campoLocalizacaoCadastrarContainer.setText("");
        
    }
    private void atualizarCamposCadastrarInterface(){
        Banco b=new Banco();
        campoCodigoInterface.setText(""+(b.max("Select max(cod_interface) from interface;")+1));
        b.fechar();
        campoNomeInterface.setText("");
    }
    private void atualizarCamposCadastrarTecnologia(){
        Banco b=new Banco();
        campoCodigoTecnologia.setText(""+(b.max("Select max(cod_tecnologia) from tecnologia;")+1));
        b.fechar();
        campoNomeTecnologia.setText("");
    }
 
    private void atualizarCamposCadastrarTipoContainer(){
           Banco b=new Banco();
           campoCodigoTipoContainer.setText(""+(b.max("select max(cod_tipo_container) from tipo_container;")+1));
           b.fechar();
           campoTipoContainer.setText("");
    }
    
    private void atualizarCampos(){
        //Atualiza campos referentes à Doação
        atualizarCamposDoacao();
        //Atualiza campos referentes à Repasse
        atualizarCamposRepasse();
        //Atualiza campos referentes à Acervo
        atualizarCamposAcervo();
        //Atualiza campos referentes à Usuário
        atualizarCamposUsuario();
        //Atualiza campos referentes à Aba Usuário
        atualizarCamposAbaUsuario();
    }
    private void atualizarTBDoacao(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="SELECT * from doacao_detalhado";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Doacao.");
             
         }
         TDoacao.setModel(DbUtils.resultSetToTableModel(rs));
    
    }
    private void atualizarTBItemDoacao(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="select * from item_doacao_detalhado;";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Item Doacao.");
             
         }
         TItemDoacao.setModel(DbUtils.resultSetToTableModel(rs));
     }
    private void atualizarTBEstoque(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="select * from estoque_detalhado";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Estoque.");
             
         }
         TEstoque.setModel(DbUtils.resultSetToTableModel(rs));
    }
    private void atualizarTBImagem(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="select * from imagem_detalhado";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Imagem.");
             
         }
         TImagem.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
     private void atualizarTBContainer(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="select * from container_detalhado";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Container.");
             
         }
         TContainer.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void atualizarTBDoador(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="select * from doador_detalhado";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Doador.");
             
         }
         TDoador.setModel(DbUtils.resultSetToTableModel(rs));
        
    }
    private void atualizarTBRepasse(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="SELECT * from repasse_detalhado;";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Repasse.");
             
         }
         TRepasse.setModel(DbUtils.resultSetToTableModel(rs));
     }
    private void atualizarTBItemRepasse(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="select * from item_repasse_detalhado";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Item Repasse.");
             
         }
         TItemRepasse.setModel(DbUtils.resultSetToTableModel(rs));
     
     }
    private void atualizarTBColetor(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="SELECT * from coletor_detalhado;";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Coletor.");
             
         }
         TColetor.setModel(DbUtils.resultSetToTableModel(rs));
     
     }
    private void atualizarTBAcervo(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="SELECT * from acervo_detalhado;";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Acervo.");
             
         }
         
         
         TAcervo.setModel(DbUtils.resultSetToTableModel(rs));
     
     }
    private void atualizarTBUsuario(){
        Connection con;
         ResultSet rs=null;
         PreparedStatement ps;
         String statement="SELECT * from usuario_detalhado;";
         try{
             con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
             ps=con.prepareCall(statement);
            rs=ps.executeQuery();
            con.close();
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para Usuario.");
             
         }
        
         TUsuario.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    private void atualizarTB(){
        //Atualizar Tabelas Relativas à Doação
        atualizarTBDoacao();
        atualizarTBItemDoacao();
        atualizarTBDoador();
        atualizarTBEstoque();
       
        
        //Atualizar Tabelas Relativas à Repasse
        atualizarTBRepasse();
        atualizarTBItemRepasse();
        atualizarTBColetor();
        
        //Atualizar Tabelas Relativas à Acervo
        atualizarTBAcervo();
        atualizarTBImagem();
        atualizarTBContainer();
        
        atualizarTBUsuario();
    }
    
    private void atualizarCBDoador(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from doador;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_doador"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Doador.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBDoadorItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBDoadorDoacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    }
    private void atualizarCBEventoOrigem(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from evento_origem;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_evento_origem"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Evento Origem.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBEventoOrigem.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBEventoOrigemAlterarDoacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    }
    private void atualizarCBDestinacao(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from destinacao;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_destinacao"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Destinacao.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBDestinacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBDestinacaoAlterarRepasse.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    
    }
    
    private void atualizarCBColetor(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from coletor;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_coletor"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Coletor.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    }
    private void atualizarCBTipoColetor(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from tipo_coletor;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_tipo_coletor"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tipo Coletor.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBTipoColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTipoColetorAlterarColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    }
    
    private void atualizarCBTipoItem(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from tipo_item;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_tipo"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tipo Item.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         
         CBTipoItemRepasse.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTipoItemDoacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTipoItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTipoAlterarItemDoacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTipoAlterarItemRepasse.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTipoAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    }
   
    private void atualizarCBMarca(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from marca;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_marca"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Marca.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBMarca.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBMarcaAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    
    }
    private void atualizarCBModelo(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from modelo;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_modelo"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Modelo.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBModelo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBModeloAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }
    private void atualizarCBInterface(){
       Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from interface;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_interface"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Interface.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBInterface.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBInterfaceAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }
    private void atualizarCBTecnologia(){
       Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from tecnologia;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_tecnologia"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tecnologia.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBTecnologia.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTecnologiaAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }
    
    private void atualizarCBTipoContainer(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from tipo_container;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_tipo_container"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tipo Container.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         
         CBTipoContainerCadastrarContainer.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         CBTipoContainerAlterarContainer.setModel(new javax.swing.DefaultComboBoxModel(tipos));
         
    }
    private void atualizarCBUsuario(){
        Connection con;
         ResultSet rs;
         PreparedStatement ps;
         String statement="SELECT * from usuario;";
         ArrayList list = new ArrayList();  
         try{
                con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps=con.prepareCall(statement);
                rs=ps.executeQuery();
                con.close();
                
                while(rs.next()){
                    list.add(rs.getString("nome_usuario"));
                }
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Usuario.");
         }
         Object[] objectList=list.toArray();
         String[] tipos=Arrays.copyOf(objectList,objectList.length,String[].class);
         CBUsuarioLogin.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }
    private void atualizarCB(){
        //Atualizar ComboBox Relativos à Doacao
        atualizarCBDoador();
        atualizarCBEventoOrigem();
        //Atualizar ComboBox Relativos à Repasse
        atualizarCBColetor();
        atualizarCBTipoColetor();
        atualizarCBDestinacao();
        //Atualizar ComboBox Relativos à Acervo
        atualizarCBTipoItem();
        atualizarCBMarca();
        atualizarCBModelo();
        atualizarCBInterface();
        atualizarCBTecnologia();
        atualizarCBTipoContainer();
        //Atualizar ComboBox relativos à Usuário
        atualizarCBUsuario();
    }
    
    private void cancelarLogin(){
        if(!logado)
        {   this.setVisible(false);
            this.dispose();
            JDLogin.dispose();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JDLogin = new javax.swing.JDialog();
        Header = new javax.swing.JLabel();
        LUsuárioLogin = new javax.swing.JLabel();
        LSenhaLogin = new javax.swing.JLabel();
        campoSenhaLogin = new javax.swing.JPasswordField();
        BLogar = new javax.swing.JButton();
        BCancelarLogin = new javax.swing.JButton();
        BEsqueciSenha = new javax.swing.JButton();
        CBUsuarioLogin = new javax.swing.JComboBox();
        LAguarde = new javax.swing.JLabel();
        LCarregandoGif = new javax.swing.JLabel();
        JDAlterarColetor = new javax.swing.JDialog();
        LCodigo_ColetorAlterarColetor = new javax.swing.JLabel();
        LNomeColetorAlterarColetor = new javax.swing.JLabel();
        LTipoColetorAlterarColetor = new javax.swing.JLabel();
        LCamposAlterarColetor = new javax.swing.JLabel();
        campoCodigoColetorAlterarColetor = new javax.swing.JTextField();
        campoNomeColetorAlterarColetor = new javax.swing.JTextField();
        CBTipoColetorAlterarColetor = new javax.swing.JComboBox();
        BAlterarColetor = new javax.swing.JButton();
        JDAlterarContainer = new javax.swing.JDialog();
        LCodContainerAlterarContainer = new javax.swing.JLabel();
        LLocalizacaoAlterarContainer = new javax.swing.JLabel();
        LTipoContainerAlterarContainer = new javax.swing.JLabel();
        LCamposAlterarContainer = new javax.swing.JLabel();
        campoCodContainerAlterarContainer = new javax.swing.JTextField();
        campoLocalizacaoAlterarContainer = new javax.swing.JTextField();
        BAlterarContainer = new javax.swing.JButton();
        CBTipoContainerAlterarContainer = new javax.swing.JComboBox();
        JDAlterarDoacao = new javax.swing.JDialog();
        LCodigo_doacaoAlterarDoacao = new javax.swing.JLabel();
        LUsuárioAlterarDoacao = new javax.swing.JLabel();
        LDataAlterarDoacao = new javax.swing.JLabel();
        LOrigemAlterarDoacao = new javax.swing.JLabel();
        LDoadorAlterarDoacao = new javax.swing.JLabel();
        LCamposAlterarDoacao = new javax.swing.JLabel();
        campoDoacaoAlterarDoacao = new javax.swing.JTextField();
        campoUsuarioAlterarDoacao = new javax.swing.JTextField();
        campoDataAlterarDoacao = new javax.swing.JFormattedTextField();
        campoDoadorAlterarDoacao = new javax.swing.JTextField();
        CBEventoOrigemAlterarDoacao = new javax.swing.JComboBox();
        BAlterarDoacao = new javax.swing.JButton();
        JDAlterarDoador = new javax.swing.JDialog();
        LCodigo_doadorAlterarDoador = new javax.swing.JLabel();
        LNomeDoadorAlterarDoador = new javax.swing.JLabel();
        LCamposAlterarItemDoacao1 = new javax.swing.JLabel();
        campoCodigoDoadorAlterarDoador = new javax.swing.JTextField();
        campoNomeDoadorAlterarDoador = new javax.swing.JTextField();
        BAlterarDoador = new javax.swing.JButton();
        JDAlterarImagem = new javax.swing.JDialog();
        LCodImagemAlterarImagem = new javax.swing.JLabel();
        LCodItemAcervoAlterarImagem = new javax.swing.JLabel();
        LLinkAlterarImagem = new javax.swing.JLabel();
        LCamposAlterarImagem = new javax.swing.JLabel();
        LImagemAlterarImagem = new javax.swing.JLabel();
        campoCodImagemAlterarImagem = new javax.swing.JTextField();
        campoCodItemAcervoAlterarImagem = new javax.swing.JTextField();
        campoLinkAlterarImagem = new javax.swing.JTextField();
        CheckImagemAlterarImagem = new javax.swing.JButton();
        BAlterarImagem = new javax.swing.JButton();
        JDAlterarItemAcervo = new javax.swing.JDialog();
        LCodigo_ItemAlterarItemAcervo = new javax.swing.JLabel();
        LUsuárioAlterarItemAcervo = new javax.swing.JLabel();
        LDoadorAlterarItemAcervo = new javax.swing.JLabel();
        LDataAlterarItemAcervo = new javax.swing.JLabel();
        LTipoAlterarItemAcervo = new javax.swing.JLabel();
        LMarcaAlterarItemAcervo = new javax.swing.JLabel();
        LModeloAlterarItemAcervo = new javax.swing.JLabel();
        LInterfaceAlterarItemAcervo = new javax.swing.JLabel();
        LTecnologiaAlterarItemAcervo = new javax.swing.JLabel();
        LCapacidadeAlterarItemAcervo = new javax.swing.JLabel();
        LAnoAlterarItemAcervo = new javax.swing.JLabel();
        LContainerAlterarItemAcervo = new javax.swing.JLabel();
        LDescricaoAlterarItemAcervo = new javax.swing.JLabel();
        LFuncionaAlterarItemAcervo = new javax.swing.JLabel();
        LCamposAlterarItemAcervo = new javax.swing.JLabel();
        campoItemAcervoAlterarItemAcervo = new javax.swing.JTextField();
        campoUsuarioAlterarItemAcervo = new javax.swing.JTextField();
        campoDoadorAlterarItemAcervo = new javax.swing.JTextField();
        campoDataAlterarItemAcervo = new javax.swing.JFormattedTextField();
        CBTipoAlterarItemAcervo = new javax.swing.JComboBox();
        CBMarcaAlterarItemAcervo = new javax.swing.JComboBox();
        CBModeloAlterarItemAcervo = new javax.swing.JComboBox();
        CBInterfaceAlterarItemAcervo = new javax.swing.JComboBox();
        CBTecnologiaAlterarItemAcervo = new javax.swing.JComboBox();
        campoCapacidadeAlterarItemAcervo = new javax.swing.JTextField();
        campoAnoAlterarItemAcervo = new javax.swing.JTextField();
        campoContainerAlterarItemAcervo = new javax.swing.JTextField();
        SPDescricaoAlterarItemAcervo = new javax.swing.JScrollPane();
        TADescricaoAlterarItemAcervo = new javax.swing.JTextArea();
        CheckFuncionaAlterarItemAcervo = new javax.swing.JCheckBox();
        BAlterarItemAcervo = new javax.swing.JButton();
        JDAlterarItemDoacao = new javax.swing.JDialog();
        LCodigo_doacaoAlterarDoacao1 = new javax.swing.JLabel();
        LUsuárioAlterarDoacao1 = new javax.swing.JLabel();
        LDoadorAlterarDoacao1 = new javax.swing.JLabel();
        LDataAlterarDoacao1 = new javax.swing.JLabel();
        LOrigemAlterarDoacao1 = new javax.swing.JLabel();
        LCamposAlterarItemDoacao = new javax.swing.JLabel();
        campoItemDoacaoAlterarItemDoacao = new javax.swing.JTextField();
        campoUsuarioAlterarItemDoacao = new javax.swing.JTextField();
        campoDoadorAlterarItemDoacao = new javax.swing.JTextField();
        CBTipoAlterarItemDoacao = new javax.swing.JComboBox();
        campoQuantidadeAlterarItemDoacao = new javax.swing.JTextField();
        BAlterarItemDoacao = new javax.swing.JButton();
        JDAlterarItemRepasse = new javax.swing.JDialog();
        LCodigo_RepasseAlterarItemRepasse = new javax.swing.JLabel();
        LUsuárioAlterarItemRepasse = new javax.swing.JLabel();
        LDataAlterarItemRepasse = new javax.swing.JLabel();
        LOrigemAlterarItemRepasse = new javax.swing.JLabel();
        LDoadorAlterarItemRepasse = new javax.swing.JLabel();
        LCamposAlterarItemRepasse = new javax.swing.JLabel();
        campoItemRepasseAlterarItemRepasse = new javax.swing.JTextField();
        campoUsuarioAlterarItemRepasse = new javax.swing.JTextField();
        campoColetorAlterarItemRepasse = new javax.swing.JTextField();
        CBTipoAlterarItemRepasse = new javax.swing.JComboBox();
        campoQuantidadeAlterarItemRepasse = new javax.swing.JTextField();
        BAlterarItemRepasse = new javax.swing.JButton();
        JDAlterarRepasse = new javax.swing.JDialog();
        LCodigo_doacaoAlterarRepasse = new javax.swing.JLabel();
        LUsuárioAlterarRepasse = new javax.swing.JLabel();
        LDataAlterarRepasse = new javax.swing.JLabel();
        LOrigemAlterarRepasse = new javax.swing.JLabel();
        LDoadorAlterarRepasse = new javax.swing.JLabel();
        LCamposAlterarRepasse = new javax.swing.JLabel();
        campoRepasseAlterarRepasse = new javax.swing.JTextField();
        campoUsuarioAlterarRepasse = new javax.swing.JTextField();
        campoDataAlterarRepasse = new javax.swing.JFormattedTextField();
        campoColetorAlterarRepasse = new javax.swing.JTextField();
        CBDestinacaoAlterarRepasse = new javax.swing.JComboBox();
        BAlterarRepasse = new javax.swing.JButton();
        JDAlterarUsuario = new javax.swing.JDialog();
        LCodigo_UsuarioAlterarUsuario = new javax.swing.JLabel();
        LNome_UsuarioAlterarUsuario = new javax.swing.JLabel();
        LRegistroAcademicoAlterarUsuarioJD = new javax.swing.JLabel();
        LEmailAlterarUsuarioJD = new javax.swing.JLabel();
        LNovaSenhaAlterarUsuarioJD = new javax.swing.JLabel();
        LRepetirNovaSenhaAlterarUsuarioJD = new javax.swing.JLabel();
        LPermissaoAlterarUsuarioJD = new javax.swing.JLabel();
        LCamposAlterarItemDoacao2 = new javax.swing.JLabel();
        campoCodigoUsuarioAlterarUsuarioJD = new javax.swing.JTextField();
        campoNomeUsuarioAlterarUsuarioJD = new javax.swing.JTextField();
        campoRegistroAcademicoAlterarUsuarioJD = new javax.swing.JTextField();
        campoEmailAlterarUsuarioJD = new javax.swing.JTextField();
        campoNovaSenhaAlterarUsuarioJD = new javax.swing.JPasswordField();
        campoRepetirNovaSenhaAlterarUsuarioJD = new javax.swing.JPasswordField();
        CheckAdministradorAlterarUsuarioJD = new javax.swing.JCheckBox();
        BAlterarUsuarioJD = new javax.swing.JButton();
        JDCadastrarTipoContainer = new javax.swing.JDialog();
        LCodTipoContainer = new javax.swing.JLabel();
        LTipoTipoContainer = new javax.swing.JLabel();
        LPO11 = new javax.swing.JLabel();
        campoCodigoTipoContainer = new javax.swing.JTextField();
        campoTipoContainer = new javax.swing.JTextField();
        BCadastrarTipoContainer = new javax.swing.JButton();
        JDCadastrarTipoItem = new javax.swing.JDialog();
        LCodTipo = new javax.swing.JLabel();
        LTipo = new javax.swing.JLabel();
        LPO3 = new javax.swing.JLabel();
        campoCodigoTipoItem = new javax.swing.JTextField();
        campoTipoItem = new javax.swing.JTextField();
        BCadastrarTipo = new javax.swing.JButton();
        JDCadastrarMarca = new javax.swing.JDialog();
        LCodMarca = new javax.swing.JLabel();
        LNomeMarca = new javax.swing.JLabel();
        LPO4 = new javax.swing.JLabel();
        campoCodigoMarca = new javax.swing.JTextField();
        campoNomeMarca = new javax.swing.JTextField();
        BCadastrarMarca = new javax.swing.JButton();
        JDCadastrarModelo = new javax.swing.JDialog();
        LCodModelo = new javax.swing.JLabel();
        LNomeModelo = new javax.swing.JLabel();
        LPO5 = new javax.swing.JLabel();
        campoCodigoModelo = new javax.swing.JTextField();
        campoNomeModelo = new javax.swing.JTextField();
        BCadastrarModelo = new javax.swing.JButton();
        JDCadastrarInterface = new javax.swing.JDialog();
        LCodInterface = new javax.swing.JLabel();
        LNomeInterface = new javax.swing.JLabel();
        LPO6 = new javax.swing.JLabel();
        campoCodigoInterface = new javax.swing.JTextField();
        campoNomeInterface = new javax.swing.JTextField();
        BCadastrarInterface = new javax.swing.JButton();
        JDCadastrarTecnologia = new javax.swing.JDialog();
        LCodTecnologia = new javax.swing.JLabel();
        LNomeTecnologia = new javax.swing.JLabel();
        LPO7 = new javax.swing.JLabel();
        campoCodigoTecnologia = new javax.swing.JTextField();
        campoNomeTecnologia = new javax.swing.JTextField();
        BCadastrarTecnologia = new javax.swing.JButton();
        JDCadastrarColetor = new javax.swing.JDialog();
        LNomeColetor = new javax.swing.JLabel();
        LTipoColetor = new javax.swing.JLabel();
        LCamposObrigatorios5 = new javax.swing.JLabel();
        campoNomeColetor = new javax.swing.JTextField();
        LCodNovoColetor = new javax.swing.JLabel();
        campoCodigoColetor = new javax.swing.JTextField();
        BCadastrarColetor = new javax.swing.JButton();
        CBTipoColetor = new javax.swing.JComboBox();
        BNovoTipoColetor = new javax.swing.JButton();
        JDCadastrarDoador = new javax.swing.JDialog();
        LCodDoador = new javax.swing.JLabel();
        campoCodigoDoador = new javax.swing.JTextField();
        LNomeDoador = new javax.swing.JLabel();
        campoNomeDoador = new javax.swing.JTextField();
        BCadastrarDoador = new javax.swing.JButton();
        LCamposObrigatorios2 = new javax.swing.JLabel();
        JDExcluirColetor = new javax.swing.JDialog();
        LCodigo_ColetorExcluirColetor = new javax.swing.JLabel();
        campoColetorExcluirColetor = new javax.swing.JTextField();
        BConfirmarExcluirColetor = new javax.swing.JButton();
        BCancelarExcluirColetor = new javax.swing.JButton();
        LCautionExcluirColetor = new javax.swing.JLabel();
        LConfirmaExcluirColetor = new javax.swing.JLabel();
        JDExcluirContainer = new javax.swing.JDialog();
        LCodigo_ContainerExcluirContainer = new javax.swing.JLabel();
        campoContainerExcluirContainer = new javax.swing.JTextField();
        BConfirmarExcluirContainer = new javax.swing.JButton();
        BCancelarExcluirContainer = new javax.swing.JButton();
        LCautionExcluirContainer = new javax.swing.JLabel();
        LConfirmaExcluirContainer = new javax.swing.JLabel();
        JDExcluirDoacao = new javax.swing.JDialog();
        LCodigo_doacaoExcluirDoacao = new javax.swing.JLabel();
        campoDoacaoExcluirDoacao = new javax.swing.JTextField();
        BConfirmarExcluirDoacao = new javax.swing.JButton();
        BCancelarExcluirDoacao = new javax.swing.JButton();
        LCautionExcluirDoacao = new javax.swing.JLabel();
        LConfirmaExcluirDoacao = new javax.swing.JLabel();
        JDExcluirDoador = new javax.swing.JDialog();
        LCodigo_doadorExcluirDoador = new javax.swing.JLabel();
        campoDoadorExcluirDoador = new javax.swing.JTextField();
        BConfirmarExcluirDoador = new javax.swing.JButton();
        BCancelarExcluirDoador = new javax.swing.JButton();
        LCautionExcluirDoador = new javax.swing.JLabel();
        LConfirmaExcluirDoador = new javax.swing.JLabel();
        JDExcluirImagem = new javax.swing.JDialog();
        LCodigo_ImagemExcluirImagem = new javax.swing.JLabel();
        campoImagemExcluirImagem = new javax.swing.JTextField();
        BConfirmarExcluirImagem = new javax.swing.JButton();
        BCancelarExcluirImagem = new javax.swing.JButton();
        LCautionExcluirImagem = new javax.swing.JLabel();
        LConfirmaExcluirImagem = new javax.swing.JLabel();
        JDExcluirItemAcervo = new javax.swing.JDialog();
        LCodigo_ItemAcervoExcluirItemAcervo = new javax.swing.JLabel();
        campoItemAcervoExcluirItemAcervo = new javax.swing.JTextField();
        BConfirmarExcluirItemAcervo = new javax.swing.JButton();
        BCancelarExcluirItemAcervo = new javax.swing.JButton();
        LCautionExcluirItemAcervo = new javax.swing.JLabel();
        LConfirmaExcluirItemAcervo = new javax.swing.JLabel();
        JDExcluirItemDoacao = new javax.swing.JDialog();
        LCodigo_ItemDoacaoExcluirItemDoacao = new javax.swing.JLabel();
        campoItemDoacaoExcluirItemDoacao = new javax.swing.JTextField();
        BConfirmarExcluirItemDoacao = new javax.swing.JButton();
        BCancelarExcluirItemDoacao = new javax.swing.JButton();
        LCautionExcluirItemDoacao = new javax.swing.JLabel();
        LConfirmaExcluirItemDoacao = new javax.swing.JLabel();
        JDExcluirItemRepasse = new javax.swing.JDialog();
        LCodigo_ItemRepasseExcluirItemRepasse = new javax.swing.JLabel();
        campoItemRepasseExcluirItemRepasse = new javax.swing.JTextField();
        BConfirmarExcluirItemRepasse = new javax.swing.JButton();
        BCancelarExcluirItemRepasse = new javax.swing.JButton();
        LCautionExcluirItemRepasse = new javax.swing.JLabel();
        LConfirmaExcluirItemRepasse = new javax.swing.JLabel();
        JDExcluirRepasse = new javax.swing.JDialog();
        LCodigo_RepasseExcluirRepasse = new javax.swing.JLabel();
        campoRepasseExcluirRepasse = new javax.swing.JTextField();
        BConfirmarExcluirRepasse = new javax.swing.JButton();
        BCancelarExcluirRepasse = new javax.swing.JButton();
        LCautionExcluirRepasse = new javax.swing.JLabel();
        LConfirmaExcluirRepasse = new javax.swing.JLabel();
        JDExcluirUsuario = new javax.swing.JDialog();
        LCodigo_UsuarioExcluirUsuario = new javax.swing.JLabel();
        campoUsuarioExcluirUsuario = new javax.swing.JTextField();
        LCautionExcluirUsuario = new javax.swing.JLabel();
        LConfirmaExcluirUsuario = new javax.swing.JLabel();
        BConfirmarExcluirUsuario = new javax.swing.JButton();
        BCancelarExcluirUsuario = new javax.swing.JButton();
        JDCadastrarEventoOrigem = new javax.swing.JDialog();
        LCodEO = new javax.swing.JLabel();
        LNomeEO = new javax.swing.JLabel();
        LPO8 = new javax.swing.JLabel();
        campoCodigoEventoOrigem = new javax.swing.JTextField();
        campoNomeEventoOrigem = new javax.swing.JTextField();
        BCadastrarEventoOrigem = new javax.swing.JButton();
        JDCadastrarDestinacao = new javax.swing.JDialog();
        LCodDestinacao = new javax.swing.JLabel();
        LNomeDestinacao = new javax.swing.JLabel();
        LPO9 = new javax.swing.JLabel();
        campoCodigoDestinacao = new javax.swing.JTextField();
        campoNomeDestinacao = new javax.swing.JTextField();
        BCadastrarDestinacao = new javax.swing.JButton();
        JDCadastrarTipoColetor = new javax.swing.JDialog();
        LCodTipoColetor = new javax.swing.JLabel();
        LTipoTipoColetor = new javax.swing.JLabel();
        LPO10 = new javax.swing.JLabel();
        campoCodigoTipoColetor = new javax.swing.JTextField();
        campoTipoColetor = new javax.swing.JTextField();
        BCadastrarTipoColetor = new javax.swing.JButton();
        JDCarregandoDados = new javax.swing.JDialog();
        JFCarregandoDados = new javax.swing.JFrame();
        JPBCarregando = new javax.swing.JProgressBar();
        LCarregando = new javax.swing.JLabel();
        Principal = new javax.swing.JTabbedPane();
        Doacoes = new javax.swing.JTabbedPane();
        CadastrarDoacao = new javax.swing.JPanel();
        LCodigo_doacao = new javax.swing.JLabel();
        LUsuário = new javax.swing.JLabel();
        LData = new javax.swing.JLabel();
        LOrigem = new javax.swing.JLabel();
        Ldoador = new javax.swing.JLabel();
        LCamposObrigatorios = new javax.swing.JLabel();
        campoDoacao = new javax.swing.JTextField();
        campoUsuarioDoacao = new javax.swing.JTextField();
        campoDataDoacao = new javax.swing.JFormattedTextField();
        CBDoadorDoacao = new javax.swing.JComboBox();
        BCadastrarDoacao = new javax.swing.JButton();
        BNovoDoador = new javax.swing.JButton();
        CBEventoOrigem = new javax.swing.JComboBox();
        BNovoEventoOrigem = new javax.swing.JButton();
        CadastrarItemDoacao = new javax.swing.JPanel();
        LItemDoacao = new javax.swing.JLabel();
        LUsuarioItemDoacao = new javax.swing.JLabel();
        LDoadorItemDoacao = new javax.swing.JLabel();
        LDoacaoItemDoacao = new javax.swing.JLabel();
        LQuantidadeItemDoacao = new javax.swing.JLabel();
        LTipoItemDoacao = new javax.swing.JLabel();
        LCamposObrigatorios1 = new javax.swing.JLabel();
        campoItemDoacao = new javax.swing.JTextField();
        campoUsuarioItemDoacao = new javax.swing.JTextField();
        campoDoadorItemDoacao = new javax.swing.JTextField();
        campoDoacaoItemDoacao = new javax.swing.JTextField();
        campoQuantidadeItemDoacao = new javax.swing.JTextField();
        BCadastrarItemDoacao = new javax.swing.JToggleButton();
        CBTipoItemDoacao = new javax.swing.JComboBox();
        BNovoTipoItemDoacao = new javax.swing.JButton();
        PainelDoacoes = new javax.swing.JPanel();
        RelatorioDoacoes = new javax.swing.JScrollPane();
        TDoacao = new javax.swing.JTable();
        BExcluirDoacao = new javax.swing.JButton();
        BEditarDoacao = new javax.swing.JButton();
        BRelatorioDoacao = new javax.swing.JButton();
        PainelItemDoacao = new javax.swing.JPanel();
        RelatorioItemDoacao = new javax.swing.JScrollPane();
        TItemDoacao = new javax.swing.JTable();
        BEditarItemDoacao = new javax.swing.JButton();
        BExcluirItemDoacao = new javax.swing.JButton();
        BRelatorioItemDoacao = new javax.swing.JButton();
        PainelEstoque = new javax.swing.JPanel();
        RelatorioEstoque = new javax.swing.JScrollPane();
        TEstoque = new javax.swing.JTable();
        BRelatorioEstoque = new javax.swing.JButton();
        PainelDoadores = new javax.swing.JPanel();
        ExibirDoadores = new javax.swing.JScrollPane();
        TDoador = new javax.swing.JTable();
        BExcluirDoador = new javax.swing.JButton();
        BEditarDoador = new javax.swing.JButton();
        BRelatorioDoadores = new javax.swing.JButton();
        Repasses = new javax.swing.JTabbedPane();
        CadastrarRepasse = new javax.swing.JPanel();
        LCodRepasse = new javax.swing.JLabel();
        LUsuarioRegistrarRepasse = new javax.swing.JLabel();
        LDataRepasse = new javax.swing.JLabel();
        LDescricaoRepasse = new javax.swing.JLabel();
        LColetorRepasse = new javax.swing.JLabel();
        LCamposObrigatorios3 = new javax.swing.JLabel();
        campoRepasse = new javax.swing.JTextField();
        campoUsuarioRepasse = new javax.swing.JTextField();
        campoDataRepasse = new javax.swing.JFormattedTextField();
        BCadastrarRepasse = new javax.swing.JButton();
        CBColetor = new javax.swing.JComboBox();
        BNovoColetor = new javax.swing.JButton();
        CBDestinacao = new javax.swing.JComboBox();
        BNovoDestinacao = new javax.swing.JButton();
        CadastrarItemRepasse = new javax.swing.JPanel();
        LCodItemRepasse = new javax.swing.JLabel();
        LCodUsuarioItemRepasse = new javax.swing.JLabel();
        LCodColetorItemRepasse = new javax.swing.JLabel();
        LCodRepasseItemRepasse = new javax.swing.JLabel();
        LQuantidadeItemRepasse = new javax.swing.JLabel();
        LTipoItemRepasse = new javax.swing.JLabel();
        LCamposObrigatorios4 = new javax.swing.JLabel();
        campoItemRepasse = new javax.swing.JTextField();
        campoUsuarioItemRepasse = new javax.swing.JTextField();
        campoColetorItemRepasse = new javax.swing.JTextField();
        campoRepasseItemRepasse = new javax.swing.JTextField();
        campoQuantidadeItemRepasse = new javax.swing.JTextField();
        BCadastrarItemRepasse = new javax.swing.JToggleButton();
        CBTipoItemRepasse = new javax.swing.JComboBox();
        BNovoTipoItemDoacao1 = new javax.swing.JButton();
        PainelRepasse = new javax.swing.JPanel();
        RelatorioRepasse = new javax.swing.JScrollPane();
        TRepasse = new javax.swing.JTable();
        BEditarRepasse = new javax.swing.JButton();
        BExcluirRepasse = new javax.swing.JButton();
        BRelatorioRepasse = new javax.swing.JButton();
        PainelItemRepasse = new javax.swing.JPanel();
        RelatorioItemRepasse = new javax.swing.JScrollPane();
        TItemRepasse = new javax.swing.JTable();
        BEditarItemRepasse = new javax.swing.JButton();
        BExcluirItemRepasse = new javax.swing.JButton();
        BRelatorioItemRepasse = new javax.swing.JButton();
        PainelColetores = new javax.swing.JPanel();
        ExibirColetores = new javax.swing.JScrollPane();
        TColetor = new javax.swing.JTable();
        BEditarColetor = new javax.swing.JButton();
        BExcluirColetor = new javax.swing.JButton();
        BRelatorioColetor = new javax.swing.JButton();
        Acervo = new javax.swing.JTabbedPane();
        CadastrarItemAcervo = new javax.swing.JPanel();
        LCodItemAcervo = new javax.swing.JLabel();
        LCodUsuario = new javax.swing.JLabel();
        LDataAcervo = new javax.swing.JLabel();
        LCod_Doador = new javax.swing.JLabel();
        LTipoItemAcervo = new javax.swing.JLabel();
        LMarca = new javax.swing.JLabel();
        LModeloItemAcervo = new javax.swing.JLabel();
        LAnoItemAcervo = new javax.swing.JLabel();
        LDescricao = new javax.swing.JLabel();
        LItemFunciona = new javax.swing.JLabel();
        LCamposComplementares = new javax.swing.JLabel();
        LInterface = new javax.swing.JLabel();
        LTecnologia = new javax.swing.JLabel();
        LCapacidade_MB = new javax.swing.JLabel();
        LContainer = new javax.swing.JLabel();
        LPO = new javax.swing.JLabel();
        campoCodigoItemAcervo = new javax.swing.JTextField();
        campoUsuarioItemAcervo = new javax.swing.JTextField();
        campoDataItemAcervo = new javax.swing.JFormattedTextField();
        campoAnoItemAcervo = new javax.swing.JTextField();
        SPDescricaoItemAcervo = new javax.swing.JScrollPane();
        campoDescricaoItemAcervo = new javax.swing.JTextPane();
        CBFunciona = new javax.swing.JCheckBox();
        campoCapacidadeItemAcervo = new javax.swing.JTextField();
        CBTipoItemAcervo = new javax.swing.JComboBox();
        CBMarca = new javax.swing.JComboBox();
        CBModelo = new javax.swing.JComboBox();
        CBInterface = new javax.swing.JComboBox();
        CBTecnologia = new javax.swing.JComboBox();
        campoCodContainerCadastrarItemAcervo = new javax.swing.JTextField();
        BNovoTipoItem = new javax.swing.JButton();
        BNovoMarca = new javax.swing.JButton();
        BNovoModelo = new javax.swing.JButton();
        BNovoInterface = new javax.swing.JButton();
        BNovoTecnologia = new javax.swing.JButton();
        BCadastrarItemAcervo = new javax.swing.JToggleButton();
        CBDoadorItemAcervo = new javax.swing.JComboBox();
        BNovoDoadorAcervo = new javax.swing.JButton();
        CadastrarImagem = new javax.swing.JPanel();
        LImagemCadastrarImagem = new javax.swing.JLabel();
        LUsuarioCadastrarImagem = new javax.swing.JLabel();
        LItemAcervoCadastrarImagem = new javax.swing.JLabel();
        LLinkCadastrarImagem = new javax.swing.JLabel();
        campoCodImagemCadastrarImagem = new javax.swing.JTextField();
        campoUsuarioCadastrarImagem = new javax.swing.JTextField();
        campoItemAcervoCadastrarImagem = new javax.swing.JTextField();
        campoLink = new javax.swing.JTextField();
        BCadastrarImagem = new javax.swing.JButton();
        BCheckLink = new javax.swing.JButton();
        LFotoAcervo = new javax.swing.JLabel();
        LCamposCadastrarImagem = new javax.swing.JLabel();
        CadastrarContainer = new javax.swing.JPanel();
        LCodContainerCadastrarContainer = new javax.swing.JLabel();
        LUsuarioCadastrarContainer = new javax.swing.JLabel();
        LLocalizacaoCadastrarContainer = new javax.swing.JLabel();
        LTipoContainerCadastrarContainer = new javax.swing.JLabel();
        LCamposCadastrarContainer = new javax.swing.JLabel();
        campoCodContainerCadastrarContainer = new javax.swing.JTextField();
        campoUsuarioCadastrarContainer = new javax.swing.JTextField();
        campoLocalizacaoCadastrarContainer = new javax.swing.JTextField();
        CBTipoContainerCadastrarContainer = new javax.swing.JComboBox();
        BCadastrarContainer = new javax.swing.JButton();
        BNovoTipoContainer = new javax.swing.JButton();
        PainelAcervo = new javax.swing.JPanel();
        RelatorioAcervo = new javax.swing.JScrollPane();
        TAcervo = new javax.swing.JTable();
        BEditarItemAcervo = new javax.swing.JButton();
        BExcluirItemAcervo = new javax.swing.JButton();
        BRelatorioAcervo = new javax.swing.JButton();
        Imagens = new javax.swing.JPanel();
        SPImagem = new javax.swing.JScrollPane();
        TImagem = new javax.swing.JTable();
        BEditarImagem = new javax.swing.JButton();
        BExcluirImagem = new javax.swing.JButton();
        Container = new javax.swing.JPanel();
        SPContainer = new javax.swing.JScrollPane();
        TContainer = new javax.swing.JTable();
        BEditarContainer = new javax.swing.JButton();
        BExcluirContainer = new javax.swing.JButton();
        Usuarios = new javax.swing.JTabbedPane();
        CadastrarUsuario = new javax.swing.JPanel();
        LCodNovoUsuario = new javax.swing.JLabel();
        LNomeUsuario = new javax.swing.JLabel();
        LRegistroAcademico = new javax.swing.JLabel();
        LEmail = new javax.swing.JLabel();
        LSenha = new javax.swing.JLabel();
        LRepetirSenha = new javax.swing.JLabel();
        LPermissão = new javax.swing.JLabel();
        LCamposCadastrarUsuario = new javax.swing.JLabel();
        campoCodigoNovoUsuario = new javax.swing.JTextField();
        campoNomeNovoUsuario = new javax.swing.JTextField();
        campoRegistroAcademicoCadastrarUsuario = new javax.swing.JTextField();
        campoEmailCadastrarUsuario = new javax.swing.JTextField();
        campoSenhaCadastrarUsuario = new javax.swing.JPasswordField();
        campoRepetirSenhaCadastrarUsuario = new javax.swing.JPasswordField();
        CBAdministrador = new javax.swing.JCheckBox();
        BCadastrarUsuario = new javax.swing.JButton();
        PainelUsuario = new javax.swing.JPanel();
        ExibirUsuarios = new javax.swing.JScrollPane();
        TUsuario = new javax.swing.JTable();
        BEditarUsuario = new javax.swing.JButton();
        BExcluirUsuario = new javax.swing.JButton();
        AbaDoUsuario = new javax.swing.JTabbedPane();
        SuasInformacoes = new javax.swing.JPanel();
        LCodUsuarioInfo = new javax.swing.JLabel();
        LNomeUsuarioInfo = new javax.swing.JLabel();
        LSenhaAlterar = new javax.swing.JLabel();
        LAlterar = new javax.swing.JLabel();
        LUsuarioAdm = new javax.swing.JLabel();
        LRegistroAcademicoAlterarUsuario = new javax.swing.JLabel();
        LCamposRegularesAlterarUsuario = new javax.swing.JLabel();
        LRepetirSenhaAlterarUsuario = new javax.swing.JLabel();
        LEmailAlterarUsuario = new javax.swing.JLabel();
        campoCodigoAlterarUsuario = new javax.swing.JTextField();
        campoNomeAlterarUsuario = new javax.swing.JTextField();
        campoRegistroAcademicoAlterarUsuario = new javax.swing.JTextField();
        campoSenhaAtualAlterarUsuario = new javax.swing.JPasswordField();
        campoNovaSenhaAlterarUsuario = new javax.swing.JPasswordField();
        campoRepetirSenhaAlterarUsuario = new javax.swing.JPasswordField();
        campoEmailAlterarUsuario = new javax.swing.JTextField();
        campoAdministradorAlterarUsuario = new javax.swing.JCheckBox();
        BAlterarUsuario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Deslogar = new javax.swing.JPanel();
        BDeslogar = new javax.swing.JToggleButton();
        LCertezaDeslogar = new javax.swing.JLabel();

        JDLogin.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDLogin.setTitle("SGACERVO - Login");
        JDLogin.setAlwaysOnTop(true);
        JDLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JDLogin.setMinimumSize(new java.awt.Dimension(500, 270));
        JDLogin.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        JDLogin.setResizable(false);
        JDLogin.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                JDLoginWindowClosed(evt);
            }
        });

        Header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/header.jpg"))); // NOI18N

        LUsuárioLogin.setText("Usuário:");

        LSenhaLogin.setText("Senha:");

        BLogar.setText("Entrar");
        BLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLogarActionPerformed(evt);
            }
        });

        BCancelarLogin.setText("Cancelar");
        BCancelarLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarLoginActionPerformed(evt);
            }
        });

        BEsqueciSenha.setText("Esqueci a senha");
        BEsqueciSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEsqueciSenhaActionPerformed(evt);
            }
        });

        CBUsuarioLogin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        LAguarde.setText("Aguarde o carregamento...");

        LCarregandoGif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/carregando.gif"))); // NOI18N

        javax.swing.GroupLayout JDLoginLayout = new javax.swing.GroupLayout(JDLogin.getContentPane());
        JDLogin.getContentPane().setLayout(JDLoginLayout);
        JDLoginLayout.setHorizontalGroup(
            JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(JDLoginLayout.createSequentialGroup()
                .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDLoginLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LSenhaLogin)
                            .addComponent(LUsuárioLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LAguarde)
                            .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(BLogar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BCancelarLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(campoSenhaLogin, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BEsqueciSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(CBUsuarioLogin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(JDLoginLayout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(LCarregandoGif)))
                .addContainerGap())
        );
        JDLoginLayout.setVerticalGroup(
            JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDLoginLayout.createSequentialGroup()
                .addComponent(Header)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LUsuárioLogin)
                    .addComponent(CBUsuarioLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LSenhaLogin)
                    .addComponent(campoSenhaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BLogar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCancelarLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BEsqueciSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LAguarde)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCarregandoGif)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDAlterarColetor.setTitle("SGACERVO - Alterar Doador");
        JDAlterarColetor.setMinimumSize(new java.awt.Dimension(365, 225));
        JDAlterarColetor.setResizable(false);

        LCodigo_ColetorAlterarColetor.setText("Código do Coletor:");

        LNomeColetorAlterarColetor.setText("**Nome do Coletor:");

        LTipoColetorAlterarColetor.setText("**Tipo do Coletor:");

        LCamposAlterarColetor.setText("** Campos que você pode alterar.");

        campoCodigoColetorAlterarColetor.setEditable(false);
        campoCodigoColetorAlterarColetor.setEnabled(false);

        CBTipoColetorAlterarColetor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BAlterarColetor.setText("Salvar");
        BAlterarColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarColetorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarColetorLayout = new javax.swing.GroupLayout(JDAlterarColetor.getContentPane());
        JDAlterarColetor.getContentPane().setLayout(JDAlterarColetorLayout);
        JDAlterarColetorLayout.setHorizontalGroup(
            JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDAlterarColetorLayout.createSequentialGroup()
                        .addGroup(JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeColetorAlterarColetor)
                            .addComponent(LCodigo_ColetorAlterarColetor)
                            .addComponent(LTipoColetorAlterarColetor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoCodigoColetorAlterarColetor, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(campoNomeColetorAlterarColetor, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(BAlterarColetor)
                            .addComponent(CBTipoColetorAlterarColetor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(LCamposAlterarColetor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDAlterarColetorLayout.setVerticalGroup(
            JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCodigoColetorAlterarColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_ColetorAlterarColetor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNomeColetorAlterarColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNomeColetorAlterarColetor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTipoColetorAlterarColetor)
                    .addComponent(CBTipoColetorAlterarColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(BAlterarColetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarColetor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDAlterarContainer.setTitle("SGACERVO - Alterar Imagem");
        JDAlterarContainer.setMinimumSize(null);
        JDAlterarContainer.setResizable(false);

        LCodContainerAlterarContainer.setText("Código do Container:");

        LLocalizacaoAlterarContainer.setText("**Localização do Container:");

        LTipoContainerAlterarContainer.setText("**Tipo do Container");

        LCamposAlterarContainer.setText("** Campos que você pode alterar.");

        campoCodContainerAlterarContainer.setEditable(false);
        campoCodContainerAlterarContainer.setEnabled(false);

        BAlterarContainer.setText("Salvar");
        BAlterarContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarContainerActionPerformed(evt);
            }
        });

        CBTipoContainerAlterarContainer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout JDAlterarContainerLayout = new javax.swing.GroupLayout(JDAlterarContainer.getContentPane());
        JDAlterarContainer.getContentPane().setLayout(JDAlterarContainerLayout);
        JDAlterarContainerLayout.setHorizontalGroup(
            JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposAlterarContainer)
                    .addGroup(JDAlterarContainerLayout.createSequentialGroup()
                        .addGroup(JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LLocalizacaoAlterarContainer)
                            .addComponent(LCodContainerAlterarContainer)
                            .addComponent(LTipoContainerAlterarContainer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BAlterarContainer)
                            .addComponent(campoCodContainerAlterarContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(campoLocalizacaoAlterarContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(CBTipoContainerAlterarContainer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDAlterarContainerLayout.setVerticalGroup(
            JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCodContainerAlterarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodContainerAlterarContainer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoLocalizacaoAlterarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LLocalizacaoAlterarContainer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTipoContainerAlterarContainer)
                    .addComponent(CBTipoContainerAlterarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAlterarContainer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarContainer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDAlterarDoacao.setTitle("SGACERVO - Alterar Doação");
        JDAlterarDoacao.setMinimumSize(new java.awt.Dimension(335, 225));
        JDAlterarDoacao.setResizable(false);

        LCodigo_doacaoAlterarDoacao.setText("Código de Doação:");

        LUsuárioAlterarDoacao.setText("Usuário:");

        LDataAlterarDoacao.setText("Data:");

        LOrigemAlterarDoacao.setText("** Origem:");

        LDoadorAlterarDoacao.setText("Doador:");

        LCamposAlterarDoacao.setText("** Campos que você pode alterar.");

        campoDoacaoAlterarDoacao.setEditable(false);
        campoDoacaoAlterarDoacao.setEnabled(false);

        campoUsuarioAlterarDoacao.setEditable(false);
        campoUsuarioAlterarDoacao.setEnabled(false);

        campoDataAlterarDoacao.setEditable(false);
        campoDataAlterarDoacao.setEnabled(false);

        campoDoadorAlterarDoacao.setEditable(false);
        campoDoadorAlterarDoacao.setEnabled(false);

        CBEventoOrigemAlterarDoacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BAlterarDoacao.setText("Salvar");
        BAlterarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarDoacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarDoacaoLayout = new javax.swing.GroupLayout(JDAlterarDoacao.getContentPane());
        JDAlterarDoacao.getContentPane().setLayout(JDAlterarDoacaoLayout);
        JDAlterarDoacaoLayout.setHorizontalGroup(
            JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarDoacaoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposAlterarDoacao)
                    .addGroup(JDAlterarDoacaoLayout.createSequentialGroup()
                        .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LUsuárioAlterarDoacao)
                            .addComponent(LCodigo_doacaoAlterarDoacao)
                            .addComponent(LOrigemAlterarDoacao)
                            .addComponent(LDoadorAlterarDoacao)
                            .addComponent(LDataAlterarDoacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BAlterarDoacao)
                            .addComponent(campoUsuarioAlterarDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoDataAlterarDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoDoadorAlterarDoacao)
                            .addComponent(CBEventoOrigemAlterarDoacao, 0, 200, Short.MAX_VALUE)
                            .addComponent(campoDoacaoAlterarDoacao))))
                .addGap(40, 40, 40))
        );
        JDAlterarDoacaoLayout.setVerticalGroup(
            JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDoacaoAlterarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_doacaoAlterarDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoUsuarioAlterarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LUsuárioAlterarDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LDoadorAlterarDoacao)
                    .addComponent(campoDoadorAlterarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDataAlterarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LDataAlterarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LOrigemAlterarDoacao)
                    .addComponent(CBEventoOrigemAlterarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(BAlterarDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarDoacao)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        JDAlterarDoador.setTitle("SGACERVO - Alterar Doador");
        JDAlterarDoador.setMinimumSize(new java.awt.Dimension(365, 225));
        JDAlterarDoador.setResizable(false);

        LCodigo_doadorAlterarDoador.setText("Código do Doador:");

        LNomeDoadorAlterarDoador.setText("**Nome do Doador:");

        LCamposAlterarItemDoacao1.setText("** Campos que você pode alterar.");

        campoCodigoDoadorAlterarDoador.setEditable(false);
        campoCodigoDoadorAlterarDoador.setEnabled(false);

        BAlterarDoador.setText("Salvar");
        BAlterarDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarDoadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarDoadorLayout = new javax.swing.GroupLayout(JDAlterarDoador.getContentPane());
        JDAlterarDoador.getContentPane().setLayout(JDAlterarDoadorLayout);
        JDAlterarDoadorLayout.setHorizontalGroup(
            JDAlterarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarDoadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDAlterarDoadorLayout.createSequentialGroup()
                        .addGroup(JDAlterarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeDoadorAlterarDoador)
                            .addComponent(LCodigo_doadorAlterarDoador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoCodigoDoadorAlterarDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoNomeDoadorAlterarDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BAlterarDoador)))
                    .addComponent(LCamposAlterarItemDoacao1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDAlterarDoadorLayout.setVerticalGroup(
            JDAlterarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarDoadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCodigoDoadorAlterarDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_doadorAlterarDoador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNomeDoadorAlterarDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNomeDoadorAlterarDoador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAlterarDoador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarItemDoacao1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDAlterarImagem.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDAlterarImagem.setTitle("SGACERVO - Alterar Imagem");
        JDAlterarImagem.setMinimumSize(null);
        JDAlterarImagem.setResizable(false);
        JDAlterarImagem.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                resetarImagem(evt);
            }
        });

        LCodImagemAlterarImagem.setText("Código da Imagem:");

        LCodItemAcervoAlterarImagem.setText("Código do Item Acervo:");

        LLinkAlterarImagem.setText("**Caminho/Link da Imagem:");

        LCamposAlterarImagem.setText("** Campos que você pode alterar.");

        LImagemAlterarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png"))); // NOI18N

        campoCodImagemAlterarImagem.setEditable(false);
        campoCodImagemAlterarImagem.setEnabled(false);

        campoCodItemAcervoAlterarImagem.setEditable(false);
        campoCodItemAcervoAlterarImagem.setEnabled(false);

        campoLinkAlterarImagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoLinkAlterarImagemKeyTyped(evt);
            }
        });

        CheckImagemAlterarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/check16.png"))); // NOI18N
        CheckImagemAlterarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckImagemAlterarImagemActionPerformed(evt);
            }
        });

        BAlterarImagem.setText("Salvar");
        BAlterarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarImagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarImagemLayout = new javax.swing.GroupLayout(JDAlterarImagem.getContentPane());
        JDAlterarImagem.getContentPane().setLayout(JDAlterarImagemLayout);
        JDAlterarImagemLayout.setHorizontalGroup(
            JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposAlterarImagem)
                    .addGroup(JDAlterarImagemLayout.createSequentialGroup()
                        .addGroup(JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LCodItemAcervoAlterarImagem)
                            .addComponent(LCodImagemAlterarImagem)
                            .addComponent(LLinkAlterarImagem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LImagemAlterarImagem)
                            .addComponent(BAlterarImagem)
                            .addGroup(JDAlterarImagemLayout.createSequentialGroup()
                                .addComponent(campoLinkAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CheckImagemAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoCodImagemAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoCodItemAcervoAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        JDAlterarImagemLayout.setVerticalGroup(
            JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCodImagemAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodImagemAlterarImagem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCodItemAcervoAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodItemAcervoAlterarImagem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CheckImagemAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JDAlterarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LLinkAlterarImagem)
                        .addComponent(campoLinkAlterarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LImagemAlterarImagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAlterarImagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarImagem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDAlterarItemAcervo.setTitle("SGACERVO - Alterar Item Doação");
        JDAlterarItemAcervo.setMinimumSize(new java.awt.Dimension(365, 525));

        LCodigo_ItemAlterarItemAcervo.setText("Código de Item Acervo:");

        LUsuárioAlterarItemAcervo.setText("Usuário:");

        LDoadorAlterarItemAcervo.setText("Doador:");

        LDataAlterarItemAcervo.setText("Data de Doação:");

        LTipoAlterarItemAcervo.setText("**Tipo:");

        LMarcaAlterarItemAcervo.setText("**Marca:");

        LModeloAlterarItemAcervo.setText("**Modelo:");

        LInterfaceAlterarItemAcervo.setText("**Interface:");

        LTecnologiaAlterarItemAcervo.setText("**Tecnologia:");

        LCapacidadeAlterarItemAcervo.setText("** Capacidade(MB):");

        LAnoAlterarItemAcervo.setText("**Ano:");

        LContainerAlterarItemAcervo.setText("**Código do Container:");

        LDescricaoAlterarItemAcervo.setText("**Descrição:");

        LFuncionaAlterarItemAcervo.setText("**Funciona:");

        LCamposAlterarItemAcervo.setText("** Campos que você pode alterar.");

        campoItemAcervoAlterarItemAcervo.setEditable(false);
        campoItemAcervoAlterarItemAcervo.setEnabled(false);

        campoUsuarioAlterarItemAcervo.setEditable(false);
        campoUsuarioAlterarItemAcervo.setEnabled(false);

        campoDoadorAlterarItemAcervo.setEditable(false);
        campoDoadorAlterarItemAcervo.setEnabled(false);

        campoDataAlterarItemAcervo.setEditable(false);
        campoDataAlterarItemAcervo.setEnabled(false);

        CBTipoAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBMarcaAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBModeloAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBInterfaceAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBTecnologiaAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        TADescricaoAlterarItemAcervo.setColumns(20);
        TADescricaoAlterarItemAcervo.setRows(5);
        SPDescricaoAlterarItemAcervo.setViewportView(TADescricaoAlterarItemAcervo);

        BAlterarItemAcervo.setText("Salvar");
        BAlterarItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarItemAcervoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarItemAcervoLayout = new javax.swing.GroupLayout(JDAlterarItemAcervo.getContentPane());
        JDAlterarItemAcervo.getContentPane().setLayout(JDAlterarItemAcervoLayout);
        JDAlterarItemAcervoLayout.setHorizontalGroup(
            JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarItemAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposAlterarItemAcervo)
                    .addGroup(JDAlterarItemAcervoLayout.createSequentialGroup()
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LUsuárioAlterarItemAcervo)
                            .addComponent(LCodigo_ItemAlterarItemAcervo)
                            .addComponent(LModeloAlterarItemAcervo)
                            .addComponent(LInterfaceAlterarItemAcervo)
                            .addComponent(LMarcaAlterarItemAcervo)
                            .addComponent(LTipoAlterarItemAcervo)
                            .addComponent(LDataAlterarItemAcervo)
                            .addComponent(LDoadorAlterarItemAcervo)
                            .addComponent(LTecnologiaAlterarItemAcervo)
                            .addComponent(LCapacidadeAlterarItemAcervo)
                            .addComponent(LAnoAlterarItemAcervo)
                            .addComponent(LContainerAlterarItemAcervo)
                            .addComponent(LDescricaoAlterarItemAcervo)
                            .addComponent(LFuncionaAlterarItemAcervo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoItemAcervoAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoUsuarioAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoDoadorAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoDataAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBTipoAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBMarcaAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBModeloAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBInterfaceAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoCapacidadeAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoAnoAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoContainerAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(SPDescricaoAlterarItemAcervo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(CBTecnologiaAlterarItemAcervo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(CheckFuncionaAlterarItemAcervo)
                            .addComponent(BAlterarItemAcervo))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDAlterarItemAcervoLayout.setVerticalGroup(
            JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarItemAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JDAlterarItemAcervoLayout.createSequentialGroup()
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoItemAcervoAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_ItemAlterarItemAcervo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoUsuarioAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LUsuárioAlterarItemAcervo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LDoadorAlterarItemAcervo)
                            .addComponent(campoDoadorAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LDataAlterarItemAcervo)
                            .addComponent(campoDataAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LTipoAlterarItemAcervo)
                            .addComponent(CBTipoAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CBMarcaAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LMarcaAlterarItemAcervo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LModeloAlterarItemAcervo)
                            .addComponent(CBModeloAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LInterfaceAlterarItemAcervo)
                            .addComponent(CBInterfaceAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LTecnologiaAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBTecnologiaAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LCapacidadeAlterarItemAcervo)
                            .addComponent(campoCapacidadeAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LAnoAlterarItemAcervo)
                            .addComponent(campoAnoAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoContainerAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LContainerAlterarItemAcervo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SPDescricaoAlterarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LDescricaoAlterarItemAcervo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LFuncionaAlterarItemAcervo))
                    .addComponent(CheckFuncionaAlterarItemAcervo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAlterarItemAcervo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarItemAcervo)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        JDAlterarItemDoacao.setTitle("SGACERVO - Alterar Item Doação");
        JDAlterarItemDoacao.setMinimumSize(new java.awt.Dimension(365, 225));
        JDAlterarItemDoacao.setResizable(false);

        LCodigo_doacaoAlterarDoacao1.setText("Código de Item Doação:");

        LUsuárioAlterarDoacao1.setText("Usuário:");

        LDoadorAlterarDoacao1.setText("Doador:");

        LDataAlterarDoacao1.setText("**Tipo:");

        LOrigemAlterarDoacao1.setText("** Quantidade:");

        LCamposAlterarItemDoacao.setText("** Campos que você pode alterar.");

        campoItemDoacaoAlterarItemDoacao.setEditable(false);
        campoItemDoacaoAlterarItemDoacao.setEnabled(false);

        campoUsuarioAlterarItemDoacao.setEditable(false);
        campoUsuarioAlterarItemDoacao.setEnabled(false);

        campoDoadorAlterarItemDoacao.setEditable(false);
        campoDoadorAlterarItemDoacao.setEnabled(false);

        CBTipoAlterarItemDoacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BAlterarItemDoacao.setText("Salvar");
        BAlterarItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarItemDoacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarItemDoacaoLayout = new javax.swing.GroupLayout(JDAlterarItemDoacao.getContentPane());
        JDAlterarItemDoacao.getContentPane().setLayout(JDAlterarItemDoacaoLayout);
        JDAlterarItemDoacaoLayout.setHorizontalGroup(
            JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarItemDoacaoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposAlterarItemDoacao)
                    .addGroup(JDAlterarItemDoacaoLayout.createSequentialGroup()
                        .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LUsuárioAlterarDoacao1)
                            .addComponent(LCodigo_doacaoAlterarDoacao1)
                            .addComponent(LOrigemAlterarDoacao1)
                            .addComponent(LDoadorAlterarDoacao1)
                            .addComponent(LDataAlterarDoacao1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BAlterarItemDoacao)
                            .addComponent(campoQuantidadeAlterarItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoDoadorAlterarItemDoacao)
                            .addComponent(campoUsuarioAlterarItemDoacao)
                            .addComponent(campoItemDoacaoAlterarItemDoacao)
                            .addComponent(CBTipoAlterarItemDoacao, 0, 200, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        JDAlterarItemDoacaoLayout.setVerticalGroup(
            JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoItemDoacaoAlterarItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_doacaoAlterarDoacao1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoUsuarioAlterarItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LUsuárioAlterarDoacao1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LDoadorAlterarDoacao1)
                    .addComponent(campoDoadorAlterarItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LDataAlterarDoacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBTipoAlterarItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LOrigemAlterarDoacao1)
                    .addComponent(campoQuantidadeAlterarItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAlterarItemDoacao)
                .addGap(4, 4, 4)
                .addComponent(LCamposAlterarItemDoacao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDAlterarItemRepasse.setTitle("SGACERVO - Alterar Item Repasse");
        JDAlterarItemRepasse.setMinimumSize(new java.awt.Dimension(335, 225));
        JDAlterarItemRepasse.setResizable(false);

        LCodigo_RepasseAlterarItemRepasse.setText("Código de Item Repasse:");

        LUsuárioAlterarItemRepasse.setText("Usuário:");

        LDataAlterarItemRepasse.setText("**Tipo:");

        LOrigemAlterarItemRepasse.setText("** Quantidade:");

        LDoadorAlterarItemRepasse.setText("Coletor:");

        LCamposAlterarItemRepasse.setText("** Campos que você pode alterar.");

        campoUsuarioAlterarItemRepasse.setEditable(false);
        campoUsuarioAlterarItemRepasse.setEnabled(false);

        campoColetorAlterarItemRepasse.setEditable(false);
        campoColetorAlterarItemRepasse.setEnabled(false);

        CBTipoAlterarItemRepasse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BAlterarItemRepasse.setText("Salvar");
        BAlterarItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarItemRepasseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarItemRepasseLayout = new javax.swing.GroupLayout(JDAlterarItemRepasse.getContentPane());
        JDAlterarItemRepasse.getContentPane().setLayout(JDAlterarItemRepasseLayout);
        JDAlterarItemRepasseLayout.setHorizontalGroup(
            JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarItemRepasseLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposAlterarItemRepasse)
                    .addGroup(JDAlterarItemRepasseLayout.createSequentialGroup()
                        .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LUsuárioAlterarItemRepasse)
                            .addComponent(LCodigo_RepasseAlterarItemRepasse)
                            .addComponent(LOrigemAlterarItemRepasse)
                            .addComponent(LDoadorAlterarItemRepasse)
                            .addComponent(LDataAlterarItemRepasse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BAlterarItemRepasse)
                            .addComponent(campoUsuarioAlterarItemRepasse, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoColetorAlterarItemRepasse)
                            .addComponent(CBTipoAlterarItemRepasse, 0, 200, Short.MAX_VALUE)
                            .addComponent(campoItemRepasseAlterarItemRepasse)
                            .addComponent(campoQuantidadeAlterarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        JDAlterarItemRepasseLayout.setVerticalGroup(
            JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoItemRepasseAlterarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_RepasseAlterarItemRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoUsuarioAlterarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LUsuárioAlterarItemRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LDoadorAlterarItemRepasse)
                    .addComponent(campoColetorAlterarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LDataAlterarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBTipoAlterarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LOrigemAlterarItemRepasse)
                    .addComponent(campoQuantidadeAlterarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(BAlterarItemRepasse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarItemRepasse)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDAlterarRepasse.setTitle("SGACERVO - Alterar Repasse");
        JDAlterarRepasse.setMinimumSize(new java.awt.Dimension(335, 225));
        JDAlterarRepasse.setResizable(false);

        LCodigo_doacaoAlterarRepasse.setText("Código de Repasse:");

        LUsuárioAlterarRepasse.setText("Usuário:");

        LDataAlterarRepasse.setText("Data:");

        LOrigemAlterarRepasse.setText("** Destinação:");

        LDoadorAlterarRepasse.setText("Coletor:");

        LCamposAlterarRepasse.setText("** Campos que você pode alterar.");

        campoRepasseAlterarRepasse.setEditable(false);
        campoRepasseAlterarRepasse.setEnabled(false);

        campoUsuarioAlterarRepasse.setEditable(false);
        campoUsuarioAlterarRepasse.setEnabled(false);

        campoDataAlterarRepasse.setEditable(false);
        campoDataAlterarRepasse.setEnabled(false);

        campoColetorAlterarRepasse.setEditable(false);
        campoColetorAlterarRepasse.setEnabled(false);

        CBDestinacaoAlterarRepasse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BAlterarRepasse.setText("Salvar");
        BAlterarRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarRepasseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarRepasseLayout = new javax.swing.GroupLayout(JDAlterarRepasse.getContentPane());
        JDAlterarRepasse.getContentPane().setLayout(JDAlterarRepasseLayout);
        JDAlterarRepasseLayout.setHorizontalGroup(
            JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarRepasseLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposAlterarRepasse)
                    .addGroup(JDAlterarRepasseLayout.createSequentialGroup()
                        .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LUsuárioAlterarRepasse)
                            .addComponent(LCodigo_doacaoAlterarRepasse)
                            .addComponent(LOrigemAlterarRepasse)
                            .addComponent(LDoadorAlterarRepasse)
                            .addComponent(LDataAlterarRepasse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BAlterarRepasse)
                            .addComponent(campoUsuarioAlterarRepasse, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoDataAlterarRepasse, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoColetorAlterarRepasse)
                            .addComponent(CBDestinacaoAlterarRepasse, 0, 200, Short.MAX_VALUE)
                            .addComponent(campoRepasseAlterarRepasse))))
                .addGap(40, 40, 40))
        );
        JDAlterarRepasseLayout.setVerticalGroup(
            JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoRepasseAlterarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_doacaoAlterarRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoUsuarioAlterarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LUsuárioAlterarRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LDoadorAlterarRepasse)
                    .addComponent(campoColetorAlterarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDataAlterarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LDataAlterarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LOrigemAlterarRepasse)
                    .addComponent(CBDestinacaoAlterarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(BAlterarRepasse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarRepasse)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        JDAlterarUsuario.setTitle("SGACERVO - Alterar Usuário");
        JDAlterarUsuario.setMinimumSize(new java.awt.Dimension(365, 225));
        JDAlterarUsuario.setResizable(false);

        LCodigo_UsuarioAlterarUsuario.setText("Código do Usuário:");

        LNome_UsuarioAlterarUsuario.setText("**Nome do Usuário:");

        LRegistroAcademicoAlterarUsuarioJD.setText("**Registro Acadêmico:");

        LEmailAlterarUsuarioJD.setText("**E-mail:");

        LNovaSenhaAlterarUsuarioJD.setText("**Nova Senha:");

        LRepetirNovaSenhaAlterarUsuarioJD.setText("**Repetir Nova Senha:");

        LPermissaoAlterarUsuarioJD.setText("**Permissão:");

        LCamposAlterarItemDoacao2.setText("** Campos que você pode alterar.");

        campoCodigoUsuarioAlterarUsuarioJD.setEditable(false);
        campoCodigoUsuarioAlterarUsuarioJD.setEnabled(false);

        CheckAdministradorAlterarUsuarioJD.setText("Administrador");

        BAlterarUsuarioJD.setText("Salvar");
        BAlterarUsuarioJD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarUsuarioJDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAlterarUsuarioLayout = new javax.swing.GroupLayout(JDAlterarUsuario.getContentPane());
        JDAlterarUsuario.getContentPane().setLayout(JDAlterarUsuarioLayout);
        JDAlterarUsuarioLayout.setHorizontalGroup(
            JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDAlterarUsuarioLayout.createSequentialGroup()
                        .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNome_UsuarioAlterarUsuario)
                            .addComponent(LCodigo_UsuarioAlterarUsuario)
                            .addComponent(LRegistroAcademicoAlterarUsuarioJD)
                            .addComponent(LEmailAlterarUsuarioJD)
                            .addComponent(LNovaSenhaAlterarUsuarioJD)
                            .addComponent(LRepetirNovaSenhaAlterarUsuarioJD)
                            .addComponent(LPermissaoAlterarUsuarioJD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoUsuarioAlterarUsuarioJD, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(campoNomeUsuarioAlterarUsuarioJD, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(BAlterarUsuarioJD)
                                .addComponent(campoRegistroAcademicoAlterarUsuarioJD)
                                .addComponent(campoEmailAlterarUsuarioJD)
                                .addComponent(campoNovaSenhaAlterarUsuarioJD)
                                .addComponent(campoRepetirNovaSenhaAlterarUsuarioJD))
                            .addComponent(CheckAdministradorAlterarUsuarioJD)))
                    .addComponent(LCamposAlterarItemDoacao2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDAlterarUsuarioLayout.setVerticalGroup(
            JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAlterarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoCodigoUsuarioAlterarUsuarioJD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_UsuarioAlterarUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNomeUsuarioAlterarUsuarioJD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNome_UsuarioAlterarUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LRegistroAcademicoAlterarUsuarioJD)
                    .addComponent(campoRegistroAcademicoAlterarUsuarioJD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoEmailAlterarUsuarioJD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LEmailAlterarUsuarioJD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNovaSenhaAlterarUsuarioJD)
                    .addComponent(campoNovaSenhaAlterarUsuarioJD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LRepetirNovaSenhaAlterarUsuarioJD)
                    .addComponent(campoRepetirNovaSenhaAlterarUsuarioJD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAlterarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LPermissaoAlterarUsuarioJD)
                    .addComponent(CheckAdministradorAlterarUsuarioJD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAlterarUsuarioJD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposAlterarItemDoacao2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarTipoContainer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarTipoContainer.setTitle("SGACERVO - Cadastrar Tipo Container");
        JDCadastrarTipoContainer.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarTipoContainer.setResizable(false);

        LCodTipoContainer.setText("Código do Tipo Container:");

        LTipoTipoContainer.setText("* Tipo do Container:");

        LPO11.setText("* Campos de preenchimento obrigatório.");

        campoCodigoTipoContainer.setEnabled(false);

        BCadastrarTipoContainer.setText("Cadastrar");
        BCadastrarTipoContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarTipoContainerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarTipoContainerLayout = new javax.swing.GroupLayout(JDCadastrarTipoContainer.getContentPane());
        JDCadastrarTipoContainer.getContentPane().setLayout(JDCadastrarTipoContainerLayout);
        JDCadastrarTipoContainerLayout.setHorizontalGroup(
            JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTipoContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarTipoContainerLayout.createSequentialGroup()
                        .addGroup(JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LTipoTipoContainer)
                            .addComponent(LCodTipoContainer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarTipoContainer)
                            .addGroup(JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoTipoContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoTipoContainer))))
                    .addComponent(LPO11))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        JDCadastrarTipoContainerLayout.setVerticalGroup(
            JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTipoContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodTipoContainer)
                    .addComponent(campoCodigoTipoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarTipoContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTipoTipoContainer)
                    .addComponent(campoTipoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarTipoContainer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarTipoItem.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarTipoItem.setTitle("SGACERVO - Cadastrar Tipo Item");
        JDCadastrarTipoItem.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarTipoItem.setResizable(false);

        LCodTipo.setText("Código do Tipo Item:");

        LTipo.setText("* Tipo do Item:");

        LPO3.setText("* Campos de preenchimento obrigatório.");

        campoCodigoTipoItem.setEnabled(false);

        BCadastrarTipo.setText("Cadastrar");
        BCadastrarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarTipoItemLayout = new javax.swing.GroupLayout(JDCadastrarTipoItem.getContentPane());
        JDCadastrarTipoItem.getContentPane().setLayout(JDCadastrarTipoItemLayout);
        JDCadastrarTipoItemLayout.setHorizontalGroup(
            JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTipoItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarTipoItemLayout.createSequentialGroup()
                        .addGroup(JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LTipo)
                            .addComponent(LCodTipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarTipo)
                            .addGroup(JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoTipoItem, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoTipoItem))))
                    .addComponent(LPO3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarTipoItemLayout.setVerticalGroup(
            JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTipoItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodTipo)
                    .addComponent(campoCodigoTipoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarTipoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTipo)
                    .addComponent(campoTipoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarTipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarMarca.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarMarca.setTitle("SGACERVO - Cadastrar Marca");
        JDCadastrarMarca.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarMarca.setResizable(false);

        LCodMarca.setText("Código da Marca:");

        LNomeMarca.setText("* Nome da Marca");

        LPO4.setText("* Campos de preenchimento obrigatório.");

        campoCodigoMarca.setEnabled(false);

        BCadastrarMarca.setText("Cadastrar");
        BCadastrarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarMarcaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarMarcaLayout = new javax.swing.GroupLayout(JDCadastrarMarca.getContentPane());
        JDCadastrarMarca.getContentPane().setLayout(JDCadastrarMarcaLayout);
        JDCadastrarMarcaLayout.setHorizontalGroup(
            JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarMarcaLayout.createSequentialGroup()
                        .addGroup(JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeMarca)
                            .addComponent(LCodMarca))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarMarca)
                            .addGroup(JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoNomeMarca))))
                    .addComponent(LPO4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarMarcaLayout.setVerticalGroup(
            JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodMarca)
                    .addComponent(campoCodigoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeMarca)
                    .addComponent(campoNomeMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarMarca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarModelo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarModelo.setTitle("SGACERVO - Cadastrar Modelo");
        JDCadastrarModelo.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarModelo.setResizable(false);

        LCodModelo.setText("Código do Modelo:");

        LNomeModelo.setText("* Nome do Modelo");

        LPO5.setText("* Campos de preenchimento obrigatório.");

        campoCodigoModelo.setEnabled(false);

        BCadastrarModelo.setText("Cadastrar");
        BCadastrarModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarModeloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarModeloLayout = new javax.swing.GroupLayout(JDCadastrarModelo.getContentPane());
        JDCadastrarModelo.getContentPane().setLayout(JDCadastrarModeloLayout);
        JDCadastrarModeloLayout.setHorizontalGroup(
            JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarModeloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarModeloLayout.createSequentialGroup()
                        .addGroup(JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeModelo)
                            .addComponent(LCodModelo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarModelo)
                            .addGroup(JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoModelo, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoNomeModelo))))
                    .addComponent(LPO5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarModeloLayout.setVerticalGroup(
            JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarModeloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodModelo)
                    .addComponent(campoCodigoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarModeloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeModelo)
                    .addComponent(campoNomeModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarModelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarInterface.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarInterface.setTitle("SGACERVO - Cadastrar Interface");
        JDCadastrarInterface.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarInterface.setResizable(false);

        LCodInterface.setText("Código da Interface:");

        LNomeInterface.setText("* Nome da Interface:");

        LPO6.setText("* Campos de preenchimento obrigatório.");

        campoCodigoInterface.setEnabled(false);

        BCadastrarInterface.setText("Cadastrar");
        BCadastrarInterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarInterfaceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarInterfaceLayout = new javax.swing.GroupLayout(JDCadastrarInterface.getContentPane());
        JDCadastrarInterface.getContentPane().setLayout(JDCadastrarInterfaceLayout);
        JDCadastrarInterfaceLayout.setHorizontalGroup(
            JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarInterfaceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarInterfaceLayout.createSequentialGroup()
                        .addGroup(JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeInterface)
                            .addComponent(LCodInterface))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarInterface)
                            .addGroup(JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoInterface, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoNomeInterface))))
                    .addComponent(LPO6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarInterfaceLayout.setVerticalGroup(
            JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarInterfaceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodInterface)
                    .addComponent(campoCodigoInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarInterfaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeInterface)
                    .addComponent(campoNomeInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarInterface)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarTecnologia.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarTecnologia.setTitle("SGACERVO - Cadastrar Tecnologia");
        JDCadastrarTecnologia.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarTecnologia.setResizable(false);

        LCodTecnologia.setText("Código da Tecnologia:");

        LNomeTecnologia.setText("* Nome da Tecnologia:");

        LPO7.setText("* Campos de preenchimento obrigatório.");

        campoCodigoTecnologia.setEnabled(false);

        BCadastrarTecnologia.setText("Cadastrar");
        BCadastrarTecnologia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarTecnologiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarTecnologiaLayout = new javax.swing.GroupLayout(JDCadastrarTecnologia.getContentPane());
        JDCadastrarTecnologia.getContentPane().setLayout(JDCadastrarTecnologiaLayout);
        JDCadastrarTecnologiaLayout.setHorizontalGroup(
            JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTecnologiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarTecnologiaLayout.createSequentialGroup()
                        .addGroup(JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeTecnologia)
                            .addComponent(LCodTecnologia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarTecnologia)
                            .addGroup(JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoTecnologia, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoNomeTecnologia))))
                    .addComponent(LPO7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarTecnologiaLayout.setVerticalGroup(
            JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTecnologiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodTecnologia)
                    .addComponent(campoCodigoTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarTecnologiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeTecnologia)
                    .addComponent(campoNomeTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarTecnologia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarColetor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarColetor.setTitle("SGACERVO - Cadastrar Coletor");
        JDCadastrarColetor.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarColetor.setResizable(false);

        LNomeColetor.setText("* Nome do Coletor:");

        LTipoColetor.setText("* Tipo do Coletor:");

        LCamposObrigatorios5.setText("* Campos de preenchimento obrigatório.");

        LCodNovoColetor.setText("Código do Coletor:");

        campoCodigoColetor.setEditable(false);
        campoCodigoColetor.setEnabled(false);

        BCadastrarColetor.setText("Cadastrar");
        BCadastrarColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarColetorActionPerformed(evt);
            }
        });

        CBTipoColetor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoTipoColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoColetorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarColetorLayout = new javax.swing.GroupLayout(JDCadastrarColetor.getContentPane());
        JDCadastrarColetor.getContentPane().setLayout(JDCadastrarColetorLayout);
        JDCadastrarColetorLayout.setHorizontalGroup(
            JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposObrigatorios5)
                    .addGroup(JDCadastrarColetorLayout.createSequentialGroup()
                        .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDCadastrarColetorLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LCodNovoColetor)
                                    .addComponent(LTipoColetor)))
                            .addComponent(LNomeColetor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BCadastrarColetor)
                            .addComponent(campoNomeColetor, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(campoCodigoColetor)
                            .addComponent(CBTipoColetor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BNovoTipoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        JDCadastrarColetorLayout.setVerticalGroup(
            JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDCadastrarColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodNovoColetor)
                    .addComponent(campoCodigoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNomeColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNomeColetor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LTipoColetor)
                        .addComponent(CBTipoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoTipoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarColetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposObrigatorios5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarDoador.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarDoador.setTitle("SGACERVO - Cadastrar Doador");
        JDCadastrarDoador.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarDoador.setResizable(false);

        LCodDoador.setText("Código do Doador:");

        campoCodigoDoador.setEnabled(false);

        LNomeDoador.setText("* Nome do Doador:");

        BCadastrarDoador.setText("Cadastrar");
        BCadastrarDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarDoadorActionPerformed(evt);
            }
        });

        LCamposObrigatorios2.setText("* Campos de preenchimento obrigatório.");

        javax.swing.GroupLayout JDCadastrarDoadorLayout = new javax.swing.GroupLayout(JDCadastrarDoador.getContentPane());
        JDCadastrarDoador.getContentPane().setLayout(JDCadastrarDoadorLayout);
        JDCadastrarDoadorLayout.setHorizontalGroup(
            JDCadastrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarDoadorLayout.createSequentialGroup()
                .addGroup(JDCadastrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDCadastrarDoadorLayout.createSequentialGroup()
                            .addGap(13, 13, 13)
                            .addComponent(LCodDoador)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(campoCodigoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(JDCadastrarDoadorLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(LNomeDoador)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(JDCadastrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BCadastrarDoador)
                                .addComponent(campoNomeDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(JDCadastrarDoadorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LCamposObrigatorios2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarDoadorLayout.setVerticalGroup(
            JDCadastrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarDoadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodDoador)
                    .addComponent(campoCodigoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNomeDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNomeDoador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarDoador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LCamposObrigatorios2)
                .addContainerGap())
        );

        JDExcluirColetor.setTitle("SGACERVO - Excluir Coletor");
        JDExcluirColetor.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirColetor.setResizable(false);

        LCodigo_ColetorExcluirColetor.setText("Código de Doador:");

        campoColetorExcluirColetor.setEditable(false);
        campoColetorExcluirColetor.setEnabled(false);

        BConfirmarExcluirColetor.setText("Excluir");
        BConfirmarExcluirColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirColetorActionPerformed(evt);
            }
        });

        BCancelarExcluirColetor.setText("Cancelar");
        BCancelarExcluirColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirColetorActionPerformed(evt);
            }
        });

        LCautionExcluirColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirColetor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirColetor.setText("Tem certeza que deseja excluir esse doador?");

        javax.swing.GroupLayout JDExcluirColetorLayout = new javax.swing.GroupLayout(JDExcluirColetor.getContentPane());
        JDExcluirColetor.getContentPane().setLayout(JDExcluirColetorLayout);
        JDExcluirColetorLayout.setHorizontalGroup(
            JDExcluirColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirColetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirColetor)
                    .addGroup(JDExcluirColetorLayout.createSequentialGroup()
                        .addComponent(LCodigo_ColetorExcluirColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirColetorLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirColetor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoColetorExcluirColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirColetorLayout.setVerticalGroup(
            JDExcluirColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirColetorLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoColetorExcluirColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_ColetorExcluirColetor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirColetor)
                            .addComponent(BCancelarExcluirColetor)))
                    .addComponent(LCautionExcluirColetor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirContainer.setTitle("SGACERVO - Excluir Container");
        JDExcluirContainer.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirContainer.setResizable(false);

        LCodigo_ContainerExcluirContainer.setText("Código de Container:");

        campoContainerExcluirContainer.setEditable(false);
        campoContainerExcluirContainer.setEnabled(false);

        BConfirmarExcluirContainer.setText("Excluir");
        BConfirmarExcluirContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirContainerActionPerformed(evt);
            }
        });

        BCancelarExcluirContainer.setText("Cancelar");
        BCancelarExcluirContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirContainerActionPerformed(evt);
            }
        });

        LCautionExcluirContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirContainer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirContainer.setText("Tem certeza que deseja excluir esse Container?");

        javax.swing.GroupLayout JDExcluirContainerLayout = new javax.swing.GroupLayout(JDExcluirContainer.getContentPane());
        JDExcluirContainer.getContentPane().setLayout(JDExcluirContainerLayout);
        JDExcluirContainerLayout.setHorizontalGroup(
            JDExcluirContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirContainer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirContainer)
                    .addGroup(JDExcluirContainerLayout.createSequentialGroup()
                        .addComponent(LCodigo_ContainerExcluirContainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirContainerLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirContainer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoContainerExcluirContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirContainerLayout.setVerticalGroup(
            JDExcluirContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirContainerLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirContainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoContainerExcluirContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_ContainerExcluirContainer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirContainer)
                            .addComponent(BCancelarExcluirContainer)))
                    .addComponent(LCautionExcluirContainer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirDoacao.setTitle("SGACERVO - Excluir Doação");
        JDExcluirDoacao.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirDoacao.setResizable(false);

        LCodigo_doacaoExcluirDoacao.setText("Código de Doação:");

        campoDoacaoExcluirDoacao.setEditable(false);
        campoDoacaoExcluirDoacao.setEnabled(false);

        BConfirmarExcluirDoacao.setText("Excluir");
        BConfirmarExcluirDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirDoacaoActionPerformed(evt);
            }
        });

        BCancelarExcluirDoacao.setText("Cancelar");
        BCancelarExcluirDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirDoacaoActionPerformed(evt);
            }
        });

        LCautionExcluirDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirDoacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirDoacao.setText("Tem certeza que deseja excluir essa doação?");

        javax.swing.GroupLayout JDExcluirDoacaoLayout = new javax.swing.GroupLayout(JDExcluirDoacao.getContentPane());
        JDExcluirDoacao.getContentPane().setLayout(JDExcluirDoacaoLayout);
        JDExcluirDoacaoLayout.setHorizontalGroup(
            JDExcluirDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirDoacao)
                    .addGroup(JDExcluirDoacaoLayout.createSequentialGroup()
                        .addComponent(LCodigo_doacaoExcluirDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirDoacaoLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirDoacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoDoacaoExcluirDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirDoacaoLayout.setVerticalGroup(
            JDExcluirDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirDoacaoLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoDoacaoExcluirDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_doacaoExcluirDoacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirDoacao)
                            .addComponent(BCancelarExcluirDoacao))
                        .addGap(56, 72, Short.MAX_VALUE))
                    .addGroup(JDExcluirDoacaoLayout.createSequentialGroup()
                        .addComponent(LCautionExcluirDoacao)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        JDExcluirDoador.setTitle("SGACERVO - Excluir Doador");
        JDExcluirDoador.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirDoador.setResizable(false);

        LCodigo_doadorExcluirDoador.setText("Código de Doador:");

        campoDoadorExcluirDoador.setEditable(false);
        campoDoadorExcluirDoador.setEnabled(false);

        BConfirmarExcluirDoador.setText("Excluir");
        BConfirmarExcluirDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirDoadorActionPerformed(evt);
            }
        });

        BCancelarExcluirDoador.setText("Cancelar");
        BCancelarExcluirDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirDoadorActionPerformed(evt);
            }
        });

        LCautionExcluirDoador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirDoador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirDoador.setText("Tem certeza que deseja excluir esse doador?");

        javax.swing.GroupLayout JDExcluirDoadorLayout = new javax.swing.GroupLayout(JDExcluirDoador.getContentPane());
        JDExcluirDoador.getContentPane().setLayout(JDExcluirDoadorLayout);
        JDExcluirDoadorLayout.setHorizontalGroup(
            JDExcluirDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirDoadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirDoador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirDoador)
                    .addGroup(JDExcluirDoadorLayout.createSequentialGroup()
                        .addComponent(LCodigo_doadorExcluirDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirDoadorLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirDoador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoDoadorExcluirDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirDoadorLayout.setVerticalGroup(
            JDExcluirDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirDoadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirDoadorLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoDoadorExcluirDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_doadorExcluirDoador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirDoador)
                            .addComponent(BCancelarExcluirDoador)))
                    .addComponent(LCautionExcluirDoador))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirImagem.setTitle("SGACERVO - Excluir Imagem");
        JDExcluirImagem.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirImagem.setResizable(false);

        LCodigo_ImagemExcluirImagem.setText("Código de Imagem:");

        campoImagemExcluirImagem.setEditable(false);
        campoImagemExcluirImagem.setEnabled(false);

        BConfirmarExcluirImagem.setText("Excluir");
        BConfirmarExcluirImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirImagemActionPerformed(evt);
            }
        });

        BCancelarExcluirImagem.setText("Cancelar");
        BCancelarExcluirImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirImagemActionPerformed(evt);
            }
        });

        LCautionExcluirImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirImagem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirImagem.setText("Tem certeza que deseja excluir essa Imagem?");

        javax.swing.GroupLayout JDExcluirImagemLayout = new javax.swing.GroupLayout(JDExcluirImagem.getContentPane());
        JDExcluirImagem.getContentPane().setLayout(JDExcluirImagemLayout);
        JDExcluirImagemLayout.setHorizontalGroup(
            JDExcluirImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirImagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirImagem)
                    .addGroup(JDExcluirImagemLayout.createSequentialGroup()
                        .addComponent(LCodigo_ImagemExcluirImagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirImagemLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirImagem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoImagemExcluirImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirImagemLayout.setVerticalGroup(
            JDExcluirImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirImagemLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirImagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoImagemExcluirImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_ImagemExcluirImagem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirImagem)
                            .addComponent(BCancelarExcluirImagem)))
                    .addComponent(LCautionExcluirImagem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirItemAcervo.setTitle("SGACERVO - Excluir Item Acervo");
        JDExcluirItemAcervo.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirItemAcervo.setResizable(false);

        LCodigo_ItemAcervoExcluirItemAcervo.setText("Código de Item Acervo:");

        campoItemAcervoExcluirItemAcervo.setEditable(false);
        campoItemAcervoExcluirItemAcervo.setEnabled(false);

        BConfirmarExcluirItemAcervo.setText("Excluir");
        BConfirmarExcluirItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirItemAcervoActionPerformed(evt);
            }
        });

        BCancelarExcluirItemAcervo.setText("Cancelar");
        BCancelarExcluirItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirItemAcervoActionPerformed(evt);
            }
        });

        LCautionExcluirItemAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirItemAcervo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirItemAcervo.setText("Tem certeza que deseja excluir esse item do Acervo?");

        javax.swing.GroupLayout JDExcluirItemAcervoLayout = new javax.swing.GroupLayout(JDExcluirItemAcervo.getContentPane());
        JDExcluirItemAcervo.getContentPane().setLayout(JDExcluirItemAcervoLayout);
        JDExcluirItemAcervoLayout.setHorizontalGroup(
            JDExcluirItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirItemAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirItemAcervo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirItemAcervo)
                    .addGroup(JDExcluirItemAcervoLayout.createSequentialGroup()
                        .addComponent(LCodigo_ItemAcervoExcluirItemAcervo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirItemAcervoLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirItemAcervo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoItemAcervoExcluirItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirItemAcervoLayout.setVerticalGroup(
            JDExcluirItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirItemAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirItemAcervoLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirItemAcervo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoItemAcervoExcluirItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_ItemAcervoExcluirItemAcervo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirItemAcervo)
                            .addComponent(BCancelarExcluirItemAcervo)))
                    .addComponent(LCautionExcluirItemAcervo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirItemDoacao.setTitle("SGACERVO - Excluir Item Doação");
        JDExcluirItemDoacao.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirItemDoacao.setResizable(false);

        LCodigo_ItemDoacaoExcluirItemDoacao.setText("Código de Item Doação:");

        campoItemDoacaoExcluirItemDoacao.setEditable(false);
        campoItemDoacaoExcluirItemDoacao.setEnabled(false);

        BConfirmarExcluirItemDoacao.setText("Excluir");
        BConfirmarExcluirItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirItemDoacaoActionPerformed(evt);
            }
        });

        BCancelarExcluirItemDoacao.setText("Cancelar");
        BCancelarExcluirItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirItemDoacaoActionPerformed(evt);
            }
        });

        LCautionExcluirItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirItemDoacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirItemDoacao.setText("Tem certeza que deseja excluir esse item de doação?");

        javax.swing.GroupLayout JDExcluirItemDoacaoLayout = new javax.swing.GroupLayout(JDExcluirItemDoacao.getContentPane());
        JDExcluirItemDoacao.getContentPane().setLayout(JDExcluirItemDoacaoLayout);
        JDExcluirItemDoacaoLayout.setHorizontalGroup(
            JDExcluirItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirItemDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirItemDoacao)
                    .addGroup(JDExcluirItemDoacaoLayout.createSequentialGroup()
                        .addComponent(LCodigo_ItemDoacaoExcluirItemDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirItemDoacaoLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirItemDoacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoItemDoacaoExcluirItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirItemDoacaoLayout.setVerticalGroup(
            JDExcluirItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirItemDoacaoLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirItemDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoItemDoacaoExcluirItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_ItemDoacaoExcluirItemDoacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirItemDoacao)
                            .addComponent(BCancelarExcluirItemDoacao)))
                    .addComponent(LCautionExcluirItemDoacao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirItemRepasse.setTitle("SGACERVO - Excluir Item Repasse");
        JDExcluirItemRepasse.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirItemRepasse.setResizable(false);

        LCodigo_ItemRepasseExcluirItemRepasse.setText("Código de Item Doação:");

        campoItemRepasseExcluirItemRepasse.setEditable(false);
        campoItemRepasseExcluirItemRepasse.setEnabled(false);

        BConfirmarExcluirItemRepasse.setText("Excluir");
        BConfirmarExcluirItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirItemRepasseActionPerformed(evt);
            }
        });

        BCancelarExcluirItemRepasse.setText("Cancelar");
        BCancelarExcluirItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirItemRepasseActionPerformed(evt);
            }
        });

        LCautionExcluirItemRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirItemRepasse.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirItemRepasse.setText("Tem certeza que deseja excluir esse item de doação?");

        javax.swing.GroupLayout JDExcluirItemRepasseLayout = new javax.swing.GroupLayout(JDExcluirItemRepasse.getContentPane());
        JDExcluirItemRepasse.getContentPane().setLayout(JDExcluirItemRepasseLayout);
        JDExcluirItemRepasseLayout.setHorizontalGroup(
            JDExcluirItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirItemRepasse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirItemRepasse)
                    .addGroup(JDExcluirItemRepasseLayout.createSequentialGroup()
                        .addComponent(LCodigo_ItemRepasseExcluirItemRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirItemRepasseLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirItemRepasse)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoItemRepasseExcluirItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirItemRepasseLayout.setVerticalGroup(
            JDExcluirItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirItemRepasseLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirItemRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoItemRepasseExcluirItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_ItemRepasseExcluirItemRepasse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirItemRepasse)
                            .addComponent(BCancelarExcluirItemRepasse)))
                    .addComponent(LCautionExcluirItemRepasse))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirRepasse.setTitle("SGACERVO - Excluir Repasse");
        JDExcluirRepasse.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirRepasse.setResizable(false);

        LCodigo_RepasseExcluirRepasse.setText("Código de Repasse:");

        campoRepasseExcluirRepasse.setEditable(false);
        campoRepasseExcluirRepasse.setEnabled(false);

        BConfirmarExcluirRepasse.setText("Excluir");
        BConfirmarExcluirRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirRepasseActionPerformed(evt);
            }
        });

        BCancelarExcluirRepasse.setText("Cancelar");
        BCancelarExcluirRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirRepasseActionPerformed(evt);
            }
        });

        LCautionExcluirRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirRepasse.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirRepasse.setText("Tem certeza que deseja excluir esse repasse?");

        javax.swing.GroupLayout JDExcluirRepasseLayout = new javax.swing.GroupLayout(JDExcluirRepasse.getContentPane());
        JDExcluirRepasse.getContentPane().setLayout(JDExcluirRepasseLayout);
        JDExcluirRepasseLayout.setHorizontalGroup(
            JDExcluirRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirRepasse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirRepasse)
                    .addGroup(JDExcluirRepasseLayout.createSequentialGroup()
                        .addComponent(LCodigo_RepasseExcluirRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirRepasseLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirRepasse)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoRepasseExcluirRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirRepasseLayout.setVerticalGroup(
            JDExcluirRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirRepasseLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoRepasseExcluirRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_RepasseExcluirRepasse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirRepasse)
                            .addComponent(BCancelarExcluirRepasse)))
                    .addComponent(LCautionExcluirRepasse))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDExcluirUsuario.setTitle("SGACERVO - Excluir Usuário");
        JDExcluirUsuario.setMinimumSize(new java.awt.Dimension(450, 225));
        JDExcluirUsuario.setResizable(false);

        LCodigo_UsuarioExcluirUsuario.setText("Código de Usuário:");

        campoUsuarioExcluirUsuario.setEditable(false);
        campoUsuarioExcluirUsuario.setEnabled(false);

        LCautionExcluirUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/caution128.png"))); // NOI18N

        LConfirmaExcluirUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LConfirmaExcluirUsuario.setText("Tem certeza que deseja excluir esse Usuário?");

        BConfirmarExcluirUsuario.setText("Excluir");
        BConfirmarExcluirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConfirmarExcluirUsuarioActionPerformed(evt);
            }
        });

        BCancelarExcluirUsuario.setText("Cancelar");
        BCancelarExcluirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCancelarExcluirUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDExcluirUsuarioLayout = new javax.swing.GroupLayout(JDExcluirUsuario.getContentPane());
        JDExcluirUsuario.getContentPane().setLayout(JDExcluirUsuarioLayout);
        JDExcluirUsuarioLayout.setHorizontalGroup(
            JDExcluirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDExcluirUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCautionExcluirUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDExcluirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LConfirmaExcluirUsuario)
                    .addGroup(JDExcluirUsuarioLayout.createSequentialGroup()
                        .addComponent(LCodigo_UsuarioExcluirUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDExcluirUsuarioLayout.createSequentialGroup()
                                .addComponent(BConfirmarExcluirUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCancelarExcluirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoUsuarioExcluirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDExcluirUsuarioLayout.setVerticalGroup(
            JDExcluirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDExcluirUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDExcluirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDExcluirUsuarioLayout.createSequentialGroup()
                        .addComponent(LConfirmaExcluirUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoUsuarioExcluirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCodigo_UsuarioExcluirUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDExcluirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BConfirmarExcluirUsuario)
                            .addComponent(BCancelarExcluirUsuario)))
                    .addComponent(LCautionExcluirUsuario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarEventoOrigem.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarEventoOrigem.setTitle("SGACERVO - Cadastrar Evento Origem");
        JDCadastrarEventoOrigem.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarEventoOrigem.setResizable(false);

        LCodEO.setText("Código de Evento Origem:");

        LNomeEO.setText("* Nome do Evento Origem:");

        LPO8.setText("* Campos de preenchimento obrigatório.");

        campoCodigoEventoOrigem.setEnabled(false);

        BCadastrarEventoOrigem.setText("Cadastrar");
        BCadastrarEventoOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarEventoOrigemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarEventoOrigemLayout = new javax.swing.GroupLayout(JDCadastrarEventoOrigem.getContentPane());
        JDCadastrarEventoOrigem.getContentPane().setLayout(JDCadastrarEventoOrigemLayout);
        JDCadastrarEventoOrigemLayout.setHorizontalGroup(
            JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarEventoOrigemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarEventoOrigemLayout.createSequentialGroup()
                        .addGroup(JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeEO)
                            .addComponent(LCodEO))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarEventoOrigem)
                            .addGroup(JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoEventoOrigem, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoNomeEventoOrigem))))
                    .addComponent(LPO8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarEventoOrigemLayout.setVerticalGroup(
            JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarEventoOrigemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodEO)
                    .addComponent(campoCodigoEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarEventoOrigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeEO)
                    .addComponent(campoNomeEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarEventoOrigem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarDestinacao.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarDestinacao.setTitle("SGACERVO - Cadastrar Destinação");
        JDCadastrarDestinacao.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarDestinacao.setResizable(false);

        LCodDestinacao.setText("Código de Destinação:");

        LNomeDestinacao.setText("* Nome da Destinação:");

        LPO9.setText("* Campos de preenchimento obrigatório.");

        campoCodigoDestinacao.setEnabled(false);

        BCadastrarDestinacao.setText("Cadastrar");
        BCadastrarDestinacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarDestinacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarDestinacaoLayout = new javax.swing.GroupLayout(JDCadastrarDestinacao.getContentPane());
        JDCadastrarDestinacao.getContentPane().setLayout(JDCadastrarDestinacaoLayout);
        JDCadastrarDestinacaoLayout.setHorizontalGroup(
            JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarDestinacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarDestinacaoLayout.createSequentialGroup()
                        .addGroup(JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeDestinacao)
                            .addComponent(LCodDestinacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarDestinacao)
                            .addGroup(JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoDestinacao, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoNomeDestinacao))))
                    .addComponent(LPO9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDCadastrarDestinacaoLayout.setVerticalGroup(
            JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarDestinacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodDestinacao)
                    .addComponent(campoCodigoDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarDestinacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeDestinacao)
                    .addComponent(campoNomeDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarDestinacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCadastrarTipoColetor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDCadastrarTipoColetor.setTitle("SGACERVO - Cadastrar Tipo Item");
        JDCadastrarTipoColetor.setMinimumSize(new java.awt.Dimension(306, 143));
        JDCadastrarTipoColetor.setResizable(false);

        LCodTipoColetor.setText("Código do Tipo Coletor:");

        LTipoTipoColetor.setText("* Tipo do Coletor:");

        LPO10.setText("* Campos de preenchimento obrigatório.");

        campoCodigoTipoColetor.setEnabled(false);

        BCadastrarTipoColetor.setText("Cadastrar");
        BCadastrarTipoColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarTipoColetorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDCadastrarTipoColetorLayout = new javax.swing.GroupLayout(JDCadastrarTipoColetor.getContentPane());
        JDCadastrarTipoColetor.getContentPane().setLayout(JDCadastrarTipoColetorLayout);
        JDCadastrarTipoColetorLayout.setHorizontalGroup(
            JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTipoColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDCadastrarTipoColetorLayout.createSequentialGroup()
                        .addGroup(JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LTipoTipoColetor)
                            .addComponent(LCodTipoColetor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BCadastrarTipoColetor)
                            .addGroup(JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoCodigoTipoColetor, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(campoTipoColetor))))
                    .addComponent(LPO10))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        JDCadastrarTipoColetorLayout.setVerticalGroup(
            JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDCadastrarTipoColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodTipoColetor)
                    .addComponent(campoCodigoTipoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDCadastrarTipoColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTipoTipoColetor)
                    .addComponent(campoTipoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarTipoColetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDCarregandoDados.setTitle("SGACERVO - Aguarde");
        JDCarregandoDados.setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        JDCarregandoDados.setResizable(false);
        JDCarregandoDados.setType(java.awt.Window.Type.POPUP);
        JDCarregandoDados.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                JDCarregandoDadosWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout JDCarregandoDadosLayout = new javax.swing.GroupLayout(JDCarregandoDados.getContentPane());
        JDCarregandoDados.getContentPane().setLayout(JDCarregandoDadosLayout);
        JDCarregandoDadosLayout.setHorizontalGroup(
            JDCarregandoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        JDCarregandoDadosLayout.setVerticalGroup(
            JDCarregandoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );

        JDCarregandoDados.getAccessibleContext().setAccessibleDescription("");
        JDCarregandoDados.getAccessibleContext().setAccessibleParent(Principal);

        JFCarregandoDados.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                JFCarregandoDadosWindowActivated(evt);
            }
        });

        JPBCarregando.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        JPBCarregando.setString("Em progresso");
        JPBCarregando.setStringPainted(true);
        JPBCarregando.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JPBCarregandoStateChanged(evt);
            }
        });

        LCarregando.setText("Aguarde o carregamento dos dados do servidor...");

        javax.swing.GroupLayout JFCarregandoDadosLayout = new javax.swing.GroupLayout(JFCarregandoDados.getContentPane());
        JFCarregandoDados.getContentPane().setLayout(JFCarregandoDadosLayout);
        JFCarregandoDadosLayout.setHorizontalGroup(
            JFCarregandoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JFCarregandoDadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JFCarregandoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JFCarregandoDadosLayout.createSequentialGroup()
                        .addComponent(JPBCarregando, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JFCarregandoDadosLayout.createSequentialGroup()
                        .addComponent(LCarregando)
                        .addGap(58, 58, 58))))
        );
        JFCarregandoDadosLayout.setVerticalGroup(
            JFCarregandoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JFCarregandoDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCarregando)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPBCarregando, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JPBCarregando.getAccessibleContext().setAccessibleName("");
        JPBCarregando.getAccessibleContext().setAccessibleDescription("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SGACERVO - Principal");
        setMinimumSize(new java.awt.Dimension(700, 400));

        Principal.setForeground(new java.awt.Color(51, 51, 51));
        Principal.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        Principal.setName("SGACERVO - Principal"); // NOI18N
        Principal.setOpaque(true);
        Principal.setRequestFocusEnabled(false);

        Doacoes.setBackground(new java.awt.Color(153, 255, 153));
        Doacoes.setOpaque(true);

        LCodigo_doacao.setText("Código de Doação:");

        LUsuário.setText("Usuário:");

        LData.setText("Data:");

        LOrigem.setText("Evento de Origem:");

        Ldoador.setText("* Doador:");

        LCamposObrigatorios.setText("* Campos de preenchimento obrigatório.");

        campoDoacao.setEditable(false);
        campoDoacao.setEnabled(false);

        campoUsuarioDoacao.setEditable(false);
        campoUsuarioDoacao.setEnabled(false);

        campoDataDoacao.setEditable(false);
        campoDataDoacao.setEnabled(false);

        CBDoadorDoacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BCadastrarDoacao.setText("Cadastrar");
        BCadastrarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarDoacaoActionPerformed(evt);
            }
        });

        BNovoDoador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoDoadorActionPerformed(evt);
            }
        });

        CBEventoOrigem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoEventoOrigem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoEventoOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoEventoOrigemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarDoacaoLayout = new javax.swing.GroupLayout(CadastrarDoacao);
        CadastrarDoacao.setLayout(CadastrarDoacaoLayout);
        CadastrarDoacaoLayout.setHorizontalGroup(
            CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposObrigatorios)
                    .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                        .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LUsuário)
                            .addComponent(LCodigo_doacao)
                            .addComponent(LData)
                            .addComponent(LOrigem)
                            .addComponent(Ldoador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BCadastrarDoacao)
                            .addComponent(campoDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoUsuarioDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoDataDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CBDoadorDoacao, javax.swing.GroupLayout.Alignment.TRAILING, 0, 200, Short.MAX_VALUE)
                            .addComponent(CBEventoOrigem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BNovoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BNovoEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(471, Short.MAX_VALUE))
        );
        CadastrarDoacaoLayout.setVerticalGroup(
            CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodigo_doacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoUsuarioDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LUsuário))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDataDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LData, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                        .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LOrigem)
                            .addComponent(CBEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Ldoador)
                                .addComponent(CBDoadorDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BNovoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BCadastrarDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LCamposObrigatorios))
                    .addComponent(BNovoEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(310, Short.MAX_VALUE))
        );

        campoUsuarioDoacao.setText(nomeUsuario);

        Doacoes.addTab("Cadastrar Doação", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarDoacao); // NOI18N

        LItemDoacao.setText("Código Item Doação:");

        LUsuarioItemDoacao.setText("Usuário:");

        LDoadorItemDoacao.setText("Doador:");

        LDoacaoItemDoacao.setText("* Código Doação:");

        LQuantidadeItemDoacao.setText("* Quantidade:");

        LTipoItemDoacao.setText("* Tipo do Item:");

        LCamposObrigatorios1.setText("* Campos de preenchimento obrigatório.");

        campoItemDoacao.setEditable(false);
        campoItemDoacao.setEnabled(false);

        campoUsuarioItemDoacao.setEditable(false);
        campoUsuarioItemDoacao.setEnabled(false);

        campoDoadorItemDoacao.setEditable(false);
        campoDoadorItemDoacao.setEnabled(false);

        BCadastrarItemDoacao.setText("Cadastrar");
        BCadastrarItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarItemDoacaoActionPerformed(evt);
            }
        });

        CBTipoItemDoacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoTipoItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoItemDoacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarItemDoacaoLayout = new javax.swing.GroupLayout(CadastrarItemDoacao);
        CadastrarItemDoacao.setLayout(CadastrarItemDoacaoLayout);
        CadastrarItemDoacaoLayout.setHorizontalGroup(
            CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposObrigatorios1)
                    .addGroup(CadastrarItemDoacaoLayout.createSequentialGroup()
                        .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LItemDoacao)
                            .addComponent(LQuantidadeItemDoacao)
                            .addComponent(LUsuarioItemDoacao)
                            .addComponent(LDoadorItemDoacao)
                            .addComponent(LDoacaoItemDoacao)
                            .addComponent(LTipoItemDoacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BCadastrarItemDoacao)
                            .addComponent(campoUsuarioItemDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoQuantidadeItemDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoDoadorItemDoacao, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(campoDoacaoItemDoacao, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(CBTipoItemDoacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoItemDoacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BNovoTipoItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(461, Short.MAX_VALUE))
        );
        CadastrarItemDoacaoLayout.setVerticalGroup(
            CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LItemDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoUsuarioItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LUsuarioItemDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDoadorItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LDoadorItemDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDoacaoItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LDoacaoItemDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LTipoItemDoacao)
                        .addComponent(CBTipoItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoTipoItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoQuantidadeItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LQuantidadeItemDoacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarItemDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposObrigatorios1)
                .addContainerGap(284, Short.MAX_VALUE))
        );

        Doacoes.addTab("Cadastrar Item/Doação", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarItemDoacao); // NOI18N

        RelatorioDoacoes.setBorder(null);

        TDoacao.setAutoCreateRowSorter(true);
        TDoacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TDoacao.setRowHeight(30);
        RelatorioDoacoes.setViewportView(TDoacao);

        BExcluirDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirDoacaoActionPerformed(evt);
            }
        });

        BEditarDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarDoacaoActionPerformed(evt);
            }
        });

        BRelatorioDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioDoacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelDoacoesLayout = new javax.swing.GroupLayout(PainelDoacoes);
        PainelDoacoes.setLayout(PainelDoacoesLayout);
        PainelDoacoesLayout.setHorizontalGroup(
            PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RelatorioDoacoes, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BExcluirDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(BRelatorioDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelDoacoesLayout.setVerticalGroup(
            PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RelatorioDoacoes, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(PainelDoacoesLayout.createSequentialGroup()
                        .addGroup(PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BEditarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PainelDoacoesLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(BExcluirDoacao)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        Doacoes.addTab("Painel Doacoes", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelDoacoes); // NOI18N

        RelatorioItemDoacao.setBorder(null);

        TItemDoacao.setAutoCreateRowSorter(true);
        TItemDoacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TItemDoacao.setRowHeight(30);
        RelatorioItemDoacao.setViewportView(TItemDoacao);

        BEditarItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarItemDoacaoActionPerformed(evt);
            }
        });

        BExcluirItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirItemDoacaoActionPerformed(evt);
            }
        });

        BRelatorioItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioItemDoacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelItemDoacaoLayout = new javax.swing.GroupLayout(PainelItemDoacao);
        PainelItemDoacao.setLayout(PainelItemDoacaoLayout);
        PainelItemDoacaoLayout.setHorizontalGroup(
            PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RelatorioItemDoacao, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BEditarItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(BRelatorioItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelItemDoacaoLayout.setVerticalGroup(
            PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RelatorioItemDoacao, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(PainelItemDoacaoLayout.createSequentialGroup()
                        .addComponent(BEditarItemDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirItemDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        Doacoes.addTab("Painel Item-Doacao", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelItemDoacao); // NOI18N

        TEstoque.setAutoCreateRowSorter(true);
        TEstoque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TEstoque.setRowHeight(30);
        RelatorioEstoque.setViewportView(TEstoque);

        BRelatorioEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioEstoqueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelEstoqueLayout = new javax.swing.GroupLayout(PainelEstoque);
        PainelEstoque.setLayout(PainelEstoqueLayout);
        PainelEstoqueLayout.setHorizontalGroup(
            PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RelatorioEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BRelatorioEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap())
        );
        PainelEstoqueLayout.setVerticalGroup(
            PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RelatorioEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(PainelEstoqueLayout.createSequentialGroup()
                        .addComponent(BRelatorioEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        Doacoes.addTab("Painel Estoque", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelEstoque); // NOI18N

        TDoador.setAutoCreateRowSorter(true);
        TDoador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TDoador.setRowHeight(30);
        ExibirDoadores.setViewportView(TDoador);
        if (TDoador.getColumnModel().getColumnCount() > 0) {
            TDoador.getColumnModel().getColumn(0).setHeaderValue("Código do Doador");
            TDoador.getColumnModel().getColumn(1).setHeaderValue("Nome do Doador");
        }

        BExcluirDoador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirDoadorActionPerformed(evt);
            }
        });

        BEditarDoador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarDoadorActionPerformed(evt);
            }
        });

        BRelatorioDoadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioDoadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioDoadoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelDoadoresLayout = new javax.swing.GroupLayout(PainelDoadores);
        PainelDoadores.setLayout(PainelDoadoresLayout);
        PainelDoadoresLayout.setHorizontalGroup(
            PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ExibirDoadores, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BEditarDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BExcluirDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(BRelatorioDoadores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelDoadoresLayout.setVerticalGroup(
            PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ExibirDoadores, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(PainelDoadoresLayout.createSequentialGroup()
                        .addComponent(BEditarDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioDoadores, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        Doacoes.addTab("Painel Doadores", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelDoadores); // NOI18N

        Principal.addTab("Doações", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/doacao32.png")), Doacoes); // NOI18N

        Repasses.setBackground(new java.awt.Color(255, 51, 51));
        Repasses.setOpaque(true);

        LCodRepasse.setText("Código de Repasse:");

        LUsuarioRegistrarRepasse.setText("Usuário:");

        LDataRepasse.setText("Data:");

        LDescricaoRepasse.setText("* Destinação:");

        LColetorRepasse.setText("* Coletor:");

        LCamposObrigatorios3.setText("* Campos de preenchimento obrigatório.");

        campoRepasse.setEditable(false);
        campoRepasse.setEnabled(false);

        campoUsuarioRepasse.setEnabled(false);

        campoDataRepasse.setEditable(false);
        campoDataRepasse.setEnabled(false);

        BCadastrarRepasse.setText("Cadastrar");
        BCadastrarRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarRepasseActionPerformed(evt);
            }
        });

        CBColetor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoColetorActionPerformed(evt);
            }
        });

        CBDestinacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoDestinacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoDestinacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoDestinacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarRepasseLayout = new javax.swing.GroupLayout(CadastrarRepasse);
        CadastrarRepasse.setLayout(CadastrarRepasseLayout);
        CadastrarRepasseLayout.setHorizontalGroup(
            CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                        .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LUsuarioRegistrarRepasse)
                            .addComponent(LCodRepasse)
                            .addComponent(LDataRepasse)
                            .addComponent(LDescricaoRepasse)
                            .addComponent(LColetorRepasse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BCadastrarRepasse)
                            .addComponent(campoUsuarioRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(CBColetor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoDataRepasse, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoRepasse, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CBDestinacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BNovoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BNovoDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(LCamposObrigatorios3))
                .addContainerGap(460, Short.MAX_VALUE))
        );
        CadastrarRepasseLayout.setVerticalGroup(
            CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoUsuarioRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LUsuarioRegistrarRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDataRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LDataRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LDescricaoRepasse)
                        .addComponent(CBDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LColetorRepasse)
                        .addComponent(CBColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarRepasse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposObrigatorios3)
                .addContainerGap(310, Short.MAX_VALUE))
        );

        Repasses.addTab("Cadastrar Repasse", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarRepasse); // NOI18N

        LCodItemRepasse.setText("Código Item Repasse:");

        LCodUsuarioItemRepasse.setText(" Usuário:");

        LCodColetorItemRepasse.setText("Coletor:");

        LCodRepasseItemRepasse.setText("* Código Repasse:");

        LQuantidadeItemRepasse.setText("* Quantidade:");

        LTipoItemRepasse.setText("* Tipo do Item:");

        LCamposObrigatorios4.setText("* Campos de preenchimento obrigatório.");

        campoItemRepasse.setEnabled(false);

        campoUsuarioItemRepasse.setEditable(false);
        campoUsuarioItemRepasse.setEnabled(false);

        campoColetorItemRepasse.setEditable(false);
        campoColetorItemRepasse.setEnabled(false);

        BCadastrarItemRepasse.setText("Cadastrar");
        BCadastrarItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarItemRepasseActionPerformed(evt);
            }
        });

        CBTipoItemRepasse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoTipoItemDoacao1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoItemDoacao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoItemDoacao1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarItemRepasseLayout = new javax.swing.GroupLayout(CadastrarItemRepasse);
        CadastrarItemRepasse.setLayout(CadastrarItemRepasseLayout);
        CadastrarItemRepasseLayout.setHorizontalGroup(
            CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCamposObrigatorios4)
                    .addGroup(CadastrarItemRepasseLayout.createSequentialGroup()
                        .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LCodItemRepasse)
                            .addComponent(LCodUsuarioItemRepasse)
                            .addComponent(LCodColetorItemRepasse)
                            .addComponent(LCodRepasseItemRepasse)
                            .addComponent(LTipoItemRepasse)
                            .addComponent(LQuantidadeItemRepasse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BCadastrarItemRepasse)
                            .addComponent(campoItemRepasse)
                            .addComponent(campoUsuarioItemRepasse)
                            .addComponent(campoColetorItemRepasse)
                            .addComponent(campoRepasseItemRepasse)
                            .addComponent(campoQuantidadeItemRepasse)
                            .addComponent(CBTipoItemRepasse, 0, 200, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BNovoTipoItemDoacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(456, Short.MAX_VALUE))
        );
        CadastrarItemRepasseLayout.setVerticalGroup(
            CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodItemRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodUsuarioItemRepasse)
                    .addComponent(campoUsuarioItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoColetorItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodColetorItemRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoRepasseItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCodRepasseItemRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LTipoItemRepasse)
                        .addComponent(CBTipoItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoTipoItemDoacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoQuantidadeItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LQuantidadeItemRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarItemRepasse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposObrigatorios4)
                .addContainerGap(284, Short.MAX_VALUE))
        );

        Repasses.addTab("Cadastrar Item/Repasse", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarItemRepasse); // NOI18N

        TRepasse.setAutoCreateRowSorter(true);
        TRepasse.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TRepasse.setRowHeight(30);
        RelatorioRepasse.setViewportView(TRepasse);
        if (TRepasse.getColumnModel().getColumnCount() > 0) {
            TRepasse.getColumnModel().getColumn(0).setHeaderValue("Código de Repasse");
            TRepasse.getColumnModel().getColumn(1).setHeaderValue("Código de Coletor");
            TRepasse.getColumnModel().getColumn(2).setHeaderValue("Código de Usuário");
            TRepasse.getColumnModel().getColumn(3).setHeaderValue("Descrição");
            TRepasse.getColumnModel().getColumn(4).setHeaderValue("Data");
        }

        BEditarRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarRepasseActionPerformed(evt);
            }
        });

        BExcluirRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirRepasseActionPerformed(evt);
            }
        });

        BRelatorioRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioRepasseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelRepasseLayout = new javax.swing.GroupLayout(PainelRepasse);
        PainelRepasse.setLayout(PainelRepasseLayout);
        PainelRepasseLayout.setHorizontalGroup(
            PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RelatorioRepasse, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BExcluirRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BEditarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(BRelatorioRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelRepasseLayout.setVerticalGroup(
            PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelRepasseLayout.createSequentialGroup()
                        .addComponent(BEditarRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(RelatorioRepasse, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
                .addContainerGap())
        );

        Repasses.addTab("Painel de Repasses", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelRepasse); // NOI18N

        RelatorioItemRepasse.setBorder(null);

        TItemRepasse.setAutoCreateRowSorter(true);
        TItemRepasse.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TItemRepasse.setRowHeight(30);
        RelatorioItemRepasse.setViewportView(TItemRepasse);
        if (TItemRepasse.getColumnModel().getColumnCount() > 0) {
            TItemRepasse.getColumnModel().getColumn(0).setHeaderValue("Código do Repasse");
            TItemRepasse.getColumnModel().getColumn(1).setHeaderValue("Código de Item/Repasse");
            TItemRepasse.getColumnModel().getColumn(2).setHeaderValue("Código do Coletor");
            TItemRepasse.getColumnModel().getColumn(3).setHeaderValue("Código do Usuário");
            TItemRepasse.getColumnModel().getColumn(4).setHeaderValue("Nome do Item");
            TItemRepasse.getColumnModel().getColumn(5).setHeaderValue("Quantidade");
        }

        BEditarItemRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarItemRepasseActionPerformed(evt);
            }
        });

        BExcluirItemRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirItemRepasseActionPerformed(evt);
            }
        });

        BRelatorioItemRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioItemRepasseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelItemRepasseLayout = new javax.swing.GroupLayout(PainelItemRepasse);
        PainelItemRepasse.setLayout(PainelItemRepasseLayout);
        PainelItemRepasseLayout.setHorizontalGroup(
            PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RelatorioItemRepasse, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BEditarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(BRelatorioItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelItemRepasseLayout.setVerticalGroup(
            PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RelatorioItemRepasse, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(PainelItemRepasseLayout.createSequentialGroup()
                        .addComponent(BEditarItemRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirItemRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        Repasses.addTab("Painel Item Repasse", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelItemRepasse); // NOI18N

        ExibirColetores.setBorder(null);

        TColetor.setAutoCreateRowSorter(true);
        TColetor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TColetor.setRowHeight(30);
        ExibirColetores.setViewportView(TColetor);
        if (TColetor.getColumnModel().getColumnCount() > 0) {
            TColetor.getColumnModel().getColumn(0).setHeaderValue("Código do Coletor");
            TColetor.getColumnModel().getColumn(1).setHeaderValue("Nome do Coletor");
            TColetor.getColumnModel().getColumn(2).setHeaderValue("Tipo do Coletor");
        }

        BEditarColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarColetorActionPerformed(evt);
            }
        });

        BExcluirColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirColetorActionPerformed(evt);
            }
        });

        BRelatorioColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioColetorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelColetoresLayout = new javax.swing.GroupLayout(PainelColetores);
        PainelColetores.setLayout(PainelColetoresLayout);
        PainelColetoresLayout.setHorizontalGroup(
            PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelColetoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ExibirColetores, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BEditarColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(BRelatorioColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelColetoresLayout.setVerticalGroup(
            PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelColetoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ExibirColetores, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(PainelColetoresLayout.createSequentialGroup()
                        .addComponent(BEditarColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        Repasses.addTab("Painel Coletores", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelColetores); // NOI18N

        Principal.addTab("Repasses", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/repasse32.png")), Repasses); // NOI18N

        Acervo.setBackground(new java.awt.Color(0, 153, 255));
        Acervo.setOpaque(true);

        LCodItemAcervo.setText("Código de Item de Acervo:");

        LCodUsuario.setText("Usuário:");

        LDataAcervo.setText("Data:");

        LCod_Doador.setText("*Doador:");

        LTipoItemAcervo.setText("* Tipo do Item:");

        LMarca.setText("* Marca:");

        LModeloItemAcervo.setText("* Modelo:");

        LAnoItemAcervo.setText("*Ano:");

        LDescricao.setText("* Descrição:");

        LItemFunciona.setText("Item Funciona:");

        LCamposComplementares.setText("Campos Complementares (OPCIONAIS)");

        LInterface.setText("Interface");

        LTecnologia.setText("Tecnologia:");

        LCapacidade_MB.setText("Capacidade (MB):");

        LContainer.setText("Código de Container:");

        LPO.setText("* Campos de preenchimento obrigatório.");

        campoCodigoItemAcervo.setEditable(false);
        campoCodigoItemAcervo.setEnabled(false);

        campoUsuarioItemAcervo.setEnabled(false);

        campoDataItemAcervo.setEditable(false);
        campoDataItemAcervo.setEnabled(false);

        SPDescricaoItemAcervo.setViewportView(campoDescricaoItemAcervo);

        CBFunciona.setText("Funciona");

        CBMarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBModelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBInterface.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBTecnologia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoTipoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoItemActionPerformed(evt);
            }
        });

        BNovoMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoMarcaActionPerformed(evt);
            }
        });

        BNovoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoModeloActionPerformed(evt);
            }
        });

        BNovoInterface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoInterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoInterfaceActionPerformed(evt);
            }
        });

        BNovoTecnologia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTecnologia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTecnologiaActionPerformed(evt);
            }
        });

        BCadastrarItemAcervo.setText("Cadastrar");
        BCadastrarItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarItemAcervoActionPerformed(evt);
            }
        });

        CBDoadorItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoDoadorAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoDoadorAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoDoadorAcervoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarItemAcervoLayout = new javax.swing.GroupLayout(CadastrarItemAcervo);
        CadastrarItemAcervo.setLayout(CadastrarItemAcervoLayout);
        CadastrarItemAcervoLayout.setHorizontalGroup(
            CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(LDescricao)
                                .addComponent(LDataAcervo)
                                .addComponent(LCodUsuario)
                                .addComponent(LCodItemAcervo)
                                .addComponent(LCod_Doador)
                                .addComponent(LTipoItemAcervo)
                                .addComponent(LMarca)
                                .addComponent(LModeloItemAcervo)
                                .addComponent(LAnoItemAcervo))
                            .addComponent(LItemFunciona, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBFunciona)
                            .addComponent(BCadastrarItemAcervo)
                            .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campoAnoItemAcervo)
                                    .addComponent(campoUsuarioItemAcervo)
                                    .addComponent(CBTipoItemAcervo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CBMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CBDoadorItemAcervo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(campoDataItemAcervo)
                                    .addComponent(SPDescricaoItemAcervo)
                                    .addComponent(campoCodigoItemAcervo)
                                    .addComponent(CBModelo, 0, 200, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(BNovoDoadorAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(BNovoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(BNovoTipoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(BNovoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LContainer)
                                            .addComponent(LCapacidade_MB, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(LTecnologia)
                                    .addComponent(LInterface))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CBTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoCapacidadeItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoCodContainerCadastrarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BNovoTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                        .addComponent(CBInterface, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BNovoInterface, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(LCamposComplementares)))))
                    .addComponent(LPO))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        CadastrarItemAcervoLayout.setVerticalGroup(
            CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                        .addComponent(LCamposComplementares)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LInterface)
                                .addComponent(CBInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BNovoInterface, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LTecnologia)
                            .addComponent(CBTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BNovoTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LCapacidade_MB)
                            .addComponent(campoCapacidadeItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LContainer)
                            .addComponent(campoCodContainerCadastrarItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LCodItemAcervo)
                            .addComponent(campoCodigoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LCodUsuario)
                            .addComponent(campoUsuarioItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LDataAcervo)
                            .addComponent(campoDataItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LCod_Doador)
                                    .addComponent(CBDoadorItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BNovoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(BNovoTipoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(LTipoItemAcervo)
                                                .addComponent(CBTipoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(LMarca)
                                            .addComponent(CBMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LModeloItemAcervo)
                                        .addComponent(CBModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(BNovoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LAnoItemAcervo)
                                    .addComponent(campoAnoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LDescricao)
                                    .addComponent(SPDescricaoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CBFunciona)
                                    .addComponent(LItemFunciona))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCadastrarItemAcervo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LPO))
                            .addComponent(BNovoDoadorAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(125, Short.MAX_VALUE))))
        );

        Acervo.addTab("Cadastrar Item ao Acervo", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarItemAcervo); // NOI18N

        LImagemCadastrarImagem.setText("Codigo de Imagem:");

        LUsuarioCadastrarImagem.setText("Usuário:");

        LItemAcervoCadastrarImagem.setText("*Código de Item Acervo:");

        LLinkCadastrarImagem.setText("*Caminho/Link da Imagem:");

        campoCodImagemCadastrarImagem.setEditable(false);
        campoCodImagemCadastrarImagem.setEnabled(false);

        campoUsuarioCadastrarImagem.setEditable(false);
        campoUsuarioCadastrarImagem.setEnabled(false);

        campoLink.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoLinkKeyTyped(evt);
            }
        });

        BCadastrarImagem.setText("Cadastrar");
        BCadastrarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarImagemActionPerformed(evt);
            }
        });

        BCheckLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/check16.png"))); // NOI18N
        BCheckLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCheckLinkActionPerformed(evt);
            }
        });

        LFotoAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png"))); // NOI18N

        LCamposCadastrarImagem.setText("*Campos de preenchimento obrigatório.");

        javax.swing.GroupLayout CadastrarImagemLayout = new javax.swing.GroupLayout(CadastrarImagem);
        CadastrarImagem.setLayout(CadastrarImagemLayout);
        CadastrarImagemLayout.setHorizontalGroup(
            CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarImagemLayout.createSequentialGroup()
                        .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LUsuarioCadastrarImagem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LItemAcervoCadastrarImagem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LLinkCadastrarImagem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LImagemCadastrarImagem, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LFotoAcervo)
                            .addGroup(CadastrarImagemLayout.createSequentialGroup()
                                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(BCadastrarImagem)
                                        .addComponent(campoCodImagemCadastrarImagem)
                                        .addComponent(campoUsuarioCadastrarImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(campoItemAcervoCadastrarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(campoLink, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCheckLink, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(LCamposCadastrarImagem))
                .addContainerGap(431, Short.MAX_VALUE))
        );
        CadastrarImagemLayout.setVerticalGroup(
            CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LImagemCadastrarImagem)
                    .addComponent(campoCodImagemCadastrarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LUsuarioCadastrarImagem)
                    .addComponent(campoUsuarioCadastrarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LItemAcervoCadastrarImagem)
                    .addComponent(campoItemAcervoCadastrarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LLinkCadastrarImagem)
                        .addComponent(campoLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BCheckLink, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LFotoAcervo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarImagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposCadastrarImagem)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        Acervo.addTab("Cadastrar Imagem", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarImagem); // NOI18N

        LCodContainerCadastrarContainer.setText("Código de Container:");

        LUsuarioCadastrarContainer.setText("Usuário:");

        LLocalizacaoCadastrarContainer.setText("*Localização de Container:");

        LTipoContainerCadastrarContainer.setText("*Tipo de Container:");

        LCamposCadastrarContainer.setText("*Campos de preenchimento obrigatório.");

        campoCodContainerCadastrarContainer.setEditable(false);
        campoCodContainerCadastrarContainer.setEnabled(false);

        campoUsuarioCadastrarContainer.setEditable(false);
        campoUsuarioCadastrarContainer.setEnabled(false);

        CBTipoContainerCadastrarContainer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BCadastrarContainer.setText("Cadastrar");
        BCadastrarContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarContainerActionPerformed(evt);
            }
        });

        BNovoTipoContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoContainerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarContainerLayout = new javax.swing.GroupLayout(CadastrarContainer);
        CadastrarContainer.setLayout(CadastrarContainerLayout);
        CadastrarContainerLayout.setHorizontalGroup(
            CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarContainerLayout.createSequentialGroup()
                        .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(CadastrarContainerLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LCodContainerCadastrarContainer)
                                    .addComponent(LUsuarioCadastrarContainer))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoUsuarioCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoCodContainerCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(CadastrarContainerLayout.createSequentialGroup()
                                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LLocalizacaoCadastrarContainer)
                                    .addComponent(LTipoContainerCadastrarContainer))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BCadastrarContainer)
                                    .addComponent(campoLocalizacaoCadastrarContainer)
                                    .addComponent(CBTipoContainerCadastrarContainer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BNovoTipoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LCamposCadastrarContainer))
                .addContainerGap(435, Short.MAX_VALUE))
        );
        CadastrarContainerLayout.setVerticalGroup(
            CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodContainerCadastrarContainer)
                    .addComponent(campoCodContainerCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LUsuarioCadastrarContainer)
                    .addComponent(campoUsuarioCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LLocalizacaoCadastrarContainer)
                    .addComponent(campoLocalizacaoCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LTipoContainerCadastrarContainer)
                        .addComponent(CBTipoContainerCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoTipoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarContainer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposCadastrarContainer)
                .addContainerGap(336, Short.MAX_VALUE))
        );

        Acervo.addTab("Cadastrar Container", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarContainer); // NOI18N

        RelatorioAcervo.setBorder(null);

        TAcervo.setAutoCreateRowSorter(true);
        TAcervo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TAcervo.setRowHeight(30);
        RelatorioAcervo.setViewportView(TAcervo);
        if (TAcervo.getColumnModel().getColumnCount() > 0) {
            TAcervo.getColumnModel().getColumn(0).setHeaderValue("Código do Item do Acervo");
            TAcervo.getColumnModel().getColumn(1).setHeaderValue("Código de Usuário");
            TAcervo.getColumnModel().getColumn(2).setHeaderValue("Código de Container");
            TAcervo.getColumnModel().getColumn(3).setHeaderValue("Data");
            TAcervo.getColumnModel().getColumn(4).setHeaderValue("Tipo");
            TAcervo.getColumnModel().getColumn(5).setHeaderValue("Marca");
            TAcervo.getColumnModel().getColumn(6).setHeaderValue("Modelo");
            TAcervo.getColumnModel().getColumn(7).setHeaderValue("Ano");
            TAcervo.getColumnModel().getColumn(8).setHeaderValue("Funciona");
        }

        BEditarItemAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarItemAcervoActionPerformed(evt);
            }
        });

        BExcluirItemAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirItemAcervoActionPerformed(evt);
            }
        });

        BRelatorioAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioAcervoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelAcervoLayout = new javax.swing.GroupLayout(PainelAcervo);
        PainelAcervo.setLayout(PainelAcervoLayout);
        PainelAcervoLayout.setHorizontalGroup(
            PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RelatorioAcervo, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BExcluirItemAcervo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarItemAcervo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelAcervoLayout.setVerticalGroup(
            PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RelatorioAcervo, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(PainelAcervoLayout.createSequentialGroup()
                        .addComponent(BEditarItemAcervo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirItemAcervo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        Acervo.addTab("Acervo", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelAcervo); // NOI18N

        TImagem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TImagem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TImagem.setRowHeight(30);
        SPImagem.setViewportView(TImagem);

        BEditarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarImagemActionPerformed(evt);
            }
        });

        BExcluirImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirImagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ImagensLayout = new javax.swing.GroupLayout(Imagens);
        Imagens.setLayout(ImagensLayout);
        ImagensLayout.setHorizontalGroup(
            ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SPImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BExcluirImagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarImagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        ImagensLayout.setVerticalGroup(
            ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ImagensLayout.createSequentialGroup()
                        .addComponent(BEditarImagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirImagem)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(SPImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addContainerGap())
        );

        Acervo.addTab("Imagens", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), Imagens); // NOI18N

        SPContainer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        TContainer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TContainer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TContainer.setRowHeight(30);
        SPContainer.setViewportView(TContainer);

        BEditarContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarContainerActionPerformed(evt);
            }
        });

        BExcluirContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirContainerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SPContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BExcluirContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addComponent(BEditarContainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirContainer)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(SPContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addContainerGap())
        );

        Acervo.addTab("Containeres", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), Container); // NOI18N

        Principal.addTab("Acervo", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/acervo32.png")), Acervo); // NOI18N

        Usuarios.setBackground(new java.awt.Color(255, 153, 255));
        Usuarios.setOpaque(true);

        LCodNovoUsuario.setText("Código de Usuário:");

        LNomeUsuario.setText("* Nome do Usuário:");

        LRegistroAcademico.setText("*Registro Acadêmico:");

        LEmail.setText("*E-mail:");

        LSenha.setText("* Senha:");

        LRepetirSenha.setText("*Repetir Senha:");

        LPermissão.setText("* Permissão:");

        LCamposCadastrarUsuario.setText("* Campos de preenchimento obrigatório.");

        campoCodigoNovoUsuario.setEditable(false);
        campoCodigoNovoUsuario.setEnabled(false);

        CBAdministrador.setText("Administrador");

        BCadastrarUsuario.setText("Cadastrar");
        BCadastrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarUsuarioLayout = new javax.swing.GroupLayout(CadastrarUsuario);
        CadastrarUsuario.setLayout(CadastrarUsuarioLayout);
        CadastrarUsuarioLayout.setHorizontalGroup(
            CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarUsuarioLayout.createSequentialGroup()
                        .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeUsuario)
                            .addComponent(LCodNovoUsuario)
                            .addComponent(LSenha)
                            .addComponent(LPermissão)
                            .addComponent(LRepetirSenha)
                            .addComponent(LRegistroAcademico)
                            .addComponent(LEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BCadastrarUsuario)
                            .addComponent(campoCodigoNovoUsuario)
                            .addComponent(campoNomeNovoUsuario)
                            .addComponent(campoRegistroAcademicoCadastrarUsuario)
                            .addComponent(campoRepetirSenhaCadastrarUsuario, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoSenhaCadastrarUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(CBAdministrador)
                            .addComponent(campoEmailCadastrarUsuario)))
                    .addGroup(CadastrarUsuarioLayout.createSequentialGroup()
                        .addComponent(LCamposCadastrarUsuario)
                        .addGap(109, 109, 109)))
                .addContainerGap(488, Short.MAX_VALUE))
        );
        CadastrarUsuarioLayout.setVerticalGroup(
            CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodNovoUsuario)
                    .addComponent(campoCodigoNovoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeUsuario)
                    .addComponent(campoNomeNovoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LRegistroAcademico)
                    .addComponent(campoRegistroAcademicoCadastrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LEmail)
                    .addComponent(campoEmailCadastrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LSenha)
                    .addComponent(campoSenhaCadastrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LRepetirSenha)
                    .addComponent(campoRepetirSenhaCadastrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LPermissão)
                    .addComponent(CBAdministrador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposCadastrarUsuario)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        Usuarios.addTab("Cadastrar Usuário", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarUsuario); // NOI18N

        ExibirUsuarios.setBorder(null);

        TUsuario.setAutoCreateRowSorter(true);
        TUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TUsuario.setRowHeight(30);
        ExibirUsuarios.setViewportView(TUsuario);

        BEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarUsuarioActionPerformed(evt);
            }
        });

        BExcluirUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelUsuarioLayout = new javax.swing.GroupLayout(PainelUsuario);
        PainelUsuario.setLayout(PainelUsuarioLayout);
        PainelUsuarioLayout.setHorizontalGroup(
            PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ExibirUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BExcluirUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelUsuarioLayout.setVerticalGroup(
            PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelUsuarioLayout.createSequentialGroup()
                        .addComponent(BEditarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirUsuario)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ExibirUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addContainerGap())
        );

        Usuarios.addTab("Usuarios", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelUsuario); // NOI18N

        Principal.addTab("Usuários", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/users.png")), Usuarios); // NOI18N

        LCodUsuarioInfo.setText("Código do Usuário:");

        LNomeUsuarioInfo.setText("** Nome do Usuário:");

        LSenhaAlterar.setText("* Nova Senha:");

        LAlterar.setText("** Campos que você precisa de permissões de administrador pra alterar.");

        LUsuarioAdm.setText("**Usuário Administrador");

        LRegistroAcademicoAlterarUsuario.setText("**Registro Academico:");

        LCamposRegularesAlterarUsuario.setText("* Campos que você pode alterar.");

        LRepetirSenhaAlterarUsuario.setText("*Repetir Senha:");

        LEmailAlterarUsuario.setText("*E-mail:");

        campoCodigoAlterarUsuario.setEditable(false);
        campoCodigoAlterarUsuario.setEnabled(false);

        campoNomeAlterarUsuario.setEditable(false);
        campoNomeAlterarUsuario.setEnabled(false);

        campoRegistroAcademicoAlterarUsuario.setEditable(false);
        campoRegistroAcademicoAlterarUsuario.setEnabled(false);

        campoAdministradorAlterarUsuario.setText("Administrador");
        campoAdministradorAlterarUsuario.setEnabled(false);

        BAlterarUsuario.setText("Salvar");
        BAlterarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAlterarUsuarioActionPerformed(evt);
            }
        });

        jLabel1.setText("Senha atual:");

        javax.swing.GroupLayout SuasInformacoesLayout = new javax.swing.GroupLayout(SuasInformacoes);
        SuasInformacoes.setLayout(SuasInformacoesLayout);
        SuasInformacoesLayout.setHorizontalGroup(
            SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuasInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LAlterar)
                    .addGroup(SuasInformacoesLayout.createSequentialGroup()
                        .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LNomeUsuarioInfo)
                            .addComponent(LCodUsuarioInfo)
                            .addComponent(LSenhaAlterar)
                            .addComponent(LRegistroAcademicoAlterarUsuario)
                            .addComponent(LCamposRegularesAlterarUsuario)
                            .addComponent(LUsuarioAdm)
                            .addComponent(LRepetirSenhaAlterarUsuario)
                            .addComponent(LEmailAlterarUsuario)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoAdministradorAlterarUsuario)
                            .addComponent(campoNovaSenhaAlterarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(campoCodigoAlterarUsuario)
                            .addComponent(campoNomeAlterarUsuario)
                            .addComponent(BAlterarUsuario)
                            .addComponent(campoRegistroAcademicoAlterarUsuario)
                            .addComponent(campoEmailAlterarUsuario)
                            .addComponent(campoRepetirSenhaAlterarUsuario)
                            .addComponent(campoSenhaAtualAlterarUsuario))))
                .addContainerGap(432, Short.MAX_VALUE))
        );
        SuasInformacoesLayout.setVerticalGroup(
            SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuasInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LCodUsuarioInfo)
                    .addComponent(campoCodigoAlterarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNomeUsuarioInfo)
                    .addComponent(campoNomeAlterarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LRegistroAcademicoAlterarUsuario)
                    .addComponent(campoRegistroAcademicoAlterarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoSenhaAtualAlterarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LSenhaAlterar)
                    .addComponent(campoNovaSenhaAlterarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LRepetirSenhaAlterarUsuario)
                    .addComponent(campoRepetirSenhaAlterarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LEmailAlterarUsuario)
                    .addComponent(campoEmailAlterarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SuasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LUsuarioAdm)
                    .addComponent(campoAdministradorAlterarUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAlterarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposRegularesAlterarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LAlterar)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        AbaDoUsuario.addTab("Editar Suas Informações", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png")), SuasInformacoes); // NOI18N

        BDeslogar.setText("Deslogar");
        BDeslogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDeslogarActionPerformed(evt);
            }
        });

        LCertezaDeslogar.setText("Tem certeza que deseja deslogar?");

        javax.swing.GroupLayout DeslogarLayout = new javax.swing.GroupLayout(Deslogar);
        Deslogar.setLayout(DeslogarLayout);
        DeslogarLayout.setHorizontalGroup(
            DeslogarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeslogarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DeslogarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LCertezaDeslogar)
                    .addComponent(BDeslogar))
                .addContainerGap(632, Short.MAX_VALUE))
        );
        DeslogarLayout.setVerticalGroup(
            DeslogarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeslogarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCertezaDeslogar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BDeslogar)
                .addContainerGap(440, Short.MAX_VALUE))
        );

        AbaDoUsuario.addTab("Deslogar", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/logout.png")), Deslogar); // NOI18N

        Principal.addTab("AbaDoUsuario", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/userlittle.png")), AbaDoUsuario); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Principal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Principal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void BCadastrarDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarDoadorActionPerformed
        // TODO add your handling code here:

        
        Doador d=new Doador();
        
        try{        
            d.setNome_doador(campoNomeDoador.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(CadastrarDoacao, "Campos de preenchimento obrigatório estão vazios.\nPreencha e tente novamente.","Erro",0);
        }
        DoadorDAO daod=new DoadorDAO();
        int sucesso=daod.inserir(d);
        daod.fechar();
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarDoador,"Falha na Inserção.","Erro",0);
        else {
            Banco b = new Banco();
            sucesso=b.max("SELECT MAX(cod_doador) from doador;");
            b.fechar();
            atualizarCBDoador();
            atualizarTBDoador();
            JDCadastrarDoador.dispose();
            JOptionPane.showMessageDialog(JDCadastrarDoador,"Código de Doador: "+sucesso,"Doador Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBDoadorDoacao.setSelectedIndex((CBDoadorDoacao.getItemCount()-1));
            CBDoadorItemAcervo.setSelectedIndex((CBDoadorDoacao.getItemCount()-1));
        }
        
    }//GEN-LAST:event_BCadastrarDoadorActionPerformed
    private void BCadastrarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarDoacaoActionPerformed
        // TODO add your handling code here:
        
        Doacao d=new Doacao();
        Doador doador;
        Evento_origem eo;
        
        d.setCod_usuario(codigoUsuario);
        
        DoadorDAO daodoador=new DoadorDAO();
        doador=daodoador.getByNome(CBDoadorDoacao.getSelectedItem().toString());
        daodoador.fechar();
        d.setCod_doador(doador.getCod_doador());
        
        Evento_origemDAO daoeo=new Evento_origemDAO();
        eo=daoeo.getByNome(CBEventoOrigem.getSelectedItem().toString());
        daoeo.fechar();
        d.setCod_evento_origem(eo.getCod_evento_origem());
        
        DoacaoDAO daod=new DoacaoDAO();
        int sucesso=daod.inserir(d);
        daod.fechar();
        if(sucesso==0) JOptionPane.showMessageDialog(CadastrarDoacao,"Falha na inserção.","Erro",0);
        else{
            
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_doacao) from doacao;");
            b.fechar();
            JOptionPane.showMessageDialog(CadastrarDoacao,"Código de Doação: "+sucesso,"Doação Registrada.",JOptionPane.INFORMATION_MESSAGE);
            atualizarTBDoacao();
        }
       atualizarCamposCadastrarDoacao();
    }//GEN-LAST:event_BCadastrarDoacaoActionPerformed
    private void BCadastrarItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarItemDoacaoActionPerformed
        // TODO add your handling code here:
        
        Item_doacao itemd=new Item_doacao();
        
        Doacao doaaux=new Doacao();
        
        Tipo_item t;
       
        int sucesso=0;
        try{
            DoacaoDAO daodoaaux=new DoacaoDAO();
            doaaux=daodoaaux.getByCod(Integer.parseInt(campoDoacaoItemDoacao.getText()));
            daodoaaux.fechar();
            itemd.setCod_doacao(Integer.parseInt(campoDoacaoItemDoacao.getText()));
            itemd.setQuantidade_item_doacao(Integer.parseInt(campoQuantidadeItemDoacao.getText()));
            Tipo_itemDAO daot=new Tipo_itemDAO();
            t=daot.getByNome(CBTipoItemDoacao.getSelectedItem().toString());
            daot.fechar();
            if(t==null) JOptionPane.showMessageDialog(CadastrarItemDoacao,"Tipo Inexistente.","Erro",0);
            else itemd.setCod_tipo(t.getCod_tipo());
            Item_doacaoDAO daoitemd=new Item_doacaoDAO();
            sucesso=daoitemd.inserir(itemd);
            daoitemd.fechar();
        }catch(NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(CadastrarItemDoacao,"Campos Incorretos.\nVerifique e tente novamente.","Erro",0);
        }
        finally{
            if (sucesso==0) {JOptionPane.showMessageDialog(CadastrarItemDoacao,"Falha na inserção.","Erro",0);
                            atualizarCamposCadastrarItemDoacao(true);}
            else {
                Banco b=new Banco();
                sucesso=b.max("SELECT MAX(cod_item_doacao) from item_doacao;");
                b.fechar();
                JOptionPane.showMessageDialog(CadastrarItemDoacao,"Código de Item-Doacao: "+sucesso,"Item-Doacao Registrado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBItemDoacao();
                atualizarTBEstoque();
                atualizarCamposCadastrarItemDoacao(false);
                }
            
        }
        
    }//GEN-LAST:event_BCadastrarItemDoacaoActionPerformed
    private void BCadastrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarUsuarioActionPerformed
        
        Usuario u=new Usuario();
        u.setNome_usuario(campoNomeNovoUsuario.getText());
        u.setUsuario_administrador(CBAdministrador.isSelected());
        u.setChave_encriptacao(UUID.randomUUID().toString().substring(0, 16));
        u.setEmail(campoEmailCadastrarUsuario.getText());
        u.setRegistro_academico(campoRegistroAcademicoCadastrarUsuario.getText());
        
        String senha=new String(campoSenhaCadastrarUsuario.getPassword());
        String repetirsenha=new String(campoRepetirSenhaCadastrarUsuario.getPassword());
        
        if (u.getNome_usuario().equals("")||u.getEmail().equals("")||u.getRegistro_academico().equals("")||senha.equals("")){
            JOptionPane.showMessageDialog(CadastrarUsuario,"Campos obrigatórios vazios","Erro",0);
        }
        else if (!senha.equals(repetirsenha)) {
            JOptionPane.showMessageDialog(CadastrarUsuario,"Senhas diferem.","Erro",0);
        }
        else{
            Encryptor aes=new Encryptor();
            String encriptada=aes.encrypt(u.getChave_encriptacao(), senha);
            u.setSenha_usuario(encriptada);
            
            UsuarioDAO daou=new UsuarioDAO();
            int sucesso=daou.inserir(u);
            daou.fechar();
            if (sucesso==0) JOptionPane.showMessageDialog(CadastrarUsuario,"Falha na Inserção.","Erro",0);
            else {
                Banco b=new Banco();
                sucesso=b.max("SELECT MAX(cod_usuario) from usuario;");
                b.fechar();
                JOptionPane.showMessageDialog(CadastrarUsuario,"Código de Usuário: "+sucesso,"Usuário Registrado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBUsuario();
                atualizarCBUsuario();
            }
        }
    }//GEN-LAST:event_BCadastrarUsuarioActionPerformed
    private void BLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLogarActionPerformed

        Usuario u;
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByNome(CBUsuarioLogin.getSelectedItem().toString());
        daou.fechar();
        String senha=new String(campoSenhaLogin.getPassword());
        
        Encryptor aes=new Encryptor();
        String encriptada=aes.encrypt(u.getChave_encriptacao(), senha);
        
        if(u!=null&&u.getSenha_usuario().equals(encriptada))
        {
            campoSenhaLogin.setText("");
            logado=true;
            nomeUsuario=u.getNome_usuario();
            senhaUsuario=u.getSenha_usuario();
            administrador=u.isUsuario_administrador();
            codigoUsuario=u.getCod_usuario();
            
            this.remove(AbaDoUsuario);
            Principal.addTab(nomeUsuario, new javax.swing.ImageIcon(getClass().getResource("/view/imagens/userlittle.png")), AbaDoUsuario);
            
            campoUsuarioDoacao.setText(nomeUsuario);
            /*
            LAguarde.setVisible(true);
            LCarregandoGif.setVisible(true);
            
            JDLogin.pack();
            JDLogin.repaint();
            */
            atualizarTB();
            atualizarCB();
            atualizarCampos();
            JDLogin.setModal(false);
            JDLogin.setVisible(false);
            JDLogin.dispose();
            
            if(!administrador) {
                Principal.remove(Usuarios);
                campoNomeAlterarUsuario.setEditable(false);
                campoRegistroAcademicoAlterarUsuario.setEditable(false);
                campoNomeAlterarUsuario.setEnabled(false);
                campoRegistroAcademicoAlterarUsuario.setEnabled(false);
                
            }
            else{
                Principal.addTab("Usuários", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/users.png")), Usuarios); // NOI18N
                campoNomeAlterarUsuario.setEditable(true);
                campoRegistroAcademicoAlterarUsuario.setEditable(true);
                campoNomeAlterarUsuario.setEnabled(true);
                campoRegistroAcademicoAlterarUsuario.setEnabled(true);
            }
            Principal.repaint();
            
            this.setVisible(true);
            
        }
        else JOptionPane.showMessageDialog(JDLogin,"Usuário ou Senha Incorretos.\nTente Novamente.","Erro de Autenticação",0);
    }//GEN-LAST:event_BLogarActionPerformed
    private void BCadastrarColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarColetorActionPerformed
        // TODO add your handling code here:
        Coletor c=new Coletor();
        Tipo_coletor tc;
        
        c.setNome(campoNomeColetor.getText());
        Tipo_coletorDAO daotc=new Tipo_coletorDAO();
        tc=daotc.getByNome(CBTipoColetor.getSelectedItem().toString());
        daotc.fechar();
        c.setCod_tipo_coletor(tc.getCod_tipo_coletor());
        ColetorDAO daoc=new ColetorDAO();
        int sucesso=daoc.inserir(c);
        daoc.fechar();
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarColetor,"Falha na Inserção.","Erro",0);
        else {
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_coletor) from coletor");
            b.fechar();
            atualizarTBColetor();
            atualizarCBColetor();
            JDCadastrarColetor.dispose();
            JOptionPane.showMessageDialog(JDCadastrarColetor,"Código de Coletor: "+sucesso,"Coletor Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBColetor.setSelectedIndex((CBColetor.getItemCount()-1));
            
        }
        atualizarCamposCadastrarColetor();
        
    }//GEN-LAST:event_BCadastrarColetorActionPerformed
    private void BCadastrarItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarItemRepasseActionPerformed
        // TODO add your handling code here:
        
        Item_repasse itemd=new Item_repasse();
        Repasse doaaux;
        Tipo_item t;
        
        RepasseDAO daor=new RepasseDAO();
        doaaux=daor.getByCod(Integer.parseInt(campoRepasseItemRepasse.getText()));
        daor.fechar();
        if(doaaux==null) JOptionPane.showMessageDialog(CadastrarItemRepasse, "","Erro",0);
        else {
            
            itemd.setCod_repasse(Integer.parseInt(campoRepasseItemRepasse.getText()));
            itemd.setQuantidade_item_repasse(Integer.parseInt(campoQuantidadeItemRepasse.getText()));
            Tipo_itemDAO daot=new Tipo_itemDAO();
            t=daot.getByNome(CBTipoItemRepasse.getSelectedItem().toString());
            daot.fechar();
            itemd.setCod_tipo(t.getCod_tipo());
            Item_repasseDAO daoitemd=new Item_repasseDAO();
            int sucesso=daoitemd.inserir(itemd);
            daoitemd.fechar();
            if (sucesso==0) JOptionPane.showMessageDialog(CadastrarItemRepasse,"Falha na Inserção.","Erro",0);
            
            else {
                Banco b=new Banco();
                sucesso=b.max("SELECT MAX(cod_item_Repasse) from item_Repasse;");
                b.fechar();
                JOptionPane.showMessageDialog(CadastrarItemRepasse,"Código de Item-Repasse: "+sucesso,"Item-Repasse Registrado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBItemRepasse();
                atualizarTBEstoque();
            }
        }
        atualizarCamposCadastrarItemRepasse(false);
        
    }//GEN-LAST:event_BCadastrarItemRepasseActionPerformed
    private void BCadastrarRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarRepasseActionPerformed
        // TODO add your handling code here:
        
        Repasse r=new Repasse();
        Destinacao d;
        Coletor coletor;
        
        ColetorDAO daocoletor=new ColetorDAO();
        coletor=daocoletor.getByNome(CBColetor.getSelectedItem().toString());
        daocoletor.fechar();
        
        DestinacaoDAO daod=new DestinacaoDAO();
        d=daod.getByNome(CBDestinacao.getSelectedItem().toString());
        daod.fechar();
        
        r.setCod_destinacao(d.getCod_destinacao());
        r.setCod_coletor(coletor.getCod_coletor());
        r.setCod_usuario(codigoUsuario);
        
        RepasseDAO daor=new RepasseDAO();
        int sucesso=daor.inserir(r);
        daor.fechar();
        if(sucesso==0) JOptionPane.showMessageDialog(CadastrarRepasse,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_repasse) from repasse;");
            b.fechar();
            JOptionPane.showMessageDialog(CadastrarRepasse,"Código de Repasse: "+sucesso,"Repasse Registrado.",JOptionPane.INFORMATION_MESSAGE);
            atualizarTBRepasse();
        }
        atualizarCamposCadastrarRepasse();
    }//GEN-LAST:event_BCadastrarRepasseActionPerformed
    private void BEsqueciSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEsqueciSenhaActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(JDLogin, "Contate o Administrador", "Acesso perdido",0);
    }//GEN-LAST:event_BEsqueciSenhaActionPerformed
    private void JDLoginWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_JDLoginWindowClosed
        if(!logado)
        {   
            this.setVisible(false);
            this.dispose();
        }
    }//GEN-LAST:event_JDLoginWindowClosed
    private void BCancelarLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarLoginActionPerformed
       cancelarLogin();
    }//GEN-LAST:event_BCancelarLoginActionPerformed
    private void BCadastrarItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarItemAcervoActionPerformed
        // TODO add your handling code here:
        
        Item_acervo ia=new Item_acervo();
        Doador d;
        Tipo_item t;
        Marca ma;
        Modelo mo;
        Interface i;
        Tecnologia tec;
        Container c;
        Imagem img=new Imagem();
        
        ia.setCod_usuario(codigoUsuario);
        
        try{
            ia.setDescricao(campoDescricaoItemAcervo.getText());
            ia.setAno(Integer.parseInt(campoAnoItemAcervo.getText()));
            ia.setFunciona(CBFunciona.isSelected());
        }catch(Exception e){
            JOptionPane.showMessageDialog(CadastrarItemAcervo,"Campos obrigatórios em branco.\nPreencha-os e tente novamente.","Erro",0);
        }
        
        
        if(!campoCapacidadeItemAcervo.getText().equals(""))
            ia.setCapacidade(Integer.parseInt(campoCapacidadeItemAcervo.getText()));
        
        
        try{
            DoadorDAO daod=new DoadorDAO();
            d=daod.getByNome(CBDoadorItemAcervo.getSelectedItem().toString());
            daod.fechar();
            
            Tipo_itemDAO daot=new Tipo_itemDAO();
            t=daot.getByNome(CBTipoItemAcervo.getSelectedItem().toString());
            daot.fechar();
            
            MarcaDAO daoma=new MarcaDAO();
            ma=daoma.getByNome(CBMarca.getSelectedItem().toString());
            daoma.fechar();
            
            ModeloDAO daomo=new ModeloDAO();
            mo=daomo.getByNome(CBModelo.getSelectedItem().toString());
            daomo.fechar();
            
            InterfaceDAO daoi=new InterfaceDAO();
            i=daoi.getByNome(CBInterface.getSelectedItem().toString());
            daoi.fechar();
            
            TecnologiaDAO daotec=new TecnologiaDAO();
            tec=daotec.getByNome(CBTecnologia.getSelectedItem().toString());
            daotec.fechar();
            
            ContainerDAO daoc=new ContainerDAO();
            c=daoc.getByCod(Integer.parseInt(campoCodContainerCadastrarItemAcervo.getText()));
            daoc.fechar();
            
            ia.setCod_doador(d.getCod_doador());
            ia.setCod_tipo(t.getCod_tipo());
            ia.setCod_marca(ma.getCod_marca());
            ia.setCod_modelo(mo.getCod_modelo());
            ia.setCod_interface(i.getCod_interface());
            ia.setCod_tecnologia(tec.getCod_tecnologia());
            ia.setCod_container(c.getCod_container());
        }catch(Exception e){
            JOptionPane.showMessageDialog(CadastrarItemAcervo,"O sistema não pode localizar algum dos itens selecionados.\nTente novamente.","Erro",0);
        }
        finally{
            Item_acervoDAO daoia=new Item_acervoDAO();
            int sucesso=daoia.inserir(ia);
            daoia.fechar();
            if(sucesso==0) JOptionPane.showMessageDialog(CadastrarItemAcervo,"Falha na Inserção.","Erro",0);
            else{   
                    Banco b=new Banco();
                    sucesso=b.max("SELECT MAX(cod_item_acervo) from item_acervo;");
                    b.fechar();
                    JOptionPane.showMessageDialog(CadastrarItemAcervo,"Código de Item-Acervo: "+sucesso,"Item-Acervo Registrado.",JOptionPane.INFORMATION_MESSAGE);
                    
                atualizarTBAcervo();
                }
        atualizarCamposCadastrarItemAcervo();
        
        }
        
    }//GEN-LAST:event_BCadastrarItemAcervoActionPerformed
    private void BCadastrarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarTipoActionPerformed
        // TODO add your handling code here:
        
        Tipo_item t=new Tipo_item();
        
        int sucesso=0;
        if(!campoTipoItem.getText().equals("")){
            t.setNome(campoTipoItem.getText());
            Tipo_itemDAO daot=new Tipo_itemDAO();
            sucesso=daot.inserir(t);
            daot.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarTipoItem,"Campo Nome Vazio","Erro",0);
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarTipoItem,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_tipo) from tipo_item;");
            b.fechar();
            atualizarCBTipoItem();
            JDCadastrarTipoItem.dispose();
            JOptionPane.showMessageDialog(JDCadastrarTipoItem,"Código de Tipo: "+sucesso,"Tipo Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBTipoItemAcervo.setSelectedIndex((CBTipoItemAcervo.getItemCount()-1));
            CBTipoItemDoacao.setSelectedIndex((CBTipoItemAcervo.getItemCount()-1));
            CBTipoItemRepasse.setSelectedIndex((CBTipoItemAcervo.getItemCount()-1));
            
        }
        atualizarCamposCadastrarTipoItem();
        
    }//GEN-LAST:event_BCadastrarTipoActionPerformed
    private void BNovoTipoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoTipoItemActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarTipoItem();
        JDCadastrarTipoItem.setIconImage(icone);
        JDCadastrarTipoItem.setModal(true);
        JDCadastrarTipoItem.setLocationRelativeTo(null);
        JDCadastrarTipoItem.pack();
        JDCadastrarTipoItem.setVisible(true);
        
    }//GEN-LAST:event_BNovoTipoItemActionPerformed
    private void BCadastrarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarMarcaActionPerformed
        // TODO add your handling code here:
        
        Marca m=new Marca();
        
        int sucesso=0;
        if(!campoNomeMarca.getText().equals("")){
            m.setNome(campoNomeMarca.getText());
            MarcaDAO daom=new MarcaDAO();
            sucesso=daom.inserir(m);
            daom.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarMarca,"Campo Nome Vazio","Erro",0);
        
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarMarca,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_marca) from marca;");
            b.fechar();
            atualizarCBMarca();
            JDCadastrarMarca.dispose();
            JOptionPane.showMessageDialog(JDCadastrarMarca,"Código de Marca: "+sucesso,"Marca Registrada.",JOptionPane.INFORMATION_MESSAGE);
            CBMarca.setSelectedIndex((CBMarca.getItemCount()-1));
        }
        atualizarCamposCadastrarMarca();
    }//GEN-LAST:event_BCadastrarMarcaActionPerformed
    private void BNovoMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoMarcaActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarMarca();
        JDCadastrarMarca.setIconImage(icone);
        JDCadastrarMarca.setModal(true);
        JDCadastrarMarca.setLocationRelativeTo(null);
        JDCadastrarMarca.pack();
        JDCadastrarMarca.setVisible(true);
    }//GEN-LAST:event_BNovoMarcaActionPerformed
    private void BCadastrarModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarModeloActionPerformed
        // TODO add your handling code here:
        
        Modelo m=new Modelo();
        
        int sucesso=0;
        if(!campoNomeModelo.getText().equals("")){
            m.setNome(campoNomeModelo.getText());
            ModeloDAO daom=new ModeloDAO();
            sucesso=daom.inserir(m);
            daom.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarModelo,"Campo Nome Vazio","Erro",0);
        
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarModelo,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_modelo) from modelo;");
            b.fechar();
            atualizarCBModelo();
            JDCadastrarModelo.dispose();
            JOptionPane.showMessageDialog(JDCadastrarModelo,"Código de Modelo: "+sucesso,"Modelo Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBModelo.setSelectedIndex((CBModelo.getItemCount()-1));
        }
        atualizarCamposCadastrarModelo();
    }//GEN-LAST:event_BCadastrarModeloActionPerformed
    private void BNovoModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoModeloActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarModelo();
        JDCadastrarModelo.setIconImage(icone);
        JDCadastrarModelo.setModal(true);
        JDCadastrarModelo.setLocationRelativeTo(null);
        JDCadastrarModelo.pack();
        JDCadastrarModelo.setVisible(true);
    }//GEN-LAST:event_BNovoModeloActionPerformed
    private void BCadastrarInterfaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarInterfaceActionPerformed
        // TODO add your handling code here:
        
        Interface i=new Interface();
        
        int sucesso=0;
        if(!campoNomeInterface.getText().equals("")){
            i.setNome(campoNomeInterface.getText());
            InterfaceDAO daoi=new InterfaceDAO();
            sucesso=daoi.inserir(i);
            daoi.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarInterface,"Campo Nome Vazio","Erro",0);
        
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarInterface,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_interface) from interface;");
            b.fechar();
            atualizarCBInterface();
            JDCadastrarInterface.dispose();
            JOptionPane.showMessageDialog(JDCadastrarInterface,"Código de Interface: "+sucesso,"Interface Registrada.",JOptionPane.INFORMATION_MESSAGE);
            CBInterface.setSelectedIndex((CBInterface.getItemCount()-1));
        }
        atualizarCamposCadastrarInterface();
        
    }//GEN-LAST:event_BCadastrarInterfaceActionPerformed
    private void BNovoInterfaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoInterfaceActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarInterface();
        JDCadastrarInterface.setIconImage(icone);
        JDCadastrarInterface.setModal(true);
        JDCadastrarInterface.setLocationRelativeTo(null);
        JDCadastrarInterface.pack();
        JDCadastrarInterface.setVisible(true);
    }//GEN-LAST:event_BNovoInterfaceActionPerformed
    private void BCadastrarTecnologiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarTecnologiaActionPerformed
        // TODO add your handling code here:
        
        Tecnologia t=new Tecnologia();
        
        int sucesso=0;
        if(!campoNomeTecnologia.getText().equals("")){
            t.setNome(campoNomeTecnologia.getText());
            TecnologiaDAO daot=new TecnologiaDAO();
            sucesso=daot.inserir(t);
            daot.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarTecnologia,"Campo Nome Vazio","Erro",0);
        
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarTecnologia,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_tecnologia) from tecnologia;");
            b.fechar();
            atualizarCBTecnologia();
            JDCadastrarTecnologia.dispose();
            JOptionPane.showMessageDialog(JDCadastrarTecnologia,"Código de Tecnologia: "+sucesso,"Tecnologia Registrada.",JOptionPane.INFORMATION_MESSAGE);
            CBTecnologia.setSelectedIndex((CBTecnologia.getItemCount()-1));
        }
        atualizarCamposCadastrarTecnologia();
        
    }//GEN-LAST:event_BCadastrarTecnologiaActionPerformed
    private void BNovoTecnologiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoTecnologiaActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarTecnologia();
        JDCadastrarTecnologia.setIconImage(icone);
        JDCadastrarTecnologia.setModal(true);
        JDCadastrarTecnologia.setLocationRelativeTo(null);
        JDCadastrarTecnologia.pack();
        JDCadastrarTecnologia.setVisible(true);
    }//GEN-LAST:event_BNovoTecnologiaActionPerformed
    private void BNovoColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoColetorActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarColetor();
        JDCadastrarColetor.setIconImage(icone);
        JDCadastrarColetor.setModal(true);
        JDCadastrarColetor.setLocationRelativeTo(null);
        JDCadastrarColetor.pack();
        JDCadastrarColetor.setVisible(true);
    }//GEN-LAST:event_BNovoColetorActionPerformed
    private void BNovoDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoDoadorActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarDoador();
        JDCadastrarDoador.setIconImage(icone);
        JDCadastrarDoador.setModal(true);
        JDCadastrarDoador.setLocationRelativeTo(null);
        JDCadastrarDoador.pack();
        JDCadastrarDoador.setVisible(true);
    }//GEN-LAST:event_BNovoDoadorActionPerformed
    private void BNovoDoadorAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoDoadorAcervoActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarDoador();
        JDCadastrarDoador.setIconImage(icone);
        JDCadastrarDoador.setModal(true);
        JDCadastrarDoador.setLocationRelativeTo(null);
        JDCadastrarDoador.pack();
        JDCadastrarDoador.setVisible(true);
    }//GEN-LAST:event_BNovoDoadorAcervoActionPerformed
    private void BAlterarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarUsuarioActionPerformed
        // TODO add your handling code here:
        UsuarioDAO daou=new UsuarioDAO();
        Usuario u=new Usuario();
        u=daou.getByCod(codigoUsuario);
        
        u.setCod_usuario(codigoUsuario);
        u.setNome_usuario(campoNomeAlterarUsuario.getText());
        u.setEmail(campoEmailAlterarUsuario.getText());
        u.setRegistro_academico(campoRegistroAcademicoAlterarUsuario.getText());
        u.setUsuario_administrador(administrador);
        
        String senhaatual=new String(campoSenhaAtualAlterarUsuario.getPassword());
        String novasenha=new String(campoNovaSenhaAlterarUsuario.getPassword());
        String repetirnovasenha=new String(campoRepetirSenhaAlterarUsuario.getPassword());
        
        boolean procedermudanca;
        boolean alterandosenha=!senhaatual.equals("");
        Encryptor aes=new Encryptor();
        
        if(!alterandosenha) {
            procedermudanca=true;
            if(!novasenha.equals("")||!repetirnovasenha.equals("")){
                JOptionPane.showMessageDialog(SuasInformacoes,"Para alterar a senha, digite a senha atual.","Erro",0);
                procedermudanca=false;
            }
        }
        else{
            senhaatual=aes.encrypt(u.getChave_encriptacao(),senhaatual);
            procedermudanca=(senhaatual.equals(u.getSenha_usuario()));
            if(novasenha.equals("")) procedermudanca=false;
        }
        
        if(!novasenha.equals(repetirnovasenha)
                ||!procedermudanca
                ||novasenha.equals("")
                ||u.getNome_usuario().equals("")
                ||u.getEmail().equals("")
                ||u.getRegistro_academico().equals("")){
            JOptionPane.showMessageDialog(SuasInformacoes,"Senha atual errada, senhas novas nao coincidem ou campos vazios.\nVerifique os campos e tente novamente","Erro",0);
        }
        else{
            if(alterandosenha&&procedermudanca){
                u.setChave_encriptacao(UUID.randomUUID().toString().substring(0,16));
                String encriptada=aes.encrypt(u.getChave_encriptacao(),novasenha);
                u.setSenha_usuario(encriptada);
                
            }
            
            boolean sucesso=daou.alterar(u);
            
            if(!sucesso) JOptionPane.showMessageDialog(SuasInformacoes,"Erro na alteração.\nVerifique os campos e tente novamente","Erro",0);
            else{ 
                JOptionPane.showMessageDialog(SuasInformacoes,"Informações Atualizadas","Concluido",1);
                nomeUsuario=u.getNome_usuario();
                senhaUsuario=u.getSenha_usuario();
                atualizarCBUsuario();
                atualizarTBUsuario();
                atualizarCamposAlterarUsuario();
            }
        }
        daou.fechar();
    }//GEN-LAST:event_BAlterarUsuarioActionPerformed
    private void BNovoTipoItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoTipoItemDoacaoActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarTipoItem();
        JDCadastrarTipoItem.setIconImage(icone);
        JDCadastrarTipoItem.setModal(true);
        JDCadastrarTipoItem.setLocationRelativeTo(null);
        JDCadastrarTipoItem.pack();
        JDCadastrarTipoItem.setVisible(true);
    }//GEN-LAST:event_BNovoTipoItemDoacaoActionPerformed
    private void BNovoTipoItemDoacao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoTipoItemDoacao1ActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarTipoItem();
        JDCadastrarTipoItem.setIconImage(icone);
        JDCadastrarTipoItem.setModal(true);
        JDCadastrarTipoItem.setLocationRelativeTo(null);
        JDCadastrarTipoItem.pack();
        JDCadastrarTipoItem.setVisible(true);
    }//GEN-LAST:event_BNovoTipoItemDoacao1ActionPerformed
    private void campoLinkKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLinkKeyTyped
        // TODO add your handling code here:
        if(campoLink.getText().equals(""));
        LFotoAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png"))); // NOI18N
    }//GEN-LAST:event_campoLinkKeyTyped
    private void BCheckLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCheckLinkActionPerformed
        // TODO add your handling code here:
        BufferedImage image;
        int h,w,times;
        try {
            image = ImageIO.read(new URL(campoLink.getText()));
            h=image.getHeight();
            w=image.getWidth();
            if(w>=h){
            times=w/LFotoAcervo.getWidth();
            w=w/(times+1);
            h=h/(times+1);
            }
        else{
            times=h/LFotoAcervo.getHeight();
            w=w/(times+1);
            h=h/(times+1);
        }
        Image resizedImage = image.getScaledInstance(w, h, 0);
        LFotoAcervo.setIcon(new javax.swing.ImageIcon(resizedImage));
        //return true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_BCheckLinkActionPerformed
    private void BDeslogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDeslogarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Doacoes.setSelectedIndex(0);
        Principal.setSelectedIndex(0);
        this.dispose();
        nomeUsuario=null;
        senhaUsuario=null;
        administrador=false;
        
        JDLogin.setIconImage(icone);
        JDLogin.setModal(true);
        JDLogin.setLocationRelativeTo(null);
        JDLogin.setVisible(true);
    }//GEN-LAST:event_BDeslogarActionPerformed
    private void BAlterarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarDoacaoActionPerformed
        // TODO add your handling code here:
        Doador donator;
        Doacao d;
        Evento_origem eo;
        
        DoadorDAO daodonator=new DoadorDAO();
        donator=daodonator.getByNome(campoDoadorAlterarDoacao.getText());
        daodonator.fechar();
        
        Evento_origemDAO daoeo=new Evento_origemDAO();
        eo=daoeo.getByNome(CBEventoOrigemAlterarDoacao.getSelectedItem().toString());
        daoeo.fechar();
        
        DoacaoDAO daod=new DoacaoDAO();
        d=daod.getByCod(Integer.parseInt(campoDoacaoAlterarDoacao.getText()));
        
        d.setCod_evento_origem(eo.getCod_evento_origem());
        d.setCod_doador(donator.getCod_doador());
        
        boolean sucesso=daod.alterar(d);
        daod.fechar();
        if(sucesso) {JOptionPane.showMessageDialog(JDAlterarDoacao, "Alterações Salvas","Sucesso",JOptionPane.PLAIN_MESSAGE);
                    atualizarTBDoacao();
                    JDAlterarDoacao.setVisible(false);
                    JDAlterarDoacao.dispose();
                    
                    }
        else JOptionPane.showMessageDialog(JDAlterarDoacao, "Alterações não foram Salvas","Erro",0);
        
    }//GEN-LAST:event_BAlterarDoacaoActionPerformed
    private void BEditarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarDoacaoActionPerformed
        
        int cod_alterardoacao=Integer.parseInt(TDoacao.getValueAt(TDoacao.getSelectedRow(), 0).toString());
        Doacao d;
        Usuario u;
        Doador doa;
        Evento_origem eo;
        
        DoacaoDAO daod=new DoacaoDAO();
        d=daod.getByCod(cod_alterardoacao);
        daod.fechar();
        
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByCod(d.getCod_usuario());
        daou.fechar();
        
        DoadorDAO daodoa=new DoadorDAO();
        doa=daodoa.getByCod(d.getCod_doador());
        daodoa.fechar();
        
        Evento_origemDAO daoeo=new Evento_origemDAO();
        eo=daoeo.getByCod(d.getCod_evento_origem());
        daoeo.fechar();
        
        campoDoacaoAlterarDoacao.setText(d.getCod_doacao()+"");
        campoUsuarioAlterarDoacao.setText(u.getNome_usuario());
        java.util.Date data=d.getData_doacao();
        campoDataAlterarDoacao.setText(data.getDate()+"/"+(data.getMonth()+1)+"/"+(1900+data.getYear()));
        CBEventoOrigemAlterarDoacao.setSelectedItem(eo.getNome_evento_origem());
        campoDoadorAlterarDoacao.setText(doa.getNome_doador());
        
        JDAlterarDoacao.setLocationRelativeTo(null);
        JDAlterarDoacao.setIconImage(icone);
        JDAlterarDoacao.setVisible(true);
        
    }//GEN-LAST:event_BEditarDoacaoActionPerformed
    private void BExcluirDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirDoacaoActionPerformed
        int cod_alterardoacao=Integer.parseInt(TDoacao.getValueAt(TDoacao.getSelectedRow(), 0).toString());
        Doacao d;
        
        DoacaoDAO daod=new DoacaoDAO();
        d=daod.getByCod(cod_alterardoacao);
        daod.fechar();
        
        campoDoacaoExcluirDoacao.setText(d.getCod_doacao()+"");
        
        JDExcluirDoacao.setLocationRelativeTo(null);
        JDExcluirDoacao.setIconImage(icone);
        JDExcluirDoacao.setVisible(true);
        
    }//GEN-LAST:event_BExcluirDoacaoActionPerformed
    private void BConfirmarExcluirDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirDoacaoActionPerformed
        // TODO add your handling code here:
        int cod_excluirdoacao=Integer.parseInt(campoDoacaoExcluirDoacao.getText());
        DoacaoDAO daodoa=new DoacaoDAO();
        boolean sucesso=daodoa.excluir(cod_excluirdoacao);
        daodoa.fechar();
        if(sucesso) {JOptionPane.showMessageDialog(JDExcluirDoacao, "Item excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                    atualizarTBDoacao();
                    atualizarTBItemDoacao();
                    JDExcluirDoacao.setVisible(false);
                    JDExcluirDoacao.dispose();
                    }
        else JOptionPane.showMessageDialog(JDExcluirDoacao, "Item não excluido.\nVerificar se há itens atrelados à Doação. Se houver, excluir todos os itens da mesma.","Erro",0);
        
    }//GEN-LAST:event_BConfirmarExcluirDoacaoActionPerformed
    private void BCancelarExcluirDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirDoacaoActionPerformed
        // TODO add your handling code here:
        JDExcluirDoacao.setVisible(false);
        JDExcluirDoacao.dispose();
    }//GEN-LAST:event_BCancelarExcluirDoacaoActionPerformed
    private void BCadastrarEventoOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarEventoOrigemActionPerformed
        // TODO add your handling code here:
        Evento_origem eo=new Evento_origem();
        
        int sucesso=0;
        if(!campoNomeEventoOrigem.getText().equals("")){
            eo.setNome_evento_origem(campoNomeEventoOrigem.getText());
            Evento_origemDAO daoeo=new Evento_origemDAO();
            sucesso=daoeo.inserir(eo);
            daoeo.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarEventoOrigem,"Campo Nome Vazio","Erro",0);
        
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarEventoOrigem,"Falha na Inserção.","Erro",0);
        else{
             Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_evento_origem) from evento_origem;");
            b.fechar();
            atualizarCBEventoOrigem();
            JDCadastrarEventoOrigem.dispose();
            JOptionPane.showMessageDialog(JDCadastrarEventoOrigem,"Código de Evento Origem: "+sucesso,"Evento origem Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBEventoOrigem.setSelectedIndex((CBEventoOrigem.getItemCount()-1));
        }
    }//GEN-LAST:event_BCadastrarEventoOrigemActionPerformed
    private void BNovoEventoOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoEventoOrigemActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarEventoOrigem();
        JDCadastrarEventoOrigem.setIconImage(icone);
        JDCadastrarEventoOrigem.setModal(true);
        JDCadastrarEventoOrigem.setLocationRelativeTo(null);
        JDCadastrarEventoOrigem.pack();
        JDCadastrarEventoOrigem.setVisible(true);
    }//GEN-LAST:event_BNovoEventoOrigemActionPerformed
    private void BNovoDestinacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoDestinacaoActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarDestinacao();
        JDCadastrarDestinacao.setIconImage(icone);
        JDCadastrarDestinacao.setModal(true);
        JDCadastrarDestinacao.setLocationRelativeTo(null);
        JDCadastrarDestinacao.pack();
        JDCadastrarDestinacao.setVisible(true);
    }//GEN-LAST:event_BNovoDestinacaoActionPerformed
    private void BCadastrarDestinacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarDestinacaoActionPerformed
        
        Destinacao d=new Destinacao();
        
        int sucesso=0;
        if(!campoNomeDestinacao.getText().equals("")){
            d.setNome_destinacao(campoNomeDestinacao.getText());
            DestinacaoDAO daod=new DestinacaoDAO();
            sucesso=daod.inserir(d);
            daod.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarDestinacao,"Campo Nome Vazio","Erro",0);
        
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarDestinacao,"Falha na Inserção.","Erro",0);
        else{
             Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_destinacao) from destinacao;");
            b.fechar();
            atualizarCBDestinacao();
            JDCadastrarDestinacao.dispose();
            JOptionPane.showMessageDialog(JDCadastrarDestinacao,"Código de Destinação: "+sucesso,"Destinação Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBDestinacao.setSelectedIndex((CBDestinacao.getItemCount()-1));
        }
    }//GEN-LAST:event_BCadastrarDestinacaoActionPerformed
    private void BCadastrarTipoColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarTipoColetorActionPerformed
        // TODO add your handling code here:
        
        Tipo_coletor tc=new Tipo_coletor();
        
        int sucesso=0;
        if(!campoTipoColetor.getText().equals("")){
            tc.setNome_tipo_coletor(campoTipoColetor.getText());
            Tipo_coletorDAO daod=new Tipo_coletorDAO();
            sucesso=daod.inserir(tc);
            daod.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarTipoColetor,"Campo Nome Vazio","Erro",0);
        
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarTipoColetor,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_tipo_coletor) from tipo_coletor;");
            b.fechar();
            atualizarCBTipoColetor();
            JDCadastrarTipoColetor.dispose();
            JDCadastrarColetor.pack();
            JOptionPane.showMessageDialog(JDCadastrarTipoColetor,"Código de Tipo Coletor: "+sucesso,"Tipo Coletor Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBTipoColetor.setSelectedIndex((CBTipoColetor.getItemCount()-1));
        }
    }//GEN-LAST:event_BCadastrarTipoColetorActionPerformed
    private void BNovoTipoColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoTipoColetorActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarTipoColetor();
        JDCadastrarTipoColetor.setIconImage(icone);
        JDCadastrarTipoColetor.setModal(true);
        JDCadastrarTipoColetor.setLocationRelativeTo(null);
        JDCadastrarTipoColetor.pack();
        JDCadastrarTipoColetor.setVisible(true);
    }//GEN-LAST:event_BNovoTipoColetorActionPerformed
    private void BCadastrarTipoContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarTipoContainerActionPerformed
        // TODO add your handling code here:
        Tipo_container tc=new Tipo_container();
        
        int sucesso=0;
        if(!campoTipoContainer.getText().equals("")){
            tc.setNome_tipo_container(campoTipoContainer.getText());
            Tipo_containerDAO daod=new Tipo_containerDAO();
            sucesso=daod.inserir(tc);
            daod.fechar();
        }
        else JOptionPane.showMessageDialog(JDCadastrarTipoContainer,"Campo Nome Vazio","Erro",0);
        if(sucesso==0) JOptionPane.showMessageDialog(JDCadastrarTipoContainer,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_tipo_container) from tipo_container;");
            b.fechar();
            atualizarCBTipoContainer();
            JDCadastrarTipoContainer.dispose();
            
            JOptionPane.showMessageDialog(JDCadastrarTipoContainer,"Código de Tipo Container: "+sucesso,"Tipo Container Registrado.",JOptionPane.INFORMATION_MESSAGE);
            CBTipoContainerCadastrarContainer.setSelectedIndex((CBTipoContainerCadastrarContainer.getItemCount()-1));
        }
    }//GEN-LAST:event_BCadastrarTipoContainerActionPerformed
    private void BNovoTipoContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNovoTipoContainerActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarTipoContainer();
        JDCadastrarTipoContainer.setIconImage(icone);
        JDCadastrarTipoContainer.setModal(true);
        JDCadastrarTipoContainer.setLocationRelativeTo(null);
        JDCadastrarTipoContainer.pack();
        JDCadastrarTipoContainer.setVisible(true);
    }//GEN-LAST:event_BNovoTipoContainerActionPerformed

    private void JPBCarregandoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JPBCarregandoStateChanged
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_JPBCarregandoStateChanged

    private void JDCarregandoDadosWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_JDCarregandoDadosWindowActivated
        // TODO add your handling code here:
            
            
            JDCarregandoDados.setModal(false);
            JDCarregandoDados.setVisible(false);
            JDCarregandoDados.dispose();
            
            
            
    }//GEN-LAST:event_JDCarregandoDadosWindowActivated

    private void JFCarregandoDadosWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_JFCarregandoDadosWindowActivated
        // TODO add your handling code here:
//            SwingUtilities.invokeLater(() -> {
                JPBCarregando.setValue(0);
                atualizarTB();
                JPBCarregando.setValue(33);
                JPBCarregando.setString("Progresso: "+JPBCarregando.getValue());
                atualizarCB();
                JPBCarregando.setValue(66);
                JPBCarregando.setString("Progresso: "+JPBCarregando.getValue());
                atualizarCampos();
                JPBCarregando.setValue(100);
                JPBCarregando.setString("Progresso: "+JPBCarregando.getValue());
                JPBCarregando.setString("Finalizado");
                JFCarregandoDados.setVisible(false);
                JFCarregandoDados.dispose();
                if(!administrador) {
                    Usuarios.remove(CadastrarUsuario);
                }
   //         });
            //this.setVisible(true);
    }//GEN-LAST:event_JFCarregandoDadosWindowActivated

    private void BEditarItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarItemDoacaoActionPerformed
        // TODO add your handling code here:
        int cod_alteraritemdoacao=Integer.parseInt(TItemDoacao.getValueAt(TItemDoacao.getSelectedRow(), 0).toString());
        Item_doacao item;
        Doacao d;
        Usuario u;
        Doador doa;
        Evento_origem eo;
        Tipo_item t;
        
        Item_doacaoDAO itemdd=new Item_doacaoDAO();
        item=itemdd.getByCod(cod_alteraritemdoacao);
        itemdd.fechar();
        
        DoacaoDAO daod=new DoacaoDAO();
        d=daod.getByCod(item.getCod_doacao());
        daod.fechar();
        
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByCod(d.getCod_usuario());
        daou.fechar();
        
        DoadorDAO daodoa=new DoadorDAO();
        doa=daodoa.getByCod(d.getCod_doador());
        daodoa.fechar();
        
        Tipo_itemDAO tdao=new Tipo_itemDAO();
        t=tdao.getByCod(item.getCod_tipo());
        tdao.fechar();
        
        campoItemDoacaoAlterarItemDoacao.setText(item.getCod_item_doacao()+"");
        campoUsuarioAlterarItemDoacao.setText(u.getNome_usuario());
        campoDoadorAlterarItemDoacao.setText(doa.getNome_doador());
        
        CBTipoAlterarItemDoacao.setSelectedItem(t.getNome());
        campoQuantidadeAlterarItemDoacao.setText(item.getQuantidade_item_doacao()+"");
        
        JDAlterarItemDoacao.setLocationRelativeTo(null);
        JDAlterarItemDoacao.setIconImage(icone);
        //JDAlterarItemDoacao.
        JDAlterarItemDoacao.setVisible(true);
    }//GEN-LAST:event_BEditarItemDoacaoActionPerformed

    private void BAlterarItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarItemDoacaoActionPerformed
        // TODO add your handling code here:
        boolean sucesso=false;
        Item_doacao item=new Item_doacao();
        try{
        
        
        Item_doacaoDAO itemdd=new Item_doacaoDAO();
        item=itemdd.getByCod(Integer.parseInt(campoItemDoacaoAlterarItemDoacao.getText()));
        
        
        Tipo_item t;
        Tipo_itemDAO daot=new Tipo_itemDAO();
        t=daot.getByNome(CBTipoAlterarItemDoacao.getSelectedItem().toString());
        daot.fechar();
        
        
        item.setCod_tipo(t.getCod_tipo());
        item.setQuantidade_item_doacao(Integer.parseInt(campoQuantidadeAlterarItemDoacao.getText()));
        sucesso=itemdd.alterar(item);
        itemdd.fechar();
        
        }
        catch(NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(PainelItemDoacao,"Campos Incorretos.\nVerifique e tente novamente.","Erro",0);
        }
        finally{
            if (!sucesso) {JOptionPane.showMessageDialog(PainelItemDoacao,"Falha na alteração.","Erro",0);
                            }
            else {
                JOptionPane.showMessageDialog(PainelItemDoacao,"Código de Item-Doacao: "+item.getCod_tipo(),"Item-Doacao Alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBItemDoacao();
                atualizarTBEstoque();
                JDAlterarItemDoacao.setModal(false);
                JDAlterarItemDoacao.setVisible(false);
                JDAlterarItemDoacao.dispose();
                }
        }
        
        
    }//GEN-LAST:event_BAlterarItemDoacaoActionPerformed

    private void BAlterarDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarDoadorActionPerformed
        // TODO add your handling code here:
        boolean sucesso=false;
        Doador doador=new Doador();
        try{
            DoadorDAO daodoador=new DoadorDAO();
            doador=daodoador.getByCod(Integer.parseInt(campoCodigoDoadorAlterarDoador.getText()));
            doador.setNome_doador(campoNomeDoadorAlterarDoador.getText());
            sucesso=daodoador.alterar(doador);
            daodoador.fechar();
        }
        catch(NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(PainelDoadores,"Campo Incorreto.\nVerifique e tente novamente.","Erro",0);
        }
        finally{
            if (!sucesso) {JOptionPane.showMessageDialog(PainelDoadores,"Falha na alteração.","Erro",0);
                            }
            else {
                JOptionPane.showMessageDialog(PainelDoadores,"Código de Doador: "+doador.getCod_doador(),"Doador Alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBDoacao();
                atualizarTBItemDoacao();
                atualizarTBDoador();
                atualizarCBDoador();
                //atualizarTBEstoque();
                JDAlterarDoador.setModal(false);
                JDAlterarDoador.setVisible(false);
                JDAlterarDoador.dispose();
                }
        }
    }//GEN-LAST:event_BAlterarDoadorActionPerformed

    private void BEditarDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarDoadorActionPerformed
        // TODO add your handling code here:
        int cod_alterardoador=Integer.parseInt(TDoador.getValueAt(TDoador.getSelectedRow(), 0).toString());
        
        DoadorDAO daodoador=new DoadorDAO();
        Doador doador=daodoador.getByCod(cod_alterardoador);
        daodoador.fechar();
        
        campoCodigoDoadorAlterarDoador.setText(doador.getCod_doador()+"");
        campoNomeDoadorAlterarDoador.setText(doador.getNome_doador());
        
        JDAlterarDoador.setLocationRelativeTo(null);
        JDAlterarDoador.setIconImage(icone);
        JDAlterarDoador.setVisible(true);
    }//GEN-LAST:event_BEditarDoadorActionPerformed

    private void BEditarRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarRepasseActionPerformed
        // TODO add your handling code here:
        int cod_alterarrepasse=Integer.parseInt(TRepasse.getValueAt(TRepasse.getSelectedRow(), 0).toString());
        Repasse r;
        Usuario u;
        Coletor c;
        Destinacao d;
        
        RepasseDAO daor=new RepasseDAO();
        r=daor.getByCod(cod_alterarrepasse);
        daor.fechar();
        
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByCod(r.getCod_usuario());
        daou.fechar();
        
        ColetorDAO daoc=new ColetorDAO();
        c=daoc.getByCod(r.getCod_coletor());
        daoc.fechar();
        
        DestinacaoDAO daod=new DestinacaoDAO();
        d=daod.getByCod(r.getCod_destinacao());
        daod.fechar();
        
        campoRepasseAlterarRepasse.setText(r.getCod_repasse()+"");
        campoUsuarioAlterarRepasse.setText(u.getNome_usuario());
        java.util.Date data=r.getData_repasse();
        campoDataAlterarRepasse.setText(data.getDate()+"/"+(data.getMonth()+1)+"/"+(1900+data.getYear()));
        campoColetorAlterarRepasse.setText(c.getNome());
        CBDestinacaoAlterarRepasse.setSelectedItem(d.getNome_destinacao());
        
        JDAlterarRepasse.setLocationRelativeTo(null);
        JDAlterarRepasse.setIconImage(icone);
        JDAlterarRepasse.setVisible(true);
    }//GEN-LAST:event_BEditarRepasseActionPerformed

    private void BAlterarRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarRepasseActionPerformed
        // TODO add your handling code here:
        boolean sucesso=false;
        Repasse r=new Repasse();
        try{
            RepasseDAO rdao=new RepasseDAO();
            r=rdao.getByCod(Integer.parseInt(campoRepasseAlterarRepasse.getText()));
            
            DestinacaoDAO daod=new DestinacaoDAO();
            Destinacao d=daod.getByNome(CBDestinacaoAlterarRepasse.getSelectedItem().toString());
            daod.fechar();
            
            r.setCod_destinacao(d.getCod_destinacao());
            sucesso=rdao.alterar(r);
        
        }
        catch(NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(PainelRepasse,"Campos Incorretos.\nVerifique e tente novamente.","Erro",0);
        }
        finally{
            if (!sucesso) {JOptionPane.showMessageDialog(PainelRepasse,"Falha na alteração.","Erro",0);
                            }
            else {
                JOptionPane.showMessageDialog(PainelRepasse,"Código de Item-Doacao: "+r.getCod_repasse(),"Repasse Alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBRepasse();
                atualizarTBItemRepasse();
                atualizarTBEstoque();
                JDAlterarRepasse.setModal(false);
                JDAlterarRepasse.setVisible(false);
                JDAlterarRepasse.dispose();
                }
        }
        
        
    }//GEN-LAST:event_BAlterarRepasseActionPerformed

    private void BAlterarItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarItemRepasseActionPerformed
        // TODO add your handling code here:
        boolean sucesso=false;
        Item_repasse item=new Item_repasse();
        try{
            
            Item_repasseDAO daoitem=new Item_repasseDAO();
            item=daoitem.getByCod(Integer.parseInt(campoItemRepasseAlterarItemRepasse.getText()));
            
            Tipo_item t=new Tipo_item();
            Tipo_itemDAO daot=new Tipo_itemDAO();
            t=daot.getByNome(CBTipoAlterarItemRepasse.getSelectedItem().toString());
            daot.fechar();
            
            item.setCod_tipo(t.getCod_tipo());
            item.setQuantidade_item_repasse(Integer.parseInt(campoQuantidadeAlterarItemRepasse.getText()));
            
            sucesso=daoitem.alterar(item);
            daoitem.fechar();
        }
        catch(NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(PainelItemRepasse,"Campo Incorreto.\nVerifique e tente novamente.","Erro",0);
            
        }
        finally{
            if (!sucesso) {JOptionPane.showMessageDialog(PainelItemRepasse,"Falha na alteração.","Erro",0);
                            }
            else {
                JOptionPane.showMessageDialog(PainelItemRepasse,"Código de Repasse: "+item.getCod_item_repasse(),"Item Repasse Alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBItemRepasse();
                atualizarTBEstoque();
                JDAlterarItemRepasse.setModal(false);
                JDAlterarItemRepasse.setVisible(false);
                JDAlterarItemRepasse.dispose();
                }
        }
        
    }//GEN-LAST:event_BAlterarItemRepasseActionPerformed

    private void BEditarItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarItemRepasseActionPerformed
        // TODO add your handling code here:
        
        int cod_alteraritemrepasse=Integer.parseInt(TItemRepasse.getValueAt(TItemRepasse.getSelectedRow(), 0).toString());
        Repasse r;
        Item_repasse item;
        Usuario u;
        Coletor c;
        Tipo_item t;
        
        Item_repasseDAO daoitem=new Item_repasseDAO();
        item=daoitem.getByCod(cod_alteraritemrepasse);
        daoitem.fechar();
        
        RepasseDAO daor=new RepasseDAO();
        r=daor.getByCod(item.getCod_repasse());
        daor.fechar();
        
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByCod(r.getCod_usuario());
        daou.fechar();
        
        ColetorDAO daoc=new ColetorDAO();
        c=daoc.getByCod(r.getCod_coletor());
        daoc.fechar();
        
        Tipo_itemDAO titem=new Tipo_itemDAO();
        t=titem.getByCod(item.getCod_tipo());
        titem.fechar();
        
        campoItemRepasseAlterarItemRepasse.setText(r.getCod_repasse()+"");
        campoUsuarioAlterarItemRepasse.setText(u.getNome_usuario());
        campoColetorAlterarItemRepasse.setText(c.getNome());
        CBTipoAlterarItemRepasse.setSelectedItem(t.getNome());
        campoQuantidadeAlterarItemRepasse.setText(item.getQuantidade_item_repasse()+"");
        
        JDAlterarItemRepasse.setLocationRelativeTo(null);
        JDAlterarItemRepasse.setIconImage(icone);
        JDAlterarItemRepasse.setVisible(true);
    }//GEN-LAST:event_BEditarItemRepasseActionPerformed

    private void BAlterarColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarColetorActionPerformed
        // TODO add your handling code here:
        boolean sucesso=false;
        Coletor coletor=new Coletor();
        try{
            ColetorDAO daocoletor=new ColetorDAO();
            coletor=daocoletor.getByCod(Integer.parseInt(campoCodigoColetorAlterarColetor.getText()));
            
            
            Tipo_coletor t;
            Tipo_coletorDAO daot=new Tipo_coletorDAO();
            t=daot.getByNome(CBTipoColetorAlterarColetor.getSelectedItem().toString());
            daot.fechar();
            
            coletor.setNome(campoNomeColetorAlterarColetor.getText());
            coletor.setCod_tipo_coletor(t.getCod_tipo_coletor());
            
            sucesso=daocoletor.alterar(coletor);
            daocoletor.fechar();
        }
        catch(NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(PainelColetores,"Campo Incorreto.\nVerifique e tente novamente.","Erro",0);
        }
        finally{
            if (!sucesso) {JOptionPane.showMessageDialog(PainelColetores,"Falha na alteração.","Erro",0);
                            }
            else {
                JOptionPane.showMessageDialog(PainelColetores,"Código de Coletor: "+coletor.getCod_coletor(),"Coletor Alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBRepasse();
                atualizarTBItemRepasse();
                atualizarTBColetor();
                atualizarCBColetor();
                
                JDAlterarColetor.setModal(false);
                JDAlterarColetor.setVisible(false);
                JDAlterarColetor.dispose();
                }
        }
    }//GEN-LAST:event_BAlterarColetorActionPerformed

    private void BEditarColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarColetorActionPerformed
        // TODO add your handling code here:
        int cod_alterarcoletor=Integer.parseInt(TColetor.getValueAt(TColetor.getSelectedRow(), 0).toString());
        
        ColetorDAO daocoletor=new ColetorDAO();
        Coletor coletor=daocoletor.getByCod(cod_alterarcoletor);
        daocoletor.fechar();
        
        Tipo_coletor tipo;
        Tipo_coletorDAO daotipo=new Tipo_coletorDAO();
        tipo=daotipo.getByCod(coletor.getCod_tipo_coletor());
        daotipo.fechar();
        
        campoCodigoColetorAlterarColetor.setText(coletor.getCod_coletor()+"");
        campoNomeColetorAlterarColetor.setText(coletor.getNome());
        CBTipoColetorAlterarColetor.setSelectedItem(tipo.getNome_tipo_coletor());
        
        JDAlterarColetor.setLocationRelativeTo(null);
        JDAlterarColetor.setIconImage(icone);
        JDAlterarColetor.setVisible(true);
    }//GEN-LAST:event_BEditarColetorActionPerformed

    private void BEditarItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarItemAcervoActionPerformed
        // TODO add your handling code here:
        Item_acervoDAO daoitem=new Item_acervoDAO();
        int coditem=Integer.parseInt(TAcervo.getValueAt(TAcervo.getSelectedRow(), 0).toString());
        Item_acervo item=daoitem.getByCod(coditem);
        daoitem.fechar();
        
        Usuario usuario;
        Doador doador;
        Tipo_item tipo;
        Marca marca;
        Modelo modelo;
        Interface iface;
        Tecnologia tecnologia;
        Container container;
        Imagem imagem;
        
        UsuarioDAO usuariodao=new UsuarioDAO();
        usuario=usuariodao.getByCod(item.getCod_usuario());
        usuariodao.fechar();
        
        DoadorDAO doadordao=new DoadorDAO();
        doador=doadordao.getByCod(item.getCod_doador());
        doadordao.fechar();
        
        Tipo_itemDAO tipodao=new Tipo_itemDAO();
        tipo=tipodao.getByCod(item.getCod_tipo());
        tipodao.fechar();
        
        MarcaDAO marcadao=new MarcaDAO();
        marca=marcadao.getByCod(item.getCod_marca());
        marcadao.fechar();
        
        ModeloDAO modelodao=new ModeloDAO();
        modelo=modelodao.getByCod(item.getCod_modelo());
        modelodao.fechar();
        
        InterfaceDAO ifacedao=new InterfaceDAO();
        iface=ifacedao.getByCod(item.getCod_interface());
        ifacedao.fechar();
        
        TecnologiaDAO tecnologiadao=new TecnologiaDAO();
        tecnologia=tecnologiadao.getByCod(item.getCod_tecnologia());
        tecnologiadao.fechar();
        
        ContainerDAO containerdao=new ContainerDAO();
        container=containerdao.getByCod(item.getCod_container());
        containerdao.fechar();
        
        campoItemAcervoAlterarItemAcervo.setText(item.getCod_item_acervo()+"");
        campoUsuarioAlterarItemAcervo.setText(usuario.getNome_usuario());
        campoDoadorAlterarItemAcervo.setText(doador.getNome_doador());
        Date data=item.getData_cadastro_item_acervo();
        campoDataAlterarItemAcervo.setText(data.getDate()+"/"+(data.getMonth()+1)+"/"+(1900+data.getYear()));
        CBTipoAlterarItemAcervo.setSelectedItem(tipo.getNome());
        CBMarcaAlterarItemAcervo.setSelectedItem(marca.getNome());
        CBModeloAlterarItemAcervo.setSelectedItem(modelo.getNome());
        CBInterfaceAlterarItemAcervo.setSelectedItem(iface.getNome());
        CBTecnologiaAlterarItemAcervo.setSelectedItem(tecnologia.getNome());
        campoCapacidadeAlterarItemAcervo.setText(item.getCapacidade()+"");
        campoAnoAlterarItemAcervo.setText(item.getAno()+"");
        campoContainerAlterarItemAcervo.setText(item.getCod_container()+"");
        TADescricaoAlterarItemAcervo.setText(item.getDescricao());
        CheckFuncionaAlterarItemAcervo.setSelected(item.isFunciona());
        
        JDAlterarItemAcervo.setLocationRelativeTo(null);
        JDAlterarItemAcervo.setIconImage(icone);
        JDAlterarItemAcervo.setVisible(true);
        
        
    }//GEN-LAST:event_BEditarItemAcervoActionPerformed

    private void BAlterarItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarItemAcervoActionPerformed
        // TODO add your handling code here:
        int coditem=Integer.parseInt(campoItemAcervoAlterarItemAcervo.getText());
        Item_acervoDAO daoitem=new Item_acervoDAO();
        boolean sucesso=false;
        try{
            Item_acervo item=daoitem.getByCod(coditem);

            Tipo_item tipo;
            Marca marca;
            Modelo modelo;
            Interface iface;
            Tecnologia tecnologia;
            Container container;

            Tipo_itemDAO tipodao=new Tipo_itemDAO();
            tipo=tipodao.getByNome(CBTipoAlterarItemAcervo.getSelectedItem().toString());
            tipodao.fechar();

            MarcaDAO marcadao=new MarcaDAO();
            marca=marcadao.getByNome(CBMarcaAlterarItemAcervo.getSelectedItem().toString());
            marcadao.fechar();

            ModeloDAO modelodao=new ModeloDAO();
            modelo=modelodao.getByNome(CBModeloAlterarItemAcervo.getSelectedItem().toString());
            modelodao.fechar();

            InterfaceDAO ifacedao=new InterfaceDAO();
            iface=ifacedao.getByNome(CBInterfaceAlterarItemAcervo.getSelectedItem().toString());
            ifacedao.fechar();

            TecnologiaDAO tecnologiadao=new TecnologiaDAO();
            tecnologia=tecnologiadao.getByNome(CBTecnologiaAlterarItemAcervo.getSelectedItem().toString());
            tecnologiadao.fechar();

            ContainerDAO containerdao=new ContainerDAO();
            container=containerdao.getByCod(Integer.parseInt(campoContainerAlterarItemAcervo.getText()));
            containerdao.fechar();

            item.setCod_tipo(tipo.getCod_tipo());
            item.setCod_marca(marca.getCod_marca());
            item.setCod_modelo(modelo.getCod_modelo());
            item.setCod_interface(iface.getCod_interface());
            item.setCod_tecnologia(tecnologia.getCod_tecnologia());
            item.setCapacidade(Integer.parseInt(campoCapacidadeAlterarItemAcervo.getText()));
            item.setAno(Integer.parseInt(campoAnoAlterarItemAcervo.getText()));
            item.setCod_container(Integer.parseInt(campoContainerAlterarItemAcervo.getText()));
            item.setDescricao(TADescricaoAlterarItemAcervo.getText());
            item.setFunciona(CheckFuncionaAlterarItemAcervo.isSelected());

            sucesso=daoitem.alterar(item);
            daoitem.fechar();
        }catch(NumberFormatException|HeadlessException e){
           JOptionPane.showMessageDialog(PainelAcervo,"Campos Incorretos.\nVerifique e tente novamente.","Erro",0);
        }
        finally{
            if (!sucesso) {JOptionPane.showMessageDialog(PainelAcervo,"Falha na alteração.","Erro",0);
                            }
            else {
                JOptionPane.showMessageDialog(PainelAcervo,"Código de Item Acervo: "+campoItemAcervoAlterarItemAcervo.getText(),"Item Acervo Alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBAcervo();
                
                
                JDAlterarItemAcervo.setModal(false);
                JDAlterarItemAcervo.setVisible(false);
                JDAlterarItemAcervo.dispose();
                }
        }
        
    }//GEN-LAST:event_BAlterarItemAcervoActionPerformed

    private void BEditarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem=new Imagem();
        int codimagem=Integer.parseInt(TImagem.getValueAt(TImagem.getSelectedRow(), 0).toString());
        ImagemDAO imagemdao=new ImagemDAO();
        imagem=imagemdao.getByCod(codimagem);
        imagemdao.fechar();
        
        campoCodImagemAlterarImagem.setText(imagem.getCod_imagem()+"");
        campoCodItemAcervoAlterarImagem.setText(imagem.getCod_item_acervo()+"");
        campoLinkAlterarImagem.setText(imagem.getLink());
        
        JDAlterarImagem.setLocationRelativeTo(null);
        JDAlterarImagem.setIconImage(icone);
        JDAlterarImagem.setVisible(true);
        JDAlterarImagem.pack();
        
        
        validaImagemAlterarImagem();
        
    }//GEN-LAST:event_BEditarImagemActionPerformed

    private void BEditarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarContainerActionPerformed
        // TODO add your handling code here:
        Container container=new Container();
        int codcontainer=Integer.parseInt(TContainer.getValueAt(TContainer.getSelectedRow(), 0).toString());
        ContainerDAO containerdao=new ContainerDAO();
        container=containerdao.getByCod(codcontainer);
        containerdao.fechar();
        
        Tipo_container tipo=new Tipo_container();
        Tipo_containerDAO tipodao=new Tipo_containerDAO();
        tipo=tipodao.getByCod(container.getCod_tipo_container());
        tipodao.fechar();
        
        campoCodContainerAlterarContainer.setText(container.getCod_container()+"");
        campoLocalizacaoAlterarContainer.setText(container.getLocalizacao_container());
        CBTipoContainerAlterarContainer.setSelectedItem(tipo.getNome_tipo_container());
        
        JDAlterarContainer.setLocationRelativeTo(null);
        JDAlterarContainer.setIconImage(icone);
        JDAlterarContainer.setVisible(true);
        JDAlterarContainer.pack();
        
    }//GEN-LAST:event_BEditarContainerActionPerformed

    private void BCadastrarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem=new Imagem();
        int sucesso=0;
        try{
            imagem.setCod_item_acervo(Integer.parseInt(campoItemAcervoCadastrarImagem.getText()));
            imagem.setLink(campoLink.getText());
            ImagemDAO imagemdao=new ImagemDAO();
            sucesso=imagemdao.inserir(imagem);
            imagemdao.fechar();
            
        }
        catch(HeadlessException|NumberFormatException e){
            
        }
        finally{
            if(sucesso==0) JOptionPane.showMessageDialog(CadastrarImagem,"Falha na Inserção.","Erro",0);
        else{
            Banco b=new Banco();
            sucesso=b.max("SELECT MAX(cod_imagem) from imagem;");
            b.fechar();
            atualizarTBImagem();
            JOptionPane.showMessageDialog(CadastrarImagem,"Código de Imagem: "+sucesso,"Imagem cadastrada.",JOptionPane.INFORMATION_MESSAGE);
            resetarImagem();
        }
            
        }
        
    }//GEN-LAST:event_BCadastrarImagemActionPerformed

    private void BCadastrarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarContainerActionPerformed
        // TODO add your handling code here:
        Container container=new Container();
        int sucesso=0;
        try{
            Tipo_containerDAO tipodao=new Tipo_containerDAO();
            Tipo_container tipo=tipodao.getByNome(CBTipoContainerCadastrarContainer.getSelectedItem().toString());
            System.out.println(CBTipoContainerCadastrarContainer.getSelectedItem().toString());
            tipodao.fechar();
            
            container.setCod_tipo_container(tipo.getCod_tipo_container());
            container.setLocalizacao_container(campoLocalizacaoCadastrarContainer.getText());
            ContainerDAO containerdao=new ContainerDAO();
            sucesso=containerdao.inserir(container);
            containerdao.fechar();
        }
        catch(NumberFormatException|HeadlessException e){
            JOptionPane.showMessageDialog(CadastrarContainer,"Falha na Inserção.","Erro",0);
        }
        finally{
            if(sucesso==0) JOptionPane.showMessageDialog(CadastrarContainer,"Falha na Inserção.","Erro",0);
            else{
                Banco b=new Banco();
                sucesso=b.max("SELECT MAX(cod_container) from container;");
                b.fechar();
                atualizarTBContainer();
                atualizarCamposAcervo();
                JOptionPane.showMessageDialog(CadastrarContainer,"Código de Container: "+sucesso,"Container cadastrado.",JOptionPane.INFORMATION_MESSAGE);
            } 
        }
    }//GEN-LAST:event_BCadastrarContainerActionPerformed

    private void BAlterarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem;
        boolean sucesso=false;
        try{
            ImagemDAO imagemdao=new ImagemDAO();
            imagem=imagemdao.getByCod(Integer.parseInt(campoCodImagemAlterarImagem.getText()));
            imagem.setLink(campoLinkAlterarImagem.getText());
            sucesso=imagemdao.alterar(imagem);
            imagemdao.fechar();
            
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(PainelAcervo,"Campos Incorretos.\nVerifique e tente novamente.","Erro",0);
        }
        finally{
            if(!sucesso) {
                JOptionPane.showMessageDialog(JDAlterarImagem,"Falha na Alteração.","Erro",0);
            }
            else{
                JOptionPane.showMessageDialog(JDAlterarImagem,"Código de Imagem: "+campoCodImagemAlterarImagem.getText(),"Imagem Alterada.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBImagem();
                atualizarTBAcervo();
                JDAlterarImagem.setModal(false);
                JDAlterarImagem.setVisible(false);
                JDAlterarImagem.dispose();
            }
        }
                
        
    }//GEN-LAST:event_BAlterarImagemActionPerformed

    private void CheckImagemAlterarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckImagemAlterarImagemActionPerformed
        // TODO add your handling code here:
        validaImagemAlterarImagem();
    }//GEN-LAST:event_CheckImagemAlterarImagemActionPerformed

    private void campoLinkAlterarImagemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLinkAlterarImagemKeyTyped
        // TODO add your handling code here:
        if(campoLinkAlterarImagem.getText().equals(""));
        LImagemAlterarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png")));
        JDAlterarImagem.pack();
    }//GEN-LAST:event_campoLinkAlterarImagemKeyTyped

    private void BAlterarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarContainerActionPerformed
        // TODO add your handling code here:
        Container container;
        boolean sucesso=false;
        try{
            ContainerDAO containerdao=new ContainerDAO();
            container=containerdao.getByCod(Integer.parseInt(campoCodContainerAlterarContainer.getText()));
            
            Tipo_containerDAO tipodao=new Tipo_containerDAO();
            Tipo_container tipo=tipodao.getByNome(CBTipoContainerAlterarContainer.getSelectedItem().toString());
            tipodao.fechar();
            
            container.setLocalizacao_container(campoLocalizacaoAlterarContainer.getText());
            container.setCod_tipo_container(tipo.getCod_tipo_container());
            
            sucesso=containerdao.alterar(container);
            containerdao.fechar();
            
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(Container,"Campos Incorretos.\nVerifique e tente novamente.","Erro",0);
            }
        finally{
            if(!sucesso){
                JOptionPane.showMessageDialog(JDAlterarContainer,"Falha na Alteração.","Erro",0);
            }
            else{
                JOptionPane.showMessageDialog(Container,"Código de Container: "+campoCodContainerAlterarContainer.getText(),"Container alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBContainer();
                
                JDAlterarContainer.setModal(false);
                JDAlterarContainer.setVisible(false);
                JDAlterarContainer.dispose();
            }
        }
        
    }//GEN-LAST:event_BAlterarContainerActionPerformed

    private void BConfirmarExcluirItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirItemDoacaoActionPerformed
        // TODO add your handling code here:
        Item_doacao item;
        boolean sucesso=false;
        
            Item_doacaoDAO itemdao=new Item_doacaoDAO();
            item=itemdao.getByCod(Integer.parseInt(campoItemDoacaoExcluirItemDoacao.getText()));
            sucesso=itemdao.excluir(item.getCod_item_doacao());
            itemdao.fechar();
            if(!sucesso) JOptionPane.showMessageDialog(JDExcluirItemDoacao, "Item não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
            else{
                JOptionPane.showMessageDialog(JDExcluirItemDoacao, "Item excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBItemDoacao();
                atualizarTBEstoque();
                JDExcluirItemDoacao.setModal(false);
                JDExcluirItemDoacao.setVisible(false);
                JDExcluirItemDoacao.dispose();
            }
        
    }//GEN-LAST:event_BConfirmarExcluirItemDoacaoActionPerformed

    private void BCancelarExcluirItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirItemDoacaoActionPerformed
        // TODO add your handling code here:
        JDExcluirItemDoacao.setModal(false);
        JDExcluirItemDoacao.setVisible(false);
        JDExcluirItemDoacao.dispose();
    }//GEN-LAST:event_BCancelarExcluirItemDoacaoActionPerformed

    private void BExcluirItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirItemDoacaoActionPerformed
        // TODO add your handling code here:
        Item_doacao item;
        Item_doacaoDAO itemdao=new Item_doacaoDAO();
        item=itemdao.getByCod(Integer.parseInt(TItemDoacao.getValueAt(TItemDoacao.getSelectedRow(), 0).toString()));
        itemdao.fechar();
        campoItemDoacaoExcluirItemDoacao.setText(item.getCod_item_doacao()+"");
        
        JDExcluirItemDoacao.setLocationRelativeTo(null);
        JDExcluirItemDoacao.setIconImage(icone);
        JDExcluirItemDoacao.setVisible(true);
        JDExcluirItemDoacao.pack();       
                
    }//GEN-LAST:event_BExcluirItemDoacaoActionPerformed

    private void BConfirmarExcluirDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirDoadorActionPerformed
        // TODO add your handling code here:
        Doador doador;
        DoadorDAO doadordao=new DoadorDAO();
        boolean sucesso=false;
        doador=doadordao.getByCod(Integer.parseInt(campoDoadorExcluirDoador.getText()));
        sucesso=doadordao.excluir(doador.getCod_doador());
        doadordao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirDoador, "Doador não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirDoador, "Doador excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBDoador();
                atualizarCBDoador();
                
                JDExcluirDoador.setModal(false);
                JDExcluirDoador.setVisible(false);
                JDExcluirDoador.dispose();
            }
        
    }//GEN-LAST:event_BConfirmarExcluirDoadorActionPerformed

    private void BCancelarExcluirDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirDoadorActionPerformed
        // TODO add your handling code here:
        JDExcluirDoador.setModal(false);
        JDExcluirDoador.setVisible(false);
        JDExcluirDoador.dispose();
    }//GEN-LAST:event_BCancelarExcluirDoadorActionPerformed

    private void BExcluirDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirDoadorActionPerformed
        // TODO add your handling code here:
        Doador doador;
        DoadorDAO doadordao=new DoadorDAO();
        doador=doadordao.getByCod(Integer.parseInt(TDoador.getValueAt(TDoador.getSelectedRow(), 0).toString()));
        doadordao.fechar();
        
        campoDoadorExcluirDoador.setText(doador.getCod_doador()+"");
        JDExcluirDoador.setLocationRelativeTo(null);
        JDExcluirDoador.setIconImage(icone);
        JDExcluirDoador.setVisible(true);
        JDExcluirDoador.pack(); 
    }//GEN-LAST:event_BExcluirDoadorActionPerformed

    private void BConfirmarExcluirRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirRepasseActionPerformed
        // TODO add your handling code here:
        Repasse repasse;
        RepasseDAO repassedao=new RepasseDAO();
        boolean sucesso=false;
        repasse=repassedao.getByCod(Integer.parseInt(campoRepasseExcluirRepasse.getText()));
        sucesso=repassedao.excluir(repasse.getCod_repasse());
        repassedao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirRepasse, "Repasse não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirRepasse, "Repasse excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBRepasse();
                
                JDExcluirRepasse.setModal(false);
                JDExcluirRepasse.setVisible(false);
                JDExcluirRepasse.dispose();
            }
        
    }//GEN-LAST:event_BConfirmarExcluirRepasseActionPerformed

    private void BCancelarExcluirRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirRepasseActionPerformed
        // TODO add your handling code here:
        JDExcluirRepasse.setModal(false);
        JDExcluirRepasse.setVisible(false);
        JDExcluirRepasse.dispose();
    }//GEN-LAST:event_BCancelarExcluirRepasseActionPerformed

    private void BExcluirRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirRepasseActionPerformed
        // TODO add your handling code here:
        int cod_excluirrepasse=Integer.parseInt(TRepasse.getValueAt(TRepasse.getSelectedRow(), 0).toString());
        Repasse r;
        
        RepasseDAO daor=new RepasseDAO();
        r=daor.getByCod(cod_excluirrepasse);
        daor.fechar();
        
        campoRepasseExcluirRepasse.setText(r.getCod_repasse()+"");
        
        JDExcluirRepasse.setLocationRelativeTo(null);
        JDExcluirRepasse.setIconImage(icone);
        JDExcluirRepasse.setVisible(true);
    }//GEN-LAST:event_BExcluirRepasseActionPerformed

    private void BConfirmarExcluirItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirItemRepasseActionPerformed
        // TODO add your handling code here:
        Item_repasse item;
        Item_repasseDAO itemdao=new Item_repasseDAO();
        boolean sucesso=false;
        item=itemdao.getByCod(Integer.parseInt(campoItemRepasseExcluirItemRepasse.getText()));
        sucesso=itemdao.excluir(item.getCod_item_repasse());
        itemdao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirItemRepasse, "Item Repasse não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirItemRepasse, "Item Repasse excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBItemRepasse();
                atualizarTBEstoque();
                
                JDExcluirItemRepasse.setModal(false);
                JDExcluirItemRepasse.setVisible(false);
                JDExcluirItemRepasse.dispose();
            }
    }//GEN-LAST:event_BConfirmarExcluirItemRepasseActionPerformed

    private void BCancelarExcluirItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirItemRepasseActionPerformed
        // TODO add your handling code here:
        JDExcluirItemRepasse.setModal(false);
        JDExcluirItemRepasse.setVisible(false);
        JDExcluirItemRepasse.dispose();
    }//GEN-LAST:event_BCancelarExcluirItemRepasseActionPerformed

    private void BExcluirItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirItemRepasseActionPerformed
        // TODO add your handling code here:
        int cod_item=Integer.parseInt(TItemRepasse.getValueAt(TItemRepasse.getSelectedRow(), 0).toString());
        Item_repasse r;
        
        Item_repasseDAO daoitem=new Item_repasseDAO();
        r=daoitem.getByCod(cod_item);
        daoitem.fechar();
        
        campoItemRepasseExcluirItemRepasse.setText(r.getCod_item_repasse()+"");
        
        JDExcluirItemRepasse.setLocationRelativeTo(null);
        JDExcluirItemRepasse.setIconImage(icone);
        JDExcluirItemRepasse.setVisible(true);
    }//GEN-LAST:event_BExcluirItemRepasseActionPerformed

    private void BConfirmarExcluirColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirColetorActionPerformed
        // TODO add your handling code here:
        Coletor coletor;
        ColetorDAO coletordao=new ColetorDAO();
        boolean sucesso=false;
        coletor=coletordao.getByCod(Integer.parseInt(campoColetorExcluirColetor.getText()));
        sucesso=coletordao.excluir(coletor.getCod_coletor());
        coletordao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirColetor, "Coletor não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirColetor, "Coletor excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBColetor();
                atualizarCBColetor();
                
                JDExcluirColetor.setModal(false);
                JDExcluirColetor.setVisible(false);
                JDExcluirColetor.dispose();
            }
        
    }//GEN-LAST:event_BConfirmarExcluirColetorActionPerformed

    private void BCancelarExcluirColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirColetorActionPerformed
        // TODO add your handling code here:
        JDExcluirColetor.setModal(false);
        JDExcluirColetor.setVisible(false);
        JDExcluirColetor.dispose();
    }//GEN-LAST:event_BCancelarExcluirColetorActionPerformed

    private void BExcluirColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirColetorActionPerformed
        // TODO add your handling code here:
        Coletor coletor;
        ColetorDAO coletordao=new ColetorDAO();
        coletor=coletordao.getByCod(Integer.parseInt(TColetor.getValueAt(TColetor.getSelectedRow(), 0).toString()));
        coletordao.fechar();
        
        campoColetorExcluirColetor.setText(coletor.getCod_coletor()+"");
        JDExcluirColetor.setLocationRelativeTo(null);
        JDExcluirColetor.setIconImage(icone);
        JDExcluirColetor.setVisible(true);
        JDExcluirColetor.pack(); 
    }//GEN-LAST:event_BExcluirColetorActionPerformed

    private void BExcluirItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirItemAcervoActionPerformed
        // TODO add your handling code here:
        Item_acervo item_acervo;
        Item_acervoDAO item_acervodao=new Item_acervoDAO();
        item_acervo=item_acervodao.getByCod(Integer.parseInt(TAcervo.getValueAt(TAcervo.getSelectedRow(), 0).toString()));
        item_acervodao.fechar();
        
        campoItemAcervoExcluirItemAcervo.setText(item_acervo.getCod_item_acervo()+"");
        JDExcluirItemAcervo.setLocationRelativeTo(null);
        JDExcluirItemAcervo.setIconImage(icone);
        JDExcluirItemAcervo.setVisible(true);
        JDExcluirItemAcervo.pack(); 
    }//GEN-LAST:event_BExcluirItemAcervoActionPerformed

    private void BConfirmarExcluirItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirItemAcervoActionPerformed
        // TODO add your handling code here:
        Item_acervo item_acervo;
        Item_acervoDAO item_acervodao=new Item_acervoDAO();
        boolean sucesso=false;
        item_acervo=item_acervodao.getByCod(Integer.parseInt(campoItemAcervoExcluirItemAcervo.getText()));
        sucesso=item_acervodao.excluir(item_acervo.getCod_item_acervo());
        item_acervodao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirItemAcervo, "Item Acervo não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirItemAcervo, "Item Acervo excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBAcervo();
                
                
                JDExcluirItemAcervo.setModal(false);
                JDExcluirItemAcervo.setVisible(false);
                JDExcluirItemAcervo.dispose();
            }
    }//GEN-LAST:event_BConfirmarExcluirItemAcervoActionPerformed

    private void BCancelarExcluirItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirItemAcervoActionPerformed
        // TODO add your handling code here:
        JDExcluirItemAcervo.setModal(false);
        JDExcluirItemAcervo.setVisible(false);
        JDExcluirItemAcervo.dispose();
    }//GEN-LAST:event_BCancelarExcluirItemAcervoActionPerformed

    private void BConfirmarExcluirImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem;
        ImagemDAO imagemdao=new ImagemDAO();
        boolean sucesso=false;
        imagem=imagemdao.getByCod(Integer.parseInt(campoImagemExcluirImagem.getText()));
        sucesso=imagemdao.excluir(imagem.getCod_imagem());
        imagemdao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirImagem, "Imagem não excluida.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirImagem, "Imagem excluida","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBImagem();
                
                JDExcluirImagem.setModal(false);
                JDExcluirImagem.setVisible(false);
                JDExcluirImagem.dispose();
            }
    }//GEN-LAST:event_BConfirmarExcluirImagemActionPerformed

    private void BCancelarExcluirImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirImagemActionPerformed
        // TODO add your handling code here:
        JDExcluirImagem.setModal(false);
        JDExcluirImagem.setVisible(false);
        JDExcluirImagem.dispose();
    }//GEN-LAST:event_BCancelarExcluirImagemActionPerformed

    private void BExcluirImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem;
        ImagemDAO imagemdao=new ImagemDAO();
        imagem=imagemdao.getByCod(Integer.parseInt(TImagem.getValueAt(TImagem.getSelectedRow(), 0).toString()));
        imagemdao.fechar();
        
        campoImagemExcluirImagem.setText(imagem.getCod_imagem()+"");
        JDExcluirImagem.setLocationRelativeTo(null);
        JDExcluirImagem.setIconImage(icone);
        JDExcluirImagem.setVisible(true);
        JDExcluirImagem.pack(); 
    }//GEN-LAST:event_BExcluirImagemActionPerformed

    private void BExcluirContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirContainerActionPerformed
        // TODO add your handling code here:
        Container container;
        ContainerDAO containerdao=new ContainerDAO();
        container=containerdao.getByCod(Integer.parseInt(TContainer.getValueAt(TContainer.getSelectedRow(), 0).toString()));
        containerdao.fechar();
        
        campoContainerExcluirContainer.setText(container.getCod_container()+"");
        JDExcluirContainer.setLocationRelativeTo(null);
        JDExcluirContainer.setIconImage(icone);
        JDExcluirContainer.setVisible(true);
        JDExcluirContainer.pack(); 
    }//GEN-LAST:event_BExcluirContainerActionPerformed

    private void BConfirmarExcluirContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirContainerActionPerformed
        // TODO add your handling code here:
        Container container;
        ContainerDAO containerdao=new ContainerDAO();
        boolean sucesso=false;
        container=containerdao.getByCod(Integer.parseInt(campoContainerExcluirContainer.getText()));
        sucesso=containerdao.excluir(container.getCod_container());
        containerdao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirContainer, "Container não excluida.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirContainer, "Container excluida","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBContainer();
                
                JDExcluirContainer.setModal(false);
                JDExcluirContainer.setVisible(false);
                JDExcluirContainer.dispose();
            }
    }//GEN-LAST:event_BConfirmarExcluirContainerActionPerformed

    private void BCancelarExcluirContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirContainerActionPerformed
        // TODO add your handling code here:
        JDExcluirContainer.setModal(false);
        JDExcluirContainer.setVisible(false);
        JDExcluirContainer.dispose();
    }//GEN-LAST:event_BCancelarExcluirContainerActionPerformed

    private void resetarImagem(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_resetarImagem
        // TODO add your handling code here:
         resetarImagem();
    }//GEN-LAST:event_resetarImagem

    private void BExcluirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirUsuarioActionPerformed
        // TODO add your handling code here:
        Usuario u=new Usuario();
        int codusuario=Integer.parseInt(TUsuario.getValueAt(TUsuario.getSelectedRow(), 0).toString());
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByCod(codusuario);
        daou.fechar();
        
        
    }//GEN-LAST:event_BExcluirUsuarioActionPerformed

    private void BEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarUsuarioActionPerformed
        // TODO add your handling code here:
        Usuario u=new Usuario();
        int codusuario=Integer.parseInt(TUsuario.getValueAt(TUsuario.getSelectedRow(), 0).toString());
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByCod(codusuario);
        daou.fechar();
        
        campoCodigoUsuarioAlterarUsuarioJD.setText(u.getCod_usuario()+"");
        campoNomeUsuarioAlterarUsuarioJD.setText(u.getNome_usuario());
        campoRegistroAcademicoAlterarUsuarioJD.setText(u.getRegistro_academico());
        campoEmailAlterarUsuarioJD.setText(u.getEmail());
        campoNovaSenhaAlterarUsuarioJD.setText("");
        campoRepetirNovaSenhaAlterarUsuarioJD.setText("");
        CheckAdministradorAlterarUsuarioJD.setSelected(u.isUsuario_administrador());
        
        JDAlterarUsuario.setLocationRelativeTo(null);
        JDAlterarUsuario.setIconImage(icone);
        JDAlterarUsuario.setVisible(true);
        JDAlterarUsuario.pack();
    }//GEN-LAST:event_BEditarUsuarioActionPerformed

    private void BAlterarUsuarioJDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarUsuarioJDActionPerformed
        // TODO add your handling code here:
        Usuario u;
        UsuarioDAO daou=new UsuarioDAO();
        u=daou.getByCod(Integer.parseInt(campoCodigoUsuarioAlterarUsuarioJD.getText()));
        boolean sucesso=false;
        
        try{
            String novasenha=new String(campoNovaSenhaAlterarUsuarioJD.getPassword());
            String repetirnovasenha=new String(campoRepetirNovaSenhaAlterarUsuarioJD.getPassword());
            if(novasenha.equals(repetirnovasenha)&&!novasenha.equals("")){
                u.setChave_encriptacao(UUID.randomUUID().toString().substring(0,16));
                Encryptor aes=new Encryptor();
                u.setSenha_usuario(aes.encrypt(u.getChave_encriptacao(), novasenha));
            }
            u.setEmail(campoEmailAlterarUsuarioJD.getText());
            u.setNome_usuario(campoNomeUsuarioAlterarUsuarioJD.getText());
            u.setRegistro_academico(campoRegistroAcademicoAlterarUsuarioJD.getText());
            u.setUsuario_administrador(CheckAdministradorAlterarUsuarioJD.isSelected());
            sucesso=daou.alterar(u);
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(Usuarios,"Campos Incorretos.\nVerifique e tente novamente.","Erro",0);
            }
        finally{
            if(!sucesso){
                JOptionPane.showMessageDialog(JDAlterarUsuario,"Falha na Alteração.","Erro",0);
            }
            else{
                JOptionPane.showMessageDialog(Usuarios,"Código de Usuário: "+campoCodigoUsuarioAlterarUsuarioJD.getText(),"Usuario alterado.",JOptionPane.INFORMATION_MESSAGE);
                atualizarTBUsuario();
                
                JDAlterarUsuario.setModal(false);
                JDAlterarUsuario.setVisible(false);
                JDAlterarUsuario.dispose();
            }
        }
        daou.fechar();
    }//GEN-LAST:event_BAlterarUsuarioJDActionPerformed

    private void BConfirmarExcluirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirUsuarioActionPerformed
        // TODO add your handling code here:
        Usuario u;
        UsuarioDAO udao=new UsuarioDAO();
        boolean sucesso=false;
        u=udao.getByCod(Integer.parseInt(campoUsuarioExcluirUsuario.getText()));
        sucesso=udao.excluir(u.getCod_usuario());
        udao.fechar();
        if(!sucesso) JOptionPane.showMessageDialog(JDExcluirUsuario, "Usuario não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.","Erro",0);
        else{
                JOptionPane.showMessageDialog(JDExcluirUsuario, "Usuario excluido","Sucesso",JOptionPane.PLAIN_MESSAGE);
                
                atualizarTBUsuario();
                atualizarCBUsuario();
                JDExcluirUsuario.setModal(false);
                JDExcluirUsuario.setVisible(false);
                JDExcluirUsuario.dispose();
            }
    }//GEN-LAST:event_BConfirmarExcluirUsuarioActionPerformed

    private void BCancelarExcluirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirUsuarioActionPerformed
        // TODO add your handling code here:
        JDExcluirUsuario.setModal(false);
        JDExcluirUsuario.setVisible(false);
        JDExcluirUsuario.dispose();
    }//GEN-LAST:event_BCancelarExcluirUsuarioActionPerformed
    private void MostrarRelatorio(String consultaSQL,String formatoXml){
        Banco b=new Banco();
        try {
            
            JasperDesign jasperDesign = JRXmlLoader.load(formatoXml);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(consultaSQL);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, b.getConexao());
            JasperViewer.viewReport(jasperPrint,false);
            
           
        } catch (JRException e) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
        }
        
        
        b.fechar();
    }
    private void BRelatorioDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioDoacaoActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioDoacao.jrxml";
        String sql = "select * from doacao_detalhado;";
        MostrarRelatorio(sql,form);
                
        
    }//GEN-LAST:event_BRelatorioDoacaoActionPerformed

    private void BRelatorioItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioItemDoacaoActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioItemDoacao.jrxml";
        String sql = "select * from item_doacao_detalhado;";
        MostrarRelatorio(sql,form);
    }//GEN-LAST:event_BRelatorioItemDoacaoActionPerformed

    private void BRelatorioEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioEstoqueActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioEstoque.jrxml";
        String sql = "select * from estoque_detalhado;";
        MostrarRelatorio(sql,form);
    }//GEN-LAST:event_BRelatorioEstoqueActionPerformed

    private void BRelatorioDoadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioDoadoresActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioDoador.jrxml";
        String sql = "select * from doador_detalhado;";
        MostrarRelatorio(sql,form);
    }//GEN-LAST:event_BRelatorioDoadoresActionPerformed

    private void BRelatorioRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioRepasseActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioRepasse.jrxml";
        String sql = "select * from repasse_detalhado;";
        MostrarRelatorio(sql,form);
    }//GEN-LAST:event_BRelatorioRepasseActionPerformed

    private void BRelatorioItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioItemRepasseActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioItemRepasse.jrxml";
        String sql = "select * from item_repasse_detalhado;";
        MostrarRelatorio(sql,form);
    }//GEN-LAST:event_BRelatorioItemRepasseActionPerformed

    private void BRelatorioColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioColetorActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioColetor.jrxml";
        String sql = "select * from coletor_detalhado;";
        MostrarRelatorio(sql,form);
    }//GEN-LAST:event_BRelatorioColetorActionPerformed

    private void BRelatorioAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioAcervoActionPerformed
        // TODO add your handling code here:
        String form="src/relatorios/relatorioAcervo.jrxml";
        String sql = "select * from acervo_detalhado;";
        MostrarRelatorio(sql,form);
    }//GEN-LAST:event_BRelatorioAcervoActionPerformed
    private void validaImagemAlterarImagem(){
        BufferedImage image;
        int h,w,times;
        try {
            image = ImageIO.read(new URL(campoLinkAlterarImagem.getText()));
            h=image.getHeight();
            w=image.getWidth();
            if(w>=h){
            times=w/LImagemAlterarImagem.getWidth();
            w=w/(times+1);
            h=h/(times+1);
            }
        else{
            times=h/LImagemAlterarImagem.getHeight();
            w=w/(times+1);
            h=h/(times+1);
        }
        Image resizedImage = image.getScaledInstance(w, h, 0);
        LImagemAlterarImagem.setIcon(new javax.swing.ImageIcon(resizedImage));
        //return true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        JDAlterarImagem.pack();
    }
    private void resetarImagem(){
        LImagemAlterarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png")));
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable(){
             public void run() {
                new Principal().setVisible(true);
            }
            //Principal p=new Principal();
            //p.setExtendedState(JFrame.MAXIMIZED_BOTH);
            //p.setVisible(true);
       });
    }
	
//Variáveis
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane AbaDoUsuario;
    private javax.swing.JTabbedPane Acervo;
    private javax.swing.JButton BAlterarColetor;
    private javax.swing.JButton BAlterarContainer;
    private javax.swing.JButton BAlterarDoacao;
    private javax.swing.JButton BAlterarDoador;
    private javax.swing.JButton BAlterarImagem;
    private javax.swing.JButton BAlterarItemAcervo;
    private javax.swing.JButton BAlterarItemDoacao;
    private javax.swing.JButton BAlterarItemRepasse;
    private javax.swing.JButton BAlterarRepasse;
    private javax.swing.JButton BAlterarUsuario;
    private javax.swing.JButton BAlterarUsuarioJD;
    private javax.swing.JButton BCadastrarColetor;
    private javax.swing.JButton BCadastrarContainer;
    private javax.swing.JButton BCadastrarDestinacao;
    private javax.swing.JButton BCadastrarDoacao;
    private javax.swing.JButton BCadastrarDoador;
    private javax.swing.JButton BCadastrarEventoOrigem;
    private javax.swing.JButton BCadastrarImagem;
    private javax.swing.JButton BCadastrarInterface;
    private javax.swing.JToggleButton BCadastrarItemAcervo;
    private javax.swing.JToggleButton BCadastrarItemDoacao;
    private javax.swing.JToggleButton BCadastrarItemRepasse;
    private javax.swing.JButton BCadastrarMarca;
    private javax.swing.JButton BCadastrarModelo;
    private javax.swing.JButton BCadastrarRepasse;
    private javax.swing.JButton BCadastrarTecnologia;
    private javax.swing.JButton BCadastrarTipo;
    private javax.swing.JButton BCadastrarTipoColetor;
    private javax.swing.JButton BCadastrarTipoContainer;
    private javax.swing.JButton BCadastrarUsuario;
    private javax.swing.JButton BCancelarExcluirColetor;
    private javax.swing.JButton BCancelarExcluirContainer;
    private javax.swing.JButton BCancelarExcluirDoacao;
    private javax.swing.JButton BCancelarExcluirDoador;
    private javax.swing.JButton BCancelarExcluirImagem;
    private javax.swing.JButton BCancelarExcluirItemAcervo;
    private javax.swing.JButton BCancelarExcluirItemDoacao;
    private javax.swing.JButton BCancelarExcluirItemRepasse;
    private javax.swing.JButton BCancelarExcluirRepasse;
    private javax.swing.JButton BCancelarExcluirUsuario;
    private javax.swing.JButton BCancelarLogin;
    private javax.swing.JButton BCheckLink;
    private javax.swing.JButton BConfirmarExcluirColetor;
    private javax.swing.JButton BConfirmarExcluirContainer;
    private javax.swing.JButton BConfirmarExcluirDoacao;
    private javax.swing.JButton BConfirmarExcluirDoador;
    private javax.swing.JButton BConfirmarExcluirImagem;
    private javax.swing.JButton BConfirmarExcluirItemAcervo;
    private javax.swing.JButton BConfirmarExcluirItemDoacao;
    private javax.swing.JButton BConfirmarExcluirItemRepasse;
    private javax.swing.JButton BConfirmarExcluirRepasse;
    private javax.swing.JButton BConfirmarExcluirUsuario;
    private javax.swing.JToggleButton BDeslogar;
    private javax.swing.JButton BEditarColetor;
    private javax.swing.JButton BEditarContainer;
    private javax.swing.JButton BEditarDoacao;
    private javax.swing.JButton BEditarDoador;
    private javax.swing.JButton BEditarImagem;
    private javax.swing.JButton BEditarItemAcervo;
    private javax.swing.JButton BEditarItemDoacao;
    private javax.swing.JButton BEditarItemRepasse;
    private javax.swing.JButton BEditarRepasse;
    private javax.swing.JButton BEditarUsuario;
    private javax.swing.JButton BEsqueciSenha;
    private javax.swing.JButton BExcluirColetor;
    private javax.swing.JButton BExcluirContainer;
    private javax.swing.JButton BExcluirDoacao;
    private javax.swing.JButton BExcluirDoador;
    private javax.swing.JButton BExcluirImagem;
    private javax.swing.JButton BExcluirItemAcervo;
    private javax.swing.JButton BExcluirItemDoacao;
    private javax.swing.JButton BExcluirItemRepasse;
    private javax.swing.JButton BExcluirRepasse;
    private javax.swing.JButton BExcluirUsuario;
    private javax.swing.JButton BLogar;
    private javax.swing.JButton BNovoColetor;
    private javax.swing.JButton BNovoDestinacao;
    private javax.swing.JButton BNovoDoador;
    private javax.swing.JButton BNovoDoadorAcervo;
    private javax.swing.JButton BNovoEventoOrigem;
    private javax.swing.JButton BNovoInterface;
    private javax.swing.JButton BNovoMarca;
    private javax.swing.JButton BNovoModelo;
    private javax.swing.JButton BNovoTecnologia;
    private javax.swing.JButton BNovoTipoColetor;
    private javax.swing.JButton BNovoTipoContainer;
    private javax.swing.JButton BNovoTipoItem;
    private javax.swing.JButton BNovoTipoItemDoacao;
    private javax.swing.JButton BNovoTipoItemDoacao1;
    private javax.swing.JButton BRelatorioAcervo;
    private javax.swing.JButton BRelatorioColetor;
    private javax.swing.JButton BRelatorioDoacao;
    private javax.swing.JButton BRelatorioDoadores;
    private javax.swing.JButton BRelatorioEstoque;
    private javax.swing.JButton BRelatorioItemDoacao;
    private javax.swing.JButton BRelatorioItemRepasse;
    private javax.swing.JButton BRelatorioRepasse;
    private javax.swing.JCheckBox CBAdministrador;
    private javax.swing.JComboBox CBColetor;
    private javax.swing.JComboBox CBDestinacao;
    private javax.swing.JComboBox CBDestinacaoAlterarRepasse;
    private javax.swing.JComboBox CBDoadorDoacao;
    private javax.swing.JComboBox CBDoadorItemAcervo;
    private javax.swing.JComboBox CBEventoOrigem;
    private javax.swing.JComboBox CBEventoOrigemAlterarDoacao;
    private javax.swing.JCheckBox CBFunciona;
    private javax.swing.JComboBox CBInterface;
    private javax.swing.JComboBox CBInterfaceAlterarItemAcervo;
    private javax.swing.JComboBox CBMarca;
    private javax.swing.JComboBox CBMarcaAlterarItemAcervo;
    private javax.swing.JComboBox CBModelo;
    private javax.swing.JComboBox CBModeloAlterarItemAcervo;
    private javax.swing.JComboBox CBTecnologia;
    private javax.swing.JComboBox CBTecnologiaAlterarItemAcervo;
    private javax.swing.JComboBox CBTipoAlterarItemAcervo;
    private javax.swing.JComboBox CBTipoAlterarItemDoacao;
    private javax.swing.JComboBox CBTipoAlterarItemRepasse;
    private javax.swing.JComboBox CBTipoColetor;
    private javax.swing.JComboBox CBTipoColetorAlterarColetor;
    private javax.swing.JComboBox CBTipoContainerAlterarContainer;
    private javax.swing.JComboBox CBTipoContainerCadastrarContainer;
    private javax.swing.JComboBox CBTipoItemAcervo;
    private javax.swing.JComboBox CBTipoItemDoacao;
    private javax.swing.JComboBox CBTipoItemRepasse;
    private javax.swing.JComboBox CBUsuarioLogin;
    private javax.swing.JPanel CadastrarContainer;
    private javax.swing.JPanel CadastrarDoacao;
    private javax.swing.JPanel CadastrarImagem;
    private javax.swing.JPanel CadastrarItemAcervo;
    private javax.swing.JPanel CadastrarItemDoacao;
    private javax.swing.JPanel CadastrarItemRepasse;
    private javax.swing.JPanel CadastrarRepasse;
    private javax.swing.JPanel CadastrarUsuario;
    private javax.swing.JCheckBox CheckAdministradorAlterarUsuarioJD;
    private javax.swing.JCheckBox CheckFuncionaAlterarItemAcervo;
    private javax.swing.JButton CheckImagemAlterarImagem;
    private javax.swing.JPanel Container;
    private javax.swing.JPanel Deslogar;
    private javax.swing.JTabbedPane Doacoes;
    private javax.swing.JScrollPane ExibirColetores;
    private javax.swing.JScrollPane ExibirDoadores;
    private javax.swing.JScrollPane ExibirUsuarios;
    private javax.swing.JLabel Header;
    private javax.swing.JPanel Imagens;
    private javax.swing.JDialog JDAlterarColetor;
    private javax.swing.JDialog JDAlterarContainer;
    private javax.swing.JDialog JDAlterarDoacao;
    private javax.swing.JDialog JDAlterarDoador;
    private javax.swing.JDialog JDAlterarImagem;
    private javax.swing.JDialog JDAlterarItemAcervo;
    private javax.swing.JDialog JDAlterarItemDoacao;
    private javax.swing.JDialog JDAlterarItemRepasse;
    private javax.swing.JDialog JDAlterarRepasse;
    private javax.swing.JDialog JDAlterarUsuario;
    private javax.swing.JDialog JDCadastrarColetor;
    private javax.swing.JDialog JDCadastrarDestinacao;
    private javax.swing.JDialog JDCadastrarDoador;
    private javax.swing.JDialog JDCadastrarEventoOrigem;
    private javax.swing.JDialog JDCadastrarInterface;
    private javax.swing.JDialog JDCadastrarMarca;
    private javax.swing.JDialog JDCadastrarModelo;
    private javax.swing.JDialog JDCadastrarTecnologia;
    private javax.swing.JDialog JDCadastrarTipoColetor;
    private javax.swing.JDialog JDCadastrarTipoContainer;
    private javax.swing.JDialog JDCadastrarTipoItem;
    private javax.swing.JDialog JDCarregandoDados;
    private javax.swing.JDialog JDExcluirColetor;
    private javax.swing.JDialog JDExcluirContainer;
    private javax.swing.JDialog JDExcluirDoacao;
    private javax.swing.JDialog JDExcluirDoador;
    private javax.swing.JDialog JDExcluirImagem;
    private javax.swing.JDialog JDExcluirItemAcervo;
    private javax.swing.JDialog JDExcluirItemDoacao;
    private javax.swing.JDialog JDExcluirItemRepasse;
    private javax.swing.JDialog JDExcluirRepasse;
    private javax.swing.JDialog JDExcluirUsuario;
    private javax.swing.JDialog JDLogin;
    private javax.swing.JFrame JFCarregandoDados;
    private javax.swing.JProgressBar JPBCarregando;
    private javax.swing.JLabel LAguarde;
    private javax.swing.JLabel LAlterar;
    private javax.swing.JLabel LAnoAlterarItemAcervo;
    private javax.swing.JLabel LAnoItemAcervo;
    private javax.swing.JLabel LCamposAlterarColetor;
    private javax.swing.JLabel LCamposAlterarContainer;
    private javax.swing.JLabel LCamposAlterarDoacao;
    private javax.swing.JLabel LCamposAlterarImagem;
    private javax.swing.JLabel LCamposAlterarItemAcervo;
    private javax.swing.JLabel LCamposAlterarItemDoacao;
    private javax.swing.JLabel LCamposAlterarItemDoacao1;
    private javax.swing.JLabel LCamposAlterarItemDoacao2;
    private javax.swing.JLabel LCamposAlterarItemRepasse;
    private javax.swing.JLabel LCamposAlterarRepasse;
    private javax.swing.JLabel LCamposCadastrarContainer;
    private javax.swing.JLabel LCamposCadastrarImagem;
    private javax.swing.JLabel LCamposCadastrarUsuario;
    private javax.swing.JLabel LCamposComplementares;
    private javax.swing.JLabel LCamposObrigatorios;
    private javax.swing.JLabel LCamposObrigatorios1;
    private javax.swing.JLabel LCamposObrigatorios2;
    private javax.swing.JLabel LCamposObrigatorios3;
    private javax.swing.JLabel LCamposObrigatorios4;
    private javax.swing.JLabel LCamposObrigatorios5;
    private javax.swing.JLabel LCamposRegularesAlterarUsuario;
    private javax.swing.JLabel LCapacidadeAlterarItemAcervo;
    private javax.swing.JLabel LCapacidade_MB;
    private javax.swing.JLabel LCarregando;
    private javax.swing.JLabel LCarregandoGif;
    private javax.swing.JLabel LCautionExcluirColetor;
    private javax.swing.JLabel LCautionExcluirContainer;
    private javax.swing.JLabel LCautionExcluirDoacao;
    private javax.swing.JLabel LCautionExcluirDoador;
    private javax.swing.JLabel LCautionExcluirImagem;
    private javax.swing.JLabel LCautionExcluirItemAcervo;
    private javax.swing.JLabel LCautionExcluirItemDoacao;
    private javax.swing.JLabel LCautionExcluirItemRepasse;
    private javax.swing.JLabel LCautionExcluirRepasse;
    private javax.swing.JLabel LCautionExcluirUsuario;
    private javax.swing.JLabel LCertezaDeslogar;
    private javax.swing.JLabel LCodColetorItemRepasse;
    private javax.swing.JLabel LCodContainerAlterarContainer;
    private javax.swing.JLabel LCodContainerCadastrarContainer;
    private javax.swing.JLabel LCodDestinacao;
    private javax.swing.JLabel LCodDoador;
    private javax.swing.JLabel LCodEO;
    private javax.swing.JLabel LCodImagemAlterarImagem;
    private javax.swing.JLabel LCodInterface;
    private javax.swing.JLabel LCodItemAcervo;
    private javax.swing.JLabel LCodItemAcervoAlterarImagem;
    private javax.swing.JLabel LCodItemRepasse;
    private javax.swing.JLabel LCodMarca;
    private javax.swing.JLabel LCodModelo;
    private javax.swing.JLabel LCodNovoColetor;
    private javax.swing.JLabel LCodNovoUsuario;
    private javax.swing.JLabel LCodRepasse;
    private javax.swing.JLabel LCodRepasseItemRepasse;
    private javax.swing.JLabel LCodTecnologia;
    private javax.swing.JLabel LCodTipo;
    private javax.swing.JLabel LCodTipoColetor;
    private javax.swing.JLabel LCodTipoContainer;
    private javax.swing.JLabel LCodUsuario;
    private javax.swing.JLabel LCodUsuarioInfo;
    private javax.swing.JLabel LCodUsuarioItemRepasse;
    private javax.swing.JLabel LCod_Doador;
    private javax.swing.JLabel LCodigo_ColetorAlterarColetor;
    private javax.swing.JLabel LCodigo_ColetorExcluirColetor;
    private javax.swing.JLabel LCodigo_ContainerExcluirContainer;
    private javax.swing.JLabel LCodigo_ImagemExcluirImagem;
    private javax.swing.JLabel LCodigo_ItemAcervoExcluirItemAcervo;
    private javax.swing.JLabel LCodigo_ItemAlterarItemAcervo;
    private javax.swing.JLabel LCodigo_ItemDoacaoExcluirItemDoacao;
    private javax.swing.JLabel LCodigo_ItemRepasseExcluirItemRepasse;
    private javax.swing.JLabel LCodigo_RepasseAlterarItemRepasse;
    private javax.swing.JLabel LCodigo_RepasseExcluirRepasse;
    private javax.swing.JLabel LCodigo_UsuarioAlterarUsuario;
    private javax.swing.JLabel LCodigo_UsuarioExcluirUsuario;
    private javax.swing.JLabel LCodigo_doacao;
    private javax.swing.JLabel LCodigo_doacaoAlterarDoacao;
    private javax.swing.JLabel LCodigo_doacaoAlterarDoacao1;
    private javax.swing.JLabel LCodigo_doacaoAlterarRepasse;
    private javax.swing.JLabel LCodigo_doacaoExcluirDoacao;
    private javax.swing.JLabel LCodigo_doadorAlterarDoador;
    private javax.swing.JLabel LCodigo_doadorExcluirDoador;
    private javax.swing.JLabel LColetorRepasse;
    private javax.swing.JLabel LConfirmaExcluirColetor;
    private javax.swing.JLabel LConfirmaExcluirContainer;
    private javax.swing.JLabel LConfirmaExcluirDoacao;
    private javax.swing.JLabel LConfirmaExcluirDoador;
    private javax.swing.JLabel LConfirmaExcluirImagem;
    private javax.swing.JLabel LConfirmaExcluirItemAcervo;
    private javax.swing.JLabel LConfirmaExcluirItemDoacao;
    private javax.swing.JLabel LConfirmaExcluirItemRepasse;
    private javax.swing.JLabel LConfirmaExcluirRepasse;
    private javax.swing.JLabel LConfirmaExcluirUsuario;
    private javax.swing.JLabel LContainer;
    private javax.swing.JLabel LContainerAlterarItemAcervo;
    private javax.swing.JLabel LData;
    private javax.swing.JLabel LDataAcervo;
    private javax.swing.JLabel LDataAlterarDoacao;
    private javax.swing.JLabel LDataAlterarDoacao1;
    private javax.swing.JLabel LDataAlterarItemAcervo;
    private javax.swing.JLabel LDataAlterarItemRepasse;
    private javax.swing.JLabel LDataAlterarRepasse;
    private javax.swing.JLabel LDataRepasse;
    private javax.swing.JLabel LDescricao;
    private javax.swing.JLabel LDescricaoAlterarItemAcervo;
    private javax.swing.JLabel LDescricaoRepasse;
    private javax.swing.JLabel LDoacaoItemDoacao;
    private javax.swing.JLabel LDoadorAlterarDoacao;
    private javax.swing.JLabel LDoadorAlterarDoacao1;
    private javax.swing.JLabel LDoadorAlterarItemAcervo;
    private javax.swing.JLabel LDoadorAlterarItemRepasse;
    private javax.swing.JLabel LDoadorAlterarRepasse;
    private javax.swing.JLabel LDoadorItemDoacao;
    private javax.swing.JLabel LEmail;
    private javax.swing.JLabel LEmailAlterarUsuario;
    private javax.swing.JLabel LEmailAlterarUsuarioJD;
    private javax.swing.JLabel LFotoAcervo;
    private javax.swing.JLabel LFuncionaAlterarItemAcervo;
    private javax.swing.JLabel LImagemAlterarImagem;
    private javax.swing.JLabel LImagemCadastrarImagem;
    private javax.swing.JLabel LInterface;
    private javax.swing.JLabel LInterfaceAlterarItemAcervo;
    private javax.swing.JLabel LItemAcervoCadastrarImagem;
    private javax.swing.JLabel LItemDoacao;
    private javax.swing.JLabel LItemFunciona;
    private javax.swing.JLabel LLinkAlterarImagem;
    private javax.swing.JLabel LLinkCadastrarImagem;
    private javax.swing.JLabel LLocalizacaoAlterarContainer;
    private javax.swing.JLabel LLocalizacaoCadastrarContainer;
    private javax.swing.JLabel LMarca;
    private javax.swing.JLabel LMarcaAlterarItemAcervo;
    private javax.swing.JLabel LModeloAlterarItemAcervo;
    private javax.swing.JLabel LModeloItemAcervo;
    private javax.swing.JLabel LNomeColetor;
    private javax.swing.JLabel LNomeColetorAlterarColetor;
    private javax.swing.JLabel LNomeDestinacao;
    private javax.swing.JLabel LNomeDoador;
    private javax.swing.JLabel LNomeDoadorAlterarDoador;
    private javax.swing.JLabel LNomeEO;
    private javax.swing.JLabel LNomeInterface;
    private javax.swing.JLabel LNomeMarca;
    private javax.swing.JLabel LNomeModelo;
    private javax.swing.JLabel LNomeTecnologia;
    private javax.swing.JLabel LNomeUsuario;
    private javax.swing.JLabel LNomeUsuarioInfo;
    private javax.swing.JLabel LNome_UsuarioAlterarUsuario;
    private javax.swing.JLabel LNovaSenhaAlterarUsuarioJD;
    private javax.swing.JLabel LOrigem;
    private javax.swing.JLabel LOrigemAlterarDoacao;
    private javax.swing.JLabel LOrigemAlterarDoacao1;
    private javax.swing.JLabel LOrigemAlterarItemRepasse;
    private javax.swing.JLabel LOrigemAlterarRepasse;
    private javax.swing.JLabel LPO;
    private javax.swing.JLabel LPO10;
    private javax.swing.JLabel LPO11;
    private javax.swing.JLabel LPO3;
    private javax.swing.JLabel LPO4;
    private javax.swing.JLabel LPO5;
    private javax.swing.JLabel LPO6;
    private javax.swing.JLabel LPO7;
    private javax.swing.JLabel LPO8;
    private javax.swing.JLabel LPO9;
    private javax.swing.JLabel LPermissaoAlterarUsuarioJD;
    private javax.swing.JLabel LPermissão;
    private javax.swing.JLabel LQuantidadeItemDoacao;
    private javax.swing.JLabel LQuantidadeItemRepasse;
    private javax.swing.JLabel LRegistroAcademico;
    private javax.swing.JLabel LRegistroAcademicoAlterarUsuario;
    private javax.swing.JLabel LRegistroAcademicoAlterarUsuarioJD;
    private javax.swing.JLabel LRepetirNovaSenhaAlterarUsuarioJD;
    private javax.swing.JLabel LRepetirSenha;
    private javax.swing.JLabel LRepetirSenhaAlterarUsuario;
    private javax.swing.JLabel LSenha;
    private javax.swing.JLabel LSenhaAlterar;
    private javax.swing.JLabel LSenhaLogin;
    private javax.swing.JLabel LTecnologia;
    private javax.swing.JLabel LTecnologiaAlterarItemAcervo;
    private javax.swing.JLabel LTipo;
    private javax.swing.JLabel LTipoAlterarItemAcervo;
    private javax.swing.JLabel LTipoColetor;
    private javax.swing.JLabel LTipoColetorAlterarColetor;
    private javax.swing.JLabel LTipoContainerAlterarContainer;
    private javax.swing.JLabel LTipoContainerCadastrarContainer;
    private javax.swing.JLabel LTipoItemAcervo;
    private javax.swing.JLabel LTipoItemDoacao;
    private javax.swing.JLabel LTipoItemRepasse;
    private javax.swing.JLabel LTipoTipoColetor;
    private javax.swing.JLabel LTipoTipoContainer;
    private javax.swing.JLabel LUsuarioAdm;
    private javax.swing.JLabel LUsuarioCadastrarContainer;
    private javax.swing.JLabel LUsuarioCadastrarImagem;
    private javax.swing.JLabel LUsuarioItemDoacao;
    private javax.swing.JLabel LUsuarioRegistrarRepasse;
    private javax.swing.JLabel LUsuário;
    private javax.swing.JLabel LUsuárioAlterarDoacao;
    private javax.swing.JLabel LUsuárioAlterarDoacao1;
    private javax.swing.JLabel LUsuárioAlterarItemAcervo;
    private javax.swing.JLabel LUsuárioAlterarItemRepasse;
    private javax.swing.JLabel LUsuárioAlterarRepasse;
    private javax.swing.JLabel LUsuárioLogin;
    private javax.swing.JLabel Ldoador;
    private javax.swing.JPanel PainelAcervo;
    private javax.swing.JPanel PainelColetores;
    private javax.swing.JPanel PainelDoacoes;
    private javax.swing.JPanel PainelDoadores;
    private javax.swing.JPanel PainelEstoque;
    private javax.swing.JPanel PainelItemDoacao;
    private javax.swing.JPanel PainelItemRepasse;
    private javax.swing.JPanel PainelRepasse;
    private javax.swing.JPanel PainelUsuario;
    private javax.swing.JTabbedPane Principal;
    private javax.swing.JScrollPane RelatorioAcervo;
    private javax.swing.JScrollPane RelatorioDoacoes;
    private javax.swing.JScrollPane RelatorioEstoque;
    private javax.swing.JScrollPane RelatorioItemDoacao;
    private javax.swing.JScrollPane RelatorioItemRepasse;
    private javax.swing.JScrollPane RelatorioRepasse;
    private javax.swing.JTabbedPane Repasses;
    private javax.swing.JScrollPane SPContainer;
    private javax.swing.JScrollPane SPDescricaoAlterarItemAcervo;
    private javax.swing.JScrollPane SPDescricaoItemAcervo;
    private javax.swing.JScrollPane SPImagem;
    private javax.swing.JPanel SuasInformacoes;
    private javax.swing.JTextArea TADescricaoAlterarItemAcervo;
    private javax.swing.JTable TAcervo;
    private javax.swing.JTable TColetor;
    private javax.swing.JTable TContainer;
    private javax.swing.JTable TDoacao;
    private javax.swing.JTable TDoador;
    private javax.swing.JTable TEstoque;
    private javax.swing.JTable TImagem;
    private javax.swing.JTable TItemDoacao;
    private javax.swing.JTable TItemRepasse;
    private javax.swing.JTable TRepasse;
    private javax.swing.JTable TUsuario;
    private javax.swing.JTabbedPane Usuarios;
    private javax.swing.JCheckBox campoAdministradorAlterarUsuario;
    private javax.swing.JTextField campoAnoAlterarItemAcervo;
    private javax.swing.JTextField campoAnoItemAcervo;
    private javax.swing.JTextField campoCapacidadeAlterarItemAcervo;
    private javax.swing.JTextField campoCapacidadeItemAcervo;
    private javax.swing.JTextField campoCodContainerAlterarContainer;
    private javax.swing.JTextField campoCodContainerCadastrarContainer;
    private javax.swing.JTextField campoCodContainerCadastrarItemAcervo;
    private javax.swing.JTextField campoCodImagemAlterarImagem;
    private javax.swing.JTextField campoCodImagemCadastrarImagem;
    private javax.swing.JTextField campoCodItemAcervoAlterarImagem;
    private javax.swing.JTextField campoCodigoAlterarUsuario;
    private javax.swing.JTextField campoCodigoColetor;
    private javax.swing.JTextField campoCodigoColetorAlterarColetor;
    private javax.swing.JTextField campoCodigoDestinacao;
    private javax.swing.JTextField campoCodigoDoador;
    private javax.swing.JTextField campoCodigoDoadorAlterarDoador;
    private javax.swing.JTextField campoCodigoEventoOrigem;
    private javax.swing.JTextField campoCodigoInterface;
    private javax.swing.JTextField campoCodigoItemAcervo;
    private javax.swing.JTextField campoCodigoMarca;
    private javax.swing.JTextField campoCodigoModelo;
    private javax.swing.JTextField campoCodigoNovoUsuario;
    private javax.swing.JTextField campoCodigoTecnologia;
    private javax.swing.JTextField campoCodigoTipoColetor;
    private javax.swing.JTextField campoCodigoTipoContainer;
    private javax.swing.JTextField campoCodigoTipoItem;
    private javax.swing.JTextField campoCodigoUsuarioAlterarUsuarioJD;
    private javax.swing.JTextField campoColetorAlterarItemRepasse;
    private javax.swing.JTextField campoColetorAlterarRepasse;
    private javax.swing.JTextField campoColetorExcluirColetor;
    private javax.swing.JTextField campoColetorItemRepasse;
    private javax.swing.JTextField campoContainerAlterarItemAcervo;
    private javax.swing.JTextField campoContainerExcluirContainer;
    private javax.swing.JFormattedTextField campoDataAlterarDoacao;
    private javax.swing.JFormattedTextField campoDataAlterarItemAcervo;
    private javax.swing.JFormattedTextField campoDataAlterarRepasse;
    private javax.swing.JFormattedTextField campoDataDoacao;
    private javax.swing.JFormattedTextField campoDataItemAcervo;
    private javax.swing.JFormattedTextField campoDataRepasse;
    private javax.swing.JTextPane campoDescricaoItemAcervo;
    private javax.swing.JTextField campoDoacao;
    private javax.swing.JTextField campoDoacaoAlterarDoacao;
    private javax.swing.JTextField campoDoacaoExcluirDoacao;
    private javax.swing.JTextField campoDoacaoItemDoacao;
    private javax.swing.JTextField campoDoadorAlterarDoacao;
    private javax.swing.JTextField campoDoadorAlterarItemAcervo;
    private javax.swing.JTextField campoDoadorAlterarItemDoacao;
    private javax.swing.JTextField campoDoadorExcluirDoador;
    private javax.swing.JTextField campoDoadorItemDoacao;
    private javax.swing.JTextField campoEmailAlterarUsuario;
    private javax.swing.JTextField campoEmailAlterarUsuarioJD;
    private javax.swing.JTextField campoEmailCadastrarUsuario;
    private javax.swing.JTextField campoImagemExcluirImagem;
    private javax.swing.JTextField campoItemAcervoAlterarItemAcervo;
    private javax.swing.JTextField campoItemAcervoCadastrarImagem;
    private javax.swing.JTextField campoItemAcervoExcluirItemAcervo;
    private javax.swing.JTextField campoItemDoacao;
    private javax.swing.JTextField campoItemDoacaoAlterarItemDoacao;
    private javax.swing.JTextField campoItemDoacaoExcluirItemDoacao;
    private javax.swing.JTextField campoItemRepasse;
    private javax.swing.JTextField campoItemRepasseAlterarItemRepasse;
    private javax.swing.JTextField campoItemRepasseExcluirItemRepasse;
    private javax.swing.JTextField campoLink;
    private javax.swing.JTextField campoLinkAlterarImagem;
    private javax.swing.JTextField campoLocalizacaoAlterarContainer;
    private javax.swing.JTextField campoLocalizacaoCadastrarContainer;
    private javax.swing.JTextField campoNomeAlterarUsuario;
    private javax.swing.JTextField campoNomeColetor;
    private javax.swing.JTextField campoNomeColetorAlterarColetor;
    private javax.swing.JTextField campoNomeDestinacao;
    private javax.swing.JTextField campoNomeDoador;
    private javax.swing.JTextField campoNomeDoadorAlterarDoador;
    private javax.swing.JTextField campoNomeEventoOrigem;
    private javax.swing.JTextField campoNomeInterface;
    private javax.swing.JTextField campoNomeMarca;
    private javax.swing.JTextField campoNomeModelo;
    private javax.swing.JTextField campoNomeNovoUsuario;
    private javax.swing.JTextField campoNomeTecnologia;
    private javax.swing.JTextField campoNomeUsuarioAlterarUsuarioJD;
    private javax.swing.JPasswordField campoNovaSenhaAlterarUsuario;
    private javax.swing.JPasswordField campoNovaSenhaAlterarUsuarioJD;
    private javax.swing.JTextField campoQuantidadeAlterarItemDoacao;
    private javax.swing.JTextField campoQuantidadeAlterarItemRepasse;
    private javax.swing.JTextField campoQuantidadeItemDoacao;
    private javax.swing.JTextField campoQuantidadeItemRepasse;
    private javax.swing.JTextField campoRegistroAcademicoAlterarUsuario;
    private javax.swing.JTextField campoRegistroAcademicoAlterarUsuarioJD;
    private javax.swing.JTextField campoRegistroAcademicoCadastrarUsuario;
    private javax.swing.JTextField campoRepasse;
    private javax.swing.JTextField campoRepasseAlterarRepasse;
    private javax.swing.JTextField campoRepasseExcluirRepasse;
    private javax.swing.JTextField campoRepasseItemRepasse;
    private javax.swing.JPasswordField campoRepetirNovaSenhaAlterarUsuarioJD;
    private javax.swing.JPasswordField campoRepetirSenhaAlterarUsuario;
    private javax.swing.JPasswordField campoRepetirSenhaCadastrarUsuario;
    private javax.swing.JPasswordField campoSenhaAtualAlterarUsuario;
    private javax.swing.JPasswordField campoSenhaCadastrarUsuario;
    private javax.swing.JPasswordField campoSenhaLogin;
    private javax.swing.JTextField campoTipoColetor;
    private javax.swing.JTextField campoTipoContainer;
    private javax.swing.JTextField campoTipoItem;
    private javax.swing.JTextField campoUsuarioAlterarDoacao;
    private javax.swing.JTextField campoUsuarioAlterarItemAcervo;
    private javax.swing.JTextField campoUsuarioAlterarItemDoacao;
    private javax.swing.JTextField campoUsuarioAlterarItemRepasse;
    private javax.swing.JTextField campoUsuarioAlterarRepasse;
    private javax.swing.JTextField campoUsuarioCadastrarContainer;
    private javax.swing.JTextField campoUsuarioCadastrarImagem;
    private javax.swing.JTextField campoUsuarioDoacao;
    private javax.swing.JTextField campoUsuarioExcluirUsuario;
    private javax.swing.JTextField campoUsuarioItemAcervo;
    private javax.swing.JTextField campoUsuarioItemDoacao;
    private javax.swing.JTextField campoUsuarioItemRepasse;
    private javax.swing.JTextField campoUsuarioRepasse;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    private String nomeUsuario,senhaUsuario;
    private int codigoUsuario;
    private int progresso;
    private boolean logado=false,administrador;
    private final ConfigBanco cb=new ConfigBanco();
    private final String dbURL=cb.getDbURL();
    private final String dbUser=cb.getLogin();
    private final String dbPassword=cb.getPassword();
    
    private final ImageIcon iconeaux = new ImageIcon(Principal.class.getResource("imagens/acervo32.png"));
    private final Image icone=iconeaux.getImage();

  

    
}
