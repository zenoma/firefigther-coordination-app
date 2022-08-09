package es.udc.fireproject.backend.rest.dtos;

import org.locationtech.jts.geom.Coordinates;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

public class CuadrantDto extends BaseDto {

    private static final long serialVersionUID = 4848346612436497001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id1;

    private String escala;

    private String nombre;

    private String folla50;

    private String folla25;

    private String folla5;

    private List<Coordinates> coordinates;

    public CuadrantDto(Integer id1, String escala, String nombre, String folla50, String folla25, String folla5, List<Coordinates> coordinates) {
        this.id1 = id1;
        this.escala = escala;
        this.nombre = nombre;
        this.folla50 = folla50;
        this.folla25 = folla25;
        this.folla5 = folla5;
        this.coordinates = coordinates;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFolla50() {
        return folla50;
    }

    public void setFolla50(String folla50) {
        this.folla50 = folla50;
    }

    public String getFolla25() {
        return folla25;
    }

    public void setFolla25(String folla25) {
        this.folla25 = folla25;
    }

    public String getFolla5() {
        return folla5;
    }

    public void setFolla5(String folla5) {
        this.folla5 = folla5;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuadrantDto that = (CuadrantDto) o;
        return Objects.equals(id1, that.id1) && Objects.equals(escala, that.escala) && Objects.equals(nombre, that.nombre) && Objects.equals(folla50, that.folla50) && Objects.equals(folla25, that.folla25) && Objects.equals(folla5, that.folla5) && Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, escala, nombre, folla50, folla25, folla5, coordinates);
    }

    @Override
    public String toString() {
        return "CuadrantDto{" +
                "id1=" + id1 +
                ", escala='" + escala + '\'' +
                ", nombre='" + nombre + '\'' +
                ", folla50='" + folla50 + '\'' +
                ", folla25='" + folla25 + '\'' +
                ", folla5='" + folla5 + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
