package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class AdditionalSubtypeMap {
    static final com.android.server.inputmethod.AdditionalSubtypeMap EMPTY_MAP = new com.android.server.inputmethod.AdditionalSubtypeMap(new android.util.ArrayMap());

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, java.util.List<android.view.inputmethod.InputMethodSubtype>> mMap;

    @android.annotation.NonNull
    private static com.android.server.inputmethod.AdditionalSubtypeMap createOrEmpty(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.util.List<android.view.inputmethod.InputMethodSubtype>> arrayMap) {
        return arrayMap.isEmpty() ? EMPTY_MAP : new com.android.server.inputmethod.AdditionalSubtypeMap(arrayMap);
    }

    @android.annotation.NonNull
    static com.android.server.inputmethod.AdditionalSubtypeMap of(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.util.List<android.view.inputmethod.InputMethodSubtype>> arrayMap) {
        return createOrEmpty(arrayMap);
    }

    @android.annotation.NonNull
    com.android.server.inputmethod.AdditionalSubtypeMap cloneWithRemoveOrSelf(@android.annotation.NonNull java.lang.String str) {
        if (isEmpty() || !containsKey(str)) {
            return this;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mMap);
        arrayMap.remove(str);
        return createOrEmpty(arrayMap);
    }

    @android.annotation.NonNull
    com.android.server.inputmethod.AdditionalSubtypeMap cloneWithRemoveOrSelf(@android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        if (isEmpty()) {
            return this;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mMap);
        return arrayMap.removeAll(collection) ? createOrEmpty(arrayMap) : this;
    }

    @android.annotation.NonNull
    com.android.server.inputmethod.AdditionalSubtypeMap cloneWithPut(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.util.List<android.view.inputmethod.InputMethodSubtype> list) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mMap);
        arrayMap.put(str, list);
        return new com.android.server.inputmethod.AdditionalSubtypeMap(arrayMap);
    }

    private AdditionalSubtypeMap(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.util.List<android.view.inputmethod.InputMethodSubtype>> arrayMap) {
        this.mMap = arrayMap;
    }

    @android.annotation.Nullable
    java.util.List<android.view.inputmethod.InputMethodSubtype> get(@android.annotation.Nullable java.lang.String str) {
        return this.mMap.get(str);
    }

    boolean containsKey(@android.annotation.Nullable java.lang.String str) {
        return this.mMap.containsKey(str);
    }

    boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    @android.annotation.NonNull
    java.util.Collection<java.lang.String> keySet() {
        return this.mMap.keySet();
    }

    int size() {
        return this.mMap.size();
    }
}
