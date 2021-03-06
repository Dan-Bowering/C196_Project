package com.example.c196project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    SimpleDateFormat sdf;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    String dateFormat;
    Term currentTerm;
    int numCourses;

    // Loads XML activity
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

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
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

        // Create the date/time formatter
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Date date;
                String info = editStart.getText().toString();
                if (info.equals("")) info = String.valueOf(Calendar.getInstance().getTime());
                try {
                    calendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseList.this, startDate, calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date;
                String info = editEnd.getText().toString();
                if (info.equals("")) info = String.valueOf(Calendar.getInstance().getTime());
                try {
                    calendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseList.this, endDate, calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    // Sets the Start Date DatePicker to the current date
    private void updateLabelStart() {
        editStart.setText(sdf.format(calendarStart.getTime()));
    }

    // Sets the End Date DatePicker to the current date
    private void updateLabelEnd() {
        editEnd.setText(sdf.format(calendarEnd.getTime()));
    }

    // Adds the menu to the action bar
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    // Method to return/handle item from action bar selected
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Returns to the previous screen
            case android.R.id.home:
                this.finish();
                return true;

            // Refreshes the RecyclerView/list
            case R.id.refresh:
                RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
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
                return true;

            // Deletes the selected term if there is no associated course
            case R.id.delete:
                for (Term term : repository.getAllTerms()) {
                    if (term.getTermId() == id) currentTerm = term;
                }

                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getTermId() == id) ++numCourses;
                }

                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(CourseList.this, currentTerm.getTermName() +
                            " was deleted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(CourseList.this, "Can't delete a term that contains courses",
                            Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return true;
    }

    // Saves input in the form and updates the selected term
    public void saveUpdateTermButton(View view) {

        Term term;

        term = new Term(id, editName.getText().toString(), editStart.getText().toString(),
                editEnd.getText().toString());

        repository.update(term);
        finish();
    }

    // Go to the Add Course screen
    public void goToAddCourse(View view) {

        Intent intent = new Intent(CourseList.this, AddCourse.class);
        startActivity(intent);
    }
}