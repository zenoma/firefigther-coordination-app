package es.udc.fireproject.backend.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.udc.fireproject.backend.model.entities.fire.FireIndex;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class FireDto extends BaseDto {

    private static final long serialVersionUID = -91681558665988183L;

    private String description;

    private String type;

    private FireIndex fireIndex;

    private LocalDateTime createdAt;

    private LocalDateTime extinguishedAt;

    @JsonProperty("cuadrants")
    private List<CuadrantInfoDto> cuadrantDtoList;


    public FireDto() {
    }

    public FireDto(String description, String type, FireIndex fireIndex, List<CuadrantInfoDto> cuadrantDtoList, LocalDateTime createdAt, LocalDateTime extinguishedAt) {
        this.description = description;
        this.type = type;
        this.fireIndex = fireIndex;
        this.cuadrantDtoList = cuadrantDtoList;
        this.createdAt = createdAt;
        this.extinguishedAt = extinguishedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FireIndex getFireIndex() {
        return fireIndex;
    }

    public void setFireIndex(FireIndex fireIndex) {
        this.fireIndex = fireIndex;
    }

    public List<CuadrantInfoDto> getCuadrantDtoList() {
        return cuadrantDtoList;
    }

    public void setCuadrantDtoList(List<CuadrantInfoDto> cuadrantDtoList) {
        this.cuadrantDtoList = cuadrantDtoList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExtinguishedAt() {
        return extinguishedAt;
    }

    public void setExtinguishedAt(LocalDateTime extinguishedAt) {
        this.extinguishedAt = extinguishedAt;
    }

    @Override
    public String toString() {
        return "FireDto{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", fireIndex=" + fireIndex +
                ", createdAt=" + createdAt +
                ", extinguishedAt=" + extinguishedAt +
                ", cuadrantDtoList=" + cuadrantDtoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireDto fireDto = (FireDto) o;
        return Objects.equals(description, fireDto.description) && Objects.equals(type, fireDto.type) && fireIndex == fireDto.fireIndex && Objects.equals(createdAt, fireDto.createdAt) && Objects.equals(extinguishedAt, fireDto.extinguishedAt) && Objects.equals(cuadrantDtoList, fireDto.cuadrantDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, type, fireIndex, createdAt, extinguishedAt, cuadrantDtoList);
    }
}
