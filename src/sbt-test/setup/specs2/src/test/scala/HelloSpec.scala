import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.specs2.mutable._

class HelloSpec extends Specification {
  "PhantomJSDriver" should {
    "get web content" in {
      val driver = new PhantomJSDriver()
      try {
        driver.get("http://saturday06.github.io/sbt-phantomjs/test/hello.html")
        driver.getTitle() must_== "Hello, PhantomJS!"
      } finally {
        driver.quit()
      }
    }
  }
}
