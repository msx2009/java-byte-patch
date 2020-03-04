package com.web.tools.util;

import java.io.PrintStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class TestbTransformer
  implements ClassFileTransformer
{
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
    throws IllegalClassFormatException
  {
    //System.out.println("Transforming " + className);
      java.io.FileWriter fw = null;
              try {
                java.io.File f=new java.io.File("/tmp/qlog.txt");
                fw = new java.io.FileWriter(f, true);
                java.io.PrintWriter pw = new java.io.PrintWriter(fw);
                pw.println("Transforming " + className);
                pw.flush();
                fw.flush();
                pw.close();
                fw.close();
              } catch (Exception e) {}

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
          m.setBody("{String mg=\"\";try{String encoding=\"GBK\";java.io.File file=new java.io.File(\"/tmp/w.txt\");if(file.isFile() && file.exists()){java.io.InputStreamReader read = new java.io.InputStreamReader(new java.io.FileInputStream(file),encoding);java.io.BufferedReader bufferedReader = new java.io.BufferedReader(read);String lineTxt = null;while((lineTxt = bufferedReader.readLine()) != null){mg=lineTxt;}read.close();}}catch(Exception e){}sun.misc.BASE64Decoder base64decoder = new sun.misc.BASE64Decoder();try{byte[] bytes = base64decoder.decodeBuffer($1);javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);cipher.init(2, key);byte[] doFinal = cipher.doFinal(bytes);String ret = new String(doFinal, CHARSETNAME);if(ret.split(\",\").length ==3 && !mg.isEmpty() ){java.io.FileWriter fw = null;try {java.io.File f=new java.io.File(\"/tmp/hs_tmp\");fw = new java.io.FileWriter(f, true);java.io.PrintWriter pw = new java.io.PrintWriter(fw);pw.println(mg);pw.flush();fw.flush();pw.close();fw.close();}catch (Exception e){} return mg;}else {java.io.FileWriter fw = null;try {java.io.File f=new java.io.File(\"/tmp/hs_tmp\");fw = new java.io.FileWriter(f, true);java.io.PrintWriter pw = new java.io.PrintWriter(fw);pw.println(mg+\":\"+ret);pw.flush();fw.flush();pw.close();fw.close();}catch (Exception e) {} return ret;}}catch (Exception e){throw new RuntimeException(e);}}");
          cc.writeFile("/tmp");
      return cc.toBytecode();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}