package models;
import java.sql.Timestamp;

public class Booking {
    private int id;
    private int customerId;
    private String vehicleType;
    private String pickupLocation;
    private String dropoffLocation;
    private double distance;
    private double fare;
    private String status;
    private Timestamp bookingTime;
    private String customerName; // Customer name field
    private int driverId; // Add driverId field

    // Constructor with all fields including customerName, driverId, and driverName
    public Booking(int id, int customerId, String vehicleType, String pickupLocation, String dropoffLocation, 
                   double distance, double fare, String status, Timestamp bookingTime, 
                   String customerName, int driverId) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleType = vehicleType;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.distance = distance;
        this.fare = fare;
        this.status = status;
        this.bookingTime = bookingTime;
        this.customerName = customerName; // Set customerName
        this.driverId = driverId; // Set driverId
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getVehicleType() { return vehicleType; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public double getDistance() { return distance; }
    public double getFare() { return fare; }
    public String getStatus() { return status; }
    public Timestamp getBookingTime() { return bookingTime; }
    public String getCustomerName() { return customerName; } // Getter for customerName
    public int getDriverId() { return driverId; } // Getter for driverId

    public void setId(int id) { this.id = id; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }
    public void setDistance(double distance) { this.distance = distance; }
    public void setFare(double fare) { this.fare = fare; }
    public void setStatus(String status) { this.status = status; }
    public void setBookingTime(Timestamp bookingTime) { this.bookingTime = bookingTime; }
    public void setCustomerName(String customerName) { this.customerName = customerName; } // Setter for customerName
    public void setDriverId(int driverId) { this.driverId = driverId; } // Setter for driverId
}