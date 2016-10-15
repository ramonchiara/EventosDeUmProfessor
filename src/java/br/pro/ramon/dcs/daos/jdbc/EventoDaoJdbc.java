package br.pro.ramon.dcs.daos.jdbc;

import br.pro.ramon.dcs.daos.DaoException;
import br.pro.ramon.dcs.daos.EventoDao;
import br.pro.ramon.dcs.entities.Evento;
import br.pro.ramon.dcs.entities.Professor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventoDaoJdbc implements EventoDao {

    private String url, user, pass;

    public EventoDaoJdbc(String driver, String url, String user, String pass) throws DaoException {
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
    public List<Evento> findAllByProfessor(Professor professor) throws DaoException {
        List<Evento> eventos = new ArrayList<Evento>();

        // http://www.ramon.pro.br/jdbc-acessando-bancos-de-dados-em-java/
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            try {
                String sql = "select * from Evento e join TipoEvento te on te.codTipoEvento = e.codTipoEvento where e.codProfessor = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                try {
                    stmt.setInt(1, professor.getId());
                    ResultSet rs = stmt.executeQuery();
                    try {
                        while (rs.next()) {
                            Integer id = rs.getInt("codEvento");
                            String descricao = rs.getString("descricao");
                            Date data = rs.getDate("data");
                            String tipoEvento = rs.getString("descTipoEvento");
                            String status = rs.getString("codStatus");
                            String identificador = rs.getString("identificador");

                            Evento evento = new Evento(id, descricao, data, tipoEvento, status, identificador);
                            eventos.add(evento);
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

        return eventos;
    }

}
