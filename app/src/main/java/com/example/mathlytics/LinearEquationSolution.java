package com.example.mathlytics;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LinearEquationSolution extends AppCompatActivity {

    private EditText equation1Input, equation2Input, equation3Input;
    private TextView resultTextView;
    private Button calculateButton;
    private String method;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearequationsolution);

        //fetching value from the activity
        Button  heading = findViewById(R.id.headingLinearEquation);
        Intent intent = getIntent();
        if(intent.getStringExtra("Method_name").contains("Gauss Jordan")){
             heading.setText("Gauss-Jordan Method");
        }
        else if(intent.getStringExtra("Method_name").contains("Gauss Seidal")){
            heading.setText("Gauss-Seidal Method");
        }
        else if(intent.getStringExtra("Method_name").contains("Crouts Method")){
            heading.setText("Crout's Method");
        }

        // Get references to UI elements
        equation1Input = findViewById(R.id.equation1_input);
        equation2Input = findViewById(R.id.equation2_input);
        equation3Input = findViewById(R.id.equation3_input);
        resultTextView = findViewById(R.id.result_text_view);
        calculateButton = findViewById(R.id.calculate_button);

        // method = getIntent().getStringExtra("Method_name");

        String equation1 = equation1Input.getText().toString();
        String equation2 = equation2Input.getText().toString();
        String equation3 = equation3Input.getText().toString();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getStringExtra("Method_name").contains("Gauss Jordan")) {
                    solveUsingGaussJordan(equation1,equation2,equation3);
                } else if(intent.getStringExtra("Method_name").contains("Gauss Seidal")){
                    solveUsingGaussSeidel(equation1,equation2,equation3);
                }
                else if(intent.getStringExtra("Method_name").contains("Crouts Method")){
                    solveUsingCroutsMethod(equation1,equation2,equation3);
                }
            }

            private void solveUsingCroutsMethod(String equation1, String equation2, String equation3) {
                double[][] coefficients = {
                        parseEquation(equation1),
                        parseEquation(equation2),
                        parseEquation(equation3)
                };

                findSolution(coefficients);
            }

            private void solveUsingGaussSeidel(String equation1, String equation2, String equation3) {
                double[][] coefficients = {
                        parseEquation(equation1),
                        parseEquation(equation2),
                        parseEquation(equation3)
                };

                findSolution(coefficients);
            }

            private void solveUsingGaussJordan(String equation1, String equation2, String equation3) {
                double[][] coefficients = {
                        parseEquation(equation1),
                        parseEquation(equation2),
                        parseEquation(equation3)
                };

                findSolution(coefficients);
            }

            private double[] parseEquation(String equation) {
                equation = equation.replaceAll("-", "+-"); // Normalize negative coefficients
                String[] terms = equation.split("(?=[+-])");

                double[] coefficients = new double[4]; // Coefficients: a, b, c, constant term
                for (String term : terms) {
                    term = term.trim();
                    if (term.contains("x")) {
                        coefficients[0] += parseCoefficient(term);
                    } else if (term.contains("y")) {
                        coefficients[1] += parseCoefficient(term);
                    } else if (term.contains("z")) {
                        coefficients[2] += parseCoefficient(term);
                    } else {
                        coefficients[3] += Double.parseDouble(term);
                    }
                }
                return coefficients;
            }

            private double parseCoefficient(String term) {
                return Double.parseDouble(term.substring(0, term.length() - 1));
            }

            private double determinantOfMatrix(double[][] mat) {
                return mat[0][0] * (mat[1][1] * mat[2][2] - mat[2][1] * mat[1][2])
                        - mat[0][1] * (mat[1][0] * mat[2][2] - mat[1][2] * mat[2][0])
                        + mat[0][2] * (mat[1][0] * mat[2][1] - mat[1][1] * mat[2][0]);
            }

            private double[] solveEquations(double[][] coefficients, double D) {
                double[][] temp = new double[3][4];

                for (int i = 0; i < 3; i++) {
                    System.arraycopy(coefficients[i], 0, temp[i], 0, 4);
                }

                double[] solutions = new double[3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[j][i] = coefficients[j][3];
                    }
                    solutions[i] = determinantOfMatrix(temp) / D;

                    for (int j = 0; j < 3; j++) {
                        temp[j][i] = coefficients[j][i];
                    }
                }
                return solutions;
            }

            private void findSolution(double[][] coefficients) {
                double D = determinantOfMatrix(coefficients);
                double[] solution = solveEquations(coefficients, D);
                displayResult(solution);
            }

            private void displayResult(double[] solution) {
                StringBuilder result = new StringBuilder("Solution:\n");
                result.append("x = ").append(solution[0]).append("\n");
                result.append("y = ").append(solution[1]).append("\n");
                result.append("z = ").append(solution[2]);
                resultTextView.setText(result.toString());
            }

        });

    }
}