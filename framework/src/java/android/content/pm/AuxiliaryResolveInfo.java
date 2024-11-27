package android.content.pm;

/* loaded from: classes.dex */
public final class AuxiliaryResolveInfo {
    public final android.content.Intent failureIntent;
    public final java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> filters;
    public final int[] hostDigestPrefixSecure;
    public final android.content.ComponentName installFailureActivity;
    public final boolean needsPhaseTwo;
    public final java.lang.String token;

    public AuxiliaryResolveInfo(java.lang.String str, boolean z, android.content.Intent intent, java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> list, int[] iArr) {
        this.token = str;
        this.needsPhaseTwo = z;
        this.failureIntent = intent;
        this.filters = list;
        this.installFailureActivity = null;
        this.hostDigestPrefixSecure = iArr;
    }

    public AuxiliaryResolveInfo(android.content.ComponentName componentName, android.content.Intent intent, java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> list) {
        this.installFailureActivity = componentName;
        this.filters = list;
        this.token = null;
        this.needsPhaseTwo = false;
        this.failureIntent = intent;
        this.hostDigestPrefixSecure = null;
    }

    public AuxiliaryResolveInfo(android.content.ComponentName componentName, java.lang.String str, long j, java.lang.String str2) {
        this(componentName, null, java.util.Collections.singletonList(new android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter(str, j, str2)));
    }

    public static final class AuxiliaryFilter extends android.content.IntentFilter {
        public final android.os.Bundle extras;
        public final java.lang.String packageName;
        public final android.content.pm.InstantAppResolveInfo resolveInfo;
        public final java.lang.String splitName;
        public final long versionCode;

        public AuxiliaryFilter(android.content.IntentFilter intentFilter, android.content.pm.InstantAppResolveInfo instantAppResolveInfo, java.lang.String str, android.os.Bundle bundle) {
            super(intentFilter);
            this.resolveInfo = instantAppResolveInfo;
            this.packageName = instantAppResolveInfo.getPackageName();
            this.versionCode = instantAppResolveInfo.getLongVersionCode();
            this.splitName = str;
            this.extras = bundle;
        }

        public AuxiliaryFilter(android.content.pm.InstantAppResolveInfo instantAppResolveInfo, java.lang.String str, android.os.Bundle bundle) {
            this.resolveInfo = instantAppResolveInfo;
            this.packageName = instantAppResolveInfo.getPackageName();
            this.versionCode = instantAppResolveInfo.getLongVersionCode();
            this.splitName = str;
            this.extras = bundle;
        }

        public AuxiliaryFilter(java.lang.String str, long j, java.lang.String str2) {
            this.resolveInfo = null;
            this.packageName = str;
            this.versionCode = j;
            this.splitName = str2;
            this.extras = null;
        }

        public java.lang.String toString() {
            return "AuxiliaryFilter{packageName='" + this.packageName + android.text.format.DateFormat.QUOTE + ", versionCode=" + this.versionCode + ", splitName='" + this.splitName + android.text.format.DateFormat.QUOTE + '}';
        }
    }
}
