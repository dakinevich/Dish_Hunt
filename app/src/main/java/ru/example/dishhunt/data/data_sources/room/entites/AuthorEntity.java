package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "author_table")
public class AuthorEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String Name;
    @NonNull
    private String Bio;
    @NonNull
    private String ImgSrc;


}
