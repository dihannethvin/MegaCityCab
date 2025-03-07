package models;

import java.sql.Timestamp;

public class Booking {
    private int id;
    private int customerId;
    private String vehicleType;
    private String pickupLocation;
    private String dropoffLocation;
    private double fare;
    private String status;
    private Timestamp bookingTime;
    private String customerName;
    private Integer driverId; // Make this nullable
    private String driverName; // Driver's name
    private Integer vehicleId; // Make this nullable
    private String plateNumber; // Vehicle's plate number

    // Constructor with all fields including customerName, driverId, driverName, vehicleId, and plateNumber
    public Booking(int id, int customerId, String vehicleType, String pickupLocation, String dropoffLocation,
                   double fare, String status, Timestamp bookingTime, 
                   String customerName, Integer driverId, String driverName, Integer vehicleId, String plateNumber) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleType = vehicleType;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.fare = fare;
        this.status = status;
        this.bookingTime = bookingTime;
        this.customerName = customerName;
        this.driverId = driverId;
        this.driverName = driverName;
        this.vehicleId = vehicleId;
        this.plateNumber = plateNumber;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getVehicleType() { return vehicleType; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public double getFare() { return fare; }
    public String getStatus() { return status; }
    public Timestamp getBookingTime() { return bookingTime; }
    public String getCustomerName() { return customerName; }
    public Integer getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public Integer getVehicleId() { return vehicleId; }
    public String getPlateNumber() { return plateNumber; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }
    public void setFare(double fare) { this.fare = fare; }
    public void setStatus(String status) { this.status = status; }
    public void setBookingTime(Timestamp bookingTime) { this.bookingTime = bookingTime; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
}
