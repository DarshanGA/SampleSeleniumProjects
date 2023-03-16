package com.justmedarshan.caseStudyPractice;

public class ParabankUser {

    private String firstname, lastname, address, city , state , zipcode, phoneNo, ssn, username, password;
    //private String firstname = null, lastname = null, address = null, city = null, state = null, zipcode = null, phoneNo = null, ssn = null, username = null, password = null;

    public ParabankUser(){

    }

    public ParabankUser(String givenFirstname, String givenLastname, String givenAddress, String givenCity, String givenState, String givenZipcode, String givenPhonNo, String givenSsn
    ,String givenUsername, String givenPassword){

        this.firstname = givenFirstname;
        this.lastname = givenLastname;
        this.address = givenAddress;
        this.city = givenCity;
        this.state = givenState;
        this.zipcode = givenZipcode;
        this.phoneNo = givenPhonNo;
        this.ssn = givenSsn;
        this.username = givenUsername;
        this.password = givenPassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserDetails(int index){

        String returnData = null;
        switch(index){

            case 0:
                returnData = firstname;
                break;
            case 1:
                returnData = lastname;
                break;
            case 2:
                returnData = address;
                break;
            case 3:
                returnData = city;
                break;
            case 4:
                returnData = zipcode;
                break;
            case 5:
                returnData = phoneNo;
                break;
            case 6:
                returnData = ssn;
                break;
            case 7:
                returnData = username;
                break;
            case 8:
                returnData = password;
                break;

        }
        return returnData;
    }

    @Override
    public String toString() {
        return "ParabankUser { " +
                "firstname = '" + firstname + '\'' +
                ", lastname ='" + lastname + '\'' +
                ", address = '" + address + '\'' +
                ", city ='" + city + '\'' +
                ", state ='" + state + '\'' +
                ", zipcode ='" + zipcode + '\'' +
                ", phoneNo ='" + phoneNo + '\'' +
                ", ssn ='" + ssn + '\'' +
                ", username ='" + username + '\'' +
                ", password ='" + password + '\'' +
                " } " ;
    }
}
