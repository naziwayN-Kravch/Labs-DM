package com.lab.two;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MailDelivery {

    final static int M = 10; //number of edges


    public static void main(String[] args) {

        ArrayList<Eji> gasse = new ArrayList<>();

        try (FileReader reader = new FileReader("C:\\Users\\Nazar.Kravchuk\\Desktop\\Nazar\\src\\com\\lab\\two\\lab2")) {
            int c;
            int numbOut = 0, numbIn = 0;
            while ((c = reader.read()) != -1) {

                if ((char) c == '\n') {
                    gasse.add(new Eji(numbOut, numbIn));
                } else if (c >= 65) {
                    numbOut = Transformer.upperLetterToNum((char) c);
                    while ((c = reader.read()) < 65) ;
                    numbIn = Transformer.upperLetterToNum((char) c);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner in = new Scanner(System.in);
        System.out.print("Input place to start: ");
        String firstDot = in.next();

        if (!Walk(gasse, Transformer.upperLetterToNum(firstDot.charAt(0))))
            System.out.println("no way");
    }

    public static boolean fullWay(ArrayList<Eji> gasse) {
        for (Eji e : gasse) {
            if (!e.visited) {
                return false;
            }
        }
        return true;
    }

    public static boolean Walk(ArrayList<Eji> graph, int gps) {
        if (fullWay(graph))
            return true;
        else {
            for (Eji street : graph)
                if (!street.visited && (street.v == gps || street.u == gps)) {
                    street.visited = true;
                    if (street.v == gps) {
                        Walk(graph, street.u);
                        if (fullWay(graph)) {
                            System.out.println(Transformer.numToUpperLetter(street.u) + "-" + Transformer.numToUpperLetter(street.v));
                            return true;
                        } else
                            street.visited = false;
                    } else {
                        Walk(graph, street.v);
                        if (fullWay(graph)) {
                            System.out.println(Transformer.numToUpperLetter(street.v) + "-" + Transformer.numToUpperLetter(street.u));
                            return true;
                        } else
                            street.visited = false;
                    }
                }
            return false;
        }
    }

    private static class Eji {
        int v;
        int u;
        boolean visited = false;

        public Eji(int v, int u) {
            this.v = v;
            this.u = u;
        }
    }

    private static class Transformer {
        public static char numToUpperLetter(int num) {
            return (char) (num + 65);
        }

        public static int upperLetterToNum(char s) {
            return (int) s - 65;
        }
    }
}
