package get;

import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import javassist.ClassPool;
import javassist.CtClass;

public class TestAgent
{
  public static void agentmain(String args, Instrumentation inst)
  {
    try
    {
      ClassPool cp = ClassPool.getDefault();
      CtClass cc = cp.get("com.web.tools.util.DESUtil");
      cc.writeFile("/tmp/tmp2");
    }
    catch (Exception e)
    {
      
    }
  }
}
