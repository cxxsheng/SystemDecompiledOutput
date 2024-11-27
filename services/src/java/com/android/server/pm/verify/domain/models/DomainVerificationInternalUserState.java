package com.android.server.pm.verify.domain.models;

/* loaded from: classes2.dex */
public class DomainVerificationInternalUserState {

    @android.annotation.NonNull
    private final android.util.ArraySet<java.lang.String> mEnabledHosts;
    private boolean mLinkHandlingAllowed;
    private final int mUserId;

    public DomainVerificationInternalUserState(int i) {
        this.mLinkHandlingAllowed = true;
        this.mUserId = i;
        this.mEnabledHosts = new android.util.ArraySet<>();
    }

    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState addHosts(@android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        this.mEnabledHosts.addAll((android.util.ArraySet<? extends java.lang.String>) arraySet);
        return this;
    }

    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState addHosts(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        this.mEnabledHosts.addAll(set);
        return this;
    }

    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState removeHost(java.lang.String str) {
        this.mEnabledHosts.remove(str);
        return this;
    }

    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState removeHosts(@android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        this.mEnabledHosts.removeAll((android.util.ArraySet<? extends java.lang.String>) arraySet);
        return this;
    }

    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState removeHosts(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        this.mEnabledHosts.removeAll(set);
        return this;
    }

    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState retainHosts(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        this.mEnabledHosts.retainAll(set);
        return this;
    }

    public DomainVerificationInternalUserState(int i, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, boolean z) {
        this.mLinkHandlingAllowed = true;
        this.mUserId = i;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.UserIdInt.class, (android.annotation.UserIdInt) null, this.mUserId);
        this.mEnabledHosts = arraySet;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mEnabledHosts);
        this.mLinkHandlingAllowed = z;
    }

    public int getUserId() {
        return this.mUserId;
    }

    @android.annotation.NonNull
    public android.util.ArraySet<java.lang.String> getEnabledHosts() {
        return this.mEnabledHosts;
    }

    public boolean isLinkHandlingAllowed() {
        return this.mLinkHandlingAllowed;
    }

    @android.annotation.NonNull
    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState setLinkHandlingAllowed(boolean z) {
        this.mLinkHandlingAllowed = z;
        return this;
    }

    public java.lang.String toString() {
        return "DomainVerificationInternalUserState { userId = " + this.mUserId + ", enabledHosts = " + this.mEnabledHosts + ", linkHandlingAllowed = " + this.mLinkHandlingAllowed + " }";
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState = (com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState) obj;
        if (this.mUserId == domainVerificationInternalUserState.mUserId && java.util.Objects.equals(this.mEnabledHosts, domainVerificationInternalUserState.mEnabledHosts) && this.mLinkHandlingAllowed == domainVerificationInternalUserState.mLinkHandlingAllowed) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mUserId + 31) * 31) + java.util.Objects.hashCode(this.mEnabledHosts)) * 31) + java.lang.Boolean.hashCode(this.mLinkHandlingAllowed);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
