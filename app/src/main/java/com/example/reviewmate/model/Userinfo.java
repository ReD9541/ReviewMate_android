package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "userinfo",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE))
public class Userinfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    private Integer rowId;

    @ColumnInfo(name = "user_id")
    private Integer userId;

    @ColumnInfo(name = "fname")
    private String firstName;

    @ColumnInfo(name = "lname")
    private String lastName;

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

    public Userinfo() {
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

}
