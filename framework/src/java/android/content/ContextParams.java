package android.content;

/* loaded from: classes.dex */
public final class ContextParams {
    public static final android.content.ContextParams EMPTY = new android.content.ContextParams.Builder().build();
    private final java.lang.String mAttributionTag;
    private final android.content.AttributionSource mNext;
    private final java.util.Set<java.lang.String> mRenouncedPermissions;
    private final boolean mShouldRegisterAttributionSource;

    private ContextParams(java.lang.String str, android.content.AttributionSource attributionSource, java.util.Set<java.lang.String> set, boolean z) {
        this.mAttributionTag = str;
        this.mNext = attributionSource;
        this.mRenouncedPermissions = set == null ? java.util.Collections.emptySet() : set;
        this.mShouldRegisterAttributionSource = z;
    }

    public java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> getRenouncedPermissions() {
        return this.mRenouncedPermissions;
    }

    public boolean isRenouncedPermission(java.lang.String str) {
        return this.mRenouncedPermissions.contains(str);
    }

    public android.content.AttributionSource getNextAttributionSource() {
        return this.mNext;
    }

    public boolean shouldRegisterAttributionSource() {
        return this.mShouldRegisterAttributionSource;
    }

    public static final class Builder {
        private java.lang.String mAttributionTag;
        private android.content.AttributionSource mNext;
        private java.util.Set<java.lang.String> mRenouncedPermissions;
        private boolean mShouldRegisterAttributionSource;

        public Builder() {
            this.mRenouncedPermissions = java.util.Collections.emptySet();
        }

        public Builder(android.content.ContextParams contextParams) {
            this.mRenouncedPermissions = java.util.Collections.emptySet();
            java.util.Objects.requireNonNull(contextParams);
            this.mAttributionTag = contextParams.mAttributionTag;
            this.mRenouncedPermissions = contextParams.mRenouncedPermissions;
            this.mNext = contextParams.mNext;
        }

        public android.content.ContextParams.Builder setAttributionTag(java.lang.String str) {
            this.mAttributionTag = str;
            return this;
        }

        public android.content.ContextParams.Builder setNextAttributionSource(android.content.AttributionSource attributionSource) {
            this.mNext = attributionSource;
            return this;
        }

        public android.content.ContextParams.Builder setShouldRegisterAttributionSource(boolean z) {
            this.mShouldRegisterAttributionSource = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.content.ContextParams.Builder setRenouncedPermissions(java.util.Set<java.lang.String> set) {
            if (set != null && !set.isEmpty() && android.app.ActivityThread.currentApplication().checkSelfPermission(android.Manifest.permission.RENOUNCE_PERMISSIONS) != 0) {
                throw new java.lang.SecurityException("Renouncing permissions requires: android.permission.RENOUNCE_PERMISSIONS");
            }
            this.mRenouncedPermissions = set;
            return this;
        }

        public android.content.ContextParams build() {
            return new android.content.ContextParams(this.mAttributionTag, this.mNext, this.mRenouncedPermissions, this.mShouldRegisterAttributionSource);
        }
    }
}
