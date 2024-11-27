package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
class AssociationStoreImpl implements com.android.server.companion.AssociationStore {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CDM_AssociationStore";
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.Integer, android.companion.AssociationInfo> mIdMap = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.net.MacAddress, java.util.Set<java.lang.Integer>> mAddressMap = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.List<android.companion.AssociationInfo>> mCachedPerUser = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mListeners"})
    private final java.util.Set<com.android.server.companion.AssociationStore.OnChangeListener> mListeners = new java.util.LinkedHashSet();

    AssociationStoreImpl() {
    }

    void addAssociation(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        checkNotRevoked(associationInfo);
        int id = associationInfo.getId();
        synchronized (this.mLock) {
            try {
                if (this.mIdMap.containsKey(java.lang.Integer.valueOf(id))) {
                    android.util.Slog.e(TAG, "Association with id " + id + " already exists.");
                    return;
                }
                this.mIdMap.put(java.lang.Integer.valueOf(id), associationInfo);
                android.net.MacAddress deviceMacAddress = associationInfo.getDeviceMacAddress();
                if (deviceMacAddress != null) {
                    this.mAddressMap.computeIfAbsent(deviceMacAddress, new java.util.function.Function() { // from class: com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda4
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            java.util.Set lambda$addAssociation$0;
                            lambda$addAssociation$0 = com.android.server.companion.AssociationStoreImpl.lambda$addAssociation$0((android.net.MacAddress) obj);
                            return lambda$addAssociation$0;
                        }
                    }).add(java.lang.Integer.valueOf(id));
                }
                invalidateCacheForUserLocked(associationInfo.getUserId());
                broadcastChange(0, associationInfo);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$addAssociation$0(android.net.MacAddress macAddress) {
        return new java.util.HashSet();
    }

    void updateAssociation(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        checkNotRevoked(associationInfo);
        int id = associationInfo.getId();
        synchronized (this.mLock) {
            try {
                android.companion.AssociationInfo associationInfo2 = this.mIdMap.get(java.lang.Integer.valueOf(id));
                if (associationInfo2 == null) {
                    return;
                }
                if (associationInfo2.equals(associationInfo)) {
                    return;
                }
                this.mIdMap.put(java.lang.Integer.valueOf(id), associationInfo);
                invalidateCacheForUserLocked(associationInfo2.getUserId());
                android.net.MacAddress deviceMacAddress = associationInfo.getDeviceMacAddress();
                android.net.MacAddress deviceMacAddress2 = associationInfo2.getDeviceMacAddress();
                boolean z = !java.util.Objects.equals(deviceMacAddress2, deviceMacAddress);
                if (z) {
                    if (deviceMacAddress2 != null) {
                        this.mAddressMap.get(deviceMacAddress2).remove(java.lang.Integer.valueOf(id));
                    }
                    if (deviceMacAddress != null) {
                        this.mAddressMap.computeIfAbsent(deviceMacAddress, new java.util.function.Function() { // from class: com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda5
                            @Override // java.util.function.Function
                            public final java.lang.Object apply(java.lang.Object obj) {
                                java.util.Set lambda$updateAssociation$1;
                                lambda$updateAssociation$1 = com.android.server.companion.AssociationStoreImpl.lambda$updateAssociation$1((android.net.MacAddress) obj);
                                return lambda$updateAssociation$1;
                            }
                        }).add(java.lang.Integer.valueOf(id));
                    }
                }
                broadcastChange(z ? 2 : 3, associationInfo);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$updateAssociation$1(android.net.MacAddress macAddress) {
        return new java.util.HashSet();
    }

    void removeAssociation(int i) {
        synchronized (this.mLock) {
            try {
                android.companion.AssociationInfo remove = this.mIdMap.remove(java.lang.Integer.valueOf(i));
                if (remove == null) {
                    return;
                }
                android.net.MacAddress deviceMacAddress = remove.getDeviceMacAddress();
                if (deviceMacAddress != null) {
                    this.mAddressMap.get(deviceMacAddress).remove(java.lang.Integer.valueOf(i));
                }
                invalidateCacheForUserLocked(remove.getUserId());
                broadcastChange(1, remove);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.companion.AssociationStore
    @android.annotation.NonNull
    public java.util.Collection<android.companion.AssociationInfo> getAssociations() {
        java.util.List copyOf;
        synchronized (this.mLock) {
            copyOf = java.util.List.copyOf(this.mIdMap.values());
        }
        return copyOf;
    }

    @Override // com.android.server.companion.AssociationStore
    @android.annotation.NonNull
    public java.util.List<android.companion.AssociationInfo> getAssociationsForUser(int i) {
        java.util.List<android.companion.AssociationInfo> associationsForUserLocked;
        synchronized (this.mLock) {
            associationsForUserLocked = getAssociationsForUserLocked(i);
        }
        return associationsForUserLocked;
    }

    @Override // com.android.server.companion.AssociationStore
    @android.annotation.NonNull
    public java.util.List<android.companion.AssociationInfo> getAssociationsForPackage(int i, @android.annotation.NonNull final java.lang.String str) {
        return java.util.Collections.unmodifiableList(com.android.internal.util.CollectionUtils.filter(getAssociationsForUser(i), new java.util.function.Predicate() { // from class: com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getAssociationsForPackage$2;
                lambda$getAssociationsForPackage$2 = com.android.server.companion.AssociationStoreImpl.lambda$getAssociationsForPackage$2(str, (android.companion.AssociationInfo) obj);
                return lambda$getAssociationsForPackage$2;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAssociationsForPackage$2(java.lang.String str, android.companion.AssociationInfo associationInfo) {
        return associationInfo.getPackageName().equals(str);
    }

    @Override // com.android.server.companion.AssociationStore
    @android.annotation.Nullable
    public android.companion.AssociationInfo getAssociationsForPackageWithAddress(final int i, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        return (android.companion.AssociationInfo) com.android.internal.util.CollectionUtils.find(getAssociationsByAddress(str2), new java.util.function.Predicate() { // from class: com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getAssociationsForPackageWithAddress$3;
                lambda$getAssociationsForPackageWithAddress$3 = com.android.server.companion.AssociationStoreImpl.lambda$getAssociationsForPackageWithAddress$3(i, str, (android.companion.AssociationInfo) obj);
                return lambda$getAssociationsForPackageWithAddress$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAssociationsForPackageWithAddress$3(int i, java.lang.String str, android.companion.AssociationInfo associationInfo) {
        return associationInfo.belongsToPackage(i, str);
    }

    @Override // com.android.server.companion.AssociationStore
    @android.annotation.Nullable
    public android.companion.AssociationInfo getAssociationById(int i) {
        android.companion.AssociationInfo associationInfo;
        synchronized (this.mLock) {
            associationInfo = this.mIdMap.get(java.lang.Integer.valueOf(i));
        }
        return associationInfo;
    }

    @Override // com.android.server.companion.AssociationStore
    @android.annotation.NonNull
    public java.util.List<android.companion.AssociationInfo> getAssociationsByAddress(@android.annotation.NonNull java.lang.String str) {
        android.net.MacAddress fromString = android.net.MacAddress.fromString(str);
        synchronized (this.mLock) {
            try {
                java.util.Set<java.lang.Integer> set = this.mAddressMap.get(fromString);
                if (set == null) {
                    return java.util.Collections.emptyList();
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(set.size());
                java.util.Iterator<java.lang.Integer> it = set.iterator();
                while (it.hasNext()) {
                    arrayList.add(this.mIdMap.get(it.next()));
                }
                return java.util.Collections.unmodifiableList(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.companion.AssociationInfo> getAssociationsForUserLocked(int i) {
        java.util.List<android.companion.AssociationInfo> list = this.mCachedPerUser.get(i);
        if (list != null) {
            return list;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.companion.AssociationInfo associationInfo : this.mIdMap.values()) {
            if (associationInfo.getUserId() == i) {
                arrayList.add(associationInfo);
            }
        }
        java.util.List<android.companion.AssociationInfo> unmodifiableList = java.util.Collections.unmodifiableList(arrayList);
        this.mCachedPerUser.set(i, unmodifiableList);
        return unmodifiableList;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void invalidateCacheForUserLocked(int i) {
        this.mCachedPerUser.delete(i);
    }

    @Override // com.android.server.companion.AssociationStore
    public void registerListener(@android.annotation.NonNull com.android.server.companion.AssociationStore.OnChangeListener onChangeListener) {
        synchronized (this.mListeners) {
            this.mListeners.add(onChangeListener);
        }
    }

    @Override // com.android.server.companion.AssociationStore
    public void unregisterListener(@android.annotation.NonNull com.android.server.companion.AssociationStore.OnChangeListener onChangeListener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(onChangeListener);
        }
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.append("Companion Device Associations: ");
        if (getAssociations().isEmpty()) {
            printWriter.append("<empty>\n");
            return;
        }
        printWriter.append("\n");
        java.util.Iterator<android.companion.AssociationInfo> it = getAssociations().iterator();
        while (it.hasNext()) {
            printWriter.append("  ").append((java.lang.CharSequence) it.next().toString()).append('\n');
        }
    }

    private void broadcastChange(int i, android.companion.AssociationInfo associationInfo) {
        synchronized (this.mListeners) {
            try {
                java.util.Iterator<com.android.server.companion.AssociationStore.OnChangeListener> it = this.mListeners.iterator();
                while (it.hasNext()) {
                    it.next().onAssociationChanged(i, associationInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setAssociations(java.util.Collection<android.companion.AssociationInfo> collection) {
        collection.forEach(new java.util.function.Consumer() { // from class: com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.companion.AssociationStoreImpl.checkNotRevoked((android.companion.AssociationInfo) obj);
            }
        });
        synchronized (this.mLock) {
            setAssociationsLocked(collection);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setAssociationsLocked(java.util.Collection<android.companion.AssociationInfo> collection) {
        clearLocked();
        for (android.companion.AssociationInfo associationInfo : collection) {
            int id = associationInfo.getId();
            this.mIdMap.put(java.lang.Integer.valueOf(id), associationInfo);
            android.net.MacAddress deviceMacAddress = associationInfo.getDeviceMacAddress();
            if (deviceMacAddress != null) {
                this.mAddressMap.computeIfAbsent(deviceMacAddress, new java.util.function.Function() { // from class: com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.Set lambda$setAssociationsLocked$5;
                        lambda$setAssociationsLocked$5 = com.android.server.companion.AssociationStoreImpl.lambda$setAssociationsLocked$5((android.net.MacAddress) obj);
                        return lambda$setAssociationsLocked$5;
                    }
                }).add(java.lang.Integer.valueOf(id));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$setAssociationsLocked$5(android.net.MacAddress macAddress) {
        return new java.util.HashSet();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void clearLocked() {
        this.mIdMap.clear();
        this.mAddressMap.clear();
        this.mCachedPerUser.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkNotRevoked(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        if (associationInfo.isRevoked()) {
            throw new java.lang.IllegalArgumentException("Revoked (removed) associations MUST NOT appear in the AssociationStore");
        }
    }
}
