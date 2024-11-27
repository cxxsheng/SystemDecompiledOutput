package android.app;

/* loaded from: classes.dex */
class AppOpInfo {
    public final android.app.AppOpsManager.RestrictionBypass allowSystemRestrictionBypass;
    public final int code;
    public final int defaultMode;
    public final boolean disableReset;
    public final boolean forceCollectNotes;
    public final java.lang.String name;
    public final java.lang.String permission;
    public final boolean restrictRead;
    public final java.lang.String restriction;
    public final java.lang.String simpleName;
    public final int switchCode;

    AppOpInfo(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.app.AppOpsManager.RestrictionBypass restrictionBypass, int i3, boolean z, boolean z2, boolean z3) {
        if (i < -1) {
            throw new java.lang.IllegalArgumentException();
        }
        if (i2 < -1) {
            throw new java.lang.IllegalArgumentException();
        }
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        this.code = i;
        this.switchCode = i2;
        this.name = str;
        this.simpleName = str2;
        this.permission = str3;
        this.restriction = str4;
        this.allowSystemRestrictionBypass = restrictionBypass;
        this.defaultMode = i3;
        this.disableReset = z;
        this.restrictRead = z2;
        this.forceCollectNotes = z3;
    }

    static class Builder {
        private int mCode;
        private java.lang.String mName;
        private java.lang.String mSimpleName;
        private int mSwitchCode;
        private java.lang.String mPermission = null;
        private java.lang.String mRestriction = null;
        private android.app.AppOpsManager.RestrictionBypass mAllowSystemRestrictionBypass = null;
        private int mDefaultMode = 3;
        private boolean mDisableReset = false;
        private boolean mRestrictRead = false;
        private boolean mForceCollectNotes = false;

        Builder(int i, java.lang.String str, java.lang.String str2) {
            if (i < -1) {
                throw new java.lang.IllegalArgumentException();
            }
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(str2);
            this.mCode = i;
            this.mSwitchCode = i;
            this.mName = str;
            this.mSimpleName = str2;
        }

        public android.app.AppOpInfo.Builder setCode(int i) {
            this.mCode = i;
            return this;
        }

        public android.app.AppOpInfo.Builder setSwitchCode(int i) {
            this.mSwitchCode = i;
            return this;
        }

        public android.app.AppOpInfo.Builder setName(java.lang.String str) {
            this.mName = str;
            return this;
        }

        public android.app.AppOpInfo.Builder setSimpleName(java.lang.String str) {
            this.mSimpleName = str;
            return this;
        }

        public android.app.AppOpInfo.Builder setPermission(java.lang.String str) {
            this.mPermission = str;
            return this;
        }

        public android.app.AppOpInfo.Builder setRestriction(java.lang.String str) {
            this.mRestriction = str;
            return this;
        }

        public android.app.AppOpInfo.Builder setAllowSystemRestrictionBypass(android.app.AppOpsManager.RestrictionBypass restrictionBypass) {
            this.mAllowSystemRestrictionBypass = restrictionBypass;
            return this;
        }

        public android.app.AppOpInfo.Builder setDefaultMode(int i) {
            this.mDefaultMode = i;
            return this;
        }

        public android.app.AppOpInfo.Builder setDisableReset(boolean z) {
            this.mDisableReset = z;
            return this;
        }

        public android.app.AppOpInfo.Builder setRestrictRead(boolean z) {
            this.mRestrictRead = z;
            return this;
        }

        public android.app.AppOpInfo.Builder setForceCollectNotes(boolean z) {
            this.mForceCollectNotes = z;
            return this;
        }

        public android.app.AppOpInfo build() {
            return new android.app.AppOpInfo(this.mCode, this.mSwitchCode, this.mName, this.mSimpleName, this.mPermission, this.mRestriction, this.mAllowSystemRestrictionBypass, this.mDefaultMode, this.mDisableReset, this.mRestrictRead, this.mForceCollectNotes);
        }
    }
}
