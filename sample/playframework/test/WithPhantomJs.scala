import com.typesafe.config.ConfigFactory
import java.io.File
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import play.api.Play
import play.api.test.{WithBrowser, WebDriverFactory}
import scala.collection.JavaConversions._

class WithPhantomJs extends WithBrowser(webDriver = WithPhantomJs.webDriver()) {
}

object WithPhantomJs {
  ConfigFactory.parseFile(new File("target/phantomjs.properties")).entrySet().foreach(entry => {
    System.setProperty(entry.getKey(), entry.getValue().unwrapped().toString())
  })

  def webDriver(): WebDriver = {
    WebDriverFactory(classOf[PhantomJSDriver])
  }
}
