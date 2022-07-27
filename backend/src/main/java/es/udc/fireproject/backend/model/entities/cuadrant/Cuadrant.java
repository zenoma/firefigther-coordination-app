package es.udc.fireproject.backend.model.entities.cuadrant;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.MultiPolygon;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cuadrants", schema = "public")
public class Cuadrant {

    private static final long serialVersionUID = 4848346612436497001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid", nullable = false)
    private Integer id1;

    @Column(name = "escala", length = 50)
    private String escala;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "folla50", length = 50)
    private String folla50;

    @Column(name = "folla25", length = 50)
    private String folla25;

    @Column(name = "folla5", length = 50)
    private String folla5;

    @Type(type = "org.locationtech.jts.geom.MultiPolygon")
    @Column(name = "geom")
    private MultiPolygon geom;

    public Cuadrant() {
    }

    public MultiPolygon getGeom() {
        return geom;
    }

    public void setGeom(MultiPolygon geom) {
        this.geom = geom;
    }

    public String getFolla5() {
        return folla5;
    }

    public void setFolla5(String folla5) {
        this.folla5 = folla5;
    }

    public String getFolla25() {
        return folla25;
    }

    public void setFolla25(String folla25) {
        this.folla25 = folla25;
    }

    public String getFolla50() {
        return folla50;
    }

    public void setFolla50(String folla50) {
        this.folla50 = folla50;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuadrant cuadrant = (Cuadrant) o;
        return Objects.equals(id1, cuadrant.id1) && Objects.equals(escala, cuadrant.escala) && Objects.equals(nombre, cuadrant.nombre) && Objects.equals(folla50, cuadrant.folla50) && Objects.equals(folla25, cuadrant.folla25) && Objects.equals(folla5, cuadrant.folla5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, escala, nombre, folla50, folla25, folla5);
    }

    @Override
    public String toString() {
        return "Cuadrant{" +
                "id1=" + id1 +
                ", escala='" + escala + '\'' +
                ", nombre='" + nombre + '\'' +
                ", folla50='" + folla50 + '\'' +
                ", folla25='" + folla25 + '\'' +
                ", folla5='" + folla5 + '\'' +
                '}';
    }
}
