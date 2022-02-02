package com.example.c196project.UI;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.util.List;

public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Repository repository = new Repository((getApplication()));
        List<Term> terms = repository.getAllTerms();
        final TermAdapter adapter = new TermAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termlist, menu);
        return true;
    }
}