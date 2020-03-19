import breeze.plot._

object Binary {

  def toBinary(x: Int, bits: Int): String = {
    if(bits == 0) {
      return "";
    }
    val binaryVal = (x % 2).toString;
    return toBinary(x / 2, bits - 1) + binaryVal;
  }

  def weight(b: String): Int = {
    var ans = 0;
    for (c <- b){
      if(c.toString == "1") {
        ans += 1;
      }
    }
    return ans;
  }

  def main(args: Array[String]): Unit = {
    val xs = (0 until 1024)
    val ys = (0 until 1024).map { i => weight(toBinary(i, 12)) }
    val f = Figure()
    var p = f.subplot(0);
    p += plot(xs, ys);
    p.title = "Weights for Binary #s 0-1024";
    p.xlabel = "Decimal Number";
    p.ylabel = "Binary Weight";
    f.saveas("Plot");
  }
}
