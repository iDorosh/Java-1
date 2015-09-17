package com.idorosh.i_dorosh_advancedviews;

/**
 * Created by iDorosh on 9/14/15.
 */
public class Contacts {

    //Custom object names
    private String name;
    private String phone;
    private String email;
    private String aim;
    private String address;
    private String photo;
    private String color;


    //Method to set the incoming values to the variables
    public Contacts(String _name, String _phone, String _email, String _aim, String _address, String _photo, String _color) {
        this.name = _name;
        this.phone = _phone;
        this.email = _email;
        this.aim = _aim;
        this.address = _address;
        this.photo = _photo;
        this.color = _color;
    }

    //Methods that returns the information from the variables to mainactivity
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAim() {
        return aim;
    }

    public String getAddress() {return address;}

    public String getPhoto() {return photo;}

    public String getColor() {return color;}



}

