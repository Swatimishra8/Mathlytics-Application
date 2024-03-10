package com.example.mathlytics;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DifferentialEquationSolution extends AppCompatActivity {

    private EditText equationInput, stepSize, initialValue;
    private TextView resultTextView;
    private Button calculateButton;
    private  String method;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_differential_equation_solution);

        //fetching value from the activity
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button  heading = findViewById(R.id.headingDifferentialEquation);
        Intent intent = getIntent();
        if(intent.getStringExtra("Method_name").contains("Taylor Series")){
            heading.setText("Taylor Series Method");
        }
        else if(intent.getStringExtra("Method_name").contains("Euler")){
            heading.setText("Euler Method");
        }
        else if(intent.getStringExtra("Method_name").contains("Euler's Modified")){
            heading.setText("Euler's Modified Method");
        }
        else if(intent.getStringExtra("Method_name").contains("Runge Kutta")){
            heading.setText("Runge Kutta Method");
        }
        else if(intent.getStringExtra("Method_name").contains("Milne's Predictor Corrector")){
            heading.setText("Milne's Predictor Corrector Method");
        }
        else if(intent.getStringExtra("Method_name").contains("Adam's Predictor Corrector")){
            heading.setText("Adam's Predictor Corrector Method");
        }
    }
}