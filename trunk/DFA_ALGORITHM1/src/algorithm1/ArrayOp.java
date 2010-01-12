/**
 * @Title: ArrayOp.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 下午01:07:10
 * @Version: V1.0
 */
package algorithm1;
import java.util.ArrayList;

public class ArrayOp {
	/**
	 * 新建一个字符串数组的副本
	 * @param strings 原字符串数组
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
      * 在字符串数组中查找某个字符串
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
      * 添加某个字符串，如果已经存在则返回下标，不存在则添加
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

