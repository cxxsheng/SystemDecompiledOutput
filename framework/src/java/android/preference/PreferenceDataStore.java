package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface PreferenceDataStore {
    default void putString(java.lang.String str, java.lang.String str2) {
        throw new java.lang.UnsupportedOperationException("Not implemented on this data store");
    }

    default void putStringSet(java.lang.String str, java.util.Set<java.lang.String> set) {
        throw new java.lang.UnsupportedOperationException("Not implemented on this data store");
    }

    default void putInt(java.lang.String str, int i) {
        throw new java.lang.UnsupportedOperationException("Not implemented on this data store");
    }

    default void putLong(java.lang.String str, long j) {
        throw new java.lang.UnsupportedOperationException("Not implemented on this data store");
    }

    default void putFloat(java.lang.String str, float f) {
        throw new java.lang.UnsupportedOperationException("Not implemented on this data store");
    }

    default void putBoolean(java.lang.String str, boolean z) {
        throw new java.lang.UnsupportedOperationException("Not implemented on this data store");
    }

    default java.lang.String getString(java.lang.String str, java.lang.String str2) {
        return str2;
    }

    default java.util.Set<java.lang.String> getStringSet(java.lang.String str, java.util.Set<java.lang.String> set) {
        return set;
    }

    default int getInt(java.lang.String str, int i) {
        return i;
    }

    default long getLong(java.lang.String str, long j) {
        return j;
    }

    default float getFloat(java.lang.String str, float f) {
        return f;
    }

    default boolean getBoolean(java.lang.String str, boolean z) {
        return z;
    }
}
