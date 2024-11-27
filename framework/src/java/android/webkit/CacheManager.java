package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public final class CacheManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    @java.lang.Deprecated
    public static class CacheResult {
        long contentLength;
        java.lang.String contentdisposition;
        java.lang.String crossDomain;
        java.lang.String encoding;
        java.lang.String etag;
        long expires;
        java.lang.String expiresString;
        int httpStatusCode;
        java.io.InputStream inStream;
        java.lang.String lastModified;
        java.lang.String localPath;
        java.lang.String location;
        java.lang.String mimeType;
        java.io.File outFile;
        java.io.OutputStream outStream;

        public int getHttpStatusCode() {
            return this.httpStatusCode;
        }

        public long getContentLength() {
            return this.contentLength;
        }

        public java.lang.String getLocalPath() {
            return this.localPath;
        }

        public long getExpires() {
            return this.expires;
        }

        public java.lang.String getExpiresString() {
            return this.expiresString;
        }

        public java.lang.String getLastModified() {
            return this.lastModified;
        }

        public java.lang.String getETag() {
            return this.etag;
        }

        public java.lang.String getMimeType() {
            return this.mimeType;
        }

        public java.lang.String getLocation() {
            return this.location;
        }

        public java.lang.String getEncoding() {
            return this.encoding;
        }

        public java.lang.String getContentDisposition() {
            return this.contentdisposition;
        }

        public java.io.InputStream getInputStream() {
            return this.inStream;
        }

        public java.io.OutputStream getOutputStream() {
            return this.outStream;
        }

        public void setInputStream(java.io.InputStream inputStream) {
            this.inStream = inputStream;
        }

        public void setEncoding(java.lang.String str) {
            this.encoding = str;
        }

        public void setContentLength(long j) {
            this.contentLength = j;
        }
    }

    @java.lang.Deprecated
    public static java.io.File getCacheFileBaseDir() {
        return null;
    }

    @java.lang.Deprecated
    public static boolean cacheDisabled() {
        return false;
    }

    @java.lang.Deprecated
    public static boolean startCacheTransaction() {
        return false;
    }

    @java.lang.Deprecated
    public static boolean endCacheTransaction() {
        return false;
    }

    @java.lang.Deprecated
    public static android.webkit.CacheManager.CacheResult getCacheFile(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) {
        return null;
    }

    @java.lang.Deprecated
    public static void saveCacheFile(java.lang.String str, android.webkit.CacheManager.CacheResult cacheResult) {
        saveCacheFile(str, 0L, cacheResult);
    }

    static void saveCacheFile(java.lang.String str, long j, android.webkit.CacheManager.CacheResult cacheResult) {
        try {
            cacheResult.outStream.close();
        } catch (java.io.IOException e) {
        }
    }
}
