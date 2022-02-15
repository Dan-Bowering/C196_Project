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
    private List<Term> mAllTermIds;
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

    //**********  CRUD operations for Terms DB **********//
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

    public void update(Term term) {

        databaseExecutors.execute(() ->{
            mTermDAO.updateTerm(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {

        databaseExecutors.execute(() -> {
            mTermDAO.deleteTerm(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //**********  CRUD operations for Courses DB **********//
    public List<Course> getAllCourses() {
        databaseExecutors.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Course course) {

        databaseExecutors.execute(() ->{
            mCourseDAO.insertCourse(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {

        databaseExecutors.execute(() ->{
            mCourseDAO.updateCourse(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {

        databaseExecutors.execute(() -> {
            mCourseDAO.deleteCourse(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //**********  CRUD operations for Assessments DB **********//
    public List<Assessment> getAllAssessments() {
        databaseExecutors.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessment assessment) {

        databaseExecutors.execute(() ->{
            mAssessmentDAO.insertAssessment(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {

        databaseExecutors.execute(() ->{
            mAssessmentDAO.updateAssessment(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {

        databaseExecutors.execute(() -> {
            mAssessmentDAO.deleteAssessment(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
