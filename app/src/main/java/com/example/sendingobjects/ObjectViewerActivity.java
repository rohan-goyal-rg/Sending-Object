package com.example.sendingobjects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sendingobjects.databinding.ActivityObjectViewerBinding;
import com.example.sendingobjects.models.Student;

public class ObjectViewerActivity extends AppCompatActivity {

    ActivityObjectViewerBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize binding
        b=ActivityObjectViewerBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setTitle("ObjectViewer Activity");

        //Receive data
        receiveData();
    }

    /**
     * Receive data from ObjectSenderActivity
     */
    private void receiveData() {
        Student student = (Student) getIntent().getSerializableExtra("STUDENT_DATA");

       //update views
        b.studentName.setText(student.getName());
        b.phoneNo.setText(student.getPhoneNo());
        b.rollNo.setText(student.getRollNo());

        // check gender
        if(R.id.gender_male==student.getGender()){
            b.genderId.setText("Male");
        }

        else {
            b.genderId.setText("Female");
        }
    }
}