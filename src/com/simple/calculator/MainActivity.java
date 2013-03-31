package com.simple.calculator;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	/**
	 * Project Simple Calculator : Main Activity
	 * This class is main activity class for Simple Calculator project
	 * In this class is portrait mode for calculator interface found in activity_main.xml
	 * This is only interface class and all calculations are done in different class
	 * Class tries to be smart about what inputs are valid and what are not and that way prevent user errors
	 */
	public ArrayList<String> calculate = new ArrayList<String>();		//This ArrayList holds calculation
	public String buffer = null;										//This String is buffer for adding numbers to the calculate Sting ArrayList
	public String ans = "0";											//This Sting holds last answer that is calculated and it has default value of 0
	/*
	 * Here is interface TextView
	 */
	TextView screen;
	
	/*
	 * Hear is few static variables for some important chars
	 */
	public static String POTENS = "²";
	public static String SQROOT = "√";
	public static String OBRACKET = "(";
	public static String CBRACKET = ")";
	public static String DIVISION = "÷";
	public static String MULTIPLY = "x";
	public static String PLUS = "+";
	public static String MINUS = "-";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		screen = (TextView) findViewById(R.id.view);
		
	}
	public void updScreen(){
		/**
		 * updScreen() is method for updating TextView called screen for giving user feedback
		 * screen shows calculation that is entered 
		 */
		if (this.calculate.size() == 0){ 
			// Set number 0 for screen if no calculation has been given
			this.screen.setText("0");
			return;
		}
		// Idea is show user everything that has been set for ArrayList calculate by getting all Strings and adding them into one and setting that string text for TextView screen
		String tmp = "";
		for (String s : this.calculate) tmp = tmp + s;
		this.screen.setText(tmp);
	}
	public void don(View v){
		/**
		 * don() is method used as button listener for number buttons
		 */
		if (this.buffer == null){ 
			if (calculate.size() == 0){
				if ("ans".equals((String) v.getTag())){
					buffer = ans;
					calculate.add(buffer);
				}
				else{
					buffer = (String) v.getTag();
					calculate.add(this.buffer);
				}
			}
			else if (calculate.get(this.calculate.size()-1).equals(POTENS) && calculate.size() != 0){
				calculate.remove(calculate.size()-1);
				buffer = calculate.get(calculate.size()-1);
				buffer  = buffer + ( (String) v.getTag());
				calculate.set(calculate.size()-1, buffer);
			}
			else if (calculate.get(this.calculate.size()-1).equals(CBRACKET)) return;
			else {
				if ("ans".equals((String) v.getTag())){
					buffer = ans;
					calculate.add(buffer);
				}
				else {
					this.buffer = (String) v.getTag();
					this.calculate.add(this.buffer);
			
				}
			}
		}
		else {
			if ( ((String) v.getTag()).equals(".") &&  buffer.contains(".")) return;
			if ( ((String) v.getTag()).equals("ans")) return;
			this.buffer  = this.buffer + ( (String) v.getTag() );
			this.calculate.set(this.calculate.size()-1, this.buffer);
		}
		this.updScreen();
	}
	public void doact(View v){
		/**
		 * doact() is used button listener for actions/mark (like +, - or x) buttons like
		 */
		if (calculate.size() == 0) return;
		if (this.buffer != null){
			this.calculate.add((String) v.getTag());
			buffer = null;
			this.updScreen();
			return;
		}
		else {
			String tmp = this.calculate.get(this.calculate.size()-1);
			if (tmp.equals(POTENS) || tmp.equals(CBRACKET)){
				calculate.add((String) v.getTag());
			}
			else if (tmp.equals(SQROOT)) return;
			else {
				this.calculate.set(calculate.size()-1, (String) v.getTag());
			}
		}
		this.updScreen();
	}
	public void clear(View v){
		/**
		 * clear() is button listener method for clear button and it clear buffer and calculate ArrayList
		 */
		this.calculate = new ArrayList<String>();
		this.buffer = null;
		this.updScreen();
	}
	public void erase(View v){
		/**
		 * erase() is button listener method for erasing one char or number from TextView screen
		 */
		if (calculate.size() == 0) return;
 		if (buffer != null){
 			if (buffer.length() != 1){
 				buffer = buffer.substring(0, buffer.length()-1);
 				calculate.set(calculate.size()-1, buffer);
 			}
 			else {
 				calculate.remove(calculate.size()-1);
 				buffer = null;
 			}
		}
		else {
			String tmp = this.calculate.get(this.calculate.size()-1);
			//if (tmp.equals(POTENS));
			if (tmp.equals(SQROOT)){
				calculate.remove(calculate.size()-1);
			}
			else if (tmp.equals(OBRACKET)){
				calculate.remove(calculate.size()-1);
			}
			//else if (tmp.equals(CBRACKET));
			else {
				calculate.remove(calculate.size()-1);
				tmp = this.calculate.get(this.calculate.size()-1);
				if (tmp.equals(POTENS)) ;
				else if (tmp.equals(CBRACKET)) ;
				else buffer = tmp;
			}
		}
		this.updScreen();
	}
	public void calc(View v){
		//TODO jos laskutoimitus on viimeinen merkki niin se pitaa poistaa
		String tmp = this.calculate.get(this.calculate.size()-1);
		if (tmp.equals(SQROOT) || tmp.equals(MULTIPLY) || tmp.equals(MINUS) || tmp.equals(PLUS) || tmp.equals(DIVISION) || tmp.equals(OBRACKET)){
			if (this.calculate.size() == 1 && tmp.equals(OBRACKET)){
				this.calculate = new ArrayList<String>();
				return;
			}
			else if (tmp.equals(OBRACKET)){
				this.calculate.remove(this.calculate.size()-1);
				this.calculate.remove(this.calculate.size()-1);
			}
			else{
				this.calculate.remove(this.calculate.size()-1);
			}
		}
		int open = 0;
		for (int i = 0; i < this.calculate.size(); i++){
			if (this.calculate.get(i).equals(OBRACKET)) open++;
			else if (this.calculate.get(i).equals(CBRACKET)) open--;
			else if (this.calculate.get(i).equals(MULTIPLY)) this.calculate.set(i, "*");
		}
		while (open > 0){
			this.calculate.add(CBRACKET);
		}
		try {
			new Calculate(this.calculate);
			this.ans = Calculate.getResult();
			this.screen.setText(this.ans);
		}
		catch(java.lang.Exception e) {
			this.screen.setText("ERROR");
			this.ans = "0";
		}
		this.calculate = new ArrayList<String>();
		this.buffer = null;
	}
	public void brac(View v){
		/**
		 * brac() is button listener method for brackets button and tries to be smart for adding brackets
		 */
		if (calculate.size() == 0){
			calculate.add(OBRACKET);
		}
		else {
			int open = 0;
			int close = 0;
			for (String st: calculate){
				if (st.equals(OBRACKET)) open ++;
				else if (st.equals(CBRACKET)) close++;
			}
			String tmp = calculate.get(calculate.size()-1);
			if (buffer == null && tmp.compareTo(POTENS) != 0){
				if (close < open && tmp.equals(CBRACKET)) calculate.add(CBRACKET);
				else if (close == open && tmp.equals(CBRACKET)) return;
				else calculate.add(OBRACKET);
			}
			else if (buffer != null && close < open){
				buffer = null;
				calculate.add(CBRACKET);
			}
		}
		this.updScreen();
	}
	public void tosecond(View v){
		/**
		 * tosecond() is button listener method for potency button
		 */
		if (this.buffer == null ){
			if (calculate.get(calculate.size()-1).equals(CBRACKET)){
				calculate.add(POTENS);
			}
			else return;
		}
		else {
			buffer = null;
			calculate.add(POTENS);
		}
		this.updScreen();
	}
	public void squeroot(View v){
		/**
		 * squeroot() is button listener for square root button
		 */
		if (this.buffer != null) return;
		if (calculate.size() != 0)
			if (calculate.get(calculate.size()-1).equals(POTENS))
				return;
		calculate.add(SQROOT);
		this.updScreen();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
