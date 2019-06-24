package com.cxystyle.demo.superitemdecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Datas {

  public static ArrayList<Integer> list;

  static{
    list = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      list.add((int) (Math.random() * 101));
    }

    //Collections.addAll(list, 0, 1, 11, 12, 22, 34);


    /**
     * 首字母排序
     */
    Collections.sort(list, new Comparator<Integer>() {
      @Override public int compare(Integer o1, Integer o2) {
        String s1 = String.valueOf(o1);
        String s2 = String.valueOf(o2);
        int first = s1.charAt(0) - s2.charAt(0);
        return first == 0 ? Integer.valueOf(s1) - Integer.valueOf(s2) : first;
      }
    });

    /**
     * 大小排序
     */
    //Collections.sort(list, new Comparator<Integer>() {
    //  @Override public int compare(Integer o1, Integer o2) {
    //    return o1 - o2;
    //  }
    //});

  }

}
