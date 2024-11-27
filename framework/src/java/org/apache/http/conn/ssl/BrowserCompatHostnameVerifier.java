package org.apache.http.conn.ssl;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public class BrowserCompatHostnameVerifier extends org.apache.http.conn.ssl.AbstractVerifier {
    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2) throws javax.net.ssl.SSLException {
        verify(str, strArr, strArr2, false);
    }

    public final java.lang.String toString() {
        return "BROWSER_COMPATIBLE";
    }
}
