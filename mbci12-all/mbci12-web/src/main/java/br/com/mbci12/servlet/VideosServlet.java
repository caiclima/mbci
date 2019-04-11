package br.com.mbci12.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Ednaldo Caic
 * @since 25/03/2012
 * 
 */
@WebServlet(name = "videos", asyncSupported = false, urlPatterns = "/videos")
public class VideosServlet extends HttpServlet {

	private static final long serialVersionUID = 8257986392458351401L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/videos.xhtml");
			dispatcher.forward(req, response);
		} catch (ServletException e) {
			Logger.getLogger(VideosServlet.class.getName()).log(Level.SEVERE, "Erro ao direcionar para pagina inicial");
			response.setContentType("text/plain");
			response.getWriter().println("Desculpe, mas no momento esta p\u00E1gina n\u00E3o est\u00E1 dispon\u00EDvel");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
