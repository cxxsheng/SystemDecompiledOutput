package com.android.server.pm;

/* loaded from: classes2.dex */
public final class PackageInstallerHistoricalSession {
    private final int mBridges;
    private final int[] mChildSessionIds;
    private final float mClientProgress;
    private final boolean mCommitted;
    private final long mCommittedMillis;
    private final long mCreatedMillis;
    private final boolean mDestroyed;
    private final int mFds;
    private final java.lang.String mFinalMessage;
    private final int mFinalStatus;
    private final com.android.server.pm.InstallSource mInstallSource;
    private final int mInstallerUid;
    private final java.lang.String mOriginalInstallerPackageName;
    private final int mOriginalInstallerUid;
    private final java.lang.String mPackageName;
    private final java.lang.String mParams;
    private final int mParentSessionId;
    private final boolean mPermissionsManuallyAccepted;
    private final java.lang.String mPreVerifiedDomains;
    private final java.lang.String mPreapprovalDetails;
    private final boolean mPreapprovalRequested;
    private final float mProgress;
    private final boolean mSealed;
    private final boolean mSessionApplied;
    private final int mSessionErrorCode;
    private final java.lang.String mSessionErrorMessage;
    private final boolean mSessionFailed;
    private final boolean mSessionReady;
    private final java.lang.String mStageCid;
    private final java.io.File mStageDir;
    private final boolean mStageDirInUse;
    private final long mUpdatedMillis;
    public final int sessionId;
    public final int userId;

    PackageInstallerHistoricalSession(int i, int i2, int i3, java.lang.String str, com.android.server.pm.InstallSource installSource, int i4, long j, long j2, long j3, java.io.File file, java.lang.String str2, float f, float f2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i5, int i6, int i7, java.lang.String str3, android.content.pm.PackageInstaller.SessionParams sessionParams, int i8, int[] iArr, boolean z7, boolean z8, boolean z9, int i9, java.lang.String str4, android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails, android.content.pm.verify.domain.DomainSet domainSet, java.lang.String str5) {
        java.lang.String str6;
        this.sessionId = i;
        this.userId = i2;
        this.mOriginalInstallerUid = i3;
        this.mOriginalInstallerPackageName = str;
        this.mInstallSource = installSource;
        this.mInstallerUid = i4;
        this.mCreatedMillis = j;
        this.mUpdatedMillis = j2;
        this.mCommittedMillis = j3;
        this.mStageDir = file;
        this.mStageCid = str2;
        this.mClientProgress = f;
        this.mProgress = f2;
        this.mCommitted = z;
        this.mPreapprovalRequested = z2;
        this.mSealed = z3;
        this.mPermissionsManuallyAccepted = z4;
        this.mStageDirInUse = z5;
        this.mDestroyed = z6;
        this.mFds = i5;
        this.mBridges = i6;
        this.mFinalStatus = i7;
        this.mFinalMessage = str3;
        java.io.CharArrayWriter charArrayWriter = new java.io.CharArrayWriter();
        sessionParams.dump(new com.android.internal.util.IndentingPrintWriter(charArrayWriter, "    "));
        this.mParams = charArrayWriter.toString();
        this.mParentSessionId = i8;
        this.mChildSessionIds = iArr;
        this.mSessionApplied = z7;
        this.mSessionFailed = z8;
        this.mSessionReady = z9;
        this.mSessionErrorCode = i9;
        this.mSessionErrorMessage = str4;
        if (preapprovalDetails != null) {
            this.mPreapprovalDetails = preapprovalDetails.toString();
        } else {
            this.mPreapprovalDetails = null;
        }
        if (domainSet != null) {
            this.mPreVerifiedDomains = java.lang.String.join(",", domainSet.getDomains());
        } else {
            this.mPreVerifiedDomains = null;
        }
        if (preapprovalDetails != null) {
            str6 = preapprovalDetails.getPackageName();
        } else {
            str6 = str5 != null ? str5 : sessionParams.appPackageName;
        }
        this.mPackageName = str6;
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Session " + this.sessionId + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("userId", java.lang.Integer.valueOf(this.userId));
        indentingPrintWriter.printPair("mOriginalInstallerUid", java.lang.Integer.valueOf(this.mOriginalInstallerUid));
        indentingPrintWriter.printPair("mOriginalInstallerPackageName", this.mOriginalInstallerPackageName);
        indentingPrintWriter.printPair("installerPackageName", this.mInstallSource.mInstallerPackageName);
        indentingPrintWriter.printPair("installInitiatingPackageName", this.mInstallSource.mInitiatingPackageName);
        indentingPrintWriter.printPair("installOriginatingPackageName", this.mInstallSource.mOriginatingPackageName);
        indentingPrintWriter.printPair("mInstallerUid", java.lang.Integer.valueOf(this.mInstallerUid));
        indentingPrintWriter.printPair("createdMillis", java.lang.Long.valueOf(this.mCreatedMillis));
        indentingPrintWriter.printPair("updatedMillis", java.lang.Long.valueOf(this.mUpdatedMillis));
        indentingPrintWriter.printPair("committedMillis", java.lang.Long.valueOf(this.mCommittedMillis));
        indentingPrintWriter.printPair("stageDir", this.mStageDir);
        indentingPrintWriter.printPair("stageCid", this.mStageCid);
        indentingPrintWriter.println();
        indentingPrintWriter.print(this.mParams);
        indentingPrintWriter.printPair("mClientProgress", java.lang.Float.valueOf(this.mClientProgress));
        indentingPrintWriter.printPair("mProgress", java.lang.Float.valueOf(this.mProgress));
        indentingPrintWriter.printPair("mCommitted", java.lang.Boolean.valueOf(this.mCommitted));
        indentingPrintWriter.printPair("mPreapprovalRequested", java.lang.Boolean.valueOf(this.mPreapprovalRequested));
        indentingPrintWriter.printPair("mSealed", java.lang.Boolean.valueOf(this.mSealed));
        indentingPrintWriter.printPair("mPermissionsManuallyAccepted", java.lang.Boolean.valueOf(this.mPermissionsManuallyAccepted));
        indentingPrintWriter.printPair("mStageDirInUse", java.lang.Boolean.valueOf(this.mStageDirInUse));
        indentingPrintWriter.printPair("mDestroyed", java.lang.Boolean.valueOf(this.mDestroyed));
        indentingPrintWriter.printPair("mFds", java.lang.Integer.valueOf(this.mFds));
        indentingPrintWriter.printPair("mBridges", java.lang.Integer.valueOf(this.mBridges));
        indentingPrintWriter.printPair("mFinalStatus", java.lang.Integer.valueOf(this.mFinalStatus));
        indentingPrintWriter.printPair("mFinalMessage", this.mFinalMessage);
        indentingPrintWriter.printPair("mParentSessionId", java.lang.Integer.valueOf(this.mParentSessionId));
        indentingPrintWriter.printPair("mChildSessionIds", this.mChildSessionIds);
        indentingPrintWriter.printPair("mSessionApplied", java.lang.Boolean.valueOf(this.mSessionApplied));
        indentingPrintWriter.printPair("mSessionFailed", java.lang.Boolean.valueOf(this.mSessionFailed));
        indentingPrintWriter.printPair("mSessionReady", java.lang.Boolean.valueOf(this.mSessionReady));
        indentingPrintWriter.printPair("mSessionErrorCode", java.lang.Integer.valueOf(this.mSessionErrorCode));
        indentingPrintWriter.printPair("mSessionErrorMessage", this.mSessionErrorMessage);
        indentingPrintWriter.printPair("mPreapprovalDetails", this.mPreapprovalDetails);
        indentingPrintWriter.printPair("mPreVerifiedDomains", this.mPreVerifiedDomains);
        indentingPrintWriter.printPair("mAppPackageName", this.mPackageName);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    public android.content.pm.PackageInstaller.SessionInfo generateInfo() {
        android.content.pm.PackageInstaller.SessionInfo sessionInfo = new android.content.pm.PackageInstaller.SessionInfo();
        sessionInfo.sessionId = this.sessionId;
        sessionInfo.userId = this.userId;
        sessionInfo.installerPackageName = this.mInstallSource.mInstallerPackageName;
        sessionInfo.installerAttributionTag = this.mInstallSource.mInstallerAttributionTag;
        sessionInfo.progress = this.mProgress;
        sessionInfo.sealed = this.mSealed;
        sessionInfo.isCommitted = this.mCommitted;
        sessionInfo.isPreapprovalRequested = this.mPreapprovalRequested;
        sessionInfo.parentSessionId = this.mParentSessionId;
        sessionInfo.childSessionIds = this.mChildSessionIds;
        sessionInfo.isSessionApplied = this.mSessionApplied;
        sessionInfo.isSessionReady = this.mSessionReady;
        sessionInfo.isSessionFailed = this.mSessionFailed;
        sessionInfo.setSessionErrorCode(this.mSessionErrorCode, this.mSessionErrorMessage);
        sessionInfo.createdMillis = this.mCreatedMillis;
        sessionInfo.updatedMillis = this.mUpdatedMillis;
        sessionInfo.installerUid = this.mInstallerUid;
        sessionInfo.appPackageName = this.mPackageName;
        return sessionInfo;
    }
}
