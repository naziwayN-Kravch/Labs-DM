package com.lab.five;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Isomorphism {

    final static int ZN = 10;
    final static int ZE = 15;
    static boolean general = false;

    public static void main(String[] args) {
        MatGraph oneInc = new MatGraph(ZN);
        MatGraph twoInc = new MatGraph(ZN);
        Eji[] oneGraph = new Eji[ZE];
        Eji[] twoGraph = new Eji[ZE];
        Eji.readGraphFromFile(oneGraph, "C:\\Users\\Nazar.Kravchuk\\Desktop\\Nazar\\src\\com\\lab\\five\\iso1");
        Eji.readGraphFromFile(twoGraph, "C:\\Users\\Nazar.Kravchuk\\Desktop\\Nazar\\src\\com\\lab\\five\\iso2");
        for (int i = 0; i < ZE; i++) {
            oneInc.mat[oneGraph[i].A][oneGraph[i].B] = true;
            oneInc.mat[oneGraph[i].B][oneGraph[i].A] = true;
            twoInc.mat[twoGraph[i].A][twoGraph[i].B] = true;
            twoInc.mat[twoGraph[i].B][twoGraph[i].A] = true;
        }
        oneInc.print();
        twoInc.print();
        oneInc.antiphlex(twoInc, ZN - 1);

        if (general)
            System.out.println("its work");
        else System.out.println("its bad");


    }


    private static class MatGraph {
        public boolean[][] mat;
        int size;

        private MatGraph(int size) {
            this.size = size;
            mat = new boolean[size][size];
        }

        public boolean sameAs(MatGraph da) {
            if (da.size != this.size) return false;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (da.mat[i][j] != this.mat[i][j]) return false;
                }
            }
            return true;
        }

        static void reverse(MatGraph P, int m) {
            int i = 0, j = m;
            while (i < j) {
                P.swapLeafs(i, j);
                ++i;
                --j;
            }
        }

        void antiphlex(MatGraph P, int m) {
            int i;
            if (m == 0) {
                if (this.sameAs(P)) {
                    general = true;
                }
            } else {
                for (i = 0; i <= m; ++i) {
                    this.antiphlex(P, m - 1);
                    if (i < m) {
                        P.swapLeafs(i, m);
                        reverse(P, m - 1);
                    }
                }
            }
        }

        public void print() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(mat[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("---------------------------------");
        }

        public void swapLeafs(int x, int y) {
            for (int i = 0; i < size; i++) {
                boolean k = mat[i][x];
                mat[i][x] = mat[i][y];
                mat[i][y] = k;
            }
            for (int j = 0; j < size; j++) {
                boolean k = mat[x][j];
                mat[x][j] = mat[y][j];
                mat[y][j] = k;
            }
        }

    }

    private static class Eji {
        public int A;
        public int B;

        private Eji(int a, int b) {
            A = a;
            B = b;
        }

        public static void readGraphFromFile(Eji[] graph, String filename) {
            try (FileReader reader = new FileReader(filename)) {
                int c;
                int numbOut = 0, numbIn = 0, i = 0;
                while ((c = reader.read()) != -1) {
                    if ((char) c == '\n') {
                        graph[i] = new Eji(numbOut, numbIn);
                        i++;
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
        }
    }

    private static class Transformer {
        public static int upperLetterToNum(char s) {
            return (int) s - 65;
        }
    }
}
