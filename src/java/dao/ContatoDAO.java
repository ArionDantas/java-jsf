package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Contato;

public class ContatoDAO {

    //Objeto para conexão com o Banco de Dados
    MyConnection myConnection = new MyConnection();
    Connection conn;
    PreparedStatement stmt;
    ResultSet rs;
    Contato contato = new Contato();

    //CRUD
    public boolean addContato(Contato contato) {
        boolean resposta = false;

        //Definindo o comando que será executado no DB.
        String sql;
        
        // "insert into contato(nome,telefonel,email) values(?,?,?)";
        //Tentando executar os comandos no DB.
        try {
            //Estabelecendo a conexão com o DB.
            conn = MyConnection.getConnection();
            
            if (contato.getId() == null) {
                sql = "insert into tb_contato(nome_contato, email_contato, tel_contato) values(?,?,?)";
                stmt = conn.prepareCall(sql);
            } else {
                sql = "uptade into tb_contatonome_contato=? , email_contato=? , tel_contato=? where id_contato=?";
                stmt = conn.prepareCall(sql);
                stmt.setInt(4, contato.getId());
            }
           
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.executeUpdate();
            resposta = true;
            
        } catch (SQLException e) {
            System.out.println("Erro ao tentar inserir Contato. " + e);
        } finally {
            MyConnection.closeConnection(conn, stmt);
        }

        return resposta;
    }

    public List<Contato> buscar() {
        List contatos = new ArrayList();
        String sql = "select * from contato";

        try {
            conn = MyConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Contato con = new Contato();
                con.setId(rs.getInt("id_contato"));
                con.setNome(rs.getString("nome"));
                con.setEmail(rs.getString("email"));
                con.setTelefone(rs.getString("telefonel"));
                //Adicionando o aluno na lista de alunos
                contatos.add(con);
            }
        } catch (SQLException e) {
            System.out.println("Ops!... Erro ao selecionar contatos!"+e);
        } finally {
            MyConnection.closeConnection(conn, stmt, rs);
        }
          return contatos;    
    }

//    public boolean update(Contato aluno) {
//        boolean resposta = false;
//        conn = MyConnection.getConnection();
//        String sql = "update aluno set nome=?, email=?, telefonel=?, idade=? where idAluno=?";
//        try {
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, aluno.getNome());
//            stmt.setString(2, aluno.getEmail());
//            stmt.setString(3, aluno.getTelefone());
//            stmt.setInt(4, aluno.getIdade());
//            stmt.setInt(5, aluno.getIdAluno());
//            stmt.executeUpdate();
//            resposta = true;
//        } catch (SQLException e) {
//            System.out.println("Ops!... Erro ao atualizar Alunos!"+e);
//        }finally{
//            MyConnection.closeConnection(conn, stmt);
//        }
//        return resposta;
//    }

    public boolean delete(int id) {
        boolean resposta = false;
        conn = MyConnection.getConnection();
        String sql = "delete from contato where id_contato=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            resposta = true;
        } catch (SQLException e) {
            System.out.println("Ops!... Erro ao tentar excluir contato!"+e);
        }finally{
            MyConnection.closeConnection(conn, stmt);
        }
        return resposta;
    }

    public boolean update(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
