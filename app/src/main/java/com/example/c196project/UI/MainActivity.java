package com.example.c196project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterHandler(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

        Repository repo = new Repository(getApplication());
        Term term = new Term(1, "Term 1", "02/01/2022", "07/31/2022");
        repo.insert(term);
    }

}