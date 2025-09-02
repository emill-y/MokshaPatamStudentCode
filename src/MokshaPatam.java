import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Moksha Patam
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Eisha Yadav
 *
 */

public class MokshaPatam {

    public static int fewestMoves(int boardsize, int[][] ladders, int[][] snakes) {

        // Begin by specifically identifying spots in the game board as normal spaces, ladders, or snakes
        // Normal spaces can be denoted as -1's (as typical when referring to a non-numerical space, if positive, c
        // could get mixed up with actual values for the snakes/ladders
        // I believe this is called "mapping"

        int[] allMoves = new int[boardsize];
        // Fill array with -1 for "normal space"
        Arrays.fill(allMoves, -1);
        // Add in Ladders (start becomes end)
        for (int[] ladder : ladders) {allMoves[ladder[0]] = ladder[1];}
        // Add in snakes
        for (int[] snake : snakes) {allMoves[snake[0]] = snake[1];}

        // Now the board aldready has the changes of the snakes and ladders factored in, so we don't need to worry about them anymore (no if else!)
        // Breadth First Search Tree! (Aka Binary Search Tree, aka BST)
        // Keep track of if square is visited (don't repeat-> otherwise code will never finish running)
        boolean[] visited = new boolean[boardsize];
        // Create a queue to add new moves onto (i.e move from square 1 -> square 2)
        Queue<int[]> movesQueue = new LinkedList<>();
        // Start at the corner square of the game board ( {0.0} just like in the game)
        // Kind of like the starting node at the base of the tree
        movesQueue.add(new int[]{0, 0});
        // Set this square to visited (bcuz you are starting on it)
        visited[0] = true;

        // Go through the board and track all possible paths (with dice rolls 1-6) to find the shortest path
        while (!movesQueue.isEmpty()) {
            int[] currentLoc = movesQueue.poll();
            int pos = currentLoc[0];
            int numMoves = currentLoc[1];

            // Return the number of moves if the game is over
            // The game is over when the last square is reached
            // Once game is over it will return the minimum number of moves
            if (pos == boardsize - 1) {return numMoves;}

            // Do all possible moves from the current square
            // So move when dice roll = 1, till dice roll = 6
            for (int i = 1; i <= 6; i++) {
                int next = pos + i;
                if (next >= boardsize){continue;}

                // Move the next position further if a snake or ladder (not regular)
                // Because we mapped prior, it aldready stored and knows the new location 
                if (allMoves[next] != -1) {next = allMoves[next];}
                
                // If the square has not yet been visited, add one to the number of moves
                if (!visited[next]) {
                    visited[next] = true;
                    movesQueue.add(new int[]{next, numMoves + 1});
                }
            }
        }

        // Return -1 if the board cannot be solved (no solution is reached in any of the paths) 
        return -1;
    }
}
