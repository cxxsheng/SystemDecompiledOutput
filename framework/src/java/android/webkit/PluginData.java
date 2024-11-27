package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public final class PluginData {
    private long mContentLength;
    private java.util.Map<java.lang.String, java.lang.String[]> mHeaders;
    private int mStatusCode;
    private java.io.InputStream mStream;

    @java.lang.Deprecated
    public PluginData(java.io.InputStream inputStream, long j, java.util.Map<java.lang.String, java.lang.String[]> map, int i) {
        this.mStream = inputStream;
        this.mContentLength = j;
        this.mHeaders = map;
        this.mStatusCode = i;
    }

    @java.lang.Deprecated
    public java.io.InputStream getInputStream() {
        return this.mStream;
    }

    @java.lang.Deprecated
    public long getContentLength() {
        return this.mContentLength;
    }

    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String[]> getHeaders() {
        return this.mHeaders;
    }

    @java.lang.Deprecated
    public int getStatusCode() {
        return this.mStatusCode;
    }
}
