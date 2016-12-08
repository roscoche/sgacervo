package view;

import static com.lowagie.text.pdf.PdfFileSpecification.url;
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
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import javax.swing.table.DefaultTableModel;
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
        JDLogin.setIconImage(icone);
        JDLogin.setModal(true);

        JDLogin.pack();
        JDLogin.setLocationRelativeTo(null);
        atualizarCBUsuario();
        JDLogin.setVisible(true);

    }

    @SuppressWarnings("unchecked")

    private void atualizarCamposCadastrarDoacao() {

        java.util.Date data = new java.util.Date();
        Banco b = new Banco();
        int doacao = b.max("SELECT MAX(cod_doacao) from doacao;");
        b.fechar();
        doacao++;

    }

    private void atualizarCamposCadastrarDoador() {
        Banco b = new Banco();
        campoCodigoDoador.setText("" + (b.max("SELECT MAX(cod_doador) from doador;") + 1));
        b.fechar();
        campoNomeDoador.setText("");
    }

    private void atualizarCamposCadastrarEventoOrigem() {
        Banco b = new Banco();
        campoCodigoEventoOrigem.setText("" + (b.max("SELECT MAX(cod_evento_origem) from evento_origem;") + 1));
        b.fechar();
        campoNomeEventoOrigem.setText("");
    }

    private void atualizarCamposDoacao() {
        atualizarCamposCadastrarDoacao();
        atualizarCamposCadastrarDoador();
        atualizarCamposCadastrarEventoOrigem();
    }

    private void atualizarCamposCadastrarRepasse() {
        Banco b = new Banco();
        int max = b.max("SELECT MAX(cod_repasse) from repasse");
        b.fechar();
        max++;
        java.util.Date data = new java.util.Date();

    }

    private void atualizarCamposCadastrarDestinacao() {
        Banco b = new Banco();
        int max = b.max("SELECT MAX(cod_destinacao) from destinacao");
        b.fechar();
        max++;
        campoCodigoDestinacao.setText("" + max);
        campoNomeDestinacao.setText("");
    }

    private void atualizarCamposCadastrarColetor() {
        Banco b = new Banco();
        campoCodigoColetor.setText("" + (b.max("SELECT MAX(cod_coletor) from coletor;") + 1));
        b.fechar();
        campoNomeColetor.setText("");
    }

    private void atualizarCamposCadastrarTipoColetor() {
        Banco b = new Banco();
        campoCodigoTipoColetor.setText("" + (b.max("SELECT MAX(cod_tipo_coletor) from tipo_coletor;") + 1));
        b.fechar();
        campoTipoColetor.setText("");
    }

    private void atualizarCamposRepasse() {
        atualizarCamposCadastrarRepasse();
        atualizarCamposCadastrarDestinacao();
        atualizarCamposCadastrarColetor();
        atualizarCamposCadastrarTipoColetor();
    }

    private void atualizarCamposCadastrarItemAcervo() {
        Banco b = new Banco();
        int max = b.max("SELECT MAX(cod_item_acervo) from item_acervo");
        b.fechar();
        max++;
        java.util.Date data = new java.util.Date();
        campoDescricaoItemAcervo.setText("");
        campoAnoItemAcervo.setText("");
        CBFunciona.setSelected(false);
        campoCapacidadeItemAcervo.setText("");
        campoLink.setText("");
        LFotoAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png"))); // NOI18N
    }

    private void atualizarCamposAcervo() {
        atualizarCamposCadastrarItemAcervo();
        atualizarCamposCadastrarImagem();
        atualizarCamposCadastrarContainer();

    }

    private void atualizarCamposCadastrarUsuario() {
        Banco b = new Banco();
        campoCodigoNovoUsuario.setText("" + (b.max("Select MAX(cod_usuario) from usuario") + 1));
        b.fechar();
        campoNomeNovoUsuario.setText("");
        campoSenhaCadastrarUsuario.setText("");
        CBAdministrador.setSelected(false);

    }

    private void atualizarCamposUsuario() {
        atualizarCamposCadastrarUsuario();
    }

    private void atualizarCamposAlterarUsuario() {
        UsuarioDAO daou = new UsuarioDAO();
        Usuario u = daou.getByCod(codigoUsuario);
        daou.fechar();

        campoCodigoAlterarUsuario.setText("" + codigoUsuario);
        campoSenhaAtualAlterarUsuario.setText("");
        campoNovaSenhaAlterarUsuario.setText("");
        campoRepetirSenhaAlterarUsuario.setText("");
        campoNomeAlterarUsuario.setText(nomeUsuario);
        campoEmailAlterarUsuario.setText(u.getEmail());
        campoRegistroAcademicoAlterarUsuario.setText(u.getRegistro_academico());
        campoAdministradorAlterarUsuario.setSelected(administrador);

    }

    private void atualizarCamposAbaUsuario() {
        atualizarCamposAlterarUsuario();
    }

    private void atualizarCamposCadastrarTipoItem() {
        Banco b = new Banco();
        campoCodigoTipoItem.setText("" + (b.max("Select max(cod_tipo) from tipo_item;") + 1));
        b.fechar();
        campoTipoItem.setText("");
    }

    private void atualizarCamposCadastrarMarca() {
        Banco b = new Banco();
        campoCodigoMarca.setText("" + (b.max("Select max(cod_marca) from marca;") + 1));
        b.fechar();
        campoNomeMarca.setText("");
    }

    private void atualizarCamposCadastrarModelo() {
        Banco b = new Banco();
        campoCodigoModelo.setText("" + (b.max("Select max(cod_modelo) from modelo;") + 1));
        b.fechar();
        campoNomeModelo.setText("");
    }

    private void atualizarCamposCadastrarImagem() {
        Banco b = new Banco();
        b.fechar();
        campoItemAcervoCadastrarImagem.setText("");
        campoLink.setText("");
    }

    private void atualizarCamposCadastrarContainer() {
        Banco b = new Banco();
        b.fechar();
        campoLocalizacaoCadastrarContainer.setText("");

    }

    private void atualizarCamposCadastrarInterface() {
        Banco b = new Banco();
        campoCodigoInterface.setText("" + (b.max("Select max(cod_interface) from interface;") + 1));
        b.fechar();
        campoNomeInterface.setText("");
    }

    private void atualizarCamposCadastrarTecnologia() {
        Banco b = new Banco();
        campoCodigoTecnologia.setText("" + (b.max("Select max(cod_tecnologia) from tecnologia;") + 1));
        b.fechar();
        campoNomeTecnologia.setText("");
    }

    private void atualizarCamposCadastrarTipoContainer() {
        Banco b = new Banco();
        campoCodigoTipoContainer.setText("" + (b.max("select max(cod_tipo_container) from tipo_container;") + 1));
        b.fechar();
        campoTipoContainer.setText("");
    }

    private void atualizarCampos() {
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
    private void setTableColumnsWidths(JTable tabela,int[] percents){
        tabela.setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);
        
        int tmax=tabela.getWidth();
        int apercent=tmax/100;
        //System.out.println("tmax"+tmax);
        //System.out.println("apercent"+apercent);
        for(int i=0;i<percents.length;i++) tabela.getColumnModel().getColumn(i).setPreferredWidth(percents[i]*apercent);
    }
    private void atualizarTBDoacao() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoDoacao + FiltroDoacao;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TDoacao.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={10,30,30,20,10};
                setTableColumnsWidths(TDoacao,percents);
                numeroPaginaDoacao = 1;
                LDoacaoPagina.setText(numeroPaginaDoacao + "");
                numeroMaxPaginaDoacao = ((TDoacao.getRowCount() - 1) / Integer.parseInt(SPDoacaoItensPagina.getValue().toString())) + 1;
                LDoacaoTotalPaginas.setText(numeroMaxPaginaDoacao + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Doacao.");

            }
        } else {
            int limit = Integer.parseInt(SPDoacaoItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaDoacao - 1);
            PaginaDoacao = " limit " + limit + " offset " + offset + " ";
            statement = SelecaoDoacao + FiltroDoacao + PaginaDoacao;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Doacao.");

            }
            System.out.println(statement);
            TDoacao.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={10,30,30,20,10};
            setTableColumnsWidths(TDoacao,percents);
            LDoacaoPagina.setText(numeroPaginaDoacao + "");
        }

    }

    private void atualizarTBItemDoacao() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoItemDoacao + FiltroItemDoacao;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TItemDoacao.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={6,6,20,20,20,10,10,7};
                setTableColumnsWidths(TItemDoacao,percents);
                numeroPaginaItemDoacao = 1;
                LItemDoacaoPagina.setText(numeroPaginaItemDoacao + "");
                numeroMaxPaginaItemDoacao = ((TItemDoacao.getRowCount() - 1) / Integer.parseInt(SPItemDoacaoItensPagina.getValue().toString())) + 1;
                LItemDoacaoTotalPaginas.setText(numeroMaxPaginaItemDoacao + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Item Doacao.");

            }
        } else {
            int limit = Integer.parseInt(SPItemDoacaoItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaItemDoacao - 1);
            PaginaItemDoacao = " limit " + limit + " offset " + offset + " ";
            statement = SelecaoItemDoacao + FiltroItemDoacao + PaginaItemDoacao;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Item Doacao.");

            }
            TItemDoacao.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={6,6,20,20,20,10,10,7};
            setTableColumnsWidths(TItemDoacao,percents);
            LItemDoacaoPagina.setText(numeroPaginaItemDoacao + "");
        }

    }

    private void atualizarTBEstoque() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoEstoque + FiltroEstoque;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TEstoque.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={10,30,20,20,20};
                setTableColumnsWidths(TEstoque,percents);
                numeroPaginaEstoque = 1;
                LEstoquePagina.setText(numeroPaginaEstoque + "");
                numeroMaxPaginaEstoque = ((TEstoque.getRowCount() - 1) / Integer.parseInt(SPEstoqueItensPagina.getValue().toString())) + 1;
                LEstoqueTotalPaginas.setText(numeroMaxPaginaEstoque + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Estoque.");

            }
        } else {
            int limit = Integer.parseInt(SPEstoqueItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaEstoque - 1);
            PaginaEstoque = "limit " + limit + " offset " + offset;
            statement = SelecaoEstoque + FiltroEstoque + PaginaEstoque;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Estoque.");

            }
            TEstoque.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={10,30,20,20,20};
            setTableColumnsWidths(TEstoque,percents);
            LEstoquePagina.setText(numeroPaginaEstoque + "");
        }

    }

    private void atualizarTBImagem() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoImagem + FiltroImagem;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TImagem.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={10,10,25,25,30};
                setTableColumnsWidths(TImagem,percents);
                numeroPaginaImagem = 1;
                LImagemPagina.setText(numeroPaginaImagem + "");
                numeroMaxPaginaImagem = ((TImagem.getRowCount() - 1) / Integer.parseInt(SPImagemItensPagina.getValue().toString())) + 1;
                LImagemTotalPaginas.setText(numeroMaxPaginaImagem + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Imagem.");

            }
        } else {
            int limit = Integer.parseInt(SPImagemItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaImagem - 1);
            PaginaImagem = "limit " + limit + " offset " + offset;
            statement = SelecaoImagem + FiltroImagem + PaginaImagem;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Imagem.");

            }
            TImagem.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={10,10,25,25,30};
            setTableColumnsWidths(TImagem,percents);
            LImagemPagina.setText(numeroPaginaImagem + "");
        }

    }

    private void atualizarTBContainer() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoContainer + FiltroContainer;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TContainer.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={10,45,45};
                setTableColumnsWidths(TContainer,percents);
                numeroPaginaContainer = 1;
                LContainerPagina.setText(numeroPaginaContainer + "");
                numeroMaxPaginaContainer = ((TContainer.getRowCount() - 1) / Integer.parseInt(SPContainerItensPagina.getValue().toString())) + 1;
                LContainerTotalPaginas.setText(numeroMaxPaginaContainer + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Container.");

            }
        } else {
            int limit = Integer.parseInt(SPContainerItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaContainer - 1);
            PaginaContainer = "limit " + limit + " offset " + offset;
            statement = SelecaoContainer + FiltroContainer + PaginaContainer;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Container.");

            }
            TContainer.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={10,45,45};
            setTableColumnsWidths(TContainer,percents);
            LContainerPagina.setText(numeroPaginaContainer + "");
        }

    }

    private void atualizarTBDoador() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoDoador + FiltroDoador;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TDoador.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={15,75};
                setTableColumnsWidths(TDoador,percents);
                numeroPaginaDoador = 1;
                LDoadorPagina.setText(numeroPaginaDoador + "");
                numeroMaxPaginaDoador = ((TDoador.getRowCount() - 1) / Integer.parseInt(SPDoadorItensPagina.getValue().toString())) + 1;
                LDoadorTotalPaginas.setText(numeroMaxPaginaDoador + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Doador.");

            }
        } else {
            int limit = Integer.parseInt(SPDoadorItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaDoador - 1);
            PaginaDoador = "limit " + limit + " offset " + offset;
            statement = SelecaoDoador + FiltroDoador + PaginaDoador;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Doador.");

            }
            TDoador.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={15,75};
            setTableColumnsWidths(TDoador,percents);
            LDoadorPagina.setText(numeroPaginaDoador + "");
        }

    }

    private void atualizarTBRepasse() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoRepasse + FiltroRepasse;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TRepasse.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={10,20,20,20,20,10};
                setTableColumnsWidths(TRepasse,percents);
                numeroPaginaRepasse = 1;
                LRepassePagina.setText(numeroPaginaRepasse + "");
                numeroMaxPaginaRepasse = ((TRepasse.getRowCount() - 1) / Integer.parseInt(SPRepasseItensPagina.getValue().toString())) + 1;
                LRepasseTotalPaginas.setText(numeroMaxPaginaRepasse + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Repasse.");

            }
        } else {
            int limit = Integer.parseInt(SPRepasseItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaRepasse - 1);
            PaginaRepasse = "limit " + limit + " offset " + offset;
            statement = SelecaoRepasse + FiltroRepasse + PaginaRepasse;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Repasse.");

            }
            TRepasse.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={10,20,20,20,20,10};
            setTableColumnsWidths(TRepasse,percents);
            LRepassePagina.setText(numeroPaginaRepasse + "");
        }

    }

    private void atualizarTBItemRepasse() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoItemRepasse + FiltroItemRepasse;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TItemRepasse.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={5,5,15,20,20,20,5,10};
                setTableColumnsWidths(TItemRepasse,percents);
                numeroPaginaItemRepasse = 1;
                LItemRepassePagina.setText(numeroPaginaItemRepasse + "");
                numeroMaxPaginaItemRepasse = ((TItemRepasse.getRowCount() - 1) / Integer.parseInt(SPItemRepasseItensPagina.getValue().toString())) + 1;
                LItemRepasseTotalPaginas.setText(numeroMaxPaginaItemRepasse + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Item Repasse.");

            }
        } else {
            int limit = Integer.parseInt(SPItemRepasseItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaItemRepasse - 1);
            PaginaItemRepasse = "limit " + limit + " offset " + offset;
            statement = SelecaoItemRepasse + FiltroItemRepasse + PaginaItemRepasse;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Item Repasse.");

            }
            TItemRepasse.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={5,5,15,20,20,20,5,10};
            setTableColumnsWidths(TItemRepasse,percents);
            LItemRepassePagina.setText(numeroPaginaItemRepasse + "");
        }

    }

    private void atualizarTBColetor() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoColetor + FiltroColetor;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TColetor.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={20,40,40};
                setTableColumnsWidths(TColetor,percents);
                numeroPaginaColetor = 1;
                LColetorPagina.setText(numeroPaginaColetor + "");
                numeroMaxPaginaColetor = ((TColetor.getRowCount() - 1) / Integer.parseInt(SPColetorItensPagina.getValue().toString())) + 1;
                LColetorTotalPaginas.setText(numeroMaxPaginaColetor + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Coletor.");

            }
        } else {
            int limit = Integer.parseInt(SPColetorItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaColetor - 1);
            PaginaColetor = "limit " + limit + " offset " + offset;
            statement = SelecaoColetor + FiltroColetor + PaginaColetor;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Coletor.");

            }
            TColetor.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={20,40,40};
            setTableColumnsWidths(TColetor,percents);
            LColetorPagina.setText(numeroPaginaColetor + "");
        }

    }

    private void atualizarTBAcervo() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoAcervo + FiltroAcervo;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TAcervo.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={5,10,10,5,10,10,10,10,10,2,3,10,2,3};
                setTableColumnsWidths(TAcervo,percents);
                numeroPaginaAcervo = 1;
                LAcervoPagina.setText(numeroPaginaAcervo + "");
                numeroMaxPaginaAcervo = ((TAcervo.getRowCount() - 1) / Integer.parseInt(SPAcervoItensPagina.getValue().toString())) + 1;
                LAcervoTotalPaginas.setText(numeroMaxPaginaAcervo + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Acervo.");

            }
        } else {
            int limit = Integer.parseInt(SPAcervoItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaAcervo - 1);
            PaginaAcervo = "limit " + limit + " offset " + offset;
            statement = SelecaoAcervo + FiltroAcervo + PaginaAcervo;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Acervo.");

            }
            TAcervo.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={5,10,10,5,10,10,10,10,10,2,3,10,2,3};
            setTableColumnsWidths(TAcervo,percents);
            LAcervoPagina.setText(numeroPaginaAcervo + "");
        }

    }

    private void atualizarTBUsuario() {
        Connection con;
        ResultSet rs = null;
        PreparedStatement ps;
        String statement;

        if (achandoMax) {
            statement = SelecaoUsuarios + FiltroUsuarios;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
                TUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
                int[] percents={10,15,25,25,25};
                setTableColumnsWidths(TUsuarios,percents);
                numeroPaginaUsuarios = 1;
                LUsuariosPagina.setText(numeroPaginaUsuarios + "");
                numeroMaxPaginaUsuarios = ((TUsuarios.getRowCount() - 1) / Integer.parseInt(SPUsuariosItensPagina.getValue().toString())) + 1;
                LUsuariosTotalPaginas.setText(numeroMaxPaginaUsuarios + "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Usuarios.");

            }
        } else {
            int limit = Integer.parseInt(SPUsuariosItensPagina.getValue().toString());
            int offset = limit * (numeroPaginaUsuarios - 1);
            PaginaUsuarios = "limit " + limit + " offset " + offset;
            statement = SelecaoUsuarios + FiltroUsuarios + PaginaUsuarios;
            try {
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                ps = con.prepareCall(statement);
                rs = ps.executeQuery();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro de consulta para Usuarios.");

            }
            TUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
            int[] percents={10,15,25,25,25};
            setTableColumnsWidths(TUsuarios,percents);
            LUsuariosPagina.setText(numeroPaginaUsuarios + "");
        }

    }

    private void atualizarTB() {
        //Atualizar Tabelas Relativas à Doação
        atualizarTBDoacao();
        atualizarTBItemDoacao();
        atualizarTBEstoque();
        atualizarTBDoador();

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

    private void atualizarCBDoador() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from doador order by nome_doador ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_doador"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Doador.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBDoadorItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBDoadorDoacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBDoacaoDoador.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBItemDoacaoDoador.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBDoadorDoador.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBAcervoDoador.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBEventoOrigem() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from evento_origem ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_evento_origem"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Evento Origem.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBEventoOrigem.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBEventoOrigemAlterarDoacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBDoacaoEvento.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBItemDoacaoEvento.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBDestinacao() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from destinacao order by nome_destinacao ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_destinacao"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Destinacao.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBDestinacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBDestinacaoAlterarRepasse.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBRepasseDestinacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBItemRepasseDestinacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBColetor() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from coletor order by nome_coletor ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_coletor"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Coletor.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBRepasseColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBItemRepasseColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBColetorColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBTipoColetor() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from tipo_coletor order by nome_tipo_coletor ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_tipo_coletor"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tipo Coletor.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBTipoColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBTipoColetorAlterarColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBRepasseTipoColetor.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBColetorTipo.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBTipoItem() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from tipo_item order by nome_tipo ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_tipo"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tipo Item.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);

        CBTipoItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBTipoAdicionarItemLista.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBTipoAlterarItemDoacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBTipoAlterarItemRepasse.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBTipoAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBEstoqueTipo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBItemRepasseTipo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBAcervoTipo.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBMarca() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from marca order by nome_marca ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_marca"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Marca.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBMarca.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBMarcaAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBAcervoMarca.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBImagemMarca.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBModelo() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from modelo order by nome_modelo ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_modelo"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Modelo.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBModelo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBModeloAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBAcervoModelo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBImagemModelo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }

    private void atualizarCBInterface() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from interface order by nome_interface ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_interface"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Interface.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBInterface.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBInterfaceAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBAcervoInterface.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }

    private void atualizarCBTecnologia() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from tecnologia order by nome_tecnologia ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_tecnologia"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tecnologia.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBTecnologia.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBTecnologiaAlterarItemAcervo.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBAcervoTecnologia.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }

    private void atualizarCBTipoContainer() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from tipo_container order by nome_tipo_container ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_tipo_container"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Tipo Container.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);

        CBTipoContainerCadastrarContainer.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBTipoContainerAlterarContainer.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBContainerTipo.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBLocalizacaoContainer() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from container order by localizacao_container ";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("localizacao_container"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Localização Container.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);

        CBContainerLocalizacao.setModel(new javax.swing.DefaultComboBoxModel(tipos));

    }

    private void atualizarCBUsuario() {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String statement = "SELECT * from usuario order by nome_usuario";
        ArrayList list = new ArrayList();
        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            ps = con.prepareCall(statement);
            rs = ps.executeQuery();
            con.close();

            while (rs.next()) {
                list.add(rs.getString("nome_usuario"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de consulta para ComboBox Usuario.");
        }
        Object[] objectList = list.toArray();
        String[] tipos = Arrays.copyOf(objectList, objectList.length, String[].class);
        CBUsuarioLogin.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBDoacaoUsuario.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBItemDoacaoUsuario.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBRepasseUsuario.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBItemRepasseUsuario.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBAcervoUsuario.setModel(new javax.swing.DefaultComboBoxModel(tipos));
        CBUsuarioNome.setModel(new javax.swing.DefaultComboBoxModel(tipos));
    }

    private void atualizarCB() {
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
        atualizarCBLocalizacaoContainer();
        //Atualizar ComboBox relativos à Usuário
        atualizarCBUsuario();
    }

    private void cancelarLogin() {
        if (!logado) {
            this.setVisible(false);
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
        BEsqueciSenha = new javax.swing.JButton();
        CBUsuarioLogin = new javax.swing.JComboBox();
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
        JDAdicionarItemLista = new javax.swing.JDialog();
        LTipo1 = new javax.swing.JLabel();
        LPO12 = new javax.swing.JLabel();
        BAdicionarItemLista = new javax.swing.JButton();
        CBTipoAdicionarItemLista = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        SPQuantidadeItemLista = new javax.swing.JSpinner();
        BNovoTipoItemDoacao = new javax.swing.JButton();
        JDFiltrarDoacao = new javax.swing.JDialog();
        SPDoacaoMin = new javax.swing.JSpinner();
        CheckDoacaoCodigo = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        SPDoacaoMax = new javax.swing.JSpinner();
        CheckDoacaoUsuario = new javax.swing.JCheckBox();
        CBDoacaoUsuario = new javax.swing.JComboBox<>();
        CheckDoacaoDoador = new javax.swing.JCheckBox();
        CBDoacaoDoador = new javax.swing.JComboBox<>();
        CheckDoacaoEvento = new javax.swing.JCheckBox();
        CBDoacaoEvento = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        CheckDoacaoData = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        BDoacaoFiltrar = new javax.swing.JButton();
        JDCDoacaoMin = new com.toedter.calendar.JDateChooser();
        JDCDoacaoMax = new com.toedter.calendar.JDateChooser();
        jLabel51 = new javax.swing.JLabel();
        JDFiltrarItemDoacao = new javax.swing.JDialog();
        SPItemDoacaoDoacaoMin = new javax.swing.JSpinner();
        CheckItemDoacaoDoacaoCodigo = new javax.swing.JCheckBox();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        SPItemDoacaoDoacaoMax = new javax.swing.JSpinner();
        CheckItemDoacaoUsuario = new javax.swing.JCheckBox();
        CBItemDoacaoUsuario = new javax.swing.JComboBox<>();
        CheckItemDoacaoDoador = new javax.swing.JCheckBox();
        CBItemDoacaoDoador = new javax.swing.JComboBox<>();
        CheckItemDoacaoEvento = new javax.swing.JCheckBox();
        CBItemDoacaoEvento = new javax.swing.JComboBox<>();
        jLabel54 = new javax.swing.JLabel();
        CheckItemDoacaoData = new javax.swing.JCheckBox();
        jLabel55 = new javax.swing.JLabel();
        BItemDoacaoFiltrar = new javax.swing.JButton();
        JDCItemDoacaoMin = new com.toedter.calendar.JDateChooser();
        JDCItemDoacaoMax = new com.toedter.calendar.JDateChooser();
        jLabel56 = new javax.swing.JLabel();
        CheckItemDoacaoCodigo = new javax.swing.JCheckBox();
        jLabel57 = new javax.swing.JLabel();
        SPItemDoacaoMin = new javax.swing.JSpinner();
        jLabel58 = new javax.swing.JLabel();
        SPItemDoacaoMax = new javax.swing.JSpinner();
        JDFiltrarEstoque = new javax.swing.JDialog();
        jLabel59 = new javax.swing.JLabel();
        BEstoqueFiltrar = new javax.swing.JButton();
        CheckEstoqueTipo = new javax.swing.JCheckBox();
        CBEstoqueTipo = new javax.swing.JComboBox<>();
        CheckEstoqueDoacao = new javax.swing.JCheckBox();
        CheckEstoqueRepasse = new javax.swing.JCheckBox();
        CheckEstoqueSaldo = new javax.swing.JCheckBox();
        SPEstoqueDoacaoMax = new javax.swing.JSpinner();
        jLabel66 = new javax.swing.JLabel();
        SPEstoqueDoacaoMin = new javax.swing.JSpinner();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        SPEstoqueRepasseMin = new javax.swing.JSpinner();
        jLabel67 = new javax.swing.JLabel();
        SPEstoqueRepasseMax = new javax.swing.JSpinner();
        jLabel62 = new javax.swing.JLabel();
        SPEstoqueSaldoMin = new javax.swing.JSpinner();
        jLabel68 = new javax.swing.JLabel();
        SPEstoqueSaldoMax = new javax.swing.JSpinner();
        JDFiltrarDoador = new javax.swing.JDialog();
        SPDoadorCodigoMin = new javax.swing.JSpinner();
        CheckDoadorCodigo = new javax.swing.JCheckBox();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        SPDoadorCodigoMax = new javax.swing.JSpinner();
        CheckDoadorDoador = new javax.swing.JCheckBox();
        CBDoadorDoador = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        BDoadorFiltrar = new javax.swing.JButton();
        JDFiltrarRepasse = new javax.swing.JDialog();
        SPRepasseMin = new javax.swing.JSpinner();
        CheckRepasseCodigo = new javax.swing.JCheckBox();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        SPRepasseMax = new javax.swing.JSpinner();
        CheckRepasseUsuario = new javax.swing.JCheckBox();
        CBRepasseUsuario = new javax.swing.JComboBox<>();
        CheckRepasseColetor = new javax.swing.JCheckBox();
        CBRepasseColetor = new javax.swing.JComboBox<>();
        CheckRepasseTipoColetor = new javax.swing.JCheckBox();
        CBRepasseTipoColetor = new javax.swing.JComboBox<>();
        jLabel71 = new javax.swing.JLabel();
        CheckRepasseData = new javax.swing.JCheckBox();
        jLabel72 = new javax.swing.JLabel();
        BRepasseFiltrar = new javax.swing.JButton();
        JDCRepasseMin = new com.toedter.calendar.JDateChooser();
        JDCRepasseMax = new com.toedter.calendar.JDateChooser();
        jLabel73 = new javax.swing.JLabel();
        CheckRepasseDestinacao = new javax.swing.JCheckBox();
        CBRepasseDestinacao = new javax.swing.JComboBox<>();
        JDFiltrarItemRepasse = new javax.swing.JDialog();
        SPItemRepasseRepasseMin = new javax.swing.JSpinner();
        CheckItemRepasseRepasse = new javax.swing.JCheckBox();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        SPItemRepasseRepasseMax = new javax.swing.JSpinner();
        CheckItemRepasseUsuario = new javax.swing.JCheckBox();
        CBItemRepasseUsuario = new javax.swing.JComboBox<>();
        CheckItemRepasseColetor = new javax.swing.JCheckBox();
        CBItemRepasseColetor = new javax.swing.JComboBox<>();
        CheckItemRepasseTipo = new javax.swing.JCheckBox();
        CBItemRepasseTipo = new javax.swing.JComboBox<>();
        jLabel76 = new javax.swing.JLabel();
        CheckItemRepasseData = new javax.swing.JCheckBox();
        jLabel77 = new javax.swing.JLabel();
        BItemRepasseFiltrar = new javax.swing.JButton();
        JDCItemRepasseMin = new com.toedter.calendar.JDateChooser();
        JDCItemRepasseMax = new com.toedter.calendar.JDateChooser();
        jLabel78 = new javax.swing.JLabel();
        CheckItemRepasseDestinacao = new javax.swing.JCheckBox();
        CBItemRepasseDestinacao = new javax.swing.JComboBox<>();
        CheckItemRepasseCodigo = new javax.swing.JCheckBox();
        SPItemRepasseMin = new javax.swing.JSpinner();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        SPItemRepasseMax = new javax.swing.JSpinner();
        CheckItemRepasseQuantidade = new javax.swing.JCheckBox();
        jLabel81 = new javax.swing.JLabel();
        SPItemRepasseQuantidadeMin = new javax.swing.JSpinner();
        jLabel82 = new javax.swing.JLabel();
        SPItemRepasseQuantidadeMax = new javax.swing.JSpinner();
        JDFiltrarColetor = new javax.swing.JDialog();
        SPColetorCodigoMin = new javax.swing.JSpinner();
        CheckColetorCodigo = new javax.swing.JCheckBox();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        SPColetorCodigoMax = new javax.swing.JSpinner();
        CheckColetorColetor = new javax.swing.JCheckBox();
        CBColetorColetor = new javax.swing.JComboBox<>();
        jLabel85 = new javax.swing.JLabel();
        BColetorFiltrar = new javax.swing.JButton();
        CheckColetorTipo = new javax.swing.JCheckBox();
        CBColetorTipo = new javax.swing.JComboBox<>();
        JDFiltrarAcervo = new javax.swing.JDialog();
        jLabel86 = new javax.swing.JLabel();
        CheckAcervoCodigo = new javax.swing.JCheckBox();
        CheckAcervoUsuario = new javax.swing.JCheckBox();
        CheckAcervoDoador = new javax.swing.JCheckBox();
        CheckAcervoData = new javax.swing.JCheckBox();
        CheckAcervoTipo = new javax.swing.JCheckBox();
        CheckAcervoMarca = new javax.swing.JCheckBox();
        CheckAcervoModelo = new javax.swing.JCheckBox();
        CheckAcervoInterface = new javax.swing.JCheckBox();
        CheckAcervoTecnologia = new javax.swing.JCheckBox();
        CheckAcervoCapacidade = new javax.swing.JCheckBox();
        CheckAcervoAno = new javax.swing.JCheckBox();
        CheckAcervoFuncionamento = new javax.swing.JCheckBox();
        CheckAcervoContainer = new javax.swing.JCheckBox();
        jLabel87 = new javax.swing.JLabel();
        SPAcervoCodigoMax = new javax.swing.JSpinner();
        SPAcervoCodigoMin = new javax.swing.JSpinner();
        jLabel88 = new javax.swing.JLabel();
        CBAcervoUsuario = new javax.swing.JComboBox<>();
        CBAcervoDoador = new javax.swing.JComboBox<>();
        JDCAcervoDataMax = new com.toedter.calendar.JDateChooser();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        JDCAcervoDataMin = new com.toedter.calendar.JDateChooser();
        CBAcervoTipo = new javax.swing.JComboBox<>();
        CBAcervoMarca = new javax.swing.JComboBox<>();
        CBAcervoModelo = new javax.swing.JComboBox<>();
        CBAcervoInterface = new javax.swing.JComboBox<>();
        CBAcervoTecnologia = new javax.swing.JComboBox<>();
        jLabel91 = new javax.swing.JLabel();
        SPAcervoCapacidadeMin = new javax.swing.JSpinner();
        jLabel92 = new javax.swing.JLabel();
        SPAcervoCapacidadeMax = new javax.swing.JSpinner();
        jLabel93 = new javax.swing.JLabel();
        SPAcervoAnoMin = new javax.swing.JSpinner();
        jLabel94 = new javax.swing.JLabel();
        SPAcervoAnoMax = new javax.swing.JSpinner();
        CheckAcervoFunciona = new javax.swing.JCheckBox();
        jLabel95 = new javax.swing.JLabel();
        SPAcervoContainerMin = new javax.swing.JSpinner();
        jLabel96 = new javax.swing.JLabel();
        SPAcervoContainerMax = new javax.swing.JSpinner();
        BAcervoFiltrar = new javax.swing.JButton();
        JDFiltrarImagem = new javax.swing.JDialog();
        jLabel97 = new javax.swing.JLabel();
        CheckImagemCodigo = new javax.swing.JCheckBox();
        CheckImagemMarca = new javax.swing.JCheckBox();
        CheckImagemModelo = new javax.swing.JCheckBox();
        jLabel98 = new javax.swing.JLabel();
        SPImagemCodigoMax = new javax.swing.JSpinner();
        SPImagemCodigoMin = new javax.swing.JSpinner();
        jLabel99 = new javax.swing.JLabel();
        CBImagemMarca = new javax.swing.JComboBox<>();
        CBImagemModelo = new javax.swing.JComboBox<>();
        BImagemFiltrar = new javax.swing.JButton();
        CheckImagemItem = new javax.swing.JCheckBox();
        jLabel108 = new javax.swing.JLabel();
        SPImagemItemMin = new javax.swing.JSpinner();
        jLabel109 = new javax.swing.JLabel();
        SPImagemItemMax = new javax.swing.JSpinner();
        JDFiltrarContainer = new javax.swing.JDialog();
        jLabel100 = new javax.swing.JLabel();
        CheckContainerCodigo = new javax.swing.JCheckBox();
        CheckContainerLocalizacao = new javax.swing.JCheckBox();
        CheckContainerTipo = new javax.swing.JCheckBox();
        jLabel101 = new javax.swing.JLabel();
        SPContainerCodigoMax = new javax.swing.JSpinner();
        SPContainerCodigoMin = new javax.swing.JSpinner();
        jLabel102 = new javax.swing.JLabel();
        CBContainerLocalizacao = new javax.swing.JComboBox<>();
        CBContainerTipo = new javax.swing.JComboBox<>();
        BContainerFiltrar = new javax.swing.JButton();
        JDFiltrarUsuario = new javax.swing.JDialog();
        jLabel103 = new javax.swing.JLabel();
        CheckUsuarioCodigo = new javax.swing.JCheckBox();
        CheckUsuarioNome = new javax.swing.JCheckBox();
        CheckUsuarioPermissao = new javax.swing.JCheckBox();
        jLabel104 = new javax.swing.JLabel();
        SPUsuarioCodigoMax = new javax.swing.JSpinner();
        SPUsuarioCodigoMin = new javax.swing.JSpinner();
        jLabel105 = new javax.swing.JLabel();
        CBUsuarioNome = new javax.swing.JComboBox<>();
        BContainerFiltrar1 = new javax.swing.JButton();
        CheckUsuarioAdministrador = new javax.swing.JCheckBox();
        Principal = new javax.swing.JTabbedPane();
        Doacoes = new javax.swing.JTabbedPane();
        MenuDoacoes = new javax.swing.JPanel();
        LBemVindoDoacoes = new javax.swing.JLabel();
        BAbrirCadastrarDoacao = new javax.swing.JButton();
        BAbrirDoacoes = new javax.swing.JButton();
        BAbrirEstoque = new javax.swing.JButton();
        BAbrirDoadores = new javax.swing.JButton();
        BAbrirItemDoacao = new javax.swing.JButton();
        BMenuCadastrarDoador = new javax.swing.JButton();
        CadastrarDoacao = new javax.swing.JPanel();
        LOrigem = new javax.swing.JLabel();
        Ldoador = new javax.swing.JLabel();
        LCamposObrigatorios = new javax.swing.JLabel();
        CBDoadorDoacao = new javax.swing.JComboBox();
        BCadastrarDoacao = new javax.swing.JButton();
        BNovoDoador = new javax.swing.JButton();
        CBEventoOrigem = new javax.swing.JComboBox();
        BNovoEventoOrigem = new javax.swing.JButton();
        LInfoDoacao = new javax.swing.JLabel();
        LItemsDoacao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TAdicionarItemDoacao = new javax.swing.JTable();
        BAdicionarItemDoacao = new javax.swing.JButton();
        BRemoverItemDoacao = new javax.swing.JButton();
        PainelDoacoes = new javax.swing.JPanel();
        RelatorioDoacoes = new javax.swing.JScrollPane();
        TDoacao = new javax.swing.JTable();
        BExcluirDoacao = new javax.swing.JButton();
        BEditarDoacao = new javax.swing.JButton();
        BRelatorioDoacao = new javax.swing.JButton();
        BFiltrarDoacao = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        SPDoacaoItensPagina = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        LDoacaoPagina = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LDoacaoTotalPaginas = new javax.swing.JLabel();
        BDoacaoProxPagina = new javax.swing.JButton();
        BDoacaoPaginaAnterior = new javax.swing.JButton();
        PainelItemDoacao = new javax.swing.JPanel();
        RelatorioItemDoacao = new javax.swing.JScrollPane();
        TItemDoacao = new javax.swing.JTable();
        BEditarItemDoacao = new javax.swing.JButton();
        BExcluirItemDoacao = new javax.swing.JButton();
        BRelatorioItemDoacao = new javax.swing.JButton();
        BRelatorioItemDoacao1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        SPItemDoacaoItensPagina = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        LItemDoacaoPagina = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        LItemDoacaoTotalPaginas = new javax.swing.JLabel();
        BItemDoacaoPaginaAnterior = new javax.swing.JButton();
        BItemDoacaoProxPagina = new javax.swing.JButton();
        PainelEstoque = new javax.swing.JPanel();
        RelatorioEstoque = new javax.swing.JScrollPane();
        TEstoque = new javax.swing.JTable();
        BRelatorioEstoque = new javax.swing.JButton();
        BFiltrarEstoque = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        SPEstoqueItensPagina = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        LEstoquePagina = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        LEstoqueTotalPaginas = new javax.swing.JLabel();
        BEstoquePaginaAnterior = new javax.swing.JButton();
        BEstoqueProxPagina = new javax.swing.JButton();
        PainelDoadores = new javax.swing.JPanel();
        ExibirDoadores = new javax.swing.JScrollPane();
        TDoador = new javax.swing.JTable();
        BExcluirDoador = new javax.swing.JButton();
        BEditarDoador = new javax.swing.JButton();
        BRelatorioDoadores = new javax.swing.JButton();
        BRelatorioDoadores1 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        SPDoadorItensPagina = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        LDoadorPagina = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        LDoadorTotalPaginas = new javax.swing.JLabel();
        BDoadorPaginaAnterior = new javax.swing.JButton();
        BDoadorProxPagina = new javax.swing.JButton();
        Repasses = new javax.swing.JTabbedPane();
        MenuRepasse = new javax.swing.JPanel();
        LBemVindoDoacoes1 = new javax.swing.JLabel();
        BAbrirCadastrarDoacao1 = new javax.swing.JButton();
        BAbrirRepasses = new javax.swing.JButton();
        BExibirColetores = new javax.swing.JButton();
        BAbrirItemRepasse = new javax.swing.JButton();
        BMenuCadastrarColetor = new javax.swing.JButton();
        CadastrarRepasse = new javax.swing.JPanel();
        LDescricaoRepasse = new javax.swing.JLabel();
        LColetorRepasse = new javax.swing.JLabel();
        BCadastrarRepasse = new javax.swing.JButton();
        CBColetor = new javax.swing.JComboBox();
        BNovoColetor = new javax.swing.JButton();
        CBDestinacao = new javax.swing.JComboBox();
        BNovoDestinacao = new javax.swing.JButton();
        LInfoDoacao1 = new javax.swing.JLabel();
        LCamposObrigatorios1 = new javax.swing.JLabel();
        LItemsDoacao1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TAdicionarItemRepasse = new javax.swing.JTable();
        BAdicionarItemRepasse = new javax.swing.JButton();
        BRemoverItemRepasse = new javax.swing.JButton();
        PainelRepasse = new javax.swing.JPanel();
        RelatorioRepasse = new javax.swing.JScrollPane();
        TRepasse = new javax.swing.JTable();
        BEditarRepasse = new javax.swing.JButton();
        BExcluirRepasse = new javax.swing.JButton();
        BRelatorioRepasse = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        SPRepasseItensPagina = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        LRepassePagina = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        LRepasseTotalPaginas = new javax.swing.JLabel();
        BRepassePaginaAnterior = new javax.swing.JButton();
        BRepasseProxPagina = new javax.swing.JButton();
        BFiltrarRepasse = new javax.swing.JButton();
        PainelItemRepasse = new javax.swing.JPanel();
        RelatorioItemRepasse = new javax.swing.JScrollPane();
        TItemRepasse = new javax.swing.JTable();
        BEditarItemRepasse = new javax.swing.JButton();
        BExcluirItemRepasse = new javax.swing.JButton();
        BRelatorioItemRepasse = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        SPItemRepasseItensPagina = new javax.swing.JSpinner();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        LItemRepassePagina = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        LItemRepasseTotalPaginas = new javax.swing.JLabel();
        BItemRepassePaginaAnterior = new javax.swing.JButton();
        BItemRepasseProxPagina = new javax.swing.JButton();
        BFiltrarItemRepasse = new javax.swing.JButton();
        PainelColetores = new javax.swing.JPanel();
        ExibirColetores = new javax.swing.JScrollPane();
        TColetor = new javax.swing.JTable();
        BEditarColetor = new javax.swing.JButton();
        BExcluirColetor = new javax.swing.JButton();
        BRelatorioColetor = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        SPColetorItensPagina = new javax.swing.JSpinner();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        LColetorPagina = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        LColetorTotalPaginas = new javax.swing.JLabel();
        BColetorPaginaAnterior = new javax.swing.JButton();
        BColetorProxPagina = new javax.swing.JButton();
        BFiltrarColetor = new javax.swing.JButton();
        Acervo = new javax.swing.JTabbedPane();
        MenuAcervo = new javax.swing.JPanel();
        LBemVindoDoacoes2 = new javax.swing.JLabel();
        BMenuCadastrarItemAcervo = new javax.swing.JButton();
        BMenuCadastrarImagem = new javax.swing.JButton();
        BExibirAcervo = new javax.swing.JButton();
        BExibirImagens = new javax.swing.JButton();
        BMenuCadastrarContainer = new javax.swing.JButton();
        BExibirContainer = new javax.swing.JButton();
        CadastrarItemAcervo = new javax.swing.JPanel();
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
        LInfoDoacao2 = new javax.swing.JLabel();
        CadastrarImagem = new javax.swing.JPanel();
        LItemAcervoCadastrarImagem = new javax.swing.JLabel();
        LLinkCadastrarImagem = new javax.swing.JLabel();
        campoItemAcervoCadastrarImagem = new javax.swing.JTextField();
        campoLink = new javax.swing.JTextField();
        BCadastrarImagem = new javax.swing.JButton();
        BCheckLink = new javax.swing.JButton();
        LFotoAcervo = new javax.swing.JLabel();
        LCamposCadastrarImagem = new javax.swing.JLabel();
        LInfoDoacao3 = new javax.swing.JLabel();
        CadastrarContainer = new javax.swing.JPanel();
        LLocalizacaoCadastrarContainer = new javax.swing.JLabel();
        LTipoContainerCadastrarContainer = new javax.swing.JLabel();
        LCamposCadastrarContainer = new javax.swing.JLabel();
        campoLocalizacaoCadastrarContainer = new javax.swing.JTextField();
        CBTipoContainerCadastrarContainer = new javax.swing.JComboBox();
        BCadastrarContainer = new javax.swing.JButton();
        BNovoTipoContainer = new javax.swing.JButton();
        LInfoDoacao4 = new javax.swing.JLabel();
        PainelAcervo = new javax.swing.JPanel();
        RelatorioAcervo = new javax.swing.JScrollPane();
        TAcervo = new javax.swing.JTable();
        BEditarItemAcervo = new javax.swing.JButton();
        BExcluirItemAcervo = new javax.swing.JButton();
        BRelatorioAcervo = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        SPAcervoItensPagina = new javax.swing.JSpinner();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        LAcervoPagina = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        LAcervoTotalPaginas = new javax.swing.JLabel();
        BAcervoPaginaAnterior = new javax.swing.JButton();
        BAcervoProxPagina = new javax.swing.JButton();
        BFiltrarAcervo = new javax.swing.JButton();
        Imagens = new javax.swing.JPanel();
        SPImagem = new javax.swing.JScrollPane();
        TImagem = new javax.swing.JTable();
        BEditarImagem = new javax.swing.JButton();
        BExcluirImagem = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        SPImagemItensPagina = new javax.swing.JSpinner();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        LImagemPagina = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        LImagemTotalPaginas = new javax.swing.JLabel();
        BImagemPaginaAnterior = new javax.swing.JButton();
        BImagemProxPagina = new javax.swing.JButton();
        BFiltrarImagem = new javax.swing.JButton();
        Container = new javax.swing.JPanel();
        SPContainer = new javax.swing.JScrollPane();
        TContainer = new javax.swing.JTable();
        BEditarContainer = new javax.swing.JButton();
        BExcluirContainer = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        SPContainerItensPagina = new javax.swing.JSpinner();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        LContainerPagina = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        LContainerTotalPaginas = new javax.swing.JLabel();
        BContainerPaginaAnterior = new javax.swing.JButton();
        BContainerProxPagina = new javax.swing.JButton();
        BFiltrarContainer = new javax.swing.JButton();
        Usuarios = new javax.swing.JTabbedPane();
        MenuUsuarios = new javax.swing.JPanel();
        LBemVindoDoacoes3 = new javax.swing.JLabel();
        BAbrirCadastrarUsuario = new javax.swing.JButton();
        BExibirUsuarios = new javax.swing.JButton();
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
        TUsuarios = new javax.swing.JTable();
        BEditarUsuario = new javax.swing.JButton();
        BExcluirUsuario = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        SPUsuariosItensPagina = new javax.swing.JSpinner();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        LUsuariosPagina = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        LUsuariosTotalPaginas = new javax.swing.JLabel();
        BUsuariosPaginaAnterior = new javax.swing.JButton();
        BUsuariosProxPagina = new javax.swing.JButton();
        BFiltrarUsuario = new javax.swing.JButton();
        AbaDoUsuario = new javax.swing.JTabbedPane();
        MenuAbaUsuario = new javax.swing.JPanel();
        LBemVindoDoacoes4 = new javax.swing.JLabel();
        BAbrirEditarInformacoes = new javax.swing.JButton();
        BAbrirDeslogar = new javax.swing.JButton();
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
        BarraMenu = new javax.swing.JMenuBar();
        MenuAjuda = new javax.swing.JMenu();
        AbrirManual = new javax.swing.JMenuItem();
        AbrirManualEspecifico = new javax.swing.JMenuItem();
        MenuOpcoes = new javax.swing.JMenu();
        AbrirCodigoFonte = new javax.swing.JMenuItem();

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

        campoSenhaLogin.setText("admin");
        campoSenhaLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoSenhaLoginFocusGained(evt);
            }
        });
        campoSenhaLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaLoginActionPerformed(evt);
            }
        });

        BLogar.setText("Entrar");
        BLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLogarActionPerformed(evt);
            }
        });

        BEsqueciSenha.setText("Esqueci a senha");
        BEsqueciSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEsqueciSenhaActionPerformed(evt);
            }
        });

        CBUsuarioLogin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Administrador" }));

        javax.swing.GroupLayout JDLoginLayout = new javax.swing.GroupLayout(JDLogin.getContentPane());
        JDLogin.getContentPane().setLayout(JDLoginLayout);
        JDLoginLayout.setHorizontalGroup(
            JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(JDLoginLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LSenhaLogin)
                    .addComponent(LUsuárioLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(BLogar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(campoSenhaLogin, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BEsqueciSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(CBUsuarioLogin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48))
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
                .addComponent(BEsqueciSenha)
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

        JDAdicionarItemLista.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDAdicionarItemLista.setTitle("SGACERVO - Adicionar Item Lista");
        JDAdicionarItemLista.setMinimumSize(new java.awt.Dimension(306, 143));
        JDAdicionarItemLista.setResizable(false);

        LTipo1.setText("* Quantidade:");

        LPO12.setText("* Campos de preenchimento obrigatório.");

        BAdicionarItemLista.setText("Cadastrar");
        BAdicionarItemLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAdicionarItemListaActionPerformed(evt);
            }
        });

        CBTipoAdicionarItemLista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("* Tipo do Item:");

        SPQuantidadeItemLista.setModel(new javax.swing.SpinnerNumberModel(1, 1, 500, 1));

        BNovoTipoItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoItemDoacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDAdicionarItemListaLayout = new javax.swing.GroupLayout(JDAdicionarItemLista.getContentPane());
        JDAdicionarItemLista.getContentPane().setLayout(JDAdicionarItemListaLayout);
        JDAdicionarItemListaLayout.setHorizontalGroup(
            JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAdicionarItemListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDAdicionarItemListaLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LTipo1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDAdicionarItemListaLayout.createSequentialGroup()
                                .addComponent(CBTipoAdicionarItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BNovoTipoItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(SPQuantidadeItemLista, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BAdicionarItemLista, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(LPO12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JDAdicionarItemListaLayout.setVerticalGroup(
            JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDAdicionarItemListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BNovoTipoItemDoacao, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CBTipoAdicionarItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDAdicionarItemListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTipo1)
                    .addComponent(SPQuantidadeItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAdicionarItemLista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPO12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDFiltrarDoacao.setTitle("Filtrar Doação");

        SPDoacaoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckDoacaoCodigo.setText("Código de Doação");
        CheckDoacaoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckDoacaoCodigoActionPerformed(evt);
            }
        });

        jLabel3.setText("Filtrar por:");

        jLabel4.setText("Max:");

        SPDoacaoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckDoacaoUsuario.setText("Usuário");

        CBDoacaoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckDoacaoDoador.setText("Doador");

        CBDoacaoDoador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckDoacaoEvento.setText("Evento de Origem");

        CBDoacaoEvento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Min:");

        CheckDoacaoData.setText("Intervalo de Data");

        jLabel6.setText("até");

        BDoacaoFiltrar.setText("Filtrar Doacao");
        BDoacaoFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDoacaoFiltrarActionPerformed(evt);
            }
        });

        jLabel51.setText("de");

        javax.swing.GroupLayout JDFiltrarDoacaoLayout = new javax.swing.GroupLayout(JDFiltrarDoacao.getContentPane());
        JDFiltrarDoacao.getContentPane().setLayout(JDFiltrarDoacaoLayout);
        JDFiltrarDoacaoLayout.setHorizontalGroup(
            JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BDoacaoFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                        .addComponent(CheckDoacaoCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                        .addComponent(CheckDoacaoDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBDoacaoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                        .addComponent(CheckDoacaoUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBDoacaoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarDoacaoLayout.createSequentialGroup()
                        .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckDoacaoEvento)
                            .addComponent(CheckDoacaoData))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBDoacaoEvento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarDoacaoLayout.createSequentialGroup()
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JDCDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarDoacaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JDFiltrarDoacaoLayout.setVerticalGroup(
            JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                        .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JDFiltrarDoacaoLayout.createSequentialGroup()
                                .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckDoacaoCodigo)
                                    .addComponent(SPDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(SPDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckDoacaoUsuario)
                                    .addComponent(CBDoacaoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckDoacaoDoador)
                                    .addComponent(CBDoacaoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckDoacaoEvento)
                                    .addComponent(CBDoacaoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(JDFiltrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckDoacaoData)
                                    .addComponent(jLabel51)))
                            .addComponent(JDCDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BDoacaoFiltrar)
                .addContainerGap())
        );

        JDFiltrarItemDoacao.setTitle("Filtrar Items Doados");

        SPItemDoacaoDoacaoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckItemDoacaoDoacaoCodigo.setText("Código de Doação");

        jLabel52.setText("Filtrar por:");

        jLabel53.setText("Max:");

        SPItemDoacaoDoacaoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckItemDoacaoUsuario.setText("Usuário");

        CBItemDoacaoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckItemDoacaoDoador.setText("Doador");

        CBItemDoacaoDoador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckItemDoacaoEvento.setText("Evento de Origem");

        CBItemDoacaoEvento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel54.setText("Min:");

        CheckItemDoacaoData.setText("Intervalo de Data");

        jLabel55.setText("até");

        BItemDoacaoFiltrar.setText("Filtrar Items Doados");
        BItemDoacaoFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BItemDoacaoFiltrarActionPerformed(evt);
            }
        });

        jLabel56.setText("de");

        CheckItemDoacaoCodigo.setText("Código de Item Doação");

        jLabel57.setText("Min:");

        SPItemDoacaoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel58.setText("Max:");

        SPItemDoacaoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout JDFiltrarItemDoacaoLayout = new javax.swing.GroupLayout(JDFiltrarItemDoacao.getContentPane());
        JDFiltrarItemDoacao.getContentPane().setLayout(JDFiltrarItemDoacaoLayout);
        JDFiltrarItemDoacaoLayout.setHorizontalGroup(
            JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BItemDoacaoFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                        .addComponent(CheckItemDoacaoDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBItemDoacaoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemDoacaoLayout.createSequentialGroup()
                        .addComponent(CheckItemDoacaoData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCItemDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                        .addComponent(CheckItemDoacaoEvento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBItemDoacaoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemDoacaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemDoacaoLayout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JDCItemDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemDoacaoLayout.createSequentialGroup()
                                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CheckItemDoacaoDoacaoCodigo)
                                    .addComponent(jLabel52)
                                    .addComponent(CheckItemDoacaoCodigo)
                                    .addComponent(CheckItemDoacaoUsuario))
                                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemDoacaoLayout.createSequentialGroup()
                                                .addComponent(jLabel54)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(SPItemDoacaoDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel53))
                                            .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                                                .addComponent(jLabel57)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(SPItemDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel58)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(SPItemDoacaoMax)
                                            .addComponent(SPItemDoacaoDoacaoMax, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
                                    .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(CBItemDoacaoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        JDFiltrarItemDoacaoLayout.setVerticalGroup(
            JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemDoacaoDoacaoCodigo)
                    .addComponent(SPItemDoacaoDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53)
                    .addComponent(SPItemDoacaoDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemDoacaoCodigo)
                    .addComponent(SPItemDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(SPItemDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemDoacaoUsuario)
                    .addComponent(CBItemDoacaoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemDoacaoDoador)
                    .addComponent(CBItemDoacaoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemDoacaoEvento)
                    .addComponent(CBItemDoacaoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                        .addGroup(JDFiltrarItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CheckItemDoacaoData)
                            .addComponent(jLabel56))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel55))
                    .addGroup(JDFiltrarItemDoacaoLayout.createSequentialGroup()
                        .addComponent(JDCItemDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCItemDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BItemDoacaoFiltrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDFiltrarEstoque.setTitle("Filtrar Estoque");

        jLabel59.setText("Filtrar por:");

        BEstoqueFiltrar.setText("Filtrar Doacao");
        BEstoqueFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEstoqueFiltrarActionPerformed(evt);
            }
        });

        CheckEstoqueTipo.setText("Tipo de Item");

        CBEstoqueTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckEstoqueDoacao.setText("Quantidade de Doações");

        CheckEstoqueRepasse.setText("Quantidade de Repasses");

        CheckEstoqueSaldo.setText("Saldo");

        SPEstoqueDoacaoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel66.setText("Max");

        SPEstoqueDoacaoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel60.setText("Min");

        jLabel61.setText("Min");

        SPEstoqueRepasseMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel67.setText("Max");

        SPEstoqueRepasseMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel62.setText("Min");

        SPEstoqueSaldoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel68.setText("Max");

        SPEstoqueSaldoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout JDFiltrarEstoqueLayout = new javax.swing.GroupLayout(JDFiltrarEstoque.getContentPane());
        JDFiltrarEstoque.getContentPane().setLayout(JDFiltrarEstoqueLayout);
        JDFiltrarEstoqueLayout.setHorizontalGroup(
            JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BEstoqueFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarEstoqueLayout.createSequentialGroup()
                        .addComponent(CheckEstoqueTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBEstoqueTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarEstoqueLayout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JDFiltrarEstoqueLayout.createSequentialGroup()
                        .addComponent(CheckEstoqueDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPEstoqueDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPEstoqueDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarEstoqueLayout.createSequentialGroup()
                        .addComponent(CheckEstoqueRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPEstoqueRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPEstoqueRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarEstoqueLayout.createSequentialGroup()
                        .addComponent(CheckEstoqueSaldo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPEstoqueSaldoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPEstoqueSaldoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JDFiltrarEstoqueLayout.setVerticalGroup(
            JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckEstoqueTipo)
                    .addComponent(CBEstoqueTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckEstoqueDoacao)
                    .addComponent(SPEstoqueDoacaoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66)
                    .addComponent(SPEstoqueDoacaoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SPEstoqueRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel67)
                        .addComponent(SPEstoqueRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel61))
                    .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CheckEstoqueRepasse)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SPEstoqueSaldoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel68)
                        .addComponent(SPEstoqueSaldoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel62))
                    .addGroup(JDFiltrarEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CheckEstoqueSaldo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BEstoqueFiltrar)
                .addContainerGap())
        );

        JDFiltrarDoador.setTitle("Filtrar Doador");

        SPDoadorCodigoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckDoadorCodigo.setText("Código de Doador");

        jLabel63.setText("Filtrar por:");

        jLabel64.setText("Max:");

        SPDoadorCodigoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckDoadorDoador.setText("Doador");

        CBDoadorDoador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel65.setText("Min:");

        BDoadorFiltrar.setText("Filtrar Doador");
        BDoadorFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDoadorFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDFiltrarDoadorLayout = new javax.swing.GroupLayout(JDFiltrarDoador.getContentPane());
        JDFiltrarDoador.getContentPane().setLayout(JDFiltrarDoadorLayout);
        JDFiltrarDoadorLayout.setHorizontalGroup(
            JDFiltrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarDoadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDFiltrarDoadorLayout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(JDFiltrarDoadorLayout.createSequentialGroup()
                        .addGroup(JDFiltrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BDoadorFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(JDFiltrarDoadorLayout.createSequentialGroup()
                                .addComponent(CheckDoadorCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel65)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SPDoadorCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel64)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SPDoadorCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JDFiltrarDoadorLayout.createSequentialGroup()
                                .addComponent(CheckDoadorDoador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CBDoadorDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        JDFiltrarDoadorLayout.setVerticalGroup(
            JDFiltrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarDoadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckDoadorCodigo)
                    .addComponent(SPDoadorCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(SPDoadorCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarDoadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckDoadorDoador)
                    .addComponent(CBDoadorDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BDoadorFiltrar)
                .addContainerGap())
        );

        JDFiltrarRepasse.setTitle("Filtrar Repasse");

        SPRepasseMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckRepasseCodigo.setText("Código de Repasse");

        jLabel69.setText("Filtrar por:");

        jLabel70.setText("Max:");

        SPRepasseMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckRepasseUsuario.setText("Usuário");

        CBRepasseUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckRepasseColetor.setText("Coletor");

        CBRepasseColetor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckRepasseTipoColetor.setText("Tipo de Coletor");

        CBRepasseTipoColetor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel71.setText("Min:");

        CheckRepasseData.setText("Intervalo de Data");

        jLabel72.setText("até");

        BRepasseFiltrar.setText("Filtrar Repasse");
        BRepasseFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRepasseFiltrarActionPerformed(evt);
            }
        });

        jLabel73.setText("de");

        CheckRepasseDestinacao.setText("Destinação");

        CBRepasseDestinacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout JDFiltrarRepasseLayout = new javax.swing.GroupLayout(JDFiltrarRepasse.getContentPane());
        JDFiltrarRepasse.getContentPane().setLayout(JDFiltrarRepasseLayout);
        JDFiltrarRepasseLayout.setHorizontalGroup(
            JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BRepasseFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarRepasseLayout.createSequentialGroup()
                        .addComponent(CheckRepasseCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarRepasseLayout.createSequentialGroup()
                        .addComponent(CheckRepasseColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBRepasseColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarRepasseLayout.createSequentialGroup()
                        .addComponent(CheckRepasseUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBRepasseUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarRepasseLayout.createSequentialGroup()
                        .addComponent(CheckRepasseData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarRepasseLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarRepasseLayout.createSequentialGroup()
                        .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckRepasseTipoColetor)
                            .addComponent(jLabel69)
                            .addComponent(CheckRepasseDestinacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CBRepasseTipoColetor, 0, 187, Short.MAX_VALUE)
                            .addComponent(CBRepasseDestinacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JDFiltrarRepasseLayout.setVerticalGroup(
            JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckRepasseCodigo)
                    .addComponent(SPRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70)
                    .addComponent(SPRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckRepasseUsuario)
                    .addComponent(CBRepasseUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckRepasseColetor)
                    .addComponent(CBRepasseColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckRepasseTipoColetor)
                    .addComponent(CBRepasseTipoColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckRepasseDestinacao)
                    .addComponent(CBRepasseDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JDFiltrarRepasseLayout.createSequentialGroup()
                        .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JDFiltrarRepasseLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(JDFiltrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckRepasseData)
                                    .addComponent(jLabel73)))
                            .addComponent(JDCRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel72))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BRepasseFiltrar)
                .addContainerGap())
        );

        JDFiltrarItemRepasse.setTitle("Filtrar Item Repasse");

        SPItemRepasseRepasseMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckItemRepasseRepasse.setText("Código de Repasse");

        jLabel74.setText("Filtrar por:");

        jLabel75.setText("Max:");

        SPItemRepasseRepasseMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckItemRepasseUsuario.setText("Usuário");

        CBItemRepasseUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckItemRepasseColetor.setText("Coletor");

        CBItemRepasseColetor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckItemRepasseTipo.setText("Tipo de Item");

        CBItemRepasseTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel76.setText("Min:");

        CheckItemRepasseData.setText("Intervalo de Data");

        jLabel77.setText("até");

        BItemRepasseFiltrar.setText("Filtrar Item Repasse");
        BItemRepasseFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BItemRepasseFiltrarActionPerformed(evt);
            }
        });

        jLabel78.setText("de");

        CheckItemRepasseDestinacao.setText("Destinação");

        CBItemRepasseDestinacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CheckItemRepasseCodigo.setText("Código de Item Repasse");

        SPItemRepasseMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel79.setText("Min:");

        jLabel80.setText("Max:");

        SPItemRepasseMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckItemRepasseQuantidade.setText("Quantidade");

        jLabel81.setText("Min:");

        SPItemRepasseQuantidadeMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel82.setText("Max:");

        SPItemRepasseQuantidadeMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout JDFiltrarItemRepasseLayout = new javax.swing.GroupLayout(JDFiltrarItemRepasse.getContentPane());
        JDFiltrarItemRepasse.getContentPane().setLayout(JDFiltrarItemRepasseLayout);
        JDFiltrarItemRepasseLayout.setHorizontalGroup(
            JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BItemRepasseFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBItemRepasseColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBItemRepasseUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCItemRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel79)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel80)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseDestinacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBItemRepasseDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCItemRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBItemRepasseTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseQuantidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemRepasseQuantidadeMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel82)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemRepasseQuantidadeMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(jLabel74)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addComponent(CheckItemRepasseRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel76)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemRepasseRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemRepasseRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JDFiltrarItemRepasseLayout.setVerticalGroup(
            JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemRepasseCodigo)
                    .addComponent(SPItemRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80)
                    .addComponent(SPItemRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemRepasseRepasse)
                    .addComponent(jLabel76)
                    .addComponent(SPItemRepasseRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75)
                    .addComponent(SPItemRepasseRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemRepasseUsuario)
                    .addComponent(CBItemRepasseUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemRepasseColetor)
                    .addComponent(CBItemRepasseColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemRepasseDestinacao)
                    .addComponent(CBItemRepasseDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckItemRepasseTipo)
                    .addComponent(CBItemRepasseTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SPItemRepasseQuantidadeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel82)
                        .addComponent(SPItemRepasseQuantidadeMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel81))
                    .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CheckItemRepasseQuantidade)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JDFiltrarItemRepasseLayout.createSequentialGroup()
                        .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JDFiltrarItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CheckItemRepasseData)
                                .addComponent(jLabel78))
                            .addComponent(JDCItemRepasseMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCItemRepasseMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BItemRepasseFiltrar)
                .addContainerGap())
        );

        JDFiltrarColetor.setTitle("Filtrar Coletor");

        SPColetorCodigoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckColetorCodigo.setText("Código de Coletor");

        jLabel83.setText("Filtrar por:");

        jLabel84.setText("Max:");

        SPColetorCodigoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckColetorColetor.setText("Coletor");

        CBColetorColetor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel85.setText("Min:");

        BColetorFiltrar.setText("Filtrar Coletor");
        BColetorFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BColetorFiltrarActionPerformed(evt);
            }
        });

        CheckColetorTipo.setText("Tipo de Coletor");

        CBColetorTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout JDFiltrarColetorLayout = new javax.swing.GroupLayout(JDFiltrarColetor.getContentPane());
        JDFiltrarColetor.getContentPane().setLayout(JDFiltrarColetorLayout);
        JDFiltrarColetorLayout.setHorizontalGroup(
            JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BColetorFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarColetorLayout.createSequentialGroup()
                        .addComponent(CheckColetorCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel85)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPColetorCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel84)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPColetorCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarColetorLayout.createSequentialGroup()
                        .addGroup(JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckColetorColetor)
                            .addComponent(jLabel83)
                            .addComponent(CheckColetorTipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CBColetorColetor, 0, 187, Short.MAX_VALUE)
                            .addComponent(CBColetorTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JDFiltrarColetorLayout.setVerticalGroup(
            JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarColetorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel83)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckColetorCodigo)
                    .addComponent(SPColetorCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84)
                    .addComponent(SPColetorCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel85))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckColetorColetor)
                    .addComponent(CBColetorColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarColetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckColetorTipo)
                    .addComponent(CBColetorTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BColetorFiltrar)
                .addContainerGap())
        );

        JDFiltrarAcervo.setTitle("Filtrar Acervo");

        jLabel86.setText("Filtrar por:");

        CheckAcervoCodigo.setText("Código do Item do Acervo");

        CheckAcervoUsuario.setText("Usuário");

        CheckAcervoDoador.setText("Doador");

        CheckAcervoData.setText("Intervalo de Data");

        CheckAcervoTipo.setText("Tipo do Item");

        CheckAcervoMarca.setText("Marca");

        CheckAcervoModelo.setText("Modelo");

        CheckAcervoInterface.setText("Interface");

        CheckAcervoTecnologia.setText("Tecnologia");

        CheckAcervoCapacidade.setText("Capacidade (MB)");

        CheckAcervoAno.setText("Ano");

        CheckAcervoFuncionamento.setText("Funcionamento");

        CheckAcervoContainer.setText("Código de Container");

        jLabel87.setText("Max");

        SPAcervoCodigoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        SPAcervoCodigoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel88.setText("Min");

        CBAcervoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBAcervoDoador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel89.setText("até");

        jLabel90.setText("de");

        CBAcervoTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBAcervoMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBAcervoModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBAcervoInterface.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBAcervoTecnologia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel91.setText("Min");

        SPAcervoCapacidadeMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel92.setText("Max");

        SPAcervoCapacidadeMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel93.setText("Min");

        SPAcervoAnoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel94.setText("Max");

        SPAcervoAnoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        CheckAcervoFunciona.setText("Funciona");

        jLabel95.setText("Min");

        SPAcervoContainerMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel96.setText("Max");

        SPAcervoContainerMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        BAcervoFiltrar.setText("Filtrar Acervo");
        BAcervoFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAcervoFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDFiltrarAcervoLayout = new javax.swing.GroupLayout(JDFiltrarAcervo.getContentPane());
        JDFiltrarAcervo.getContentPane().setLayout(JDFiltrarAcervoLayout);
        JDFiltrarAcervoLayout.setHorizontalGroup(
            JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BAcervoFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel88)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel87)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBAcervoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarAcervoLayout.createSequentialGroup()
                        .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckAcervoDoador)
                            .addComponent(CheckAcervoData))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBAcervoDoador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarAcervoLayout.createSequentialGroup()
                                .addComponent(jLabel90)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JDCAcervoDataMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBAcervoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoMarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBAcervoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoModelo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBAcervoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoInterface)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBAcervoInterface, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(jLabel86)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoTecnologia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBAcervoTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoCapacidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel91)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoCapacidadeMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel92)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoCapacidadeMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoAno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel93)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoAnoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel94)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoAnoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoFuncionamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CheckAcervoFunciona))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDFiltrarAcervoLayout.createSequentialGroup()
                        .addComponent(CheckAcervoContainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel95)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoContainerMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel96)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoContainerMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarAcervoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel89)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCAcervoDataMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JDFiltrarAcervoLayout.setVerticalGroup(
            JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86)
                .addGap(10, 10, 10)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JDFiltrarAcervoLayout.createSequentialGroup()
                        .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JDFiltrarAcervoLayout.createSequentialGroup()
                                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckAcervoCodigo)
                                    .addComponent(jLabel87)
                                    .addComponent(SPAcervoCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SPAcervoCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel88))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckAcervoUsuario)
                                    .addComponent(CBAcervoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CheckAcervoDoador)
                                    .addComponent(CBAcervoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CheckAcervoData)
                                    .addComponent(JDCAcervoDataMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel90))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDCAcervoDataMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel89))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoTipo)
                    .addComponent(CBAcervoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoMarca)
                    .addComponent(CBAcervoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoModelo)
                    .addComponent(CBAcervoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoInterface)
                    .addComponent(CBAcervoInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoTecnologia)
                    .addComponent(CBAcervoTecnologia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoCapacidade)
                    .addComponent(jLabel92)
                    .addComponent(SPAcervoCapacidadeMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPAcervoCapacidadeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel91))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoAno)
                    .addComponent(jLabel94)
                    .addComponent(SPAcervoAnoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPAcervoAnoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoFuncionamento)
                    .addComponent(CheckAcervoFunciona))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAcervoContainer)
                    .addComponent(jLabel96)
                    .addComponent(SPAcervoContainerMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPAcervoContainerMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BAcervoFiltrar)
                .addContainerGap())
        );

        JDFiltrarImagem.setTitle("Filtrar Imagem");

        jLabel97.setText("Filtrar por:");

        CheckImagemCodigo.setText("Código de Imagem");

        CheckImagemMarca.setText("Marca");

        CheckImagemModelo.setText("Modelo");

        jLabel98.setText("Max");

        SPImagemCodigoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        SPImagemCodigoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel99.setText("Min");

        CBImagemMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBImagemModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BImagemFiltrar.setText("Filtrar Imagem");
        BImagemFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BImagemFiltrarActionPerformed(evt);
            }
        });

        CheckImagemItem.setText("Código de Item-Acervo");

        jLabel108.setText("Min");

        SPImagemItemMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel109.setText("Max");

        SPImagemItemMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout JDFiltrarImagemLayout = new javax.swing.GroupLayout(JDFiltrarImagem.getContentPane());
        JDFiltrarImagem.getContentPane().setLayout(JDFiltrarImagemLayout);
        JDFiltrarImagemLayout.setHorizontalGroup(
            JDFiltrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BImagemFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarImagemLayout.createSequentialGroup()
                        .addComponent(CheckImagemCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(jLabel99)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPImagemCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel98)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPImagemCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarImagemLayout.createSequentialGroup()
                        .addComponent(CheckImagemItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel108)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPImagemItemMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel109)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPImagemItemMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarImagemLayout.createSequentialGroup()
                        .addComponent(CheckImagemMarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBImagemMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarImagemLayout.createSequentialGroup()
                        .addComponent(jLabel97)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JDFiltrarImagemLayout.createSequentialGroup()
                        .addComponent(CheckImagemModelo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBImagemModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JDFiltrarImagemLayout.setVerticalGroup(
            JDFiltrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel97)
                .addGap(10, 10, 10)
                .addGroup(JDFiltrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckImagemCodigo)
                    .addComponent(jLabel98)
                    .addComponent(SPImagemCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPImagemCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckImagemItem)
                    .addComponent(jLabel109)
                    .addComponent(SPImagemItemMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPImagemItemMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckImagemMarca)
                    .addComponent(CBImagemMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckImagemModelo)
                    .addComponent(CBImagemModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BImagemFiltrar)
                .addContainerGap())
        );

        JDFiltrarContainer.setTitle("Filtrar Container");

        jLabel100.setText("Filtrar por:");

        CheckContainerCodigo.setText("Código de Container");

        CheckContainerLocalizacao.setText("Localização do Container");

        CheckContainerTipo.setText("Tipo do Container");

        jLabel101.setText("Max");

        SPContainerCodigoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        SPContainerCodigoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel102.setText("Min");

        CBContainerLocalizacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBContainerTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BContainerFiltrar.setText("Filtrar Container");
        BContainerFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BContainerFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JDFiltrarContainerLayout = new javax.swing.GroupLayout(JDFiltrarContainer.getContentPane());
        JDFiltrarContainer.getContentPane().setLayout(JDFiltrarContainerLayout);
        JDFiltrarContainerLayout.setHorizontalGroup(
            JDFiltrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BContainerFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarContainerLayout.createSequentialGroup()
                        .addComponent(CheckContainerCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel102)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPContainerCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel101)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPContainerCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarContainerLayout.createSequentialGroup()
                        .addComponent(CheckContainerLocalizacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBContainerLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDFiltrarContainerLayout.createSequentialGroup()
                        .addComponent(jLabel100)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JDFiltrarContainerLayout.createSequentialGroup()
                        .addComponent(CheckContainerTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBContainerTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JDFiltrarContainerLayout.setVerticalGroup(
            JDFiltrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel100)
                .addGap(10, 10, 10)
                .addGroup(JDFiltrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckContainerCodigo)
                    .addComponent(jLabel101)
                    .addComponent(SPContainerCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPContainerCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckContainerLocalizacao)
                    .addComponent(CBContainerLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckContainerTipo)
                    .addComponent(CBContainerTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BContainerFiltrar)
                .addContainerGap())
        );

        JDFiltrarUsuario.setTitle("Filtrar Usuario");

        jLabel103.setText("Filtrar por:");

        CheckUsuarioCodigo.setText("Código de Usuário");

        CheckUsuarioNome.setText("Nome do Usuário");

        CheckUsuarioPermissao.setText("Permissão");

        jLabel104.setText("Max");

        SPUsuarioCodigoMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        SPUsuarioCodigoMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel105.setText("Min");

        CBUsuarioNome.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BContainerFiltrar1.setText("Filtrar Usuário");
        BContainerFiltrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BContainerFiltrar1ActionPerformed(evt);
            }
        });

        CheckUsuarioAdministrador.setText("Administrador");

        javax.swing.GroupLayout JDFiltrarUsuarioLayout = new javax.swing.GroupLayout(JDFiltrarUsuario.getContentPane());
        JDFiltrarUsuario.getContentPane().setLayout(JDFiltrarUsuarioLayout);
        JDFiltrarUsuarioLayout.setHorizontalGroup(
            JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BContainerFiltrar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JDFiltrarUsuarioLayout.createSequentialGroup()
                        .addComponent(CheckUsuarioCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jLabel105)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPUsuarioCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel104)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPUsuarioCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDFiltrarUsuarioLayout.createSequentialGroup()
                        .addGroup(JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckUsuarioNome)
                            .addComponent(jLabel103)
                            .addComponent(CheckUsuarioPermissao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBUsuarioNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CheckUsuarioAdministrador, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        JDFiltrarUsuarioLayout.setVerticalGroup(
            JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDFiltrarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel103)
                .addGap(10, 10, 10)
                .addGroup(JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckUsuarioCodigo)
                    .addComponent(jLabel104)
                    .addComponent(SPUsuarioCodigoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SPUsuarioCodigoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckUsuarioNome)
                    .addComponent(CBUsuarioNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDFiltrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckUsuarioPermissao)
                    .addComponent(CheckUsuarioAdministrador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(BContainerFiltrar1)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SGACERVO - Principal");
        setMinimumSize(new java.awt.Dimension(700, 400));

        Principal.setForeground(new java.awt.Color(51, 51, 51));
        Principal.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        Principal.setName("SGACERVO - Principal"); // NOI18N
        Principal.setOpaque(true);
        Principal.setRequestFocusEnabled(false);
        Principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrincipalMouseClicked(evt);
            }
        });
        Principal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrincipalKeyPressed(evt);
            }
        });

        Doacoes.setOpaque(true);

        LBemVindoDoacoes.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LBemVindoDoacoes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBemVindoDoacoes.setText("O que deseja fazer?");

        BAbrirCadastrarDoacao.setText("Cadastrar Doação");
        BAbrirCadastrarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirCadastrarDoacaoActionPerformed(evt);
            }
        });

        BAbrirDoacoes.setText("Exibir Doações");
        BAbrirDoacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirDoacoesActionPerformed(evt);
            }
        });

        BAbrirEstoque.setText("Exibir Estoque");
        BAbrirEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirEstoqueActionPerformed(evt);
            }
        });

        BAbrirDoadores.setText("Exibir Doadores");
        BAbrirDoadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirDoadoresActionPerformed(evt);
            }
        });

        BAbrirItemDoacao.setText("Exibir Items Doados");
        BAbrirItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirItemDoacaoActionPerformed(evt);
            }
        });

        BMenuCadastrarDoador.setText("Cadastrar Doador");
        BMenuCadastrarDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BMenuCadastrarDoadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuDoacoesLayout = new javax.swing.GroupLayout(MenuDoacoes);
        MenuDoacoes.setLayout(MenuDoacoesLayout);
        MenuDoacoesLayout.setHorizontalGroup(
            MenuDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuDoacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBemVindoDoacoes)
                    .addGroup(MenuDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BMenuCadastrarDoador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirItemDoacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirDoadores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirEstoque, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirDoacoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirCadastrarDoacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(764, Short.MAX_VALUE))
        );
        MenuDoacoesLayout.setVerticalGroup(
            MenuDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuDoacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBemVindoDoacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirCadastrarDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BMenuCadastrarDoador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirDoacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirItemDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirEstoque)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirDoadores)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        Doacoes.addTab("Menu", MenuDoacoes);

        LOrigem.setText("*Evento de Origem:");

        Ldoador.setText("* Doador:");

        LCamposObrigatorios.setText("* Campos de preenchimento obrigatório.");

        CBDoadorDoacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BCadastrarDoacao.setText("Cadastrar");
        BCadastrarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarDoacaoActionPerformed(evt);
            }
        });

        BNovoDoador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoDoador.setToolTipText("Cadastrar Novo Doador");
        BNovoDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoDoadorActionPerformed(evt);
            }
        });

        CBEventoOrigem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoEventoOrigem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoEventoOrigem.setToolTipText("Cadastrar novo Evento de Origem");
        BNovoEventoOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoEventoOrigemActionPerformed(evt);
            }
        });

        LInfoDoacao.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LInfoDoacao.setText("Informações de Doação:");

        LItemsDoacao.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LItemsDoacao.setText("Items");

        TAdicionarItemDoacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Tipo", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TAdicionarItemDoacao);

        BAdicionarItemDoacao.setText("Adicionar Item");
        BAdicionarItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAdicionarItemDoacaoActionPerformed(evt);
            }
        });

        BRemoverItemDoacao.setText("Remover Itens");
        BRemoverItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRemoverItemDoacaoActionPerformed(evt);
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
                    .addComponent(LInfoDoacao)
                    .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LOrigem)
                            .addComponent(Ldoador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LItemsDoacao)
                            .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BAdicionarItemDoacao)
                                    .addComponent(BRemoverItemDoacao)))
                            .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                                .addComponent(CBDoadorDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BNovoDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                                .addComponent(CBEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BNovoEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BCadastrarDoacao))))
                .addContainerGap(261, Short.MAX_VALUE))
        );
        CadastrarDoacaoLayout.setVerticalGroup(
            CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LInfoDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposObrigatorios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BNovoDoador, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Ldoador)
                        .addComponent(CBDoadorDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BNovoEventoOrigem, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LOrigem)
                        .addComponent(CBEventoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LItemsDoacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CadastrarDoacaoLayout.createSequentialGroup()
                        .addComponent(BAdicionarItemDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRemoverItemDoacao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarDoacao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Doacoes.addTab("Cadastrar Doação", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarDoacao); // NOI18N

        RelatorioDoacoes.setBorder(null);

        TDoacao.setAutoCreateRowSorter(true);
        TDoacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TDoacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TDoacao.setRowHeight(30);
        RelatorioDoacoes.setViewportView(TDoacao);

        BExcluirDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirDoacao.setToolTipText("Excluir Doações");
        BExcluirDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirDoacaoActionPerformed(evt);
            }
        });

        BEditarDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarDoacao.setToolTipText("Editar Doações");
        BEditarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarDoacaoActionPerformed(evt);
            }
        });

        BRelatorioDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioDoacao.setToolTipText("Gerar Relatório");
        BRelatorioDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioDoacaoActionPerformed(evt);
            }
        });

        BFiltrarDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarDoacao.setToolTipText("Filtrar Resultados");
        BFiltrarDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarDoacaoActionPerformed(evt);
            }
        });

        jLabel7.setText("Mostrar");

        SPDoacaoItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPDoacaoItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPDoacaoItensPaginaStateChanged(evt);
            }
        });

        jLabel8.setText("itens por página.");

        jLabel9.setText("Página");

        LDoacaoPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LDoacaoPagina.setText("1");

        jLabel11.setText("de");

        LDoacaoTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LDoacaoTotalPaginas.setText("%TotalPaginas%");

        BDoacaoProxPagina.setText("Próxima Página");
        BDoacaoProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDoacaoProxPaginaActionPerformed(evt);
            }
        });

        BDoacaoPaginaAnterior.setText("Página Anterior");
        BDoacaoPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDoacaoPaginaAnteriorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelDoacoesLayout = new javax.swing.GroupLayout(PainelDoacoes);
        PainelDoacoes.setLayout(PainelDoacoesLayout);
        PainelDoacoesLayout.setHorizontalGroup(
            PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDoacoesLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPDoacaoItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LDoacaoPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LDoacaoTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(BDoacaoPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BDoacaoProxPagina))
                    .addComponent(RelatorioDoacoes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BFiltrarDoacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioDoacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirDoacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarDoacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelDoacoesLayout.setVerticalGroup(
            PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDoacoesLayout.createSequentialGroup()
                        .addComponent(BEditarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BFiltrarDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 272, Short.MAX_VALUE))
                    .addComponent(RelatorioDoacoes, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDoacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(SPDoacaoItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(LDoacaoPagina)
                    .addComponent(jLabel11)
                    .addComponent(LDoacaoTotalPaginas)
                    .addComponent(BDoacaoPaginaAnterior)
                    .addComponent(BDoacaoProxPagina))
                .addContainerGap())
        );

        Doacoes.addTab("Doações", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelDoacoes); // NOI18N

        RelatorioItemDoacao.setBorder(null);

        TItemDoacao.setAutoCreateRowSorter(true);
        TItemDoacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TItemDoacao.setRowHeight(30);
        RelatorioItemDoacao.setViewportView(TItemDoacao);

        BEditarItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarItemDoacao.setToolTipText("Editar Items Doados");
        BEditarItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarItemDoacaoActionPerformed(evt);
            }
        });

        BExcluirItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirItemDoacao.setToolTipText("Excluir Items Doados");
        BExcluirItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirItemDoacaoActionPerformed(evt);
            }
        });

        BRelatorioItemDoacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioItemDoacao.setToolTipText("Gerar Relatório de Items Doados");
        BRelatorioItemDoacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioItemDoacaoActionPerformed(evt);
            }
        });

        BRelatorioItemDoacao1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BRelatorioItemDoacao1.setToolTipText("Filtrar Resultados");
        BRelatorioItemDoacao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioItemDoacao1ActionPerformed(evt);
            }
        });

        jLabel10.setText("Mostrar");

        SPItemDoacaoItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPItemDoacaoItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPItemDoacaoItensPaginaStateChanged(evt);
            }
        });

        jLabel12.setText("itens por página.");

        jLabel13.setText("Página");

        LItemDoacaoPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LItemDoacaoPagina.setText("1");

        jLabel14.setText("de");

        LItemDoacaoTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LItemDoacaoTotalPaginas.setText("%TotalPaginas%");

        BItemDoacaoPaginaAnterior.setText("Página Anterior");
        BItemDoacaoPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BItemDoacaoPaginaAnteriorActionPerformed(evt);
            }
        });

        BItemDoacaoProxPagina.setText("Próxima Página");
        BItemDoacaoProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BItemDoacaoProxPaginaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelItemDoacaoLayout = new javax.swing.GroupLayout(PainelItemDoacao);
        PainelItemDoacao.setLayout(PainelItemDoacaoLayout);
        PainelItemDoacaoLayout.setHorizontalGroup(
            PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelItemDoacaoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemDoacaoItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LItemDoacaoPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LItemDoacaoTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BItemDoacaoPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BItemDoacaoProxPagina))
                    .addComponent(RelatorioItemDoacao, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BRelatorioItemDoacao1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioItemDoacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarItemDoacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirItemDoacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelItemDoacaoLayout.setVerticalGroup(
            PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelItemDoacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelItemDoacaoLayout.createSequentialGroup()
                        .addComponent(BEditarItemDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirItemDoacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioItemDoacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioItemDoacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(RelatorioItemDoacao, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelItemDoacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(SPItemDoacaoItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(LItemDoacaoPagina)
                    .addComponent(jLabel14)
                    .addComponent(LItemDoacaoTotalPaginas)
                    .addComponent(BItemDoacaoPaginaAnterior)
                    .addComponent(BItemDoacaoProxPagina))
                .addContainerGap())
        );

        Doacoes.addTab("Items Doados", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelItemDoacao); // NOI18N

        TEstoque.setAutoCreateRowSorter(true);
        TEstoque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TEstoque.setRowHeight(30);
        RelatorioEstoque.setViewportView(TEstoque);

        BRelatorioEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioEstoque.setToolTipText("Gerar Relatório de Estoque");
        BRelatorioEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioEstoqueActionPerformed(evt);
            }
        });

        BFiltrarEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarEstoque.setToolTipText("Filtrar Resultados");
        BFiltrarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarEstoqueActionPerformed(evt);
            }
        });

        jLabel15.setText("Mostrar");

        SPEstoqueItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPEstoqueItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPEstoqueItensPaginaStateChanged(evt);
            }
        });

        jLabel16.setText("itens por página.");

        jLabel17.setText("Página");

        LEstoquePagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LEstoquePagina.setText("1");

        jLabel18.setText("de");

        LEstoqueTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LEstoqueTotalPaginas.setText("%TotalPaginas%");

        BEstoquePaginaAnterior.setText("Página Anterior");
        BEstoquePaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEstoquePaginaAnteriorActionPerformed(evt);
            }
        });

        BEstoqueProxPagina.setText("Próxima Página");
        BEstoqueProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEstoqueProxPaginaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelEstoqueLayout = new javax.swing.GroupLayout(PainelEstoque);
        PainelEstoque.setLayout(PainelEstoqueLayout);
        PainelEstoqueLayout.setHorizontalGroup(
            PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelEstoqueLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPEstoqueItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LEstoquePagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LEstoqueTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(BEstoquePaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BEstoqueProxPagina))
                    .addComponent(RelatorioEstoque))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BRelatorioEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BFiltrarEstoque, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelEstoqueLayout.setVerticalGroup(
            PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelEstoqueLayout.createSequentialGroup()
                        .addComponent(BFiltrarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 335, Short.MAX_VALUE))
                    .addComponent(RelatorioEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(SPEstoqueItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(LEstoquePagina)
                    .addComponent(jLabel18)
                    .addComponent(LEstoqueTotalPaginas)
                    .addComponent(BEstoquePaginaAnterior)
                    .addComponent(BEstoqueProxPagina))
                .addContainerGap())
        );

        Doacoes.addTab("Estoque", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelEstoque); // NOI18N

        TDoador.setAutoCreateRowSorter(true);
        TDoador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TDoador.setRowHeight(30);
        ExibirDoadores.setViewportView(TDoador);
        if (TDoador.getColumnModel().getColumnCount() > 0) {
            TDoador.getColumnModel().getColumn(0).setHeaderValue("Código do Doador");
            TDoador.getColumnModel().getColumn(1).setHeaderValue("Nome do Doador");
        }

        BExcluirDoador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirDoador.setToolTipText("Excluir Doador");
        BExcluirDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirDoadorActionPerformed(evt);
            }
        });

        BEditarDoador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarDoador.setToolTipText("Editar Doador");
        BEditarDoador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarDoadorActionPerformed(evt);
            }
        });

        BRelatorioDoadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioDoadores.setToolTipText("Gerar Relatório de Doadores");
        BRelatorioDoadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioDoadoresActionPerformed(evt);
            }
        });

        BRelatorioDoadores1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BRelatorioDoadores1.setToolTipText("Filtrar Resultados");
        BRelatorioDoadores1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioDoadores1ActionPerformed(evt);
            }
        });

        jLabel19.setText("Mostrar");

        SPDoadorItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPDoadorItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPDoadorItensPaginaStateChanged(evt);
            }
        });

        jLabel20.setText("itens por página.");

        jLabel21.setText("Página");

        LDoadorPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LDoadorPagina.setText("1");

        jLabel22.setText("de");

        LDoadorTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LDoadorTotalPaginas.setText("%TotalPaginas%");

        BDoadorPaginaAnterior.setText("Página Anterior");
        BDoadorPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDoadorPaginaAnteriorActionPerformed(evt);
            }
        });

        BDoadorProxPagina.setText("Próxima Página");
        BDoadorProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDoadorProxPaginaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelDoadoresLayout = new javax.swing.GroupLayout(PainelDoadores);
        PainelDoadores.setLayout(PainelDoadoresLayout);
        PainelDoadoresLayout.setHorizontalGroup(
            PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDoadoresLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPDoadorItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LDoadorPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LDoadorTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(BDoadorPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BDoadorProxPagina))
                    .addComponent(ExibirDoadores))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BExcluirDoador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioDoadores1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioDoadores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarDoador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelDoadoresLayout.setVerticalGroup(
            PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDoadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDoadoresLayout.createSequentialGroup()
                        .addComponent(BEditarDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirDoador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioDoadores1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioDoadores, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ExibirDoadores, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDoadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(SPDoadorItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(LDoadorPagina)
                    .addComponent(jLabel22)
                    .addComponent(LDoadorTotalPaginas)
                    .addComponent(BDoadorPaginaAnterior)
                    .addComponent(BDoadorProxPagina))
                .addContainerGap())
        );

        Doacoes.addTab("Doadores", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelDoadores); // NOI18N

        Principal.addTab("Doações", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/doacao32.png")), Doacoes); // NOI18N

        Repasses.setOpaque(true);

        LBemVindoDoacoes1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LBemVindoDoacoes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBemVindoDoacoes1.setText("O que deseja fazer?");

        BAbrirCadastrarDoacao1.setText("Cadastrar Repasse");
        BAbrirCadastrarDoacao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirCadastrarDoacao1ActionPerformed(evt);
            }
        });

        BAbrirRepasses.setText("Exibir Repasses");
        BAbrirRepasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirRepassesActionPerformed(evt);
            }
        });

        BExibirColetores.setText("Exibir Coletores");
        BExibirColetores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExibirColetoresActionPerformed(evt);
            }
        });

        BAbrirItemRepasse.setText("Exibir Items repassados");
        BAbrirItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirItemRepasseActionPerformed(evt);
            }
        });

        BMenuCadastrarColetor.setText("Cadastrar Coletor");
        BMenuCadastrarColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BMenuCadastrarColetorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuRepasseLayout = new javax.swing.GroupLayout(MenuRepasse);
        MenuRepasse.setLayout(MenuRepasseLayout);
        MenuRepasseLayout.setHorizontalGroup(
            MenuRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBemVindoDoacoes1)
                    .addGroup(MenuRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BMenuCadastrarColetor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirItemRepasse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BExibirColetores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirRepasses, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirCadastrarDoacao1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(764, Short.MAX_VALUE))
        );
        MenuRepasseLayout.setVerticalGroup(
            MenuRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBemVindoDoacoes1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirCadastrarDoacao1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BMenuCadastrarColetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirRepasses)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirItemRepasse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BExibirColetores)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        Repasses.addTab("Menu", MenuRepasse);

        LDescricaoRepasse.setText("* Destinação:");

        LColetorRepasse.setText("* Coletor:");

        BCadastrarRepasse.setText("Cadastrar");
        BCadastrarRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarRepasseActionPerformed(evt);
            }
        });

        CBColetor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoColetor.setToolTipText("Cadastrar Novo Coletor");
        BNovoColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoColetorActionPerformed(evt);
            }
        });

        CBDestinacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoDestinacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoDestinacao.setToolTipText("Cadastrar Nova Destinação");
        BNovoDestinacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoDestinacaoActionPerformed(evt);
            }
        });

        LInfoDoacao1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LInfoDoacao1.setText("Informações de Repasse:");

        LCamposObrigatorios1.setText("* Campos de preenchimento obrigatório.");

        LItemsDoacao1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LItemsDoacao1.setText("Items");

        TAdicionarItemRepasse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Tipo", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TAdicionarItemRepasse);

        BAdicionarItemRepasse.setText("Adicionar Item");
        BAdicionarItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAdicionarItemRepasseActionPerformed(evt);
            }
        });

        BRemoverItemRepasse.setText("Remover Itens");
        BRemoverItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRemoverItemRepasseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarRepasseLayout = new javax.swing.GroupLayout(CadastrarRepasse);
        CadastrarRepasse.setLayout(CadastrarRepasseLayout);
        CadastrarRepasseLayout.setHorizontalGroup(
            CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LInfoDoacao1)
                    .addComponent(LCamposObrigatorios1)
                    .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LDescricaoRepasse)
                            .addComponent(LColetorRepasse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LItemsDoacao1)
                            .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BAdicionarItemRepasse)
                                    .addComponent(BRemoverItemRepasse)))
                            .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BCadastrarRepasse)
                                    .addComponent(CBColetor, 0, 200, Short.MAX_VALUE)
                                    .addComponent(CBDestinacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BNovoColetor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BNovoDestinacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(264, Short.MAX_VALUE))
        );
        CadastrarRepasseLayout.setVerticalGroup(
            CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LInfoDoacao1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposObrigatorios1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LDescricaoRepasse)
                        .addComponent(CBDestinacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoDestinacao, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LColetorRepasse)
                        .addComponent(CBColetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoColetor, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LItemsDoacao1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarRepasseLayout.createSequentialGroup()
                        .addComponent(BAdicionarItemRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRemoverItemRepasse))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarRepasse)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Repasses.addTab("Cadastrar Repasse", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarRepasse); // NOI18N

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
        BEditarRepasse.setToolTipText("Editar Repasse");
        BEditarRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarRepasseActionPerformed(evt);
            }
        });

        BExcluirRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirRepasse.setToolTipText("Excluir Repasse");
        BExcluirRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirRepasseActionPerformed(evt);
            }
        });

        BRelatorioRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioRepasse.setToolTipText("Gerar Relatório de Repasse");
        BRelatorioRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioRepasseActionPerformed(evt);
            }
        });

        jLabel23.setText("Mostrar");

        SPRepasseItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPRepasseItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPRepasseItensPaginaStateChanged(evt);
            }
        });

        jLabel24.setText("itens por página.");

        jLabel25.setText("Página");

        LRepassePagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LRepassePagina.setText("1");

        jLabel26.setText("de");

        LRepasseTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LRepasseTotalPaginas.setText("%TotalPaginas%");

        BRepassePaginaAnterior.setText("Página Anterior");
        BRepassePaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRepassePaginaAnteriorActionPerformed(evt);
            }
        });

        BRepasseProxPagina.setText("Próxima Página");
        BRepasseProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRepasseProxPaginaActionPerformed(evt);
            }
        });

        BFiltrarRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarRepasse.setToolTipText("Filtrar Resultados");
        BFiltrarRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarRepasseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelRepasseLayout = new javax.swing.GroupLayout(PainelRepasse);
        PainelRepasse.setLayout(PainelRepasseLayout);
        PainelRepasseLayout.setHorizontalGroup(
            PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelRepasseLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPRepasseItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LRepassePagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LRepasseTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BRepassePaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRepasseProxPagina))
                    .addComponent(RelatorioRepasse, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BEditarRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BExcluirRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(BFiltrarRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BRelatorioRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(BFiltrarRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(RelatorioRepasse, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(SPRepasseItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(LRepassePagina)
                    .addComponent(jLabel26)
                    .addComponent(LRepasseTotalPaginas)
                    .addComponent(BRepassePaginaAnterior)
                    .addComponent(BRepasseProxPagina))
                .addContainerGap())
        );

        Repasses.addTab("Repasses", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelRepasse); // NOI18N

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
        BEditarItemRepasse.setToolTipText("Editar Item de Repasse");
        BEditarItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarItemRepasseActionPerformed(evt);
            }
        });

        BExcluirItemRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirItemRepasse.setToolTipText("Excluir Item Repasse");
        BExcluirItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirItemRepasseActionPerformed(evt);
            }
        });

        BRelatorioItemRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioItemRepasse.setToolTipText("Gerar Relatório de Item Repasse");
        BRelatorioItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioItemRepasseActionPerformed(evt);
            }
        });

        jLabel27.setText("Mostrar");

        SPItemRepasseItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPItemRepasseItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPItemRepasseItensPaginaStateChanged(evt);
            }
        });

        jLabel28.setText("itens por página.");

        jLabel29.setText("Página");

        LItemRepassePagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LItemRepassePagina.setText("1");

        jLabel30.setText("de");

        LItemRepasseTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LItemRepasseTotalPaginas.setText("%TotalPaginas%");

        BItemRepassePaginaAnterior.setText("Página Anterior");
        BItemRepassePaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BItemRepassePaginaAnteriorActionPerformed(evt);
            }
        });

        BItemRepasseProxPagina.setText("Próxima Página");
        BItemRepasseProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BItemRepasseProxPaginaActionPerformed(evt);
            }
        });

        BFiltrarItemRepasse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarItemRepasse.setToolTipText("Filtrar Resultados");
        BFiltrarItemRepasse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarItemRepasseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelItemRepasseLayout = new javax.swing.GroupLayout(PainelItemRepasse);
        PainelItemRepasse.setLayout(PainelItemRepasseLayout);
        PainelItemRepasseLayout.setHorizontalGroup(
            PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelItemRepasseLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPItemRepasseItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LItemRepassePagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LItemRepasseTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(BItemRepassePaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BItemRepasseProxPagina))
                    .addComponent(RelatorioItemRepasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BFiltrarItemRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioItemRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarItemRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirItemRepasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelItemRepasseLayout.setVerticalGroup(
            PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelItemRepasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelItemRepasseLayout.createSequentialGroup()
                        .addComponent(BEditarItemRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirItemRepasse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BFiltrarItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioItemRepasse, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(RelatorioItemRepasse, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelItemRepasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(SPItemRepasseItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(LItemRepassePagina)
                    .addComponent(jLabel30)
                    .addComponent(LItemRepasseTotalPaginas)
                    .addComponent(BItemRepassePaginaAnterior)
                    .addComponent(BItemRepasseProxPagina))
                .addContainerGap())
        );

        Repasses.addTab("Items Repassados", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelItemRepasse); // NOI18N

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
        BEditarColetor.setToolTipText("Editar Coletor");
        BEditarColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarColetorActionPerformed(evt);
            }
        });

        BExcluirColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirColetor.setToolTipText("Excluir Coletor");
        BExcluirColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirColetorActionPerformed(evt);
            }
        });

        BRelatorioColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioColetor.setToolTipText("Gerar Relatório de Coletores");
        BRelatorioColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioColetorActionPerformed(evt);
            }
        });

        jLabel31.setText("Mostrar");

        SPColetorItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPColetorItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPColetorItensPaginaStateChanged(evt);
            }
        });

        jLabel32.setText("itens por página.");

        jLabel33.setText("Página");

        LColetorPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LColetorPagina.setText("1");

        jLabel34.setText("de");

        LColetorTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LColetorTotalPaginas.setText("%TotalPaginas%");

        BColetorPaginaAnterior.setText("Página Anterior");
        BColetorPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BColetorPaginaAnteriorActionPerformed(evt);
            }
        });

        BColetorProxPagina.setText("Próxima Página");
        BColetorProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BColetorProxPaginaActionPerformed(evt);
            }
        });

        BFiltrarColetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarColetor.setToolTipText("Filtrar Resultados");
        BFiltrarColetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarColetorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelColetoresLayout = new javax.swing.GroupLayout(PainelColetores);
        PainelColetores.setLayout(PainelColetoresLayout);
        PainelColetoresLayout.setHorizontalGroup(
            PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelColetoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelColetoresLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPColetorItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LColetorPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LColetorTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(BColetorPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BColetorProxPagina))
                    .addComponent(ExibirColetores))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BFiltrarColetor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioColetor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BEditarColetor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirColetor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelColetoresLayout.setVerticalGroup(
            PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelColetoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelColetoresLayout.createSequentialGroup()
                        .addComponent(BEditarColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirColetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BFiltrarColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioColetor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ExibirColetores, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelColetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(SPColetorItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(LColetorPagina)
                    .addComponent(jLabel34)
                    .addComponent(LColetorTotalPaginas)
                    .addComponent(BColetorPaginaAnterior)
                    .addComponent(BColetorProxPagina))
                .addContainerGap())
        );

        Repasses.addTab("Coletores", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelColetores); // NOI18N

        Principal.addTab("Repasses", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/repasse32.png")), Repasses); // NOI18N

        Acervo.setOpaque(true);

        LBemVindoDoacoes2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LBemVindoDoacoes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBemVindoDoacoes2.setText("O que deseja fazer?");

        BMenuCadastrarItemAcervo.setText("Cadastrar Item ao Acervo");
        BMenuCadastrarItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BMenuCadastrarItemAcervoActionPerformed(evt);
            }
        });

        BMenuCadastrarImagem.setText("Cadastrar Imagem de Item");
        BMenuCadastrarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BMenuCadastrarImagemActionPerformed(evt);
            }
        });

        BExibirAcervo.setText("Exibir Acervo");
        BExibirAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExibirAcervoActionPerformed(evt);
            }
        });

        BExibirImagens.setText("Exibir Imagens");
        BExibirImagens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExibirImagensActionPerformed(evt);
            }
        });

        BMenuCadastrarContainer.setText("Cadastrar Container");
        BMenuCadastrarContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BMenuCadastrarContainerActionPerformed(evt);
            }
        });

        BExibirContainer.setText("Exibir Containeres");
        BExibirContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExibirContainerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuAcervoLayout = new javax.swing.GroupLayout(MenuAcervo);
        MenuAcervo.setLayout(MenuAcervoLayout);
        MenuAcervoLayout.setHorizontalGroup(
            MenuAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBemVindoDoacoes2)
                    .addGroup(MenuAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BExibirContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BMenuCadastrarContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BExibirImagens, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BExibirAcervo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BMenuCadastrarImagem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BMenuCadastrarItemAcervo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(764, Short.MAX_VALUE))
        );
        MenuAcervoLayout.setVerticalGroup(
            MenuAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBemVindoDoacoes2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BMenuCadastrarItemAcervo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BMenuCadastrarImagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BMenuCadastrarContainer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BExibirAcervo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BExibirImagens)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BExibirContainer)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        Acervo.addTab("Menu", MenuAcervo);

        LCod_Doador.setText("*Doador:");

        LTipoItemAcervo.setText("* Tipo do Item:");

        LMarca.setText("* Marca:");

        LModeloItemAcervo.setText("* Modelo:");

        LAnoItemAcervo.setText("*Ano:");

        LDescricao.setText("* Descrição:");

        LItemFunciona.setText("Item Funciona:");

        LCamposComplementares.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        LCamposComplementares.setText("Campos Complementares (OPCIONAIS)");

        LInterface.setText("Interface");

        LTecnologia.setText("Tecnologia:");

        LCapacidade_MB.setText("Capacidade (MB):");

        LContainer.setText("Código de Container:");

        LPO.setText("* Campos de preenchimento obrigatório.");

        campoAnoItemAcervo.setToolTipText("Ano de lançamento do item");

        SPDescricaoItemAcervo.setViewportView(campoDescricaoItemAcervo);

        CBFunciona.setText("Funciona");

        CBMarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBModelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBInterface.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CBTecnologia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BNovoTipoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoItem.setToolTipText("Cadastrar Novo Tipo de Item");
        BNovoTipoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoItemActionPerformed(evt);
            }
        });

        BNovoMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoMarca.setToolTipText("Cadastrar Nova Marca");
        BNovoMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoMarcaActionPerformed(evt);
            }
        });

        BNovoModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoModelo.setToolTipText("Cadastrar Novo Modelo");
        BNovoModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoModeloActionPerformed(evt);
            }
        });

        BNovoInterface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoInterface.setToolTipText("Cadastrar Nova Interface");
        BNovoInterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoInterfaceActionPerformed(evt);
            }
        });

        BNovoTecnologia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTecnologia.setToolTipText("Cadastrar Nova Tecnologia");
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
        BNovoDoadorAcervo.setToolTipText("Cadastrar Novo Doador");
        BNovoDoadorAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoDoadorAcervoActionPerformed(evt);
            }
        });

        LInfoDoacao2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LInfoDoacao2.setText("Informações de Item de Acervo:");

        javax.swing.GroupLayout CadastrarItemAcervoLayout = new javax.swing.GroupLayout(CadastrarItemAcervo);
        CadastrarItemAcervo.setLayout(CadastrarItemAcervoLayout);
        CadastrarItemAcervoLayout.setHorizontalGroup(
            CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LItemFunciona)
                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(LTipoItemAcervo)
                                            .addComponent(LCod_Doador)
                                            .addComponent(LMarca)
                                            .addComponent(LModeloItemAcervo)
                                            .addComponent(LAnoItemAcervo)
                                            .addComponent(LDescricao))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CBTipoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CBDoadorItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CBMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CBModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAnoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(SPDescricaoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CBFunciona)
                                            .addComponent(BCadastrarItemAcervo)))
                                    .addComponent(LPO))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(BNovoTipoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(BNovoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LContainer)
                                            .addComponent(LCapacidade_MB, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                        .addComponent(BNovoDoadorAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(LTecnologia))
                                    .addComponent(LInterface)
                                    .addComponent(BNovoModelo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(LInfoDoacao2))
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
                            .addComponent(LCamposComplementares))))
                .addContainerGap(143, Short.MAX_VALUE))
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
                        .addComponent(LInfoDoacao2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LPO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarItemAcervoLayout.createSequentialGroup()
                                .addComponent(BNovoDoadorAcervo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(CBTipoItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LTipoItemAcervo))
                                    .addComponent(BNovoTipoItem, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LMarca)
                                        .addComponent(CBMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(BNovoMarca))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BNovoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LModeloItemAcervo)
                                        .addComponent(CBModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(CadastrarItemAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CBDoadorItemAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(LCod_Doador)))
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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Acervo.addTab("Cadastrar Item ao Acervo", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarItemAcervo); // NOI18N

        LItemAcervoCadastrarImagem.setText("*Código de Item Acervo:");

        LLinkCadastrarImagem.setText("*Caminho/Link da Imagem:");

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
        BCheckLink.setToolTipText("Verificar consistência do Link");
        BCheckLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCheckLinkActionPerformed(evt);
            }
        });

        LFotoAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png"))); // NOI18N

        LCamposCadastrarImagem.setText("*Campos de preenchimento obrigatório.");

        LInfoDoacao3.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LInfoDoacao3.setText("Informações da Imagem:");

        javax.swing.GroupLayout CadastrarImagemLayout = new javax.swing.GroupLayout(CadastrarImagem);
        CadastrarImagem.setLayout(CadastrarImagemLayout);
        CadastrarImagemLayout.setHorizontalGroup(
            CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarImagemLayout.createSequentialGroup()
                        .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LItemAcervoCadastrarImagem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LLinkCadastrarImagem, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LFotoAcervo)
                            .addGroup(CadastrarImagemLayout.createSequentialGroup()
                                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BCadastrarImagem)
                                    .addComponent(campoItemAcervoCadastrarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoLink, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BCheckLink))))
                    .addComponent(LCamposCadastrarImagem)
                    .addComponent(LInfoDoacao3))
                .addContainerGap(548, Short.MAX_VALUE))
        );
        CadastrarImagemLayout.setVerticalGroup(
            CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LInfoDoacao3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposCadastrarImagem)
                .addGap(10, 10, 10)
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoItemAcervoCadastrarImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LItemAcervoCadastrarImagem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LLinkCadastrarImagem)
                        .addComponent(campoLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BCheckLink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LFotoAcervo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarImagem)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        Acervo.addTab("Cadastrar Imagem", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarImagem); // NOI18N

        LLocalizacaoCadastrarContainer.setText("*Localização de Container:");

        LTipoContainerCadastrarContainer.setText("*Tipo de Container:");

        LCamposCadastrarContainer.setText("*Campos de preenchimento obrigatório.");

        CBTipoContainerCadastrarContainer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BCadastrarContainer.setText("Cadastrar");
        BCadastrarContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarContainerActionPerformed(evt);
            }
        });

        BNovoTipoContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png"))); // NOI18N
        BNovoTipoContainer.setToolTipText("Cadastrar Novo Tipo de Container");
        BNovoTipoContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNovoTipoContainerActionPerformed(evt);
            }
        });

        LInfoDoacao4.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LInfoDoacao4.setText("Informações do Container:");

        javax.swing.GroupLayout CadastrarContainerLayout = new javax.swing.GroupLayout(CadastrarContainer);
        CadastrarContainer.setLayout(CadastrarContainerLayout);
        CadastrarContainerLayout.setHorizontalGroup(
            CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarContainerLayout.createSequentialGroup()
                        .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LLocalizacaoCadastrarContainer)
                            .addComponent(LTipoContainerCadastrarContainer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BCadastrarContainer)
                            .addComponent(campoLocalizacaoCadastrarContainer)
                            .addComponent(CBTipoContainerCadastrarContainer, 0, 189, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BNovoTipoContainer))
                    .addComponent(LCamposCadastrarContainer)
                    .addComponent(LInfoDoacao4))
                .addContainerGap(559, Short.MAX_VALUE))
        );
        CadastrarContainerLayout.setVerticalGroup(
            CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LInfoDoacao4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LCamposCadastrarContainer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LLocalizacaoCadastrarContainer)
                    .addComponent(campoLocalizacaoCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LTipoContainerCadastrarContainer)
                        .addComponent(CBTipoContainerCadastrarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BNovoTipoContainer, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BCadastrarContainer)
                .addContainerGap(289, Short.MAX_VALUE))
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
        BEditarItemAcervo.setToolTipText("Editar Item de Acervo");
        BEditarItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarItemAcervoActionPerformed(evt);
            }
        });

        BExcluirItemAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirItemAcervo.setToolTipText("Excluir  Item de Acervo");
        BExcluirItemAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirItemAcervoActionPerformed(evt);
            }
        });

        BRelatorioAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/relatorio.png"))); // NOI18N
        BRelatorioAcervo.setToolTipText("Gerar Relatório de  Item de Acervo");
        BRelatorioAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRelatorioAcervoActionPerformed(evt);
            }
        });

        jLabel35.setText("Mostrar");

        SPAcervoItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPAcervoItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPAcervoItensPaginaStateChanged(evt);
            }
        });

        jLabel36.setText("itens por página.");

        jLabel37.setText("Página");

        LAcervoPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LAcervoPagina.setText("1");

        jLabel38.setText("de");

        LAcervoTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LAcervoTotalPaginas.setText("%TotalPaginas%");

        BAcervoPaginaAnterior.setText("Página Anterior");
        BAcervoPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAcervoPaginaAnteriorActionPerformed(evt);
            }
        });

        BAcervoProxPagina.setText("Próxima Página");
        BAcervoProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAcervoProxPaginaActionPerformed(evt);
            }
        });

        BFiltrarAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarAcervo.setToolTipText("Filtrar Resultados");
        BFiltrarAcervo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarAcervoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelAcervoLayout = new javax.swing.GroupLayout(PainelAcervo);
        PainelAcervo.setLayout(PainelAcervoLayout);
        PainelAcervoLayout.setHorizontalGroup(
            PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelAcervoLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPAcervoItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LAcervoPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LAcervoTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                        .addComponent(BAcervoPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BAcervoProxPagina))
                    .addComponent(RelatorioAcervo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BEditarItemAcervo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BExcluirItemAcervo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BFiltrarAcervo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BRelatorioAcervo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PainelAcervoLayout.setVerticalGroup(
            PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelAcervoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelAcervoLayout.createSequentialGroup()
                        .addComponent(BEditarItemAcervo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirItemAcervo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BFiltrarAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRelatorioAcervo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(RelatorioAcervo, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelAcervoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(SPAcervoItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(LAcervoPagina)
                    .addComponent(jLabel38)
                    .addComponent(LAcervoTotalPaginas)
                    .addComponent(BAcervoPaginaAnterior)
                    .addComponent(BAcervoProxPagina))
                .addContainerGap())
        );

        Acervo.addTab("Acervo", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelAcervo); // NOI18N

        TImagem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TImagem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TImagem.setRowHeight(30);
        SPImagem.setViewportView(TImagem);

        BEditarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarImagem.setToolTipText("Editar Imagem");
        BEditarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarImagemActionPerformed(evt);
            }
        });

        BExcluirImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirImagem.setToolTipText("Excluir Imagens");
        BExcluirImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirImagemActionPerformed(evt);
            }
        });

        jLabel39.setText("Mostrar");

        SPImagemItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPImagemItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPImagemItensPaginaStateChanged(evt);
            }
        });

        jLabel40.setText("itens por página.");

        jLabel41.setText("Página");

        LImagemPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LImagemPagina.setText("1");

        jLabel42.setText("de");

        LImagemTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LImagemTotalPaginas.setText("%TotalPaginas%");

        BImagemPaginaAnterior.setText("Página Anterior");
        BImagemPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BImagemPaginaAnteriorActionPerformed(evt);
            }
        });

        BImagemProxPagina.setText("Próxima Página");
        BImagemProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BImagemProxPaginaActionPerformed(evt);
            }
        });

        BFiltrarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarImagem.setToolTipText("Filtrar Resultados");
        BFiltrarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarImagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ImagensLayout = new javax.swing.GroupLayout(Imagens);
        Imagens.setLayout(ImagensLayout);
        ImagensLayout.setHorizontalGroup(
            ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ImagensLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPImagemItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel40)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LImagemPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LImagemTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BImagemPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BImagemProxPagina))
                    .addComponent(SPImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BExcluirImagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BEditarImagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BFiltrarImagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(BFiltrarImagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirImagem)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(SPImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(SPImagemItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(LImagemPagina)
                    .addComponent(jLabel42)
                    .addComponent(LImagemTotalPaginas)
                    .addComponent(BImagemPaginaAnterior)
                    .addComponent(BImagemProxPagina))
                .addContainerGap())
        );

        Acervo.addTab("Imagens", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), Imagens); // NOI18N

        SPContainer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        TContainer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TContainer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TContainer.setRowHeight(30);
        SPContainer.setViewportView(TContainer);

        BEditarContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/edit.png"))); // NOI18N
        BEditarContainer.setToolTipText("Editar Container");
        BEditarContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditarContainerActionPerformed(evt);
            }
        });

        BExcluirContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/delete16.png"))); // NOI18N
        BExcluirContainer.setToolTipText("Excluir Containers");
        BExcluirContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExcluirContainerActionPerformed(evt);
            }
        });

        jLabel43.setText("Mostrar");

        SPContainerItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPContainerItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPContainerItensPaginaStateChanged(evt);
            }
        });

        jLabel44.setText("itens por página.");

        jLabel45.setText("Página");

        LContainerPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LContainerPagina.setText("1");

        jLabel46.setText("de");

        LContainerTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LContainerTotalPaginas.setText("%TotalPaginas%");

        BContainerPaginaAnterior.setText("Página Anterior");
        BContainerPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BContainerPaginaAnteriorActionPerformed(evt);
            }
        });

        BContainerProxPagina.setText("Próxima Página");
        BContainerProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BContainerProxPaginaActionPerformed(evt);
            }
        });

        BFiltrarContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarContainer.setToolTipText("Filtrar Resultados");
        BFiltrarContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarContainerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPContainerItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel44)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LContainerPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LContainerTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BContainerPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BContainerProxPagina))
                    .addComponent(SPContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BExcluirContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BEditarContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BFiltrarContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(BFiltrarContainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirContainer)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(SPContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(SPContainerItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45)
                    .addComponent(LContainerPagina)
                    .addComponent(jLabel46)
                    .addComponent(LContainerTotalPaginas)
                    .addComponent(BContainerPaginaAnterior)
                    .addComponent(BContainerProxPagina))
                .addContainerGap())
        );

        Acervo.addTab("Containeres", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), Container); // NOI18N

        Principal.addTab("Acervo", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/acervo32.png")), Acervo); // NOI18N

        Usuarios.setOpaque(true);

        LBemVindoDoacoes3.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LBemVindoDoacoes3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBemVindoDoacoes3.setText("O que deseja fazer?");

        BAbrirCadastrarUsuario.setText("Cadastrar Usuário");
        BAbrirCadastrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirCadastrarUsuarioActionPerformed(evt);
            }
        });

        BExibirUsuarios.setText("Exibir Usuários");
        BExibirUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExibirUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuUsuariosLayout = new javax.swing.GroupLayout(MenuUsuarios);
        MenuUsuarios.setLayout(MenuUsuariosLayout);
        MenuUsuariosLayout.setHorizontalGroup(
            MenuUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBemVindoDoacoes3)
                    .addGroup(MenuUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BExibirUsuarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirCadastrarUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))
                .addContainerGap(764, Short.MAX_VALUE))
        );
        MenuUsuariosLayout.setVerticalGroup(
            MenuUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBemVindoDoacoes3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirCadastrarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BExibirUsuarios)
                .addContainerGap(345, Short.MAX_VALUE))
        );

        Usuarios.addTab("Menu", MenuUsuarios);

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
                .addContainerGap(585, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Usuarios.addTab("Cadastrar Usuário", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/plus16.png")), CadastrarUsuario); // NOI18N

        ExibirUsuarios.setBorder(null);

        TUsuarios.setAutoCreateRowSorter(true);
        TUsuarios.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TUsuarios.setRowHeight(30);
        ExibirUsuarios.setViewportView(TUsuarios);

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

        jLabel47.setText("Mostrar");

        SPUsuariosItensPagina.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
        SPUsuariosItensPagina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SPUsuariosItensPaginaStateChanged(evt);
            }
        });

        jLabel48.setText("itens por página.");

        jLabel49.setText("Página");

        LUsuariosPagina.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LUsuariosPagina.setText("1");

        jLabel50.setText("de");

        LUsuariosTotalPaginas.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LUsuariosTotalPaginas.setText("%TotalPaginas%");

        BUsuariosPaginaAnterior.setText("Página Anterior");
        BUsuariosPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUsuariosPaginaAnteriorActionPerformed(evt);
            }
        });

        BUsuariosProxPagina.setText("Próxima Página");
        BUsuariosProxPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUsuariosProxPaginaActionPerformed(evt);
            }
        });

        BFiltrarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/filtrar16.png"))); // NOI18N
        BFiltrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelUsuarioLayout = new javax.swing.GroupLayout(PainelUsuario);
        PainelUsuario.setLayout(PainelUsuarioLayout);
        PainelUsuarioLayout.setHorizontalGroup(
            PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPUsuariosItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel48)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LUsuariosPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LUsuariosTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BUsuariosPaginaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BUsuariosProxPagina))
                    .addComponent(ExibirUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BExcluirUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BEditarUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BFiltrarUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(BFiltrarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BExcluirUsuario)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ExibirUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(SPUsuariosItensPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel49)
                    .addComponent(LUsuariosPagina)
                    .addComponent(jLabel50)
                    .addComponent(LUsuariosTotalPaginas)
                    .addComponent(BUsuariosPaginaAnterior)
                    .addComponent(BUsuariosProxPagina))
                .addContainerGap())
        );

        Usuarios.addTab("Usuarios", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/list16.png")), PainelUsuario); // NOI18N

        Principal.addTab("Usuários", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/users.png")), Usuarios); // NOI18N

        LBemVindoDoacoes4.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        LBemVindoDoacoes4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBemVindoDoacoes4.setText("O que deseja fazer?");

        BAbrirEditarInformacoes.setText("Editar suas Informações");
        BAbrirEditarInformacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirEditarInformacoesActionPerformed(evt);
            }
        });

        BAbrirDeslogar.setText("Deslogar");
        BAbrirDeslogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAbrirDeslogarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuAbaUsuarioLayout = new javax.swing.GroupLayout(MenuAbaUsuario);
        MenuAbaUsuario.setLayout(MenuAbaUsuarioLayout);
        MenuAbaUsuarioLayout.setHorizontalGroup(
            MenuAbaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuAbaUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuAbaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBemVindoDoacoes4)
                    .addGroup(MenuAbaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BAbrirDeslogar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BAbrirEditarInformacoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(764, Short.MAX_VALUE))
        );
        MenuAbaUsuarioLayout.setVerticalGroup(
            MenuAbaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuAbaUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBemVindoDoacoes4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirEditarInformacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAbrirDeslogar)
                .addContainerGap(345, Short.MAX_VALUE))
        );

        AbaDoUsuario.addTab("Menu", MenuAbaUsuario);

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
                .addContainerGap(481, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(741, Short.MAX_VALUE))
        );
        DeslogarLayout.setVerticalGroup(
            DeslogarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeslogarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LCertezaDeslogar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BDeslogar)
                .addContainerGap(389, Short.MAX_VALUE))
        );

        AbaDoUsuario.addTab("Deslogar", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/logout.png")), Deslogar); // NOI18N

        Principal.addTab("AbaDoUsuario", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/userlittle.png")), AbaDoUsuario); // NOI18N

        MenuAjuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/help32.png"))); // NOI18N
        MenuAjuda.setText("Ajuda");

        AbrirManual.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        AbrirManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/help32.png"))); // NOI18N
        AbrirManual.setText("Abrir Manual");
        AbrirManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirManualActionPerformed(evt);
            }
        });
        MenuAjuda.add(AbrirManual);

        AbrirManualEspecifico.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        AbrirManualEspecifico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/help32.png"))); // NOI18N
        AbrirManualEspecifico.setText("Abrir Manual para Tela Atual");
        AbrirManualEspecifico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirManualEspecificoActionPerformed(evt);
            }
        });
        MenuAjuda.add(AbrirManualEspecifico);

        BarraMenu.add(MenuAjuda);

        MenuOpcoes.setText("Opções");

        AbrirCodigoFonte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/github.png"))); // NOI18N
        AbrirCodigoFonte.setText("Ver Código Fonte");
        AbrirCodigoFonte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirCodigoFonteActionPerformed(evt);
            }
        });
        MenuOpcoes.add(AbrirCodigoFonte);

        BarraMenu.add(MenuOpcoes);

        setJMenuBar(BarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Principal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Principal, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void BCadastrarDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarDoadorActionPerformed
        // TODO add your handling code here:

        Doador d = new Doador();

        try {
            d.setNome_doador(campoNomeDoador.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(CadastrarDoacao, "Campos de preenchimento obrigatório estão vazios.\nPreencha e tente novamente.", "Erro", 0);
        }
        DoadorDAO daod = new DoadorDAO();
        int sucesso = daod.inserir(d);
        daod.fechar();
        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarDoador, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_doador) from doador;");
            b.fechar();
            atualizarCBDoador();
            atualizarTBDoador();
            JDCadastrarDoador.dispose();
            JOptionPane.showMessageDialog(JDCadastrarDoador, "Código de Doador: " + sucesso, "Doador Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBDoadorDoacao.setSelectedIndex((CBDoadorDoacao.getItemCount() - 1));
            CBDoadorItemAcervo.setSelectedIndex((CBDoadorDoacao.getItemCount() - 1));
        }

    }//GEN-LAST:event_BCadastrarDoadorActionPerformed
    private void BCadastrarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarDoacaoActionPerformed
        // TODO add your handling code here:

        Doacao d = new Doacao();
        Doador doador;
        Evento_origem eo;

        d.setCod_usuario(codigoUsuario);

        DoadorDAO daodoador = new DoadorDAO();
        doador = daodoador.getByNome(CBDoadorDoacao.getSelectedItem().toString());
        daodoador.fechar();
        d.setCod_doador(doador.getCod_doador());

        Evento_origemDAO daoeo = new Evento_origemDAO();
        eo = daoeo.getByNome(CBEventoOrigem.getSelectedItem().toString());
        daoeo.fechar();
        d.setCod_evento_origem(eo.getCod_evento_origem());

        DoacaoDAO daod = new DoacaoDAO();
        int sucesso = daod.inserir(d);
        daod.fechar();
        if (sucesso == 0) {
            JOptionPane.showMessageDialog(CadastrarDoacao, "Falha na inserção.", "Erro", 0);
        } else {

            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_doacao) from doacao;");
            b.fechar();
            lista = (DefaultTableModel) TAdicionarItemDoacao.getModel();
            int items = lista.getRowCount();
            for (int i = 0; i < items; i++) {
                CadastrarItemDoacao(sucesso, lista.getValueAt(i, 1).toString(), Integer.parseInt(lista.getValueAt(i, 2).toString()));
            }
            lista.setRowCount(0);
            JOptionPane.showMessageDialog(CadastrarDoacao, "Código de Doação: " + sucesso, "Doação Registrada.", JOptionPane.INFORMATION_MESSAGE);
            achandoMax = true;
            atualizarTBDoacao();
            achandoMax = false;
            atualizarTBDoacao();
        }

        atualizarCamposCadastrarDoacao();
    }//GEN-LAST:event_BCadastrarDoacaoActionPerformed
    private void CadastrarItemDoacao(int numeroDoacao, String tipo, int quantidade) {
        Item_doacao itemd = new Item_doacao();
        Doacao doaaux = new Doacao();
        Tipo_item t;

        int sucesso = 0;
        try {
            DoacaoDAO daodoaaux = new DoacaoDAO();
            doaaux = daodoaaux.getByCod(numeroDoacao);
            daodoaaux.fechar();
            itemd.setCod_doacao(numeroDoacao);
            itemd.setQuantidade_item_doacao(quantidade);
            Tipo_itemDAO daot = new Tipo_itemDAO();
            t = daot.getByNome(tipo);
            daot.fechar();
            if (t == null) {
                JOptionPane.showMessageDialog(CadastrarDoacao, "Tipo Inexistente.", "Erro", 0);
            } else {
                itemd.setCod_tipo(t.getCod_tipo());
            }
            Item_doacaoDAO daoitemd = new Item_doacaoDAO();
            sucesso = daoitemd.inserir(itemd);
            daoitemd.fechar();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(CadastrarDoacao, "Campos Incorretos.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (sucesso == 0) {
                JOptionPane.showMessageDialog(CadastrarDoacao, "Falha na inserção.", "Erro", 0);
            } else {
                achandoMax = true;
                atualizarTBItemDoacao();
                atualizarTBEstoque();
                achandoMax = false;
                atualizarTBItemDoacao();
                atualizarTBEstoque();
            }

        }
    }
    private void BCadastrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarUsuarioActionPerformed

        Usuario u = new Usuario();
        u.setNome_usuario(campoNomeNovoUsuario.getText());
        u.setUsuario_administrador(CBAdministrador.isSelected());
        u.setChave_encriptacao(UUID.randomUUID().toString().substring(0, 16));
        u.setEmail(campoEmailCadastrarUsuario.getText());
        u.setRegistro_academico(campoRegistroAcademicoCadastrarUsuario.getText());

        String senha = new String(campoSenhaCadastrarUsuario.getPassword());
        String repetirsenha = new String(campoRepetirSenhaCadastrarUsuario.getPassword());

        if (u.getNome_usuario().equals("") || u.getEmail().equals("") || u.getRegistro_academico().equals("") || senha.equals("")) {
            JOptionPane.showMessageDialog(CadastrarUsuario, "Campos obrigatórios vazios", "Erro", 0);
        } else if (!senha.equals(repetirsenha)) {
            JOptionPane.showMessageDialog(CadastrarUsuario, "Senhas diferem.", "Erro", 0);
        } else {
            Encryptor aes = new Encryptor();
            String encriptada = aes.encrypt(u.getChave_encriptacao(), senha);
            u.setSenha_usuario(encriptada);

            UsuarioDAO daou = new UsuarioDAO();
            int sucesso = daou.inserir(u);
            daou.fechar();
            if (sucesso == 0) {
                JOptionPane.showMessageDialog(CadastrarUsuario, "Falha na Inserção.", "Erro", 0);
            } else {
                Banco b = new Banco();
                sucesso = b.max("SELECT MAX(cod_usuario) from usuario;");
                b.fechar();
                JOptionPane.showMessageDialog(CadastrarUsuario, "Código de Usuário: " + sucesso, "Usuário Registrado.", JOptionPane.INFORMATION_MESSAGE);
                atualizarTBUsuario();
                atualizarCBUsuario();
            }
        }
    }//GEN-LAST:event_BCadastrarUsuarioActionPerformed
    private void BLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLogarActionPerformed

        Usuario u;
        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByNome(CBUsuarioLogin.getSelectedItem().toString());
        daou.fechar();
        String senha = new String(campoSenhaLogin.getPassword());

        Encryptor aes = new Encryptor();
        String encriptada = aes.encrypt(u.getChave_encriptacao(), senha);

        if (u != null && u.getSenha_usuario().equals(encriptada)) {
            campoSenhaLogin.setText("");
            logado = true;
            nomeUsuario = u.getNome_usuario();
            senhaUsuario = u.getSenha_usuario();
            administrador = u.isUsuario_administrador();
            codigoUsuario = u.getCod_usuario();

            this.remove(AbaDoUsuario);
            Principal.addTab(nomeUsuario, new javax.swing.ImageIcon(getClass().getResource("/view/imagens/userlittle.png")), AbaDoUsuario);

            atualizarTB();
            achandoMax = false;
            atualizarTB();
            atualizarCB();
            atualizarCampos();
            JDLogin.setModal(false);
            JDLogin.setVisible(false);
            JDLogin.dispose();

            if (!administrador) {
                Principal.remove(Usuarios);
                campoNomeAlterarUsuario.setEditable(false);
                campoRegistroAcademicoAlterarUsuario.setEditable(false);
                campoNomeAlterarUsuario.setEnabled(false);
                campoRegistroAcademicoAlterarUsuario.setEnabled(false);

            } else {
                Principal.addTab("Usuários", new javax.swing.ImageIcon(getClass().getResource("/view/imagens/users.png")), Usuarios); // NOI18N
                campoNomeAlterarUsuario.setEditable(true);
                campoRegistroAcademicoAlterarUsuario.setEditable(true);
                campoNomeAlterarUsuario.setEnabled(true);
                campoRegistroAcademicoAlterarUsuario.setEnabled(true);
            }
            Principal.repaint();

            this.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(JDLogin, "Usuário ou Senha Incorretos.\nTente Novamente.", "Erro de Autenticação", 0);
        }
    }//GEN-LAST:event_BLogarActionPerformed
    private void BCadastrarColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarColetorActionPerformed
        // TODO add your handling code here:
        Coletor c = new Coletor();
        Tipo_coletor tc;

        c.setNome(campoNomeColetor.getText());
        Tipo_coletorDAO daotc = new Tipo_coletorDAO();
        tc = daotc.getByNome(CBTipoColetor.getSelectedItem().toString());
        daotc.fechar();
        c.setCod_tipo_coletor(tc.getCod_tipo_coletor());
        ColetorDAO daoc = new ColetorDAO();
        int sucesso = daoc.inserir(c);
        daoc.fechar();
        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarColetor, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_coletor) from coletor");
            b.fechar();
            atualizarTBColetor();
            atualizarCBColetor();
            JDCadastrarColetor.dispose();
            JOptionPane.showMessageDialog(JDCadastrarColetor, "Código de Coletor: " + sucesso, "Coletor Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBColetor.setSelectedIndex((CBColetor.getItemCount() - 1));

        }
        atualizarCamposCadastrarColetor();

    }//GEN-LAST:event_BCadastrarColetorActionPerformed
    private void CadastrarItemRepasse(int numeroRepasse, String tipo, int quantidade) {
        Item_repasse itemd = new Item_repasse();
        Repasse doaaux;
        Tipo_item t;

        RepasseDAO daor = new RepasseDAO();
        doaaux = daor.getByCod(numeroRepasse);
        daor.fechar();
        if (doaaux == null) {
            JOptionPane.showMessageDialog(CadastrarRepasse, "", "Erro", 0);
        } else {

            itemd.setCod_repasse(numeroRepasse);
            itemd.setQuantidade_item_repasse(quantidade);
            Tipo_itemDAO daot = new Tipo_itemDAO();
            t = daot.getByNome(tipo);
            daot.fechar();
            itemd.setCod_tipo(t.getCod_tipo());
            Item_repasseDAO daoitemd = new Item_repasseDAO();
            int sucesso = daoitemd.inserir(itemd);
            daoitemd.fechar();
            if (sucesso == 0) {
                JOptionPane.showMessageDialog(CadastrarRepasse, "Falha no cadastro do item.\nVerifique se ha unidades disponiveis em Estoque.", "Erro", 0);
            } else {
                atualizarTBItemRepasse();
                atualizarTBEstoque();
            }
        }

    }
    private void BCadastrarRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarRepasseActionPerformed
        // TODO add your handling code here:

        Repasse r = new Repasse();
        Destinacao d;
        Coletor coletor;

        ColetorDAO daocoletor = new ColetorDAO();
        coletor = daocoletor.getByNome(CBColetor.getSelectedItem().toString());
        daocoletor.fechar();

        DestinacaoDAO daod = new DestinacaoDAO();
        d = daod.getByNome(CBDestinacao.getSelectedItem().toString());
        daod.fechar();

        r.setCod_destinacao(d.getCod_destinacao());
        r.setCod_coletor(coletor.getCod_coletor());
        r.setCod_usuario(codigoUsuario);

        RepasseDAO daor = new RepasseDAO();
        int sucesso = daor.inserir(r);
        daor.fechar();
        if (sucesso == 0) {
            JOptionPane.showMessageDialog(CadastrarRepasse, "Falha na Inserção.", "Erro", 0);
        } else {

            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_repasse) from repasse;");
            b.fechar();
            lista = (DefaultTableModel) TAdicionarItemRepasse.getModel();
            int items = lista.getRowCount();
            for (int i = 0; i < items; i++) {
                CadastrarItemRepasse(sucesso, lista.getValueAt(i, 1).toString(), Integer.parseInt(lista.getValueAt(i, 2).toString()));
            }
            lista.setRowCount(0);
            JOptionPane.showMessageDialog(CadastrarRepasse, "Código de Repasse: " + sucesso, "Repasse Registrado.", JOptionPane.INFORMATION_MESSAGE);
            atualizarTBRepasse();
        }

        atualizarCamposCadastrarRepasse();
    }//GEN-LAST:event_BCadastrarRepasseActionPerformed
    private void BEsqueciSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEsqueciSenhaActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(JDLogin, "Contate o Administrador", "Acesso perdido", 0);
    }//GEN-LAST:event_BEsqueciSenhaActionPerformed
    private void JDLoginWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_JDLoginWindowClosed
        if (!logado) {
            this.setVisible(false);
            this.dispose();
        }
    }//GEN-LAST:event_JDLoginWindowClosed
    private void BCadastrarItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarItemAcervoActionPerformed
        // TODO add your handling code here:

        Item_acervo ia = new Item_acervo();
        Doador d;
        Tipo_item t;
        Marca ma;
        Modelo mo;
        Interface i;
        Tecnologia tec;
        Container c;
        Imagem img = new Imagem();

        ia.setCod_usuario(codigoUsuario);

        try {
            ia.setDescricao(campoDescricaoItemAcervo.getText());
            ia.setAno(Integer.parseInt(campoAnoItemAcervo.getText()));
            ia.setFunciona(CBFunciona.isSelected());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(CadastrarItemAcervo, "Campos obrigatórios em branco.\nPreencha-os e tente novamente.", "Erro", 0);
        }

        if (!campoCapacidadeItemAcervo.getText().equals("")) {
            ia.setCapacidade(Integer.parseInt(campoCapacidadeItemAcervo.getText()));
        }

        try {
            DoadorDAO daod = new DoadorDAO();
            d = daod.getByNome(CBDoadorItemAcervo.getSelectedItem().toString());
            daod.fechar();

            Tipo_itemDAO daot = new Tipo_itemDAO();
            t = daot.getByNome(CBTipoItemAcervo.getSelectedItem().toString());
            daot.fechar();

            MarcaDAO daoma = new MarcaDAO();
            ma = daoma.getByNome(CBMarca.getSelectedItem().toString());
            daoma.fechar();

            ModeloDAO daomo = new ModeloDAO();
            mo = daomo.getByNome(CBModelo.getSelectedItem().toString());
            daomo.fechar();

            InterfaceDAO daoi = new InterfaceDAO();
            i = daoi.getByNome(CBInterface.getSelectedItem().toString());
            daoi.fechar();

            TecnologiaDAO daotec = new TecnologiaDAO();
            tec = daotec.getByNome(CBTecnologia.getSelectedItem().toString());
            daotec.fechar();

            ContainerDAO daoc = new ContainerDAO();
            if (campoCodContainerCadastrarItemAcervo.getText().equals("")) {
                c = daoc.getByCod(1);
            } else {
                c = daoc.getByCod(Integer.parseInt(campoCodContainerCadastrarItemAcervo.getText()));
            }
            daoc.fechar();

            ia.setCod_doador(d.getCod_doador());
            ia.setCod_tipo(t.getCod_tipo());
            ia.setCod_marca(ma.getCod_marca());
            ia.setCod_modelo(mo.getCod_modelo());
            ia.setCod_interface(i.getCod_interface());
            ia.setCod_tecnologia(tec.getCod_tecnologia());
            ia.setCod_container(c.getCod_container());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(CadastrarItemAcervo, "O sistema não pode localizar algum dos itens selecionados.\nTente novamente.", "Erro", 0);
        } finally {
            Item_acervoDAO daoia = new Item_acervoDAO();
            int sucesso = daoia.inserir(ia);
            daoia.fechar();
            if (sucesso == 0) {
                JOptionPane.showMessageDialog(CadastrarItemAcervo, "Falha na Inserção.", "Erro", 0);
            } else {
                Banco b = new Banco();
                sucesso = b.max("SELECT MAX(cod_item_acervo) from item_acervo;");
                b.fechar();
                JOptionPane.showMessageDialog(CadastrarItemAcervo, "Código de Item-Acervo: " + sucesso, "Item-Acervo Registrado.", JOptionPane.INFORMATION_MESSAGE);

                atualizarTBAcervo();
            }
            atualizarCamposCadastrarItemAcervo();

        }

    }//GEN-LAST:event_BCadastrarItemAcervoActionPerformed
    private void BCadastrarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarTipoActionPerformed
        // TODO add your handling code here:

        Tipo_item t = new Tipo_item();

        int sucesso = 0;
        if (!campoTipoItem.getText().equals("")) {
            t.setNome(campoTipoItem.getText());
            Tipo_itemDAO daot = new Tipo_itemDAO();
            sucesso = daot.inserir(t);
            daot.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarTipoItem, "Campo Nome Vazio", "Erro", 0);
        }
        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarTipoItem, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_tipo) from tipo_item;");
            b.fechar();
            atualizarCBTipoItem();
            JDCadastrarTipoItem.dispose();
            JOptionPane.showMessageDialog(JDCadastrarTipoItem, "Código de Tipo: " + sucesso, "Tipo Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBTipoItemAcervo.setSelectedIndex((CBTipoItemAcervo.getItemCount() - 1));

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

        Marca m = new Marca();

        int sucesso = 0;
        if (!campoNomeMarca.getText().equals("")) {
            m.setNome(campoNomeMarca.getText());
            MarcaDAO daom = new MarcaDAO();
            sucesso = daom.inserir(m);
            daom.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarMarca, "Campo Nome Vazio", "Erro", 0);
        }

        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarMarca, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_marca) from marca;");
            b.fechar();
            atualizarCBMarca();
            JDCadastrarMarca.dispose();
            JOptionPane.showMessageDialog(JDCadastrarMarca, "Código de Marca: " + sucesso, "Marca Registrada.", JOptionPane.INFORMATION_MESSAGE);
            CBMarca.setSelectedIndex((CBMarca.getItemCount() - 1));
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

        Modelo m = new Modelo();

        int sucesso = 0;
        if (!campoNomeModelo.getText().equals("")) {
            m.setNome(campoNomeModelo.getText());
            ModeloDAO daom = new ModeloDAO();
            sucesso = daom.inserir(m);
            daom.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarModelo, "Campo Nome Vazio", "Erro", 0);
        }

        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarModelo, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_modelo) from modelo;");
            b.fechar();
            atualizarCBModelo();
            JDCadastrarModelo.dispose();
            JOptionPane.showMessageDialog(JDCadastrarModelo, "Código de Modelo: " + sucesso, "Modelo Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBModelo.setSelectedIndex((CBModelo.getItemCount() - 1));
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

        Interface i = new Interface();

        int sucesso = 0;
        if (!campoNomeInterface.getText().equals("")) {
            i.setNome(campoNomeInterface.getText());
            InterfaceDAO daoi = new InterfaceDAO();
            sucesso = daoi.inserir(i);
            daoi.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarInterface, "Campo Nome Vazio", "Erro", 0);
        }

        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarInterface, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_interface) from interface;");
            b.fechar();
            atualizarCBInterface();
            JDCadastrarInterface.dispose();
            JOptionPane.showMessageDialog(JDCadastrarInterface, "Código de Interface: " + sucesso, "Interface Registrada.", JOptionPane.INFORMATION_MESSAGE);
            CBInterface.setSelectedIndex((CBInterface.getItemCount() - 1));
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

        Tecnologia t = new Tecnologia();

        int sucesso = 0;
        if (!campoNomeTecnologia.getText().equals("")) {
            t.setNome(campoNomeTecnologia.getText());
            TecnologiaDAO daot = new TecnologiaDAO();
            sucesso = daot.inserir(t);
            daot.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarTecnologia, "Campo Nome Vazio", "Erro", 0);
        }

        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarTecnologia, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_tecnologia) from tecnologia;");
            b.fechar();
            atualizarCBTecnologia();
            JDCadastrarTecnologia.dispose();
            JOptionPane.showMessageDialog(JDCadastrarTecnologia, "Código de Tecnologia: " + sucesso, "Tecnologia Registrada.", JOptionPane.INFORMATION_MESSAGE);
            CBTecnologia.setSelectedIndex((CBTecnologia.getItemCount() - 1));
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
        UsuarioDAO daou = new UsuarioDAO();
        Usuario u = new Usuario();
        u = daou.getByCod(codigoUsuario);

        u.setCod_usuario(codigoUsuario);
        u.setNome_usuario(campoNomeAlterarUsuario.getText());
        u.setEmail(campoEmailAlterarUsuario.getText());
        u.setRegistro_academico(campoRegistroAcademicoAlterarUsuario.getText());
        u.setUsuario_administrador(administrador);

        String senhaatual = new String(campoSenhaAtualAlterarUsuario.getPassword());
        String novasenha = new String(campoNovaSenhaAlterarUsuario.getPassword());
        String repetirnovasenha = new String(campoRepetirSenhaAlterarUsuario.getPassword());

        boolean procedermudanca;
        boolean alterandosenha = !senhaatual.equals("");
        Encryptor aes = new Encryptor();

        if (!alterandosenha) {
            procedermudanca = true;
            if (!novasenha.equals("") || !repetirnovasenha.equals("")) {
                JOptionPane.showMessageDialog(SuasInformacoes, "Para alterar a senha, digite a senha atual.", "Erro", 0);
                procedermudanca = false;
            }
        } else {
            senhaatual = aes.encrypt(u.getChave_encriptacao(), senhaatual);
            procedermudanca = (senhaatual.equals(u.getSenha_usuario()));
            if (novasenha.equals("")) {
                procedermudanca = false;
            }
        }

        if (!novasenha.equals(repetirnovasenha)
                || !procedermudanca
                || novasenha.equals("")
                || u.getNome_usuario().equals("")
                || u.getEmail().equals("")
                || u.getRegistro_academico().equals("")) {
            JOptionPane.showMessageDialog(SuasInformacoes, "Senha atual errada, senhas novas nao coincidem ou campos vazios.\nVerifique os campos e tente novamente", "Erro", 0);
        } else {
            if (alterandosenha && procedermudanca) {
                u.setChave_encriptacao(UUID.randomUUID().toString().substring(0, 16));
                String encriptada = aes.encrypt(u.getChave_encriptacao(), novasenha);
                u.setSenha_usuario(encriptada);

            }

            boolean sucesso = daou.alterar(u);

            if (!sucesso) {
                JOptionPane.showMessageDialog(SuasInformacoes, "Erro na alteração.\nVerifique os campos e tente novamente", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(SuasInformacoes, "Informações Atualizadas", "Concluido", 1);
                nomeUsuario = u.getNome_usuario();
                senhaUsuario = u.getSenha_usuario();
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
    private void campoLinkKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLinkKeyTyped
        // TODO add your handling code here:
        if (campoLink.getText().equals(""));
        LFotoAcervo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png"))); // NOI18N
    }//GEN-LAST:event_campoLinkKeyTyped
    private void BCheckLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCheckLinkActionPerformed
        // TODO add your handling code here:
        BufferedImage image;
        int h, w, times;
        try {
            image = ImageIO.read(new URL(campoLink.getText()));
            h = image.getHeight();
            w = image.getWidth();
            if (w >= h) {
                times = w / LFotoAcervo.getWidth();
                w = w / (times + 1);
                h = h / (times + 1);
            } else {
                times = h / LFotoAcervo.getHeight();
                w = w / (times + 1);
                h = h / (times + 1);
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
        nomeUsuario = null;
        senhaUsuario = null;
        administrador = false;

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

        DoadorDAO daodonator = new DoadorDAO();
        donator = daodonator.getByNome(campoDoadorAlterarDoacao.getText());
        daodonator.fechar();

        Evento_origemDAO daoeo = new Evento_origemDAO();
        eo = daoeo.getByNome(CBEventoOrigemAlterarDoacao.getSelectedItem().toString());
        daoeo.fechar();

        DoacaoDAO daod = new DoacaoDAO();
        d = daod.getByCod(Integer.parseInt(campoDoacaoAlterarDoacao.getText()));

        d.setCod_evento_origem(eo.getCod_evento_origem());
        d.setCod_doador(donator.getCod_doador());

        boolean sucesso = daod.alterar(d);
        daod.fechar();
        if (sucesso) {
            JOptionPane.showMessageDialog(JDAlterarDoacao, "Alterações Salvas", "Sucesso", JOptionPane.PLAIN_MESSAGE);
            atualizarTBDoacao();
            JDAlterarDoacao.setVisible(false);
            JDAlterarDoacao.dispose();

        } else {
            JOptionPane.showMessageDialog(JDAlterarDoacao, "Alterações não foram Salvas", "Erro", 0);
        }

    }//GEN-LAST:event_BAlterarDoacaoActionPerformed
    private void BConfirmarExcluirDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirDoacaoActionPerformed
        // TODO add your handling code here:
        int cod_excluirdoacao = Integer.parseInt(campoDoacaoExcluirDoacao.getText());
        DoacaoDAO daodoa = new DoacaoDAO();
        boolean sucesso = daodoa.excluir(cod_excluirdoacao);
        daodoa.fechar();
        if (sucesso) {
            JOptionPane.showMessageDialog(JDExcluirDoacao, "Item excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);
            atualizarTBDoacao();
            atualizarTBItemDoacao();
            JDExcluirDoacao.setVisible(false);
            JDExcluirDoacao.dispose();
        } else {
            JOptionPane.showMessageDialog(JDExcluirDoacao, "Item não excluido.\nVerificar se há itens atrelados à Doação. Se houver, excluir todos os itens da mesma.", "Erro", 0);
        }

    }//GEN-LAST:event_BConfirmarExcluirDoacaoActionPerformed
    private void BCancelarExcluirDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCancelarExcluirDoacaoActionPerformed
        // TODO add your handling code here:
        JDExcluirDoacao.setVisible(false);
        JDExcluirDoacao.dispose();
    }//GEN-LAST:event_BCancelarExcluirDoacaoActionPerformed
    private void BCadastrarEventoOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarEventoOrigemActionPerformed
        // TODO add your handling code here:
        Evento_origem eo = new Evento_origem();

        int sucesso = 0;
        if (!campoNomeEventoOrigem.getText().equals("")) {
            eo.setNome_evento_origem(campoNomeEventoOrigem.getText());
            Evento_origemDAO daoeo = new Evento_origemDAO();
            sucesso = daoeo.inserir(eo);
            daoeo.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarEventoOrigem, "Campo Nome Vazio", "Erro", 0);
        }

        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarEventoOrigem, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_evento_origem) from evento_origem;");
            b.fechar();
            atualizarCBEventoOrigem();
            JDCadastrarEventoOrigem.dispose();
            JOptionPane.showMessageDialog(JDCadastrarEventoOrigem, "Código de Evento Origem: " + sucesso, "Evento origem Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBEventoOrigem.setSelectedIndex((CBEventoOrigem.getItemCount() - 1));
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

        Destinacao d = new Destinacao();

        int sucesso = 0;
        if (!campoNomeDestinacao.getText().equals("")) {
            d.setNome_destinacao(campoNomeDestinacao.getText());
            DestinacaoDAO daod = new DestinacaoDAO();
            sucesso = daod.inserir(d);
            daod.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarDestinacao, "Campo Nome Vazio", "Erro", 0);
        }

        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarDestinacao, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_destinacao) from destinacao;");
            b.fechar();
            atualizarCBDestinacao();
            JDCadastrarDestinacao.dispose();
            JOptionPane.showMessageDialog(JDCadastrarDestinacao, "Código de Destinação: " + sucesso, "Destinação Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBDestinacao.setSelectedIndex((CBDestinacao.getItemCount() - 1));
        }
    }//GEN-LAST:event_BCadastrarDestinacaoActionPerformed
    private void BCadastrarTipoColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarTipoColetorActionPerformed
        // TODO add your handling code here:

        Tipo_coletor tc = new Tipo_coletor();

        int sucesso = 0;
        if (!campoTipoColetor.getText().equals("")) {
            tc.setNome_tipo_coletor(campoTipoColetor.getText());
            Tipo_coletorDAO daod = new Tipo_coletorDAO();
            sucesso = daod.inserir(tc);
            daod.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarTipoColetor, "Campo Nome Vazio", "Erro", 0);
        }

        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarTipoColetor, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_tipo_coletor) from tipo_coletor;");
            b.fechar();
            atualizarCBTipoColetor();
            JDCadastrarTipoColetor.dispose();
            JDCadastrarColetor.pack();
            JOptionPane.showMessageDialog(JDCadastrarTipoColetor, "Código de Tipo Coletor: " + sucesso, "Tipo Coletor Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBTipoColetor.setSelectedIndex((CBTipoColetor.getItemCount() - 1));
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
        Tipo_container tc = new Tipo_container();

        int sucesso = 0;
        if (!campoTipoContainer.getText().equals("")) {
            tc.setNome_tipo_container(campoTipoContainer.getText());
            Tipo_containerDAO daod = new Tipo_containerDAO();
            sucesso = daod.inserir(tc);
            daod.fechar();
        } else {
            JOptionPane.showMessageDialog(JDCadastrarTipoContainer, "Campo Nome Vazio", "Erro", 0);
        }
        if (sucesso == 0) {
            JOptionPane.showMessageDialog(JDCadastrarTipoContainer, "Falha na Inserção.", "Erro", 0);
        } else {
            Banco b = new Banco();
            sucesso = b.max("SELECT MAX(cod_tipo_container) from tipo_container;");
            b.fechar();
            atualizarCBTipoContainer();
            JDCadastrarTipoContainer.dispose();

            JOptionPane.showMessageDialog(JDCadastrarTipoContainer, "Código de Tipo Container: " + sucesso, "Tipo Container Registrado.", JOptionPane.INFORMATION_MESSAGE);
            CBTipoContainerCadastrarContainer.setSelectedIndex((CBTipoContainerCadastrarContainer.getItemCount() - 1));
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
        JPBCarregando.setString("Progresso: " + JPBCarregando.getValue());
        atualizarCB();
        JPBCarregando.setValue(66);
        JPBCarregando.setString("Progresso: " + JPBCarregando.getValue());
        atualizarCampos();
        JPBCarregando.setValue(100);
        JPBCarregando.setString("Progresso: " + JPBCarregando.getValue());
        JPBCarregando.setString("Finalizado");
        JFCarregandoDados.setVisible(false);
        JFCarregandoDados.dispose();
        if (!administrador) {
            Usuarios.remove(CadastrarUsuario);
        }
        //         });
        //this.setVisible(true);
    }//GEN-LAST:event_JFCarregandoDadosWindowActivated

    private void BEditarItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarItemDoacaoActionPerformed
        // TODO add your handling code here:
        if (!TItemDoacao.getValueAt(TItemDoacao.getSelectedRow(), 2).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelItemDoacao, "Você nao possui permissão para editar esse item.", "Erro", 0);
            return;
        }
        int cod_alteraritemdoacao = Integer.parseInt(TItemDoacao.getValueAt(TItemDoacao.getSelectedRow(), 0).toString());
        Item_doacao item;
        Doacao d;
        Usuario u;
        Doador doa;
        Evento_origem eo;
        Tipo_item t;

        Item_doacaoDAO itemdd = new Item_doacaoDAO();
        item = itemdd.getByCod(cod_alteraritemdoacao);
        itemdd.fechar();

        DoacaoDAO daod = new DoacaoDAO();
        d = daod.getByCod(item.getCod_doacao());
        daod.fechar();

        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByCod(d.getCod_usuario());
        daou.fechar();

        DoadorDAO daodoa = new DoadorDAO();
        doa = daodoa.getByCod(d.getCod_doador());
        daodoa.fechar();

        Tipo_itemDAO tdao = new Tipo_itemDAO();
        t = tdao.getByCod(item.getCod_tipo());
        tdao.fechar();

        campoItemDoacaoAlterarItemDoacao.setText(item.getCod_item_doacao() + "");
        campoUsuarioAlterarItemDoacao.setText(u.getNome_usuario());
        campoDoadorAlterarItemDoacao.setText(doa.getNome_doador());

        CBTipoAlterarItemDoacao.setSelectedItem(t.getNome());
        campoQuantidadeAlterarItemDoacao.setText(item.getQuantidade_item_doacao() + "");

        JDAlterarItemDoacao.setLocationRelativeTo(null);
        JDAlterarItemDoacao.setIconImage(icone);
        //JDAlterarItemDoacao.
        JDAlterarItemDoacao.setVisible(true);
    }//GEN-LAST:event_BEditarItemDoacaoActionPerformed

    private void BAlterarItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarItemDoacaoActionPerformed
        // TODO add your handling code here:
        boolean sucesso = false;
        Item_doacao item = new Item_doacao();
        try {

            Item_doacaoDAO itemdd = new Item_doacaoDAO();
            item = itemdd.getByCod(Integer.parseInt(campoItemDoacaoAlterarItemDoacao.getText()));

            Tipo_item t;
            Tipo_itemDAO daot = new Tipo_itemDAO();
            t = daot.getByNome(CBTipoAlterarItemDoacao.getSelectedItem().toString());
            daot.fechar();

            item.setCod_tipo(t.getCod_tipo());
            item.setQuantidade_item_doacao(Integer.parseInt(campoQuantidadeAlterarItemDoacao.getText()));
            sucesso = itemdd.alterar(item);
            itemdd.fechar();

        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(PainelItemDoacao, "Campos Incorretos.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(PainelItemDoacao, "Falha na alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(PainelItemDoacao, "Código de Item-Doacao: " + item.getCod_tipo(), "Item-Doacao Alterado.", JOptionPane.INFORMATION_MESSAGE);
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
        boolean sucesso = false;
        Doador doador = new Doador();
        try {
            DoadorDAO daodoador = new DoadorDAO();
            doador = daodoador.getByCod(Integer.parseInt(campoCodigoDoadorAlterarDoador.getText()));
            doador.setNome_doador(campoNomeDoadorAlterarDoador.getText());
            sucesso = daodoador.alterar(doador);
            daodoador.fechar();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(PainelDoadores, "Campo Incorreto.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(PainelDoadores, "Falha na alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(PainelDoadores, "Código de Doador: " + doador.getCod_doador(), "Doador Alterado.", JOptionPane.INFORMATION_MESSAGE);
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
        int cod_alterardoador = Integer.parseInt(TDoador.getValueAt(TDoador.getSelectedRow(), 0).toString());

        DoadorDAO daodoador = new DoadorDAO();
        Doador doador = daodoador.getByCod(cod_alterardoador);
        daodoador.fechar();

        campoCodigoDoadorAlterarDoador.setText(doador.getCod_doador() + "");
        campoNomeDoadorAlterarDoador.setText(doador.getNome_doador());

        JDAlterarDoador.setLocationRelativeTo(null);
        JDAlterarDoador.setIconImage(icone);
        JDAlterarDoador.setVisible(true);
    }//GEN-LAST:event_BEditarDoadorActionPerformed

    private void BEditarRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarRepasseActionPerformed
        // TODO add your handling code here:
        if (!TRepasse.getValueAt(TRepasse.getSelectedRow(), 1).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelRepasse, "Você nao possui permissão para editar esse item.", "Erro", 0);
            return;
        }
        int cod_alterarrepasse = Integer.parseInt(TRepasse.getValueAt(TRepasse.getSelectedRow(), 0).toString());
        Repasse r;
        Usuario u;
        Coletor c;
        Destinacao d;

        RepasseDAO daor = new RepasseDAO();
        r = daor.getByCod(cod_alterarrepasse);
        daor.fechar();

        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByCod(r.getCod_usuario());
        daou.fechar();

        ColetorDAO daoc = new ColetorDAO();
        c = daoc.getByCod(r.getCod_coletor());
        daoc.fechar();

        DestinacaoDAO daod = new DestinacaoDAO();
        d = daod.getByCod(r.getCod_destinacao());
        daod.fechar();

        campoRepasseAlterarRepasse.setText(r.getCod_repasse() + "");
        campoUsuarioAlterarRepasse.setText(u.getNome_usuario());
        java.util.Date data = r.getData_repasse();
        campoDataAlterarRepasse.setText(data.getDate() + "/" + (data.getMonth() + 1) + "/" + (1900 + data.getYear()));
        campoColetorAlterarRepasse.setText(c.getNome());
        CBDestinacaoAlterarRepasse.setSelectedItem(d.getNome_destinacao());

        JDAlterarRepasse.setLocationRelativeTo(null);
        JDAlterarRepasse.setIconImage(icone);
        JDAlterarRepasse.setVisible(true);
    }//GEN-LAST:event_BEditarRepasseActionPerformed

    private void BAlterarRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarRepasseActionPerformed
        // TODO add your handling code here:
        boolean sucesso = false;
        Repasse r = new Repasse();
        try {
            RepasseDAO rdao = new RepasseDAO();
            r = rdao.getByCod(Integer.parseInt(campoRepasseAlterarRepasse.getText()));

            DestinacaoDAO daod = new DestinacaoDAO();
            Destinacao d = daod.getByNome(CBDestinacaoAlterarRepasse.getSelectedItem().toString());
            daod.fechar();

            r.setCod_destinacao(d.getCod_destinacao());
            sucesso = rdao.alterar(r);

        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(PainelRepasse, "Campos Incorretos.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(PainelRepasse, "Falha na alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(PainelRepasse, "Código de Item-Doacao: " + r.getCod_repasse(), "Repasse Alterado.", JOptionPane.INFORMATION_MESSAGE);
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
        boolean sucesso = false;
        Item_repasse item = new Item_repasse();
        try {

            Item_repasseDAO daoitem = new Item_repasseDAO();
            item = daoitem.getByCod(Integer.parseInt(campoItemRepasseAlterarItemRepasse.getText()));

            Tipo_item t = new Tipo_item();
            Tipo_itemDAO daot = new Tipo_itemDAO();
            t = daot.getByNome(CBTipoAlterarItemRepasse.getSelectedItem().toString());
            daot.fechar();

            item.setCod_tipo(t.getCod_tipo());
            item.setQuantidade_item_repasse(Integer.parseInt(campoQuantidadeAlterarItemRepasse.getText()));

            sucesso = daoitem.alterar(item);
            daoitem.fechar();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(PainelItemRepasse, "Campo Incorreto.\nVerifique e tente novamente.", "Erro", 0);

        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(PainelItemRepasse, "Falha na alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(PainelItemRepasse, "Código de Repasse: " + item.getCod_item_repasse(), "Item Repasse Alterado.", JOptionPane.INFORMATION_MESSAGE);
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
        if (!TItemRepasse.getValueAt(TItemRepasse.getSelectedRow(), 2).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelItemRepasse, "Você nao possui permissão para editar esse item.", "Erro", 0);
            return;
        }
        int cod_alteraritemrepasse = Integer.parseInt(TItemRepasse.getValueAt(TItemRepasse.getSelectedRow(), 0).toString());
        Repasse r;
        Item_repasse item;
        Usuario u;
        Coletor c;
        Tipo_item t;

        Item_repasseDAO daoitem = new Item_repasseDAO();
        item = daoitem.getByCod(cod_alteraritemrepasse);
        daoitem.fechar();

        RepasseDAO daor = new RepasseDAO();
        r = daor.getByCod(item.getCod_repasse());
        daor.fechar();

        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByCod(r.getCod_usuario());
        daou.fechar();

        ColetorDAO daoc = new ColetorDAO();
        c = daoc.getByCod(r.getCod_coletor());
        daoc.fechar();

        Tipo_itemDAO titem = new Tipo_itemDAO();
        t = titem.getByCod(item.getCod_tipo());
        titem.fechar();

        campoItemRepasseAlterarItemRepasse.setText(r.getCod_repasse() + "");
        campoUsuarioAlterarItemRepasse.setText(u.getNome_usuario());
        campoColetorAlterarItemRepasse.setText(c.getNome());
        CBTipoAlterarItemRepasse.setSelectedItem(t.getNome());
        campoQuantidadeAlterarItemRepasse.setText(item.getQuantidade_item_repasse() + "");

        JDAlterarItemRepasse.setLocationRelativeTo(null);
        JDAlterarItemRepasse.setIconImage(icone);
        JDAlterarItemRepasse.setVisible(true);
    }//GEN-LAST:event_BEditarItemRepasseActionPerformed

    private void BAlterarColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarColetorActionPerformed
        // TODO add your handling code here:
        boolean sucesso = false;
        Coletor coletor = new Coletor();
        try {
            ColetorDAO daocoletor = new ColetorDAO();
            coletor = daocoletor.getByCod(Integer.parseInt(campoCodigoColetorAlterarColetor.getText()));

            Tipo_coletor t;
            Tipo_coletorDAO daot = new Tipo_coletorDAO();
            t = daot.getByNome(CBTipoColetorAlterarColetor.getSelectedItem().toString());
            daot.fechar();

            coletor.setNome(campoNomeColetorAlterarColetor.getText());
            coletor.setCod_tipo_coletor(t.getCod_tipo_coletor());

            sucesso = daocoletor.alterar(coletor);
            daocoletor.fechar();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(PainelColetores, "Campo Incorreto.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(PainelColetores, "Falha na alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(PainelColetores, "Código de Coletor: " + coletor.getCod_coletor(), "Coletor Alterado.", JOptionPane.INFORMATION_MESSAGE);
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
        int cod_alterarcoletor = Integer.parseInt(TColetor.getValueAt(TColetor.getSelectedRow(), 0).toString());

        ColetorDAO daocoletor = new ColetorDAO();
        Coletor coletor = daocoletor.getByCod(cod_alterarcoletor);
        daocoletor.fechar();

        Tipo_coletor tipo;
        Tipo_coletorDAO daotipo = new Tipo_coletorDAO();
        tipo = daotipo.getByCod(coletor.getCod_tipo_coletor());
        daotipo.fechar();

        campoCodigoColetorAlterarColetor.setText(coletor.getCod_coletor() + "");
        campoNomeColetorAlterarColetor.setText(coletor.getNome());
        CBTipoColetorAlterarColetor.setSelectedItem(tipo.getNome_tipo_coletor());

        JDAlterarColetor.setLocationRelativeTo(null);
        JDAlterarColetor.setIconImage(icone);
        JDAlterarColetor.setVisible(true);
    }//GEN-LAST:event_BEditarColetorActionPerformed

    private void BEditarItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarItemAcervoActionPerformed
        // TODO add your handling code here:
        if (!TAcervo.getValueAt(TAcervo.getSelectedRow(), 1).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelAcervo, "Você nao possui permissão para editar esse item.", "Erro", 0);
            return;
        }
        Item_acervoDAO daoitem = new Item_acervoDAO();
        int coditem = Integer.parseInt(TAcervo.getValueAt(TAcervo.getSelectedRow(), 0).toString());
        Item_acervo item = daoitem.getByCod(coditem);
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

        UsuarioDAO usuariodao = new UsuarioDAO();
        usuario = usuariodao.getByCod(item.getCod_usuario());
        usuariodao.fechar();

        DoadorDAO doadordao = new DoadorDAO();
        doador = doadordao.getByCod(item.getCod_doador());
        doadordao.fechar();

        Tipo_itemDAO tipodao = new Tipo_itemDAO();
        tipo = tipodao.getByCod(item.getCod_tipo());
        tipodao.fechar();

        MarcaDAO marcadao = new MarcaDAO();
        marca = marcadao.getByCod(item.getCod_marca());
        marcadao.fechar();

        ModeloDAO modelodao = new ModeloDAO();
        modelo = modelodao.getByCod(item.getCod_modelo());
        modelodao.fechar();

        InterfaceDAO ifacedao = new InterfaceDAO();
        iface = ifacedao.getByCod(item.getCod_interface());
        ifacedao.fechar();

        TecnologiaDAO tecnologiadao = new TecnologiaDAO();
        tecnologia = tecnologiadao.getByCod(item.getCod_tecnologia());
        tecnologiadao.fechar();

        ContainerDAO containerdao = new ContainerDAO();
        container = containerdao.getByCod(item.getCod_container());
        containerdao.fechar();

        campoItemAcervoAlterarItemAcervo.setText(item.getCod_item_acervo() + "");
        campoUsuarioAlterarItemAcervo.setText(usuario.getNome_usuario());
        campoDoadorAlterarItemAcervo.setText(doador.getNome_doador());
        Date data = item.getData_cadastro_item_acervo();
        campoDataAlterarItemAcervo.setText(data.getDate() + "/" + (data.getMonth() + 1) + "/" + (1900 + data.getYear()));
        CBTipoAlterarItemAcervo.setSelectedItem(tipo.getNome());
        CBMarcaAlterarItemAcervo.setSelectedItem(marca.getNome());
        CBModeloAlterarItemAcervo.setSelectedItem(modelo.getNome());
        CBInterfaceAlterarItemAcervo.setSelectedItem(iface.getNome());
        CBTecnologiaAlterarItemAcervo.setSelectedItem(tecnologia.getNome());
        campoCapacidadeAlterarItemAcervo.setText(item.getCapacidade() + "");
        campoAnoAlterarItemAcervo.setText(item.getAno() + "");
        campoContainerAlterarItemAcervo.setText(item.getCod_container() + "");
        TADescricaoAlterarItemAcervo.setText(item.getDescricao());
        CheckFuncionaAlterarItemAcervo.setSelected(item.isFunciona());

        JDAlterarItemAcervo.setLocationRelativeTo(null);
        JDAlterarItemAcervo.setIconImage(icone);
        JDAlterarItemAcervo.setVisible(true);


    }//GEN-LAST:event_BEditarItemAcervoActionPerformed

    private void BAlterarItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarItemAcervoActionPerformed
        // TODO add your handling code here:
        int coditem = Integer.parseInt(campoItemAcervoAlterarItemAcervo.getText());
        Item_acervoDAO daoitem = new Item_acervoDAO();
        boolean sucesso = false;
        try {
            Item_acervo item = daoitem.getByCod(coditem);

            Tipo_item tipo;
            Marca marca;
            Modelo modelo;
            Interface iface;
            Tecnologia tecnologia;
            Container container;

            Tipo_itemDAO tipodao = new Tipo_itemDAO();
            tipo = tipodao.getByNome(CBTipoAlterarItemAcervo.getSelectedItem().toString());
            tipodao.fechar();

            MarcaDAO marcadao = new MarcaDAO();
            marca = marcadao.getByNome(CBMarcaAlterarItemAcervo.getSelectedItem().toString());
            marcadao.fechar();

            ModeloDAO modelodao = new ModeloDAO();
            modelo = modelodao.getByNome(CBModeloAlterarItemAcervo.getSelectedItem().toString());
            modelodao.fechar();

            InterfaceDAO ifacedao = new InterfaceDAO();
            iface = ifacedao.getByNome(CBInterfaceAlterarItemAcervo.getSelectedItem().toString());
            ifacedao.fechar();

            TecnologiaDAO tecnologiadao = new TecnologiaDAO();
            tecnologia = tecnologiadao.getByNome(CBTecnologiaAlterarItemAcervo.getSelectedItem().toString());
            tecnologiadao.fechar();

            ContainerDAO containerdao = new ContainerDAO();
            container = containerdao.getByCod(Integer.parseInt(campoContainerAlterarItemAcervo.getText()));
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

            sucesso = daoitem.alterar(item);
            daoitem.fechar();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(PainelAcervo, "Campos Incorretos.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(PainelAcervo, "Falha na alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(PainelAcervo, "Código de Item Acervo: " + campoItemAcervoAlterarItemAcervo.getText(), "Item Acervo Alterado.", JOptionPane.INFORMATION_MESSAGE);
                atualizarTBAcervo();

                JDAlterarItemAcervo.setModal(false);
                JDAlterarItemAcervo.setVisible(false);
                JDAlterarItemAcervo.dispose();
            }
        }

    }//GEN-LAST:event_BAlterarItemAcervoActionPerformed

    private void BEditarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem = new Imagem();
        int codimagem = Integer.parseInt(TImagem.getValueAt(TImagem.getSelectedRow(), 0).toString());
        ImagemDAO imagemdao = new ImagemDAO();
        imagem = imagemdao.getByCod(codimagem);
        imagemdao.fechar();

        campoCodImagemAlterarImagem.setText(imagem.getCod_imagem() + "");
        campoCodItemAcervoAlterarImagem.setText(imagem.getCod_item_acervo() + "");
        campoLinkAlterarImagem.setText(imagem.getLink());

        JDAlterarImagem.setLocationRelativeTo(null);
        JDAlterarImagem.setIconImage(icone);
        JDAlterarImagem.setVisible(true);
        JDAlterarImagem.pack();

        validaImagemAlterarImagem();

    }//GEN-LAST:event_BEditarImagemActionPerformed

    private void BEditarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarContainerActionPerformed
        // TODO add your handling code here:
        Container container = new Container();
        int codcontainer = Integer.parseInt(TContainer.getValueAt(TContainer.getSelectedRow(), 0).toString());
        ContainerDAO containerdao = new ContainerDAO();
        container = containerdao.getByCod(codcontainer);
        containerdao.fechar();

        Tipo_container tipo = new Tipo_container();
        Tipo_containerDAO tipodao = new Tipo_containerDAO();
        tipo = tipodao.getByCod(container.getCod_tipo_container());
        tipodao.fechar();

        campoCodContainerAlterarContainer.setText(container.getCod_container() + "");
        campoLocalizacaoAlterarContainer.setText(container.getLocalizacao_container());
        CBTipoContainerAlterarContainer.setSelectedItem(tipo.getNome_tipo_container());

        JDAlterarContainer.setLocationRelativeTo(null);
        JDAlterarContainer.setIconImage(icone);
        JDAlterarContainer.setVisible(true);
        JDAlterarContainer.pack();

    }//GEN-LAST:event_BEditarContainerActionPerformed

    private void BCadastrarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem = new Imagem();
        int sucesso = 0;
        try {
            imagem.setCod_item_acervo(Integer.parseInt(campoItemAcervoCadastrarImagem.getText()));
            imagem.setLink(campoLink.getText());
            ImagemDAO imagemdao = new ImagemDAO();
            sucesso = imagemdao.inserir(imagem);
            imagemdao.fechar();

        } catch (HeadlessException | NumberFormatException e) {

        } finally {
            if (sucesso == 0) {
                JOptionPane.showMessageDialog(CadastrarImagem, "Falha na Inserção.", "Erro", 0);
            } else {
                Banco b = new Banco();
                sucesso = b.max("SELECT MAX(cod_imagem) from imagem;");
                b.fechar();
                atualizarTBImagem();
                JOptionPane.showMessageDialog(CadastrarImagem, "Código de Imagem: " + sucesso, "Imagem cadastrada.", JOptionPane.INFORMATION_MESSAGE);
                resetarImagem();
            }

        }

    }//GEN-LAST:event_BCadastrarImagemActionPerformed

    private void BCadastrarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarContainerActionPerformed
        // TODO add your handling code here:
        Container container = new Container();
        int sucesso = 0;
        try {
            Tipo_containerDAO tipodao = new Tipo_containerDAO();
            Tipo_container tipo = tipodao.getByNome(CBTipoContainerCadastrarContainer.getSelectedItem().toString());
            System.out.println(CBTipoContainerCadastrarContainer.getSelectedItem().toString());
            tipodao.fechar();

            container.setCod_tipo_container(tipo.getCod_tipo_container());
            container.setLocalizacao_container(campoLocalizacaoCadastrarContainer.getText());
            ContainerDAO containerdao = new ContainerDAO();
            sucesso = containerdao.inserir(container);
            containerdao.fechar();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(CadastrarContainer, "Falha na Inserção.", "Erro", 0);
        } finally {
            if (sucesso == 0) {
                JOptionPane.showMessageDialog(CadastrarContainer, "Falha na Inserção.", "Erro", 0);
            } else {
                Banco b = new Banco();
                sucesso = b.max("SELECT MAX(cod_container) from container;");
                b.fechar();
                atualizarTBContainer();
                atualizarCamposAcervo();
                JOptionPane.showMessageDialog(CadastrarContainer, "Código de Container: " + sucesso, "Container cadastrado.", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_BCadastrarContainerActionPerformed

    private void BAlterarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarImagemActionPerformed
        // TODO add your handling code here:
        Imagem imagem;
        boolean sucesso = false;
        try {
            ImagemDAO imagemdao = new ImagemDAO();
            imagem = imagemdao.getByCod(Integer.parseInt(campoCodImagemAlterarImagem.getText()));
            imagem.setLink(campoLinkAlterarImagem.getText());
            sucesso = imagemdao.alterar(imagem);
            imagemdao.fechar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(PainelAcervo, "Campos Incorretos.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(JDAlterarImagem, "Falha na Alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(JDAlterarImagem, "Código de Imagem: " + campoCodImagemAlterarImagem.getText(), "Imagem Alterada.", JOptionPane.INFORMATION_MESSAGE);
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
        if (campoLinkAlterarImagem.getText().equals(""));
        LImagemAlterarImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/fotoacervo.png")));
        JDAlterarImagem.pack();
    }//GEN-LAST:event_campoLinkAlterarImagemKeyTyped

    private void BAlterarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAlterarContainerActionPerformed
        // TODO add your handling code here:
        Container container;
        boolean sucesso = false;
        try {
            ContainerDAO containerdao = new ContainerDAO();
            container = containerdao.getByCod(Integer.parseInt(campoCodContainerAlterarContainer.getText()));

            Tipo_containerDAO tipodao = new Tipo_containerDAO();
            Tipo_container tipo = tipodao.getByNome(CBTipoContainerAlterarContainer.getSelectedItem().toString());
            tipodao.fechar();

            container.setLocalizacao_container(campoLocalizacaoAlterarContainer.getText());
            container.setCod_tipo_container(tipo.getCod_tipo_container());

            sucesso = containerdao.alterar(container);
            containerdao.fechar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(Container, "Campos Incorretos.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(JDAlterarContainer, "Falha na Alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(Container, "Código de Container: " + campoCodContainerAlterarContainer.getText(), "Container alterado.", JOptionPane.INFORMATION_MESSAGE);
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
        boolean sucesso = false;

        Item_doacaoDAO itemdao = new Item_doacaoDAO();
        item = itemdao.getByCod(Integer.parseInt(campoItemDoacaoExcluirItemDoacao.getText()));
        sucesso = itemdao.excluir(item.getCod_item_doacao());
        itemdao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirItemDoacao, "Item não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirItemDoacao, "Item excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        if (!TItemDoacao.getValueAt(TItemDoacao.getSelectedRow(), 2).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelItemDoacao, "Você nao possui permissão para excluir esse item.", "Erro", 0);
            return;
        }
        Item_doacao item;
        Item_doacaoDAO itemdao = new Item_doacaoDAO();
        item = itemdao.getByCod(Integer.parseInt(TItemDoacao.getValueAt(TItemDoacao.getSelectedRow(), 0).toString()));
        itemdao.fechar();
        campoItemDoacaoExcluirItemDoacao.setText(item.getCod_item_doacao() + "");

        JDExcluirItemDoacao.setLocationRelativeTo(null);
        JDExcluirItemDoacao.setIconImage(icone);
        JDExcluirItemDoacao.setVisible(true);
        JDExcluirItemDoacao.pack();

    }//GEN-LAST:event_BExcluirItemDoacaoActionPerformed

    private void BConfirmarExcluirDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirDoadorActionPerformed
        // TODO add your handling code here:
        Doador doador;
        DoadorDAO doadordao = new DoadorDAO();
        boolean sucesso = false;
        doador = doadordao.getByCod(Integer.parseInt(campoDoadorExcluirDoador.getText()));
        sucesso = doadordao.excluir(doador.getCod_doador());
        doadordao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirDoador, "Doador não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirDoador, "Doador excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        DoadorDAO doadordao = new DoadorDAO();
        doador = doadordao.getByCod(Integer.parseInt(TDoador.getValueAt(TDoador.getSelectedRow(), 0).toString()));
        doadordao.fechar();

        campoDoadorExcluirDoador.setText(doador.getCod_doador() + "");
        JDExcluirDoador.setLocationRelativeTo(null);
        JDExcluirDoador.setIconImage(icone);
        JDExcluirDoador.setVisible(true);
        JDExcluirDoador.pack();
    }//GEN-LAST:event_BExcluirDoadorActionPerformed

    private void BConfirmarExcluirRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirRepasseActionPerformed
        // TODO add your handling code here:
        Repasse repasse;
        RepasseDAO repassedao = new RepasseDAO();
        boolean sucesso = false;
        repasse = repassedao.getByCod(Integer.parseInt(campoRepasseExcluirRepasse.getText()));
        sucesso = repassedao.excluir(repasse.getCod_repasse());
        repassedao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirRepasse, "Repasse não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirRepasse, "Repasse excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        if (!TRepasse.getValueAt(TRepasse.getSelectedRow(), 1).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelRepasse, "Você nao possui permissão para excluir esse item.", "Erro", 0);
            return;
        }
        int cod_excluirrepasse = Integer.parseInt(TRepasse.getValueAt(TRepasse.getSelectedRow(), 0).toString());
        Repasse r;

        RepasseDAO daor = new RepasseDAO();
        r = daor.getByCod(cod_excluirrepasse);
        daor.fechar();

        campoRepasseExcluirRepasse.setText(r.getCod_repasse() + "");

        JDExcluirRepasse.setLocationRelativeTo(null);
        JDExcluirRepasse.setIconImage(icone);
        JDExcluirRepasse.setVisible(true);
    }//GEN-LAST:event_BExcluirRepasseActionPerformed

    private void BConfirmarExcluirItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirItemRepasseActionPerformed
        // TODO add your handling code here:
        Item_repasse item;
        Item_repasseDAO itemdao = new Item_repasseDAO();
        boolean sucesso = false;
        item = itemdao.getByCod(Integer.parseInt(campoItemRepasseExcluirItemRepasse.getText()));
        sucesso = itemdao.excluir(item.getCod_item_repasse());
        itemdao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirItemRepasse, "Item Repasse não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirItemRepasse, "Item Repasse excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        if (!TItemRepasse.getValueAt(TItemRepasse.getSelectedRow(), 2).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelItemRepasse, "Você nao possui permissão para excluir esse item.", "Erro", 0);
            return;
        }
        int cod_item = Integer.parseInt(TItemRepasse.getValueAt(TItemRepasse.getSelectedRow(), 0).toString());
        Item_repasse r;

        Item_repasseDAO daoitem = new Item_repasseDAO();
        r = daoitem.getByCod(cod_item);
        daoitem.fechar();

        campoItemRepasseExcluirItemRepasse.setText(r.getCod_item_repasse() + "");

        JDExcluirItemRepasse.setLocationRelativeTo(null);
        JDExcluirItemRepasse.setIconImage(icone);
        JDExcluirItemRepasse.setVisible(true);
    }//GEN-LAST:event_BExcluirItemRepasseActionPerformed

    private void BConfirmarExcluirColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirColetorActionPerformed
        // TODO add your handling code here:
        Coletor coletor;
        ColetorDAO coletordao = new ColetorDAO();
        boolean sucesso = false;
        coletor = coletordao.getByCod(Integer.parseInt(campoColetorExcluirColetor.getText()));
        sucesso = coletordao.excluir(coletor.getCod_coletor());
        coletordao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirColetor, "Coletor não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirColetor, "Coletor excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        ColetorDAO coletordao = new ColetorDAO();
        coletor = coletordao.getByCod(Integer.parseInt(TColetor.getValueAt(TColetor.getSelectedRow(), 0).toString()));
        coletordao.fechar();

        campoColetorExcluirColetor.setText(coletor.getCod_coletor() + "");
        JDExcluirColetor.setLocationRelativeTo(null);
        JDExcluirColetor.setIconImage(icone);
        JDExcluirColetor.setVisible(true);
        JDExcluirColetor.pack();
    }//GEN-LAST:event_BExcluirColetorActionPerformed

    private void BExcluirItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirItemAcervoActionPerformed
        // TODO add your handling code here:
        if (!TAcervo.getValueAt(TAcervo.getSelectedRow(), 1).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelAcervo, "Você nao possui permissão para excluir esse item.", "Erro", 0);
            return;
        }
        Item_acervo item_acervo;
        Item_acervoDAO item_acervodao = new Item_acervoDAO();
        item_acervo = item_acervodao.getByCod(Integer.parseInt(TAcervo.getValueAt(TAcervo.getSelectedRow(), 0).toString()));
        item_acervodao.fechar();

        campoItemAcervoExcluirItemAcervo.setText(item_acervo.getCod_item_acervo() + "");
        JDExcluirItemAcervo.setLocationRelativeTo(null);
        JDExcluirItemAcervo.setIconImage(icone);
        JDExcluirItemAcervo.setVisible(true);
        JDExcluirItemAcervo.pack();
    }//GEN-LAST:event_BExcluirItemAcervoActionPerformed

    private void BConfirmarExcluirItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirItemAcervoActionPerformed
        // TODO add your handling code here:
        Item_acervo item_acervo;
        Item_acervoDAO item_acervodao = new Item_acervoDAO();
        boolean sucesso = false;
        item_acervo = item_acervodao.getByCod(Integer.parseInt(campoItemAcervoExcluirItemAcervo.getText()));
        sucesso = item_acervodao.excluir(item_acervo.getCod_item_acervo());
        item_acervodao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirItemAcervo, "Item Acervo não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirItemAcervo, "Item Acervo excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        ImagemDAO imagemdao = new ImagemDAO();
        boolean sucesso = false;
        imagem = imagemdao.getByCod(Integer.parseInt(campoImagemExcluirImagem.getText()));
        sucesso = imagemdao.excluir(imagem.getCod_imagem());
        imagemdao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirImagem, "Imagem não excluida.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirImagem, "Imagem excluida", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        ImagemDAO imagemdao = new ImagemDAO();
        imagem = imagemdao.getByCod(Integer.parseInt(TImagem.getValueAt(TImagem.getSelectedRow(), 0).toString()));
        imagemdao.fechar();

        campoImagemExcluirImagem.setText(imagem.getCod_imagem() + "");
        JDExcluirImagem.setLocationRelativeTo(null);
        JDExcluirImagem.setIconImage(icone);
        JDExcluirImagem.setVisible(true);
        JDExcluirImagem.pack();
    }//GEN-LAST:event_BExcluirImagemActionPerformed

    private void BExcluirContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirContainerActionPerformed
        // TODO add your handling code here:
        Container container;
        ContainerDAO containerdao = new ContainerDAO();
        container = containerdao.getByCod(Integer.parseInt(TContainer.getValueAt(TContainer.getSelectedRow(), 0).toString()));
        containerdao.fechar();

        campoContainerExcluirContainer.setText(container.getCod_container() + "");
        JDExcluirContainer.setLocationRelativeTo(null);
        JDExcluirContainer.setIconImage(icone);
        JDExcluirContainer.setVisible(true);
        JDExcluirContainer.pack();
    }//GEN-LAST:event_BExcluirContainerActionPerformed

    private void BConfirmarExcluirContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConfirmarExcluirContainerActionPerformed
        // TODO add your handling code here:
        Container container;
        ContainerDAO containerdao = new ContainerDAO();
        boolean sucesso = false;
        container = containerdao.getByCod(Integer.parseInt(campoContainerExcluirContainer.getText()));
        sucesso = containerdao.excluir(container.getCod_container());
        containerdao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirContainer, "Container não excluida.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirContainer, "Container excluida", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
        Usuario u = new Usuario();
        int codusuario = Integer.parseInt(TUsuarios.getValueAt(TUsuarios.getSelectedRow(), 0).toString());
        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByCod(codusuario);
        daou.fechar();


    }//GEN-LAST:event_BExcluirUsuarioActionPerformed

    private void BEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarUsuarioActionPerformed
        // TODO add your handling code here:
        Usuario u = new Usuario();
        int codusuario = Integer.parseInt(TUsuarios.getValueAt(TUsuarios.getSelectedRow(), 0).toString());
        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByCod(codusuario);
        daou.fechar();

        campoCodigoUsuarioAlterarUsuarioJD.setText(u.getCod_usuario() + "");
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
        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByCod(Integer.parseInt(campoCodigoUsuarioAlterarUsuarioJD.getText()));
        boolean sucesso = false;

        try {
            String novasenha = new String(campoNovaSenhaAlterarUsuarioJD.getPassword());
            String repetirnovasenha = new String(campoRepetirNovaSenhaAlterarUsuarioJD.getPassword());
            if (novasenha.equals(repetirnovasenha) && !novasenha.equals("")) {
                u.setChave_encriptacao(UUID.randomUUID().toString().substring(0, 16));
                Encryptor aes = new Encryptor();
                u.setSenha_usuario(aes.encrypt(u.getChave_encriptacao(), novasenha));
            }
            u.setEmail(campoEmailAlterarUsuarioJD.getText());
            u.setNome_usuario(campoNomeUsuarioAlterarUsuarioJD.getText());
            u.setRegistro_academico(campoRegistroAcademicoAlterarUsuarioJD.getText());
            u.setUsuario_administrador(CheckAdministradorAlterarUsuarioJD.isSelected());
            sucesso = daou.alterar(u);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(Usuarios, "Campos Incorretos.\nVerifique e tente novamente.", "Erro", 0);
        } finally {
            if (!sucesso) {
                JOptionPane.showMessageDialog(JDAlterarUsuario, "Falha na Alteração.", "Erro", 0);
            } else {
                JOptionPane.showMessageDialog(Usuarios, "Código de Usuário: " + campoCodigoUsuarioAlterarUsuarioJD.getText(), "Usuario alterado.", JOptionPane.INFORMATION_MESSAGE);
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
        UsuarioDAO udao = new UsuarioDAO();
        boolean sucesso = false;
        u = udao.getByCod(Integer.parseInt(campoUsuarioExcluirUsuario.getText()));
        sucesso = udao.excluir(u.getCod_usuario());
        udao.fechar();
        if (!sucesso) {
            JOptionPane.showMessageDialog(JDExcluirUsuario, "Usuario não excluido.Verificar dependências.\nSe houver, excluir todos os itens da mesma.", "Erro", 0);
        } else {
            JOptionPane.showMessageDialog(JDExcluirUsuario, "Usuario excluido", "Sucesso", JOptionPane.PLAIN_MESSAGE);

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
    private void MostrarRelatorio(String consultaSQL, String formatoXml) {
        Banco b = new Banco();
        try {
            String currentPath = Principal.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            currentPath = currentPath.replace("SGACERVO.jar", "");
            formatoXml = currentPath + formatoXml;
            //JOptionPane.showMessageDialog(null, "SRC: "+formatoXml);
            JasperDesign jasperDesign = JRXmlLoader.load(formatoXml);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(consultaSQL);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, b.getConexao());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "ERRO CARREGAMENTO: " + formatoXml);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERRO URI: " + formatoXml);
        }

        b.fechar();
    }
    private void BRelatorioItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioItemDoacaoActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioItemDoacao.jrxml";
        String sql = SelecaoItemDoacao + FiltroItemDoacao;
        MostrarRelatorio(sql, form);
    }//GEN-LAST:event_BRelatorioItemDoacaoActionPerformed

    private void BRelatorioEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioEstoqueActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioEstoque.jrxml";
        String sql = SelecaoEstoque + FiltroEstoque;
        MostrarRelatorio(sql, form);
    }//GEN-LAST:event_BRelatorioEstoqueActionPerformed

    private void BRelatorioDoadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioDoadoresActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioDoador.jrxml";
        String sql = SelecaoDoador + FiltroDoador;
        MostrarRelatorio(sql, form);
    }//GEN-LAST:event_BRelatorioDoadoresActionPerformed

    private void BRelatorioRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioRepasseActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioRepasse.jrxml";
        String sql = SelecaoRepasse + FiltroRepasse;
        MostrarRelatorio(sql, form);
    }//GEN-LAST:event_BRelatorioRepasseActionPerformed

    private void BRelatorioItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioItemRepasseActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioItemRepasse.jrxml";
        String sql = SelecaoItemRepasse + FiltroItemRepasse;
        MostrarRelatorio(sql, form);
    }//GEN-LAST:event_BRelatorioItemRepasseActionPerformed

    private void BRelatorioColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioColetorActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioColetor.jrxml";
        String sql = SelecaoColetor + FiltroColetor;
        MostrarRelatorio(sql, form);
    }//GEN-LAST:event_BRelatorioColetorActionPerformed

    private void BRelatorioAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioAcervoActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioAcervo.jrxml";
        String sql = SelecaoAcervo + FiltroAcervo;
        MostrarRelatorio(sql, form);
    }//GEN-LAST:event_BRelatorioAcervoActionPerformed

    private void BAdicionarItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAdicionarItemDoacaoActionPerformed
        // TODO add your handling code here:
        lista = (DefaultTableModel) TAdicionarItemDoacao.getModel();
        JDAdicionarItemLista.setLocationRelativeTo(null);
        JDAdicionarItemLista.setIconImage(icone);
        JDAdicionarItemLista.setVisible(true);
        JDAdicionarItemLista.pack();
    }//GEN-LAST:event_BAdicionarItemDoacaoActionPerformed

    private void BAdicionarItemListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAdicionarItemListaActionPerformed
        // TODO add your handling code here:
        int linha = lista.getRowCount();
        lista.insertRow(linha, (Object[]) null);
        lista.setValueAt(linha + 1, linha, 0);
        lista.setValueAt(CBTipoAdicionarItemLista.getSelectedItem().toString(), linha, 1);
        lista.setValueAt(SPQuantidadeItemLista.getValue(), linha, 2);
        CBTipoAdicionarItemLista.setSelectedIndex(0);
        SPQuantidadeItemLista.setValue(1);

    }//GEN-LAST:event_BAdicionarItemListaActionPerformed

    private void BAdicionarItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAdicionarItemRepasseActionPerformed
        // TODO add your handling code here:
        lista = (DefaultTableModel) TAdicionarItemRepasse.getModel();
        JDAdicionarItemLista.setLocationRelativeTo(null);
        JDAdicionarItemLista.setIconImage(icone);
        JDAdicionarItemLista.setVisible(true);
        JDAdicionarItemLista.pack();
    }//GEN-LAST:event_BAdicionarItemRepasseActionPerformed

    private void campoSenhaLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoSenhaLoginActionPerformed
        // TODO add your handling code here:
        campoSenhaLogin.setText("");
    }//GEN-LAST:event_campoSenhaLoginActionPerformed

    private void campoSenhaLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoSenhaLoginFocusGained
        // TODO add your handling code here:
        campoSenhaLogin.setText("");
    }//GEN-LAST:event_campoSenhaLoginFocusGained

    private void BMenuCadastrarDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMenuCadastrarDoadorActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarDoador();
        JDCadastrarDoador.setIconImage(icone);
        JDCadastrarDoador.setModal(true);
        JDCadastrarDoador.setLocationRelativeTo(null);
        JDCadastrarDoador.pack();
        JDCadastrarDoador.setVisible(true);
    }//GEN-LAST:event_BMenuCadastrarDoadorActionPerformed

    private void BAbrirDoacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirDoacoesActionPerformed
        // TODO add your handling code here:
        Doacoes.setSelectedIndex(2);
    }//GEN-LAST:event_BAbrirDoacoesActionPerformed

    private void BAbrirCadastrarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirCadastrarDoacaoActionPerformed
        // TODO add your handling code here:
        Doacoes.setSelectedIndex(1);
    }//GEN-LAST:event_BAbrirCadastrarDoacaoActionPerformed

    private void BAbrirItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirItemDoacaoActionPerformed
        // TODO add your handling code here:
        Doacoes.setSelectedIndex(3);
    }//GEN-LAST:event_BAbrirItemDoacaoActionPerformed

    private void BAbrirEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirEstoqueActionPerformed
        // TODO add your handling code here:
        Doacoes.setSelectedIndex(4);
    }//GEN-LAST:event_BAbrirEstoqueActionPerformed

    private void BAbrirDoadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirDoadoresActionPerformed
        // TODO add your handling code here:
        Doacoes.setSelectedIndex(5);
    }//GEN-LAST:event_BAbrirDoadoresActionPerformed

    private void BAbrirCadastrarDoacao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirCadastrarDoacao1ActionPerformed
        // TODO add your handling code here:
        Repasses.setSelectedIndex(1);
    }//GEN-LAST:event_BAbrirCadastrarDoacao1ActionPerformed

    private void BMenuCadastrarColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMenuCadastrarColetorActionPerformed
        // TODO add your handling code here:
        atualizarCamposCadastrarColetor();
        JDCadastrarColetor.setIconImage(icone);
        JDCadastrarColetor.setModal(true);
        JDCadastrarColetor.setLocationRelativeTo(null);
        JDCadastrarColetor.pack();
        JDCadastrarColetor.setVisible(true);
    }//GEN-LAST:event_BMenuCadastrarColetorActionPerformed

    private void BAbrirRepassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirRepassesActionPerformed
        // TODO add your handling code here:
        Repasses.setSelectedIndex(2);
    }//GEN-LAST:event_BAbrirRepassesActionPerformed

    private void BAbrirItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirItemRepasseActionPerformed
        // TODO add your handling code here:
        Repasses.setSelectedIndex(3);
    }//GEN-LAST:event_BAbrirItemRepasseActionPerformed

    private void BExibirColetoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExibirColetoresActionPerformed
        // TODO add your handling code here:
        Repasses.setSelectedIndex(4);
    }//GEN-LAST:event_BExibirColetoresActionPerformed

    private void BMenuCadastrarItemAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMenuCadastrarItemAcervoActionPerformed
        // TODO add your handling code here:
        Acervo.setSelectedIndex(1);
    }//GEN-LAST:event_BMenuCadastrarItemAcervoActionPerformed

    private void PrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrincipalMouseClicked
        // TODO add your handling code here:
        Doacoes.setSelectedIndex(0);
        Repasses.setSelectedIndex(0);
        Acervo.setSelectedIndex(0);
        AbaDoUsuario.setSelectedIndex(0);
        Usuarios.setSelectedIndex(0);
    }//GEN-LAST:event_PrincipalMouseClicked

    private void BMenuCadastrarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMenuCadastrarImagemActionPerformed
        // TODO add your handling code here:
        Acervo.setSelectedIndex(2);
    }//GEN-LAST:event_BMenuCadastrarImagemActionPerformed

    private void BMenuCadastrarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMenuCadastrarContainerActionPerformed
        // TODO add your handling code here:
        Acervo.setSelectedIndex(3);
    }//GEN-LAST:event_BMenuCadastrarContainerActionPerformed

    private void BExibirAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExibirAcervoActionPerformed
        // TODO add your handling code here:
        Acervo.setSelectedIndex(4);
    }//GEN-LAST:event_BExibirAcervoActionPerformed

    private void BExibirImagensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExibirImagensActionPerformed
        // TODO add your handling code here:
        Acervo.setSelectedIndex(5);
    }//GEN-LAST:event_BExibirImagensActionPerformed

    private void BExibirContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExibirContainerActionPerformed
        // TODO add your handling code here:
        Acervo.setSelectedIndex(6);
    }//GEN-LAST:event_BExibirContainerActionPerformed

    private void BAbrirCadastrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirCadastrarUsuarioActionPerformed
        // TODO add your handling code here:
        Usuarios.setSelectedIndex(1);
    }//GEN-LAST:event_BAbrirCadastrarUsuarioActionPerformed

    private void BExibirUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExibirUsuariosActionPerformed
        // TODO add your handling code here:
        Usuarios.setSelectedIndex(2);
    }//GEN-LAST:event_BExibirUsuariosActionPerformed

    private void BAbrirEditarInformacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirEditarInformacoesActionPerformed
        // TODO add your handling code here:
        AbaDoUsuario.setSelectedIndex(1);
    }//GEN-LAST:event_BAbrirEditarInformacoesActionPerformed

    private void BAbrirDeslogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAbrirDeslogarActionPerformed
        // TODO add your handling code here:
        AbaDoUsuario.setSelectedIndex(2);
    }//GEN-LAST:event_BAbrirDeslogarActionPerformed

    private void BRelatorioItemDoacao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioItemDoacao1ActionPerformed
        // TODO add your handling code here:
        JDFiltrarItemDoacao.setIconImage(icone);
        JDFiltrarItemDoacao.setModal(true);
        JDFiltrarItemDoacao.setLocationRelativeTo(null);
        JDFiltrarItemDoacao.pack();
        JDFiltrarItemDoacao.setVisible(true);
    }//GEN-LAST:event_BRelatorioItemDoacao1ActionPerformed

    private void BFiltrarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarEstoqueActionPerformed
        // TODO add your handling code here:
        JDFiltrarEstoque.setIconImage(icone);
        JDFiltrarEstoque.setModal(true);
        JDFiltrarEstoque.setLocationRelativeTo(null);
        JDFiltrarEstoque.pack();
        JDFiltrarEstoque.setVisible(true);
    }//GEN-LAST:event_BFiltrarEstoqueActionPerformed

    private void BRelatorioDoadores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioDoadores1ActionPerformed
        // TODO add your handling code here:
        JDFiltrarDoador.setIconImage(icone);
        JDFiltrarDoador.setModal(true);
        JDFiltrarDoador.setLocationRelativeTo(null);
        JDFiltrarDoador.pack();
        JDFiltrarDoador.setVisible(true);
    }//GEN-LAST:event_BRelatorioDoadores1ActionPerformed

    private void BRemoverItemDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRemoverItemDoacaoActionPerformed
        // TODO add your handling code here:
        excluirLinhasTabela(TAdicionarItemDoacao);
    }//GEN-LAST:event_BRemoverItemDoacaoActionPerformed

    private void BRemoverItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRemoverItemRepasseActionPerformed
        // TODO add your handling code here:
        excluirLinhasTabela(TAdicionarItemRepasse);
    }//GEN-LAST:event_BRemoverItemRepasseActionPerformed

    private void CheckDoacaoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckDoacaoCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckDoacaoCodigoActionPerformed

    private void BDoacaoFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDoacaoFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroDoacao = " ";
        int checkedfilters = 0;
        if (CheckDoacaoCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckDoacaoUsuario.isSelected()) {
            checkedfilters++;
        }
        if (CheckDoacaoDoador.isSelected()) {
            checkedfilters++;
        }
        if (CheckDoacaoEvento.isSelected()) {
            checkedfilters++;
        }
        if (CheckDoacaoData.isSelected()) {
            checkedfilters++;
        }
        if (checkedfilters > 0) {
            FiltroDoacao += " WHERE ";
            if (CheckDoacaoCodigo.isSelected()) {
                int min = Integer.parseInt(SPDoacaoMin.getValue().toString());
                int max = Integer.parseInt(SPDoacaoMax.getValue().toString());
                FiltroDoacao += "\"Código de Doação\">=" + min + " AND \"Código de Doação\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroDoacao += " AND ";
                }
            }
            if (CheckDoacaoUsuario.isSelected()) {
                FiltroDoacao += "\"Usuário\"=\'" + CBDoacaoUsuario.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroDoacao += " AND ";
                }
            }
            if (CheckDoacaoDoador.isSelected()) {
                FiltroDoacao += "\"Doador\"=\'" + CBDoacaoDoador.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroDoacao += " AND ";
                }
            }
            if (CheckDoacaoEvento.isSelected()) {
                FiltroDoacao += "\"Evento de Origem\"=\'" + CBDoacaoEvento.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroDoacao += " AND ";
                }
            }
            if (CheckDoacaoData.isSelected()) {
                //"Data"::date > '2016-11-13'
                FiltroDoacao += "\"Data\"::date>=\'" + (JDCDoacaoMin.getDate().getYear() + 1900) + "-" + (JDCDoacaoMin.getDate().getMonth() + 1) + "-" + JDCDoacaoMin.getDate().getDate() + "\' AND ";
                FiltroDoacao += "\"Data\"::date<=\'" + (JDCDoacaoMax.getDate().getYear() + 1900) + "-" + (JDCDoacaoMax.getDate().getMonth() + 1) + "-" + JDCDoacaoMax.getDate().getDate() + "\' ";
                System.out.println(FiltroDoacao);
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroDoacao += " AND ";
                }
            }
        } else {
            FiltroDoacao = "";
        }

        achandoMax = true;
        atualizarTBDoacao();
        achandoMax = false;
        atualizarTBDoacao();
    }//GEN-LAST:event_BDoacaoFiltrarActionPerformed

    private void BItemDoacaoPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BItemDoacaoPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaItemDoacao > 1) {
            numeroPaginaItemDoacao--;
        }
        atualizarTBItemDoacao();
    }//GEN-LAST:event_BItemDoacaoPaginaAnteriorActionPerformed

    private void BItemDoacaoProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BItemDoacaoProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaItemDoacao < numeroMaxPaginaItemDoacao) {
            numeroPaginaItemDoacao++;
        }
        atualizarTBItemDoacao();
    }//GEN-LAST:event_BItemDoacaoProxPaginaActionPerformed

    private void BEstoquePaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEstoquePaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaEstoque > 1) {
            numeroPaginaEstoque--;
        }
        atualizarTBEstoque();
    }//GEN-LAST:event_BEstoquePaginaAnteriorActionPerformed

    private void BEstoqueProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEstoqueProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaEstoque < numeroMaxPaginaEstoque) {
            numeroPaginaEstoque++;
        }
        atualizarTBEstoque();
    }//GEN-LAST:event_BEstoqueProxPaginaActionPerformed

    private void BDoadorPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDoadorPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaDoador > 1) {
            numeroPaginaDoador--;
        }
        atualizarTBDoador();
    }//GEN-LAST:event_BDoadorPaginaAnteriorActionPerformed

    private void BDoadorProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDoadorProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaDoador < numeroMaxPaginaDoador) {
            numeroPaginaDoador++;
        }
        atualizarTBDoador();
    }//GEN-LAST:event_BDoadorProxPaginaActionPerformed

    private void BDoacaoPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDoacaoPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaDoacao > 1) {
            numeroPaginaDoacao--;
        }
        atualizarTBDoacao();
    }//GEN-LAST:event_BDoacaoPaginaAnteriorActionPerformed

    private void BDoacaoProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDoacaoProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaDoacao < numeroMaxPaginaDoacao) {
            numeroPaginaDoacao++;
        }
        atualizarTBDoacao();
    }//GEN-LAST:event_BDoacaoProxPaginaActionPerformed

    private void BFiltrarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarDoacaoActionPerformed
        // TODO add your handling code here:
        JDFiltrarDoacao.setIconImage(icone);
        JDFiltrarDoacao.setModal(true);
        JDFiltrarDoacao.setLocationRelativeTo(null);
        JDFiltrarDoacao.pack();
        JDFiltrarDoacao.setVisible(true);
    }//GEN-LAST:event_BFiltrarDoacaoActionPerformed

    private void BRelatorioDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRelatorioDoacaoActionPerformed
        // TODO add your handling code here:
        String form = "relatorios/relatorioDoacao.jrxml";
        String sql = SelecaoDoacao + FiltroDoacao;
        MostrarRelatorio(sql, form);

    }//GEN-LAST:event_BRelatorioDoacaoActionPerformed

    private void BEditarDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditarDoacaoActionPerformed

        if (!TDoacao.getValueAt(TDoacao.getSelectedRow(), 1).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelDoacoes, "Você nao possui permissão para editar esse item.", "Erro", 0);
            return;
        }
        int cod_alterardoacao = Integer.parseInt(TDoacao.getValueAt(TDoacao.getSelectedRow(), 0).toString());
        Doacao d;
        Usuario u;
        Doador doa;
        Evento_origem eo;

        DoacaoDAO daod = new DoacaoDAO();
        d = daod.getByCod(cod_alterardoacao);
        daod.fechar();

        UsuarioDAO daou = new UsuarioDAO();
        u = daou.getByCod(d.getCod_usuario());
        daou.fechar();

        DoadorDAO daodoa = new DoadorDAO();
        doa = daodoa.getByCod(d.getCod_doador());
        daodoa.fechar();

        Evento_origemDAO daoeo = new Evento_origemDAO();
        eo = daoeo.getByCod(d.getCod_evento_origem());
        daoeo.fechar();

        campoDoacaoAlterarDoacao.setText(d.getCod_doacao() + "");
        campoUsuarioAlterarDoacao.setText(u.getNome_usuario());
        java.util.Date data = d.getData_doacao();
        campoDataAlterarDoacao.setText(data.getDate() + "/" + (data.getMonth() + 1) + "/" + (1900 + data.getYear()));
        CBEventoOrigemAlterarDoacao.setSelectedItem(eo.getNome_evento_origem());
        campoDoadorAlterarDoacao.setText(doa.getNome_doador());

        JDAlterarDoacao.setLocationRelativeTo(null);
        JDAlterarDoacao.setIconImage(icone);
        JDAlterarDoacao.setVisible(true);

    }//GEN-LAST:event_BEditarDoacaoActionPerformed

    private void BExcluirDoacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExcluirDoacaoActionPerformed
        if (!TDoacao.getValueAt(TDoacao.getSelectedRow(), 1).toString().equals(nomeUsuario)) {
            JOptionPane.showMessageDialog(PainelDoacoes, "Você nao possui permissão para excluir esse item.", "Erro", 0);
            return;
        }
        int cod_alterardoacao = Integer.parseInt(TDoacao.getValueAt(TDoacao.getSelectedRow(), 0).toString());
        Doacao d;

        DoacaoDAO daod = new DoacaoDAO();
        d = daod.getByCod(cod_alterardoacao);
        daod.fechar();

        campoDoacaoExcluirDoacao.setText(d.getCod_doacao() + "");

        JDExcluirDoacao.setLocationRelativeTo(null);
        JDExcluirDoacao.setIconImage(icone);
        JDExcluirDoacao.setVisible(true);

    }//GEN-LAST:event_BExcluirDoacaoActionPerformed

    private void SPDoacaoItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPDoacaoItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBDoacao();
        achandoMax = false;
        atualizarTBDoacao();
    }//GEN-LAST:event_SPDoacaoItensPaginaStateChanged

    private void SPItemDoacaoItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPItemDoacaoItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBItemDoacao();
        achandoMax = false;
        atualizarTBItemDoacao();
    }//GEN-LAST:event_SPItemDoacaoItensPaginaStateChanged

    private void SPEstoqueItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPEstoqueItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBEstoque();
        achandoMax = false;
        atualizarTBEstoque();
    }//GEN-LAST:event_SPEstoqueItensPaginaStateChanged

    private void SPDoadorItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPDoadorItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBDoador();
        achandoMax = false;
        atualizarTBDoador();
    }//GEN-LAST:event_SPDoadorItensPaginaStateChanged

    private void SPRepasseItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPRepasseItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBRepasse();
        achandoMax = false;
        atualizarTBRepasse();
    }//GEN-LAST:event_SPRepasseItensPaginaStateChanged

    private void BRepassePaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRepassePaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaRepasse > 1) {
            numeroPaginaRepasse--;
        }
        atualizarTBRepasse();
    }//GEN-LAST:event_BRepassePaginaAnteriorActionPerformed

    private void BRepasseProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRepasseProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaRepasse < numeroMaxPaginaRepasse) {
            numeroPaginaRepasse++;
        }
        atualizarTBRepasse();
    }//GEN-LAST:event_BRepasseProxPaginaActionPerformed

    private void SPItemRepasseItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPItemRepasseItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBItemRepasse();
        achandoMax = false;
        atualizarTBItemRepasse();
    }//GEN-LAST:event_SPItemRepasseItensPaginaStateChanged

    private void BItemRepassePaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BItemRepassePaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaItemRepasse > 1) {
            numeroPaginaItemRepasse--;
        }
        atualizarTBItemRepasse();
    }//GEN-LAST:event_BItemRepassePaginaAnteriorActionPerformed

    private void BItemRepasseProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BItemRepasseProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaItemRepasse < numeroMaxPaginaItemRepasse) {
            numeroPaginaItemRepasse++;
        }
        atualizarTBItemRepasse();
    }//GEN-LAST:event_BItemRepasseProxPaginaActionPerformed

    private void SPColetorItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPColetorItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBColetor();
        achandoMax = false;
        atualizarTBColetor();
    }//GEN-LAST:event_SPColetorItensPaginaStateChanged

    private void BColetorPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BColetorPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaColetor > 1) {
            numeroPaginaColetor--;
        }
        atualizarTBColetor();
    }//GEN-LAST:event_BColetorPaginaAnteriorActionPerformed

    private void BColetorProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BColetorProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaColetor < numeroMaxPaginaColetor) {
            numeroPaginaColetor++;
        }
        atualizarTBColetor();
    }//GEN-LAST:event_BColetorProxPaginaActionPerformed

    private void BFiltrarRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarRepasseActionPerformed
        // TODO add your handling code here:
        JDFiltrarRepasse.setIconImage(icone);
        JDFiltrarRepasse.setModal(true);
        JDFiltrarRepasse.setLocationRelativeTo(null);
        JDFiltrarRepasse.pack();
        JDFiltrarRepasse.setVisible(true);
    }//GEN-LAST:event_BFiltrarRepasseActionPerformed

    private void BFiltrarItemRepasseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarItemRepasseActionPerformed
        // TODO add your handling code here:
        JDFiltrarItemRepasse.setIconImage(icone);
        JDFiltrarItemRepasse.setModal(true);
        JDFiltrarItemRepasse.setLocationRelativeTo(null);
        JDFiltrarItemRepasse.pack();
        JDFiltrarItemRepasse.setVisible(true);
    }//GEN-LAST:event_BFiltrarItemRepasseActionPerformed

    private void BFiltrarColetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarColetorActionPerformed
        // TODO add your handling code here:
        JDFiltrarColetor.setIconImage(icone);
        JDFiltrarColetor.setModal(true);
        JDFiltrarColetor.setLocationRelativeTo(null);
        JDFiltrarColetor.pack();
        JDFiltrarColetor.setVisible(true);
    }//GEN-LAST:event_BFiltrarColetorActionPerformed

    private void SPAcervoItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPAcervoItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBAcervo();
        achandoMax = false;
        atualizarTBAcervo();
    }//GEN-LAST:event_SPAcervoItensPaginaStateChanged

    private void BAcervoPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAcervoPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaAcervo > 1) {
            numeroPaginaAcervo--;
        }
        atualizarTBAcervo();
    }//GEN-LAST:event_BAcervoPaginaAnteriorActionPerformed

    private void BAcervoProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAcervoProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaAcervo < numeroMaxPaginaAcervo) {
            numeroPaginaAcervo++;
        }
        atualizarTBAcervo();
    }//GEN-LAST:event_BAcervoProxPaginaActionPerformed

    private void BFiltrarAcervoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarAcervoActionPerformed
        // TODO add your handling code here:
        JDFiltrarAcervo.setIconImage(icone);
        JDFiltrarAcervo.setModal(true);
        JDFiltrarAcervo.setLocationRelativeTo(null);
        JDFiltrarAcervo.pack();
        JDFiltrarAcervo.setVisible(true);
    }//GEN-LAST:event_BFiltrarAcervoActionPerformed

    private void SPImagemItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPImagemItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBImagem();
        achandoMax = false;
        atualizarTBImagem();
    }//GEN-LAST:event_SPImagemItensPaginaStateChanged

    private void BImagemPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BImagemPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaImagem > 1) {
            numeroPaginaImagem--;
        }
        atualizarTBImagem();
    }//GEN-LAST:event_BImagemPaginaAnteriorActionPerformed

    private void BImagemProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BImagemProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaImagem < numeroMaxPaginaImagem) {
            numeroPaginaImagem++;
        }
        atualizarTBImagem();
    }//GEN-LAST:event_BImagemProxPaginaActionPerformed

    private void SPContainerItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPContainerItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBContainer();
        achandoMax = false;
        atualizarTBContainer();
    }//GEN-LAST:event_SPContainerItensPaginaStateChanged

    private void BContainerPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BContainerPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaContainer > 1) {
            numeroPaginaContainer--;
        }
        atualizarTBContainer();
    }//GEN-LAST:event_BContainerPaginaAnteriorActionPerformed

    private void BContainerProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BContainerProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaContainer < numeroMaxPaginaContainer) {
            numeroPaginaContainer++;
        }
        atualizarTBContainer();
    }//GEN-LAST:event_BContainerProxPaginaActionPerformed

    private void SPUsuariosItensPaginaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SPUsuariosItensPaginaStateChanged
        // TODO add your handling code here:
        achandoMax = true;
        atualizarTBUsuario();
        achandoMax = false;
        atualizarTBUsuario();
    }//GEN-LAST:event_SPUsuariosItensPaginaStateChanged

    private void BUsuariosPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUsuariosPaginaAnteriorActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaUsuarios > 1) {
            numeroPaginaUsuarios--;
        }
        atualizarTBUsuario();
    }//GEN-LAST:event_BUsuariosPaginaAnteriorActionPerformed

    private void BUsuariosProxPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUsuariosProxPaginaActionPerformed
        // TODO add your handling code here:
        if (numeroPaginaUsuarios < numeroMaxPaginaUsuarios) {
            numeroPaginaUsuarios++;
        }
        atualizarTBUsuario();
    }//GEN-LAST:event_BUsuariosProxPaginaActionPerformed

    private void BItemDoacaoFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BItemDoacaoFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroItemDoacao = " ";
        int checkedfilters = 0;
        if (CheckItemDoacaoDoacaoCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemDoacaoCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemDoacaoUsuario.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemDoacaoDoador.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemDoacaoEvento.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemDoacaoData.isSelected()) {
            checkedfilters++;
        }
        if (checkedfilters > 0) {
            FiltroItemDoacao += " WHERE ";
            if (CheckItemDoacaoDoacaoCodigo.isSelected()) {
                int min = Integer.parseInt(SPItemDoacaoDoacaoMin.getValue().toString());
                int max = Integer.parseInt(SPItemDoacaoDoacaoMax.getValue().toString());
                FiltroItemDoacao += "\"Código de Doação\">=" + min + " AND \"Código de Doação\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemDoacao += " AND ";
                }
            }
            if (CheckItemDoacaoCodigo.isSelected()) {
                int min = Integer.parseInt(SPItemDoacaoMin.getValue().toString());
                int max = Integer.parseInt(SPItemDoacaoMax.getValue().toString());
                FiltroItemDoacao += "\"Código de Item-Doação\">=" + min + " AND \"Código de Item-Doação\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemDoacao += " AND ";
                }
            }
            if (CheckItemDoacaoUsuario.isSelected()) {
                FiltroItemDoacao += "\"Usuário\"=\'" + CBItemDoacaoUsuario.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemDoacao += " AND ";
                }
            }
            if (CheckItemDoacaoDoador.isSelected()) {
                FiltroItemDoacao += "\"Doador\"=\'" + CBItemDoacaoDoador.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemDoacao += " AND ";
                }
            }
            if (CheckItemDoacaoEvento.isSelected()) {
                FiltroItemDoacao += "\"Origem\"=\'" + CBItemDoacaoEvento.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemDoacao += " AND ";
                }
            }
            if (CheckItemDoacaoData.isSelected()) {
                //"Data"::date > '2016-11-13'
                FiltroItemDoacao += "\"Data\"::date>=\'" + (JDCItemDoacaoMin.getDate().getYear() + 1900) + "-" + (JDCItemDoacaoMin.getDate().getMonth() + 1) + "-" + JDCItemDoacaoMin.getDate().getDate() + "\' AND ";
                FiltroItemDoacao += "\"Data\"::date<=\'" + (JDCItemDoacaoMax.getDate().getYear() + 1900) + "-" + (JDCItemDoacaoMax.getDate().getMonth() + 1) + "-" + JDCItemDoacaoMax.getDate().getDate() + "\' ";
                System.out.println(FiltroItemDoacao);
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemDoacao += " AND ";
                }
            }
        } else {
            FiltroItemDoacao = "";
        }
        System.out.println(FiltroItemDoacao);
        achandoMax = true;
        atualizarTBItemDoacao();
        achandoMax = false;
        atualizarTBItemDoacao();
    }//GEN-LAST:event_BItemDoacaoFiltrarActionPerformed

    private void BEstoqueFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEstoqueFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroEstoque = " ";
        int checkedfilters = 0;
        if (CheckEstoqueTipo.isSelected()) {
            checkedfilters++;
        }
        if (CheckEstoqueDoacao.isSelected()) {
            checkedfilters++;
        }
        if (CheckEstoqueRepasse.isSelected()) {
            checkedfilters++;
        }
        if (CheckEstoqueSaldo.isSelected()) {
            checkedfilters++;
        }
        if (checkedfilters > 0) {
            FiltroEstoque += " WHERE ";
            if (CheckEstoqueTipo.isSelected()) {
                FiltroEstoque += "\"Tipo\"=\'" + CBEstoqueTipo.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroEstoque += " AND ";
                }
            }
            if (CheckEstoqueDoacao.isSelected()) {
                int min = Integer.parseInt(SPEstoqueDoacaoMin.getValue().toString());
                int max = Integer.parseInt(SPEstoqueDoacaoMax.getValue().toString());
                FiltroEstoque += "\"Quantidade de Doações\">=" + min + " AND \"Quantidade de Doações\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroEstoque += " AND ";
                }
            }
            if (CheckEstoqueRepasse.isSelected()) {
                int min = Integer.parseInt(SPEstoqueRepasseMin.getValue().toString());
                int max = Integer.parseInt(SPEstoqueRepasseMax.getValue().toString());
                FiltroEstoque += "\"Quantidade de Repasses\">=" + min + " AND \"Quantidade de Repasses\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroEstoque += " AND ";
                }
            }
            if (CheckEstoqueSaldo.isSelected()) {
                int min = Integer.parseInt(SPEstoqueSaldoMin.getValue().toString());
                int max = Integer.parseInt(SPEstoqueSaldoMax.getValue().toString());
                FiltroEstoque += "\"Saldo\">=" + min + " AND \"Saldo\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroEstoque += " AND ";
                }
            }

        } else {
            FiltroEstoque = "";
        }
        System.out.println(FiltroEstoque);
        achandoMax = true;
        atualizarTBEstoque();
        achandoMax = false;
        atualizarTBEstoque();
    }//GEN-LAST:event_BEstoqueFiltrarActionPerformed

    private void BDoadorFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDoadorFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroDoador = " ";
        int checkedfilters = 0;
        if (CheckDoadorCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckDoadorDoador.isSelected()) {
            checkedfilters++;
        }
        if (checkedfilters > 0) {
            FiltroDoador += " WHERE ";
            if (CheckDoadorDoador.isSelected()) {
                FiltroDoador += "\"Nome do Doador\"=\'" + CBDoadorDoador.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroDoador += " AND ";
                }
            }
            if (CheckDoadorCodigo.isSelected()) {
                int min = Integer.parseInt(SPDoadorCodigoMin.getValue().toString());
                int max = Integer.parseInt(SPDoadorCodigoMax.getValue().toString());
                FiltroDoador += "\"Código do Doador\">=" + min + " AND \"Código do Doador\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroDoador += " AND ";
                }
            }

        } else {
            FiltroDoador = "";
        }
        System.out.println(FiltroDoador);
        achandoMax = true;
        atualizarTBDoador();
        achandoMax = false;
        atualizarTBDoador();
    }//GEN-LAST:event_BDoadorFiltrarActionPerformed

    private void BRepasseFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRepasseFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroRepasse = " ";
        int checkedfilters = 0;
        if (CheckRepasseCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckRepasseUsuario.isSelected()) {
            checkedfilters++;
        }
        if (CheckRepasseColetor.isSelected()) {
            checkedfilters++;
        }
        if (CheckRepasseTipoColetor.isSelected()) {
            checkedfilters++;
        }
        if (CheckRepasseDestinacao.isSelected()) {
            checkedfilters++;
        }
        if (CheckRepasseData.isSelected()) {
            checkedfilters++;
        }
        if (checkedfilters > 0) {
            FiltroRepasse += " WHERE ";
            if (CheckRepasseCodigo.isSelected()) {
                int min = Integer.parseInt(SPRepasseMin.getValue().toString());
                int max = Integer.parseInt(SPRepasseMax.getValue().toString());
                FiltroRepasse += "\"Código do Repasse\">=" + min + " AND \"Código do Repasse\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroRepasse += " AND ";
                }
            }
            if (CheckRepasseUsuario.isSelected()) {
                FiltroRepasse += "\"Usuário\"=\'" + CBRepasseUsuario.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroRepasse += " AND ";
                }
            }
            if (CheckRepasseColetor.isSelected()) {
                FiltroRepasse += "\"Coletor\"=\'" + CBRepasseColetor.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroRepasse += " AND ";
                }
            }
            if (CheckRepasseTipoColetor.isSelected()) {
                FiltroRepasse += "\"Tipo de Coletor\"=\'" + CBRepasseTipoColetor.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroRepasse += " AND ";
                }
            }
            if (CheckRepasseDestinacao.isSelected()) {
                FiltroRepasse += "\"Destinação\"=\'" + CBRepasseDestinacao.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroRepasse += " AND ";
                }
            }
            if (CheckRepasseData.isSelected()) {
                //"Data"::date > '2016-11-13'
                FiltroRepasse += "\"Data\"::date>=\'" + (JDCRepasseMin.getDate().getYear() + 1900) + "-" + (JDCRepasseMin.getDate().getMonth() + 1) + "-" + JDCRepasseMin.getDate().getDate() + "\' AND ";
                FiltroRepasse += "\"Data\"::date<=\'" + (JDCRepasseMax.getDate().getYear() + 1900) + "-" + (JDCRepasseMax.getDate().getMonth() + 1) + "-" + JDCRepasseMax.getDate().getDate() + "\' ";
                System.out.println(FiltroRepasse);
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroRepasse += " AND ";
                }
            }
        } else {
            FiltroRepasse = "";
        }
        System.out.println(FiltroRepasse);
        achandoMax = true;
        atualizarTBRepasse();
        achandoMax = false;
        atualizarTBRepasse();
    }//GEN-LAST:event_BRepasseFiltrarActionPerformed

    private void BItemRepasseFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BItemRepasseFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroItemRepasse = " ";
        int checkedfilters = 0;
        if (CheckItemRepasseCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemRepasseRepasse.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemRepasseUsuario.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemRepasseColetor.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemRepasseTipo.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemRepasseDestinacao.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemRepasseQuantidade.isSelected()) {
            checkedfilters++;
        }
        if (CheckItemRepasseData.isSelected()) {
            checkedfilters++;
        }
        if (checkedfilters > 0) {
            FiltroItemRepasse += " WHERE ";
            if (CheckItemRepasseCodigo.isSelected()) {
                int min = Integer.parseInt(SPItemRepasseMin.getValue().toString());
                int max = Integer.parseInt(SPItemRepasseMax.getValue().toString());
                FiltroItemRepasse += "\"Código do Item-Repasse\">=" + min + " AND \"Código do Item-Repasse\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
            if (CheckItemRepasseRepasse.isSelected()) {
                int min = Integer.parseInt(SPItemRepasseRepasseMin.getValue().toString());
                int max = Integer.parseInt(SPItemRepasseRepasseMax.getValue().toString());
                FiltroItemRepasse += "\"Codigo do Repasse\">=" + min + " AND \"Codigo do Repasse\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
            if (CheckItemRepasseQuantidade.isSelected()) {
                int min = Integer.parseInt(SPItemRepasseQuantidadeMin.getValue().toString());
                int max = Integer.parseInt(SPItemRepasseQuantidadeMax.getValue().toString());
                FiltroItemRepasse += "\"Quantidade\">=" + min + " AND \"Quantidade\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
            if (CheckItemRepasseUsuario.isSelected()) {
                FiltroItemRepasse += "\"Usuário\"=\'" + CBItemRepasseUsuario.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
            if (CheckItemRepasseColetor.isSelected()) {
                FiltroItemRepasse += "\"Coletor\"=\'" + CBItemRepasseColetor.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
            if (CheckItemRepasseTipo.isSelected()) {
                FiltroItemRepasse += "\"Tipo de Item\"=\'" + CBItemRepasseTipo.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
            if (CheckItemRepasseDestinacao.isSelected()) {
                FiltroItemRepasse += "\"Destinação\"=\'" + CBItemRepasseDestinacao.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
            if (CheckItemRepasseData.isSelected()) {
                //"Data"::date > '2016-11-13'
                FiltroItemRepasse += "\"Data\"::date>=\'" + (JDCItemRepasseMin.getDate().getYear() + 1900) + "-" + (JDCItemRepasseMin.getDate().getMonth() + 1) + "-" + JDCItemRepasseMin.getDate().getDate() + "\' AND ";
                FiltroItemRepasse += "\"Data\"::date<=\'" + (JDCItemRepasseMax.getDate().getYear() + 1900) + "-" + (JDCItemRepasseMax.getDate().getMonth() + 1) + "-" + JDCItemRepasseMax.getDate().getDate() + "\' ";
                System.out.println(FiltroItemRepasse);
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroItemRepasse += " AND ";
                }
            }
        } else {
            FiltroItemRepasse = "";
        }
        System.out.println(FiltroItemRepasse);
        achandoMax = true;
        atualizarTBItemRepasse();
        achandoMax = false;
        atualizarTBItemRepasse();
    }//GEN-LAST:event_BItemRepasseFiltrarActionPerformed

    private void BColetorFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BColetorFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroColetor = " ";
        int checkedfilters = 0;
        if (CheckColetorCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckColetorColetor.isSelected()) {
            checkedfilters++;
        }
        if (CheckColetorTipo.isSelected()) {
            checkedfilters++;
        }

        if (checkedfilters > 0) {
            FiltroColetor += " WHERE ";
            if (CheckColetorCodigo.isSelected()) {
                int min = Integer.parseInt(SPColetorCodigoMin.getValue().toString());
                int max = Integer.parseInt(SPColetorCodigoMax.getValue().toString());
                FiltroColetor += "\"Código do Coletor\">=" + min + " AND \"Código do Coletor\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroColetor += " AND ";
                }
            }

            if (CheckColetorColetor.isSelected()) {
                FiltroColetor += "\"Nome do Coletor\"=\'" + CBColetorColetor.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroColetor += " AND ";
                }
            }
            if (CheckColetorTipo.isSelected()) {
                FiltroColetor += "\"Tipo do Coletor\"=\'" + CBColetorTipo.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroColetor += " AND ";
                }
            }

        } else {
            FiltroColetor = "";
        }
        System.out.println(FiltroColetor);
        achandoMax = true;
        atualizarTBColetor();
        achandoMax = false;
        atualizarTBColetor();
    }//GEN-LAST:event_BColetorFiltrarActionPerformed

    private void BAcervoFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAcervoFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroAcervo = " ";
        int checkedfilters = 0;
        if (CheckAcervoCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoUsuario.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoDoador.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoData.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoTipo.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoMarca.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoModelo.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoInterface.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoTecnologia.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoCapacidade.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoAno.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoFuncionamento.isSelected()) {
            checkedfilters++;
        }
        if (CheckAcervoContainer.isSelected()) {
            checkedfilters++;
        }

        if (checkedfilters > 0) {
            FiltroAcervo += " WHERE ";
            if (CheckAcervoCodigo.isSelected()) {
                int min = Integer.parseInt(SPAcervoCodigoMin.getValue().toString());
                int max = Integer.parseInt(SPAcervoCodigoMax.getValue().toString());
                FiltroAcervo += "\"Código de Item do Acervo\">=" + min + " AND \"Código de Item do Acervo\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoUsuario.isSelected()) {
                FiltroAcervo += "\"Usuário\"=\'" + CBAcervoUsuario.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoDoador.isSelected()) {
                FiltroAcervo += "\"Doador\"=\'" + CBAcervoDoador.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoData.isSelected()) {
                //"Data"::date > '2016-11-13'
                FiltroAcervo += "\"Data\"::date>=\'" + (JDCAcervoDataMin.getDate().getYear() + 1900) + "-" + (JDCAcervoDataMin.getDate().getMonth() + 1) + "-" + JDCAcervoDataMin.getDate().getDate() + "\' AND ";
                FiltroAcervo += "\"Data\"::date<=\'" + (JDCAcervoDataMax.getDate().getYear() + 1900) + "-" + (JDCAcervoDataMax.getDate().getMonth() + 1) + "-" + JDCAcervoDataMax.getDate().getDate() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoTipo.isSelected()) {
                FiltroAcervo += "\"Tipo\"=\'" + CBAcervoTipo.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoMarca.isSelected()) {
                FiltroAcervo += "\"Marca\"=\'" + CBAcervoMarca.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoModelo.isSelected()) {
                FiltroAcervo += "\"Modelo\"=\'" + CBAcervoModelo.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoInterface.isSelected()) {
                FiltroAcervo += "\"Interface\"=\'" + CBAcervoInterface.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoTecnologia.isSelected()) {
                FiltroAcervo += "\"Tecnologia\"=\'" + CBAcervoTecnologia.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoCapacidade.isSelected()) {
                int min = Integer.parseInt(SPAcervoCapacidadeMin.getValue().toString());
                int max = Integer.parseInt(SPAcervoCapacidadeMax.getValue().toString());
                FiltroAcervo += "\"Capacidade\">=" + min + " AND \"Capacidade\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoAno.isSelected()) {
                int min = Integer.parseInt(SPAcervoAnoMin.getValue().toString());
                int max = Integer.parseInt(SPAcervoAnoMax.getValue().toString());
                FiltroAcervo += "\"Ano\">=" + min + " AND \"Ano\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoFuncionamento.isSelected()) {
                if (CheckAcervoFunciona.isSelected()) {
                    FiltroAcervo += "\"Funciona\"='t'";
                } else {
                    FiltroAcervo += "\"Funciona\"='f'";
                }
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }
            if (CheckAcervoContainer.isSelected()) {
                int min = Integer.parseInt(SPAcervoContainerMin.getValue().toString());
                int max = Integer.parseInt(SPAcervoContainerMax.getValue().toString());
                FiltroAcervo += "\"Código de Container\">=" + min + " AND \"Código de Container\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroAcervo += " AND ";
                }
            }

        } else {
            FiltroAcervo = "";
        }
        System.out.println(FiltroAcervo);
        achandoMax = true;
        atualizarTBAcervo();
        achandoMax = false;
        atualizarTBAcervo();
    }//GEN-LAST:event_BAcervoFiltrarActionPerformed

    private void BImagemFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BImagemFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroImagem = " ";
        int checkedfilters = 0;
        if (CheckImagemCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckImagemItem.isSelected()) {
            checkedfilters++;
        }
        if (CheckImagemMarca.isSelected()) {
            checkedfilters++;
        }
        if (CheckImagemModelo.isSelected()) {
            checkedfilters++;
        }

        if (checkedfilters > 0) {
            FiltroImagem += " WHERE ";
            if (CheckImagemCodigo.isSelected()) {
                int min = Integer.parseInt(SPImagemCodigoMin.getValue().toString());
                int max = Integer.parseInt(SPImagemCodigoMax.getValue().toString());
                FiltroImagem += "\"Código de Imagem\">=" + min + " AND \"Código de Imagem\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroImagem += " AND ";
                }
            }
            if (CheckImagemItem.isSelected()) {
                int min = Integer.parseInt(SPImagemItemMin.getValue().toString());
                int max = Integer.parseInt(SPImagemItemMax.getValue().toString());
                FiltroImagem += "\"Código de Item Acervo\">=" + min + " AND \"Código de Item Acervo\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroImagem += " AND ";
                }
            }

            if (CheckImagemMarca.isSelected()) {
                FiltroImagem += "\"Marca\"=\'" + CBImagemMarca.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroImagem += " AND ";
                }
            }
            if (CheckImagemModelo.isSelected()) {
                FiltroImagem += "\"Modelo\"=\'" + CBImagemModelo.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroImagem += " AND ";
                }
            }

        } else {
            FiltroImagem = "";
        }
        System.out.println(FiltroImagem);
        achandoMax = true;
        atualizarTBImagem();
        achandoMax = false;
        atualizarTBImagem();
    }//GEN-LAST:event_BImagemFiltrarActionPerformed

    private void BFiltrarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarImagemActionPerformed
        // TODO add your handling code here:
        JDFiltrarImagem.setIconImage(icone);
        JDFiltrarImagem.setModal(true);
        JDFiltrarImagem.setLocationRelativeTo(null);
        JDFiltrarImagem.pack();
        JDFiltrarImagem.setVisible(true);
    }//GEN-LAST:event_BFiltrarImagemActionPerformed

    private void BFiltrarContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarContainerActionPerformed
        // TODO add your handling code here:
        JDFiltrarContainer.setIconImage(icone);
        JDFiltrarContainer.setModal(true);
        JDFiltrarContainer.setLocationRelativeTo(null);
        JDFiltrarContainer.pack();
        JDFiltrarContainer.setVisible(true);
    }//GEN-LAST:event_BFiltrarContainerActionPerformed

    private void BContainerFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BContainerFiltrarActionPerformed
        // TODO add your handling code here:
        FiltroContainer = " ";
        int checkedfilters = 0;
        if (CheckContainerCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckContainerLocalizacao.isSelected()) {
            checkedfilters++;
        }
        if (CheckContainerTipo.isSelected()) {
            checkedfilters++;
        }

        if (checkedfilters > 0) {
            FiltroContainer += " WHERE ";
            if (CheckContainerCodigo.isSelected()) {
                int min = Integer.parseInt(SPContainerCodigoMin.getValue().toString());
                int max = Integer.parseInt(SPContainerCodigoMax.getValue().toString());
                FiltroContainer += "\"Código do Container\">=" + min + " AND \"Código do Container\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroContainer += " AND ";
                }
            }

            if (CheckContainerLocalizacao.isSelected()) {
                FiltroContainer += "\"Localização do Container\"=\'" + CBContainerLocalizacao.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroContainer += " AND ";
                }
            }
            if (CheckContainerTipo.isSelected()) {
                FiltroContainer += "\"Tipo do Container\"=\'" + CBContainerTipo.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroContainer += " AND ";
                }
            }

        } else {
            FiltroContainer = "";
        }
        System.out.println(FiltroContainer);
        achandoMax = true;
        atualizarTBContainer();
        achandoMax = false;
        atualizarTBContainer();
    }//GEN-LAST:event_BContainerFiltrarActionPerformed

    private void BFiltrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarUsuarioActionPerformed
        // TODO add your handling code here:
        JDFiltrarUsuario.setIconImage(icone);
        JDFiltrarUsuario.setModal(true);
        JDFiltrarUsuario.setLocationRelativeTo(null);
        JDFiltrarUsuario.pack();
        JDFiltrarUsuario.setVisible(true);
    }//GEN-LAST:event_BFiltrarUsuarioActionPerformed

    private void BContainerFiltrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BContainerFiltrar1ActionPerformed
        // TODO add your handling code here:
        FiltroUsuarios = " ";
        int checkedfilters = 0;
        if (CheckUsuarioCodigo.isSelected()) {
            checkedfilters++;
        }
        if (CheckUsuarioNome.isSelected()) {
            checkedfilters++;
        }
        if (CheckUsuarioPermissao.isSelected()) {
            checkedfilters++;
        }

        if (checkedfilters > 0) {
            FiltroUsuarios += " WHERE ";
            if (CheckUsuarioCodigo.isSelected()) {
                int min = Integer.parseInt(SPUsuarioCodigoMin.getValue().toString());
                int max = Integer.parseInt(SPUsuarioCodigoMax.getValue().toString());
                FiltroUsuarios += "\"Código de Usuário\">=" + min + " AND \"Código de Usuário\"<=" + max + " ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroUsuarios += " AND ";
                }
            }

            if (CheckUsuarioNome.isSelected()) {
                FiltroUsuarios += "\"Nome do Usuário\"=\'" + CBUsuarioNome.getSelectedItem().toString() + "\' ";
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroUsuarios += " AND ";
                }
            }
            if (CheckUsuarioPermissao.isSelected()) {
                if (CheckUsuarioAdministrador.isSelected()) {
                    FiltroUsuarios += "\"Administrador\"=\'t\'";
                } else {
                    FiltroUsuarios += "\"Administrador\"=\'f\'";
                }
                checkedfilters--;
                if (checkedfilters > 0) {
                    FiltroUsuarios += " AND ";
                }
            }

        } else {
            FiltroUsuarios = "";
        }
        System.out.println(FiltroUsuarios);
        achandoMax = true;
        atualizarTBUsuario();
        achandoMax = false;
        atualizarTBUsuario();
    }//GEN-LAST:event_BContainerFiltrar1ActionPerformed

    private void abrirManual(String secao) {
        try {
            URI uri;
            //String currentPath = Principal.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            //currentPath = currentPath.replace("SGACERVO.jar", "");
            //currentPath += secao;
            //uri = new URI("file://" + currentPath);
            
            uri = new URI("http://htmlpreview.github.io/?https://github.com/roscoche/sgacervo/blob/master/"+secao);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void PrincipalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrincipalKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            abrirManualEspecifico();
        }

    }//GEN-LAST:event_PrincipalKeyPressed

    private void abrirManualEspecifico() {
        String secao;
        switch (Principal.getSelectedIndex()) {
            case 0:
                switch (Doacoes.getSelectedIndex()) {
                    case 0:
                        secao = "Manual.html#doacoes_menu";
                        abrirManual(secao);
                        break;
                    case 1:
                        secao = "Manual.html#doacoes_cadastrar_doacao";
                        abrirManual(secao);
                        break;
                    case 2:
                        secao = "Manual.html#doacoes_doacoes";
                        abrirManual(secao);
                        break;
                    case 3:
                        secao = "Manual.html#doacoes_item_doacoes";
                        abrirManual(secao);
                        break;
                    case 4:
                        secao = "Manual.html#doacoes_estoque";
                        abrirManual(secao);
                        break;
                    case 5:
                        secao = "Manual.html#doacoes_doadores";
                        abrirManual(secao);
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (Repasses.getSelectedIndex()) {
                    case 0:
                        secao = "Manual.html#repasses_menu";
                        abrirManual(secao);
                        break;
                    case 1:
                        secao = "Manual.html#repasses_cadastrar_repasse";
                        abrirManual(secao);
                        break;
                    case 2:
                        secao = "Manual.html#repasses_repasses";
                        abrirManual(secao);
                        break;
                    case 3:
                        secao = "Manual.html#repasses_item_repasses";
                        abrirManual(secao);
                        break;
                    case 4:
                        secao = "Manual.html#repasses_coletores";
                        abrirManual(secao);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (Acervo.getSelectedIndex()) {
                    case 0:
                        secao = "Manual.html#acervo_menu";
                        abrirManual(secao);
                        break;
                    case 1:
                        secao = "Manual.html#acervo_cadastrar_item_acervo";
                        abrirManual(secao);
                        break;
                    case 2:
                        secao = "Manual.html#acervo_cadastrar_imagem";
                        abrirManual(secao);
                        break;
                    case 3:
                        secao = "Manual.html#acervo_cadastrar_container";
                        abrirManual(secao);
                        break;
                    case 4:
                        secao = "Manual.html#acervo_acervo";
                        abrirManual(secao);
                        break;
                    case 5:
                        secao = "Manual.html#acervo_imagens";
                        abrirManual(secao);
                        break;
                    case 6:
                        secao = "Manual.html#acervo_containers";
                        abrirManual(secao);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (AbaDoUsuario.getSelectedIndex()) {
                    case 0:
                        secao = "Manual.html#abadousuario_menu";
                        abrirManual(secao);
                        break;
                    case 1:
                        secao = "Manual.html#abadousuario_editar_info";
                        abrirManual(secao);
                        break;
                    case 2:
                        secao = "Manual.html#abadousuario_deslogar";
                        abrirManual(secao);
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                switch (Usuarios.getSelectedIndex()) {
                    case 0:
                        secao = "Manual_Administrador.html#usuarios_menu";
                        abrirManual(secao);
                        break;
                    case 1:
                        secao = "Manual_Administrador.html#usuarios_cadastrar_usuario";
                        abrirManual(secao);
                        break;
                    case 2:
                        secao = "Manual_Administrador.html#usuarios_usuarios";
                        abrirManual(secao);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

    }

    private void AbrirManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirManualActionPerformed
        // TODO add your handling code here:
        abrirManual("Manual.html");
    }//GEN-LAST:event_AbrirManualActionPerformed

    private void AbrirManualEspecificoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirManualEspecificoActionPerformed
        // TODO add your handling code here:
        abrirManualEspecifico();
    }//GEN-LAST:event_AbrirManualEspecificoActionPerformed

    private void AbrirCodigoFonteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirCodigoFonteActionPerformed
        // TODO add your handling code here:

        try {
            URI codigofonte = new URI("https://github.com/roscoche/sgacervo");
            Desktop.getDesktop().browse(codigofonte);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AbrirCodigoFonteActionPerformed

    private void excluirLinhasTabela(JTable tabela) {
        while (tabela.getSelectedRowCount() > 0) {
            ((DefaultTableModel) tabela.getModel()).removeRow(tabela.getSelectedRow());
        }
    }

    private void validaImagemAlterarImagem() {
        BufferedImage image;
        int h, w, times;
        try {
            image = ImageIO.read(new URL(campoLinkAlterarImagem.getText()));
            h = image.getHeight();
            w = image.getWidth();
            if (w >= h) {
                times = w / LImagemAlterarImagem.getWidth();
                w = w / (times + 1);
                h = h / (times + 1);
            } else {
                times = h / LImagemAlterarImagem.getHeight();
                w = w / (times + 1);
                h = h / (times + 1);
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

    private void resetarImagem() {
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
                if ("Nimbus".equals(info.getName())) {
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
        java.awt.EventQueue.invokeLater(new Runnable() {
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
    private javax.swing.JMenuItem AbrirCodigoFonte;
    private javax.swing.JMenuItem AbrirManual;
    private javax.swing.JMenuItem AbrirManualEspecifico;
    private javax.swing.JTabbedPane Acervo;
    private javax.swing.JButton BAbrirCadastrarDoacao;
    private javax.swing.JButton BAbrirCadastrarDoacao1;
    private javax.swing.JButton BAbrirCadastrarUsuario;
    private javax.swing.JButton BAbrirDeslogar;
    private javax.swing.JButton BAbrirDoacoes;
    private javax.swing.JButton BAbrirDoadores;
    private javax.swing.JButton BAbrirEditarInformacoes;
    private javax.swing.JButton BAbrirEstoque;
    private javax.swing.JButton BAbrirItemDoacao;
    private javax.swing.JButton BAbrirItemRepasse;
    private javax.swing.JButton BAbrirRepasses;
    private javax.swing.JButton BAcervoFiltrar;
    private javax.swing.JButton BAcervoPaginaAnterior;
    private javax.swing.JButton BAcervoProxPagina;
    private javax.swing.JButton BAdicionarItemDoacao;
    private javax.swing.JButton BAdicionarItemLista;
    private javax.swing.JButton BAdicionarItemRepasse;
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
    private javax.swing.JButton BCheckLink;
    private javax.swing.JButton BColetorFiltrar;
    private javax.swing.JButton BColetorPaginaAnterior;
    private javax.swing.JButton BColetorProxPagina;
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
    private javax.swing.JButton BContainerFiltrar;
    private javax.swing.JButton BContainerFiltrar1;
    private javax.swing.JButton BContainerPaginaAnterior;
    private javax.swing.JButton BContainerProxPagina;
    private javax.swing.JToggleButton BDeslogar;
    private javax.swing.JButton BDoacaoFiltrar;
    private javax.swing.JButton BDoacaoPaginaAnterior;
    private javax.swing.JButton BDoacaoProxPagina;
    private javax.swing.JButton BDoadorFiltrar;
    private javax.swing.JButton BDoadorPaginaAnterior;
    private javax.swing.JButton BDoadorProxPagina;
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
    private javax.swing.JButton BEstoqueFiltrar;
    private javax.swing.JButton BEstoquePaginaAnterior;
    private javax.swing.JButton BEstoqueProxPagina;
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
    private javax.swing.JButton BExibirAcervo;
    private javax.swing.JButton BExibirColetores;
    private javax.swing.JButton BExibirContainer;
    private javax.swing.JButton BExibirImagens;
    private javax.swing.JButton BExibirUsuarios;
    private javax.swing.JButton BFiltrarAcervo;
    private javax.swing.JButton BFiltrarColetor;
    private javax.swing.JButton BFiltrarContainer;
    private javax.swing.JButton BFiltrarDoacao;
    private javax.swing.JButton BFiltrarEstoque;
    private javax.swing.JButton BFiltrarImagem;
    private javax.swing.JButton BFiltrarItemRepasse;
    private javax.swing.JButton BFiltrarRepasse;
    private javax.swing.JButton BFiltrarUsuario;
    private javax.swing.JButton BImagemFiltrar;
    private javax.swing.JButton BImagemPaginaAnterior;
    private javax.swing.JButton BImagemProxPagina;
    private javax.swing.JButton BItemDoacaoFiltrar;
    private javax.swing.JButton BItemDoacaoPaginaAnterior;
    private javax.swing.JButton BItemDoacaoProxPagina;
    private javax.swing.JButton BItemRepasseFiltrar;
    private javax.swing.JButton BItemRepassePaginaAnterior;
    private javax.swing.JButton BItemRepasseProxPagina;
    private javax.swing.JButton BLogar;
    private javax.swing.JButton BMenuCadastrarColetor;
    private javax.swing.JButton BMenuCadastrarContainer;
    private javax.swing.JButton BMenuCadastrarDoador;
    private javax.swing.JButton BMenuCadastrarImagem;
    private javax.swing.JButton BMenuCadastrarItemAcervo;
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
    private javax.swing.JButton BRelatorioAcervo;
    private javax.swing.JButton BRelatorioColetor;
    private javax.swing.JButton BRelatorioDoacao;
    private javax.swing.JButton BRelatorioDoadores;
    private javax.swing.JButton BRelatorioDoadores1;
    private javax.swing.JButton BRelatorioEstoque;
    private javax.swing.JButton BRelatorioItemDoacao;
    private javax.swing.JButton BRelatorioItemDoacao1;
    private javax.swing.JButton BRelatorioItemRepasse;
    private javax.swing.JButton BRelatorioRepasse;
    private javax.swing.JButton BRemoverItemDoacao;
    private javax.swing.JButton BRemoverItemRepasse;
    private javax.swing.JButton BRepasseFiltrar;
    private javax.swing.JButton BRepassePaginaAnterior;
    private javax.swing.JButton BRepasseProxPagina;
    private javax.swing.JButton BUsuariosPaginaAnterior;
    private javax.swing.JButton BUsuariosProxPagina;
    private javax.swing.JMenuBar BarraMenu;
    private javax.swing.JComboBox<String> CBAcervoDoador;
    private javax.swing.JComboBox<String> CBAcervoInterface;
    private javax.swing.JComboBox<String> CBAcervoMarca;
    private javax.swing.JComboBox<String> CBAcervoModelo;
    private javax.swing.JComboBox<String> CBAcervoTecnologia;
    private javax.swing.JComboBox<String> CBAcervoTipo;
    private javax.swing.JComboBox<String> CBAcervoUsuario;
    private javax.swing.JCheckBox CBAdministrador;
    private javax.swing.JComboBox CBColetor;
    private javax.swing.JComboBox<String> CBColetorColetor;
    private javax.swing.JComboBox<String> CBColetorTipo;
    private javax.swing.JComboBox<String> CBContainerLocalizacao;
    private javax.swing.JComboBox<String> CBContainerTipo;
    private javax.swing.JComboBox CBDestinacao;
    private javax.swing.JComboBox CBDestinacaoAlterarRepasse;
    private javax.swing.JComboBox<String> CBDoacaoDoador;
    private javax.swing.JComboBox<String> CBDoacaoEvento;
    private javax.swing.JComboBox<String> CBDoacaoUsuario;
    private javax.swing.JComboBox CBDoadorDoacao;
    private javax.swing.JComboBox<String> CBDoadorDoador;
    private javax.swing.JComboBox CBDoadorItemAcervo;
    private javax.swing.JComboBox<String> CBEstoqueTipo;
    private javax.swing.JComboBox CBEventoOrigem;
    private javax.swing.JComboBox CBEventoOrigemAlterarDoacao;
    private javax.swing.JCheckBox CBFunciona;
    private javax.swing.JComboBox<String> CBImagemMarca;
    private javax.swing.JComboBox<String> CBImagemModelo;
    private javax.swing.JComboBox CBInterface;
    private javax.swing.JComboBox CBInterfaceAlterarItemAcervo;
    private javax.swing.JComboBox<String> CBItemDoacaoDoador;
    private javax.swing.JComboBox<String> CBItemDoacaoEvento;
    private javax.swing.JComboBox<String> CBItemDoacaoUsuario;
    private javax.swing.JComboBox<String> CBItemRepasseColetor;
    private javax.swing.JComboBox<String> CBItemRepasseDestinacao;
    private javax.swing.JComboBox<String> CBItemRepasseTipo;
    private javax.swing.JComboBox<String> CBItemRepasseUsuario;
    private javax.swing.JComboBox CBMarca;
    private javax.swing.JComboBox CBMarcaAlterarItemAcervo;
    private javax.swing.JComboBox CBModelo;
    private javax.swing.JComboBox CBModeloAlterarItemAcervo;
    private javax.swing.JComboBox<String> CBRepasseColetor;
    private javax.swing.JComboBox<String> CBRepasseDestinacao;
    private javax.swing.JComboBox<String> CBRepasseTipoColetor;
    private javax.swing.JComboBox<String> CBRepasseUsuario;
    private javax.swing.JComboBox CBTecnologia;
    private javax.swing.JComboBox CBTecnologiaAlterarItemAcervo;
    private javax.swing.JComboBox<String> CBTipoAdicionarItemLista;
    private javax.swing.JComboBox CBTipoAlterarItemAcervo;
    private javax.swing.JComboBox CBTipoAlterarItemDoacao;
    private javax.swing.JComboBox CBTipoAlterarItemRepasse;
    private javax.swing.JComboBox CBTipoColetor;
    private javax.swing.JComboBox CBTipoColetorAlterarColetor;
    private javax.swing.JComboBox CBTipoContainerAlterarContainer;
    private javax.swing.JComboBox CBTipoContainerCadastrarContainer;
    private javax.swing.JComboBox CBTipoItemAcervo;
    private javax.swing.JComboBox CBUsuarioLogin;
    private javax.swing.JComboBox<String> CBUsuarioNome;
    private javax.swing.JPanel CadastrarContainer;
    private javax.swing.JPanel CadastrarDoacao;
    private javax.swing.JPanel CadastrarImagem;
    private javax.swing.JPanel CadastrarItemAcervo;
    private javax.swing.JPanel CadastrarRepasse;
    private javax.swing.JPanel CadastrarUsuario;
    private javax.swing.JCheckBox CheckAcervoAno;
    private javax.swing.JCheckBox CheckAcervoCapacidade;
    private javax.swing.JCheckBox CheckAcervoCodigo;
    private javax.swing.JCheckBox CheckAcervoContainer;
    private javax.swing.JCheckBox CheckAcervoData;
    private javax.swing.JCheckBox CheckAcervoDoador;
    private javax.swing.JCheckBox CheckAcervoFunciona;
    private javax.swing.JCheckBox CheckAcervoFuncionamento;
    private javax.swing.JCheckBox CheckAcervoInterface;
    private javax.swing.JCheckBox CheckAcervoMarca;
    private javax.swing.JCheckBox CheckAcervoModelo;
    private javax.swing.JCheckBox CheckAcervoTecnologia;
    private javax.swing.JCheckBox CheckAcervoTipo;
    private javax.swing.JCheckBox CheckAcervoUsuario;
    private javax.swing.JCheckBox CheckAdministradorAlterarUsuarioJD;
    private javax.swing.JCheckBox CheckColetorCodigo;
    private javax.swing.JCheckBox CheckColetorColetor;
    private javax.swing.JCheckBox CheckColetorTipo;
    private javax.swing.JCheckBox CheckContainerCodigo;
    private javax.swing.JCheckBox CheckContainerLocalizacao;
    private javax.swing.JCheckBox CheckContainerTipo;
    private javax.swing.JCheckBox CheckDoacaoCodigo;
    private javax.swing.JCheckBox CheckDoacaoData;
    private javax.swing.JCheckBox CheckDoacaoDoador;
    private javax.swing.JCheckBox CheckDoacaoEvento;
    private javax.swing.JCheckBox CheckDoacaoUsuario;
    private javax.swing.JCheckBox CheckDoadorCodigo;
    private javax.swing.JCheckBox CheckDoadorDoador;
    private javax.swing.JCheckBox CheckEstoqueDoacao;
    private javax.swing.JCheckBox CheckEstoqueRepasse;
    private javax.swing.JCheckBox CheckEstoqueSaldo;
    private javax.swing.JCheckBox CheckEstoqueTipo;
    private javax.swing.JCheckBox CheckFuncionaAlterarItemAcervo;
    private javax.swing.JButton CheckImagemAlterarImagem;
    private javax.swing.JCheckBox CheckImagemCodigo;
    private javax.swing.JCheckBox CheckImagemItem;
    private javax.swing.JCheckBox CheckImagemMarca;
    private javax.swing.JCheckBox CheckImagemModelo;
    private javax.swing.JCheckBox CheckItemDoacaoCodigo;
    private javax.swing.JCheckBox CheckItemDoacaoData;
    private javax.swing.JCheckBox CheckItemDoacaoDoacaoCodigo;
    private javax.swing.JCheckBox CheckItemDoacaoDoador;
    private javax.swing.JCheckBox CheckItemDoacaoEvento;
    private javax.swing.JCheckBox CheckItemDoacaoUsuario;
    private javax.swing.JCheckBox CheckItemRepasseCodigo;
    private javax.swing.JCheckBox CheckItemRepasseColetor;
    private javax.swing.JCheckBox CheckItemRepasseData;
    private javax.swing.JCheckBox CheckItemRepasseDestinacao;
    private javax.swing.JCheckBox CheckItemRepasseQuantidade;
    private javax.swing.JCheckBox CheckItemRepasseRepasse;
    private javax.swing.JCheckBox CheckItemRepasseTipo;
    private javax.swing.JCheckBox CheckItemRepasseUsuario;
    private javax.swing.JCheckBox CheckRepasseCodigo;
    private javax.swing.JCheckBox CheckRepasseColetor;
    private javax.swing.JCheckBox CheckRepasseData;
    private javax.swing.JCheckBox CheckRepasseDestinacao;
    private javax.swing.JCheckBox CheckRepasseTipoColetor;
    private javax.swing.JCheckBox CheckRepasseUsuario;
    private javax.swing.JCheckBox CheckUsuarioAdministrador;
    private javax.swing.JCheckBox CheckUsuarioCodigo;
    private javax.swing.JCheckBox CheckUsuarioNome;
    private javax.swing.JCheckBox CheckUsuarioPermissao;
    private javax.swing.JPanel Container;
    private javax.swing.JPanel Deslogar;
    private javax.swing.JTabbedPane Doacoes;
    private javax.swing.JScrollPane ExibirColetores;
    private javax.swing.JScrollPane ExibirDoadores;
    private javax.swing.JScrollPane ExibirUsuarios;
    private javax.swing.JLabel Header;
    private javax.swing.JPanel Imagens;
    private javax.swing.JDialog JDAdicionarItemLista;
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
    private com.toedter.calendar.JDateChooser JDCAcervoDataMax;
    private com.toedter.calendar.JDateChooser JDCAcervoDataMin;
    private com.toedter.calendar.JDateChooser JDCDoacaoMax;
    private com.toedter.calendar.JDateChooser JDCDoacaoMin;
    private com.toedter.calendar.JDateChooser JDCItemDoacaoMax;
    private com.toedter.calendar.JDateChooser JDCItemDoacaoMin;
    private com.toedter.calendar.JDateChooser JDCItemRepasseMax;
    private com.toedter.calendar.JDateChooser JDCItemRepasseMin;
    private com.toedter.calendar.JDateChooser JDCRepasseMax;
    private com.toedter.calendar.JDateChooser JDCRepasseMin;
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
    private javax.swing.JDialog JDFiltrarAcervo;
    private javax.swing.JDialog JDFiltrarColetor;
    private javax.swing.JDialog JDFiltrarContainer;
    private javax.swing.JDialog JDFiltrarDoacao;
    private javax.swing.JDialog JDFiltrarDoador;
    private javax.swing.JDialog JDFiltrarEstoque;
    private javax.swing.JDialog JDFiltrarImagem;
    private javax.swing.JDialog JDFiltrarItemDoacao;
    private javax.swing.JDialog JDFiltrarItemRepasse;
    private javax.swing.JDialog JDFiltrarRepasse;
    private javax.swing.JDialog JDFiltrarUsuario;
    private javax.swing.JDialog JDLogin;
    private javax.swing.JFrame JFCarregandoDados;
    private javax.swing.JProgressBar JPBCarregando;
    private javax.swing.JLabel LAcervoPagina;
    private javax.swing.JLabel LAcervoTotalPaginas;
    private javax.swing.JLabel LAlterar;
    private javax.swing.JLabel LAnoAlterarItemAcervo;
    private javax.swing.JLabel LAnoItemAcervo;
    private javax.swing.JLabel LBemVindoDoacoes;
    private javax.swing.JLabel LBemVindoDoacoes1;
    private javax.swing.JLabel LBemVindoDoacoes2;
    private javax.swing.JLabel LBemVindoDoacoes3;
    private javax.swing.JLabel LBemVindoDoacoes4;
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
    private javax.swing.JLabel LCamposObrigatorios5;
    private javax.swing.JLabel LCamposRegularesAlterarUsuario;
    private javax.swing.JLabel LCapacidadeAlterarItemAcervo;
    private javax.swing.JLabel LCapacidade_MB;
    private javax.swing.JLabel LCarregando;
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
    private javax.swing.JLabel LCodContainerAlterarContainer;
    private javax.swing.JLabel LCodDestinacao;
    private javax.swing.JLabel LCodDoador;
    private javax.swing.JLabel LCodEO;
    private javax.swing.JLabel LCodImagemAlterarImagem;
    private javax.swing.JLabel LCodInterface;
    private javax.swing.JLabel LCodItemAcervoAlterarImagem;
    private javax.swing.JLabel LCodMarca;
    private javax.swing.JLabel LCodModelo;
    private javax.swing.JLabel LCodNovoColetor;
    private javax.swing.JLabel LCodNovoUsuario;
    private javax.swing.JLabel LCodTecnologia;
    private javax.swing.JLabel LCodTipo;
    private javax.swing.JLabel LCodTipoColetor;
    private javax.swing.JLabel LCodTipoContainer;
    private javax.swing.JLabel LCodUsuarioInfo;
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
    private javax.swing.JLabel LCodigo_doacaoAlterarDoacao;
    private javax.swing.JLabel LCodigo_doacaoAlterarDoacao1;
    private javax.swing.JLabel LCodigo_doacaoAlterarRepasse;
    private javax.swing.JLabel LCodigo_doacaoExcluirDoacao;
    private javax.swing.JLabel LCodigo_doadorAlterarDoador;
    private javax.swing.JLabel LCodigo_doadorExcluirDoador;
    private javax.swing.JLabel LColetorPagina;
    private javax.swing.JLabel LColetorRepasse;
    private javax.swing.JLabel LColetorTotalPaginas;
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
    private javax.swing.JLabel LContainerPagina;
    private javax.swing.JLabel LContainerTotalPaginas;
    private javax.swing.JLabel LDataAlterarDoacao;
    private javax.swing.JLabel LDataAlterarDoacao1;
    private javax.swing.JLabel LDataAlterarItemAcervo;
    private javax.swing.JLabel LDataAlterarItemRepasse;
    private javax.swing.JLabel LDataAlterarRepasse;
    private javax.swing.JLabel LDescricao;
    private javax.swing.JLabel LDescricaoAlterarItemAcervo;
    private javax.swing.JLabel LDescricaoRepasse;
    private javax.swing.JLabel LDoacaoPagina;
    private javax.swing.JLabel LDoacaoTotalPaginas;
    private javax.swing.JLabel LDoadorAlterarDoacao;
    private javax.swing.JLabel LDoadorAlterarDoacao1;
    private javax.swing.JLabel LDoadorAlterarItemAcervo;
    private javax.swing.JLabel LDoadorAlterarItemRepasse;
    private javax.swing.JLabel LDoadorAlterarRepasse;
    private javax.swing.JLabel LDoadorPagina;
    private javax.swing.JLabel LDoadorTotalPaginas;
    private javax.swing.JLabel LEmail;
    private javax.swing.JLabel LEmailAlterarUsuario;
    private javax.swing.JLabel LEmailAlterarUsuarioJD;
    private javax.swing.JLabel LEstoquePagina;
    private javax.swing.JLabel LEstoqueTotalPaginas;
    private javax.swing.JLabel LFotoAcervo;
    private javax.swing.JLabel LFuncionaAlterarItemAcervo;
    private javax.swing.JLabel LImagemAlterarImagem;
    private javax.swing.JLabel LImagemPagina;
    private javax.swing.JLabel LImagemTotalPaginas;
    private javax.swing.JLabel LInfoDoacao;
    private javax.swing.JLabel LInfoDoacao1;
    private javax.swing.JLabel LInfoDoacao2;
    private javax.swing.JLabel LInfoDoacao3;
    private javax.swing.JLabel LInfoDoacao4;
    private javax.swing.JLabel LInterface;
    private javax.swing.JLabel LInterfaceAlterarItemAcervo;
    private javax.swing.JLabel LItemAcervoCadastrarImagem;
    private javax.swing.JLabel LItemDoacaoPagina;
    private javax.swing.JLabel LItemDoacaoTotalPaginas;
    private javax.swing.JLabel LItemFunciona;
    private javax.swing.JLabel LItemRepassePagina;
    private javax.swing.JLabel LItemRepasseTotalPaginas;
    private javax.swing.JLabel LItemsDoacao;
    private javax.swing.JLabel LItemsDoacao1;
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
    private javax.swing.JLabel LPO12;
    private javax.swing.JLabel LPO3;
    private javax.swing.JLabel LPO4;
    private javax.swing.JLabel LPO5;
    private javax.swing.JLabel LPO6;
    private javax.swing.JLabel LPO7;
    private javax.swing.JLabel LPO8;
    private javax.swing.JLabel LPO9;
    private javax.swing.JLabel LPermissaoAlterarUsuarioJD;
    private javax.swing.JLabel LPermissão;
    private javax.swing.JLabel LRegistroAcademico;
    private javax.swing.JLabel LRegistroAcademicoAlterarUsuario;
    private javax.swing.JLabel LRegistroAcademicoAlterarUsuarioJD;
    private javax.swing.JLabel LRepassePagina;
    private javax.swing.JLabel LRepasseTotalPaginas;
    private javax.swing.JLabel LRepetirNovaSenhaAlterarUsuarioJD;
    private javax.swing.JLabel LRepetirSenha;
    private javax.swing.JLabel LRepetirSenhaAlterarUsuario;
    private javax.swing.JLabel LSenha;
    private javax.swing.JLabel LSenhaAlterar;
    private javax.swing.JLabel LSenhaLogin;
    private javax.swing.JLabel LTecnologia;
    private javax.swing.JLabel LTecnologiaAlterarItemAcervo;
    private javax.swing.JLabel LTipo;
    private javax.swing.JLabel LTipo1;
    private javax.swing.JLabel LTipoAlterarItemAcervo;
    private javax.swing.JLabel LTipoColetor;
    private javax.swing.JLabel LTipoColetorAlterarColetor;
    private javax.swing.JLabel LTipoContainerAlterarContainer;
    private javax.swing.JLabel LTipoContainerCadastrarContainer;
    private javax.swing.JLabel LTipoItemAcervo;
    private javax.swing.JLabel LTipoTipoColetor;
    private javax.swing.JLabel LTipoTipoContainer;
    private javax.swing.JLabel LUsuarioAdm;
    private javax.swing.JLabel LUsuariosPagina;
    private javax.swing.JLabel LUsuariosTotalPaginas;
    private javax.swing.JLabel LUsuárioAlterarDoacao;
    private javax.swing.JLabel LUsuárioAlterarDoacao1;
    private javax.swing.JLabel LUsuárioAlterarItemAcervo;
    private javax.swing.JLabel LUsuárioAlterarItemRepasse;
    private javax.swing.JLabel LUsuárioAlterarRepasse;
    private javax.swing.JLabel LUsuárioLogin;
    private javax.swing.JLabel Ldoador;
    private javax.swing.JPanel MenuAbaUsuario;
    private javax.swing.JPanel MenuAcervo;
    private javax.swing.JMenu MenuAjuda;
    private javax.swing.JPanel MenuDoacoes;
    private javax.swing.JMenu MenuOpcoes;
    private javax.swing.JPanel MenuRepasse;
    private javax.swing.JPanel MenuUsuarios;
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
    private javax.swing.JSpinner SPAcervoAnoMax;
    private javax.swing.JSpinner SPAcervoAnoMin;
    private javax.swing.JSpinner SPAcervoCapacidadeMax;
    private javax.swing.JSpinner SPAcervoCapacidadeMin;
    private javax.swing.JSpinner SPAcervoCodigoMax;
    private javax.swing.JSpinner SPAcervoCodigoMin;
    private javax.swing.JSpinner SPAcervoContainerMax;
    private javax.swing.JSpinner SPAcervoContainerMin;
    private javax.swing.JSpinner SPAcervoItensPagina;
    private javax.swing.JSpinner SPColetorCodigoMax;
    private javax.swing.JSpinner SPColetorCodigoMin;
    private javax.swing.JSpinner SPColetorItensPagina;
    private javax.swing.JScrollPane SPContainer;
    private javax.swing.JSpinner SPContainerCodigoMax;
    private javax.swing.JSpinner SPContainerCodigoMin;
    private javax.swing.JSpinner SPContainerItensPagina;
    private javax.swing.JScrollPane SPDescricaoAlterarItemAcervo;
    private javax.swing.JScrollPane SPDescricaoItemAcervo;
    private javax.swing.JSpinner SPDoacaoItensPagina;
    private javax.swing.JSpinner SPDoacaoMax;
    private javax.swing.JSpinner SPDoacaoMin;
    private javax.swing.JSpinner SPDoadorCodigoMax;
    private javax.swing.JSpinner SPDoadorCodigoMin;
    private javax.swing.JSpinner SPDoadorItensPagina;
    private javax.swing.JSpinner SPEstoqueDoacaoMax;
    private javax.swing.JSpinner SPEstoqueDoacaoMin;
    private javax.swing.JSpinner SPEstoqueItensPagina;
    private javax.swing.JSpinner SPEstoqueRepasseMax;
    private javax.swing.JSpinner SPEstoqueRepasseMin;
    private javax.swing.JSpinner SPEstoqueSaldoMax;
    private javax.swing.JSpinner SPEstoqueSaldoMin;
    private javax.swing.JScrollPane SPImagem;
    private javax.swing.JSpinner SPImagemCodigoMax;
    private javax.swing.JSpinner SPImagemCodigoMin;
    private javax.swing.JSpinner SPImagemItemMax;
    private javax.swing.JSpinner SPImagemItemMin;
    private javax.swing.JSpinner SPImagemItensPagina;
    private javax.swing.JSpinner SPItemDoacaoDoacaoMax;
    private javax.swing.JSpinner SPItemDoacaoDoacaoMin;
    private javax.swing.JSpinner SPItemDoacaoItensPagina;
    private javax.swing.JSpinner SPItemDoacaoMax;
    private javax.swing.JSpinner SPItemDoacaoMin;
    private javax.swing.JSpinner SPItemRepasseItensPagina;
    private javax.swing.JSpinner SPItemRepasseMax;
    private javax.swing.JSpinner SPItemRepasseMin;
    private javax.swing.JSpinner SPItemRepasseQuantidadeMax;
    private javax.swing.JSpinner SPItemRepasseQuantidadeMin;
    private javax.swing.JSpinner SPItemRepasseRepasseMax;
    private javax.swing.JSpinner SPItemRepasseRepasseMin;
    private javax.swing.JSpinner SPQuantidadeItemLista;
    private javax.swing.JSpinner SPRepasseItensPagina;
    private javax.swing.JSpinner SPRepasseMax;
    private javax.swing.JSpinner SPRepasseMin;
    private javax.swing.JSpinner SPUsuarioCodigoMax;
    private javax.swing.JSpinner SPUsuarioCodigoMin;
    private javax.swing.JSpinner SPUsuariosItensPagina;
    private javax.swing.JPanel SuasInformacoes;
    private javax.swing.JTextArea TADescricaoAlterarItemAcervo;
    private javax.swing.JTable TAcervo;
    private javax.swing.JTable TAdicionarItemDoacao;
    private javax.swing.JTable TAdicionarItemRepasse;
    private javax.swing.JTable TColetor;
    private javax.swing.JTable TContainer;
    private javax.swing.JTable TDoacao;
    private javax.swing.JTable TDoador;
    private javax.swing.JTable TEstoque;
    private javax.swing.JTable TImagem;
    private javax.swing.JTable TItemDoacao;
    private javax.swing.JTable TItemRepasse;
    private javax.swing.JTable TRepasse;
    private javax.swing.JTable TUsuarios;
    private javax.swing.JTabbedPane Usuarios;
    private javax.swing.JCheckBox campoAdministradorAlterarUsuario;
    private javax.swing.JTextField campoAnoAlterarItemAcervo;
    private javax.swing.JTextField campoAnoItemAcervo;
    private javax.swing.JTextField campoCapacidadeAlterarItemAcervo;
    private javax.swing.JTextField campoCapacidadeItemAcervo;
    private javax.swing.JTextField campoCodContainerAlterarContainer;
    private javax.swing.JTextField campoCodContainerCadastrarItemAcervo;
    private javax.swing.JTextField campoCodImagemAlterarImagem;
    private javax.swing.JTextField campoCodItemAcervoAlterarImagem;
    private javax.swing.JTextField campoCodigoAlterarUsuario;
    private javax.swing.JTextField campoCodigoColetor;
    private javax.swing.JTextField campoCodigoColetorAlterarColetor;
    private javax.swing.JTextField campoCodigoDestinacao;
    private javax.swing.JTextField campoCodigoDoador;
    private javax.swing.JTextField campoCodigoDoadorAlterarDoador;
    private javax.swing.JTextField campoCodigoEventoOrigem;
    private javax.swing.JTextField campoCodigoInterface;
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
    private javax.swing.JTextField campoContainerAlterarItemAcervo;
    private javax.swing.JTextField campoContainerExcluirContainer;
    private javax.swing.JFormattedTextField campoDataAlterarDoacao;
    private javax.swing.JFormattedTextField campoDataAlterarItemAcervo;
    private javax.swing.JFormattedTextField campoDataAlterarRepasse;
    private javax.swing.JTextPane campoDescricaoItemAcervo;
    private javax.swing.JTextField campoDoacaoAlterarDoacao;
    private javax.swing.JTextField campoDoacaoExcluirDoacao;
    private javax.swing.JTextField campoDoadorAlterarDoacao;
    private javax.swing.JTextField campoDoadorAlterarItemAcervo;
    private javax.swing.JTextField campoDoadorAlterarItemDoacao;
    private javax.swing.JTextField campoDoadorExcluirDoador;
    private javax.swing.JTextField campoEmailAlterarUsuario;
    private javax.swing.JTextField campoEmailAlterarUsuarioJD;
    private javax.swing.JTextField campoEmailCadastrarUsuario;
    private javax.swing.JTextField campoImagemExcluirImagem;
    private javax.swing.JTextField campoItemAcervoAlterarItemAcervo;
    private javax.swing.JTextField campoItemAcervoCadastrarImagem;
    private javax.swing.JTextField campoItemAcervoExcluirItemAcervo;
    private javax.swing.JTextField campoItemDoacaoAlterarItemDoacao;
    private javax.swing.JTextField campoItemDoacaoExcluirItemDoacao;
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
    private javax.swing.JTextField campoRegistroAcademicoAlterarUsuario;
    private javax.swing.JTextField campoRegistroAcademicoAlterarUsuarioJD;
    private javax.swing.JTextField campoRegistroAcademicoCadastrarUsuario;
    private javax.swing.JTextField campoRepasseAlterarRepasse;
    private javax.swing.JTextField campoRepasseExcluirRepasse;
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
    private javax.swing.JTextField campoUsuarioExcluirUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
    private String nomeUsuario, senhaUsuario;
    private int codigoUsuario;
    private int progresso;
    private boolean logado = false, administrador;
    private final ConfigBanco cb = new ConfigBanco();
    private final String dbURL = cb.getDbURL();
    private final String dbUser = cb.getLogin();
    private final String dbPassword = cb.getPassword();
    private DefaultTableModel lista;
    private final ImageIcon iconeaux = new ImageIcon(Principal.class.getResource("imagens/acervo32.png"));
    private final Image icone = iconeaux.getImage();
    private int numeroPaginaDoacao = 1,
            numeroPaginaItemDoacao = 1,
            numeroPaginaDoador = 1,
            numeroPaginaEstoque = 1,
            numeroPaginaRepasse = 1,
            numeroPaginaItemRepasse = 1,
            numeroPaginaColetor = 1,
            numeroPaginaAcervo = 1,
            numeroPaginaImagem = 1,
            numeroPaginaContainer = 1,
            numeroPaginaUsuarios = 1,
            numeroMaxPaginaDoacao = 1,
            numeroMaxPaginaItemDoacao = 1,
            numeroMaxPaginaDoador = 1,
            numeroMaxPaginaEstoque = 1,
            numeroMaxPaginaRepasse = 1,
            numeroMaxPaginaItemRepasse = 1,
            numeroMaxPaginaColetor = 1,
            numeroMaxPaginaAcervo = 1,
            numeroMaxPaginaImagem = 1,
            numeroMaxPaginaUsuarios = 1,
            numeroMaxPaginaContainer = 1;

    private boolean achandoMax = true;
    private final String SelecaoDoacao = "SELECT * from doacao_detalhado ",
            SelecaoItemDoacao = "SELECT * from item_doacao_detalhado ",
            SelecaoDoador = "SELECT * from doador_detalhado ",
            SelecaoEstoque = "SELECT * from estoque_detalhado ",
            SelecaoRepasse = "SELECT * from repasse_detalhado ",
            SelecaoItemRepasse = "SELECT * from item_repasse_detalhado ",
            SelecaoColetor = "SELECT * from coletor_detalhado ",
            SelecaoAcervo = "SELECT * from acervo_detalhado ",
            SelecaoImagem = "SELECT * from imagem_detalhado ",
            SelecaoContainer = "SELECT * from container_detalhado ",
            SelecaoUsuarios = "SELECT * from usuario_detalhado ";

    private String FiltroDoacao = "",
            FiltroItemDoacao = "",
            FiltroDoador = "",
            FiltroEstoque = "",
            FiltroRepasse = "",
            FiltroItemRepasse = "",
            FiltroColetor = "",
            FiltroAcervo = "",
            FiltroImagem = "",
            FiltroContainer = "",
            FiltroUsuarios = "",
            PaginaDoacao = "",
            PaginaItemDoacao = "",
            PaginaDoador = "",
            PaginaEstoque = "",
            PaginaRepasse = "",
            PaginaItemRepasse = "",
            PaginaColetor = "",
            PaginaAcervo = "",
            PaginaImagem = "",
            PaginaUsuarios = "",
            PaginaContainer = "";

}
