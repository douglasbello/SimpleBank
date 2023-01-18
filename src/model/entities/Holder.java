package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Holder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String document;
    private Date birthDate;

    public Holder() {

    }

    public Holder(String name, String document, Date birthDate) {
        this.name = name;
        this.document = document;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
    	return "id = " + id + ", name = " + name + ", document = " + document + ", birth date = " + birthDate;
    }
}
