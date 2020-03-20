import scala.collection.mutable.ListBuffer

class Graph() {
  var db = new Redis();

  def addNode(v: String): Unit = {
    db.lpush(v, "");
  };

  def addEdge(u: String, v: String): Unit = {
    db.lpush(u, v)
    db.lpush(v, u)
  };

  def adjacent(v: String): ListBuffer[String] = {
    val size = db.llen(v).asInstanceOf[Int];
    return db.lrange(v, 0, size-1);
  };

  def findShortest(u: String, v: String, dest: String): String = {
    try {
      if (shortestPath(u, dest).size < shortestPath(v, dest).size) {
        return u
      } else {
        return v;
      }
    } catch {
      case e: Exception => e.getMessage
    }

  }

  def shortestPath(u: String, v: String): ListBuffer[String] = {
    val path = ListBuffer[String](u);
    val edges = adjacent(u);
    if(edges.contains(v)) {
      return path += v;
    }
    var shortestEdge = "";
    if (edges.length >= 2) {
      shortestEdge = edges.reduce((x, y) => findShortest(x, y, v))
    } else {
      shortestEdge = edges(0);
    }
    return path ++ shortestPath(shortestEdge, v)
  }
}
