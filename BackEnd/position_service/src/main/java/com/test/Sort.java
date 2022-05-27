package com.test;

import java.lang.reflect.Array;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 11:06
 */
public class Sort {

    public static void main(String[] args) {
        Sort sort = new Sort();
//        int[] ints = sort.get();
        int[] ints = sort.xuanze();
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    //冒泡排序
    public int[] get(){
        int[] a = {8,7,5,34,988,9,1};

        for (int i = 0; i < a.length -1; i++) {
            int flag = 0;
            for (int j = 0; j < a.length - 1-i; j++) {
                int array = 0;
                if (a[j] > a[j+1]){
                    array = a[j+1];
                    a[j+1] = a[j];
                    a[j] = array;
                    flag = 1;
                }
            }
            //如果下一趟已经排好序的话，跳过
            if (flag == 0){
                break;
            }
        }
        return a;
    }

    /**
     * 将遍历的每一个字符与这个字符后面的进行比较，如果第一个比后面的大，拿到小的数组下标，就交换位置，之前的都是排好序的
     * @return
     */
    public int[] xuanze(){
        int[] a = {8,7,5,34,988,9,1};
        int temp =0;
        for (int i = 0; i < a.length-1; i++) {
            int min = i;
            for (int j = i+1; j <a.length ; j++) {
                if (a[min] > a[j]){
                    //如果第一个比这个大，就拿到小的数组下标
                    min = j;
                }
            }
            //交换
            if (i != min){
                temp = a[i];
                a[i] = a[min];
                a[min] = temp;
            }

        }
        return a;
    }



}
