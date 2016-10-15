package br.pro.ramon.dcs.servlets;

import br.pro.ramon.dcs.daos.DaoException;
import br.pro.ramon.dcs.daos.DaoFactory;
import br.pro.ramon.dcs.daos.ProfessorDao;
import br.pro.ramon.dcs.entities.Professor;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            ProfessorDao professorDao = DaoFactory.getProfessorDao();
            Professor professor = professorDao.findByEmailSenha(email, senha);

            if (professor == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.printf("{ \"status\": \"%d\", \"erro\": \"Usuário não encontrado.\" }", response.getStatus());
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("professor", professor);
                
                response.setStatus(HttpServletResponse.SC_OK);
                out.printf("{ \"status\": \"%d\", \"nome\": \"%s\" }", response.getStatus(), professor.getNome());
            }
        } catch (DaoException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.printf("{ \"status\": \"%d\", \"erro\": \"%s\" }", response.getStatus(), ex.getMessage());
        }
    }

}
