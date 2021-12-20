package com.example.individualproject3;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ParentWithChild {
    @Embedded
    public ParentAccount parent;
    @Relation(entity = ChildAccount.class,parentColumn = "ParentId", entityColumn = "ParentId")
    List<ChildAccount> children;
}