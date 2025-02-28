package models;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String address;
    private String nic;
    private String phone;

    // Constructor for user self-registration and admin-initiated registration
    public Customer(String name, String email, String username, String password, String address, String nic, String phone) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.nic = nic;
        this.phone = phone;
    }

    // Constructor for loading customer data (with ID)
    public Customer(int id, String name, String email, String username, String password, String address, String nic, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.nic = nic;
        this.phone = phone;
    }

    // Getters and Setters for each field
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public String getNic() { return nic; }
    public String getPhone() { return phone; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setAddress(String address) { this.address = address; }
    public void setNic(String nic) { this.nic = nic; }
    public void setPhone(String phone) { this.phone = phone; }
}
