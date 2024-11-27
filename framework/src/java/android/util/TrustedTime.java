package android.util;

/* loaded from: classes3.dex */
public interface TrustedTime {
    @java.lang.Deprecated
    long currentTimeMillis();

    @java.lang.Deprecated
    boolean forceRefresh();

    @java.lang.Deprecated
    long getCacheAge();

    @java.lang.Deprecated
    boolean hasCache();
}
