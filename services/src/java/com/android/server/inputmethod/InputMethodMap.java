package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputMethodMap {
    private static final android.util.ArrayMap<java.lang.String, android.view.inputmethod.InputMethodInfo> EMPTY_MAP = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, android.view.inputmethod.InputMethodInfo> mMap;

    static com.android.server.inputmethod.InputMethodMap emptyMap() {
        return new com.android.server.inputmethod.InputMethodMap(EMPTY_MAP);
    }

    static com.android.server.inputmethod.InputMethodMap of(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.view.inputmethod.InputMethodInfo> arrayMap) {
        return new com.android.server.inputmethod.InputMethodMap(arrayMap);
    }

    private InputMethodMap(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.view.inputmethod.InputMethodInfo> arrayMap) {
        this.mMap = arrayMap.isEmpty() ? EMPTY_MAP : new android.util.ArrayMap<>(arrayMap);
    }

    @android.annotation.Nullable
    android.view.inputmethod.InputMethodInfo get(@android.annotation.Nullable java.lang.String str) {
        return this.mMap.get(str);
    }

    @android.annotation.NonNull
    java.util.List<android.view.inputmethod.InputMethodInfo> values() {
        return java.util.List.copyOf(this.mMap.values());
    }

    @android.annotation.Nullable
    android.view.inputmethod.InputMethodInfo valueAt(int i) {
        return this.mMap.valueAt(i);
    }

    boolean containsKey(@android.annotation.Nullable java.lang.String str) {
        return this.mMap.containsKey(str);
    }

    int size() {
        return this.mMap.size();
    }
}
