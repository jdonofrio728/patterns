package jad.patterns.data.model;

/**
 * Created by jdonofrio on 9/7/15.
 */
public abstract class DomainModel {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
