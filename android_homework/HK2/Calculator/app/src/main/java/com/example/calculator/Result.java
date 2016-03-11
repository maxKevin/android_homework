package com.example.calculator;

/**
 * Created by king on 2015/12/12.
 */
public class Result {

    public double mul(String number){
        String[] numbers = number.split("[*]");
        double sum=1;
        for(String num:numbers)
            sum*=Double.valueOf(num);
        return sum;
    }

    public double div(String number){
        String[] numbers = number.split("/");
        double sum=mul(numbers[0]);
        for(int i = 1 ; i <= numbers.length-1 ; i++){
            sum /= mul(numbers[i]);
        }
        return sum;
    }

    public double sub(String number){
        String[] numbers = number.split("-");
        double sum=div(numbers[0]);
        for(int i = 1 ; i <= numbers.length-1 ; i++){
            sum -= div(numbers[i]);
        }
        return sum;
    }

    public String result(String number){
        String[] numbers = number.split("[+]");
        double sum = 0;
        for(String num:numbers)
            sum += sub(num);
        return String.valueOf(sum);
    }



}
