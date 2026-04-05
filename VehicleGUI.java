package advancedproject1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class VehicleGUI extends Application {
    
    private VehicleRentalSystem rentalSystem;
    private VBox vehicleListBox;
    private VBox customerListBox;
    private VBox bookingListBox;
    
    @Override
    public void start(Stage primaryStage) {
        rentalSystem = new VehicleRentalSystem();
        initializeSampleData();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        Tab vehicleTab = createVehicleTab();
        Tab customerTab = createCustomerTab();
        Tab bookingTab = createBookingTab();
        
        tabPane.getTabs().addAll(vehicleTab, customerTab, bookingTab);
        
        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setTitle("Vehicle Rental System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void initializeSampleData() {
        Car car1 = new Car(101, "Toyota Camry", 50.0, 2023, 5, "Petrol", true);
        Car car2 = new Car(102, "Honda Accord", 45.0, 2022, 5, "Hybrid", false);
        Bike bike1 = new Bike(201, "Yamaha R15", 25.0, 2023, 155, false);
        Van van1 = new Van(301, "Ford Transit", 80.0, 2022, 12.5, true);
        
        rentalSystem.addVehicle(car1);
        rentalSystem.addVehicle(car2);
        rentalSystem.addVehicle(bike1);
        rentalSystem.addVehicle(van1);
        
        Customer cust1 = new Customer("C001", "John Smith", "john@email.com", "1234567890");
        Customer cust2 = new Customer("C002", "Sarah Johnson", "sarah@email.com", "9876543210");
        
        // Verify sample customers
        cust1.setVerified(true);
        cust2.setVerified(true);
        
        rentalSystem.addCustomer(cust1);
        rentalSystem.addCustomer(cust2);
    }
    
    private Tab createVehicleTab() {
        Tab tab = new Tab("Vehicles");
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15));
        
        // Scrollable list area
        ScrollPane scrollPane = new ScrollPane();
        vehicleListBox = new VBox(10);
        vehicleListBox.setPadding(new Insets(10));
        scrollPane.setContent(vehicleListBox);
        scrollPane.setFitToWidth(true);
        refreshVehicleList();
        
        // Button panel
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addCarBtn = new Button("Add Car");
        Button addBikeBtn = new Button("Add Bike");
        Button addVanBtn = new Button("Add Van");
        Button sortBtn = new Button("Sort by Model");
        Button refreshBtn = new Button("Refresh");
        
        addCarBtn.setOnAction(e -> showAddVehicleDialog("Car"));
        addBikeBtn.setOnAction(e -> showAddVehicleDialog("Bike"));
        addVanBtn.setOnAction(e -> showAddVehicleDialog("Van"));
        sortBtn.setOnAction(e -> {
            rentalSystem.sortVehiclesByModel();
            refreshVehicleList();
        });
        refreshBtn.setOnAction(e -> refreshVehicleList());
        
        buttonBox.getChildren().addAll(addCarBtn, addBikeBtn, addVanBtn, sortBtn, refreshBtn);
        
        layout.setCenter(scrollPane);
        layout.setBottom(buttonBox);
        tab.setContent(layout);
        return tab;
    }
    
    private void refreshVehicleList() {
        vehicleListBox.getChildren().clear();
        for (Vehicle v : rentalSystem.getVehicles()) {
            VBox card = createVehicleCard(v);
            vehicleListBox.getChildren().add(card);
        }
    }
    
    private VBox createVehicleCard(Vehicle v) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        
        Label title = new Label(v.getModel() + " (" + v.getClass().getSimpleName() + ")");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        // Build detailed information based on vehicle type
        StringBuilder detailsText = new StringBuilder();
        detailsText.append("ID: ").append(v.getVehicleId())
                   .append(" | Year: ").append(v.getYear())
                   .append(" | Rate: $").append(v.getDailyRate()).append("/day")
                   .append("\nStatus: ").append(v.isAvailable() ? "Available" : "Rented");
        
        // Add specific details based on vehicle type
        if (v instanceof Car) {
            Car car = (Car) v;
            detailsText.append("\nSeats: ").append(car.getNumSeats())
                      .append(" | Fuel: ").append(car.getFuelType())
                      .append(" | GPS: ").append(car.isHasGPS() ? "Yes" : "No");
        } else if (v instanceof Bike) {
            Bike bike = (Bike) v;
            detailsText.append("\nEngine: ").append(bike.getEngineCC() > 0 ? bike.getEngineCC() + "cc" : "N/A")
                      .append(" | Electric: ").append(bike.isElectric() ? "Yes" : "No");
        } else if (v instanceof Van) {
            Van van = (Van) v;
            detailsText.append("\nCargo Capacity: ").append(van.getCargoCapacity()).append(" m³")
                      .append(" | AC: ").append(van.isHasAC() ? "Yes" : "No");
        }
        
        Label details = new Label(detailsText.toString());
        
        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction(e -> {
            if (rentalSystem.removeVehicle(v.getVehicleId())) {
                refreshVehicleList();
            } else {
                showAlert("Error", "Cannot remove rented vehicle.");
            }
        });
        
        card.getChildren().addAll(title, details, removeBtn);
        return card;
    }
    
    private void showAddVehicleDialog(String type) {
        Dialog<Vehicle> dialog = new Dialog<>();
        dialog.setTitle("Add " + type);
        
        ButtonType addBtn = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField idField = new TextField();
        TextField modelField = new TextField();
        TextField yearField = new TextField();
        TextField rateField = new TextField();
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Model:"), 0, 1);
        grid.add(modelField, 1, 1);
        grid.add(new Label("Year:"), 0, 2);
        grid.add(yearField, 1, 2);
        grid.add(new Label("Daily Rate:"), 0, 3);
        grid.add(rateField, 1, 3);
        
        int row = 4;
        TextField extraField1 = new TextField();
        TextField extraField2 = new TextField();
        CheckBox checkBox = new CheckBox();
        
        if (type.equals("Car")) {
            grid.add(new Label("Seats:"), 0, row);
            grid.add(extraField1, 1, row++);
            grid.add(new Label("Fuel Type:"), 0, row);
            grid.add(extraField2, 1, row++);
            grid.add(new Label("Has GPS:"), 0, row);
            grid.add(checkBox, 1, row);
        } else if (type.equals("Bike")) {
            grid.add(new Label("Engine CC:"), 0, row);
            grid.add(extraField1, 1, row++);
            grid.add(new Label("Electric:"), 0, row);
            grid.add(checkBox, 1, row);
        } else {
            grid.add(new Label("Cargo (m³):"), 0, row);
            grid.add(extraField1, 1, row++);
            grid.add(new Label("Has AC:"), 0, row);
            grid.add(checkBox, 1, row);
        }
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(btn -> {
            if (btn == addBtn) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String model = modelField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    double rate = Double.parseDouble(rateField.getText());
                    
                    Vehicle vehicle = null;
                    if (type.equals("Car")) {
                        int seats = Integer.parseInt(extraField1.getText());
                        String fuel = extraField2.getText();
                        vehicle = new Car(id, model, rate, year, seats, fuel, checkBox.isSelected());
                    } else if (type.equals("Bike")) {
                        int engineCC = Integer.parseInt(extraField1.getText());
                        vehicle = new Bike(id, model, rate, year, engineCC, checkBox.isSelected());
                    } else {
                        double cargo = Double.parseDouble(extraField1.getText());
                        vehicle = new Van(id, model, rate, year, cargo, checkBox.isSelected());
                    }
                    
                    if (rentalSystem.addVehicle(vehicle)) {
                        refreshVehicleList();
                    }
                    return vehicle;
                } catch (Exception e) {
                    showAlert("Error", "Invalid input: " + e.getMessage());
                }
            }
            return null;
        });
        
        dialog.showAndWait();
    }
    
    private Tab createCustomerTab() {
        Tab tab = new Tab("Customers");
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15));
        
        ScrollPane scrollPane = new ScrollPane();
        customerListBox = new VBox(10);
        customerListBox.setPadding(new Insets(10));
        scrollPane.setContent(customerListBox);
        scrollPane.setFitToWidth(true);
        refreshCustomerList();
        
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Add Customer");
        Button refreshBtn = new Button("Refresh");
        
        addBtn.setOnAction(e -> showAddCustomerDialog());
        refreshBtn.setOnAction(e -> refreshCustomerList());
        
        buttonBox.getChildren().addAll(addBtn, refreshBtn);
        
        layout.setCenter(scrollPane);
        layout.setBottom(buttonBox);
        tab.setContent(layout);
        return tab;
    }
    
    private void refreshCustomerList() {
        customerListBox.getChildren().clear();
        for (Customer c : rentalSystem.getCustomers()) {
            VBox card = createCustomerCard(c);
            customerListBox.getChildren().add(card);
        }
    }
    
    private VBox createCustomerCard(Customer c) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        
        Label name = new Label(c.getName() + " (ID: " + c.getCustomerId() + ")");
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label details = new Label("Email: " + c.getEmail() + " | Phone: " + c.getPhone());
        
        // Verification status label
        Label verificationStatus = new Label("Status: " + (c.isVerified() ? "✓ Verified" : "⚠ Not Verified"));
        verificationStatus.setStyle(c.isVerified() ? 
            "-fx-text-fill: green; -fx-font-weight: bold;" : 
            "-fx-text-fill: orange; -fx-font-weight: bold;");
        
        HBox buttonBox = new HBox(5);
        
        // Add verify button if customer is not verified
        if (!c.isVerified()) {
            Button verifyBtn = new Button("Verify Customer");
            verifyBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            verifyBtn.setOnAction(e -> {
                c.setVerified(true);
                refreshCustomerList();
            });
            buttonBox.getChildren().add(verifyBtn);
        }
        
        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction(e -> {
            if (rentalSystem.removeCustomer(c.getCustomerId())) {
                refreshCustomerList();
            } else {
                showAlert("Error", "Cannot remove. Active bookings exist.");
            }
        });
        buttonBox.getChildren().add(removeBtn);
        
        card.getChildren().addAll(name, details, verificationStatus, buttonBox);
        return card;
    }
    
    private void showAddCustomerDialog() {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Add Customer");
        
        ButtonType addBtn = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        CheckBox verifyCheckBox = new CheckBox();
        verifyCheckBox.setSelected(true); // Default to verified
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Verify Now:"), 0, 4);
        grid.add(verifyCheckBox, 1, 4);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(btn -> {
            if (btn == addBtn) {
                try {
                    Customer customer = new Customer(
                        idField.getText(),
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText()
                    );
                    
                    // Set verification status based on checkbox
                    customer.setVerified(verifyCheckBox.isSelected());
                    
                    if (rentalSystem.addCustomer(customer)) {
                        refreshCustomerList();
                    }
                    return customer;
                } catch (Exception e) {
                    showAlert("Error", "Invalid input: " + e.getMessage());
                }
            }
            return null;
        });
        
        dialog.showAndWait();
    }
    
    private Tab createBookingTab() {
        Tab tab = new Tab("Bookings");
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15));
        
        ScrollPane scrollPane = new ScrollPane();
        bookingListBox = new VBox(10);
        bookingListBox.setPadding(new Insets(10));
        scrollPane.setContent(bookingListBox);
        scrollPane.setFitToWidth(true);
        refreshBookingList();
        
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Create Booking");
        Button refreshBtn = new Button("Refresh");
        
        addBtn.setOnAction(e -> showCreateBookingDialog());
        refreshBtn.setOnAction(e -> refreshBookingList());
        
        buttonBox.getChildren().addAll(addBtn, refreshBtn);
        
        layout.setCenter(scrollPane);
        layout.setBottom(buttonBox);
        tab.setContent(layout);
        return tab;
    }
    
    private void refreshBookingList() {
        bookingListBox.getChildren().clear();
        for (Booking b : rentalSystem.getBookings()) {
            VBox card = createBookingCard(b);
            bookingListBox.getChildren().add(card);
        }
    }
    
    private VBox createBookingCard(Booking b) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Label title = new Label("Booking: " + b.getBookingId());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label customer = new Label("Customer: " + b.getCustomer().getName());
        Label vehicle = new Label("Vehicle: " + b.getVehicle().getModel());
        Label dates = new Label("From: " + sdf.format(b.getStartDate()) + " To: " + sdf.format(b.getEndDate()));
        Label price = new Label("Total: $" + String.format("%.2f", b.getTotalPrice()) + " | Status: " + b.getStatus());
        
        HBox buttonBox = new HBox(5);
        Button completeBtn = new Button("Complete");
        Button cancelBtn = new Button("Cancel");
        
        completeBtn.setOnAction(e -> {
            b.setStatus("COMPLETED");
            refreshBookingList();
            refreshVehicleList();
        });
        
        cancelBtn.setOnAction(e -> {
            b.setStatus("CANCELLED");
            refreshBookingList();
            refreshVehicleList();
        });
        
        buttonBox.getChildren().addAll(completeBtn, cancelBtn);
        
        card.getChildren().addAll(title, customer, vehicle, dates, price, buttonBox);
        return card;
    }
    
    private void showCreateBookingDialog() {
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Create Booking");
        
        ButtonType createBtn = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createBtn, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField idField = new TextField();
        ComboBox<String> customerCombo = new ComboBox<>();
        ComboBox<String> vehicleCombo = new ComboBox<>();
        Spinner<Integer> daysSpinner = new Spinner<>(1, 365, 1);
        
        // Populate customer combo (show verification status)
        for (Customer c : rentalSystem.getCustomers()) {
            String verifyIcon = c.isVerified() ? "✓" : "⚠";
            customerCombo.getItems().add(verifyIcon + " " + c.getCustomerId() + " - " + c.getName());
        }
        
        // Populate vehicle combo (only available vehicles)
        for (Vehicle v : rentalSystem.getVehicles()) {
            if (v.isAvailable()) {
                vehicleCombo.getItems().add(v.getVehicleId() + " - " + v.getModel());
            }
        }
        
        grid.add(new Label("Booking ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Customer:"), 0, 1);
        grid.add(customerCombo, 1, 1);
        grid.add(new Label("Vehicle:"), 0, 2);
        grid.add(vehicleCombo, 1, 2);
        grid.add(new Label("Days:"), 0, 3);
        grid.add(daysSpinner, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(btn -> {
            if (btn == createBtn) {
                try {
                    String bookingId = idField.getText();
                    String custSelection = customerCombo.getValue();
                    String vehSelection = vehicleCombo.getValue();
                    
                    if (custSelection == null || vehSelection == null) {
                        showAlert("Error", "Please select both customer and vehicle!");
                        return null;
                    }
                    
                    // Extract IDs (remove verification icon from customer selection)
                    String custId = custSelection.split(" - ")[0].replaceAll("[✓⚠ ] ", "").trim();
                    int vehId = Integer.parseInt(vehSelection.split(" - ")[0]);
                    int days = daysSpinner.getValue();
                    
                    Customer customer = null;
                    for (Customer c : rentalSystem.getCustomers()) {
                        if (c.getCustomerId().equals(custId)) {
                            customer = c;
                            break;
                        }
                    }
                    
                    Vehicle vehicle = null;
                    for (Vehicle v : rentalSystem.getVehicles()) {
                        if (v.getVehicleId() == vehId) {
                            vehicle = v;
                            break;
                        }
                    }
                    
                    Date start = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(start);
                    cal.add(Calendar.DATE, days);
                    Date end = cal.getTime();
                    
                    rentalSystem.createBooking(bookingId, customer, vehicle, start, end);
                    refreshBookingList();
                    refreshVehicleList();
                    
                } catch (IllegalStateException e) {
                    showAlert("Error", e.getMessage());
                } catch (Exception e) {
                    showAlert("Error", "Invalid input: " + e.getMessage());
                }
            }
            return null;
        });
        
        dialog.showAndWait();
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
