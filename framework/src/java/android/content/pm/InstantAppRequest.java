package android.content.pm;

/* loaded from: classes.dex */
public final class InstantAppRequest {
    public final java.lang.String callingFeatureId;
    public final java.lang.String callingPackage;
    public final int[] hostDigestPrefixSecure;
    public final boolean isRequesterInstantApp;
    public final android.content.Intent origIntent;
    public final boolean resolveForStart;
    public final java.lang.String resolvedType;
    public final android.content.pm.AuxiliaryResolveInfo responseObj;
    public final java.lang.String token;
    public final int userId;
    public final android.os.Bundle verificationBundle;

    public InstantAppRequest(android.content.pm.AuxiliaryResolveInfo auxiliaryResolveInfo, android.content.Intent intent, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, int i, android.os.Bundle bundle, boolean z2, int[] iArr, java.lang.String str4) {
        this.responseObj = auxiliaryResolveInfo;
        this.origIntent = intent;
        this.resolvedType = str;
        this.callingPackage = str2;
        this.callingFeatureId = str3;
        this.isRequesterInstantApp = z;
        this.userId = i;
        this.verificationBundle = bundle;
        this.resolveForStart = z2;
        this.hostDigestPrefixSecure = iArr;
        this.token = str4;
    }
}
