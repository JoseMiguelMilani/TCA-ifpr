Creating a graph from a image and using Djikstra to find the best path
 <br>
---------------------------------------------------------------------------------------- <br>
 <br>
🚀 Technologies <br>
*Java -programming language <br>
 <br>
*Java Swing - GUI framework for visualization <br>
 <br>
*AWT (Abstract Window Toolkit) - graphics engine <br>
 <br>
*ImageIO - Reading and writing image files <br>
 <br>
*Custom Graph Algorithms - Pathfinding and segmentation <br>
 <br>
---------------------------------------------------------------------------------------- <br>
 <br>
✨ Features <br>
🖼️ Image Processing: Converts images to graph representations <br>
 <br>
🎯 Automatic Marker Detection: Identifies the start(green) and the end(red) <br>
 <br>
🔗 Region Segmentation: Uses Union-Find algorithm to group connected components on the graph <br>
 <br>
🗺️ Pathfinding: Implements Dijkstra's algorithm for shortest path <br>
 <br>
🎨 Visualization: Color-coded region display with random coloring <br>
 <br>
📊 Interactive GUI: Real-time animation and information display <br>
 <br>
⏱️ Performance Metrics: Execution time tracking <br>
 <br>
---------------------------------------------------------------------------------------- <br>
 <br>
🔄 The Process <br>
1. Image Loading & Preprocessing <br>
 <br>
2. Marker Detection <br>
-Green pixels → Start point <br>
-Red pixels → End point <br>
 <br>
3. Region Segmentation <br>
Union-Find algorithm connects adjacent pixels <br>
 <br>
-connect regions = pixels with the same RGB sum <br>
 <br>
Generates segmented matrix <br>
 <br>
4. Graph Construction <br>
Regions become graph vertices <br>
 <br>
Adjacent regions create edges <br>
 <br>
Distance between region centers as edge weights <br>
 <br>
5. Pathfinding <br>
Dijkstra's algorithm finds shortest path <br>
 <br>
Animated visualization of the path discovery <br>
 <br>
---------------------------------------------------------------------------------------- <br>
 <br>
🛠️ How I Built It <br>
 <br>
*Main.java <br>
-Application controller <br>
-workflow manager <br>
 <br>
*LendoUmaImagem.java <br>
-Image reader (with Image.IO) <br>
-RGB matrix converter (hexadecimal -> binary) <br>
 <br>
*ProcessandoImagem.java <br>
-matrix preprocessing <br>
-binarization through RGB sum (three-dimensional matrix -> two-dimensional matrix) <br>
 <br>
*AlgoritmoUnionFind.java <br>
-Region segmentation (every connected segment with the same RGB sum have it own value) <br>
-Union-Find <br>
 <br>
*Grafo.java <br>
-Graph construction (every adjascent point with different value became an edge) <br>
-Dijkstra's algorithm (use the distance between the central point of each vertice) <br>
 <br>
*telaNova.java <br>
-Swing-based GUI <br>
-animation (using the Dijkstra's result) <br>
 <br>
*VariaveisGlobais.java  <br>
-Global state management <br>
 <br>
---------------------------------------------------------------------------------------- <br>
 <br>
📚 What I Learned <br>
-Technical Skills: <br>
*Graph Theory <br>
Practical implementation of Union-Find and Dijkstra <br>
 <br>
*Image Processing <br>
RGB manipulation, pixel analysis, matrix operations <br>
 <br>
*Java Swing <br>
Building interactive desktop applications <br>
 <br>
*Software Architecture <br>
Modular design and separation of concerns <br>
 <br>
-Problem-Solving Insights: <br>
*Handling large image matrix efficiently <br>
 <br>
*Debugging complex graph algorithms <br>
 <br>
*Creating intuitive visualizations for algorithmic processes <br>
 <br>
*Managing state across multiple processing stages <br>
 <br>
---------------------------------------------------------------------------------------- <br>
 <br>
🔧 How It Can Be Improved <br>
 <br>
*Error Handling <br>
create more areas to handle error (with Try and Catch) <br>
 <br>
*Performance Optimizations <br>
Optimize memory usage in matrix operations <br>
Optimize the code flow <br>
 <br>
*Support for multiple image formats (JPG, BMP, etc.) <br>
 <br>
*Support images with a gradient  <br>
 <br>
*let the user define the weight of each vertice <br>
 <br>
*Exportable reports and statistics <br>
 <br>
*UI/UX Improvements <br>
create a interactive panel to choose the image <br>
Progress indicators for long operations <br>
 <br>
---------------------------------------------------------------------------------------- <br>
 <br>
🏃‍♂️ Running the Project <br>
Prerequisites: <br>
Java 17 or higher <br>
 <br>
Maven (for dependency management) <br>
 <br>
Installation & Setup: <br>
-Clone the repository <br>
-Compile the project <br>
-Prepare image directory <br>
-Run the application <br>
 <br>
Usage Instructions: <br>
1.Launch the application <br>
2.Select an image from the numbered list on the terminal <br>
 <br>
3.Use the GUI buttons to: <br>
View the processed graph <br>
See element information <br>
Watch pathfinding animation <br>
 <br>
How to create a functional images: <br>
-Include only one green and red point <br>
-Use PNG format <br>
-Ensure good contrast between regions and same RGB value <br>
-Recommended size: 500x500 pixels <br>
 <br>


https://github.com/user-attachments/assets/fcab99e3-252b-45ec-b4a5-738b42446dea

---------------------------------------------------------------------------------------- <br>
 <br>
José Miguel Milani <br>
Status: finished<br>
 <br>
For questions or contributions, please open an issue or submit a pull request! <br>
