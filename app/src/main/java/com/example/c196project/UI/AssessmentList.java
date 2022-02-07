package com.example.c196project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196project.DAO.CourseDAO;
import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentList extends AppCompatActivity {

    EditText editId;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editTermId;
    Integer id;
    String name;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    Integer termId;
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        // Find the view to associate with the EditText
        editId = findViewById(R.id.editCourseId);
        editName = findViewById(R.id.editCourseName);
        editStart = findViewById(R.id.editCourseStart);
        editEnd = findViewById(R.id.editCourseEnd);
        editStatus = findViewById(R.id.editCourseStatus);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);
        editTermId = findViewById(R.id.editTermId);

        // Get data to send to next screen
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor name");
        instructorPhone = getIntent().getStringExtra("instructor phone");
        instructorEmail = getIntent().getStringExtra("instructor email");
        termId = getIntent().getIntExtra("termId", -1);

        // Assign data to the EditText fields
        editId.setText(Integer.toString(id));
        editName.setText(name);
        editStart.setText(start);
        editEnd.setText(end);
        editStatus.setText(status);
        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);
        editTermId.setText(Integer.toString(termId));

        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        repository = new Repository((getApplication()));
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Loop through array list to find assessments with matching course IDs
        List<Assessment> associatedAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseId() == id)
                associatedAssessments.add(a);
        }
        adapter.setAssessments(associatedAssessments);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void saveUpdateCourseButton(View view) {

        Course course;

        course = new Course(id, editName.getText().toString(), editStart.getText().toString(),
                editEnd.getText().toString(), editStatus.getText().toString(),
                editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                editInstructorEmail.getText().toString(), Integer.parseInt(editTermId.getText().toString()));
        repository.update(course);

    }

    public void goToAddAssessment(View view) {

        Intent intent =  new Intent(AssessmentList.this, AddAssessment.class);
        startActivity(intent);
    }
}