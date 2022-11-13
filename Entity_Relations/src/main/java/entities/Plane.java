package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle{
    @Basic
    private int passangerCapacity;
    private static String VEHICLE_TYPE = "PLANE";

    public Plane() {
        super(VEHICLE_TYPE);
    }

    public Plane(String model, String fuelType, int passangerCapacity) {
        this();
        this.model = model;
        this.fuelType = fuelType;
        this.passangerCapacity = passangerCapacity;
    }

    public int getPassangerCapacity() {
        return passangerCapacity;
    }

    public void setPassangerCapacity(int passangerCapacity) {
        this.passangerCapacity = passangerCapacity;
    }

    public static String getVehicleType() {
        return VEHICLE_TYPE;
    }

    public static void setVehicleType(String vehicleType) {
        VEHICLE_TYPE = vehicleType;
    }
}
