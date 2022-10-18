package es.udc.fireproject.backend.rest.dtos;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

public class CuadrantInfoDto extends BaseDto {

    private static final long serialVersionUID = 4848346612436497001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String escala;

    private String nombre;

    public CuadrantInfoDto() {
    }

    public CuadrantInfoDto(Integer id, String escala, String nombre) {
        this.id = id;
        this.escala = escala;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    @Override
    public String toString() {
        return "CuadrantInfoDto{" +
                "id=" + id +
                ", escala='" + escala + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuadrantInfoDto that = (CuadrantInfoDto) o;
        return Objects.equals(id, that.id) && Objects.equals(escala, that.escala) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, escala, nombre);
    }
}
