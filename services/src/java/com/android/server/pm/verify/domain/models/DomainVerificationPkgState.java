package com.android.server.pm.verify.domain.models;

/* loaded from: classes2.dex */
public class DomainVerificationPkgState {

    @android.annotation.Nullable
    private final java.lang.String mBackupSignatureHash;
    private final boolean mHasAutoVerifyDomains;

    @android.annotation.NonNull
    private final java.util.UUID mId;

    @android.annotation.NonNull
    private final java.lang.String mPackageName;

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mStateMap;

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> mUriRelativeFilterGroupMap;

    @android.annotation.NonNull
    private final android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> mUserStates;

    public DomainVerificationPkgState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.UUID uuid, boolean z) {
        this(str, uuid, z, new android.util.ArrayMap(0), new android.util.SparseArray(0), null, new android.util.ArrayMap());
    }

    public DomainVerificationPkgState(@android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, @android.annotation.NonNull java.util.UUID uuid, boolean z) {
        this(domainVerificationPkgState.getPackageName(), uuid, z, domainVerificationPkgState.getStateMap(), domainVerificationPkgState.getUserStates(), null, new android.util.ArrayMap());
    }

    public DomainVerificationPkgState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.UUID uuid, boolean z, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, @android.annotation.NonNull android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> sparseArray, @android.annotation.Nullable java.lang.String str2) {
        this(str, uuid, z, arrayMap, sparseArray, str2, new android.util.ArrayMap());
    }

    @android.annotation.Nullable
    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState getUserState(int i) {
        return this.mUserStates.get(i);
    }

    @android.annotation.Nullable
    public com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState getOrCreateUserState(int i) {
        com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState = this.mUserStates.get(i);
        if (domainVerificationInternalUserState == null) {
            com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState2 = new com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState(i);
            this.mUserStates.put(i, domainVerificationInternalUserState2);
            return domainVerificationInternalUserState2;
        }
        return domainVerificationInternalUserState;
    }

    public void removeUser(int i) {
        this.mUserStates.remove(i);
    }

    public void removeAllUsers() {
        this.mUserStates.clear();
    }

    private int userStatesHashCode() {
        return this.mUserStates.contentHashCode();
    }

    private boolean userStatesEquals(@android.annotation.NonNull android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> sparseArray) {
        return this.mUserStates.contentEquals(sparseArray);
    }

    public DomainVerificationPkgState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.UUID uuid, boolean z, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, @android.annotation.NonNull android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> sparseArray, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> arrayMap2) {
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mPackageName);
        this.mId = uuid;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mId);
        this.mHasAutoVerifyDomains = z;
        this.mStateMap = arrayMap;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mStateMap);
        this.mUserStates = sparseArray;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mUserStates);
        this.mBackupSignatureHash = str2;
        this.mUriRelativeFilterGroupMap = arrayMap2;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mUriRelativeFilterGroupMap);
    }

    @android.annotation.NonNull
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    @android.annotation.NonNull
    public java.util.UUID getId() {
        return this.mId;
    }

    public boolean isHasAutoVerifyDomains() {
        return this.mHasAutoVerifyDomains;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, java.lang.Integer> getStateMap() {
        return this.mStateMap;
    }

    @android.annotation.NonNull
    public android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> getUserStates() {
        return this.mUserStates;
    }

    @android.annotation.Nullable
    public java.lang.String getBackupSignatureHash() {
        return this.mBackupSignatureHash;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> getUriRelativeFilterGroupMap() {
        return this.mUriRelativeFilterGroupMap;
    }

    public java.lang.String toString() {
        return "DomainVerificationPkgState { packageName = " + this.mPackageName + ", id = " + this.mId + ", hasAutoVerifyDomains = " + this.mHasAutoVerifyDomains + ", stateMap = " + this.mStateMap + ", userStates = " + this.mUserStates + ", backupSignatureHash = " + this.mBackupSignatureHash + ", uriRelativeFilterGroupMap = " + this.mUriRelativeFilterGroupMap + " }";
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = (com.android.server.pm.verify.domain.models.DomainVerificationPkgState) obj;
        if (java.util.Objects.equals(this.mPackageName, domainVerificationPkgState.mPackageName) && java.util.Objects.equals(this.mId, domainVerificationPkgState.mId) && this.mHasAutoVerifyDomains == domainVerificationPkgState.mHasAutoVerifyDomains && java.util.Objects.equals(this.mStateMap, domainVerificationPkgState.mStateMap) && userStatesEquals(domainVerificationPkgState.mUserStates) && java.util.Objects.equals(this.mBackupSignatureHash, domainVerificationPkgState.mBackupSignatureHash) && java.util.Objects.equals(this.mUriRelativeFilterGroupMap, domainVerificationPkgState.mUriRelativeFilterGroupMap)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((java.util.Objects.hashCode(this.mPackageName) + 31) * 31) + java.util.Objects.hashCode(this.mId)) * 31) + java.lang.Boolean.hashCode(this.mHasAutoVerifyDomains)) * 31) + java.util.Objects.hashCode(this.mStateMap)) * 31) + userStatesHashCode()) * 31) + java.util.Objects.hashCode(this.mBackupSignatureHash)) * 31) + java.util.Objects.hashCode(this.mUriRelativeFilterGroupMap);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
