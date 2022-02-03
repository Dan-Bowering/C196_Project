package com.example.c196project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity {

    int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        termID = getIntent().getIntExtra("id", -1);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Repository repository = new Repository((getApplication()));
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> associatedCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermId() == termID)
                associatedCourses.add(c);
        }
        adapter.setCourses(associatedCourses);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}