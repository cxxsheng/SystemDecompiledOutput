package com.android.server.pm;

/* loaded from: classes2.dex */
public final class CrossProfileDomainInfo {
    int mHighestApprovalLevel;
    final android.content.pm.ResolveInfo mResolveInfo;
    final int mTargetUserId;

    CrossProfileDomainInfo(android.content.pm.ResolveInfo resolveInfo, int i, int i2) {
        this.mResolveInfo = resolveInfo;
        this.mHighestApprovalLevel = i;
        this.mTargetUserId = i2;
    }

    CrossProfileDomainInfo(android.content.pm.ResolveInfo resolveInfo, int i) {
        this.mResolveInfo = resolveInfo;
        this.mHighestApprovalLevel = i;
        this.mTargetUserId = -2;
    }

    public java.lang.String toString() {
        return "CrossProfileDomainInfo{resolveInfo=" + this.mResolveInfo + ", highestApprovalLevel=" + this.mHighestApprovalLevel + ", targetUserId= " + this.mTargetUserId + '}';
    }
}
