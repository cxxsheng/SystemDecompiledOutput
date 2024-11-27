package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public class Properties {
    private static final java.lang.ThreadLocal threadProperties = new java.lang.ThreadLocal();

    private Properties() {
    }

    public static boolean isOverrideSet(java.lang.String str) {
        try {
            return isSetTrue(getPropertyValue(str));
        } catch (java.security.AccessControlException e) {
            return false;
        }
    }

    public static boolean isOverrideSetTo(java.lang.String str, boolean z) {
        try {
            java.lang.String propertyValue = getPropertyValue(str);
            if (z) {
                return isSetTrue(propertyValue);
            }
            return isSetFalse(propertyValue);
        } catch (java.security.AccessControlException e) {
            return false;
        }
    }

    public static boolean setThreadOverride(java.lang.String str, boolean z) {
        boolean isOverrideSet = isOverrideSet(str);
        java.util.Map map = (java.util.Map) threadProperties.get();
        if (map == null) {
            map = new java.util.HashMap();
            threadProperties.set(map);
        }
        map.put(str, z ? "true" : "false");
        return isOverrideSet;
    }

    public static boolean removeThreadOverride(java.lang.String str) {
        java.lang.String str2;
        java.util.Map map = (java.util.Map) threadProperties.get();
        if (map != null && (str2 = (java.lang.String) map.remove(str)) != null) {
            if (map.isEmpty()) {
                threadProperties.remove();
            }
            return "true".equals(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str2));
        }
        return false;
    }

    public static java.math.BigInteger asBigInteger(java.lang.String str) {
        java.lang.String propertyValue = getPropertyValue(str);
        if (propertyValue != null) {
            return new java.math.BigInteger(propertyValue);
        }
        return null;
    }

    public static java.util.Set<java.lang.String> asKeySet(java.lang.String str) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.lang.String propertyValue = getPropertyValue(str);
        if (propertyValue != null) {
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(propertyValue, ",");
            while (stringTokenizer.hasMoreElements()) {
                hashSet.add(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(stringTokenizer.nextToken()).trim());
            }
        }
        return java.util.Collections.unmodifiableSet(hashSet);
    }

    public static java.lang.String getPropertyValue(final java.lang.String str) {
        java.lang.String str2;
        java.lang.String str3 = (java.lang.String) java.security.AccessController.doPrivileged(new java.security.PrivilegedAction() { // from class: com.android.internal.org.bouncycastle.util.Properties.1
            @Override // java.security.PrivilegedAction
            public java.lang.Object run() {
                return java.security.Security.getProperty(str);
            }
        });
        if (str3 != null) {
            return str3;
        }
        java.util.Map map = (java.util.Map) threadProperties.get();
        if (map != null && (str2 = (java.lang.String) map.get(str)) != null) {
            return str2;
        }
        return (java.lang.String) java.security.AccessController.doPrivileged(new java.security.PrivilegedAction() { // from class: com.android.internal.org.bouncycastle.util.Properties.2
            @Override // java.security.PrivilegedAction
            public java.lang.Object run() {
                return java.lang.System.getProperty(str);
            }
        });
    }

    private static boolean isSetFalse(java.lang.String str) {
        if (str == null || str.length() != 5) {
            return false;
        }
        if (str.charAt(0) != 'f' && str.charAt(0) != 'F') {
            return false;
        }
        if (str.charAt(1) != 'a' && str.charAt(1) != 'A') {
            return false;
        }
        if (str.charAt(2) != 'l' && str.charAt(2) != 'L') {
            return false;
        }
        if (str.charAt(3) != 's' && str.charAt(3) != 'S') {
            return false;
        }
        if (str.charAt(4) != 'e' && str.charAt(4) != 'E') {
            return false;
        }
        return true;
    }

    private static boolean isSetTrue(java.lang.String str) {
        if (str == null || str.length() != 4) {
            return false;
        }
        if (str.charAt(0) != 't' && str.charAt(0) != 'T') {
            return false;
        }
        if (str.charAt(1) != 'r' && str.charAt(1) != 'R') {
            return false;
        }
        if (str.charAt(2) != 'u' && str.charAt(2) != 'U') {
            return false;
        }
        if (str.charAt(3) != 'e' && str.charAt(3) != 'E') {
            return false;
        }
        return true;
    }
}
