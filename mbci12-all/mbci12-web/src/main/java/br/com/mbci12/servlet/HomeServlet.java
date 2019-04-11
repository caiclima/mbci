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
@WebServlet(name = "Home", asyncSupported = false, urlPatterns = "/pages/home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 8257986392458351401L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer url = req.getRequestURL();
		String uri = req.getRequestURI();
		RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/home02.xhtml");
		
		dispatcher.forward(req, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
