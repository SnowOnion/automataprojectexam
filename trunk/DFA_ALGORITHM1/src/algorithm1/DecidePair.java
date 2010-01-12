/**
 * @Title: DecidePair.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-27 ионГ11:12:00
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;

public class DecidePair {
public  ArrayList<StatePair> DecPair;
public DecidePair()
{
	DecPair=new ArrayList<StatePair>();
}
public StatePair GetPair(int index)
{
	return DecPair.get(index);
}
public int GetSize()
{
	return DecPair.size();
}
public void Add(StatePair pair)
{
     DecPair.add(pair);	
}
}
