package com.example.c196project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

import java.util.List;

public class TermList extends AppCompatActivity {

    // Loads XML activity and sets the RecyclerView list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        Repository repository = new Repository((getApplication()));
        List<Term> terms = repository.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
    }

    // Adds the menu to the action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
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
                RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
                Repository repository = new Repository((getApplication()));
                List<Term> terms = repository.getAllTerms();
                final TermAdapter adapter = new TermAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setTerms(terms);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Go to the Add Term screen
    public void goToAddTerm(View view) {
        Intent intent = new Intent(TermList.this, AddTerm.class);
        startActivity(intent);
    }

}