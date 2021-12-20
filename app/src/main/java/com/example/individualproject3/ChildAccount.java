package com.example.individualproject3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Child", foreignKeys = {
        @ForeignKey(
                entity = ParentAccount.class,
                parentColumns = {"ParentId"},
                childColumns = {"ParentId"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )}
        )
public class ChildAccount  implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ChildId")
    private int childId;

    @ColumnInfo(name = "ParentId", index = true)
    private int parentId;

    @ColumnInfo(name = "child_name")
    private String childName;


    @ColumnInfo(name = "child_email")
    private String childEmail;

    @ColumnInfo(name = "child_password")
    private String childPassword;

    @ColumnInfo(name="levels_completed")
    private int levelsCompleted;

    @ColumnInfo(name="level1Complete")
    private int level1;

    @ColumnInfo(name="level2Complete")
    private int level2;

    @ColumnInfo(name="level3Complete")
    private int level3;

    @ColumnInfo(name="level4Complete")
    private int level4;

    @ColumnInfo(name="level5Complete")
    private int level5;

    @ColumnInfo(name="level6Complete")
    private int level6;

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildEmail() {
        return childEmail;
    }

    public void setChildEmail(String childEmail) {
        this.childEmail = childEmail;
    }

    public String getChildPassword() {
        return childPassword;
    }

    public void setChildPassword(String childPassword) {
        this.childPassword = childPassword;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    public void setLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
    }

    public int getLevel4() {
        return level4;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
    }

    public int getLevel5() {
        return level5;
    }

    public void setLevel5(int level5) {
        this.level5 = level5;
    }

    public int getLevel6() {
        return level6;
    }

    public void setLevel6(int level6) {
        this.level6 = level6;
    }
}