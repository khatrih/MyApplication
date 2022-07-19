package com.example.myapplication;

import java.util.Scanner;

public class NumberPractice {
    public static void main(String[] args) {
        System.out.println("Hello");
        Scanner givenNumber = new Scanner(System.in);
        int number = givenNumber.nextInt();

        int counter = 1;
        for (int j = 1; j <= number; j++) {
            if (counter >= 4) {
                counter = 1;
            }
            System.out.print(counter + " ");
            counter++;
        }

        /*int count = 0;
        int num = 0;
        do {

            count++;
            num++;
            if (count == 4 ) {
                count=1;
            }
            System.out.print(count + " ");
        } while (num < number);*/

    }
}
