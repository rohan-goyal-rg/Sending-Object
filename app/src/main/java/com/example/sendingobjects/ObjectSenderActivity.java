package com.example.sendingobjects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionProvider;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.sendingobjects.databinding.ActivityObjectSenderBinding;
import com.example.sendingobjects.models.Student;


public class ObjectSenderActivity extends AppCompatActivity {
    ActivityObjectSenderBinding b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize binding
        b = ActivityObjectSenderBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setTitle("ObjectSender Activity");


        loadSharedPreferences();

        //Send data to ViewerActivity
        sendDataToViewerActivity();

        //change IME option of StudentName EditText
        changeImeOptionOfStudentNameET();

        //change IME option of RollNo EditText
        changeImeOptionOfRollNoET();

        //change IME option of PhoneNo EditText
        changeImeOptionOfPhoneNoET();


    }

    /**
     * Get data from sharedPreference
     */
    private void loadSharedPreferences() {

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        //update views
        b.studentName.setText(sharedPreferences.getString(Constants.STUDENT_NAME, ""));
        b.gender.check(sharedPreferences.getInt(Constants.GENDER, 0));
        b.rollNo.setText(sharedPreferences.getString(Constants.ROLL_NO, ""));
        b.phoneNo.setText(sharedPreferences.getString(Constants.PHONE_NO, ""));

    }

    /**
     * Change IME option when Text changes
     */
    private void changeImeOptionOfPhoneNoET() {
        //TextChange event handler
        b.phoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //validation on phone number
                if (!s.toString().trim().isEmpty() && s.toString().trim().length() == 10) {
                    b.phoneNo.setImeOptions(EditorInfo.IME_ACTION_DONE);

                } else {
                    b.phoneNo.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                }
                InputMethodManager imm = (InputMethodManager) ObjectSenderActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.restartInput(b.phoneNo);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void changeImeOptionOfRollNoET() {
        //TextChange event handler
        b.rollNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //validation on roll number
                if (!s.toString().trim().isEmpty() && s.toString().trim().matches("(?!00)\\d{2}(([a-z]{4})|([A-Z]{4}))\\d{3,4}")) {
                    b.rollNo.setImeOptions(EditorInfo.IME_ACTION_NEXT);

                } else {
                    b.rollNo.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

                }
                InputMethodManager imm = (InputMethodManager) ObjectSenderActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.restartInput(b.rollNo);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void changeImeOptionOfStudentNameET() {
        //TextChange event handler
        b.studentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //validation on student name
                if (!s.toString().trim().isEmpty()) {
                    b.studentName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                } else  {
                    b.studentName.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                }
                InputMethodManager imm = (InputMethodManager) ObjectSenderActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.restartInput(b.studentName);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Trigger Event handlers to listen the actions
     */
    private void sendDataToViewerActivity() {
        //click event
        b.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation
                if (checkValidation()) {
                    sendData();
                }
            }
        });
    }

    /**
     * Sending data to ObjectViewer Activity
     */
    private void sendData() {

        Student student = new Student(b.studentName.getText().toString().trim(), b.rollNo.getText().toString().trim(), b.phoneNo.getText().toString().trim(), b.gender.getCheckedRadioButtonId());

        Intent intent = new Intent(ObjectSenderActivity.this, ObjectViewerActivity.class);

        intent.putExtra("STUDENT_DATA", student);

        startActivity(intent);

    }

    /**
     * Validation of data
     */
    private boolean checkValidation() {

        //validation of name
        if (b.studentName.getText().toString().trim().isEmpty()) {
            b.studentName.setError("Please enter student name");
            return false;
        }

        int genderId = b.gender.getCheckedRadioButtonId();

        if (genderId != R.id.gender_male && genderId != R.id.gender_female) {
            Toast.makeText(ObjectSenderActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        //validation of roll no
        if (b.rollNo.getText().toString().trim().isEmpty()) {
            b.rollNo.setError("Please enter a rollNo");
            return false;
        }
        if (!b.rollNo.getText().toString().trim().matches("(?!00)\\d{2}(([a-z]{4})|([A-Z]{4}))\\d{3,4}")) {
            b.rollNo.setError("Please enter valid rollNo");
            return false;
        }

        //validation of phone no
        if (b.phoneNo.getText().toString().trim().isEmpty()) {
            b.phoneNo.setError("Please enter a phone number");
            return false;
        }
        if (b.phoneNo.getText().toString().trim().length() != 10) {
            b.phoneNo.setError("Please enter valid number");
            return false;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //update sharedPreference
        getPreferences(MODE_PRIVATE).edit()
                .putString(Constants.STUDENT_NAME, b.studentName.getText().toString().trim())
                .putString(Constants.ROLL_NO, b.rollNo.getText().toString().trim())
                .putString(Constants.PHONE_NO, b.phoneNo.getText().toString().trim())
                .putInt(Constants.GENDER, b.gender.getCheckedRadioButtonId()).apply();

    }
}