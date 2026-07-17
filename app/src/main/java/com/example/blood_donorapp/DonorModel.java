package com.example.blood_donorapp;

public class DonorModel {
    // These 6 variable names MUST be exactly like this
    public String id, name, bloodGroup, city, phone, lastDonationDate;

    public DonorModel() {} // Required for Firebase

    public DonorModel(String id, String name, String bloodGroup, String city, String phone, String lastDonationDate) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.phone = phone;
        this.lastDonationDate = lastDonationDate;
    }
}