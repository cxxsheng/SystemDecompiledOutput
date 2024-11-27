package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputMethodUtils {
    public static final boolean DEBUG = false;
    static final char INPUT_METHOD_SEPARATOR = ':';
    static final char INPUT_METHOD_SUBTYPE_SEPARATOR = ';';
    static final int NOT_A_SUBTYPE_ID = -1;
    private static final java.lang.String TAG = "InputMethodUtils";

    private InputMethodUtils() {
    }

    static boolean canAddToLastInputMethod(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (inputMethodSubtype == null) {
            return true;
        }
        return !inputMethodSubtype.isAuxiliary();
    }

    static void setNonSelectedSystemImesDisabledUntilUsed(android.content.pm.PackageManager packageManager, java.util.List<android.view.inputmethod.InputMethodInfo> list) {
        boolean z;
        java.lang.String[] stringArray = android.content.res.Resources.getSystem().getStringArray(android.R.array.config_disableApksUnlessMatchedSku_apk_list);
        if (stringArray == null || stringArray.length == 0) {
            return;
        }
        android.view.textservice.SpellCheckerInfo currentSpellCheckerForUser = com.android.server.textservices.TextServicesManagerInternal.get().getCurrentSpellCheckerForUser(packageManager.getUserId());
        for (java.lang.String str : stringArray) {
            int i = 0;
            while (true) {
                if (i < list.size()) {
                    if (!str.equals(list.get(i).getPackageName())) {
                        i++;
                    } else {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z && (currentSpellCheckerForUser == null || !str.equals(currentSpellCheckerForUser.getPackageName()))) {
                try {
                    android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(32768L));
                    if (applicationInfo != null) {
                        if ((applicationInfo.flags & 1) != 0) {
                            setDisabledUntilUsed(packageManager, str);
                        }
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                }
            }
        }
    }

    private static void setDisabledUntilUsed(android.content.pm.PackageManager packageManager, java.lang.String str) {
        try {
            int applicationEnabledSetting = packageManager.getApplicationEnabledSetting(str);
            if (applicationEnabledSetting == 0 || applicationEnabledSetting == 1) {
                try {
                    packageManager.setApplicationEnabledSetting(str, 4, 0);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.w(TAG, "setApplicationEnabledSetting failed. packageName=" + str + " userId=" + packageManager.getUserId(), e);
                }
            }
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Slog.w(TAG, "getApplicationEnabledSetting failed. packageName=" + str + " userId=" + packageManager.getUserId(), e2);
        }
    }

    static boolean checkIfPackageBelongsToUid(android.content.pm.PackageManagerInternal packageManagerInternal, int i, java.lang.String str) {
        return packageManagerInternal.isSameApp(str, 0L, i, android.os.UserHandle.getUserId(i));
    }

    static boolean isSoftInputModeStateVisibleAllowed(int i, int i2) {
        if (i < 28) {
            return true;
        }
        return ((i2 & 1) == 0 || (i2 & 2) == 0) ? false : true;
    }

    static int[] resolveUserId(int i, int i2, @android.annotation.Nullable java.io.PrintWriter printWriter) {
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        if (i == -1) {
            return userManagerInternal.getUserIds();
        }
        if (i == -2) {
            i = i2;
        } else {
            if (i < 0) {
                if (printWriter != null) {
                    printWriter.print("Pseudo user ID ");
                    printWriter.print(i);
                    printWriter.println(" is not supported.");
                }
                return new int[0];
            }
            if (!userManagerInternal.exists(i)) {
                if (printWriter != null) {
                    printWriter.print("User #");
                    printWriter.print(i);
                    printWriter.println(" does not exit.");
                }
                return new int[0];
            }
        }
        return new int[]{i};
    }

    @android.annotation.NonNull
    static java.util.List<java.lang.String> getEnabledInputMethodIdsForFiltering(@android.annotation.NonNull android.content.Context context, int i) {
        java.lang.String nullIfEmpty = android.text.TextUtils.nullIfEmpty(com.android.server.inputmethod.SecureSettingsWrapper.getString("enabled_input_methods", null, i));
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Objects.requireNonNull(arrayList);
        splitEnabledImeStr(nullIfEmpty, new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add((java.lang.String) obj);
            }
        });
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static void splitEnabledImeStr(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.util.function.Consumer<java.lang.String> consumer) {
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SEPARATOR);
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter2 = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SUBTYPE_SEPARATOR);
        simpleStringSplitter.setString(str);
        while (simpleStringSplitter.hasNext()) {
            simpleStringSplitter2.setString(simpleStringSplitter.next());
            if (simpleStringSplitter2.hasNext()) {
                consumer.accept(simpleStringSplitter2.next());
            }
        }
    }

    @android.annotation.NonNull
    static java.lang.String concatEnabledImeIds(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String... strArr) {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(java.lang.Character.toString(INPUT_METHOD_SEPARATOR));
        if (!android.text.TextUtils.isEmpty(str)) {
            java.util.Objects.requireNonNull(arraySet);
            splitEnabledImeStr(str, new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    arraySet.add((java.lang.String) obj);
                }
            });
            stringJoiner.add(str);
        }
        for (java.lang.String str2 : strArr) {
            if (!arraySet.contains(str2)) {
                stringJoiner.add(str2);
                arraySet.add(str2);
            }
        }
        return stringJoiner.toString();
    }

    @android.annotation.Nullable
    public static android.content.ComponentName convertIdToComponentName(@android.annotation.NonNull java.lang.String str) {
        return android.content.ComponentName.unflattenFromString(str);
    }
}
