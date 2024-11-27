package android.webkit;

/* loaded from: classes4.dex */
public class WebStorage {

    @java.lang.Deprecated
    public interface QuotaUpdater {
        void updateQuota(long j);
    }

    public static class Origin {
        private java.lang.String mOrigin;
        private long mQuota;
        private long mUsage;

        @android.annotation.SystemApi
        protected Origin(java.lang.String str, long j, long j2) {
            this.mOrigin = null;
            this.mQuota = 0L;
            this.mUsage = 0L;
            this.mOrigin = str;
            this.mQuota = j;
            this.mUsage = j2;
        }

        public java.lang.String getOrigin() {
            return this.mOrigin;
        }

        public long getQuota() {
            return this.mQuota;
        }

        public long getUsage() {
            return this.mUsage;
        }
    }

    public void getOrigins(android.webkit.ValueCallback<java.util.Map> valueCallback) {
    }

    public void getUsageForOrigin(java.lang.String str, android.webkit.ValueCallback<java.lang.Long> valueCallback) {
    }

    public void getQuotaForOrigin(java.lang.String str, android.webkit.ValueCallback<java.lang.Long> valueCallback) {
    }

    @java.lang.Deprecated
    public void setQuotaForOrigin(java.lang.String str, long j) {
    }

    public void deleteOrigin(java.lang.String str) {
    }

    public void deleteAllData() {
    }

    public static android.webkit.WebStorage getInstance() {
        return android.webkit.WebViewFactory.getProvider().getWebStorage();
    }

    @android.annotation.SystemApi
    public WebStorage() {
    }
}
