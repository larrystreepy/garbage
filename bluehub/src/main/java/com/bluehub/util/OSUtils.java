package com.bluehub.util;

/**
 * Static utilities related to the OS/platform we're running on.
 */
public class OSUtils {
    public static final String platform;

    static {
        platform = System.getProperty("os.name").split(" ")[0].toLowerCase();
    }

    /**
     * @return The platform identifier (taken from os.name)
     */
    public static String getPlatform() {
        return platform;
    }

    /**
     * @return {@code true} if we're running on windows
     */
    public static boolean isWindows() {
        return platform.contains("win");
    }
}
