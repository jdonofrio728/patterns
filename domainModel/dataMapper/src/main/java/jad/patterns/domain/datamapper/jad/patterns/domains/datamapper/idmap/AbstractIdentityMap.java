package jad.patterns.domain.datamapper.jad.patterns.domains.datamapper.idmap;

import jad.patterns.data.model.DomainModel;
import jad.patterns.log.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdonofrio on 10/6/15.
 *
 * Basic identity map which lacks full scale features like size limitations, TTL,
 * and eviction policy.
 */
public abstract class AbstractIdentityMap<T extends DomainModel> {
    private static final Log l = Log.getLogger(AbstractIdentityMap.class.getName());
    private static Map<Class<?>, Map<Long, DomainModel>> identityMaps;

    protected AbstractIdentityMap(){}

    public abstract T getObject(Long id);
    public abstract void putObject(Long id, T obj);

    protected Map<Long, DomainModel> getObjectMap(Class<T> type){
        if(identityMaps == null){
            identityMaps = new HashMap<>();
        }
        Map<Long, DomainModel> map = identityMaps.get(type);
        if(map == null){
            map = new HashMap<>();
            identityMaps.put(type, map);
        }
        return map;
    }
}
