package com.android.server.pm;

/* loaded from: classes2.dex */
final class MoveInfo {
    final int mAppId;
    final java.lang.String mFromCodePath;
    final java.lang.String mFromUuid;
    final int mMoveId;
    final java.lang.String mPackageName;
    final java.lang.String mSeInfo;
    final int mTargetSdkVersion;
    final java.lang.String mToUuid;

    MoveInfo(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, java.lang.String str4, int i3, java.lang.String str5) {
        this.mMoveId = i;
        this.mFromUuid = str;
        this.mToUuid = str2;
        this.mPackageName = str3;
        this.mAppId = i2;
        this.mSeInfo = str4;
        this.mTargetSdkVersion = i3;
        this.mFromCodePath = str5;
    }
}
