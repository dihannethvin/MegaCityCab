package models;

public class Vehicle {
    private int vehicleId;
    private String plateNumber;
    private String vehicleType;

    // Constructor
    public Vehicle(int vehicleId, String plateNumber, String vehicleType) {
        this.vehicleId = vehicleId;
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
    }

    // Getters and Setters
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}
