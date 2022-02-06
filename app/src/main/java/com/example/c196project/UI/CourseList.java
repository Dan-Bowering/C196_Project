package com.example.c196project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity {

    EditText editId;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Integer id;
    String name;
    String start;
    String end;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // Find the view to associate with the EditText
        editId = findViewById(R.id.editTermId);
        editName = findViewById(R.id.editTermName);
        editStart = findViewById(R.id.editTermStart);
        editEnd = findViewById(R.id.editTermEnd);

        // Get data to send to next screen
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");

        // Assign data to the EditText fields
        editId.setText(Integer.toString(id));
        editName.setText(name);
        editStart.setText(start);
        editEnd.setText(end);

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        repository = new Repository((getApplication()));
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Loop through array list to find courses with matching term IDs
        List<Course> associatedCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermId() == id)
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

    public void saveUpdateTermButton(View view) {

        Term term;

        term = new Term(id, editName.getText().toString(), editStart.getText().toString(),
                editEnd.getText().toString());
        repository.update(term);

    }
}