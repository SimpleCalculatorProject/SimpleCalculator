import java.util.ArrayList;

public class Calculate {
	
	static String result;
	
	Calculate(ArrayList<String> list){
		
		ArrayList<String> array = list;
		
		for (int i = 0; i < array.size(); i++){
			if (array.get(i).equals("(")){
				int count = 1;
				int j = i + 1;
				while (count != 0){
					if (array.get(j).equals("(")){
						count++;
					}
					else if (array.get(j).equals(")")){
						count--;
					}
					if (count == 0){
						break;
					}
					j++;
				}
				ArrayList<String> subArray = new ArrayList<String>();
				for (int k = i + 1; k < j; k++){
					subArray.add(array.get(k));
				}
				Calculate z = new Calculate(subArray);
				String q = z.getResult();
				array.set(i, q);
				for(int l = j; l > i; l--){
					array.remove(l);
				}
			}	
		}
		
		for (int i = 0; i < array.size(); i++){
			if (array.get(i).equals("²") || array.get(i).equals("√")){
				if (array.get(i).equals("²")){
					String x = square(array.get(i- 1));
					array.set(i, x);
					result = array.get(i);
					array.remove(i - 1);
					i--;
				}
				else{
					String x = squareRoot(array.get(i + 1));
					array.set(i, x);
					result = array.get(i);
					array.remove(i + 1);
				}
			}
		}
		
		for (int i = 0; i < array.size(); i++){
			if (array.get(i).equals("*") || array.get(i).equals("÷")){
				if (array.get(i).equals("*")){
					String x = multiplication(array.get(i- 1), array.get(i + 1));
					array.set(i, x);
					result = array.get(i);
					array.remove(i + 1);
					array.remove(i - 1);
					i--;
				}
				else{
					String x = division(array.get(i- 1), array.get(i + 1));
					array.set(i, x);
					result = array.get(i);
					array.remove(i + 1);
					array.remove(i - 1);
					i--;
				}
			}
		}
		
		for (int i = 0; i < array.size(); i++){
			if (array.get(i).equals("+") || array.get(i).equals("-")){
				if (array.get(i).equals("+")){
					String x = addition(array.get(i- 1), array.get(i + 1));
					array.set(i, x);
					result = array.get(i);
					array.remove(i + 1);
					array.remove(i - 1);
					i--;
				}
				else{
					String x = subtraction(array.get(i- 1), array.get(i + 1));
					array.set(i, x);
					result = array.get(i);
					array.remove(i + 1);
					array.remove(i - 1);
					i--;
				}
			}
		}
	}
	
	public static String addition(String x, String y){
		double xx = Double.parseDouble(x);
		double yy = Double.parseDouble(y);
		double zz = xx + yy;
		return Double.toString(zz);
	}
	
	public static String subtraction(String x, String y){
		double xx = Double.parseDouble(x);
		double yy = Double.parseDouble(y);
		double zz = xx - yy;
		return Double.toString(zz);
	}
	
	public static String multiplication(String x, String y){
		double xx = Double.parseDouble(x);
		double yy = Double.parseDouble(y);
		double zz = xx * yy;
		return Double.toString(zz);
	}
	
	public static String division(String x, String y){
		double xx = Double.parseDouble(x);
		double yy = Double.parseDouble(y);
		double zz = xx / yy;
		return Double.toString(zz);
	}
	
	public static String square(String x){
		double xx = Double.parseDouble(x);
		double zz = xx * xx;
		return Double.toString(zz);
	}
	
	public static String squareRoot(String x){
		double xx = Double.parseDouble(x);
		double zz = Math.sqrt(xx);
		return Double.toString(zz);
	}
	
	public static String getResult(){
		return result;
	}
	
	public static void main(String [] args){
		ArrayList<String> list = new ArrayList<String>();
		list.add("7");
		list.add("+");
		list.add("√");
		list.add("9");
		list.add("-");
		list.add("2");
		list.add("²");
		list.add("*");
		list.add("(");
		list.add("3");
		list.add("-");
		list.add("(");
		list.add("6");
		list.add("*");
		list.add("2");
		list.add(")");
		list.add(")");
		list.add("²");
		list.add("+");
		list.add("(");
		list.add("3");
		list.add("*");
		list.add("5");
		list.add(")");
		list.add("*");
		list.add("√");
		list.add("(");
		list.add("5");
		list.add("÷");
		list.add("4");
		list.add(")");
		
		Calculate y = new Calculate(list);
		System.out.println(getResult());
	}
}
