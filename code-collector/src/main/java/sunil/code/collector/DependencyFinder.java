package sunil.code.collector;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.compress.utils.IOUtils;
import org.objectweb.asm.ClassReader;

public class DependencyFinder
{

  public Set<String> getDependencies(String jarFile) throws IOException
  {
    Set<String> dependents = new TreeSet<String>();
    ZipFile f = new ZipFile(jarFile);
    try
    {
      DependencyVisitor v = new DependencyVisitor();
      Enumeration<? extends ZipEntry> en = f.entries();
      while (en.hasMoreElements())
      {
        ZipEntry e = en.nextElement();
        String name = e.getName();
        if (name.endsWith(".class"))
        {
          InputStream in = null;
          try
          {
            in = f.getInputStream(e);
            ClassReader cr = new ClassReader(in);
            cr.accept(v, 0);
            Map<String, Map<String, Integer>> dependecyMap = v.groups;
            for (Entry<String, Map<String, Integer>> entry : dependecyMap
              .entrySet())
            {
              // System.out.println("Dependencies for class: " +
              // entry.getKey());

              for (Entry<String, Integer> entryDependent : entry.getValue()
                .entrySet())
              {
                // No need for java dependencies
                if (!entryDependent.getKey().startsWith("java")
                  && !entryDependent.getKey().startsWith("com/scavanger"))
                {
                  // dependents.add(entryDependent.getKey().replace("/", "."));
                  dependents.add(entryDependent.getKey());
                }
              }
            }
          }
          finally
          {
            IOUtils.closeQuietly(in);
          }
        }
      }
    }
    finally
    {
      f.close();
    }
    return dependents;
  }

  public Set<String> getDependencies(byte[] classBytes)
  {
    Set<String> dependents = new TreeSet<String>();
    DependencyVisitor v = new DependencyVisitor();
    ClassReader cr = new ClassReader(classBytes);
    cr.accept(v, 0);
    Map<String, Map<String, Integer>> dependecyMap = v.groups;
    for (Entry<String, Map<String, Integer>> entry : dependecyMap.entrySet())
    {
      for (Entry<String, Integer> entryDependent : entry.getValue().entrySet())
      {
        // No need for java dependencies
        if (!entryDependent.getKey().equals(entry.getKey())
          && !entryDependent.getKey().startsWith("java")
          && !entryDependent.getKey().startsWith("com/scavanger/test"))
        {
          // dependents.add(entryDependent.getKey().replace("/", "."));
          dependents.add(entryDependent.getKey());
        }
      }
    }
    return dependents;
  }

  public void getDependencies(Set<String> dependents, String jarFile,
                              Set<String> allDependents) throws IOException
  {
    if (dependents.size() == 0)
    {
      return;
    }
    else
    {
      Map<String, byte[]> classBytes =
        readClassesFromDependencyJar(jarFile, dependents);
      for (Entry<String, byte[]> entry : classBytes.entrySet())
      {
        Set<String> intermediateDependents = getDependencies(entry.getValue());
        intermediateDependents.removeAll(allDependents);
        if (intermediateDependents.size() == 0)
        {
          continue;
        }
        allDependents.addAll(intermediateDependents);
        getDependencies(intermediateDependents, jarFile, allDependents);
      }
    }
  }

  public Map<String, byte[]> readClassesFromDependencyJar(String jarFile,
                                                          Set<String> classes)
    throws IOException
  {
    ;
    Map<String, byte[]> classesMap = new HashMap<String, byte[]>();
    ZipFile f = new ZipFile(jarFile);
    try
    {
      Enumeration<? extends ZipEntry> en = f.entries();

      while (en.hasMoreElements())
      {
        ZipEntry e = en.nextElement();
        InputStream in = f.getInputStream(e);
        String name = e.getName();
        if (name.endsWith(".class"))
        {
          if (classes.contains(name.substring(0, name.length() - 6)))
          {
            byte[] classBytes = new byte[(int) e.getSize()];
            try
            {
              IOUtils.readFully(in, classBytes);
            }
            finally
            {
              if (in != null)
              {
                IOUtils.closeQuietly(in);
              }
            }
            classesMap.put(name.substring(0, name.length() - 6), classBytes);
          }
        }
      }
    }
    finally
    {
      f.close();
    }

    return classesMap;
  }

}
