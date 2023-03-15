package com.example.mycalcuator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Calculator_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_layout);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnCong = findViewById(R.id.btnCong);
        btnTru = findViewById(R.id.btnTru);
        btnTru = findViewById(R.id.btnTru);
        btnNhan = findViewById(R.id.btnNhan);
        btnChia = findViewById(R.id.btnChia);
        btnC = findViewById(R.id.btnC);
        btnBang = findViewById(R.id.btnBang);
        btnResult = findViewById(R.id.btnResult);
    }

    //To show value of this button in TextView
    btn0.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //when press on the btn , the value on textView will be get and add to the value in ""
            txtResult.setText(txtResult.getText() + "0");
        }
    });

    //To show value of this button in TextView
    btn1.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "1");
        }
    });

    //To show value of this button in TextView
    btn2.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "2");
        }
    });

    //To show value of this button in TextView
    btn3.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "3");
        }
    });

    //To show value of this button in TextView
    btn4.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "4");
        }
    });

    //To show value of this button in TextView
    btn5.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "5");
        }
    });

    //To show value of this button in TextView
    btn6.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "6");
        }
    });

    //To show value of this button in TextView
    btn7.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "7");
        }
    });

    //To show value of this button in TextView
    btn8.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "8");
        }
    });

    //To show value of this button in TextView
    btn9.setOClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtResult.setText(txtResult.getText() + "9");
        }
    });
}
