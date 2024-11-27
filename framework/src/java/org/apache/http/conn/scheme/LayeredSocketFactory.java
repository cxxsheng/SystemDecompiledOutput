package org.apache.http.conn.scheme;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public interface LayeredSocketFactory extends org.apache.http.conn.scheme.SocketFactory {
    java.net.Socket createSocket(java.net.Socket socket, java.lang.String str, int i, boolean z) throws java.io.IOException, java.net.UnknownHostException;
}
