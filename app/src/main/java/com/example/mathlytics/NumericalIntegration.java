package com.example.mathlytics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

public class NumericalIntegration extends AppCompatActivity {

    private EditText xValuesInput;
    private EditText yValuesInput;
    private EditText lowerBoundInput;
    private EditText upperBoundInput;
    private EditText intervalsInput;
    private Spinner methodSpinner;
    private TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numerical_integration);

        // Get references to UI elements
        xValuesInput = findViewById(R.id.x_values_input);
        yValuesInput = findViewById(R.id.y_values_input);
        lowerBoundInput = findViewById(R.id.lower_bound_input);
        upperBoundInput = findViewById(R.id.upper_bound_input);
        intervalsInput = findViewById(R.id.intervals_input);
        methodSpinner = findViewById(R.id.method_spinner);
        resultTextView = findViewById(R.id.result_text_view);

        Button calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input values
                String xValuesString = xValuesInput.getText().toString();
                String yValuesString = yValuesInput.getText().toString();
                double lowerBound = Double.parseDouble(lowerBoundInput.getText().toString());
                double upperBound = Double.parseDouble(upperBoundInput.getText().toString());
                int intervals = Integer.parseInt(intervalsInput.getText().toString());
                String method = methodSpinner.getSelectedItem().toString();

                // Convert comma-separated input strings to arrays of doubles
                double[] xValues = Arrays.stream(xValuesString.split(",")).mapToDouble(Double::parseDouble).toArray();
                double[] yValues = Arrays.stream(yValuesString.split(",")).mapToDouble(Double::parseDouble).toArray();

                // Call the appropriate numerical integration method based on user selection
                double result = 0;
                if (method.equals("Trapezoidal Rule")) {
                    result = trapezoidalRule(xValues, yValues, lowerBound, upperBound, intervals);
                } else if (method.equals("Simpson 1/3 Rule")) {
                    result = simpsonsOneThirdRule(xValues, yValues, lowerBound, upperBound, intervals);
                } else if (method.equals("Simpson 3/8 Rule")) {
                    result = simpsonsThreeEighthRule(xValues, yValues, lowerBound, upperBound, intervals);
                }

                // Display the result
                resultTextView.setText(String.format("%.4f", result));
            }
        });

    }

    private double trapezoidalRule(double[] xValues, double[] yValues, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (yValues[0] + yValues[n]);

        for (int i = 1; i < n; i++) {
            sum += yValues[i];
        }

        return h * sum;
    }

    private double simpsonsOneThirdRule(double[] xValues, double[] yValues, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = yValues[0] + yValues[n];

        for (int i = 1; i < n; i++) {
            if (i % 2 == 0) {
                sum += 2 * yValues[i];
            } else {
                sum += 4 * yValues[i];
            }
        }

        return (h / 3) * sum;
    }

    private double simpsonsThreeEighthRule(double[] xValues, double[] yValues, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = yValues[0] + yValues[n];

        for (int i = 1; i < n; i++) {
            if (i % 3 == 0) {
                sum += 2 * yValues[i];
            } else {
                sum += 3 * yValues[i];
            }
        }

        return (3 * h / 8) * sum;
    }
}