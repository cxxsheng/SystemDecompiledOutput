package android.content.pm.verify.domain;

/* loaded from: classes.dex */
public final class DomainVerificationManager {

    @android.annotation.SystemApi
    public static final int ERROR_DOMAIN_SET_ID_INVALID = 1;

    @android.annotation.SystemApi
    public static final int ERROR_UNABLE_TO_APPROVE = 3;

    @android.annotation.SystemApi
    public static final int ERROR_UNKNOWN_DOMAIN = 2;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_VERIFICATION_REQUEST = "android.content.pm.verify.domain.extra.VERIFICATION_REQUEST";
    public static final int INTERNAL_ERROR_NAME_NOT_FOUND = 1;

    @android.annotation.SystemApi
    public static final int STATUS_OK = 0;
    private final android.content.Context mContext;
    private final android.content.pm.verify.domain.IDomainVerificationManager mDomainVerificationManager;

    public @interface Error {
    }

    public DomainVerificationManager(android.content.Context context, android.content.pm.verify.domain.IDomainVerificationManager iDomainVerificationManager) {
        this.mContext = context;
        this.mDomainVerificationManager = iDomainVerificationManager;
    }

    @android.annotation.SystemApi
    public void setUriRelativeFilterGroups(java.lang.String str, java.util.Map<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> map) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(map);
        android.os.Bundle bundle = new android.os.Bundle();
        for (java.lang.String str2 : map.keySet()) {
            bundle.putParcelableList(str2, android.content.UriRelativeFilterGroup.groupsToParcels(map.get(str2)));
        }
        try {
            this.mDomainVerificationManager.setUriRelativeFilterGroups(str, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Map<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> getUriRelativeFilterGroups(java.lang.String str, java.util.List<java.lang.String> list) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(list);
        if (list.isEmpty()) {
            return java.util.Collections.emptyMap();
        }
        try {
            android.os.Bundle uriRelativeFilterGroups = this.mDomainVerificationManager.getUriRelativeFilterGroups(str, list);
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            if (!uriRelativeFilterGroups.isEmpty()) {
                for (java.lang.String str2 : uriRelativeFilterGroups.keySet()) {
                    arrayMap.put(str2, android.content.UriRelativeFilterGroup.parcelsToGroups(uriRelativeFilterGroups.getParcelableArrayList(str2, android.content.UriRelativeFilterGroupParcel.class)));
                }
            }
            return arrayMap;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> queryValidVerificationPackageNames() {
        try {
            return this.mDomainVerificationManager.queryValidVerificationPackageNames();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.content.pm.verify.domain.DomainVerificationInfo getDomainVerificationInfo(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return this.mDomainVerificationManager.getDomainVerificationInfo(str);
        } catch (java.lang.Exception e) {
            java.lang.Exception rethrow = rethrow(e, str);
            if (rethrow instanceof android.content.pm.PackageManager.NameNotFoundException) {
                throw ((android.content.pm.PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) rethrow);
            }
            throw new java.lang.RuntimeException(rethrow);
        }
    }

    @android.annotation.SystemApi
    public int setDomainVerificationStatus(java.util.UUID uuid, java.util.Set<java.lang.String> set, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        validateInput(uuid, set);
        try {
            return this.mDomainVerificationManager.setDomainVerificationStatus(uuid.toString(), new android.content.pm.verify.domain.DomainSet(set), i);
        } catch (java.lang.Exception e) {
            java.lang.Exception rethrow = rethrow(e, null);
            if (rethrow instanceof android.content.pm.PackageManager.NameNotFoundException) {
                throw ((android.content.pm.PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) rethrow);
            }
            throw new java.lang.RuntimeException(rethrow);
        }
    }

    @android.annotation.SystemApi
    public void setDomainVerificationLinkHandlingAllowed(java.lang.String str, boolean z) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            this.mDomainVerificationManager.setDomainVerificationLinkHandlingAllowed(str, z, this.mContext.getUserId());
        } catch (java.lang.Exception e) {
            java.lang.Exception rethrow = rethrow(e, null);
            if (rethrow instanceof android.content.pm.PackageManager.NameNotFoundException) {
                throw ((android.content.pm.PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) rethrow);
            }
            throw new java.lang.RuntimeException(rethrow);
        }
    }

    @android.annotation.SystemApi
    public int setDomainVerificationUserSelection(java.util.UUID uuid, java.util.Set<java.lang.String> set, boolean z) throws android.content.pm.PackageManager.NameNotFoundException {
        validateInput(uuid, set);
        try {
            return this.mDomainVerificationManager.setDomainVerificationUserSelection(uuid.toString(), new android.content.pm.verify.domain.DomainSet(set), z, this.mContext.getUserId());
        } catch (java.lang.Exception e) {
            java.lang.Exception rethrow = rethrow(e, null);
            if (rethrow instanceof android.content.pm.PackageManager.NameNotFoundException) {
                throw ((android.content.pm.PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) rethrow);
            }
            throw new java.lang.RuntimeException(rethrow);
        }
    }

    public android.content.pm.verify.domain.DomainVerificationUserState getDomainVerificationUserState(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return this.mDomainVerificationManager.getDomainVerificationUserState(str, this.mContext.getUserId());
        } catch (java.lang.Exception e) {
            java.lang.Exception rethrow = rethrow(e, str);
            if (rethrow instanceof android.content.pm.PackageManager.NameNotFoundException) {
                throw ((android.content.pm.PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) rethrow);
            }
            throw new java.lang.RuntimeException(rethrow);
        }
    }

    @android.annotation.SystemApi
    public java.util.SortedSet<android.content.pm.verify.domain.DomainOwner> getOwnersForDomain(java.lang.String str) {
        try {
            java.util.Objects.requireNonNull(str);
            final java.util.List<android.content.pm.verify.domain.DomainOwner> ownersForDomain = this.mDomainVerificationManager.getOwnersForDomain(str, this.mContext.getUserId());
            java.util.Objects.requireNonNull(ownersForDomain);
            java.util.TreeSet treeSet = new java.util.TreeSet(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: android.content.pm.verify.domain.DomainVerificationManager$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    return ownersForDomain.indexOf((android.content.pm.verify.domain.DomainOwner) obj);
                }
            }));
            treeSet.addAll(ownersForDomain);
            return treeSet;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private java.lang.Exception rethrow(java.lang.Exception exc, java.lang.String str) {
        if (exc instanceof android.os.ServiceSpecificException) {
            int i = ((android.os.ServiceSpecificException) exc).errorCode;
            if (str == null) {
                str = exc.getMessage();
            }
            if (i == 1) {
                return new android.content.pm.PackageManager.NameNotFoundException(str);
            }
            return exc;
        }
        if (exc instanceof android.os.RemoteException) {
            return ((android.os.RemoteException) exc).rethrowFromSystemServer();
        }
        return exc;
    }

    private void validateInput(java.util.UUID uuid, java.util.Set<java.lang.String> set) {
        if (uuid == null) {
            throw new java.lang.IllegalArgumentException("domainSetId cannot be null");
        }
        if (com.android.internal.util.CollectionUtils.isEmpty(set)) {
            throw new java.lang.IllegalArgumentException("Provided domain set cannot be empty");
        }
    }
}
