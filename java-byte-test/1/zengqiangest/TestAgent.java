package zengqiangest;

import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import com.msx2009.vulns.controller.DataController;

public class TestAgent
{
  public static void agentmain(String args, Instrumentation inst)
  {
    inst.addTransformer(new TestTransformer(), true);
    try
    {
      inst.retransformClasses(new Class[] { DataController.class });
      System.out.println("Agent Load Done.");
    }
    catch (Exception e)
    {
      System.out.println("agent load failed!");
    }
  }
}
