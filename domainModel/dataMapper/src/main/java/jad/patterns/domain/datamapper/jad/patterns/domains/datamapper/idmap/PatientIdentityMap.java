package jad.patterns.domain.datamapper.jad.patterns.domains.datamapper.idmap;

import jad.patterns.data.model.DomainModel;
import jad.patterns.data.model.Patient;
import jad.patterns.log.Log;

import java.util.Map;

/**
 * Created by jdonofrio on 10/6/15.
 */
public class PatientIdentityMap extends AbstractIdentityMap<Patient> {
    private static final Log l = Log.getLogger(PatientIdentityMap.class.getName());
    @Override
    public Patient getObject(Long id) {
        l.info("Fetching patient with id from cache " + id);
        Map<Long, DomainModel> map = getObjectMap(Patient.class);
        Patient p = (Patient) map.get(id);
        return p;
    }

    @Override
    public void putObject(Long id, Patient obj) {
        l.info("Adding patient with id to identity map " + id);
        Map<Long, DomainModel> map = getObjectMap(Patient.class);
        map.put(id, obj);
    }
}
