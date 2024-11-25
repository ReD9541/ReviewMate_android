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
    private Integer rowId;

    @ColumnInfo(name = "user_id")
    private Integer userId;

    @ColumnInfo(name = "fname")
    private String firstName;

    @ColumnInfo(name = "lname")
    private String lastName;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "bio")
    private String bio;

    @ColumnInfo(name = "joined_on")
    private String joinedOn;

    @ColumnInfo(name = "pfp_url")
    private String profilePictureUrl;

    // Default no-argument constructor (Ignored by Room)
    @Ignore
    public Userinfo() {
    }

    // Constructor without `rowId` and `username`
    @Ignore
    public Userinfo(Integer userId, String firstName, String lastName, String country, String address, String bio, String joinedOn, String profilePictureUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.address = address;
        this.bio = bio;
        this.joinedOn = joinedOn;
        this.profilePictureUrl = profilePictureUrl;
    }

    // Full parameter constructor
    public Userinfo(Integer userId, String firstName, String lastName, String username, String country, String address, String bio, String joinedOn, String profilePictureUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
