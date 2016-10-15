package br.pro.ramon.dcs.daos;

import br.pro.ramon.dcs.entities.Professor;

public interface ProfessorDao {

    Professor findByEmailSenha(String email, String senha) throws DaoException;

}
