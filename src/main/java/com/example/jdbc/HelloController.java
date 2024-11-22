package com.example.jdbc;

import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {
    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> typeColumn;

    @FXML
    private TableColumn<Employee, Double> salaryColumn;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField hourlyRateField;

    @FXML
    private TextField hoursField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private final ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        typeBox.setItems(FXCollections.observableArrayList("Full-time", "Part-time", "Contractor"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        salaryColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().calculateSalary()));

        employeeTable.setItems(employeeList);
    }

    @FXML
    public void handleAddEmployee() {
        String name = nameField.getText();
        String type = typeBox.getValue();

        try {
            if (type.equals("Full-time")) {
                double annualSalary = Double.parseDouble(salaryField.getText());
                employeeList.add(new FullTimeEmployee(name, annualSalary));
            } else if (type.equals("Part-time")) {
                double hourlyRate = Double.parseDouble(hourlyRateField.getText());
                int hoursWorked = Integer.parseInt(hoursField.getText());
                employeeList.add(new PartTimeEmployee(name, hourlyRate, hoursWorked));
            } else if (type.equals("Contractor")) {
                double hourlyRate = Double.parseDouble(hourlyRateField.getText());
                int maxHours = Integer.parseInt(hoursField.getText());
                employeeList.add(new Contractor(name, hourlyRate, maxHours));
            }
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numbers for salary, hourly rate, and hours.");
        }
    }

    @FXML
    public void handleDeleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeList.remove(selectedEmployee);
        } else {
            showAlert("No Selection", "Please select an employee to delete.");
        }
    }

    private void clearFields() {
        nameField.clear();
        typeBox.getSelectionModel().clearSelection();
        salaryField.clear();
        hourlyRateField.clear();
        hoursField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
