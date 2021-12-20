package com.example.individualproject3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Parent")

public class ParentAccount  implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "ParentId")
    private int parentId;

    @ColumnInfo(name = "parent_name")
    private String parentName;

    @ColumnInfo(name = "parent_email")
    private String parentEmail;

    @ColumnInfo(name = "parent_password")
    private String parentPassword;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public void setParentPassword(String parentPassword) {
        this.parentPassword = parentPassword;
    }

    public String getParentPassword() {
        return parentPassword;
    }
}
