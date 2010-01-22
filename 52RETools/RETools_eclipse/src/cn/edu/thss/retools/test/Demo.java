package cn.edu.thss.retools.test;

import cn.edu.thss.retools.InputSequence;
import cn.edu.thss.retools.Matcher;
import cn.edu.thss.retools.Pattern;
import cn.edu.thss.retools.REFormatException;
import cn.edu.thss.retools.TransitionNotMatchException;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String patternString;
		Pattern pattern;
		InputSequence inputs;
		patternString = "I(ve.?ry)+happy";//"[521]*";//"10*[12]|ab+[bc]?|011(12*|abc)+z";
		String input = "Iveryveryveryhappy";
//		inputs = new InputSequence(input);
		inputs = InputSequence.CreateTextInput(input);
		
		pattern = new Pattern(patternString);
		
		System.out.println("The model of the DFA!");
		pattern.PrintPattern();
	
		Matcher matcher;
		try {
			matcher = pattern.CreateMatcher(inputs);
			if(matcher.Matches())
			{
				System.out.println("match!");
				System.out.println("Pattern String: " + patternString);
				System.out.println("Matched String: " + matcher.GetInputs());
			}
			else
				System.out.println("No match");
		} catch (REFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransitionNotMatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
