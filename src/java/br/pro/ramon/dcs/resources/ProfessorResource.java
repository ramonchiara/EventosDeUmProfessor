package br.pro.ramon.dcs.resources;

import br.pro.ramon.dcs.daos.DaoException;
import br.pro.ramon.dcs.daos.DaoFactory;
import br.pro.ramon.dcs.daos.EventoDao;
import br.pro.ramon.dcs.daos.ProfessorDao;
import br.pro.ramon.dcs.entities.Evento;
import br.pro.ramon.dcs.entities.Professor;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/professor")
public class ProfessorResource {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String email, @FormParam("senha") String senha, @Context HttpServletRequest request) {
        try {
            ProfessorDao professorDao = DaoFactory.getProfessorDao();
            Professor professor = professorDao.findByEmailSenha(email, senha);

            if (professor == null) {
                Status status = Status.NOT_FOUND;
                return Response
                        .status(status)
                        .entity(String.format("{ \"status\": \"%d\", \"erro\": \"Usuário não encontrado.\" }", status.getStatusCode()))
                        .build();
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("professor", professor);

                Status status = Status.OK;
                return Response
                        .status(status)
                        .entity(String.format("{ \"status\": \"%d\", \"nome\": \"%s\" }", status.getStatusCode(), professor.getNome()))
                        .build();
            }
        } catch (DaoException ex) {
            Status status = Status.INTERNAL_SERVER_ERROR;
            return Response
                    .status(status)
                    .entity(String.format("{ \"status\": \"%d\", \"erro\": \"%s\" }", status.getStatusCode(), ex.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/logado")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProfessorLogado(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        Professor professor = (Professor) session.getAttribute("professor");

        if (professor == null) {
            return Response.seeOther(URI.create("index.html")).build();
        } else {
            return Response.ok(professor.getNome()).build();
        }
    }

    @GET
    @Path("/eventos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventos(@Context HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Professor professor = (Professor) session.getAttribute("professor");

            if (professor == null) {
                Status status = Status.UNAUTHORIZED;
                return Response
                        .status(status)
                        .entity(String.format("{ \"status\": \"%d\", \"erro\": \"Você precisa estar logado para ver os eventos.\" }", status.getStatusCode()))
                        .build();
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

                Status status = Status.OK;
                return Response
                        .status(status)
                        .entity(String.format("{ \"status\": \"%d\", \"eventos\": %s }", status.getStatusCode(), json.toString()))
                        .build();
            }
        } catch (DaoException ex) {
            Status status = Status.INTERNAL_SERVER_ERROR;
            return Response
                    .status(status)
                    .entity(String.format("{ \"status\": \"%d\", \"erro\": \"%s\" }", status.getStatusCode(), ex.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        // novo: após o logout, pode-se redirecionar para o index.html
        return Response.seeOther(URI.create(request.getContextPath() + "/index.html")).build();
    }

}
