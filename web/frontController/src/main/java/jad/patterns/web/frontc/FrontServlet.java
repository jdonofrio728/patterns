package jad.patterns.web.frontc;

import jad.patterns.log.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "front", urlPatterns = {"/*"})
public class FrontServlet extends HttpServlet{
    private static final Log l = Log.getLogger(FrontServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        l.info("GET Call received");
        resp.setContentType("text/plain");
        resp.getWriter().write("Test Call");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        l.info("POST Call received");
        super.doPost(req, resp);
    }
}