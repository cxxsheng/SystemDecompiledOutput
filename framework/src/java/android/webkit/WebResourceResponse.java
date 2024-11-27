package android.webkit;

/* loaded from: classes4.dex */
public class WebResourceResponse {
    private java.lang.String mEncoding;
    private boolean mImmutable;
    private java.io.InputStream mInputStream;
    private java.lang.String mMimeType;
    private java.lang.String mReasonPhrase;
    private java.util.Map<java.lang.String, java.lang.String> mResponseHeaders;
    private int mStatusCode;

    public WebResourceResponse(java.lang.String str, java.lang.String str2, java.io.InputStream inputStream) {
        this.mMimeType = str;
        this.mEncoding = str2;
        setData(inputStream);
    }

    public WebResourceResponse(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.util.Map<java.lang.String, java.lang.String> map, java.io.InputStream inputStream) {
        this(str, str2, inputStream);
        setStatusCodeAndReasonPhrase(i, str3);
        setResponseHeaders(map);
    }

    public void setMimeType(java.lang.String str) {
        checkImmutable();
        this.mMimeType = str;
    }

    public java.lang.String getMimeType() {
        return this.mMimeType;
    }

    public void setEncoding(java.lang.String str) {
        checkImmutable();
        this.mEncoding = str;
    }

    public java.lang.String getEncoding() {
        return this.mEncoding;
    }

    public void setStatusCodeAndReasonPhrase(int i, java.lang.String str) {
        checkImmutable();
        if (i < 100) {
            throw new java.lang.IllegalArgumentException("statusCode can't be less than 100.");
        }
        if (i > 599) {
            throw new java.lang.IllegalArgumentException("statusCode can't be greater than 599.");
        }
        if (i > 299 && i < 400) {
            throw new java.lang.IllegalArgumentException("statusCode can't be in the [300, 399] range.");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("reasonPhrase can't be null.");
        }
        if (str.trim().isEmpty()) {
            throw new java.lang.IllegalArgumentException("reasonPhrase can't be empty.");
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) > 127) {
                throw new java.lang.IllegalArgumentException("reasonPhrase can't contain non-ASCII characters.");
            }
        }
        this.mStatusCode = i;
        this.mReasonPhrase = str;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public java.lang.String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    public void setResponseHeaders(java.util.Map<java.lang.String, java.lang.String> map) {
        checkImmutable();
        this.mResponseHeaders = map;
    }

    public java.util.Map<java.lang.String, java.lang.String> getResponseHeaders() {
        return this.mResponseHeaders;
    }

    public void setData(java.io.InputStream inputStream) {
        checkImmutable();
        if (inputStream != null && java.io.StringBufferInputStream.class.isAssignableFrom(inputStream.getClass())) {
            throw new java.lang.IllegalArgumentException("StringBufferInputStream is deprecated and must not be passed to a WebResourceResponse");
        }
        this.mInputStream = inputStream;
    }

    public java.io.InputStream getData() {
        return this.mInputStream;
    }

    @android.annotation.SystemApi
    public WebResourceResponse(boolean z, java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.util.Map<java.lang.String, java.lang.String> map, java.io.InputStream inputStream) {
        this.mImmutable = z;
        this.mMimeType = str;
        this.mEncoding = str2;
        this.mStatusCode = i;
        this.mReasonPhrase = str3;
        this.mResponseHeaders = map;
        this.mInputStream = inputStream;
    }

    private void checkImmutable() {
        if (this.mImmutable) {
            throw new java.lang.IllegalStateException("This WebResourceResponse instance is immutable");
        }
    }
}
