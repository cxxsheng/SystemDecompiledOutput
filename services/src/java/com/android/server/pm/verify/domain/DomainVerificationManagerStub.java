package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public class DomainVerificationManagerStub extends android.content.pm.verify.domain.IDomainVerificationManager.Stub {

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationService mService;

    public DomainVerificationManagerStub(com.android.server.pm.verify.domain.DomainVerificationService domainVerificationService) {
        this.mService = domainVerificationService;
    }

    public void setUriRelativeFilterGroups(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.Bundle bundle) {
        try {
            this.mService.setUriRelativeFilterGroups(str, bundle);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    @android.annotation.NonNull
    public android.os.Bundle getUriRelativeFilterGroups(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        try {
            return this.mService.getUriRelativeFilterGroups(str, list);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> queryValidVerificationPackageNames() {
        try {
            return this.mService.queryValidVerificationPackageNames();
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    @android.annotation.Nullable
    public android.content.pm.verify.domain.DomainVerificationInfo getDomainVerificationInfo(java.lang.String str) {
        try {
            return this.mService.getDomainVerificationInfo(str);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    @android.content.pm.verify.domain.DomainVerificationManager.Error
    public int setDomainVerificationStatus(java.lang.String str, @android.annotation.NonNull android.content.pm.verify.domain.DomainSet domainSet, int i) {
        try {
            return this.mService.setDomainVerificationStatus(java.util.UUID.fromString(str), domainSet.getDomains(), i);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    public void setDomainVerificationLinkHandlingAllowed(java.lang.String str, boolean z, int i) {
        try {
            this.mService.setDomainVerificationLinkHandlingAllowed(str, z, i);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    @android.content.pm.verify.domain.DomainVerificationManager.Error
    public int setDomainVerificationUserSelection(java.lang.String str, @android.annotation.NonNull android.content.pm.verify.domain.DomainSet domainSet, boolean z, int i) {
        try {
            return this.mService.setDomainVerificationUserSelection(java.util.UUID.fromString(str), domainSet.getDomains(), z, i);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    @android.annotation.Nullable
    public android.content.pm.verify.domain.DomainVerificationUserState getDomainVerificationUserState(java.lang.String str, int i) {
        try {
            return this.mService.getDomainVerificationUserState(str, i);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    @android.annotation.Nullable
    public java.util.List<android.content.pm.verify.domain.DomainOwner> getOwnersForDomain(@android.annotation.NonNull java.lang.String str, int i) {
        try {
            java.util.Objects.requireNonNull(str);
            return this.mService.getOwnersForDomain(str, i);
        } catch (java.lang.Exception e) {
            throw rethrow(e);
        }
    }

    private java.lang.RuntimeException rethrow(java.lang.Exception exc) throws java.lang.RuntimeException {
        if (exc instanceof android.content.pm.PackageManager.NameNotFoundException) {
            return new android.os.ServiceSpecificException(1);
        }
        if (exc instanceof java.lang.RuntimeException) {
            return (java.lang.RuntimeException) exc;
        }
        return new java.lang.RuntimeException(exc);
    }
}
