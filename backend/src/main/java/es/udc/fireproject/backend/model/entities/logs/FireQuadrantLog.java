package es.udc.fireproject.backend.model.entities.logs;

import es.udc.fireproject.backend.model.entities.BaseEntity;
import es.udc.fireproject.backend.model.entities.fire.Fire;
import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "fire_quadrant_log", schema = "public")
public class FireQuadrantLog extends BaseEntity {

    private static final long serialVersionUID = -7339295013066556565L;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fire_id", nullable = false)
    private Fire fire;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "quadrant_gid", nullable = false)
    private Quadrant quadrant;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "extinguished_at", nullable = false)
    private LocalDateTime extinguishedAt;

    public FireQuadrantLog() {
    }

    public FireQuadrantLog(Fire fire, Quadrant quadrant, LocalDateTime createdAt) {
        this.fire = fire;
        this.quadrant = quadrant;
        this.createdAt = createdAt;
    }

    public Fire getFire() {
        return fire;
    }

    public void setFire(Fire fire) {
        this.fire = fire;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(Quadrant quadrant) {
        this.quadrant = quadrant;
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
        FireQuadrantLog that = (FireQuadrantLog) o;
        return Objects.equals(fire, that.fire) && Objects.equals(quadrant, that.quadrant) && Objects.equals(createdAt, that.createdAt) && Objects.equals(extinguishedAt, that.extinguishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fire, quadrant, createdAt, extinguishedAt);
    }

    @Override
    public String toString() {
        return "FireQuadrantLog{" +
                "fire=" + fire +
                ", quadrant=" + quadrant +
                ", createdAt=" + createdAt +
                ", extinguishedAt=" + extinguishedAt +
                '}';
    }
}
