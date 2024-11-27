package com.android.server.infra;

/* loaded from: classes2.dex */
public interface ServiceNameResolver {

    public interface NameResolverListener {
        void onNameResolved(int i, @android.annotation.Nullable java.lang.String str, boolean z);
    }

    void dumpShort(@android.annotation.NonNull java.io.PrintWriter printWriter);

    void dumpShort(@android.annotation.NonNull java.io.PrintWriter printWriter, int i);

    @android.annotation.Nullable
    java.lang.String getDefaultServiceName(int i);

    default void setOnTemporaryServiceNameChangedCallback(@android.annotation.NonNull com.android.server.infra.ServiceNameResolver.NameResolverListener nameResolverListener) {
    }

    @android.annotation.Nullable
    default java.lang.String[] getDefaultServiceNameList(int i) {
        if (isConfiguredInMultipleMode()) {
            throw new java.lang.UnsupportedOperationException("getting default service list not supported");
        }
        return new java.lang.String[]{getDefaultServiceName(i)};
    }

    default void setServiceNameList(java.util.List<java.lang.String> list, int i) {
    }

    default boolean isConfiguredInMultipleMode() {
        return false;
    }

    @android.annotation.Nullable
    default java.lang.String getServiceName(int i) {
        return getDefaultServiceName(i);
    }

    @android.annotation.Nullable
    default java.lang.String[] getServiceNameList(int i) {
        return getDefaultServiceNameList(i);
    }

    default boolean isTemporary(int i) {
        return false;
    }

    default void setTemporaryService(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        throw new java.lang.UnsupportedOperationException("temporary user not supported");
    }

    default void setTemporaryServices(int i, @android.annotation.NonNull java.lang.String[] strArr, int i2) {
        throw new java.lang.UnsupportedOperationException("temporary user not supported");
    }

    default void resetTemporaryService(int i) {
        throw new java.lang.UnsupportedOperationException("temporary user not supported");
    }

    default boolean setDefaultServiceEnabled(int i, boolean z) {
        throw new java.lang.UnsupportedOperationException("changing default service not supported");
    }

    default boolean isDefaultServiceEnabled(int i) {
        throw new java.lang.UnsupportedOperationException("checking default service not supported");
    }
}
