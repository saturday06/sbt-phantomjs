object Main {
  def main(args: Array[String]): Unit = {
    val k = "phantomjs.binary.path"
    println(k + ": " + System.getProperty(k))
  }
}
