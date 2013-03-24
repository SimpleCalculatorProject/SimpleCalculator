package com.simple.calculator;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	public ArrayList<String> calculate;
	public CharSequence calcuBuf;
	public boolean calculated = true;
	public boolean bracop = false;
	TextView screen;
	Button btn0;
	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;
	Button btn6;
	Button btn7;
	Button btn8;
	Button btn9;
	Button plus;
	Button minus;
	Button multiply;
	Button division;
	Button equalto;
	Button potency;
	Button squareRoot;
	Button brackets;
	Button point;
	Button answer;
	Button clear;
	Button backSpace;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		screen = (TextView) findViewById(R.id.view);
		btn0 = (Button) findViewById(R.id.button0);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button)	findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btn5 = (Button) findViewById(R.id.button5);
		btn6 = (Button) findViewById(R.id.button6);
		btn7 = (Button) findViewById(R.id.button7);
		btn8 = (Button) findViewById(R.id.button8);
		btn9 = (Button) findViewById(R.id.button9);
		plus = (Button) findViewById(R.id.btnPlus);
		minus = (Button) findViewById(R.id.btnMinus);
		multiply = (Button) findViewById(R.id.btnMulti);
		division = (Button) findViewById(R.id.btnDiv);
		equalto = (Button) findViewById(R.id.btnEqual);
		potency = (Button) findViewById(R.id.btnPotens);
		squareRoot = (Button) findViewById(R.id.btnSqrRoot);
		brackets = (Button) findViewById(R.id.btnBracket);
		clear = (Button) findViewById(R.id.buttonClear); 
		backSpace = (Button) findViewById(R.id.btnErase);
		answer = (Button) findViewById(R.id.btnAns);
		point = (Button) findViewById(R.id.btnPoint);
		
		
	}
	
	public void addNumber(char a){
		switch (a){
		//TODO
		case '+': break;
		case '-': break;
		case '/': break;
		case '*': break;
		default: break;
		}
		
	}
	
	public void don(View v){
		if (this.calculated) {
			calculated=false;
			this.screen.setText("");
		}
		String tmp =  v.getTag().toString();
		CharSequence t = this.screen.getText();
		t = t.toString() + tmp;
		this.screen.setText(t);
	}
	public void doact(View v){
		String tmp = v.getTag().toString();
		CharSequence t = this.screen.getText();
		t = t.toString() + tmp;
		this.screen.setText(t);
	}
	public void clear(View v){
		this.screen.setText("");
		this.calculated = false;
	}
	public void erase(View v){
		if (this.calculated == true){
			this.screen.setText("");
			this.calculated = false;
			return;
		}
		CharSequence t = this.screen.getText();
		if (t.length() == 0) return;
		t = t.subSequence(0, (t.length()-1));
		this.screen.setText(t);
	}
	public void calc(View v){
		if (this.calculated == true) return;
		CharSequence t = this.screen.getText();
		if (t.length() == 0) return;
		t = t + "\n=";
		this.screen.setText(t);
		this.calculated = true;
	}
	public void brac(View v){
		if (this.bracop == true){
			CharSequence t = this.screen.getText();
			t = t.toString() + ")";
			this.screen.setText(t);
			this.bracop = false;
			return;
		}
		this.bracop = true;
		CharSequence t = this.screen.getText();
		t = t.toString() + "(";
		this.screen.setText(t);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
