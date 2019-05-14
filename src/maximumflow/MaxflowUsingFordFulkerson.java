package maximumflow;

import java.util.LinkedList;
import java.util.Scanner;

public class MaxflowUsingFordFulkerson {


    public int allNodes;    //Defining a variable to store the value of all vertices including source and sink nodes

    public MaxflowUsingFordFulkerson(int allNodes) {
        this.allNodes = allNodes;
    }

    public int getAllNodes() {
        return allNodes;
    }


    /* **************************************************************************** */


    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        boolean valid = false;


        while (!valid) {
            System.out.println("Welcome to the Program !!!");
            System.out.println("Inorder to Represent the graph as a matrix...");

            System.out.println("Please Enter the Number of nodes EXCLUDING Source node (S) and Sink node (t) : ");
            int nodes = input.nextInt();

            if (nodes >= 4 && nodes <= 10) {
                valid = true;
                int allNodes = nodes + 2; //2 is added because the graph by default has the source and sink node
                System.out.println("Total number of nodes : " + allNodes);
                int capacityArray[][] = new int[allNodes][allNodes]; //This 2D array allows user to add the weights of the graph

                /*
                Note :- In this Capacity Array in which user can add the weights and represent the graph as an adjacency matrix.
                In Edges where there are no weight user can give as "0".


                 */

                for (int x = 0; x < allNodes; x++) {


                    for (int y = 0; y < allNodes; y++) {
                        System.out.println("Enter the Capacities :");


                        capacityArray[x][y] = input.nextInt();


                    }
                }

                System.out.println("********************************************");

                System.out.println("         ADJACENCY MATRIX FOR GRAPH          ");

                for (int x = 0; x < allNodes; x++) {

                    for (int y = 0; y < allNodes; y++) {


                        System.out.print(capacityArray[x][y] + " ");
                    }
                    System.out.println(" ");
                }

                System.out.println("********************************************");


                MaxflowUsingFordFulkerson m = new MaxflowUsingFordFulkerson(allNodes);
                System.out.println("The maximum possible flow of this graph " +
                        m.maximumFlowFordFulkeron(capacityArray, 0, 5));


            } else {
                System.out.println("Please enter a value between 4 and 10");
            }


        }


    }
/*

* This Code is used to Keep track of the visited array , parent array and to Queue and DeQueue the Nodes

  References to the code,

* http://www.stanford.edu/class/cs97si/08-network-flow-problems.pdf
* Introduction to Algorithms 3rd Edition by Clifford Stein, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest
* https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/

*/

    boolean bfs(int rGraph[][], int s, int t, int parentArray[]) {

        boolean hasVisited[] = new boolean[allNodes];
        for (int i = 0; i < allNodes; ++i)
            hasVisited[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        hasVisited[s] = true;
        parentArray[s] = -1;


        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < allNodes; v++) {
                if (hasVisited[v] == false && rGraph[u][v] > 0) {
                    queue.add(v);
                    parentArray[v] = u;
                    hasVisited[v] = true;
                }
            }
        }

        return (hasVisited[t] == true);
    }

    /*

     * This Code is used to Find the maximum possible flow from the source node to the sink node.
       References to the code,

     * http://www.stanford.edu/class/cs97si/08-network-flow-problems.pdf
     * Introduction to Algorithms 3rd Edition by Clifford Stein, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest
     * https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/

     */



    int maximumFlowFordFulkeron(int capacityArray[][], int s, int t) {
        int u, v;

        int residualGraph[][] = new int[allNodes][allNodes];

        for (u = 0; u < allNodes; u++)
            for (v = 0; v < allNodes; v++)
                residualGraph[u][v] = capacityArray[u][v];


        int parent[] = new int[allNodes];

        int max_flow = 0;


        while (bfs(residualGraph, s, t, parent)) {

            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }


            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;
            }


            max_flow += path_flow;
        }


        return max_flow;
    }
}





