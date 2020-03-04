package com.web.tools.util;

import java.io.PrintStream;
import java.lang.instrument.Instrumentation;

public class TesthAgent
{
  public static void agentmain(String args, Instrumentation inst)
  {
    inst.addTransformer(new TesthTransformer(), true);
    try
    {
      inst.retransformClasses(new Class[] { DESUtil.class });
      //System.out.println("Agent Load Done.");
    }
    catch (Exception e)
    {
      //System.out.println("agent load failed!");
    }
  }
}
