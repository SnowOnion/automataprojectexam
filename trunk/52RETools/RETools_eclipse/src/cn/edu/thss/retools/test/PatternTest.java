/**
 * 
 */
package cn.edu.thss.retools.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import cn.edu.thss.retools.InputSequence;
import cn.edu.thss.retools.Matcher;
import cn.edu.thss.retools.ParameterNotMatchException;
import cn.edu.thss.retools.Pattern;
import cn.edu.thss.retools.REFormatException;
import cn.edu.thss.retools.TransitionNotMatchException;

/**
 * @author Huhao
 *
 */
public class PatternTest {

	private String patternString;
	private Pattern pattern;
	private InputSequence inputs;
	private Map<String, ArrayList<String> > patAInputT;
	private Map<String, ArrayList<String> > patAInputF;
	private String partFile = "partMatch.data";
	private String fullFile = "fullMatch.data";
	
	private void ReadTestData(String file){
		try {
			patAInputT.clear();
			patAInputF.clear();
			
			BufferedReader rd = new BufferedReader(new FileReader(file));
			String line;
			Map<String, ArrayList<String> > patAInput;
			while((line = rd.readLine())!= null){
			//	StringTokenizer t = new StringTokenizer(line);
				String[] tokens = line.split("\t");
				ArrayList<String> strs = new ArrayList<String>();
				String[] ins = tokens[1].split("`");
				for(int i = 0;i<ins.length; i++	){
					strs.add(ins[i]);
				}
				if(tokens[2].equals("True")){
					patAInput = patAInputT;
				}else{
					patAInput = patAInputF;
				}
				patAInput.put(tokens[0], strs);					
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		patAInputT = new HashMap<String, ArrayList<String> >();
		patAInputF = new HashMap<String, ArrayList<String> >();	
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Pattern#Pattern()}.
	 * @throws TransitionNotMatchException 
	 * @throws REFormatException 
	 * @throws ParameterNotMatchException 
	 */
	@Test
	public void testFullMatch() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException {
		ReadTestData(fullFile);
		ValueTest(patAInputT, true, InputSequence.FULL_MATCH);
		ValueTest(patAInputF, false, InputSequence.FULL_MATCH);
	}

	private void ValueTest(Map<String, ArrayList<String> > patAin, boolean val, int full) throws REFormatException, TransitionNotMatchException, ParameterNotMatchException{
		Iterator<String> patIter = patAin.keySet().iterator();
		int i = 0;
		while(patIter.hasNext()){
			patternString = patIter.next();
			pattern = new Pattern(patternString);
			
//			pattern.setMatchType(InputSequence.FULL_MATCH);
//			pattern.setMatchType(InputSequence.PART_MATCH);
			pattern.setMatchType(full);
			pattern.setCaseSensitive(InputSequence.NO_IGNORE_CASE);
//			pattern.setMatchLength(InputSequence.LONGEST_MATCH);
			pattern.setMatchLength(InputSequence.SHORTEST_MATCH);
			
			ArrayList<String> instrs = patAin.get(patternString);
			Iterator<String> inIter = instrs.iterator();
			System.out.print(i ++ + " " +  patternString);
			
			while(inIter.hasNext()){
				String input = inIter.next().trim();
				System.out.print("\t" + input);
				
//				inputs = new InputSequence(input);
				inputs = InputSequence.CreateTextInput(input);
				Matcher matcher = pattern.CreateMatcher(inputs);
				
				if(full == InputSequence.FULL_MATCH){
					if(matcher.Matches() != val){
						System.out.println("\t\tWrong:" + input + "!!!!!!!!!!!!!!!!!!");
					} else {
						System.out.print("\t\tRight\t");
						Vector<String> vs = matcher.GetMatchs();
						if(val == true){
							String str = "";
							Iterator<String> iter = vs.iterator();
							while(iter.hasNext()){
								str += "\t" + iter.next(); 
							}
							System.out.println(str);
						}else
							System.out.println("Nothing to show!");
					}
				}else{
					if(matcher.Matches() == true){
						System.out.print("\t\tMatches\t");
						Vector<String> vs = matcher.GetMatchs();
						Collections.sort(vs);
						String str = "";
						Iterator<String> iter = vs.iterator();
						while(iter.hasNext()){
							str += "\t" + iter.next(); 
						}
						if(str.equals(""))
							System.out.println("GetMatchs nothing!!!!!!!!!!!!!!!!!!!!!!!!");
						System.out.println(str);						
					}else {
						if(val == true)
							System.out.println("Wrong!!!!!!!!!!!!!!!!!!!!!");
					}
				}
				
				
			}
		}
	}
	
//	@Test
	public void testPartMatch() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException{
		ReadTestData(partFile);
		ValueTest(patAInputT, true, InputSequence.PART_MATCH);
		ValueTest(patAInputF, false, InputSequence.PART_MATCH);
	}
	/**
	 * Test method for {@link cn.edu.thss.retools.Pattern#Compile(java.lang.String)}.
	 */
	@Test
	public void testCompile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Pattern#CreateMatcher(cn.edu.thss.retools.InputSequence)}.
	 * @throws TransitionNotMatchException 
	 * @throws REFormatException 
	 * @throws ParameterNotMatchException 
	 */
//	@Test
	public void testCreateMatcher() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException {
		matchCase();		
	}
	
	private void matchCase() throws REFormatException, TransitionNotMatchException, ParameterNotMatchException{
		patternString = "(Good)+";//"[521]*";//"Ln(.1c|b.c|xY)to";//"a2.*3";//"I(ve.?ry)+happy";
		//"[521]*";//"TP131[a-z]*";//"^[0-9]+[A-Z]? [A-Z][a-z]*( [A-Z][a-z]*)*";//"10*[^0-9a-z]";//"hel\\.oo\\?.* good!";//"(Good)+";//"I(ve.?ry)+happy";//"[521]*";//"10*[12]|ab+[bc]?|011(12*|abc)+z";
		String input = "\tafdsfdGoodGoodGood";//"meishi521125";//"ln11cto";//"a2\\+333ab3";//"Iveryveryveryhappy";
		//\t12gaoxiao:\tmeishibcgaoxiao:meishiTP131gaoxiao:meishiMM";//"123A Main St";//"1A";//"badthing heL.oO?nEVer good! !!dont't";//"abcdgoogoodGood";//"Iveryveryveryhappy";
		inputs = InputSequence.CreateTextInput(input);//new InputSequence(input);
		
		pattern = new Pattern(patternString);
//		pattern.setCaseSensitive(InputSequence.NO_IGNORE_CASE);
		pattern.setCaseSensitive(InputSequence.IGNORE_CASE);
		pattern.setMatchType(InputSequence.PART_MATCH);
//		pattern.setMatchType(InputSequence.FULL_MATCH);
//		pattern.setMatchLength(InputSequence.SHORTEST_MATCH);
		pattern.setMatchLength(InputSequence.LONGEST_MATCH);
		
		Matcher matcher = pattern.CreateMatcher(inputs);
		
		pattern.PrintPattern();
		boolean matched = false;
		while(matcher.Matches()){
			System.out.println("\n\n");
//			pattern.PrintPatternReverse();
			System.out.print("Matches\t");
			Vector<String> vs = matcher.GetMatchs();
			Collections.sort(vs);
			String str = "";
			Iterator<String> iter = vs.iterator();
			while(iter.hasNext()){
				str += iter.next() + "\t"; 
			}
			System.out.println(str);
			matched = true;
		}
		if(!matched){
			System.out.println("No matches!");
		}
	}

	/**
	 * Test method for {@link cn.edu.thss.retools.Pattern#Matches()}.
	 */
	@Test
	public void testMatches() {
		fail("Not yet implemented");
	}

}
