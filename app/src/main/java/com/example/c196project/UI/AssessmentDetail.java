package com.example.c196project.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.util.List;

public class AssessmentDetail extends AppCompatActivity {

    EditText editId;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Integer id;
    String name;
    String start;
    String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        // Find the view to associate with the EditText
        editId = findViewById(R.id.editAssessmentId);
        editName = findViewById(R.id.editAssessmentName);
        editStart = findViewById(R.id.editAssessmentStart);
        editEnd = findViewById(R.id.editAssessmentEnd);

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
