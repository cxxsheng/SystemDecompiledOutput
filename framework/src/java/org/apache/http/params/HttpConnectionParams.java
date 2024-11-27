package org.apache.http.params;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public final class HttpConnectionParams implements org.apache.http.params.CoreConnectionPNames {
    private HttpConnectionParams() {
    }

    public static int getSoTimeout(org.apache.http.params.HttpParams httpParams) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT, 0);
    }

    public static void setSoTimeout(org.apache.http.params.HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT, i);
    }

    public static boolean getTcpNoDelay(org.apache.http.params.HttpParams httpParams) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getBooleanParameter(org.apache.http.params.CoreConnectionPNames.TCP_NODELAY, true);
    }

    public static void setTcpNoDelay(org.apache.http.params.HttpParams httpParams, boolean z) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setBooleanParameter(org.apache.http.params.CoreConnectionPNames.TCP_NODELAY, z);
    }

    public static int getSocketBufferSize(org.apache.http.params.HttpParams httpParams) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(org.apache.http.params.CoreConnectionPNames.SOCKET_BUFFER_SIZE, -1);
    }

    public static void setSocketBufferSize(org.apache.http.params.HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(org.apache.http.params.CoreConnectionPNames.SOCKET_BUFFER_SIZE, i);
    }

    public static int getLinger(org.apache.http.params.HttpParams httpParams) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(org.apache.http.params.CoreConnectionPNames.SO_LINGER, -1);
    }

    public static void setLinger(org.apache.http.params.HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(org.apache.http.params.CoreConnectionPNames.SO_LINGER, i);
    }

    public static int getConnectionTimeout(org.apache.http.params.HttpParams httpParams) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT, 0);
    }

    public static void setConnectionTimeout(org.apache.http.params.HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT, i);
    }

    public static boolean isStaleCheckingEnabled(org.apache.http.params.HttpParams httpParams) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getBooleanParameter(org.apache.http.params.CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
    }

    public static void setStaleCheckingEnabled(org.apache.http.params.HttpParams httpParams, boolean z) {
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setBooleanParameter(org.apache.http.params.CoreConnectionPNames.STALE_CONNECTION_CHECK, z);
    }
}
