/**
 * @Title: ArrayOp.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 ����01:07:10
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;

public class ArrayOp {
	/**
	 * �½�һ���ַ�������ĸ���
	 * @param strings ԭ�ַ�������
	 * @return
	 */
     public static ArrayList<String> StringArrayClone(ArrayList<String> strings)
     {
    	 ArrayList<String> result=new ArrayList<String>();
    	 int size=strings.size();
    	 for(int i=0;i<size;i++)
    	 {
    		 String temp=new String(strings.get(i));
    		 result.add(temp);
    	 }
    	 return result;
     }
     /**
      * ���ַ��������в���ĳ���ַ���
      * @param content
      * @param p
      * @return
      */
     public static int FindString(ArrayList<String> content,String p)
     {
    	 int size=content.size();
    	 for(int i=0;i<size;i++)
    	 {
    		 if(content.get(i).equals(p))
    			 return i;
    	 }
    	 return -1;
     }
     /**
      * ���ĳ���ַ���������Ѿ������򷵻��±꣬�����������
      * @param strs
      * @param str
      * @return
      */
     public static int AddString(ArrayList<String> strs,String str)
     {
    	 int index=ArrayOp.FindString(strs, str);
    	 if(index!=-1)
    		 return index;
    	 index=strs.size();
    	 strs.add(str);
    	 return index;
     }
     
}

