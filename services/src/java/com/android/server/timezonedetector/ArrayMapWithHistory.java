package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class ArrayMapWithHistory<K, V> {
    private static final java.lang.String TAG = "ArrayMapWithHistory";

    @android.annotation.Nullable
    private android.util.ArrayMap<K, com.android.server.timezonedetector.ReferenceWithHistory<V>> mMap;
    private final int mMaxHistorySize;

    public ArrayMapWithHistory(int i) {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("maxHistorySize < 1: " + i);
        }
        this.mMaxHistorySize = i;
    }

    @android.annotation.Nullable
    public V put(@android.annotation.Nullable K k, @android.annotation.Nullable V v) {
        if (this.mMap == null) {
            this.mMap = new android.util.ArrayMap<>();
        }
        com.android.server.timezonedetector.ReferenceWithHistory<V> referenceWithHistory = this.mMap.get(k);
        if (referenceWithHistory == null) {
            referenceWithHistory = new com.android.server.timezonedetector.ReferenceWithHistory<>(this.mMaxHistorySize);
            this.mMap.put(k, referenceWithHistory);
        } else if (referenceWithHistory.getHistoryCount() == 0) {
            android.util.Log.w(TAG, "History for \"" + k + "\" was unexpectedly empty");
        }
        return referenceWithHistory.set(v);
    }

    @android.annotation.Nullable
    public V get(@android.annotation.Nullable java.lang.Object obj) {
        com.android.server.timezonedetector.ReferenceWithHistory<V> referenceWithHistory;
        if (this.mMap == null || (referenceWithHistory = this.mMap.get(obj)) == null) {
            return null;
        }
        if (referenceWithHistory.getHistoryCount() == 0) {
            android.util.Log.w(TAG, "History for \"" + obj + "\" was unexpectedly empty");
        }
        return referenceWithHistory.get();
    }

    public int size() {
        if (this.mMap == null) {
            return 0;
        }
        return this.mMap.size();
    }

    @android.annotation.Nullable
    public K keyAt(int i) {
        if (this.mMap == null) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return this.mMap.keyAt(i);
    }

    @android.annotation.Nullable
    public V valueAt(int i) {
        if (this.mMap == null) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        com.android.server.timezonedetector.ReferenceWithHistory<V> valueAt = this.mMap.valueAt(i);
        if (valueAt == null || valueAt.getHistoryCount() == 0) {
            android.util.Log.w(TAG, "valueAt(" + i + ") was unexpectedly null or empty");
            return null;
        }
        return valueAt.get();
    }

    public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        if (this.mMap == null) {
            indentingPrintWriter.println("{Empty}");
        } else {
            for (int i = 0; i < this.mMap.size(); i++) {
                indentingPrintWriter.println("key idx: " + i + "=" + this.mMap.keyAt(i));
                com.android.server.timezonedetector.ReferenceWithHistory<V> valueAt = this.mMap.valueAt(i);
                indentingPrintWriter.println("val idx: " + i + "=" + valueAt);
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Historic values=[");
                indentingPrintWriter.increaseIndent();
                valueAt.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("]");
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.flush();
    }

    @com.android.internal.annotations.VisibleForTesting
    public int getHistoryCountForKeyForTests(@android.annotation.Nullable K k) {
        com.android.server.timezonedetector.ReferenceWithHistory<V> referenceWithHistory;
        if (this.mMap == null || (referenceWithHistory = this.mMap.get(k)) == null) {
            return 0;
        }
        if (referenceWithHistory.getHistoryCount() == 0) {
            android.util.Log.w(TAG, "getValuesSizeForKeyForTests(\"" + k + "\") was unexpectedly empty");
            return 0;
        }
        return referenceWithHistory.getHistoryCount();
    }

    public java.lang.String toString() {
        return "ArrayMapWithHistory{mHistorySize=" + this.mMaxHistorySize + ", mMap=" + this.mMap + '}';
    }
}
