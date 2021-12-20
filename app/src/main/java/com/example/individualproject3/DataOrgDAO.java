package com.example.individualproject3;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataOrgDAO {
    @Query("SELECT * FROM Parent")
    List<ParentAccount> getAllParent();

    @Query("SELECT * FROM Child ")
    List<ChildAccount> getAllChildren();
    @Query("SELECT * FROM Parent  WHERE parent_email LIKE :email LIMIT 1")
    ParentAccount findParentByEmail(String email);

    @Query("SELECT * FROM Parent  WHERE parent_email LIKE :email AND " +
            "parent_password LIKE :password LIMIT 1")
    ParentAccount findParentByEmailPassword(String email, String password);

    @Query("SELECT * FROM Child  WHERE child_email LIKE :email LIMIT 1")
    ChildAccount findChildByEmail(String email);

    @Query("SELECT * FROM Child  WHERE child_email LIKE :email AND " +
            "child_password LIKE :password LIMIT 1")
    ChildAccount findChildByEmailPassword(String email, String password);

    @Insert(onConflict = IGNORE)
    void insertAllParents(ParentAccount... parents);

    @Insert(onConflict = IGNORE)
    void insertParent(ParentAccount parent);

    @Delete
    void deleteParent(ParentAccount parent);

    @Update
    void updateParent(ParentAccount parent);

    @Insert(onConflict = IGNORE)
    void insertChild(ChildAccount child);

    @Delete
    void deleteChild(ChildAccount child);

    @Update
    void updateChild(ChildAccount child);

    @Query("SELECT * FROM Parent Where parent_email LIKE :email")
    public List<ParentWithChild> getParentWithChildren(String email);
}
