package com.example.c196project.Database;

import android.app.Application;

import com.example.c196project.DAO.AssessmentDAO;
import com.example.c196project.DAO.CourseDAO;
import com.example.c196project.DAO.TermDAO;
import com.example.c196project.Entity.Assessment;
import com.example.c196project.Entity.Course;
import com.example.c196project.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){

        StudentViewDatabaseBuilder db = StudentViewDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    public List<Term> getAllTerms() {
        databaseExecutors.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term) {

        databaseExecutors.execute(() ->{
            mTermDAO.insertTerm(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
