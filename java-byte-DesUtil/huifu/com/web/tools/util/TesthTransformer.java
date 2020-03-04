package com.web.tools.util;

import java.io.PrintStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class TesthTransformer
  implements ClassFileTransformer
{
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
    throws IllegalClassFormatException
  {

    if (className.indexOf("DESUtil") == -1){
        return null;
    }
    try
    {
      ClassPool cp = ClassPool.getDefault();
      CtClass cc = cp.get("com.web.tools.util.DESUtil");
          if(cc.isFrozen()){
              cc.defrost();
          };
          CtMethod m = cc.getDeclaredMethod("getDecryptString");
            m.setBody("{sun.misc.BASE64Decoder base64decoder = new sun.misc.BASE64Decoder();try{byte[] bytes = base64decoder.decodeBuffer($1);javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);cipher.init(2, key);byte[] doFinal = cipher.doFinal(bytes);return new String(doFinal, CHARSETNAME);}catch (Exception e){throw new RuntimeException(e);}}");
          //m.insertBefore("{ System.out.println(\"start\"); }");
          cc.writeFile("/tmp/tmp3");
      return cc.toBytecode();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}