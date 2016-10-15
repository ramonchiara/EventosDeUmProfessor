package br.pro.ramon.dcs.daos;

import br.pro.ramon.dcs.entities.Evento;
import br.pro.ramon.dcs.entities.Professor;
import java.util.List;

public interface EventoDao {

    List<Evento> findAllByProfessor(Professor professor) throws DaoException;

}
