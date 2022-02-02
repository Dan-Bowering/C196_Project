package com.example.c196project.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196project.DAO.AssessmentDAO;
import com.example.c196project.DAO.CourseDAO;
import com.example.c196project.DAO.TermDAO;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
public abstract class StudentViewDatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile StudentViewDatabaseBuilder INSTANCE;

    static StudentViewDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
        synchronized (StudentViewDatabaseBuilder.class) {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentViewDatabaseBuilder.class,
                        "StudentViewDatabase.db")
                        .fallbackToDestructiveMigration()
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
