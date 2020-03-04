package zengqiangest;

import java.io.PrintStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class TestTransformer
  implements ClassFileTransformer
{
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
    throws IllegalClassFormatException
  {
    //System.out.println("Transforming " + className);

    if (className.indexOf("DataController") == -1){
        return null;
    }
    try
    {
      ClassPool cp = ClassPool.getDefault();
      CtClass cc = cp.get("com.msx2009.vulns.controller.DataController");
      if(cc.isFrozen()){
          cc.defrost();
      };
      CtMethod m = cc.getDeclaredMethod("getVuln");
      m.setBody("{ System.out.println(\"start\");return \"1,2,3\"; }");
      //m.insertBefore("{ System.out.println(\"start\"); }");
      //m.insertAfter("{ System.out.println(\"end\"); }");
      return cc.toBytecode();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}