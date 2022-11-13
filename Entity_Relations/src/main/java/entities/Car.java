package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends PassengerVehicle{

    private static String VEHICLE_TYPE = "CAR";

    public Car() {
        super(VEHICLE_TYPE);
    }

    public Car(String model, String fuelType, int seats) {
        this();
        this.model = model;
        this.fuelType = fuelType;
        this.seats = seats;
    }


    public static String getVehicleType() {
        return VEHICLE_TYPE;
    }

    public static void setVehicleType(String vehicleType) {
        VEHICLE_TYPE = vehicleType;
    }
}
