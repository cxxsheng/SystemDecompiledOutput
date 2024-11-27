package com.android.server.pm.verify.domain.models;

/* loaded from: classes2.dex */
public class DomainVerificationStateMap<ValueType> {
    private static final java.lang.String TAG = "DomainVerificationStateMap";

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, ValueType> mPackageNameMap = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.util.UUID, ValueType> mDomainSetIdMap = new android.util.ArrayMap<>();

    public int size() {
        return this.mPackageNameMap.size();
    }

    @android.annotation.NonNull
    public ValueType valueAt(int i) {
        return this.mPackageNameMap.valueAt(i);
    }

    @android.annotation.Nullable
    public ValueType get(@android.annotation.NonNull java.lang.String str) {
        return this.mPackageNameMap.get(str);
    }

    @android.annotation.Nullable
    public ValueType get(@android.annotation.NonNull java.util.UUID uuid) {
        return this.mDomainSetIdMap.get(uuid);
    }

    public void put(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.UUID uuid, @android.annotation.NonNull ValueType valuetype) {
        if (this.mPackageNameMap.containsKey(str)) {
            remove(str);
        }
        this.mPackageNameMap.put(str, valuetype);
        this.mDomainSetIdMap.put(uuid, valuetype);
    }

    @android.annotation.Nullable
    public ValueType remove(@android.annotation.NonNull java.lang.String str) {
        int indexOfValue;
        ValueType remove = this.mPackageNameMap.remove(str);
        if (remove != null && (indexOfValue = this.mDomainSetIdMap.indexOfValue(remove)) >= 0) {
            this.mDomainSetIdMap.removeAt(indexOfValue);
        }
        return remove;
    }

    @android.annotation.Nullable
    public ValueType remove(@android.annotation.NonNull java.util.UUID uuid) {
        int indexOfValue;
        ValueType remove = this.mDomainSetIdMap.remove(uuid);
        if (remove != null && (indexOfValue = this.mPackageNameMap.indexOfValue(remove)) >= 0) {
            this.mPackageNameMap.removeAt(indexOfValue);
        }
        return remove;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> getPackageNames() {
        return new java.util.ArrayList(this.mPackageNameMap.keySet());
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.util.Collection<ValueType> values() {
        return new java.util.ArrayList(this.mPackageNameMap.values());
    }

    public java.lang.String toString() {
        return "DomainVerificationStateMap{packageNameMap=" + this.mPackageNameMap + ", domainSetIdMap=" + this.mDomainSetIdMap + '}';
    }
}
