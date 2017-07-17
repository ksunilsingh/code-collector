package sunil.code.collector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class SourceDownloader
{

  private static Logger logger = Logger.getLogger(SourceDownloader.class);

  public void downloadAndSave(String url, String filePath)
    throws MalformedURLException, IOException
  {
    InputStream in = new URL(url).openStream();
    String source = null;
    try
    {
      source = IOUtils.toString(in);
      File destinationFile = new File(filePath);
      FileUtils.writeStringToFile(destinationFile, source);
    }
    catch (IOException IOE)
    {
      logger.error("Unable to download soource for: " + source, IOE);
    }
    finally
    {
      IOUtils.closeQuietly(in);
    }
  }

}
