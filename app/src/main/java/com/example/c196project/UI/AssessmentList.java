package com.example.c196project.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    EditText editCourseNote;
    Integer id;
    String name;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    Integer termId;
    String courseNote;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    SimpleDateFormat sdf;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    String dateFormat;
    Course currentCourse;
    int numAssessments;


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
        editCourseNote = findViewById(R.id.editCourseNote);

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
        courseNote = getIntent().getStringExtra("courseNote");

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
        editCourseNote.setText(courseNote);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
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
                new DatePickerDialog(AssessmentList.this, startDate, calendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AssessmentList.this, endDate, calendarEnd.get(Calendar.YEAR),
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

    // Adds the menu to the action bar
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_termlist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.delete:
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseId() == id) currentCourse = course;
                }

                numAssessments = 0;
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getCourseId() == id) ++numAssessments;
                }

                if (numAssessments == 0) {
                    repository.delete(currentCourse);
                    Toast.makeText(AssessmentList.this, currentCourse.getCourseName() +
                            " was deleted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AssessmentList.this, "Can't delete a course that " +
                                    "contains assessments",
                            Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editCourseNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText().toString() + " - Course Notes");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, editName.getText().toString() + " - Course Notes");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.startNotify:
                String startDateFromScreen = editStart.getText().toString();
                Date startDate = null;

                try {
                    startDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long startTrigger = startDate.getTime();
                Intent startIntent = new Intent(AssessmentList.this, MyReceiver.class);
                startIntent.putExtra("key", editName.getText().toString() + " begins today!");
                PendingIntent startSender = PendingIntent.getBroadcast(AssessmentList.this,
                        MainActivity.numAlert++, startIntent, 0);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                return true;

            case R.id.endNotify:
                String endDateFromScreen = editEnd.getText().toString();
                Date endDate = null;

                try {
                    endDate = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(AssessmentList.this, MyReceiver.class);
                endIntent.putExtra("key", editName.getText().toString() + " ends today!");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentList.this,
                        MainActivity.numAlert++, endIntent, 0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveUpdateCourseButton(View view) {

        Course course;

        course = new Course(id, editName.getText().toString(), editStart.getText().toString(),
                editEnd.getText().toString(), editStatus.getText().toString(),
                editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                editInstructorEmail.getText().toString(), Integer.parseInt(editTermId.getText().toString()),
                editCourseNote.getText().toString());

        repository.update(course);
        finish();
    }

    public void goToAddAssessment(View view) {

        Intent intent =  new Intent(AssessmentList.this, AddAssessment.class);
        startActivity(intent);
    }
}