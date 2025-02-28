<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Driver" %>
<%
    List<Driver> driverList = (List<Driver>) request.getAttribute("driverList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Drivers</title>
    <link rel="stylesheet" href="styles.css"> <!-- Include your CSS -->
    <script>
        function setUpdateForm(id, name, phone, gender, vehicleType, licenseNumber, carId) {
            document.getElementById("updateId").value = id;
            document.getElementById("updateName").value = name;
            document.getElementById("updatePhone").value = phone;
            document.getElementById("updateGender").value = gender;
            document.getElementById("updateVehicleType").value = vehicleType;
            document.getElementById("updateLicenseNumber").value = licenseNumber;
            document.getElementById("updateCarId").value = carId;
        }
        function confirmDelete(id) {
            if (confirm("Are you sure you want to delete this driver?")) {
                document.getElementById("deleteForm" + id).submit();
            }
        }
    </script>
</head>
<body>
    <h2>Manage Drivers</h2>

    <!-- Add Driver Button -->
    <button onclick="document.getElementById('addModal').style.display='block'">+ Add Driver</button>

    <!-- Drivers Table -->
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Gender</th>
                <th>Vehicle Type</th>
                <th>License Number</th>
                <th>Car ID</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% for (Driver driver : driverList) { %>
            <tr>
                <td><%= driver.getId() %></td>
                <td><%= driver.getName() %></td>
                <td><%= driver.getPhone() %></td>
                <td><%= driver.getGender() %></td>
                <td><%= driver.getVehicleType() %></td>
                <td><%= driver.getLicenseNumber() %></td>
                <td><%= driver.getCarId() %></td>
                <td>
                    <button onclick="setUpdateForm('<%= driver.getId() %>', '<%= driver.getName() %>', '<%= driver.getPhone() %>', '<%= driver.getGender() %>', '<%= driver.getVehicleType() %>', '<%= driver.getLicenseNumber() %>', '<%= driver.getCarId() %>'); document.getElementById('updateModal').style.display='block'">Edit</button>

                    <form id="deleteForm<%= driver.getId() %>" action="delete-driver" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= driver.getId() %>">
                        <button type="button" onclick="confirmDelete(<%= driver.getId() %>)">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <!-- Add Driver Modal -->
    <div id="addModal" style="display:none;">
        <form action="add-driver" method="post">
            <h3>Add Driver</h3>
            <label>Name:</label>
            <input type="text" name="name" required>
            <label>Phone:</label>
            <input type="text" name="phone" required>
            <label>Gender:</label>
            <select name="gender" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
            <label>Vehicle Type:</label>
            <select name="vehicle_type" required>
                <option value="Car">Car</option>
                <option value="SUV">SUV</option>
            </select>
            <label>License Number:</label>
            <input type="text" name="license_number" required>
            <label>Car ID:</label>
            <input type="text" name="car_id" required>
            <button type="submit">Add</button>
            <button type="button" onclick="document.getElementById('addModal').style.display='none'">Cancel</button>
        </form>
    </div>

    <!-- Update Driver Modal -->
    <div id="updateModal" style="display:none;">
        <form action="update-driver" method="post">
            <h3>Update Driver</h3>
            <input type="hidden" id="updateId" name="id">
            <label>Name:</label>
            <input type="text" id="updateName" name="name" required>
            <label>Phone:</label>
            <input type="text" id="updatePhone" name="phone" required>
            <label>Gender:</label>
            <select id="updateGender" name="gender" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
            <label>Vehicle Type:</label>
            <select id="updateVehicleType" name="vehicle_type" required>
                <option value="Car">Car</option>
                <option value="SUV">SUV</option>
            </select>
            <label>License Number:</label>
            <input type="text" id="updateLicenseNumber" name="license_number" required>
            <label>Car ID:</label>
            <input type="text" id="updateCarId" name="car_id" required>
            <button type="submit">Update</button>
            <button type="button" onclick="document.getElementById('updateModal').style.display='none'">Cancel</button>
        </form>
    </div>
</body>
</html>
