package br.pro.ramon.dcs.resources;

import br.pro.ramon.dcs.resources.respostas.RespostaProfessor;
import br.pro.ramon.dcs.resources.respostas.RespostaEventos;
import br.pro.ramon.dcs.resources.respostas.RespostaErro;
import br.pro.ramon.dcs.daos.DaoException;
import br.pro.ramon.dcs.daos.DaoFactory;
import br.pro.ramon.dcs.daos.EventoDao;
import br.pro.ramon.dcs.daos.ProfessorDao;
import br.pro.ramon.dcs.entities.Evento;
import br.pro.ramon.dcs.entities.Professor;
import java.net.URI;
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
                RespostaErro resposta = new RespostaErro(status, "Usuário não encontrado.");
                return Response.status(status).entity(resposta).build();
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("professor", professor);

                Status status = Status.OK;
                RespostaProfessor resposta = new RespostaProfessor(status, professor);
                return Response.status(status).entity(resposta).build();
            }
        } catch (DaoException ex) {
            Status status = Status.INTERNAL_SERVER_ERROR;
            RespostaErro resposta = new RespostaErro(status, ex.getMessage());
            return Response.status(status).entity(resposta).build();
        }
    }

    @GET
    @Path("/logado")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProfessorLogado(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        Professor professor = (Professor) session.getAttribute("professor");

        if (professor == null) {
            return Response.seeOther(URI.create("../index.html")).build();
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
                RespostaErro resposta = new RespostaErro(status, "Você precisa estar logado para ver os eventos.");
                return Response.status(status).entity(resposta).build();
            } else {
                EventoDao eventoDao = DaoFactory.getEventoDao();
                List<Evento> eventos = eventoDao.findAllByProfessor(professor);

                Status status = Status.OK;
                RespostaEventos resposta = new RespostaEventos(status, eventos);
                return Response.status(status).entity(resposta).build();
            }
        } catch (DaoException ex) {
            Status status = Status.INTERNAL_SERVER_ERROR;
            RespostaErro resposta = new RespostaErro(status, ex.getMessage());
            return Response.status(status).entity(resposta).build();
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
