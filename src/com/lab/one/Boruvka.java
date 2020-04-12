package com.lab.one;

import com.lab.five.Isomorphism;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class Boruvka {
    final static int N = 7;

    public static void main(String[] args) {

        Graph simpleGraph;

        for (int i = 0; i < N; i++) {
            Graph.globalLeafs.add(new Leaf(Transformer.numToUpperLetter(i)));
        }
        Graph.readGraphFromFile("C:\\Users\\Nazar.Kravchuk\\Desktop\\Nazar\\src\\com\\lab\\one\\lab1");
        simpleGraph = Graph.makingTree();
        for (Edge e : simpleGraph.T) {
            System.out.println(e.v.name + "-" + e.u.name + " " + e.weight);
        }
    }

    private static class Graph {
        static TreeSet<Edge> globalEdges = new TreeSet<>();
        static TreeSet<Leaf> globalLeafs = new TreeSet<>();

        TreeSet<Edge> T = new TreeSet<>();
        TreeSet<Leaf> L = new TreeSet<>();
        TreeSet<Edge> couples = new TreeSet<>();
        boolean removeMark = false;

        Graph() {
        }

        Graph(Leaf l) {
            addLeaf(l);
        }

        public void addEdge(Edge e) {
            T.add(e);
            addLeaf(e.u);
            addLeaf(e.v);
        }

        public static void addGlobalEdge(Edge e) {
            globalEdges.add(e);
            globalLeafs.add(e.u);
            globalLeafs.add(e.v);
        }

        public void addLeaf(Leaf l) {
            L.add(l);
            for (Edge e : globalEdges) {
                if ((l.equals(e.u) || l.equals(e.v)) && !T.contains(e)) {
                    couples.add(e);
                }
            }
        }

        public void addTree(Graph b) {
            T.addAll(b.T);
            L.addAll(b.L);
            couples.addAll(b.couples);
            for (Edge e : T) {
                couples.remove(e);
            }
        }

        public static Graph makingTree() {
            LinkedList<Graph> S = new LinkedList<>();
            for (Leaf l : globalLeafs) {
                S.add(new Graph(l));
            }
            while (S.size() > 1) {
                //System.out.println(S.size());
                for (Graph graph : S) {
                    if (graph.removeMark) continue;
                    for (Edge c : graph.couples) {
                        for (Graph b : S) {
                            if (!graph.equals(b) && !b.removeMark &&
                                    (b.L.contains(c.u) && graph.L.contains(c.v) ||
                                            graph.L.contains(c.u) && b.L.contains(c.v))) {
                                graph.addEdge(c);
                                graph.addTree(b);
                                b.removeMark = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                Iterator<Graph> iter = S.iterator();
                while (iter.hasNext()) {
                    Graph next = iter.next();
                    if (next.removeMark)
                        iter.remove();
                }
                //System.out.println(S.size());
            }
            return S.getFirst();
        }

        public static void readGraphFromFile(String filename) {

            try (FileReader reader = new FileReader(filename)) {
                int c;
                int num = 0;
                char ii = ' ', jj = ' ';
                while ((c = reader.read()) != -1) {

                    if ((char) c == '\n') {
                        Graph.addGlobalEdge(new Edge(num, Graph.globalLeafs.ceiling(new Leaf(ii)), Graph.globalLeafs.ceiling(new Leaf(jj))));
                        num = 0;
                        continue;
                    }
                    if (c >= 65) {
                        ii = (char) c;
                        while ((c = reader.read()) < 65) ;
                        jj = (char) c;
                    } else if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) <= 9) {
                        num *= 10;
                        num += Character.getNumericValue(c);
                    }
                }
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }

    private static class Edge implements Comparable<Edge> {
        int weight;
        Leaf v;
        Leaf u;

        Edge(int weight, Leaf v, Leaf u) {
            this.weight = weight;
            this.v = v;
            this.u = u;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    public static class Leaf implements Comparable<Leaf> {
        private char name;

        Leaf(char da) {
            name = da;
        }

        @Override
        public int compareTo(Leaf o) {
            return this.name - o.name;
        }
    }

    private static class Transformer {
        public static char numToUpperLetter(int num) {
            return (char) (num + 65);
        }
    }
}
