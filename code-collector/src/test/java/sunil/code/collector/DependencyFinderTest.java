package sunil.code.collector;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.junit.Test;

public class DependencyFinderTest
{

  private static Logger logger = Logger.getLogger(DependencyFinderTest.class);

  private static final String pathToProjectToBeCollectedJar =
    "C:\\Users\\sssingh\\code-collector-test-0.0.1-SNAPSHOT.jar";

  private static final String pathToProjectRoot =
    "C:\\Users\\sssingh\\projects\\code-collector-test\\";

  private static final String pathToProjectSourceCode = pathToProjectRoot
    + "src\\main\\java\\";

  private static final String pathToProjectToBeCollectedJarWithDependencies =
    "C:\\Users\\sssingh\\projects\\code-collector-test\\target\\code-collector-test-jar-with-dependencies.jar";

  private static final String SourceCodeRootURL =
    "https://raw.githubusercontent.com/apache/commons-lang/release/src/main/java/";

  private final List<String> dependenciesToCatch = new ArrayList<String>()
  {
    /**
     * 
     */
    private static final long serialVersionUID = 6164283522036661561L;

    {
      add("org/apache/commons/lang3");
    }
  };

  @Test
  public void DFTest()
  {
    DependencyFinder dependencyFinder = new DependencyFinder();

    try
    {
      Set<String> dependents =
        dependencyFinder.getDependencies(pathToProjectToBeCollectedJar);

      Set<String> allDependents = new TreeSet<String>();
      dependencyFinder
        .getDependencies(dependents,
                         pathToProjectToBeCollectedJarWithDependencies,
                         allDependents);
      SourceDownloader scl = new SourceDownloader();
      for (String dependent : allDependents)
      {
        if (!dependent.contains("$"))
        {

          for (String dependencyToCatch : dependenciesToCatch)
          {
            if (dependent.contains(dependencyToCatch))
            {
              scl.downloadAndSave(SourceCodeRootURL + dependent + ".java",
                                  pathToProjectSourceCode
                                    + dependent.replace("/", "\\") + ".java");
              break;
            }
          }
        }
      }

    }
    catch (IOException e)
    {
      fail("Test Failed");
    }
  }
}
