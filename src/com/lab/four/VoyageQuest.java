package com.lab.four;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class VoyageQuest {
    final static int CVD = 10;
    final static int CVV = 5;

    public static void main(String[] args) {

        boolean[] city = new boolean[CVV];
        GraphStream[] startGraph = new GraphStream[CVD];

        try (FileReader reader = new FileReader("C:\\Users\\Nazar.Kravchuk\\Desktop\\Nazar\\src\\com\\lab\\four\\lab4")) {
            int i = 0;
            int numb = 0;
            int c;
            int numbOut = 0, numbIn = 0;
            while ((c = reader.read()) != -1) {

                if ((char) c == '\n') {
                    startGraph[i] = new GraphStream(numbOut, numbIn, numb);
                    numb = 0;
                    i++;
                } else if (c >= 65) {
                    numbOut = Transformer.upperLetterToNum((char) c);
                    while ((c = reader.read()) < 65) ;
                    numbIn = Transformer.upperLetterToNum((char) c);
                } else if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9) {
                    numb *= 10;
                    numb += Character.getNumericValue(c);
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

        city[Transformer.upperLetterToNum(firstDot.charAt(0))] = true;
        CVWalk(city, startGraph, Transformer.upperLetterToNum(firstDot.charAt(0)), Transformer.upperLetterToNum(firstDot.charAt(0)));
    }


    public static void CVWalk(boolean[] city, GraphStream[] startGraph, int gps, int impulse) {
        while (!isAllVisited(city)) {
            int min = 6428;
            int nD = 0;
            for (int i = 0; i < CVD; i++)
                if ((startGraph[i].a == gps || startGraph[i].b == gps) && (!city[startGraph[i].b] || !city[startGraph[i].a]))
                    if (startGraph[i].c < min) {
                        min = startGraph[i].c;
                        if (startGraph[i].a == gps)
                            nD = startGraph[i].b;
                        else
                            nD = startGraph[i].a;
                    }

            city[nD] = true;
            CVWalk(city, startGraph, nD, impulse);
            if (isAllVisited(city)) {
                System.out.println(Transformer.numToUpperLetter(nD) + "-" + Transformer.numToUpperLetter(gps));
                return;
            } else {
                city[nD] = false;
                startGraph[nD].c = Integer.MAX_VALUE;
                return;
            }
        }
        System.out.println(Transformer.numToUpperLetter(impulse) + "-" + Transformer.numToUpperLetter(gps));
    }


    private static class GraphStream {
        int a;
        int b;
        int c;

        GraphStream(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static boolean isAllVisited(boolean[] r) {
        for (boolean b : r)
            if (!b)
                return false;
        return true;
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
