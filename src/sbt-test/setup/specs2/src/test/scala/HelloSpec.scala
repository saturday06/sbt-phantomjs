import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.specs2.mutable._

class HelloSpec extends Specification {
  "The 'Hello world' string" should {
    "contain 11 characters" in {
      val driver = new PhantomJSDriver()
      try {
        driver.get("https://saturday06.github.io/sbt-phantomjs/test/hello.html")
        driver.findElementByTagName("title").getText() must_== "Hello, PhantomJS!"
      } finally {
        driver.quit()
      }
    }
  }
}
