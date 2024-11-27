package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public interface UrlInterceptHandler {
    @java.lang.Deprecated
    android.webkit.PluginData getPluginData(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map);

    @java.lang.Deprecated
    android.webkit.CacheManager.CacheResult service(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map);
}
