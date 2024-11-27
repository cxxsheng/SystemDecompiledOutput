package android.service.notification;

/* loaded from: classes3.dex */
public class NotificationRankingUpdate implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.NotificationRankingUpdate> CREATOR = new android.os.Parcelable.Creator<android.service.notification.NotificationRankingUpdate>() { // from class: android.service.notification.NotificationRankingUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotificationRankingUpdate createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.NotificationRankingUpdate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotificationRankingUpdate[] newArray(int i) {
            return new android.service.notification.NotificationRankingUpdate[i];
        }
    };
    private final android.service.notification.NotificationListenerService.RankingMap mRankingMap;
    private android.os.SharedMemory mRankingMapFd;
    private final java.lang.String mSharedMemoryName;

    public NotificationRankingUpdate(android.service.notification.NotificationListenerService.Ranking[] rankingArr) {
        this.mRankingMapFd = null;
        this.mSharedMemoryName = "NotificationRankingUpdatedSharedMemory";
        this.mRankingMap = new android.service.notification.NotificationListenerService.RankingMap(rankingArr);
    }

    public NotificationRankingUpdate(android.os.Parcel parcel) {
        this.mRankingMapFd = null;
        this.mSharedMemoryName = "NotificationRankingUpdatedSharedMemory";
        if (!com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.rankingUpdateAshmem()) {
            this.mRankingMap = (android.service.notification.NotificationListenerService.RankingMap) parcel.readParcelable(getClass().getClassLoader(), android.service.notification.NotificationListenerService.RankingMap.class);
            return;
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            try {
                this.mRankingMapFd = (android.os.SharedMemory) parcel.readParcelable(getClass().getClassLoader(), android.os.SharedMemory.class);
                android.os.Bundle readBundle = parcel.readBundle(getClass().getClassLoader());
                if (this.mRankingMapFd == null) {
                    this.mRankingMap = null;
                    obtain.recycle();
                    return;
                }
                java.nio.ByteBuffer mapReadOnly = this.mRankingMapFd.mapReadOnly();
                int remaining = mapReadOnly.remaining();
                byte[] bArr = new byte[remaining];
                mapReadOnly.get(bArr);
                obtain.unmarshall(bArr, 0, remaining);
                obtain.setDataPosition(0);
                this.mRankingMap = (android.service.notification.NotificationListenerService.RankingMap) obtain.readParcelable(getClass().getClassLoader(), android.service.notification.NotificationListenerService.RankingMap.class);
                addSmartActionsFromBundleToRankingMap(readBundle);
                obtain.recycle();
                if (mapReadOnly == null || this.mRankingMapFd == null) {
                    return;
                }
                android.os.SharedMemory.unmap(mapReadOnly);
                this.mRankingMapFd.close();
            } catch (android.system.ErrnoException e) {
                throw new java.lang.RuntimeException(e);
            }
        } catch (java.lang.Throwable th) {
            obtain.recycle();
            if (0 != 0 && this.mRankingMapFd != null) {
                android.os.SharedMemory.unmap(null);
                this.mRankingMapFd.close();
            }
            throw th;
        }
    }

    private void addSmartActionsFromBundleToRankingMap(android.os.Bundle bundle) {
        if (bundle == null) {
            return;
        }
        for (java.lang.String str : this.mRankingMap.getOrderedKeys()) {
            this.mRankingMap.getRawRankingObject(str).setSmartActions(bundle.getParcelableArrayList(str, android.app.Notification.Action.class));
        }
    }

    public final boolean isFdNotNullAndClosed() {
        return this.mRankingMapFd != null && this.mRankingMapFd.getFd() == -1;
    }

    public android.service.notification.NotificationListenerService.RankingMap getRankingMap() {
        return this.mRankingMap;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mRankingMap.equals(((android.service.notification.NotificationRankingUpdate) obj).mRankingMap);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        java.nio.ByteBuffer byteBuffer;
        android.os.SharedMemory sharedMemory;
        if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.rankingUpdateAshmem()) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.os.Bundle bundle = new android.os.Bundle();
            java.lang.String[] orderedKeys = this.mRankingMap.getOrderedKeys();
            int i2 = 0;
            while (true) {
                byteBuffer = null;
                if (i2 >= orderedKeys.length) {
                    break;
                }
                java.lang.String str = orderedKeys[i2];
                android.service.notification.NotificationListenerService.Ranking rawRankingObject = this.mRankingMap.getRawRankingObject(str);
                java.util.List<android.app.Notification.Action> smartActions = rawRankingObject.getSmartActions();
                if (!smartActions.isEmpty()) {
                    bundle.putParcelableList(str, smartActions);
                }
                android.service.notification.NotificationListenerService.Ranking ranking = new android.service.notification.NotificationListenerService.Ranking();
                ranking.populate(rawRankingObject);
                ranking.setSmartActions(null);
                arrayList.add(ranking);
                i2++;
            }
            try {
                try {
                    obtain.writeParcelable(new android.service.notification.NotificationListenerService.RankingMap((android.service.notification.NotificationListenerService.Ranking[]) arrayList.toArray(new android.service.notification.NotificationListenerService.Ranking[0])), i);
                    int dataSize = obtain.dataSize();
                    this.mRankingMapFd = android.os.SharedMemory.create("NotificationRankingUpdatedSharedMemory", dataSize);
                    byteBuffer = this.mRankingMapFd.mapReadWrite();
                    byteBuffer.put(obtain.marshall(), 0, dataSize);
                    this.mRankingMapFd.setProtect(android.system.OsConstants.PROT_READ);
                    parcel.writeParcelable(this.mRankingMapFd, i);
                    parcel.writeBundle(bundle);
                    if (byteBuffer != null) {
                        if (sharedMemory != null) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                } catch (android.system.ErrnoException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } finally {
                obtain.recycle();
                if (byteBuffer != null && this.mRankingMapFd != null) {
                    android.os.SharedMemory.unmap(byteBuffer);
                    this.mRankingMapFd.close();
                }
            }
        }
        parcel.writeParcelable(this.mRankingMap, i);
    }
}
