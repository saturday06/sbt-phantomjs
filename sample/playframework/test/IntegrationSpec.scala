import java.io.FileInputStream
import java.util.Properties
import org.junit.runner._
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.specs2.mutable._
import org.specs2.runner._
import play.api.Play
import play.api.Play.current
import play.api.test._

@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  "Application" should {

    "work from within a browser" in new WithBrowser(webDriver = WebDriverFactory(classOf[PhantomJSDriver])) {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must contain("Hello, PhantomJS World")
    }
  }
}
