package android.security.net.config;

/* loaded from: classes3.dex */
public final class TrustAnchor {
    public final java.security.cert.X509Certificate certificate;
    public final boolean overridesPins;

    public TrustAnchor(java.security.cert.X509Certificate x509Certificate, boolean z) {
        if (x509Certificate == null) {
            throw new java.lang.NullPointerException(android.media.tv.interactive.TvInteractiveAppView.BI_INTERACTIVE_APP_KEY_CERTIFICATE);
        }
        this.certificate = x509Certificate;
        this.overridesPins = z;
    }
}
