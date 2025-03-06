package models;

public class Vehicle {
    private int id; // This corresponds to 'id' in the database
    private String plateNumber;
    private String vehicleType;

    // Constructor
    public Vehicle(int vehicleId, String plateNumber, String vehicleType) {
        this.id = id; // Maps to 'id' in the DB
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
    }

    // Getters and Setters
    public int getId() { return id; } // Maps to 'id'
    public void setId(int id) { this.id = id; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}
