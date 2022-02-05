package com.example.c196project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repo = new Repository(getApplication());
        Term term = new Term(1, "Term 1", "02/01/2022", "07/31/2022");
        repo.insert(term);
        Term termTwo = new Term(2, "Term 2", "08/01/2022", "01/31/2023");
        repo.insert(termTwo);
        Course course = new Course(482, "Software I", "02/01/2022",
                "03/15/2022", "Completed", "Malcolm Wabarra",
                "384-599-8787", "mwabarra@wgu.edu", 1);
        repo.insert(course);
        Course courseTwo = new Course(195, "Software II", "08/01/2022",
                "10/01/2022", "In Progress", "Malcolm Wabarra",
                "384-599-8787", "mwabarra@wgu.edu", 2);
        repo.insert(courseTwo);
        Course courseThree = new Course(196, "Mobile Application Development",
                "03/16/2022", "04/30/2022", "Plan To Take",
                "Carolyn Sher-DeCusatis", "385-428-7192",
                "carolyn.sher@wgu.edu", 1);
        repo.insert(courseThree);
        Assessment assessment = new Assessment(1960, "AMB2",
                "Performance Assessment", "03/16/2022", "04/30/2022",
                196);
        repo.insert(assessment);
    }

    public void enterHandler(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

    }

}