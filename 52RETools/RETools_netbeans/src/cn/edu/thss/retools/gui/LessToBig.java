/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.thss.retools.gui;

import java.util.Comparator;

/**
 *
 * @author Kylin
 */
class LessToBig implements Comparator<String>{

    public int compare(String o1, String o2) {
       return o1.length() - o2.length();
    }


}

class BigToLess implements Comparator<String>{

    public int compare(String o1, String o2) {
       return o2.length() - o1.length();
    }


}
