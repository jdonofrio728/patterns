package jad.patterns.web.frontc;

import jad.patterns.log.Log;
import jad.patterns.web.frontc.command.Command;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Properties;

@WebServlet(name = "front", urlPatterns = {"/*"})
public class FrontServlet extends HttpServlet{
    private static final Log l = Log.getLogger(FrontServlet.class.getName());
    private static final String PACKAGE = "jad.patterns.web.frontc.command";
    private static final String DEFAULT_COMMAND = "AllPatientController";
    private Properties commandProps;

    @PostConstruct
    public void init(){
        l.info("Initializing bean");
        URL file = this.getClass().getResource("/mapping.properties");
        File f = new File(file.getFile());
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            commandProps = new Properties();
            commandProps.load(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            l.error("Error while loading properties");
            l.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        l.info("GET Call received " + req.getPathInfo());
        String path = req.getPathInfo();
        String command = parseCommand(path);
        try {
            String className = getClassName(command);
            l.info(command + "->" + className);
            Command c = (Command) Class.forName(className).getConstructor(HttpServletRequest.class, HttpServletResponse.class).newInstance(req, resp);
            c.process();
        }catch (Exception e){
            l.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        l.info("POST Call received");
        String path = req.getPathInfo();
        String command = parseCommand(path);
        try{
            String className = getClassName(command);
            l.info(command + "->" + className);
            Command c = (Command) Class.forName(className).getConstructor(HttpServletRequest.class, HttpServletResponse.class).newInstance(req, resp);
            c.processPost();
        }catch (Exception e){
            l.error(e);
        }
    }
    private String parseCommand(String path){
        String command = null;
        if(path.equals("/")){
            command = "root";
        }
        else{
            command = path.split("/")[1];
        }
        return command;
    }
    private String getClassName(String command){
        String className = commandProps.getProperty(command);
        if(className == null){
            // Do Something
        }
        return className;
    }
}