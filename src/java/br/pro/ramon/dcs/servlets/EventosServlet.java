package br.pro.ramon.dcs.servlets;

import br.pro.ramon.dcs.daos.DaoException;
import br.pro.ramon.dcs.daos.EventoDao;
import br.pro.ramon.dcs.daos.DaoFactory;
import br.pro.ramon.dcs.entities.Evento;
import br.pro.ramon.dcs.entities.Professor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/eventos")
public class EventosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try {
            HttpSession session = request.getSession();
            Professor professor = (Professor) session.getAttribute("professor");

            if (professor == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.printf("{ \"status\": \"%d\", \"erro\": \"Você precisa estar logado para ver os eventos.\" }", response.getStatus());
            } else {
                EventoDao eventoDao = DaoFactory.getEventoDao();
                List<Evento> eventos = eventoDao.findAllByProfessor(professor);

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                StringBuilder json = new StringBuilder();
                json.append("[ ");
                for (Evento evento : eventos) {
                    json.append("{ ");
                    json.append(String.format("\"data\": \"%s\"", df.format(evento.getData())));
                    json.append(", ");
                    json.append(String.format("\"descricao\": \"%s\"", evento.getDescricao()));
                    json.append(", ");
                    json.append(String.format("\"tipo\": \"%s\"", evento.getTipoEvento()));
                    json.append(" }");
                    json.append(", ");
                }
                json.deleteCharAt(json.length() - 1);
                json.deleteCharAt(json.length() - 1);
                json.append(" ]");

                response.setStatus(HttpServletResponse.SC_OK);
                out.printf("{ \"status\": \"%d\", \"eventos\": %s }", response.getStatus(), json.toString());
            }
        } catch (DaoException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.printf("{ \"status\": \"%d\", \"erro\": \"%s\" }", response.getStatus(), ex.getMessage());
        }
    }

}
