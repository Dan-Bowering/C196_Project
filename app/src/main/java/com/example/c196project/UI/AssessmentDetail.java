package com.example.c196project.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.R;

public class AssessmentDetail extends AppCompatActivity {

    EditText editId;
    EditText editName;
    EditText editType;
    EditText editStart;
    EditText editEnd;
    EditText editCourseId;
    Integer id;
    String name;
    String type;
    String start;
    String end;
    Integer courseId;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        repository = new Repository((getApplication()));

        // Find the view to associate with the EditText
        editId = findViewById(R.id.editAssessmentId);
        editName = findViewById(R.id.editAssessmentName);
        editType = findViewById(R.id.editAssessmentType);
        editStart = findViewById(R.id.editAssessmentStart);
        editEnd = findViewById(R.id.editAssessmentEnd);
        editCourseId = findViewById(R.id.editCourseId);

        // Get data to send to next screen
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        courseId = getIntent().getIntExtra("courseId", -1);

        // Assign data to the EditText fields
        editId.setText(Integer.toString(id));
        editName.setText(name);
        editType.setText(type);
        editStart.setText(start);
        editEnd.setText(end);
        editCourseId.setText(Integer.toString(courseId));

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveUpdateAssessmentButton(View view) {

        Assessment assessment;

        assessment = new Assessment(id, editName.getText().toString(), editType.getText().toString(),
                editStart.getText().toString(), editEnd.getText().toString(),
                Integer.parseInt(editCourseId.getText().toString()));

        repository.update(assessment);
    }
}
