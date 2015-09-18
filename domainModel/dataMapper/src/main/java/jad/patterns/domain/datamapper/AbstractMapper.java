package jad.patterns.domain.datamapper;

import jad.patterns.data.model.DomainModel;
import jad.patterns.data.model.helper.ConnectionHelper;

import java.sql.*;

public abstract class AbstractMapper {

    protected Connection getConnection(){
        return ConnectionHelper.createConnection();
    }
    protected abstract String findStatement();
    public DomainModel find(Long id){
        Connection c = getConnection();
        try {
            PreparedStatement s = c.prepareStatement(findStatement());
            s.setLong(1, id.longValue());
            ResultSet rs = s.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}