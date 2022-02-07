package com.example.c196project.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.c196project.Database.Repository;
import com.example.c196project.Entity.Term;
import com.example.c196project.R;

public class AddTerm extends AppCompatActivity {

    EditText editName;
    EditText editStart;
    EditText editEnd;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        repository = new Repository((getApplication()));
        editName = findViewById(R.id.editTermName);
        editStart = findViewById(R.id.editTermStart);
        editEnd = findViewById(R.id.editTermEnd);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


        public void saveAddTermButton (View view){

            Term term;
            int newId = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermId() + 1;

            term = new Term(newId, editName.getText().toString(), editStart.getText().toString(),
                    editEnd.getText().toString());

            repository.insert(term);
        }
}
