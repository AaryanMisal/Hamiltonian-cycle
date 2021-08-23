import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

import jdk.nashorn.api.tree.ArrayAccessTree;

import java.lang.Integer;
import java.util.*;
 
// A class to store a graph edge
class Edge
{
    int source, dest;
 
    public Edge(int source, int dest)
    {
        this.source = source;
        this.dest = dest;
    }
}
 
// A class to represent a graph object
class Graph
{
    // A list of lists to represent an adjacency list
    HashMap<Integer, List<Integer>> list = new HashMap<>();
    List<List<Integer>> adjList = null;
    List<Integer> permut = null;
 
    // Constructor
    Graph(List<Edge> edges, List<List<Integer>> AList, int N,  List<Integer> permutation)
    {   
        
        permut = permutation;
        adjList = AList;
        for(int i =0; i< permut.size(); i++){
          list.put(permut.get(i), adjList.get(i));
        }
       System.out.println(list);
    }
}
 
 class Main
{
  
    public static void printAllHamiltonianPaths(Graph g,
                                                int v,
                                                HashMap<Integer, Boolean> visited,
                                                List<Integer> path,
                                                int N) {
        // if all the vertices are visited, then the Hamiltonian path exists
      /*  System.out.println(path);
        System.out.println(visited);
        System.out.println(v);*/
        if (path.size() == N )
        {
              if(g.list.get(v).contains(g.permut.get(0))){
                path.add(g.permut.get(0));
              System.out.println(path);

              }
            // print the Hamiltonian path
            return;
        }
 
        // Check if every edge starting from vertex `v` leads
        // to a solution or not
        for (int w: g.list.get(v))
        {
         // System.out.println(w);  
          // process only unvisited vertices as the Hamiltonian
            // path visit each vertex exactly once
            if (visited.get(w) == false)
            {
                visited.put(w, true);
                path.add(w);
 
                // check if adding vertex `w` to the path leads
                // to the solution or not
                v=w;
                printAllHamiltonianPaths(g, v, visited, path, N);
                // backtrack
                visited.put(w, false);
                path.remove(path.size() - 1);
            }
        }
    }

    //works
public static boolean differences(int[]arr1, int[]arr2){
  ArrayList<Integer> diff = new ArrayList<Integer> ();
  for(int i = 0; i<arr1.length; ++i){
    if(arr1[i]!=arr2[i]){
    	diff.add(i); 
    }
  }
  return diff.size() == 2 && diff.get(1) == 1 + diff.get(0); 
}


//MAIN FUNCTION WHICH AIMS TO GENERATE LIST OF EDGES 
    public static List<List<Integer>> generateAdjList(int n){
      ArrayList<Integer> permutations = getPermutation(n);
     List<List<Integer>> adjList = new ArrayList<List<Integer>>();
     for(int i = 0; i < permutations.size(); ++i ){
        List<Integer> neighbors = new ArrayList<Integer>(); 
       for(int j = 0; j<permutations.size();++j){
         if((differences(intSplit(permutations.get(i)) , intSplit(permutations.get(j)) ))){
           neighbors.add(permutations.get(j));
         }
       }
       adjList.add(neighbors); 
     }
   // System.out.println(adjList); 
    return adjList;
    }
    //make edges list by flattening adjacency list
    //canonically order adjacency list
    public static ArrayList<Integer> getPermutation(int n){
      int [] arr = new int[n];
      
      for(int i = 0; i < arr.length; ++i){
          arr[i] = i+1; 
        }
      
      ArrayList<Integer> permutations = new ArrayList<Integer>(); 
      helperGeneratePermutation(arr, 0, permutations);
         Collections.sort(permutations);
         return permutations;
      }
    //System.out.println(permutations); 
    public static List<Edge> generateEdges(int n){
      ArrayList<Integer> permutations2 = getPermutation(n);  
      List<List<Integer>> AdList = generateAdjList(n);
      List<Edge> edges = new ArrayList<Edge>();

    for(int i = 0; i < AdList.size(); ++i){
      for(int j = 0; j < AdList.get(i).size(); ++j){
        edges.add(new Edge(permutations2.get(i),AdList.get(i).get(j)));
      }
    }
//wrap in private recursive method
    //System.out.println(edges);
      return edges; 

    }
  //works 
    public static int[] intSplit(int a){
      ArrayList<Integer> digits = new ArrayList<Integer>();
      while(a!=0){
        digits.add(a%10);
        a = a/10; 
      }
      //return digits.<Integer>toArray(new int[digits.size()]);
      int returns[] = new int[digits.size()];
      for(int i=(digits.size() - 1); i>=0; i--){
        returns[i] = digits.get(i).intValue();
      }
      digits = null;
      return returns;
    }

  //works
    public static int toInteger(int[]n){
      int powerOf10 = 0;
      int res = 0;
      for(int i = n.length-1; i>=0; i--){
        res += n[i]*Math.pow(10, powerOf10); 
        powerOf10++;
      }
      return res; 
    }

    public static void swap(int[]arr, int i, int idx){
      int temp = arr[i];
      arr[i] = arr[idx];
      arr[idx] = temp;
    }


//GENERATES PERMUTATIONS
    public static void helperGeneratePermutation(int []n, int cidx, ArrayList<Integer>permutations){
      
      if(cidx == n.length-1){
        permutations.add(toInteger(n)); 
        return; 
      }
      
       for(int i = cidx; i < n.length; ++i){
        swap(n,i,cidx);
        helperGeneratePermutation(n,cidx+1, permutations); 
        swap(n,i,cidx);
      } 
    }
 
    public static void main(String[] args)
    {

        ArrayList<Integer> permutation = getPermutation(3);
        List<Edge> edges2 = generateEdges(3); 
        List<List<Integer>> adjList = generateAdjList(3);
       /* System.out.println(permutation);
        System.out.println(adjList);
        System.out.println(edges2);*/
 
        // total number of nodes in the graph
        final int N = 6;
 
        // build a graph from the given edges
        Graph g = new Graph(edges2, adjList, N, permutation);
       // System.out.println(g.permut);
       // System.out.println(g.adjList);
        // starting node
        int start = 123;
 
        // add starting node to the path
        List<Integer> path = new ArrayList<>();
        path.add(start);
 
        // mark the start node as visited
        boolean[] visited = new boolean[N];
        HashMap<Integer, Boolean> visited2 =  new HashMap<>();
        for(int i =0; i < permutation.size();i++){
          visited2.put(permutation.get(i), false); 
        }
       // System.out.println(visited2);
        visited2.put(start, true);
        //System.out.println(visited2);
 
        printAllHamiltonianPaths(g, start, visited2, path, N);
    }
}





