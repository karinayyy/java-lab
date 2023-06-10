package J2_Lab2_Karpenko.taskfirst;

import com.thoughtworks.xstream.security.TypePermission;

/**
 * The ListSubwStreamPermission class implements the TypePermission interface
 * <p>
 * and provides a way to check if a given Class type is allowed based on its name.
 * <p>
 * It allows the classes "org.example.taskfirst.ListSubwStream" and "org.example.taskfirst.HourWithDates".
 */
public class ListSubwStreamPermission implements TypePermission {
    /**
     * Check if the given Class type is allowed.
     *
     * @param type the Class type to check
     * @return true if the type name is "org.example.taskfirst.ListSubwStream" or "org.example.taskfirst.HourWithDates",
     * false otherwise
     */
    public boolean allows(Class type) {
        return type.getName().equals("org.example.taskfirst.ListSubwStream") ||
                type.getName().equals("org.example.taskfirst.HourWithDates");
    }

}

