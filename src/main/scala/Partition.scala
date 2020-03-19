object Partition {

  def moved(records: Int, startN: Int, endN: Int): Double = {
    var ans = 0.0;
    for(i <- 1 until records) {
      if(i % startN != i % endN) {
        ans += 1;
      }
    }
    return ans;
  }

  def main(args: Array[String]): Unit = {
    return print(moved(1000000, 100, 107));
  }
}
