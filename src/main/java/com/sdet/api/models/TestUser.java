package com.sdet.api.models;

public record TestUser(String firstName, String lastName, String email, String password, 
    String address, String country, String state, String city, String zipcode, String mobile) {

    public static TestUser random(){
        String id = String.valueOf(System.currentTimeMillis());
        return new TestUser("Test", "User" + id.substring(id.length() - 4), "qa_"+id+"@test.com", "Test@123",
            "1 2 3 TestStreet", "India", "Karnataka", "Bangalore", "560001", "987654321");
    }

    public String fullName(){
        return firstName + " " + lastName;
    }
}
