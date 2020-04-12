package com.lab;

import java.util.ArrayList;
import java.util.Iterator;

public class Kosaraju {

    private ArrayList<Node> stack;

    public ArrayList<ArrayList<Node>> getSCC(Node root, AdjacencyList list){
        stack = new ArrayList<Node>();

        search(root, list, true);

        list.reverseGraph();

        ArrayList<ArrayList<Node>> SCC = new ArrayList<ArrayList<Node>>();
        while(!stack.isEmpty()){
            ArrayList<Node> component = new ArrayList<Node>();
            search(stack.get(0), list, false);

            for(Iterator<Node> it = stack.iterator(); it.hasNext(); ){
                Node n = it.next();
                if(!n.visited){
                    component.add(n);
                    it.remove();
                }
            }

            SCC.add(component);
        }
        return SCC;
    }

    private void search(Node root, AdjacencyList list, boolean forward){
        root.visited = forward;
        if(list.getAdjacent(root) == null){
            if(forward) stack.add(0, root);
            return;
        }
        for(Edge e : list.getAdjacent(root)){
            if(e.to.visited != forward){
                search(e.to, list, forward);
            }
        }
        if(forward) stack.add(0, root);
    }

}