import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class XlassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        String className = args[0];
        String methodName = args[1];
        ClassLoader classLoader = new XlassLoader();
        Class<?> clazz = null;
        try{
            clazz = classLoader.loadClass(className);
        } catch(ClassNotFoundException ex){
            System.out.println("class not found. name = " + className);
            return;
        }
        boolean findMethod = false;
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.println("class name = " + clazz.getSimpleName() + ", method name=" + m.getName());
            if(m.getName().equals(methodName))
                findMethod = true;
        }
        if(!findMethod){
            System.out.println("method " + methodName + " not found!. Exit!");
            return;
        }
        
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod(methodName);
        method.invoke(instance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String resourcePath = name.replace(".", "/");
        final String suffix = ".xlass";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourcePath + suffix);
        try {
            int lenStream = inputStream.available();
            byte[] byteArray = new byte[lenStream];
            inputStream.read(byteArray);
            byte[] classBytes = decode(byteArray);
            //Converts an array of bytes into an instance of class Class.
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            close(inputStream);
        }
    }

    private static byte[] decode(byte[] byteArray) {
        byte[] targetArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            targetArray[i] = (byte) (255 - byteArray[i]);
        }
        return targetArray;
    }

    private static void close(Closeable res) {
        if (null != res) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
