# What is Seam Carving?
Seam carving is a technique for image resizing, where the size of an image is reduced by one pixel in height (by removing a horizontal seam) or width (by removing a vertical seam) at a time. Unlike cropping pixels from the edges or scaling the entire image, seam carving is content-aware, because it attempts to identify and preserve the most important content in an image by inferring the “importance” of each pixel from the surrounding pixels.

# How does Seam Carving work?
Seam carving works by using an energy function to find the lowest energy-connected pixels either horizontally or vertically across an image and then removing it.

Our implementation uses a DualGradientEnergyFunction helper class (already implemented for you). You will use this as the cost function when you model the image as a graph!

# Project Starter Code
Inside src/seamcarving, you’ll find the SeamCarver class that represents the overall seam carving application. It has methods for removing the least-noticeable horizontal or vertical seams from a picture. The removeHorizontal() method works by:
1. Calling SeamFinder.findHorizontal() to find the least-noticeable horizontal seam.
2. Removing this horizontal seam by creating a new picture with all the pixels except for the ones specified in the horizontal seam.
3. Returning the horizontal seam (primarily to verify that the correct seam was removed).

The removeVertical() method does the same thing except in the vertical orientation.

# DijkstraShortestPathFinder
Implement a shortest path finder using Dijkstra’s Algorithm.

In order to make DijkstraShortestPathFinder work with the rest of the program, we have introduced some slight modifications to how it works internally. Notably, we will implement a more efficient version of Dijkstra’s algorithm, where you might not necessarily know the number of vertices in the graph beforehand to be able to initialize every vertex to infinity and will need to insert the vertices into the distTo map as you encounter them. However, the main logic of Dijkstra’s still follows.

# It’s A Graph Problem!
A seam is a path of adjacent or diagonally-adjacent pixels from one side of the image to the other, with exactly 1 pixel from each column/row, for horizontal/vertical seams respectively (opposite sides of an image are not adjacent, so seams cannot wrap around the image).

This subproblem looks much like a regular shortest path problem, since you would still be looking for a “path” of pixels to draw a line through. However, there are three important items to take into account:
- Each edge weight is based on a vertex (pixel) rather than the edge itself. So, you’ll have to calculate a weight based on pixels, but also decide where to put the weight on the edges.
- The goal is to find the shortest path from any of the �H pixels in the left column to any of the �H pixels in the right column or any of the �W pixels in the top row to any of the �W pixels in the bottom row.
- The graph has a regular structure assuming that the coordinates are in the prescribed ranges (which means that the graph is a directed acyclic graph (DAG)):
    - There is a rightward edge (x,y) to pixels (x+1,y−1), (x+1,y), and (x+1,y+1).
    - There is a downward edge from pixel (x,y) to pixels (x−1,y+1), (x,y+1), and (x+1,y+1).
