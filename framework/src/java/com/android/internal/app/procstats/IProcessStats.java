package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public interface IProcessStats extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.procstats.IProcessStats";

    long getCommittedStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException;

    long getCommittedStatsMerged(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list, com.android.internal.app.procstats.ProcessStats processStats) throws android.os.RemoteException;

    int getCurrentMemoryState() throws android.os.RemoteException;

    byte[] getCurrentStats(java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException;

    long getMinAssociationDumpDuration() throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getStatsOverTime(long j) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.procstats.IProcessStats {
        @Override // com.android.internal.app.procstats.IProcessStats
        public byte[] getCurrentStats(java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public android.os.ParcelFileDescriptor getStatsOverTime(long j) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public int getCurrentMemoryState() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getCommittedStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getCommittedStatsMerged(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list, com.android.internal.app.procstats.ProcessStats processStats) throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getMinAssociationDumpDuration() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.procstats.IProcessStats {
        static final int TRANSACTION_getCommittedStats = 4;
        static final int TRANSACTION_getCommittedStatsMerged = 5;
        static final int TRANSACTION_getCurrentMemoryState = 3;
        static final int TRANSACTION_getCurrentStats = 1;
        static final int TRANSACTION_getMinAssociationDumpDuration = 6;
        static final int TRANSACTION_getStatsOverTime = 2;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static com.android.internal.app.procstats.IProcessStats asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.procstats.IProcessStats)) {
                return (com.android.internal.app.procstats.IProcessStats) queryLocalInterface;
            }
            return new com.android.internal.app.procstats.IProcessStats.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCurrentStats";
                case 2:
                    return "getStatsOverTime";
                case 3:
                    return "getCurrentMemoryState";
                case 4:
                    return "getCommittedStats";
                case 5:
                    return "getCommittedStatsMerged";
                case 6:
                    return "getMinAssociationDumpDuration";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    parcel.enforceNoDataAvail();
                    byte[] currentStats = getCurrentStats(arrayList);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(currentStats);
                    parcel2.writeTypedList(arrayList, 1);
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor statsOverTime = getStatsOverTime(readLong);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statsOverTime, 1);
                    return true;
                case 3:
                    int currentMemoryState = getCurrentMemoryState();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentMemoryState);
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    java.util.ArrayList arrayList2 = new java.util.ArrayList();
                    parcel.enforceNoDataAvail();
                    long committedStats = getCommittedStats(readLong2, readInt, readBoolean, arrayList2);
                    parcel2.writeNoException();
                    parcel2.writeLong(committedStats);
                    parcel2.writeTypedList(arrayList2, 1);
                    return true;
                case 5:
                    long readLong3 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    java.util.ArrayList arrayList3 = new java.util.ArrayList();
                    com.android.internal.app.procstats.ProcessStats processStats = new com.android.internal.app.procstats.ProcessStats();
                    parcel.enforceNoDataAvail();
                    long committedStatsMerged = getCommittedStatsMerged(readLong3, readInt2, readBoolean2, arrayList3, processStats);
                    parcel2.writeNoException();
                    parcel2.writeLong(committedStatsMerged);
                    parcel2.writeTypedList(arrayList3, 1);
                    parcel2.writeTypedObject(processStats, 1);
                    return true;
                case 6:
                    long minAssociationDumpDuration = getMinAssociationDumpDuration();
                    parcel2.writeNoException();
                    parcel2.writeLong(minAssociationDumpDuration);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.procstats.IProcessStats {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.procstats.IProcessStats.DESCRIPTOR;
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public byte[] getCurrentStats(java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    byte[] createByteArray = obtain2.createByteArray();
                    obtain2.readTypedList(list, android.os.ParcelFileDescriptor.CREATOR);
                    return createByteArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public android.os.ParcelFileDescriptor getStatsOverTime(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public int getCurrentMemoryState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getCommittedStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    long readLong = obtain2.readLong();
                    obtain2.readTypedList(list, android.os.ParcelFileDescriptor.CREATOR);
                    return readLong;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getCommittedStatsMerged(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list, com.android.internal.app.procstats.ProcessStats processStats) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    long readLong = obtain2.readLong();
                    obtain2.readTypedList(list, android.os.ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        processStats.readFromParcel(obtain2);
                    }
                    return readLong;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getMinAssociationDumpDuration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.procstats.IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getCurrentStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getStatsOverTime_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getCommittedStatsMerged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
