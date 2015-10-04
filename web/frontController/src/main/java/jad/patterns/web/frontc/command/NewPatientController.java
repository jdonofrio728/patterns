package jad.patterns.web.frontc.command;

import jad.patterns.data.model.Patient;
import jad.patterns.domain.datamapper.PatientMapper;
import jad.patterns.log.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/**
 * Created by jdonofrio on 10/4/15.
 */
public class NewPatientController extends Command {
    private static final Log l = Log.getLogger(NewPatientController.class.getName());
    private static String HTML;

    private HttpServletRequest req;
    private HttpServletResponse resp;

    static{
        l.info("Initializing command");
        URL url = AllPatientController.class.getResource("/newPatient.html");
        File file = new File(url.getFile());
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            HTML = new String(bytes);
        } catch (IOException e) {
            l.error(e);
        }
    }

    public NewPatientController(HttpServletRequest req, HttpServletResponse resp){
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void process() throws IOException {
        l.info("Processing request");

        resp.setContentType("text/html");
        resp.getWriter().write(HTML);
    }

    @Override
    public void processPost() throws IOException {
        l.info("Processing post request");
        String fname = req.getParameter("fname");
        String mname = req.getParameter("mname");
        String lname = req.getParameter("lname");
        Patient p = new Patient();
        p.setFirstName(fname);
        p.setMiddleName(mname);
        p.setLastName(lname);
        PatientMapper m = new PatientMapper();
        m.insert(p);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
