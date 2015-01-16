import org.openqa.selenium.phantomjs.PhantomJSDriver

object Main {
  def main(args: Array[String]): Unit = {
    val driver = new PhantomJSDriver()
    try {
      driver.get("http://saturday06.github.io/sbt-phantomjs/test/hello.html")
      assert(driver.getTitle() == "Hello, PhantomJS!")
    } finally {
      driver.quit()
    }
  }
}
