import java.io.File
import java.io.FileInputStream
import java.util.Properties

import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import play.api.test.{WithBrowser, WebDriverFactory}

class WithPhantomJs extends WithBrowser(webDriver = WithPhantomJs.webDriver()) {
}

object WithPhantomJs {
  val properties = System.getProperties()
  val inputStream = new FileInputStream(new File("target/phantomjs.properties"))
  try {
    properties.load(inputStream)
  } finally {
    inputStream.close()
  }
  System.setProperties(properties)

  def webDriver(): WebDriver = {
    WebDriverFactory(classOf[PhantomJSDriver])
  }
}
