package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public class DomainVerificationEnforcer {

    @android.annotation.NonNull
    private com.android.server.pm.verify.domain.DomainVerificationEnforcer.Callback mCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    public interface Callback {
        boolean doesUserExist(int i);

        boolean filterAppAccess(@android.annotation.NonNull java.lang.String str, int i, int i2);
    }

    public DomainVerificationEnforcer(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
    }

    public void setCallback(@android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationEnforcer.Callback callback) {
        this.mCallback = callback;
    }

    public void assertInternal(int i) {
        switch (i) {
            case 0:
            case 1000:
            case 2000:
                return;
            default:
                throw new java.lang.SecurityException("Caller " + i + " is not allowed to change internal state");
        }
    }

    public void assertApprovedQuerent(int i, @android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxy domainVerificationProxy) {
        switch (i) {
            case 0:
            case 1000:
            case 2000:
                break;
            default:
                if (!domainVerificationProxy.isCallerVerifier(i)) {
                    this.mContext.enforcePermission("android.permission.DUMP", android.os.Binder.getCallingPid(), i, "Caller " + i + " is not allowed to query domain verification state");
                    break;
                } else {
                    this.mContext.enforcePermission("android.permission.QUERY_ALL_PACKAGES", android.os.Binder.getCallingPid(), i, "Caller " + i + " does not hold android.permission.QUERY_ALL_PACKAGES");
                    break;
                }
        }
    }

    public void assertApprovedVerifier(int i, @android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxy domainVerificationProxy) throws java.lang.SecurityException {
        switch (i) {
            case 0:
            case 1000:
            case 2000:
                break;
            default:
                int callingPid = android.os.Binder.getCallingPid();
                boolean z = false;
                if (this.mContext.checkPermission("android.permission.DOMAIN_VERIFICATION_AGENT", callingPid, i) != 0) {
                    r0 = this.mContext.checkPermission("android.permission.INTENT_FILTER_VERIFICATION_AGENT", callingPid, i) == 0;
                    if (!r0) {
                        throw new java.lang.SecurityException("Caller " + i + " does not hold android.permission.DOMAIN_VERIFICATION_AGENT");
                    }
                    z = r0;
                }
                if (!z) {
                    this.mContext.enforcePermission("android.permission.QUERY_ALL_PACKAGES", callingPid, i, "Caller " + i + " does not hold android.permission.QUERY_ALL_PACKAGES");
                }
                r0 = domainVerificationProxy.isCallerVerifier(i);
                break;
        }
        if (!r0) {
            throw new java.lang.SecurityException("Caller " + i + " is not the approved domain verification agent");
        }
    }

    public boolean assertApprovedUserStateQuerent(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3) throws java.lang.SecurityException {
        if (i2 != i3) {
            this.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS", android.os.Binder.getCallingPid(), i, "Caller is not allowed to edit other users");
        }
        if (!this.mCallback.doesUserExist(i2)) {
            throw new java.lang.SecurityException("User " + i2 + " does not exist");
        }
        if (!this.mCallback.doesUserExist(i3)) {
            throw new java.lang.SecurityException("User " + i3 + " does not exist");
        }
        return !this.mCallback.filterAppAccess(str, i, i3);
    }

    public boolean assertApprovedUserSelector(int i, int i2, @android.annotation.Nullable java.lang.String str, int i3) throws java.lang.SecurityException {
        if (i2 != i3) {
            this.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS", android.os.Binder.getCallingPid(), i, "Caller is not allowed to edit other users");
        }
        this.mContext.enforcePermission("android.permission.UPDATE_DOMAIN_VERIFICATION_USER_SELECTION", android.os.Binder.getCallingPid(), i, "Caller is not allowed to edit user selections");
        if (!this.mCallback.doesUserExist(i2)) {
            throw new java.lang.SecurityException("User " + i2 + " does not exist");
        }
        if (this.mCallback.doesUserExist(i3)) {
            if (str == null) {
                return true;
            }
            return !this.mCallback.filterAppAccess(str, i, i3);
        }
        throw new java.lang.SecurityException("User " + i3 + " does not exist");
    }

    public boolean callerIsLegacyUserSelector(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3) {
        this.mContext.enforcePermission("android.permission.SET_PREFERRED_APPLICATIONS", android.os.Binder.getCallingPid(), i, "Caller is not allowed to edit user state");
        if (i2 != i3 && this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS", android.os.Binder.getCallingPid(), i) != 0) {
            return false;
        }
        if (!this.mCallback.doesUserExist(i2)) {
            throw new java.lang.SecurityException("User " + i2 + " does not exist");
        }
        if (!this.mCallback.doesUserExist(i3)) {
            throw new java.lang.SecurityException("User " + i3 + " does not exist");
        }
        return !this.mCallback.filterAppAccess(str, i, i3);
    }

    public boolean callerIsLegacyUserQuerent(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3) {
        if (i2 != i3) {
            this.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS_FULL", android.os.Binder.getCallingPid(), i, "Caller is not allowed to edit other users");
        }
        if (!this.mCallback.doesUserExist(i2)) {
            throw new java.lang.SecurityException("User " + i2 + " does not exist");
        }
        if (!this.mCallback.doesUserExist(i3)) {
            throw new java.lang.SecurityException("User " + i3 + " does not exist");
        }
        return !this.mCallback.filterAppAccess(str, i, i3);
    }

    public void assertOwnerQuerent(int i, int i2, int i3) {
        int callingPid = android.os.Binder.getCallingPid();
        if (i2 != i3) {
            this.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS", callingPid, i, "Caller is not allowed to query other users");
        }
        this.mContext.enforcePermission("android.permission.QUERY_ALL_PACKAGES", callingPid, i, "Caller " + i + " does not hold android.permission.QUERY_ALL_PACKAGES");
        this.mContext.enforcePermission("android.permission.UPDATE_DOMAIN_VERIFICATION_USER_SELECTION", callingPid, i, "Caller is not allowed to query user selections");
        if (!this.mCallback.doesUserExist(i2)) {
            throw new java.lang.SecurityException("User " + i2 + " does not exist");
        }
        if (!this.mCallback.doesUserExist(i3)) {
            throw new java.lang.SecurityException("User " + i3 + " does not exist");
        }
    }
}
