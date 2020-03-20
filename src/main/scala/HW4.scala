object HW4 {

  var graph = new Graph();

  def main(args: Array[String]): Unit = {
    graph.addNode("X")
    graph.addNode("J")
    graph.addNode("B")
    graph.addNode("F")
    graph.addNode("R")
    graph.addNode("C")
    graph.addNode("E")
    graph.addNode("Y")

    graph.addEdge("X", "J")
    graph.addEdge("J", "B")
    graph.addEdge("J", "F")
    graph.addEdge("J", "R")
    graph.addEdge("B", "C")
    graph.addEdge("B", "F")
    graph.addEdge("B", "R")
    graph.addEdge("R", "Y")
    graph.addEdge("R", "E")
    graph.addEdge("E", "F")
    graph.addEdge("E", "Y")

    print(graph.shortestPath("X", "C"));
  }

}
