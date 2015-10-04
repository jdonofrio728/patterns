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

/**
 * Created by jdonofrio on 10/3/15.
 */
public class AllPatientController extends Command {
    private static final Log l = Log.getLogger(AllPatientController.class.getName());
    private static String HTML;

    private HttpServletRequest req;
    private HttpServletResponse resp;

    static
    {
        l.info("Initializing command");
        URL url = AllPatientController.class.getResource("/index.html");
        File file = new File(url.getFile());
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            HTML = new String(bytes);
        } catch (IOException e) {
            l.error(e);
        }

    }

    public AllPatientController(){}
    public AllPatientController(HttpServletRequest req, HttpServletResponse resp){
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void process() throws IOException {
        l.info("Processing request");
        PatientMapper pm = new PatientMapper();
        if(req.getParameter("delete") != null){
            Long id = Long.parseLong(req.getParameter("delete"));
            pm.delete(id);
        }
        StringBuilder sb = new StringBuilder();

        List<Patient> list = pm.findAll();
        for(Patient p : list){
            sb.append("<tr>")
                    .append(patientToTableData(p))
                    .append("</tr>");
        }
        resp.setContentType("text/html");
        resp.getWriter().write(HTML.replace("${DATA}", sb.toString()));
    }

    @Override
    public void processPost() throws IOException {
        process();
    }

    protected String patientToTableData(Patient p){
        StringBuilder sb = new StringBuilder();
        return sb.append("<td><a href=\"/web-frontController/patient?id=")
                .append(p.getId())
                .append("\">")
                .append(p.getId())
                .append("</a></td><td>")
                .append(p.getFirstName())
                .append("</td><td>")
                .append(p.getMiddleName())
                .append("</td><td>")
                .append(p.getLastName())
                .append("</td><td><form><button name=\"delete\" value=\"")
                .append(p.getId())
                .append("\">Delete</button></form></td>")
                .toString();
    }
}
