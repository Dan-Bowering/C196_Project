package com.example.c196project.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Course;
import com.example.c196project.R;

public class AddCourse extends AppCompatActivity {

    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editTermId;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        repository = new Repository((getApplication()));
        editName = findViewById(R.id.editCourseName);
        editStart = findViewById(R.id.editCourseStart);
        editEnd = findViewById(R.id.editCourseEnd);
        editStatus = findViewById(R.id.editCourseStatus);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);
        editTermId = findViewById(R.id.editTermId);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void saveAddCourseButton (View view){

        Course course;

        int newId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;

        course = new Course(newId, editName.getText().toString(), editStart.getText().toString(),
                editEnd.getText().toString(), editStatus.getText().toString(),
                editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                editInstructorEmail.getText().toString(), Integer.parseInt(editTermId.getText().toString()));

        repository.insert(course);
    }
}
