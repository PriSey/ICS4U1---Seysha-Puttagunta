package com.athabasca;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Session 
{
    /*
     * Permission system
     * 0 = lowest, employees
     * 1 = admins
     */
    private static int PERMISSION = -1;
    private static String f_name;
    private static String l_name;
    private static String email;
    private static String token;
    private static ArrayList<String> assigned;
    private static DatabaseUtil db;

    public Session(String email, String token)
    {
        db = new DatabaseUtil();
        Session.email = email;
    }

    @SuppressWarnings("unchecked")
    public static void update(Consumer<ArrayList<String>> callback) {
        db.readData("employee", data -> {
            if (data != null) {
                try {
                    Map<String, Map<String, Object>> loadedData = (Map<String, Map<String, Object>>) data;
                    for (Map.Entry<String, Map<String, Object>> entry : loadedData.entrySet()) {
                        if (entry.getKey().equals(email.replaceAll("\\.", "\\\\"))) {
                            f_name = (String) entry.getValue().get("f_name");
                            l_name = (String) entry.getValue().get("l_name");
                            // Retrieve and cast the permission value
                            Object permissionObj = entry.getValue().get("permission");
                            if (permissionObj instanceof Long) {
                                PERMISSION = ((Long) permissionObj).intValue();
                            } else if (permissionObj instanceof Integer) {
                                PERMISSION = (Integer) permissionObj;
                            } else {
                                throw new ClassCastException("Unexpected type for permission: " + permissionObj.getClass().getName());
                            }
                            // Retrieve the assigned list
                            assigned = (ArrayList<String>) entry.getValue().get("assigned");
                            break;
                        }
                    }
                } catch (ClassCastException e) {
                    System.err.println("Error casting data: " + e.getMessage());
                    e.printStackTrace();
                }
                // Invoke the callback with the assigned list
                callback.accept(assigned);
            }
        });
    }

    /**
     * @return String return the token
     */
    public static String getToken() {
        return token;
    }

    /**
     * @return int return the PERMISSION
     */
    public static int getPermission() {
        return PERMISSION;
    }

    /**
     * @return String return the f_name
     */
    public static String getF_name() {
        return f_name;
    }

    /**
     * @param f_name the f_name to set
     */
    public static void setF_name(String F_name) {
        f_name = F_name;
    }

    /**
     * @return String return the l_name
     */
    public static String getL_name() {
        return l_name;
    }

    /**
     * @param l_name the l_name to set
     */
    public static void setL_name(String L_name) {
        l_name = L_name;
    }

    /**
     * @return String return the email
     */
    public static String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public static void setEmail(String Email) {
        email = Email;
    }

    /**
     * @return Map<Integer, String> return the assigned
     */
    public static List<String> getAssigned() {
        return assigned;
    }
}
