package android.app.usage;

/* loaded from: classes.dex */
public final class ParcelableUsageEventList implements android.os.Parcelable {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_ALL = false;
    private static final java.lang.String TAG = "ParcelableUsageEventList";
    private java.util.List<android.app.usage.UsageEvents.Event> mList;
    private static final int MAX_IPC_SIZE = android.os.IBinder.getSuggestedMaxIpcSizeBytes();
    public static final android.os.Parcelable.Creator<android.app.usage.ParcelableUsageEventList> CREATOR = new android.os.Parcelable.Creator<android.app.usage.ParcelableUsageEventList>() { // from class: android.app.usage.ParcelableUsageEventList.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.ParcelableUsageEventList createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.ParcelableUsageEventList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.ParcelableUsageEventList[] newArray(int i) {
            return new android.app.usage.ParcelableUsageEventList[i];
        }
    };

    public ParcelableUsageEventList(java.util.List<android.app.usage.UsageEvents.Event> list) {
        if (list == null) {
            throw new java.lang.IllegalArgumentException("Empty list");
        }
        this.mList = list;
    }

    private ParcelableUsageEventList(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mList = new java.util.ArrayList(readInt);
        if (readInt <= 0) {
            return;
        }
        int i = 0;
        while (i < readInt && parcel.readInt() != 0) {
            this.mList.add(readEventFromParcel(parcel));
            i++;
        }
        if (i >= readInt) {
            return;
        }
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        while (i < readInt) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            android.os.Parcel obtain2 = android.os.Parcel.obtain();
            obtain.writeInt(i);
            try {
                try {
                    readStrongBinder.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    while (i < readInt && obtain2.readInt() != 0) {
                        this.mList.add(readEventFromParcel(obtain2));
                        i++;
                    }
                } catch (android.os.RemoteException e) {
                    throw new android.os.BadParcelableException("Failure retrieving array; only received " + i + " of " + readInt, e);
                }
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, final int i) {
        final int size = this.mList.size();
        parcel.writeInt(size);
        if (size > 0) {
            int i2 = 0;
            while (i2 < size && parcel.dataSize() < MAX_IPC_SIZE) {
                parcel.writeInt(1);
                writeEventToParcel(this.mList.get(i2), parcel, i);
                i2++;
            }
            if (i2 < size) {
                parcel.writeInt(0);
                parcel.writeStrongBinder(new android.os.Binder() { // from class: android.app.usage.ParcelableUsageEventList.1
                    @Override // android.os.Binder
                    protected boolean onTransact(int i3, android.os.Parcel parcel2, android.os.Parcel parcel3, int i4) throws android.os.RemoteException {
                        if (i3 != 1) {
                            return super.onTransact(i3, parcel2, parcel3, i4);
                        }
                        if (android.app.usage.ParcelableUsageEventList.this.mList == null) {
                            throw new java.lang.IllegalArgumentException("Attempt to transfer null list, did transfer finish?");
                        }
                        int readInt = parcel2.readInt();
                        try {
                            parcel3.writeNoException();
                            while (readInt < size && parcel3.dataSize() < 65536) {
                                parcel3.writeInt(1);
                                android.app.usage.ParcelableUsageEventList.this.writeEventToParcel((android.app.usage.UsageEvents.Event) android.app.usage.ParcelableUsageEventList.this.mList.get(readInt), parcel3, i);
                                readInt++;
                            }
                            if (readInt >= size) {
                                android.app.usage.ParcelableUsageEventList.this.mList = null;
                            } else {
                                parcel3.writeInt(0);
                            }
                            return true;
                        } catch (java.lang.RuntimeException e) {
                            android.app.usage.ParcelableUsageEventList.this.mList = null;
                            throw e;
                        }
                    }
                });
            }
        }
    }

    public java.util.List<android.app.usage.UsageEvents.Event> getList() {
        return this.mList;
    }

    private android.app.usage.UsageEvents.Event readEventFromParcel(android.os.Parcel parcel) {
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
        event.mPackage = parcel.readString();
        event.mClass = parcel.readString();
        event.mInstanceId = parcel.readInt();
        event.mTaskRootPackage = parcel.readString();
        event.mTaskRootClass = parcel.readString();
        event.mEventType = parcel.readInt();
        event.mTimeStamp = parcel.readLong();
        event.mConfiguration = null;
        event.mShortcutId = null;
        event.mAction = null;
        event.mContentType = null;
        event.mContentAnnotations = null;
        event.mNotificationChannelId = null;
        event.mLocusId = null;
        event.mExtras = null;
        switch (event.mEventType) {
            case 5:
                event.mConfiguration = android.content.res.Configuration.CREATOR.createFromParcel(parcel);
                break;
            case 7:
                if (parcel.readInt() != 0) {
                    event.mExtras = parcel.readPersistableBundle(getClass().getClassLoader());
                    break;
                }
                break;
            case 8:
                event.mShortcutId = parcel.readString();
                break;
            case 9:
                event.mAction = parcel.readString();
                event.mContentType = parcel.readString();
                event.mContentAnnotations = parcel.readStringArray();
                break;
            case 11:
                event.mBucketAndReason = parcel.readInt();
                break;
            case 12:
                event.mNotificationChannelId = parcel.readString();
                break;
            case 30:
                event.mLocusId = parcel.readString();
                break;
        }
        event.mFlags = parcel.readInt();
        return event;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeEventToParcel(android.app.usage.UsageEvents.Event event, android.os.Parcel parcel, int i) {
        parcel.writeString(event.mPackage);
        parcel.writeString(event.mClass);
        parcel.writeInt(event.mInstanceId);
        parcel.writeString(event.mTaskRootPackage);
        parcel.writeString(event.mTaskRootClass);
        parcel.writeInt(event.mEventType);
        parcel.writeLong(event.mTimeStamp);
        switch (event.mEventType) {
            case 5:
                event.mConfiguration.writeToParcel(parcel, i);
                break;
            case 7:
                if (event.mExtras != null) {
                    parcel.writeInt(1);
                    parcel.writePersistableBundle(event.mExtras);
                    break;
                } else {
                    parcel.writeInt(0);
                    break;
                }
            case 8:
                parcel.writeString(event.mShortcutId);
                break;
            case 9:
                parcel.writeString(event.mAction);
                parcel.writeString(event.mContentType);
                parcel.writeStringArray(event.mContentAnnotations);
                break;
            case 11:
                parcel.writeInt(event.mBucketAndReason);
                break;
            case 12:
                parcel.writeString(event.mNotificationChannelId);
                break;
            case 30:
                parcel.writeString(event.mLocusId);
                break;
        }
        parcel.writeInt(event.mFlags);
    }
}
