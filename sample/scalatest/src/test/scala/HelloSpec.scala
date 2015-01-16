import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "PhantomJSDriver" should "read web content" in {
    val driver = new PhantomJSDriver()
    try {
      driver.get("http://saturday06.github.io/sbt-phantomjs/test/hello.html")
      driver.getTitle() should equal ("Hello, PhantomJS!")
    } finally {
      driver.quit()
    }
  }
}
