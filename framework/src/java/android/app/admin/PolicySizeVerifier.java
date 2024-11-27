package android.app.admin;

/* loaded from: classes.dex */
public class PolicySizeVerifier {
    public static final int MAX_LONG_SUPPORT_MESSAGE_LENGTH = 20000;
    public static final int MAX_ORG_NAME_LENGTH = 200;
    public static final int MAX_PACKAGE_NAME_LENGTH = 223;
    public static final int MAX_POLICY_STRING_LENGTH = 65535;
    public static final int MAX_PROFILE_NAME_LENGTH = 200;
    public static final int MAX_SHORT_SUPPORT_MESSAGE_LENGTH = 200;

    public static void enforceMaxStringLength(java.lang.String str, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkArgument(str.length() <= 65535, str2 + " loo long");
    }

    public static void enforceMaxPackageNameLength(java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgument(str.length() <= 223, "Package name too long");
    }

    public static void enforceMaxStringLength(android.os.PersistableBundle persistableBundle, java.lang.String str) {
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        arrayDeque.add(persistableBundle);
        while (!arrayDeque.isEmpty()) {
            android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) arrayDeque.remove();
            for (java.lang.String str2 : persistableBundle2.keySet()) {
                enforceMaxStringLength(str2, "key in " + str);
                java.lang.Object obj = persistableBundle2.get(str2);
                if (obj instanceof java.lang.String) {
                    enforceMaxStringLength((java.lang.String) obj, "string value in " + str);
                } else if (obj instanceof java.lang.String[]) {
                    for (java.lang.String str3 : (java.lang.String[]) obj) {
                        enforceMaxStringLength(str3, "string value in " + str);
                    }
                } else if (obj instanceof android.os.PersistableBundle) {
                    arrayDeque.add((android.os.PersistableBundle) obj);
                }
            }
        }
    }

    public static void enforceMaxParcelableFieldsLength(android.os.Parcelable parcelable) {
    }

    public static void enforceMaxComponentNameLength(android.content.ComponentName componentName) {
        enforceMaxPackageNameLength(componentName.getPackageName());
        enforceMaxStringLength(componentName.flattenToString(), "componentName");
    }

    public static java.lang.CharSequence truncateIfLonger(java.lang.CharSequence charSequence, int i) {
        if (charSequence == null || charSequence.length() <= i) {
            return charSequence;
        }
        return charSequence.subSequence(0, i);
    }
}
