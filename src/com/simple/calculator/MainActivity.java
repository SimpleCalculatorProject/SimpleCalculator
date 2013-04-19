package com.simple.calculator;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
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
	 * Here is defined screen TextView, SrollView and history string for screen
	 */
	TextView screen;
	ScrollView scroll;
	String history;
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
	/*
	 * Maximum value of int is defined
	 */
	public static double INTMAX = Double.valueOf("9223372036854775807");
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		screen = (TextView) findViewById(R.id.view);
		scroll = (ScrollView) findViewById(R.id.scroll);
		// history string is initialized
		history = "\n\n\n";
		// screen is initialized
		this.updScreen();
		
	}
	public void updScreen(){
		/**
		 * updScreen() is method for updating TextView called screen for giving user feedback
		 * screen shows calculation that is entered 
		 */
		if (this.calculate.size() == 0){ 
			// Set number 0 for screen if no calculation has been given
			this.screen.setText(history+"0");
			scroll.post(new Runnable(){
				@Override
				public void run() {
					scroll.fullScroll(View.FOCUS_DOWN);
				}
			});
			return;
		}
		// Idea is show user everything that has been set for ArrayList calculate by getting all Strings and adding them into one and setting that string text for TextView screen
		String tmp = "";
		for (String s : this.calculate) tmp = tmp + s;
		// history and calculation is added to screen
		this.screen.setText(history +tmp);
		// After screen update we auto scroll screen to down
		scroll.post(new Runnable(){
			@Override
			public void run() {
				scroll.fullScroll(View.FOCUS_DOWN);
			}
		});
		
	}
	public void don(View v){
		/**
		 * don() is method used as button listener for number buttons
		 */
		if (this.buffer == null){ 
			if (calculate.size() == 0){
				// if calculate size is 0 and ans is pushed then ans value is set to buffer and calculate
				if ("ans".equals((String) v.getTag())){
					buffer = ans;
					calculate.add(buffer);
				}
				// if calculate size is 0 and number button is pushed then that number is set to buffer and calculate
				else{
					buffer = (String) v.getTag();
					calculate.add(this.buffer);
				}
			}
			// if calculate size is one or more and last symbol is potens it is replaced by number
			else if (calculate.get(this.calculate.size()-1).equals(POTENS) && calculate.size() != 0 ){
				if ("ans".equals((String) v.getTag())) return;
				calculate.remove(calculate.size()-1);
				buffer = calculate.get(calculate.size()-1);
				buffer  = buffer + ( (String) v.getTag());
				calculate.set(calculate.size()-1, buffer);
			}
			// if calculate size is one or more and last symbol is closing bracket nothing will be done
			else if (calculate.get(this.calculate.size()-1).equals(CBRACKET)) return;
			// if calculate size is one or more and last symbol isn't potens or closing bracket then number of tag is added to calculator
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
			// if point or ans is given then nothing will be done
			if ( ((String) v.getTag()).equals(".") &&  buffer.contains(".")) return;
			if ( ((String) v.getTag()).equals("ans")) return;
			// In other case number is add to buffer and calulate is updated
			this.buffer  = this.buffer + ( (String) v.getTag() );
			this.calculate.set(this.calculate.size()-1, this.buffer);
		}
		this.updScreen();
	}
	public void doact(View v){
		/**
		 * doact() is used button listener for actions/symbol (like +, - or x) buttons like
		 */
		// symbol is get from component tag witch is found from View
		if (calculate.size() == 0){
			// if calculate size is 0 then ans is added to calculate and after that symbol is add to calculate
			calculate.add(ans);
			this.calculate.add((String) v.getTag());
		}
		// if open bracket is only object in calculate then nothing is done
		else if (this.calculate.get(calculate.size()-1).equals(OBRACKET)&&calculate.size()==1){
			if (MINUS.equals(((String) v.getTag()))){
				buffer = "-";
				calculate.add(buffer);
			}
			this.updScreen();
			return;
		}
		else if (this.buffer != null){
			// if buffer isn't empty symbol is added to calculate and buffer is emptied
			if (buffer.equals(MINUS)){
				this.updScreen();
				return;
			}
			this.calculate.add((String) v.getTag());
			buffer = null;
			this.updScreen();
			return;
		}
		else {
			String tmp = this.calculate.get(this.calculate.size()-1);
			// if buffer is empty and if last symbol in calculate is potens or closing bracket then symbol is added to calculate
			if (tmp.equals(POTENS) || tmp.equals(CBRACKET)){
				calculate.add((String) v.getTag());
			}
			// if buffer is empty and last symbol is square root nothing will be done
			else if (tmp.equals(SQROOT)) return;
			// if buffer is empty and last symbol isn't potens, square root or closing bracket then symbol is added to calculate in way that it replaces last symbol
			else {
				// if opening bracket is in calculate and new operator is given opening bracket will be wiped
				if (this.calculate.get(calculate.size()-1).equals(OBRACKET)){
					if (MINUS.equals(((String) v.getTag()))){
						buffer = "-";
						calculate.add(buffer);
					}
					else{
						this.calculate.remove(calculate.size()-1);
					}
					this.updScreen();
					return;
				}
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
		// if calculate size is 0 then nothing will be done
		if (calculate.size() == 0) return;
 		if (buffer != null){
 			// If buffer isn't empty and buffer is longer than 1 char
 			// Then last char from buffer is removed and change is updated to calculate
 			if (buffer.length() != 1){
 				buffer = buffer.substring(0, buffer.length()-1);
 				calculate.set(calculate.size()-1, buffer);
 			}
 			// In other case (buffer isn't empty and buffer has only 1 char) buffer is emptied and last string (number) is removed from calculate
 			else {
 				calculate.remove(calculate.size()-1);
 				buffer = null;
 			}
		}
		else {
			String tmp = this.calculate.get(this.calculate.size()-1);
			// if buffer is empty and last symbol is square root then square root is removed
			if (tmp.equals(SQROOT)){
				calculate.remove(calculate.size()-1);
			}
			// if buffer is empty and last symbol is opening bracket then opening bracket is removed
			else if (tmp.equals(OBRACKET)){
				calculate.remove(calculate.size()-1);
			}
			// In other case last symbol is removed and if next to last string is number string then it will be set to buffer
			else {
				calculate.remove(calculate.size()-1);
				tmp = calculate.get(calculate.size()-1);
				if (tmp.equals(POTENS)) ;
				else if (tmp.equals(CBRACKET));
				else buffer = tmp;
			}
		}
		this.updScreen();
	}
	public void calc(View v){
		/**
		 * calc() is button listener for "=" symbol and does the calculating. calc() calls Calculate.java with does calculating in this application
		 */
		//if calculate size is 1 then nothing will be done
		if (this.calculate.size() == 0) return;	
		String tmp = this.calculate.get(this.calculate.size()-1);
		//if last symbol in calculate is of the following [ +, -, x, ÷, √, ( ] then last symbol will be removed from calculate because it would cause error
		if (tmp.equals(SQROOT) || tmp.equals(MULTIPLY) || tmp.equals(MINUS) || tmp.equals(PLUS) || tmp.equals(DIVISION) || tmp.equals(OBRACKET)){
			// if only symbol in calculate is "(" then calculate will be initialized and nothing else will be done
			if (this.calculate.size() == 1 && tmp.equals(OBRACKET)){
				this.calculate = new ArrayList<String>();
				this.updScreen();
				return;
			}
			else if (tmp.equals(OBRACKET) || tmp.equals(SQROOT)){
				// if last symbol is "(" or square root and calculate is longer than 1 then:
				// 1. Last symbol is removed
				// 2. Recursion is used to make new 'validation' of calculation
				// 3. Recursion ends and result is displayed for user
				// 4. Return; is used that calculation is not done many times
				this.calculate.remove(this.calculate.size()-1);
				calc(v);
				return;
			}
			else{
				// in other cases last symbol will be removed
				this.calculate.remove(this.calculate.size()-1);
			}
		}
		int open = 0;
		for (int i = 0; i < this.calculate.size(); i++){
			// This for loop has two purposes:
			// 1. count how many open brackets are in calculate
			// 2. change "x" symbols to "*" symbols
			// 3. handles error control like change "." to "0.0" for example
			if (this.calculate.get(i).equals(OBRACKET)) open++;
			else if (this.calculate.get(i).equals(CBRACKET)) open--;
			else if (this.calculate.get(i).equals(".")) this.calculate.set(i, "0");
			if (this.calculate.get(i).startsWith(".")) this.calculate.set(i, "0"+this.calculate.get(i));
			if (this.calculate.get(i).startsWith("-.")) this.calculate.set(i, "-0"+this.calculate.get(i).substring(1));
			if (this.calculate.get(i).endsWith(".")) this.calculate.set(i, this.calculate.get(i)+ "0");
			if (this.calculate.size() > 1 && i != 0){
				if (this.calculate.get(i).equals("-") && this.calculate.get(i-1).equals("(")){
					if (i != (this.calculate.size() + 1))
						if (this.calculate.get(i+1).equals(")"))
							this.calculate.set(i, "-0");
					else
						this.calculate.set(i, "-0");
				}					
			}
		}
		while (open > 0){
			// This while loop will close all open brackets
			this.calculate.add(CBRACKET);
			open--;
		}
		ArrayList<String> temp = new ArrayList<String>();
		for (String zz : this.calculate) temp.add(zz);
		for (int i = 0; i < temp.size(); i++) if (temp.get(i).equals(MULTIPLY)) temp.set(i, "*");
		this.updScreen();
		tmp = "";
		try {
			for (String s : this.calculate) tmp = tmp + s;
			// Try Catch is used to ensure that if some illegal calculate is give for Calculate.java then application don't crash and gives user error message
			// First in this try calculate we call Calculate.java and give calculate for it
			new Calculate(temp);
			// Then answer from calculation is saved to ans
			this.ans = Calculate.getResult();
			// Then ans will be simplified if possible by using double and integer variables 
			double test = Double.parseDouble(this.ans);
			if (test%1==0 && test<INTMAX){
				//this.ans = this.ans.substring(0, this.ans.length()-2);
				Double a = Double.parseDouble(this.ans);
				this.ans = String.valueOf(a.longValue());
			}
			// Last ans will be set for screen
			//String lastText = (String) this.screen.getText();
			
			this.screen.setText(history + tmp + "=\n"+this.ans);
			this.history += tmp + "=\n"+this.ans+"\n\n";
		}
		catch(java.lang.Exception e) {
			// if there is error or exception in try bloc and error message will be given for user
			this.history += tmp + "=\n"+"ERROR \n\n";
			this.calculate = new ArrayList<String>();
			//System.out.print(e.toString());
			this.ans = "0";
			this.screen.setText(history);
		}
		// Buffer is emptied and if calculate is initialize
		this.calculate = new ArrayList<String>();
		this.buffer = null;
	}
	public void brac(View v){
		/**
		 * brac() is button listener method for brackets button and tries to be smart for adding brackets
		 */
		//if calculate size is 0 then "(" will be added 
		if (calculate.size() == 0){					
			calculate.add(OBRACKET);
		}
		else {
			int open = 0;							//if calculate size is not 0 then we count "("	and ")" in calculate
			int close = 0;
			for (String st: calculate){				//bracket count is done with for loop
				if (st.equals(OBRACKET)) open ++;
				else if (st.equals(CBRACKET)) close++;
			}
			String tmp = calculate.get(calculate.size()-1);
			if (buffer == null && tmp.compareTo(POTENS) != 0){							//if buffer is empty and last symbol is not potens symbol then:
				if (close < open && tmp.equals(CBRACKET)) calculate.add(CBRACKET);		//	-if there are open brackets and last symbol is closing bracket then closing bracket will be added 
				else if (close == open && tmp.equals(CBRACKET)) return;					//	-if there are no open brackets and last symbol is closing bracket then nothing will be done
				else calculate.add(OBRACKET);											//	-in all other cases we will add opening bracket
			}
			else if (tmp.equals(POTENS) && close < open) calculate.add(CBRACKET);
			else if (buffer != null && close < open){									//if buffer isn't empty and there are open brackets then buffer will be emptied and closing bracket 
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
		if (this.buffer == null ){											//if buffer is empty and if last symbol is closing bracket then potens will be added
			if (calculate.size() == 0) {
				calculate.add(ans);
				calculate.add(POTENS);
			}
			else if (calculate.get(calculate.size()-1).equals(CBRACKET)){
				calculate.add(POTENS);
			}
			else return;
		}
		else {																//if buffer isn't empty then buffer is emptied and potens symbol will be added
			if (MINUS.equals(this.buffer) || ".".equals(this.buffer)){
				return;
			}
			buffer = null;
			calculate.add(POTENS);
		}
		this.updScreen();
	}
	public void squeroot(View v){
		/**
		 * squeroot() is button listener for square root button
		 */
		if (this.buffer != null) return;									//if buffer isn't null then nothing will be done
		if (calculate.size() != 0){										
			if (calculate.get(calculate.size()-1).equals(POTENS)) return;
			else if (calculate.get(calculate.size()-1).equals(SQROOT)){
				calculate.add(OBRACKET);
				calculate.add(SQROOT);
			}
			else calculate.add(SQROOT);
		}
		else calculate.add(SQROOT);												//if last symbol is not potens then square root will be added
		this.updScreen();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    	case R.id.menuAbout:
	    		// if menuAbout is chosen new activity for credits/about view is started
	    		Intent about = new Intent(MainActivity.this, AboutActivity.class);
	    		MainActivity.this.startActivity(about);
	    		return true;
	    	case R.id.menuEquation:
	    		// if menuEquation is chosen new activity for equation view is started
	    		Intent equation = new Intent(MainActivity.this, EquationActivity.class);
	    		MainActivity.this.startActivity(equation);
	    		return true;
	    	case R.id.menu_share:
	    		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
	    		shareIntent.setType("text/plain");
	    		shareIntent.putExtra(Intent.EXTRA_TEXT, "Simple Calculator" + history);
	    		startActivity(Intent.createChooser(shareIntent, "Share..."));
	    		return true;
	    	case R.id.clearHistory:
	    		// if clearHistory is chosen calculation is history is cleared and set to screen
	    		history = "\n\n\n";
	    		buffer = null;
	    		calculate = new ArrayList<String>();
	    		this.updScreen();
	    		return true;
	    	default:
	    		return false;
	    }
	}

}
