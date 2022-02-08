package com.example.c196project.UI;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Course;
import com.example.c196project.R;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class AddCourse extends AppCompatActivity {

    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editTermId;
    EditText editCourseNote;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    SimpleDateFormat sdf;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    String dateFormat;


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
        editCourseNote = findViewById(R.id.editCourseNote);

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
                new DatePickerDialog(AddCourse.this, startDate, calendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AddCourse.this, endDate, calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        editStart.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEnd() {
        editEnd.setText(sdf.format(calendarEnd.getTime()));
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
                editInstructorEmail.getText().toString(), Integer.parseInt(editTermId.getText().toString()),
                editCourseNote.getText().toString());

        repository.insert(course);
        finish();
    }
}
