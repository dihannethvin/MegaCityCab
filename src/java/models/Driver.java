package models;

public class Driver {
    private int id;
    private String name;
    private String phone;
    private String gender;
    private String vehicleType;
    private String licenseNumber;

    // Default Constructor
    public Driver() {
    }

    // Parameterized Constructor
    public Driver(int id, String name, String phone, String gender, String vehicleType, String licenseNumber) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
}