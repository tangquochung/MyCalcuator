package com.example.mycalcuator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrakeOpen, getButtonBrakeClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot, buttonCompass;
    Gson gson = new Gson();
    SharedPreferences sharedPreferences;

    int historyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC, R.id.button_c);
        assignId(buttonBrakeOpen, R.id.button_open_bracket);
        assignId(getButtonBrakeClose, R.id.button_close_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonEquals, R.id.button_equal);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDot, R.id.button_dot);
        assignId(buttonCompass, R.id.bnt_Compass);

        // Store Previous Result
        sharedPreferences = this.getSharedPreferences("com.example.mycalcuator", android.content.Context.MODE_PRIVATE);

        String previous = sharedPreferences.getString("preResult", "");

       Log.i("previous result", previous);

       solutionTv.setText(previous);
    }

    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button =(MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = resultTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        if(buttonText.equals("=")){
            String finalResult = getResult(dataToCalculate);
            resultTv.setText(finalResult);


            // Store Previous Result
            String preResult = dataToCalculate + "=" + finalResult;

            // Store previous result to shared preferences
            sharedPreferences.edit().putString("preResult", preResult).apply();

            // Update previous result
            String previous = sharedPreferences.getString("preResult", "");

            Log.i("previous result", previous);

            solutionTv.setText(previous);

            historyCount += 1;

            // Starting to save history operation
            Thread SaveHistoryOperation = new Thread(new Runnable() {
                @Override
                public void run() {
                    File path = getApplicationContext().getFilesDir();

                    // Check if history.json exist
                    File historyFile = new File(path + "/history.json");
                    boolean isHistoryFileExist = historyFile.exists();
                    if (!isHistoryFileExist) {
                        try{
                            FileOutputStream writer = new FileOutputStream(new File(path, "history.json"));
                            HistoryOperation historyOperation = new HistoryOperation(solutionTv.getText().toString(), resultTv.getText().toString());
                            List<HistoryOperation> list_HistoryOperation = new ArrayList<>();
                            list_HistoryOperation.add(historyOperation);
                            String str_HistoryOperationJson = gson.toJson(list_HistoryOperation);
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(new File(path, "history.json"));
                                fileOutputStream.write(str_HistoryOperationJson.getBytes());
                                fileOutputStream.close();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "wrote to file: history.json" , Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Read history.json file
                        File readFrom = new File(path, "history.json");
                        String JSON_str_historyOperationList;
                        byte[] byte_historyOperationList = new byte[(int) readFrom.length()];
                        Type listType = new TypeToken<List<HistoryOperation>>(){}.getType();

                        try {
                            FileInputStream inputStream = new FileInputStream(readFrom);
                            inputStream.read(byte_historyOperationList);
                            JSON_str_historyOperationList = new String(byte_historyOperationList);
                            ArrayList<HistoryOperation> historyOperationArrayList = gson.fromJson(JSON_str_historyOperationList, listType);
                            int a = historyOperationArrayList.size();
                            if (historyOperationArrayList.size() >= 10) {
                                historyOperationArrayList.remove(0);
                            }

                            // After reading the history.json,
                            // then we append a new operation the history operation list for saving
                            HistoryOperation historyOperation = new HistoryOperation(solutionTv.getText().toString(), resultTv.getText().toString());
                            historyOperationArrayList.add(historyOperation);
                            String historyOperationString = gson.toJson(historyOperationArrayList);
                            FileOutputStream writer = new FileOutputStream(new File(path, "history.json"));
                            writer.write(historyOperationString.getBytes());
                            writer.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "wrote to file: history.json" , Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            SaveHistoryOperation.start();

            return;
        }

        if(buttonText.equals("Compass")) {
            OpenCompass();
            return;
        }

        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate + buttonText;
        }
        resultTv.setText(dataToCalculate);

    }

    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("HistoryCount", String.valueOf(historyCount));
        startActivity(intent);
    }

    public void OpenCompass() {
        Intent intent_compass = new Intent(this, CompassActivity.class);
        startActivity(intent_compass);
    }
//
//    public void next_exercise(View view) {
//        Intent intent = new Intent(this, TestAPI.class);
//        startActivity(intent);
//    }
}