import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class HaltAndCatchFireStatement implements Statement {
    public static int getPid() {
        java.lang.management.RuntimeMXBean runtime =
                java.lang.management.ManagementFactory.getRuntimeMXBean();
        try {
            java.lang.reflect.Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
            sun.management.VMManagement mgmt = (sun.management.VMManagement) jvm.get(runtime);
            java.lang.reflect.Method pid_method = mgmt.getClass().getDeclaredMethod("getProcessId");
            pid_method.setAccessible(true);

            return (Integer) pid_method.invoke(mgmt);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void run(Map<VariableBinding, Variable> symbolTable) {
        int pid = getPid();
        try {
            Runtime.getRuntime().exec("kill -SEGV " + pid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
