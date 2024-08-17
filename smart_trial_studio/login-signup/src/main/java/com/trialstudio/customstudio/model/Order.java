package com.trialstudio.customstudio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "orders")
public class Order {
    @Id
    private String orderId;          // Unique order identifier
    //private List<OrderProduct> items; // List of products in the order with quantity
    private double totalAmount;      // Total order amount
    private String firstName;        // Billing Details: First Name
    private String lastName;         // Billing Details: Last Name
    private String country;          // Billing Details: Country/Region
    private String address;          // Billing Details: Street Address
    private String city;             // Billing Details: Town/City
    private String state;            // Billing Details: State/County (optional)
    private String postcode;         // Billing Details: Postcode/ZIP (optional)
    private String phone;            // Billing Details: Phone
    private String email;            // Billing Details: Email Address

    private List<Map<String, Object>> products; // List of product IDs and their counts



    public List<Map<String, Object>> getProducts() {
        return products;
    }

    public void setProducts(List<Map<String, Object>> products) {
        this.products = products;
    }



    private LocalDateTime orderDate;          // Date and time the order was placed

    // Getters and Setters for all fields

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public List<OrderProduct> getItems() {
//        return items;
//    }
//
//    public void setItems(List<OrderProduct> items) {
//        this.items = items;
//    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


}
