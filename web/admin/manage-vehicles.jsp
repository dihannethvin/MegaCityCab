<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Vehicle" %>
<%
    List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Vehicles</title>
    <link rel="stylesheet" href="styles.css"> <!-- Include your CSS file -->
    <script>
        function setUpdateForm(id, plateNumber, vehicleType) {
            document.getElementById("updateId").value = id;
            document.getElementById("updatePlateNumber").value = plateNumber;
            document.getElementById("updateVehicleType").value = vehicleType;
        }
        function confirmDelete(id) {
            if (confirm("Are you sure you want to delete this vehicle?")) {
                document.getElementById("deleteForm" + id).submit();
            }
        }
    </script>
</head>
<body>
    <h2>Manage Vehicles</h2>

    <!-- Add Vehicle Button -->
    <button onclick="document.getElementById('addModal').style.display='block'">+ Add Vehicle</button>

    <!-- Vehicles Table -->
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Plate Number</th>
                <th>Vehicle Type</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% for (Vehicle vehicle : vehicleList) { %>
            <tr>
                <td><%= vehicle.getVehicleId() %></td> <!-- Updated to getVehicleId() -->
                <td><%= vehicle.getPlateNumber() %></td>
                <td><%= vehicle.getVehicleType() %></td>
                <td>
                    <button onclick="setUpdateForm('<%= vehicle.getVehicleId() %>', '<%= vehicle.getPlateNumber() %>', '<%= vehicle.getVehicleType() %>'); document.getElementById('updateModal').style.display='block'">Edit</button>
                    
                    <form id="deleteForm<%= vehicle.getVehicleId() %>" action="delete-vehicle" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= vehicle.getVehicleId() %>">
                        <button type="button" onclick="confirmDelete(<%= vehicle.getVehicleId() %>)">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <!-- Add Vehicle Modal -->
    <div id="addModal" style="display:none;">
        <form action="add-vehicle" method="post">
            <h3>Add Vehicle</h3>
            <label>Plate Number:</label>
            <input type="text" name="plate_number" required>
            <label>Vehicle Type:</label>
            <select name="vehicle_type" required>
                <option value="car">Car</option>
                <option value="suv">SUV</option>
            </select>
            <button type="submit">Add</button>
            <button type="button" onclick="document.getElementById('addModal').style.display='none'">Cancel</button>
        </form>
    </div>

    <!-- Update Vehicle Modal -->
    <div id="updateModal" style="display:none;">
        <form action="update-vehicle" method="post">
            <h3>Update Vehicle</h3>
            <input type="hidden" id="updateId" name="id">
            <label>Plate Number:</label>
            <input type="text" id="updatePlateNumber" name="plate_number" required>
            <label>Vehicle Type:</label>
            <select id="updateVehicleType" name="vehicle_type" required>
                <option value="car">Car</option>
                <option value="suv">SUV</option>
            </select>
            <button type="submit">Update</button>
            <button type="button" onclick="document.getElementById('updateModal').style.display='none'">Cancel</button>
        </form>
    </div>
</body>
</html>
