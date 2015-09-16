package jad.patterns.domain.datamapper;

import jad.patterns.domain.model.DomainModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractMapper {
    protected Connection getConnection(){
        return null;
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