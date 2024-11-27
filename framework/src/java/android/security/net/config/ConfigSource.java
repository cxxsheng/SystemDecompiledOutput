package android.security.net.config;

/* loaded from: classes3.dex */
public interface ConfigSource {
    android.security.net.config.NetworkSecurityConfig getDefaultConfig();

    java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> getPerDomainConfigs();
}
