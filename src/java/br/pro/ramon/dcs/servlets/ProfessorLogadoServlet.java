package br.pro.ramon.dcs.servlets;

import br.pro.ramon.dcs.entities.Professor;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/professorLogado")
public class ProfessorLogadoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        HttpSession session = request.getSession();
        Professor professor = (Professor) session.getAttribute("professor");

        if (professor == null) {
            response.sendRedirect("index.html");
        } else {
            out.print(professor.getNome());
        }
    }

}
