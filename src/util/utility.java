package util;

import java.util.Scanner;

public class utility {
    private static Scanner scanner = new Scanner(System.in);

    public static String getStringInput(String text){
        System.out.println(text);
        return scanner.next();
    }
    public static int getIntInput(String text){
        System.out.println(text);
        return scanner.nextInt();
    }

}
