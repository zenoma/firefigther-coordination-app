package es.udc.fireproject.backend.rest.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public class FireQuadrantLogDto extends BaseDto {
    private static final long serialVersionUID = -5703692848678170759L;

    private FireDto fireDto;

    private QuadrantInfoDto quadrantInfoDto;
    private LocalDateTime createdAt;
    private LocalDateTime extinguishedAt;

    public FireQuadrantLogDto() {
    }

    public FireQuadrantLogDto(FireDto fireDto, QuadrantInfoDto quadrantInfoDto, LocalDateTime createdAt, LocalDateTime extinguishedAt) {
        this.fireDto = fireDto;
        this.quadrantInfoDto = quadrantInfoDto;
        this.createdAt = createdAt;
        this.extinguishedAt = extinguishedAt;
    }

    public FireDto getFireDto() {
        return fireDto;
    }

    public void setFireDto(FireDto fireDto) {
        this.fireDto = fireDto;
    }

    public QuadrantInfoDto getQuadrantInfoDto() {
        return quadrantInfoDto;
    }

    public void setQuadrantInfoDto(QuadrantInfoDto quadrantInfoDto) {
        this.quadrantInfoDto = quadrantInfoDto;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireQuadrantLogDto that = (FireQuadrantLogDto) o;
        return Objects.equals(fireDto, that.fireDto) && Objects.equals(quadrantInfoDto, that.quadrantInfoDto) && Objects.equals(createdAt, that.createdAt) && Objects.equals(extinguishedAt, that.extinguishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fireDto, quadrantInfoDto, createdAt, extinguishedAt);
    }

    @Override
    public String toString() {
        return "FireQuadrantLogDto{" +
                "fireDto=" + fireDto +
                ", quadrantInfoDto=" + quadrantInfoDto +
                ", createdAt=" + createdAt +
                ", extinguishedAt=" + extinguishedAt +
                '}';
    }
}
