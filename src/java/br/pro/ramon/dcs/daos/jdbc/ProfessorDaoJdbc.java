package br.pro.ramon.dcs.daos.jdbc;

import br.pro.ramon.dcs.daos.DaoException;
import br.pro.ramon.dcs.daos.ProfessorDao;
import br.pro.ramon.dcs.entities.Professor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDaoJdbc implements ProfessorDao {

    private String url, user, pass;

    public ProfessorDaoJdbc(String driver, String url, String user, String pass) throws DaoException {
        try {
            Class.forName(driver);
            this.url = url;
            this.user = user;
            this.pass = pass;
        } catch (ClassNotFoundException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Professor findByEmailSenha(String email, String senha) throws DaoException {
        Professor professor = null;

        // http://www.ramon.pro.br/jdbc-acessando-bancos-de-dados-em-java/
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            try {
                // http://stackoverflow.com/questions/36873680/java-preparedstatement-binding-parameter-to-hashbytes/37107328#37107328
                String sql = "select * from Professor where email = ? and senha = hashbytes('sha1', convert(varchar, ?))";
                PreparedStatement stmt = conn.prepareStatement(sql);
                try {
                    stmt.setString(1, email);
                    stmt.setString(2, senha);
                    ResultSet rs = stmt.executeQuery();
                    try {
                        if (rs.next()) {
                            Integer id = rs.getInt("codProfessor");
                            String nome = rs.getString("nome");
                            String registro = rs.getString("idSenac");
                            String tipo = rs.getString("tipo");

                            professor = new Professor(id, nome, email, registro, tipo);
                        }
                    } finally {
                        rs.close();
                    }
                } finally {
                    stmt.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return professor;
    }

}
