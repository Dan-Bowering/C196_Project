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
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.R;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

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
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    SimpleDateFormat sdf;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    String dateFormat;

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
                new DatePickerDialog(AssessmentDetail.this, startDate, calendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AssessmentDetail.this, endDate, calendarEnd.get(Calendar.YEAR),
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

            case R.id.startNotify:
                String startDateFromScreen = editStart.getText().toString();
                Date startDate = null;

                try {
                    startDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long startTrigger = startDate.getTime();
                Intent startIntent = new Intent(AssessmentDetail.this, MyReceiver.class);
                startIntent.putExtra("key", editName.getText().toString() + " begins today!");
                PendingIntent startSender = PendingIntent.getBroadcast(AssessmentDetail.this,
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
                Intent endIntent = new Intent(AssessmentDetail.this, MyReceiver.class);
                endIntent.putExtra("key", editName.getText().toString() + " ends today!");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetail.this,
                        MainActivity.numAlert++, endIntent, 0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
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
        finish();
    }
}
