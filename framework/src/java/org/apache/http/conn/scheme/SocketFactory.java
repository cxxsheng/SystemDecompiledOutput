package org.apache.http.conn.scheme;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public interface SocketFactory {
    java.net.Socket connectSocket(java.net.Socket socket, java.lang.String str, int i, java.net.InetAddress inetAddress, int i2, org.apache.http.params.HttpParams httpParams) throws java.io.IOException, java.net.UnknownHostException, org.apache.http.conn.ConnectTimeoutException;

    java.net.Socket createSocket() throws java.io.IOException;

    boolean isSecure(java.net.Socket socket) throws java.lang.IllegalArgumentException;
}
