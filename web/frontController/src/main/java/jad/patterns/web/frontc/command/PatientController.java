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
import java.util.List;

public class PatientController extends Command{
    private static final Log l = Log.getLogger(PatientController.class.getName());
    private static String HTML;

    private HttpServletRequest req;
    private HttpServletResponse resp;

    static
    {
        l.info("Initializing command");
        URL url = AllPatientController.class.getResource("/patient.html");
        File file = new File(url.getFile());
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            HTML = new String(bytes);
        } catch (IOException e) {
            l.error(e);
        }

    }
    public PatientController(HttpServletRequest req, HttpServletResponse resp){
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void process() throws IOException{
        l.info("Processing request");
        PatientMapper pm = new PatientMapper();
        if(req.getParameter("id") == null){
            l.warning("Missing id parameter");
            resp.sendRedirect("/");
        }
        Long id = Long.parseLong(req.getParameter("id"));

        Patient p = pm.find(id);
        String html = HTML.replace("${fname}", p.getFirstName())
                .replace("${mname}", p.getMiddleName())
                .replace("${lname}", p.getLastName())
                .replace("${id}", p.getId().toString());
        resp.setContentType("text/html");
        resp.getWriter().write(html);
    }

    @Override
    public void processPost() throws IOException {
        l.info("Processing post request");
        Long id = Long.parseLong(req.getParameter("id"));
        String fname = req.getParameter("fname");
        String mname = req.getParameter("mname");
        String lname = req.getParameter("lname");
        Patient p = new Patient();
        p.setId(id);
        p.setFirstName(fname);
        p.setMiddleName(mname);
        p.setLastName(lname);
        PatientMapper m = new PatientMapper();
        m.update(p);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}