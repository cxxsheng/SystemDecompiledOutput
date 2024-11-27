package com.android.server.pm;

/* loaded from: classes2.dex */
final class VerificationInfo {
    final int mInstallerUid;
    final int mOriginatingUid;
    final android.net.Uri mOriginatingUri;
    final android.net.Uri mReferrer;

    VerificationInfo(android.net.Uri uri, android.net.Uri uri2, int i, int i2) {
        this.mOriginatingUri = uri;
        this.mReferrer = uri2;
        this.mOriginatingUid = i;
        this.mInstallerUid = i2;
    }
}
