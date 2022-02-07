package com.example.c196project.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.R;

public class AddAssessment extends AppCompatActivity {

    EditText editId;
    EditText editName;
    EditText editType;
    EditText editStart;
    EditText editEnd;
    EditText editCourseId;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        repository = new Repository((getApplication()));
        editId = findViewById(R.id.editAssessmentId);
        editName = findViewById(R.id.editAssessmentName);
        editType = findViewById(R.id.editAssessmentType);
        editStart = findViewById(R.id.editAssessmentStart);
        editEnd = findViewById(R.id.editAssessmentEnd);
        editCourseId = findViewById(R.id.editCourseId);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAddAssessmentButton(View view) {

        Assessment assessment;

        assessment = new Assessment(Integer.parseInt(editId.getText().toString()),
                editName.getText().toString(), editType.getText().toString(),
                editStart.getText().toString(), editEnd.getText().toString(),
                Integer.parseInt(editCourseId.getText().toString()));

        repository.insert(assessment);
    }
}
