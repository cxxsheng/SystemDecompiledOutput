package com.android.internal.util;

/* loaded from: classes5.dex */
public class MessageUtils {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = com.android.internal.util.MessageUtils.class.getSimpleName();
    public static final java.lang.String[] DEFAULT_PREFIXES = {"CMD_", "EVENT_"};

    public static class DuplicateConstantError extends java.lang.Error {
        private DuplicateConstantError() {
        }

        public DuplicateConstantError(java.lang.String str, java.lang.String str2, int i) {
            super(java.lang.String.format("Duplicate constant value: both %s and %s = %d", str, str2, java.lang.Integer.valueOf(i)));
        }
    }

    public static android.util.SparseArray<java.lang.String> findMessageNames(java.lang.Class[] clsArr, java.lang.String[] strArr) {
        android.util.SparseArray<java.lang.String> sparseArray = new android.util.SparseArray<>();
        for (java.lang.Class cls : clsArr) {
            java.lang.String name = cls.getName();
            try {
                for (java.lang.reflect.Field field : cls.getDeclaredFields()) {
                    int modifiers = field.getModifiers();
                    if (!((!java.lang.reflect.Modifier.isFinal(modifiers)) | (!java.lang.reflect.Modifier.isStatic(modifiers)))) {
                        java.lang.String name2 = field.getName();
                        for (java.lang.String str : strArr) {
                            if (name2.startsWith(str)) {
                                try {
                                    field.setAccessible(true);
                                    try {
                                        int i = field.getInt(null);
                                        java.lang.String str2 = sparseArray.get(i);
                                        if (str2 != null && !str2.equals(name2)) {
                                            throw new com.android.internal.util.MessageUtils.DuplicateConstantError(name2, str2, i);
                                        }
                                        sparseArray.put(i, name2);
                                    } catch (java.lang.ExceptionInInitializerError | java.lang.IllegalArgumentException e) {
                                    }
                                } catch (java.lang.IllegalAccessException | java.lang.SecurityException e2) {
                                }
                            }
                        }
                    }
                }
            } catch (java.lang.SecurityException e3) {
                android.util.Log.e(TAG, "Can't list fields of class " + name);
            }
        }
        return sparseArray;
    }

    public static android.util.SparseArray<java.lang.String> findMessageNames(java.lang.Class[] clsArr) {
        return findMessageNames(clsArr, DEFAULT_PREFIXES);
    }
}
