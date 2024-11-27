package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputMethodSettings {
    public static final boolean DEBUG = false;
    private static final char INPUT_METHOD_SEPARATOR = ':';
    private static final char INPUT_METHOD_SUBTYPE_SEPARATOR = ';';
    private static final int NOT_A_SUBTYPE_ID = -1;
    private static final java.lang.String NOT_A_SUBTYPE_ID_STR = java.lang.String.valueOf(-1);
    private static final java.lang.String TAG = "InputMethodSettings";
    private final java.util.List<android.view.inputmethod.InputMethodInfo> mMethodList;
    private final com.android.server.inputmethod.InputMethodMap mMethodMap;
    private final int mUserId;

    private static void buildEnabledInputMethodsSettingString(java.lang.StringBuilder sb, android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>> pair) {
        sb.append((java.lang.String) pair.first);
        for (int i = 0; i < ((java.util.ArrayList) pair.second).size(); i++) {
            java.lang.String str = (java.lang.String) ((java.util.ArrayList) pair.second).get(i);
            sb.append(INPUT_METHOD_SUBTYPE_SEPARATOR);
            sb.append(str);
        }
    }

    static com.android.server.inputmethod.InputMethodSettings createEmptyMap(int i) {
        return new com.android.server.inputmethod.InputMethodSettings(com.android.server.inputmethod.InputMethodMap.emptyMap(), i);
    }

    static com.android.server.inputmethod.InputMethodSettings create(com.android.server.inputmethod.InputMethodMap inputMethodMap, int i) {
        return new com.android.server.inputmethod.InputMethodSettings(inputMethodMap, i);
    }

    private InputMethodSettings(com.android.server.inputmethod.InputMethodMap inputMethodMap, int i) {
        this.mMethodMap = inputMethodMap;
        this.mMethodList = inputMethodMap.values();
        this.mUserId = i;
    }

    @android.annotation.NonNull
    com.android.server.inputmethod.InputMethodMap getMethodMap() {
        return this.mMethodMap;
    }

    @android.annotation.NonNull
    java.util.List<android.view.inputmethod.InputMethodInfo> getMethodList() {
        return this.mMethodList;
    }

    private void putString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        com.android.server.inputmethod.SecureSettingsWrapper.putString(str, str2, this.mUserId);
    }

    @android.annotation.Nullable
    private java.lang.String getString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        return com.android.server.inputmethod.SecureSettingsWrapper.getString(str, str2, this.mUserId);
    }

    private void putInt(java.lang.String str, int i) {
        com.android.server.inputmethod.SecureSettingsWrapper.putInt(str, i, this.mUserId);
    }

    private int getInt(java.lang.String str, int i) {
        return com.android.server.inputmethod.SecureSettingsWrapper.getInt(str, i, this.mUserId);
    }

    java.util.ArrayList<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList() {
        return getEnabledInputMethodListWithFilter(null);
    }

    @android.annotation.NonNull
    java.util.ArrayList<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodListWithFilter(@android.annotation.Nullable java.util.function.Predicate<android.view.inputmethod.InputMethodInfo> predicate) {
        return createEnabledInputMethodList(getEnabledInputMethodsAndSubtypeList(), predicate);
    }

    java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(android.view.inputmethod.InputMethodInfo inputMethodInfo, boolean z) {
        java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = getEnabledInputMethodSubtypeList(inputMethodInfo);
        if (z && enabledInputMethodSubtypeList.isEmpty()) {
            enabledInputMethodSubtypeList = com.android.server.inputmethod.SubtypeUtils.getImplicitlyApplicableSubtypes(com.android.server.inputmethod.SystemLocaleWrapper.get(this.mUserId), inputMethodInfo);
        }
        return android.view.inputmethod.InputMethodSubtype.sort(inputMethodInfo, enabledInputMethodSubtypeList);
    }

    java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        java.util.List<android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>>> enabledInputMethodsAndSubtypeList = getEnabledInputMethodsAndSubtypeList();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (inputMethodInfo != null) {
            int i = 0;
            while (true) {
                if (i >= enabledInputMethodsAndSubtypeList.size()) {
                    break;
                }
                android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>> pair = enabledInputMethodsAndSubtypeList.get(i);
                android.view.inputmethod.InputMethodInfo inputMethodInfo2 = this.mMethodMap.get((java.lang.String) pair.first);
                if (inputMethodInfo2 == null || !inputMethodInfo2.getId().equals(inputMethodInfo.getId())) {
                    i++;
                } else {
                    int subtypeCount = inputMethodInfo2.getSubtypeCount();
                    for (int i2 = 0; i2 < subtypeCount; i2++) {
                        android.view.inputmethod.InputMethodSubtype subtypeAt = inputMethodInfo2.getSubtypeAt(i2);
                        for (int i3 = 0; i3 < ((java.util.ArrayList) pair.second).size(); i3++) {
                            if (java.lang.String.valueOf(subtypeAt.hashCode()).equals((java.lang.String) ((java.util.ArrayList) pair.second).get(i3))) {
                                arrayList.add(subtypeAt);
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    java.util.List<android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>>> getEnabledInputMethodsAndSubtypeList() {
        java.lang.String enabledInputMethodsStr = getEnabledInputMethodsStr();
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SEPARATOR);
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter2 = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SUBTYPE_SEPARATOR);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (android.text.TextUtils.isEmpty(enabledInputMethodsStr)) {
            return arrayList;
        }
        simpleStringSplitter.setString(enabledInputMethodsStr);
        while (simpleStringSplitter.hasNext()) {
            simpleStringSplitter2.setString(simpleStringSplitter.next());
            if (simpleStringSplitter2.hasNext()) {
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                java.lang.String next = simpleStringSplitter2.next();
                while (simpleStringSplitter2.hasNext()) {
                    arrayList2.add(simpleStringSplitter2.next());
                }
                arrayList.add(new android.util.Pair(next, arrayList2));
            }
        }
        return arrayList;
    }

    boolean buildAndPutEnabledInputMethodsStrRemovingId(java.lang.StringBuilder sb, java.util.List<android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>>> list, java.lang.String str) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < list.size(); i++) {
            android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>> pair = list.get(i);
            if (((java.lang.String) pair.first).equals(str)) {
                z = true;
            } else {
                if (z2) {
                    sb.append(INPUT_METHOD_SEPARATOR);
                } else {
                    z2 = true;
                }
                buildEnabledInputMethodsSettingString(sb, pair);
            }
        }
        if (z) {
            putEnabledInputMethodsStr(sb.toString());
        }
        return z;
    }

    private java.util.ArrayList<android.view.inputmethod.InputMethodInfo> createEnabledInputMethodList(java.util.List<android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>>> list, java.util.function.Predicate<android.view.inputmethod.InputMethodInfo> predicate) {
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> arrayList = new java.util.ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mMethodMap.get((java.lang.String) list.get(i).first);
            if (inputMethodInfo != null && !inputMethodInfo.isVrOnly() && (predicate == null || predicate.test(inputMethodInfo))) {
                arrayList.add(inputMethodInfo);
            }
        }
        return arrayList;
    }

    void putEnabledInputMethodsStr(@android.annotation.Nullable java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            putString("enabled_input_methods", null);
        } else {
            putString("enabled_input_methods", str);
        }
    }

    @android.annotation.NonNull
    java.lang.String getEnabledInputMethodsStr() {
        return getString("enabled_input_methods", "");
    }

    private void saveSubtypeHistory(java.util.List<android.util.Pair<java.lang.String, java.lang.String>> list, java.lang.String str, java.lang.String str2) {
        boolean z;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (!android.text.TextUtils.isEmpty(str) && !android.text.TextUtils.isEmpty(str2)) {
            sb.append(str);
            sb.append(INPUT_METHOD_SUBTYPE_SEPARATOR);
            sb.append(str2);
            z = true;
        } else {
            z = false;
        }
        for (int i = 0; i < list.size(); i++) {
            android.util.Pair<java.lang.String, java.lang.String> pair = list.get(i);
            java.lang.String str3 = (java.lang.String) pair.first;
            java.lang.String str4 = (java.lang.String) pair.second;
            if (android.text.TextUtils.isEmpty(str4)) {
                str4 = NOT_A_SUBTYPE_ID_STR;
            }
            if (z) {
                sb.append(INPUT_METHOD_SEPARATOR);
            } else {
                z = true;
            }
            sb.append(str3);
            sb.append(INPUT_METHOD_SUBTYPE_SEPARATOR);
            sb.append(str4);
        }
        putSubtypeHistoryStr(sb.toString());
    }

    private void addSubtypeToHistory(java.lang.String str, java.lang.String str2) {
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> loadInputMethodAndSubtypeHistory = loadInputMethodAndSubtypeHistory();
        int i = 0;
        while (true) {
            if (i >= loadInputMethodAndSubtypeHistory.size()) {
                break;
            }
            android.util.Pair<java.lang.String, java.lang.String> pair = loadInputMethodAndSubtypeHistory.get(i);
            if (!((java.lang.String) pair.first).equals(str)) {
                i++;
            } else {
                loadInputMethodAndSubtypeHistory.remove(pair);
                break;
            }
        }
        saveSubtypeHistory(loadInputMethodAndSubtypeHistory, str, str2);
    }

    private void putSubtypeHistoryStr(@android.annotation.NonNull java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            putString("input_methods_subtype_history", null);
        } else {
            putString("input_methods_subtype_history", str);
        }
    }

    android.util.Pair<java.lang.String, java.lang.String> getLastInputMethodAndSubtype() {
        return getLastSubtypeForInputMethodInternal(null);
    }

    @android.annotation.Nullable
    android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype() {
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        android.util.Pair<java.lang.String, java.lang.String> lastInputMethodAndSubtype = getLastInputMethodAndSubtype();
        if (lastInputMethodAndSubtype == null || android.text.TextUtils.isEmpty((java.lang.CharSequence) lastInputMethodAndSubtype.first) || android.text.TextUtils.isEmpty((java.lang.CharSequence) lastInputMethodAndSubtype.second) || (inputMethodInfo = this.mMethodMap.get((java.lang.String) lastInputMethodAndSubtype.first)) == null) {
            return null;
        }
        try {
            int subtypeIdFromHashCode = com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, java.lang.Integer.parseInt((java.lang.String) lastInputMethodAndSubtype.second));
            if (subtypeIdFromHashCode < 0 || subtypeIdFromHashCode >= inputMethodInfo.getSubtypeCount()) {
                return null;
            }
            return inputMethodInfo.getSubtypeAt(subtypeIdFromHashCode);
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    java.lang.String getLastSubtypeForInputMethod(java.lang.String str) {
        android.util.Pair<java.lang.String, java.lang.String> lastSubtypeForInputMethodInternal = getLastSubtypeForInputMethodInternal(str);
        if (lastSubtypeForInputMethodInternal != null) {
            return (java.lang.String) lastSubtypeForInputMethodInternal.second;
        }
        return null;
    }

    private android.util.Pair<java.lang.String, java.lang.String> getLastSubtypeForInputMethodInternal(java.lang.String str) {
        java.util.List<android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>>> enabledInputMethodsAndSubtypeList = getEnabledInputMethodsAndSubtypeList();
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> loadInputMethodAndSubtypeHistory = loadInputMethodAndSubtypeHistory();
        for (int i = 0; i < loadInputMethodAndSubtypeHistory.size(); i++) {
            android.util.Pair<java.lang.String, java.lang.String> pair = loadInputMethodAndSubtypeHistory.get(i);
            java.lang.String str2 = (java.lang.String) pair.first;
            if (android.text.TextUtils.isEmpty(str) || str2.equals(str)) {
                java.lang.String enabledSubtypeHashCodeForInputMethodAndSubtype = getEnabledSubtypeHashCodeForInputMethodAndSubtype(enabledInputMethodsAndSubtypeList, str2, (java.lang.String) pair.second);
                if (!android.text.TextUtils.isEmpty(enabledSubtypeHashCodeForInputMethodAndSubtype)) {
                    return new android.util.Pair<>(str2, enabledSubtypeHashCodeForInputMethodAndSubtype);
                }
            }
        }
        return null;
    }

    private java.lang.String getEnabledSubtypeHashCodeForInputMethodAndSubtype(java.util.List<android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>>> list, java.lang.String str, java.lang.String str2) {
        android.os.LocaleList localeList = com.android.server.inputmethod.SystemLocaleWrapper.get(this.mUserId);
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            android.util.Pair<java.lang.String, java.util.ArrayList<java.lang.String>> pair = list.get(i2);
            if (((java.lang.String) pair.first).equals(str)) {
                java.util.ArrayList arrayList = (java.util.ArrayList) pair.second;
                android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mMethodMap.get(str);
                if (!arrayList.isEmpty()) {
                    while (i < arrayList.size()) {
                        java.lang.String str3 = (java.lang.String) arrayList.get(i);
                        if (!str3.equals(str2)) {
                            i++;
                        } else {
                            try {
                                if (com.android.server.inputmethod.SubtypeUtils.isValidSubtypeId(inputMethodInfo, java.lang.Integer.parseInt(str2))) {
                                    return str3;
                                }
                                return NOT_A_SUBTYPE_ID_STR;
                            } catch (java.lang.NumberFormatException e) {
                                return NOT_A_SUBTYPE_ID_STR;
                            }
                        }
                    }
                } else if (inputMethodInfo != null && inputMethodInfo.getSubtypeCount() > 0) {
                    java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> implicitlyApplicableSubtypes = com.android.server.inputmethod.SubtypeUtils.getImplicitlyApplicableSubtypes(localeList, inputMethodInfo);
                    int size = implicitlyApplicableSubtypes.size();
                    while (i < size) {
                        if (!java.lang.String.valueOf(implicitlyApplicableSubtypes.get(i).hashCode()).equals(str2)) {
                            i++;
                        } else {
                            return str2;
                        }
                    }
                }
                return NOT_A_SUBTYPE_ID_STR;
            }
        }
        return null;
    }

    private java.util.List<android.util.Pair<java.lang.String, java.lang.String>> loadInputMethodAndSubtypeHistory() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String subtypeHistoryStr = getSubtypeHistoryStr();
        if (android.text.TextUtils.isEmpty(subtypeHistoryStr)) {
            return arrayList;
        }
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SEPARATOR);
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter2 = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SUBTYPE_SEPARATOR);
        simpleStringSplitter.setString(subtypeHistoryStr);
        while (simpleStringSplitter.hasNext()) {
            simpleStringSplitter2.setString(simpleStringSplitter.next());
            if (simpleStringSplitter2.hasNext()) {
                java.lang.String str = NOT_A_SUBTYPE_ID_STR;
                java.lang.String next = simpleStringSplitter2.next();
                if (simpleStringSplitter2.hasNext()) {
                    str = simpleStringSplitter2.next();
                }
                arrayList.add(new android.util.Pair(next, str));
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private java.lang.String getSubtypeHistoryStr() {
        return getString("input_methods_subtype_history", "");
    }

    void putSelectedInputMethod(java.lang.String str) {
        putString("default_input_method", str);
    }

    void putSelectedSubtype(int i) {
        putInt("selected_input_method_subtype", i);
    }

    @android.annotation.Nullable
    java.lang.String getSelectedInputMethod() {
        return getString("default_input_method", null);
    }

    @android.annotation.Nullable
    java.lang.String getSelectedDefaultDeviceInputMethod() {
        return getString("default_device_input_method", null);
    }

    void putSelectedDefaultDeviceInputMethod(java.lang.String str) {
        putString("default_device_input_method", str);
    }

    void putDefaultVoiceInputMethod(java.lang.String str) {
        putString("default_voice_input_method", str);
    }

    @android.annotation.Nullable
    java.lang.String getDefaultVoiceInputMethod() {
        return getString("default_voice_input_method", null);
    }

    boolean isSubtypeSelected() {
        return getSelectedInputMethodSubtypeHashCode() != -1;
    }

    private int getSelectedInputMethodSubtypeHashCode() {
        return getInt("selected_input_method_subtype", -1);
    }

    public int getUserId() {
        return this.mUserId;
    }

    int getSelectedInputMethodSubtypeId(java.lang.String str) {
        android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mMethodMap.get(str);
        if (inputMethodInfo == null) {
            return -1;
        }
        return com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, getSelectedInputMethodSubtypeHashCode());
    }

    void saveCurrentInputMethodAndSubtypeToHistory(java.lang.String str, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        java.lang.String str2 = NOT_A_SUBTYPE_ID_STR;
        if (inputMethodSubtype != null) {
            str2 = java.lang.String.valueOf(inputMethodSubtype.hashCode());
        }
        if (com.android.server.inputmethod.InputMethodUtils.canAddToLastInputMethod(inputMethodSubtype)) {
            addSubtypeToHistory(str, str2);
        }
    }

    @android.annotation.Nullable
    android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtypeForNonCurrentUsers() {
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        int subtypeIdFromHashCode;
        java.lang.String selectedInputMethod = getSelectedInputMethod();
        if (selectedInputMethod == null || (inputMethodInfo = this.mMethodMap.get(selectedInputMethod)) == null || inputMethodInfo.getSubtypeCount() == 0) {
            return null;
        }
        int selectedInputMethodSubtypeHashCode = getSelectedInputMethodSubtypeHashCode();
        if (selectedInputMethodSubtypeHashCode != -1 && (subtypeIdFromHashCode = com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, selectedInputMethodSubtypeHashCode)) >= 0) {
            return inputMethodInfo.getSubtypeAt(subtypeIdFromHashCode);
        }
        java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = getEnabledInputMethodSubtypeList(inputMethodInfo, true);
        if (enabledInputMethodSubtypeList.isEmpty()) {
            return null;
        }
        if (enabledInputMethodSubtypeList.size() == 1) {
            return enabledInputMethodSubtypeList.get(0);
        }
        java.lang.String obj = com.android.server.inputmethod.SystemLocaleWrapper.get(this.mUserId).get(0).toString();
        android.view.inputmethod.InputMethodSubtype findLastResortApplicableSubtype = com.android.server.inputmethod.SubtypeUtils.findLastResortApplicableSubtype(enabledInputMethodSubtypeList, "keyboard", obj, true);
        if (findLastResortApplicableSubtype != null) {
            return findLastResortApplicableSubtype;
        }
        return com.android.server.inputmethod.SubtypeUtils.findLastResortApplicableSubtype(enabledInputMethodSubtypeList, null, obj, true);
    }

    @android.annotation.NonNull
    com.android.server.inputmethod.AdditionalSubtypeMap getNewAdditionalSubtypeMap(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> arrayList, @android.annotation.NonNull com.android.server.inputmethod.AdditionalSubtypeMap additionalSubtypeMap, @android.annotation.NonNull android.content.pm.PackageManagerInternal packageManagerInternal, int i) {
        android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mMethodMap.get(str);
        if (inputMethodInfo == null) {
            return additionalSubtypeMap;
        }
        if (!com.android.server.inputmethod.InputMethodUtils.checkIfPackageBelongsToUid(packageManagerInternal, i, inputMethodInfo.getPackageName())) {
            return additionalSubtypeMap;
        }
        if (arrayList.isEmpty()) {
            return additionalSubtypeMap.cloneWithRemoveOrSelf(inputMethodInfo.getId());
        }
        return additionalSubtypeMap.cloneWithPut(inputMethodInfo.getId(), arrayList);
    }

    boolean setEnabledInputMethodSubtypes(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr) {
        android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mMethodMap.get(str);
        if (inputMethodInfo == null) {
            return false;
        }
        android.util.IntArray intArray = new android.util.IntArray(iArr.length);
        for (int i : iArr) {
            if (i != -1 && com.android.server.inputmethod.SubtypeUtils.isValidSubtypeId(inputMethodInfo, i) && intArray.indexOf(i) < 0) {
                intArray.add(i);
            }
        }
        java.lang.String enabledInputMethodsStr = getEnabledInputMethodsStr();
        java.lang.String updateEnabledImeString = updateEnabledImeString(enabledInputMethodsStr, inputMethodInfo.getId(), intArray);
        if (android.text.TextUtils.equals(enabledInputMethodsStr, updateEnabledImeString)) {
            return false;
        }
        putEnabledInputMethodsStr(updateEnabledImeString);
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static java.lang.String updateEnabledImeString(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.util.IntArray intArray) {
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SEPARATOR);
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter2 = new android.text.TextUtils.SimpleStringSplitter(INPUT_METHOD_SUBTYPE_SEPARATOR);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        simpleStringSplitter.setString(str);
        boolean z = false;
        while (simpleStringSplitter.hasNext()) {
            java.lang.String next = simpleStringSplitter.next();
            simpleStringSplitter2.setString(next);
            if (simpleStringSplitter2.hasNext()) {
                if (z) {
                    sb.append(INPUT_METHOD_SEPARATOR);
                }
                if (android.text.TextUtils.equals(str2, simpleStringSplitter2.next())) {
                    sb.append(str2);
                    for (int i = 0; i < intArray.size(); i++) {
                        sb.append(INPUT_METHOD_SUBTYPE_SEPARATOR);
                        sb.append(intArray.get(i));
                    }
                } else {
                    sb.append(next);
                }
                z = true;
            }
        }
        return sb.toString();
    }

    void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + "mUserId=" + this.mUserId);
    }
}
