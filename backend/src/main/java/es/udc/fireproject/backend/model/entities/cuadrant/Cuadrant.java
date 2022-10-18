package es.udc.fireproject.backend.model.entities.cuadrant;

import es.udc.fireproject.backend.model.entities.fire.Fire;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.MultiPolygon;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cuadrants", schema = "public")
public class Cuadrant implements Serializable {

    private static final long serialVersionUID = 4848346612436497001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid", nullable = false)
    private Integer id;

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

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fire_id", nullable = false)
    private Fire fire;

    public Cuadrant() {
    }

    public Fire getFire() {
        return fire;
    }

    public void setFire(Fire fire) {
        this.fire = fire;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuadrant cuadrant = (Cuadrant) o;
        return Objects.equals(id, cuadrant.id) && Objects.equals(escala, cuadrant.escala) && Objects.equals(nombre, cuadrant.nombre) && Objects.equals(folla50, cuadrant.folla50) && Objects.equals(folla25, cuadrant.folla25) && Objects.equals(folla5, cuadrant.folla5) && Objects.equals(geom, cuadrant.geom) && Objects.equals(fire, cuadrant.fire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, escala, nombre, folla50, folla25, folla5, geom, fire);
    }

    @Override
    public String toString() {
        return "Cuadrant{" +
                "id=" + id +
                ", escala='" + escala + '\'' +
                ", nombre='" + nombre + '\'' +
                ", folla50='" + folla50 + '\'' +
                ", folla25='" + folla25 + '\'' +
                ", folla5='" + folla5 + '\'' +
                ", geom=" + geom +
                ", fire=" + fire +
                '}';
    }
}
