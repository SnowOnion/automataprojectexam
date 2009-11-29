package test;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTestSuite extends TestCase {
	public static TestSuite suite(){
        TestSuite suite = new TestSuite();        
        suite.addTestSuite(StateTest.class);
        suite.addTestSuite(AutomatonNFATest.class);
        return suite;
    }

}
