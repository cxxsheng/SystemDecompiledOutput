package com.android.server.blob;

/* loaded from: classes.dex */
class BlobMetadata {
    private java.io.File mBlobFile;
    private final android.app.blob.BlobHandle mBlobHandle;
    private final long mBlobId;
    private final android.content.Context mContext;
    private final java.lang.Object mMetadataLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mMetadataLock"})
    private final android.util.ArraySet<com.android.server.blob.BlobMetadata.Committer> mCommitters = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mMetadataLock"})
    private final android.util.ArraySet<com.android.server.blob.BlobMetadata.Leasee> mLeasees = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mRevocableFds"})
    private final android.util.ArrayMap<com.android.server.blob.BlobMetadata.Accessor, android.util.ArraySet<android.os.RevocableFileDescriptor>> mRevocableFds = new android.util.ArrayMap<>();

    BlobMetadata(android.content.Context context, long j, android.app.blob.BlobHandle blobHandle) {
        this.mContext = context;
        this.mBlobId = j;
        this.mBlobHandle = blobHandle;
    }

    long getBlobId() {
        return this.mBlobId;
    }

    android.app.blob.BlobHandle getBlobHandle() {
        return this.mBlobHandle;
    }

    void addOrReplaceCommitter(@android.annotation.NonNull com.android.server.blob.BlobMetadata.Committer committer) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.remove(committer);
            this.mCommitters.add(committer);
        }
    }

    void setCommitters(android.util.ArraySet<com.android.server.blob.BlobMetadata.Committer> arraySet) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.clear();
            this.mCommitters.addAll((android.util.ArraySet<? extends com.android.server.blob.BlobMetadata.Committer>) arraySet);
        }
    }

    void removeCommitter(@android.annotation.NonNull final java.lang.String str, final int i) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeCommitter$0;
                    lambda$removeCommitter$0 = com.android.server.blob.BlobMetadata.lambda$removeCommitter$0(i, str, (com.android.server.blob.BlobMetadata.Committer) obj);
                    return lambda$removeCommitter$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeCommitter$0(int i, java.lang.String str, com.android.server.blob.BlobMetadata.Committer committer) {
        return committer.uid == i && committer.packageName.equals(str);
    }

    void removeCommitter(@android.annotation.NonNull com.android.server.blob.BlobMetadata.Committer committer) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.remove(committer);
        }
    }

    void removeCommittersFromUnknownPkgs(final android.util.SparseArray<android.util.SparseArray<java.lang.String>> sparseArray) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeCommittersFromUnknownPkgs$1;
                    lambda$removeCommittersFromUnknownPkgs$1 = com.android.server.blob.BlobMetadata.lambda$removeCommittersFromUnknownPkgs$1(sparseArray, (com.android.server.blob.BlobMetadata.Committer) obj);
                    return lambda$removeCommittersFromUnknownPkgs$1;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeCommittersFromUnknownPkgs$1(android.util.SparseArray sparseArray, com.android.server.blob.BlobMetadata.Committer committer) {
        if (((android.util.SparseArray) sparseArray.get(android.os.UserHandle.getUserId(committer.uid))) == null) {
            return true;
        }
        return !committer.packageName.equals(r2.get(committer.uid));
    }

    void addCommittersAndLeasees(com.android.server.blob.BlobMetadata blobMetadata) {
        this.mCommitters.addAll((android.util.ArraySet<? extends com.android.server.blob.BlobMetadata.Committer>) blobMetadata.mCommitters);
        this.mLeasees.addAll((android.util.ArraySet<? extends com.android.server.blob.BlobMetadata.Leasee>) blobMetadata.mLeasees);
    }

    @android.annotation.Nullable
    com.android.server.blob.BlobMetadata.Committer getExistingCommitter(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mCommitters) {
            try {
                int size = this.mCommitters.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.blob.BlobMetadata.Committer valueAt = this.mCommitters.valueAt(i2);
                    if (valueAt.uid == i && valueAt.packageName.equals(str)) {
                        return valueAt;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void addOrReplaceLeasee(java.lang.String str, int i, int i2, java.lang.CharSequence charSequence, long j) {
        synchronized (this.mMetadataLock) {
            com.android.server.blob.BlobMetadata.Leasee leasee = new com.android.server.blob.BlobMetadata.Leasee(this.mContext, str, i, i2, charSequence, j);
            this.mLeasees.remove(leasee);
            this.mLeasees.add(leasee);
        }
    }

    void setLeasees(android.util.ArraySet<com.android.server.blob.BlobMetadata.Leasee> arraySet) {
        synchronized (this.mMetadataLock) {
            this.mLeasees.clear();
            this.mLeasees.addAll((android.util.ArraySet<? extends com.android.server.blob.BlobMetadata.Leasee>) arraySet);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeLeasee(final java.lang.String str, final int i) {
        synchronized (this.mMetadataLock) {
            this.mLeasees.removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeLeasee$2;
                    lambda$removeLeasee$2 = com.android.server.blob.BlobMetadata.lambda$removeLeasee$2(i, str, (com.android.server.blob.BlobMetadata.Leasee) obj);
                    return lambda$removeLeasee$2;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeLeasee$2(int i, java.lang.String str, com.android.server.blob.BlobMetadata.Leasee leasee) {
        return leasee.uid == i && leasee.packageName.equals(str);
    }

    void removeLeaseesFromUnknownPkgs(final android.util.SparseArray<android.util.SparseArray<java.lang.String>> sparseArray) {
        synchronized (this.mMetadataLock) {
            this.mLeasees.removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeLeaseesFromUnknownPkgs$3;
                    lambda$removeLeaseesFromUnknownPkgs$3 = com.android.server.blob.BlobMetadata.lambda$removeLeaseesFromUnknownPkgs$3(sparseArray, (com.android.server.blob.BlobMetadata.Leasee) obj);
                    return lambda$removeLeaseesFromUnknownPkgs$3;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeLeaseesFromUnknownPkgs$3(android.util.SparseArray sparseArray, com.android.server.blob.BlobMetadata.Leasee leasee) {
        if (((android.util.SparseArray) sparseArray.get(android.os.UserHandle.getUserId(leasee.uid))) == null) {
            return true;
        }
        return !leasee.packageName.equals(r2.get(leasee.uid));
    }

    void removeExpiredLeases() {
        synchronized (this.mMetadataLock) {
            this.mLeasees.removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeExpiredLeases$4;
                    lambda$removeExpiredLeases$4 = com.android.server.blob.BlobMetadata.lambda$removeExpiredLeases$4((com.android.server.blob.BlobMetadata.Leasee) obj);
                    return lambda$removeExpiredLeases$4;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeExpiredLeases$4(com.android.server.blob.BlobMetadata.Leasee leasee) {
        return !leasee.isStillValid();
    }

    void removeDataForUser(final int i) {
        synchronized (this.mMetadataLock) {
            this.mCommitters.removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeDataForUser$5;
                    lambda$removeDataForUser$5 = com.android.server.blob.BlobMetadata.lambda$removeDataForUser$5(i, (com.android.server.blob.BlobMetadata.Committer) obj);
                    return lambda$removeDataForUser$5;
                }
            });
            this.mLeasees.removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeDataForUser$6;
                    lambda$removeDataForUser$6 = com.android.server.blob.BlobMetadata.lambda$removeDataForUser$6(i, (com.android.server.blob.BlobMetadata.Leasee) obj);
                    return lambda$removeDataForUser$6;
                }
            });
            this.mRevocableFds.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeDataForUser$7;
                    lambda$removeDataForUser$7 = com.android.server.blob.BlobMetadata.lambda$removeDataForUser$7(i, (java.util.Map.Entry) obj);
                    return lambda$removeDataForUser$7;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeDataForUser$5(int i, com.android.server.blob.BlobMetadata.Committer committer) {
        return i == android.os.UserHandle.getUserId(committer.uid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeDataForUser$6(int i, com.android.server.blob.BlobMetadata.Leasee leasee) {
        return i == android.os.UserHandle.getUserId(leasee.uid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeDataForUser$7(int i, java.util.Map.Entry entry) {
        com.android.server.blob.BlobMetadata.Accessor accessor = (com.android.server.blob.BlobMetadata.Accessor) entry.getKey();
        android.util.ArraySet arraySet = (android.util.ArraySet) entry.getValue();
        if (i != android.os.UserHandle.getUserId(accessor.uid)) {
            return false;
        }
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((android.os.RevocableFileDescriptor) arraySet.valueAt(i2)).revoke();
        }
        arraySet.clear();
        return true;
    }

    boolean hasValidLeases() {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i = 0; i < size; i++) {
                    if (this.mLeasees.valueAt(i).isStillValid()) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    long getSize() {
        return getBlobFile().length();
    }

    boolean isAccessAllowedForCaller(@android.annotation.NonNull java.lang.String str, int i) {
        if (getBlobHandle().isExpired()) {
            return false;
        }
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.blob.BlobMetadata.Leasee valueAt = this.mLeasees.valueAt(i2);
                    if (valueAt.isStillValid() && valueAt.equals(str, i)) {
                        return true;
                    }
                }
                int userId = android.os.UserHandle.getUserId(i);
                int size2 = this.mCommitters.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    com.android.server.blob.BlobMetadata.Committer valueAt2 = this.mCommitters.valueAt(i3);
                    if (userId == android.os.UserHandle.getUserId(valueAt2.uid)) {
                        if (valueAt2.equals(str, i)) {
                            return true;
                        }
                        if (valueAt2.blobAccessMode.isAccessAllowedForCaller(this.mContext, str, i, valueAt2.uid)) {
                            return true;
                        }
                    }
                }
                if (!checkCallerCanAccessBlobsAcrossUsers(i)) {
                    return false;
                }
                int size3 = this.mCommitters.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    com.android.server.blob.BlobMetadata.Committer valueAt3 = this.mCommitters.valueAt(i4);
                    int userId2 = android.os.UserHandle.getUserId(valueAt3.uid);
                    if (userId != userId2 && isPackageInstalledOnUser(str, userId2) && valueAt3.blobAccessMode.isAccessAllowedForCaller(this.mContext, str, i, valueAt3.uid)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean checkCallerCanAccessBlobsAcrossUsers(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mContext.checkPermission("android.permission.ACCESS_BLOBS_ACROSS_USERS", -1, i) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isPackageInstalledOnUser(java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    boolean hasACommitterOrLeaseeInUser(int i) {
        return hasACommitterInUser(i) || hasALeaseeInUser(i);
    }

    boolean hasACommitterInUser(int i) {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mCommitters.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (i == android.os.UserHandle.getUserId(this.mCommitters.valueAt(i2).uid)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean hasALeaseeInUser(int i) {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (i == android.os.UserHandle.getUserId(this.mLeasees.valueAt(i2).uid)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isACommitter(@android.annotation.NonNull java.lang.String str, int i) {
        boolean isAnAccessor;
        synchronized (this.mMetadataLock) {
            isAnAccessor = isAnAccessor(this.mCommitters, str, i, android.os.UserHandle.getUserId(i));
        }
        return isAnAccessor;
    }

    boolean isALeasee(@android.annotation.Nullable java.lang.String str, int i) {
        boolean z;
        synchronized (this.mMetadataLock) {
            try {
                com.android.server.blob.BlobMetadata.Leasee leasee = (com.android.server.blob.BlobMetadata.Leasee) getAccessor(this.mLeasees, str, i, android.os.UserHandle.getUserId(i));
                z = leasee != null && leasee.isStillValid();
            } finally {
            }
        }
        return z;
    }

    private boolean isALeaseeInUser(@android.annotation.Nullable java.lang.String str, int i, int i2) {
        boolean z;
        synchronized (this.mMetadataLock) {
            try {
                com.android.server.blob.BlobMetadata.Leasee leasee = (com.android.server.blob.BlobMetadata.Leasee) getAccessor(this.mLeasees, str, i, i2);
                z = leasee != null && leasee.isStillValid();
            } finally {
            }
        }
        return z;
    }

    private static <T extends com.android.server.blob.BlobMetadata.Accessor> boolean isAnAccessor(@android.annotation.NonNull android.util.ArraySet<T> arraySet, @android.annotation.Nullable java.lang.String str, int i, int i2) {
        return getAccessor(arraySet, str, i, i2) != null;
    }

    private static <T extends com.android.server.blob.BlobMetadata.Accessor> T getAccessor(@android.annotation.NonNull android.util.ArraySet<T> arraySet, @android.annotation.Nullable java.lang.String str, int i, int i2) {
        int size = arraySet.size();
        for (int i3 = 0; i3 < size; i3++) {
            T valueAt = arraySet.valueAt(i3);
            if (str != null && i != -1 && valueAt.equals(str, i)) {
                return valueAt;
            }
            if (str != null && valueAt.packageName.equals(str) && i2 == android.os.UserHandle.getUserId(valueAt.uid)) {
                return valueAt;
            }
            if (i != -1 && valueAt.uid == i) {
                return valueAt;
            }
        }
        return null;
    }

    boolean shouldAttributeToUser(int i) {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (i != android.os.UserHandle.getUserId(this.mLeasees.valueAt(i2).uid)) {
                        return false;
                    }
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean shouldAttributeToLeasee(@android.annotation.NonNull java.lang.String str, int i, boolean z) {
        if (isALeaseeInUser(str, -1, i)) {
            return (z && hasOtherLeasees(str, -1, i)) ? false : true;
        }
        return false;
    }

    boolean shouldAttributeToLeasee(int i, boolean z) {
        int userId = android.os.UserHandle.getUserId(i);
        if (isALeaseeInUser(null, i, userId)) {
            return (z && hasOtherLeasees(null, i, userId)) ? false : true;
        }
        return false;
    }

    private boolean hasOtherLeasees(@android.annotation.Nullable java.lang.String str, int i, int i2) {
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.server.blob.BlobMetadata.Leasee valueAt = this.mLeasees.valueAt(i3);
                    if (valueAt.isStillValid()) {
                        if (str != null && i != -1 && !valueAt.equals(str, i)) {
                            return true;
                        }
                        if (str != null && (!valueAt.packageName.equals(str) || i2 != android.os.UserHandle.getUserId(valueAt.uid))) {
                            return true;
                        }
                        if (i != -1 && valueAt.uid != i) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    android.app.blob.LeaseInfo getLeaseInfo(@android.annotation.NonNull java.lang.String str, int i) {
        int descriptionResourceId;
        synchronized (this.mMetadataLock) {
            try {
                int size = this.mLeasees.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.blob.BlobMetadata.Leasee valueAt = this.mLeasees.valueAt(i2);
                    if (valueAt.isStillValid() && valueAt.uid == i && valueAt.packageName.equals(str)) {
                        if (valueAt.descriptionResEntryName == null) {
                            descriptionResourceId = 0;
                        } else {
                            descriptionResourceId = com.android.server.blob.BlobStoreUtils.getDescriptionResourceId(this.mContext, valueAt.descriptionResEntryName, valueAt.packageName, android.os.UserHandle.getUserId(valueAt.uid));
                        }
                        return new android.app.blob.LeaseInfo(str, valueAt.expiryTimeMillis, descriptionResourceId, valueAt.description);
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void forEachLeasee(java.util.function.Consumer<com.android.server.blob.BlobMetadata.Leasee> consumer) {
        synchronized (this.mMetadataLock) {
            this.mLeasees.forEach(consumer);
        }
    }

    java.io.File getBlobFile() {
        if (this.mBlobFile == null) {
            this.mBlobFile = com.android.server.blob.BlobStoreConfig.getBlobFile(this.mBlobId);
        }
        return this.mBlobFile;
    }

    android.os.ParcelFileDescriptor openForRead(java.lang.String str, int i) throws java.io.IOException {
        try {
            java.io.FileDescriptor open = android.system.Os.open(getBlobFile().getPath(), android.system.OsConstants.O_RDONLY, 0);
            try {
                if (com.android.server.blob.BlobStoreConfig.shouldUseRevocableFdForReads()) {
                    return createRevocableFd(open, str, i);
                }
                return new android.os.ParcelFileDescriptor(open);
            } catch (java.io.IOException e) {
                libcore.io.IoUtils.closeQuietly(open);
                throw e;
            }
        } catch (android.system.ErrnoException e2) {
            throw e2.rethrowAsIOException();
        }
    }

    @android.annotation.NonNull
    private android.os.ParcelFileDescriptor createRevocableFd(java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) throws java.io.IOException {
        final com.android.server.blob.BlobMetadata.Accessor accessor;
        final android.os.RevocableFileDescriptor revocableFileDescriptor = new android.os.RevocableFileDescriptor(this.mContext, fileDescriptor, com.android.server.blob.BlobStoreUtils.getRevocableFdHandler());
        synchronized (this.mRevocableFds) {
            try {
                accessor = new com.android.server.blob.BlobMetadata.Accessor(str, i);
                android.util.ArraySet<android.os.RevocableFileDescriptor> arraySet = this.mRevocableFds.get(accessor);
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                    this.mRevocableFds.put(accessor, arraySet);
                }
                arraySet.add(revocableFileDescriptor);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        revocableFileDescriptor.addOnCloseListener(new android.os.ParcelFileDescriptor.OnCloseListener() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda7
            @Override // android.os.ParcelFileDescriptor.OnCloseListener
            public final void onClose(java.io.IOException iOException) {
                com.android.server.blob.BlobMetadata.this.lambda$createRevocableFd$8(accessor, revocableFileDescriptor, iOException);
            }
        });
        return revocableFileDescriptor.getRevocableFileDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createRevocableFd$8(com.android.server.blob.BlobMetadata.Accessor accessor, android.os.RevocableFileDescriptor revocableFileDescriptor, java.io.IOException iOException) {
        synchronized (this.mRevocableFds) {
            try {
                android.util.ArraySet<android.os.RevocableFileDescriptor> arraySet = this.mRevocableFds.get(accessor);
                if (arraySet != null) {
                    arraySet.remove(revocableFileDescriptor);
                    if (arraySet.isEmpty()) {
                        this.mRevocableFds.remove(accessor);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void destroy() {
        revokeAndClearAllFds();
        getBlobFile().delete();
    }

    private void revokeAndClearAllFds() {
        synchronized (this.mRevocableFds) {
            try {
                int size = this.mRevocableFds.size();
                for (int i = 0; i < size; i++) {
                    android.util.ArraySet<android.os.RevocableFileDescriptor> valueAt = this.mRevocableFds.valueAt(i);
                    if (valueAt != null) {
                        int size2 = valueAt.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            valueAt.valueAt(i2).revoke();
                        }
                    }
                }
                this.mRevocableFds.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean shouldBeDeleted(boolean z) {
        if (getBlobHandle().isExpired()) {
            return true;
        }
        return (!z || hasLeaseWaitTimeElapsedForAll()) && !hasValidLeases();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasLeaseWaitTimeElapsedForAll() {
        int size = this.mCommitters.size();
        for (int i = 0; i < size; i++) {
            if (!com.android.server.blob.BlobStoreConfig.hasLeaseWaitTimeElapsed(this.mCommitters.valueAt(i).getCommitTimeMs())) {
                return false;
            }
        }
        return true;
    }

    android.util.StatsEvent dumpAsStatsEvent(int i) {
        long j;
        android.util.StatsEvent buildStatsEvent;
        synchronized (this.mMetadataLock) {
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                int size = this.mCommitters.size();
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    j = 1120986464257L;
                    if (i3 >= size) {
                        break;
                    }
                    com.android.server.blob.BlobMetadata.Committer valueAt = this.mCommitters.valueAt(i3);
                    long start = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1120986464257L, valueAt.uid);
                    protoOutputStream.write(1112396529666L, valueAt.commitTimeMs);
                    protoOutputStream.write(1120986464259L, valueAt.blobAccessMode.getAccessType());
                    protoOutputStream.write(1120986464260L, valueAt.blobAccessMode.getAllowedPackagesCount());
                    protoOutputStream.end(start);
                    i3++;
                }
                byte[] bytes = protoOutputStream.getBytes();
                android.util.proto.ProtoOutputStream protoOutputStream2 = new android.util.proto.ProtoOutputStream();
                int size2 = this.mLeasees.size();
                while (i2 < size2) {
                    com.android.server.blob.BlobMetadata.Leasee valueAt2 = this.mLeasees.valueAt(i2);
                    long start2 = protoOutputStream2.start(2246267895809L);
                    protoOutputStream2.write(j, valueAt2.uid);
                    protoOutputStream2.write(1112396529666L, valueAt2.expiryTimeMillis);
                    protoOutputStream2.end(start2);
                    i2++;
                    j = 1120986464257L;
                }
                buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, this.mBlobId, getSize(), this.mBlobHandle.getExpiryTimeMillis(), bytes, protoOutputStream2.getBytes());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return buildStatsEvent;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.blob.BlobStoreManagerService.DumpArgs dumpArgs) {
        synchronized (this.mMetadataLock) {
            try {
                indentingPrintWriter.println("blobHandle:");
                indentingPrintWriter.increaseIndent();
                this.mBlobHandle.dump(indentingPrintWriter, dumpArgs.shouldDumpFull());
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("size: " + android.text.format.Formatter.formatFileSize(this.mContext, getSize(), 8));
                indentingPrintWriter.println("Committers:");
                indentingPrintWriter.increaseIndent();
                if (this.mCommitters.isEmpty()) {
                    indentingPrintWriter.println("<empty>");
                } else {
                    int size = this.mCommitters.size();
                    for (int i = 0; i < size; i++) {
                        com.android.server.blob.BlobMetadata.Committer valueAt = this.mCommitters.valueAt(i);
                        indentingPrintWriter.println("committer " + valueAt.toString());
                        indentingPrintWriter.increaseIndent();
                        valueAt.dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Leasees:");
                indentingPrintWriter.increaseIndent();
                if (this.mLeasees.isEmpty()) {
                    indentingPrintWriter.println("<empty>");
                } else {
                    int size2 = this.mLeasees.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        com.android.server.blob.BlobMetadata.Leasee valueAt2 = this.mLeasees.valueAt(i2);
                        indentingPrintWriter.println("leasee " + valueAt2.toString());
                        indentingPrintWriter.increaseIndent();
                        valueAt2.dump(this.mContext, indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Open fds:");
                indentingPrintWriter.increaseIndent();
                if (this.mRevocableFds.isEmpty()) {
                    indentingPrintWriter.println("<empty>");
                } else {
                    int size3 = this.mRevocableFds.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        indentingPrintWriter.println(this.mRevocableFds.keyAt(i3) + ": #" + this.mRevocableFds.valueAt(i3).size());
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        synchronized (this.mMetadataLock) {
            try {
                com.android.internal.util.XmlUtils.writeLongAttribute(xmlSerializer, "id", this.mBlobId);
                xmlSerializer.startTag(null, "bh");
                this.mBlobHandle.writeToXml(xmlSerializer);
                xmlSerializer.endTag(null, "bh");
                int size = this.mCommitters.size();
                for (int i = 0; i < size; i++) {
                    xmlSerializer.startTag(null, "c");
                    this.mCommitters.valueAt(i).writeToXml(xmlSerializer);
                    xmlSerializer.endTag(null, "c");
                }
                int size2 = this.mLeasees.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    xmlSerializer.startTag(null, "l");
                    this.mLeasees.valueAt(i2).writeToXml(xmlSerializer);
                    xmlSerializer.endTag(null, "l");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    static com.android.server.blob.BlobMetadata createFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser, int i, android.content.Context context) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        long readLongAttribute = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, "id");
        if (i < 6) {
            com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "us");
        }
        android.util.ArraySet<com.android.server.blob.BlobMetadata.Committer> arraySet = new android.util.ArraySet<>();
        android.util.ArraySet<com.android.server.blob.BlobMetadata.Leasee> arraySet2 = new android.util.ArraySet<>();
        int depth = xmlPullParser.getDepth();
        android.app.blob.BlobHandle blobHandle = null;
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("bh".equals(xmlPullParser.getName())) {
                blobHandle = android.app.blob.BlobHandle.createFromXml(xmlPullParser);
            } else if ("c".equals(xmlPullParser.getName())) {
                com.android.server.blob.BlobMetadata.Committer createFromXml = com.android.server.blob.BlobMetadata.Committer.createFromXml(xmlPullParser, i);
                if (createFromXml != null) {
                    arraySet.add(createFromXml);
                }
            } else if ("l".equals(xmlPullParser.getName())) {
                arraySet2.add(com.android.server.blob.BlobMetadata.Leasee.createFromXml(xmlPullParser, i));
            }
        }
        if (blobHandle == null) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "blobHandle should be available");
            return null;
        }
        com.android.server.blob.BlobMetadata blobMetadata = new com.android.server.blob.BlobMetadata(context, readLongAttribute, blobHandle);
        blobMetadata.setCommitters(arraySet);
        blobMetadata.setLeasees(arraySet2);
        return blobMetadata;
    }

    static final class Committer extends com.android.server.blob.BlobMetadata.Accessor {
        public final com.android.server.blob.BlobAccessMode blobAccessMode;
        public final long commitTimeMs;

        Committer(java.lang.String str, int i, com.android.server.blob.BlobAccessMode blobAccessMode, long j) {
            super(str, i);
            this.blobAccessMode = blobAccessMode;
            this.commitTimeMs = j;
        }

        long getCommitTimeMs() {
            return this.commitTimeMs;
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("commit time: ");
            sb.append(this.commitTimeMs == 0 ? "<null>" : com.android.server.blob.BlobStoreUtils.formatTime(this.commitTimeMs));
            indentingPrintWriter.println(sb.toString());
            indentingPrintWriter.println("accessMode:");
            indentingPrintWriter.increaseIndent();
            this.blobAccessMode.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }

        void writeToXml(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
            com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, "p", this.packageName);
            com.android.internal.util.XmlUtils.writeIntAttribute(xmlSerializer, "u", this.uid);
            com.android.internal.util.XmlUtils.writeLongAttribute(xmlSerializer, "cmt", this.commitTimeMs);
            xmlSerializer.startTag(null, "am");
            this.blobAccessMode.writeToXml(xmlSerializer);
            xmlSerializer.endTag(null, "am");
        }

        @android.annotation.Nullable
        static com.android.server.blob.BlobMetadata.Committer createFromXml(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            long j;
            java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "p");
            int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "u");
            if (i >= 4) {
                j = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, "cmt");
            } else {
                j = 0;
            }
            int depth = xmlPullParser.getDepth();
            com.android.server.blob.BlobAccessMode blobAccessMode = null;
            while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                if ("am".equals(xmlPullParser.getName())) {
                    blobAccessMode = com.android.server.blob.BlobAccessMode.createFromXml(xmlPullParser);
                }
            }
            if (blobAccessMode == null) {
                android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "blobAccessMode should be available");
                return null;
            }
            return new com.android.server.blob.BlobMetadata.Committer(readStringAttribute, readIntAttribute, blobAccessMode, j);
        }
    }

    static final class Leasee extends com.android.server.blob.BlobMetadata.Accessor {
        public final java.lang.CharSequence description;
        public final java.lang.String descriptionResEntryName;
        public final long expiryTimeMillis;

        Leasee(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.Nullable java.lang.CharSequence charSequence, long j) {
            super(str, i);
            android.content.res.Resources packageResources = com.android.server.blob.BlobStoreUtils.getPackageResources(context, str, android.os.UserHandle.getUserId(i));
            this.descriptionResEntryName = getResourceEntryName(packageResources, i2);
            this.expiryTimeMillis = j;
            this.description = charSequence == null ? getDescription(packageResources, i2) : charSequence;
        }

        Leasee(java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.CharSequence charSequence, long j) {
            super(str, i);
            this.descriptionResEntryName = str2;
            this.expiryTimeMillis = j;
            this.description = charSequence;
        }

        @android.annotation.Nullable
        private static java.lang.String getResourceEntryName(@android.annotation.Nullable android.content.res.Resources resources, int i) {
            if (!android.content.res.ResourceId.isValid(i) || resources == null) {
                return null;
            }
            return resources.getResourceEntryName(i);
        }

        @android.annotation.Nullable
        private static java.lang.String getDescription(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
            android.content.res.Resources packageResources;
            int descriptionResourceId;
            if (str == null || str.isEmpty() || (packageResources = com.android.server.blob.BlobStoreUtils.getPackageResources(context, str2, i)) == null || (descriptionResourceId = com.android.server.blob.BlobStoreUtils.getDescriptionResourceId(packageResources, str, str2)) == 0) {
                return null;
            }
            return packageResources.getString(descriptionResourceId);
        }

        @android.annotation.Nullable
        private static java.lang.String getDescription(@android.annotation.Nullable android.content.res.Resources resources, int i) {
            if (!android.content.res.ResourceId.isValid(i) || resources == null) {
                return null;
            }
            return resources.getString(i);
        }

        boolean isStillValid() {
            return this.expiryTimeMillis == 0 || this.expiryTimeMillis >= java.lang.System.currentTimeMillis();
        }

        void dump(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("desc: " + getDescriptionToDump(context));
            indentingPrintWriter.println("expiryMs: " + this.expiryTimeMillis);
        }

        @android.annotation.NonNull
        private java.lang.String getDescriptionToDump(@android.annotation.NonNull android.content.Context context) {
            java.lang.String description = getDescription(context, this.descriptionResEntryName, this.packageName, android.os.UserHandle.getUserId(this.uid));
            if (description == null) {
                description = this.description.toString();
            }
            return description == null ? "<none>" : description;
        }

        void writeToXml(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
            com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, "p", this.packageName);
            com.android.internal.util.XmlUtils.writeIntAttribute(xmlSerializer, "u", this.uid);
            com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, "rn", this.descriptionResEntryName);
            com.android.internal.util.XmlUtils.writeLongAttribute(xmlSerializer, "ex", this.expiryTimeMillis);
            com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, "d", this.description);
        }

        @android.annotation.NonNull
        static com.android.server.blob.BlobMetadata.Leasee createFromXml(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, int i) throws java.io.IOException {
            java.lang.String str;
            java.lang.String str2;
            java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "p");
            int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "u");
            if (i >= 3) {
                str = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "rn");
            } else {
                str = null;
            }
            long readLongAttribute = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, "ex");
            if (i >= 2) {
                str2 = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "d");
            } else {
                str2 = null;
            }
            return new com.android.server.blob.BlobMetadata.Leasee(readStringAttribute, readIntAttribute, str, str2, readLongAttribute);
        }
    }

    static class Accessor {
        public final java.lang.String packageName;
        public final int uid;

        Accessor(java.lang.String str, int i) {
            this.packageName = str;
            this.uid = i;
        }

        public boolean equals(java.lang.String str, int i) {
            return this.uid == i && this.packageName.equals(str);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof com.android.server.blob.BlobMetadata.Accessor)) {
                return false;
            }
            com.android.server.blob.BlobMetadata.Accessor accessor = (com.android.server.blob.BlobMetadata.Accessor) obj;
            if (this.uid == accessor.uid && this.packageName.equals(accessor.packageName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.packageName, java.lang.Integer.valueOf(this.uid));
        }

        public java.lang.String toString() {
            return "[" + this.packageName + ", " + this.uid + "]";
        }
    }
}
