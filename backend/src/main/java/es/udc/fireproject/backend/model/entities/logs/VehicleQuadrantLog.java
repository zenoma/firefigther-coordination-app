package es.udc.fireproject.backend.model.entities.logs;

import es.udc.fireproject.backend.model.entities.BaseEntity;
import es.udc.fireproject.backend.model.entities.quadrant.Quadrant;
import es.udc.fireproject.backend.model.entities.vehicle.Vehicle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vehicle_quadrant_log", schema = "public")
public class VehicleQuadrantLog extends BaseEntity {
    private static final long serialVersionUID = -7462039001706132620L;


    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "quadrant_gid", nullable = false)
    private Quadrant quadrant;


    @Column(name = "deploy_at", nullable = false)
    private LocalDateTime deployAt;
    @Column(name = "retract_at", nullable = false)
    private LocalDateTime retractAt;

    public VehicleQuadrantLog() {
    }

    public VehicleQuadrantLog(Vehicle fire, Quadrant quadrant, LocalDateTime deployAt, LocalDateTime retractAt) {
        this.vehicle = fire;
        this.quadrant = quadrant;
        this.deployAt = deployAt;
        this.retractAt = retractAt;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(Quadrant quadrant) {
        this.quadrant = quadrant;
    }

    public LocalDateTime getDeployAt() {
        return deployAt;
    }

    public void setDeployAt(LocalDateTime deployAt) {
        this.deployAt = deployAt;
    }

    public LocalDateTime getRetractAt() {
        return retractAt;
    }

    public void setRetractAt(LocalDateTime retractAt) {
        this.retractAt = retractAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleQuadrantLog that = (VehicleQuadrantLog) o;
        return Objects.equals(vehicle, that.vehicle) && Objects.equals(quadrant, that.quadrant) && Objects.equals(deployAt, that.deployAt) && Objects.equals(retractAt, that.retractAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, quadrant, deployAt, retractAt);
    }

    @Override
    public String toString() {
        return "VehicleQuadrantLog{" +
                "vehicle=" + vehicle +
                ", quadrant=" + quadrant +
                ", deployAt=" + deployAt +
                ", retractAt=" + retractAt +
                '}';
    }
}
