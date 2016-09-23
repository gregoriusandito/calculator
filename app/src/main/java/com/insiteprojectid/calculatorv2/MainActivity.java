package com.insiteprojectid.calculatorv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    
    private Button button9,button8,button7,button6,button5,button4,
            button3,button2,button1,button0,buttonCommas,
            buttonPlus,buttonSubstract,buttonDivide,buttonMult,
            buttonValue,buttonCC, buttonPercent, buttonSqrt, buttonSquare,
            buttonBackspace;
    private EditText inputText;
    private ViewGroup rootView;
    String s = "0";
    double result = 0, temp;
    char currentOperation = ' ';
    private boolean dotPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button9=(Button)findViewById(R.id.button9);
        button8=(Button)findViewById(R.id.button8);
        button7=(Button)findViewById(R.id.button7);
        button6=(Button)findViewById(R.id.button6);
        button5=(Button)findViewById(R.id.button5);
        button4=(Button)findViewById(R.id.button4);
        button3=(Button)findViewById(R.id.button3);
        button2=(Button)findViewById(R.id.button2);
        button1=(Button)findViewById(R.id.button1);
        button0=(Button)findViewById(R.id.button0);
        rootView=(ViewGroup)findViewById(R.id.rootView);
        buttonCommas=(Button)findViewById(R.id.buttonCommas);
        buttonPlus=(Button)findViewById(R.id.buttonPlus);
        buttonSubstract=(Button)findViewById(R.id.buttonSubstract);
        buttonDivide=(Button)findViewById(R.id.buttonDivide);
        buttonMult=(Button)findViewById(R.id.buttonMult);
        buttonValue=(Button)findViewById(R.id.buttonValue);
        buttonCC=(Button)findViewById(R.id.buttonCC);
        buttonPercent=(Button)findViewById(R.id.buttonPercent);
        buttonSqrt=(Button)findViewById(R.id.buttonSqrt);
        buttonBackspace=(Button)findViewById(R.id.buttonBackspace);
        buttonSquare=(Button)findViewById(R.id.buttonSquare);
        buttonSquare.setText(Html.fromHtml("x<sup>2</sup>"));
        inputText=(EditText)findViewById(R.id.editText);
        button9.setOnClickListener(this);
        button8.setOnClickListener(this);
        button7.setOnClickListener(this);
        button6.setOnClickListener(this);
        button5.setOnClickListener(this);
        button4.setOnClickListener(this);
        button3.setOnClickListener(this);
        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
        button0.setOnClickListener(this);
        buttonCommas.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonSubstract.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMult.setOnClickListener(this);
        buttonValue.setOnClickListener(this);
        buttonCC.setOnClickListener(this);
        buttonPercent.setOnClickListener(this);
        buttonSqrt.setOnClickListener(this);
        buttonSquare.setOnClickListener(this);
        buttonBackspace.setOnClickListener(this);
        inputText.setOnClickListener(this);
        //event listener
        setupAllListener(rootView);
    }

    //the log
    private void setupAllListener(ViewGroup rootView) {
        int numChild = rootView.getChildCount();
//        Log.v(TAG,"Child Number: " + rootView.getChildCount());
        for(int i = 0; i < numChild; i++){
            View v = rootView.getChildAt(i);
//            Log.v(TAG,"View id: " + v.getId());
            if(v instanceof ViewGroup){
                setupAllListener((ViewGroup) v);
            } else if(v instanceof Button){
                Button button = (Button) v;
                button.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:

                //all-round code for button 0 to 9
                String varDigit = ((Button) v).getText().toString();
                if (s.equals("0")) {
                    s = varDigit;
                } else {
                    s += varDigit;
                }
                inputText.setText(s);
                if (currentOperation == '=') {
                    result = 0;
                    currentOperation = ' ';
                }
                //end of all-round code
                break;
            case R.id.buttonPlus:
                compute();
                currentOperation = '+';
                break;
            case R.id.buttonSubstract:
                compute();
                currentOperation = '-';
                break;
            case R.id.buttonDivide:
                compute();
                currentOperation = '/';
                break;
            case R.id.buttonMult:
                compute();
                currentOperation = '*';
                break;
            case R.id.buttonSqrt:
                temp = Double.parseDouble(inputText.getText().toString());
                result = Math.sqrt(temp);
                inputText.setText(String.valueOf(result));
                s = String.valueOf(result);
                dotPresent = false;
                break;
            case R.id.buttonSquare:
                temp = Double.parseDouble(inputText.getText().toString());
                result = Math.pow(temp, 2);
                inputText.setText(String.valueOf(result));
                s = String.valueOf(result);
                dotPresent = false;
                break;
            case R.id.buttonPercent:
                compute();
                currentOperation = '%';
                break;
            case R.id.buttonValue:
                compute();
                currentOperation = '=';
                break;
            case R.id.buttonCC:
                result = 0;
                s = "0";
                currentOperation = ' ';
                inputText.setText("0");
                dotPresent = false;
                break;
            case R.id.buttonBackspace:
                applyBackspace();
                break;
            case R.id.buttonCommas:
                if (!dotPresent){
                    s = String.valueOf(inputText.getText() + ".");
                    inputText.setText(inputText.getText() + ".");
                    dotPresent = true;
                }
                break;
            default:
                Log.v(TAG, "log all");
        }
    }

    private void applyBackspace() {
        if (s.length() > 1 ) {
            s = s.substring(0, s.length() - 1);
            //i'm sorry but this part below still get any
            //the part below means if there isn't a commas found on inputText, user can insert new commas
            if(s.matches("(.*).(.*)")){
                dotPresent = true;
            } else {
                dotPresent = false;
            }
            inputText.setText(s);
        }
        else if (s.length() <=1 ) {
            s = "0";
            inputText.setText("0");
            dotPresent = false;
        }
    }

    private void compute() {
        double doubleNum = Double.parseDouble(s);
        s = "0";
        if (currentOperation == ' ') {
            result = doubleNum;
        } else if (currentOperation == '+') {
            result += doubleNum;
            dotPresent = false;
        } else if (currentOperation == '-') {
            result -= doubleNum;
            dotPresent = false;
        } else if (currentOperation == '*') {
            result *= doubleNum;
            dotPresent = false;
        } else if (currentOperation == '/') {
            result /= doubleNum;
            dotPresent = false;
        } else if (currentOperation == '%') {
            result %= doubleNum;
            dotPresent = false;
        } else if (currentOperation == '=') {
            //left it blank to keep the result for the next operation
        }
        inputText.setText(String.valueOf(result));
    }
}