package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Taller.
 */
@Document(collection = "taller")
public class Taller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("marca")
    private String marca;

    @Field("modelo")
    private String modelo;

    @Field("matricula")
    private String matricula;

    @Field("color")
    private String color;

    @Field("num_serie")
    private String numSerie;

    @Field("generacion")
    private String generacion;

    @DBRef
    @Field("unidadservicio")
    private UnidadServicio unidadservicio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Taller id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return this.marca;
    }

    public Taller marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Taller modelo(String modelo) {
        this.setModelo(modelo);
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public Taller matricula(String matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getColor() {
        return this.color;
    }

    public Taller color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumSerie() {
        return this.numSerie;
    }

    public Taller numSerie(String numSerie) {
        this.setNumSerie(numSerie);
        return this;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getGeneracion() {
        return this.generacion;
    }

    public Taller generacion(String generacion) {
        this.setGeneracion(generacion);
        return this;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public UnidadServicio getUnidadservicio() {
        return this.unidadservicio;
    }

    public void setUnidadservicio(UnidadServicio unidadServicio) {
        this.unidadservicio = unidadServicio;
    }

    public Taller unidadservicio(UnidadServicio unidadServicio) {
        this.setUnidadservicio(unidadServicio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taller)) {
            return false;
        }
        return id != null && id.equals(((Taller) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Taller{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", matricula='" + getMatricula() + "'" +
            ", color='" + getColor() + "'" +
            ", numSerie='" + getNumSerie() + "'" +
            ", generacion='" + getGeneracion() + "'" +
            "}";
    }
}
