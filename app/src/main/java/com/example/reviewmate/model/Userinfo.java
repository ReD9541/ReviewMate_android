package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

import java.io.Serializable;

@Entity(tableName = "userinfo",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE),
        indices = {@Index(value = "user_id")}
)
public class Userinfo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    public Integer rowId;

    @ColumnInfo(name = "user_id")
    public Integer userId;

    @ColumnInfo(name = "fname")
    public String firstName;

    @ColumnInfo(name = "lname")
    public String lastName;

    @ColumnInfo(name = "username")
    public String username;  // Added username field

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "bio")
    public String bio;

    @ColumnInfo(name = "joined_on")
    public String joinedOn;

    @ColumnInfo(name = "pfp_url")
    public String profilePictureUrl;

    @Ignore
    public Userinfo(){

    }


    public Userinfo(Integer userId, String firstName, String lastName,  String country, String address, String bio, String joinedOn, String profilePictureUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.address = address;
        this.bio = bio;
        this.joinedOn = joinedOn;
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getters and setters

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
