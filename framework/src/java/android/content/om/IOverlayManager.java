package android.content.om;

/* loaded from: classes.dex */
public interface IOverlayManager extends android.os.IInterface {
    void commit(android.content.om.OverlayManagerTransaction overlayManagerTransaction) throws android.os.RemoteException;

    java.util.Map<java.lang.String, java.util.List<android.content.om.OverlayInfo>> getAllOverlays(int i) throws android.os.RemoteException;

    java.lang.String[] getDefaultOverlayPackages() throws android.os.RemoteException;

    android.content.om.OverlayInfo getOverlayInfo(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.om.OverlayInfo getOverlayInfoByIdentifier(android.content.om.OverlayIdentifier overlayIdentifier, int i) throws android.os.RemoteException;

    java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getPartitionOrder() throws android.os.RemoteException;

    void invalidateCachesForOverlay(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isDefaultPartitionOrder() throws android.os.RemoteException;

    boolean setEnabled(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    boolean setEnabledExclusive(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    boolean setEnabledExclusiveInCategory(java.lang.String str, int i) throws android.os.RemoteException;

    boolean setHighestPriority(java.lang.String str, int i) throws android.os.RemoteException;

    boolean setLowestPriority(java.lang.String str, int i) throws android.os.RemoteException;

    boolean setPriority(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    public static class Default implements android.content.om.IOverlayManager {
        @Override // android.content.om.IOverlayManager
        public java.util.Map<java.lang.String, java.util.List<android.content.om.OverlayInfo>> getAllOverlays(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public android.content.om.OverlayInfo getOverlayInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public android.content.om.OverlayInfo getOverlayInfoByIdentifier(android.content.om.OverlayIdentifier overlayIdentifier, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabled(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabledExclusive(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setEnabledExclusiveInCategory(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setPriority(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setHighestPriority(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public boolean setLowestPriority(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.om.IOverlayManager
        public java.lang.String[] getDefaultOverlayPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public void invalidateCachesForOverlay(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public void commit(android.content.om.OverlayManagerTransaction overlayManagerTransaction) throws android.os.RemoteException {
        }

        @Override // android.content.om.IOverlayManager
        public java.lang.String getPartitionOrder() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.om.IOverlayManager
        public boolean isDefaultPartitionOrder() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.om.IOverlayManager {
        public static final java.lang.String DESCRIPTOR = "android.content.om.IOverlayManager";
        static final int TRANSACTION_commit = 13;
        static final int TRANSACTION_getAllOverlays = 1;
        static final int TRANSACTION_getDefaultOverlayPackages = 11;
        static final int TRANSACTION_getOverlayInfo = 3;
        static final int TRANSACTION_getOverlayInfoByIdentifier = 4;
        static final int TRANSACTION_getOverlayInfosForTarget = 2;
        static final int TRANSACTION_getPartitionOrder = 14;
        static final int TRANSACTION_invalidateCachesForOverlay = 12;
        static final int TRANSACTION_isDefaultPartitionOrder = 15;
        static final int TRANSACTION_setEnabled = 5;
        static final int TRANSACTION_setEnabledExclusive = 6;
        static final int TRANSACTION_setEnabledExclusiveInCategory = 7;
        static final int TRANSACTION_setHighestPriority = 9;
        static final int TRANSACTION_setLowestPriority = 10;
        static final int TRANSACTION_setPriority = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.om.IOverlayManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.om.IOverlayManager)) {
                return (android.content.om.IOverlayManager) queryLocalInterface;
            }
            return new android.content.om.IOverlayManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllOverlays";
                case 2:
                    return "getOverlayInfosForTarget";
                case 3:
                    return "getOverlayInfo";
                case 4:
                    return "getOverlayInfoByIdentifier";
                case 5:
                    return "setEnabled";
                case 6:
                    return "setEnabledExclusive";
                case 7:
                    return "setEnabledExclusiveInCategory";
                case 8:
                    return "setPriority";
                case 9:
                    return "setHighestPriority";
                case 10:
                    return "setLowestPriority";
                case 11:
                    return "getDefaultOverlayPackages";
                case 12:
                    return "invalidateCachesForOverlay";
                case 13:
                    return "commit";
                case 14:
                    return "getPartitionOrder";
                case 15:
                    return "isDefaultPartitionOrder";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, final android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.Map<java.lang.String, java.util.List<android.content.om.OverlayInfo>> allOverlays = getAllOverlays(readInt);
                    parcel2.writeNoException();
                    if (allOverlays == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allOverlays.size());
                        allOverlays.forEach(new java.util.function.BiConsumer() { // from class: android.content.om.IOverlayManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.content.om.IOverlayManager.Stub.lambda$onTransact$0(android.os.Parcel.this, (java.lang.String) obj, (java.util.List) obj2);
                            }
                        });
                    }
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.om.OverlayInfo> overlayInfosForTarget = getOverlayInfosForTarget(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(overlayInfosForTarget, 1);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.om.OverlayInfo overlayInfo = getOverlayInfo(readString2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayInfo, 1);
                    return true;
                case 4:
                    android.content.om.OverlayIdentifier overlayIdentifier = (android.content.om.OverlayIdentifier) parcel.readTypedObject(android.content.om.OverlayIdentifier.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.om.OverlayInfo overlayInfoByIdentifier = getOverlayInfoByIdentifier(overlayIdentifier, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlayInfoByIdentifier, 1);
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabled = setEnabled(readString3, readBoolean, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabled);
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabledExclusive = setEnabledExclusive(readString4, readBoolean2, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabledExclusive);
                    return true;
                case 7:
                    java.lang.String readString5 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enabledExclusiveInCategory = setEnabledExclusiveInCategory(readString5, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabledExclusiveInCategory);
                    return true;
                case 8:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean priority = setPriority(readString6, readString7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(priority);
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean highestPriority = setHighestPriority(readString8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highestPriority);
                    return true;
                case 10:
                    java.lang.String readString9 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean lowestPriority = setLowestPriority(readString9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lowestPriority);
                    return true;
                case 11:
                    java.lang.String[] defaultOverlayPackages = getDefaultOverlayPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(defaultOverlayPackages);
                    return true;
                case 12:
                    java.lang.String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    invalidateCachesForOverlay(readString10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.content.om.OverlayManagerTransaction overlayManagerTransaction = (android.content.om.OverlayManagerTransaction) parcel.readTypedObject(android.content.om.OverlayManagerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    commit(overlayManagerTransaction);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String partitionOrder = getPartitionOrder();
                    parcel2.writeNoException();
                    parcel2.writeString(partitionOrder);
                    return true;
                case 15:
                    boolean isDefaultPartitionOrder = isDefaultPartitionOrder();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDefaultPartitionOrder);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(android.os.Parcel parcel, java.lang.String str, java.util.List list) {
            parcel.writeString(str);
            parcel.writeTypedList(list, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.content.om.IOverlayManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.om.IOverlayManager.Stub.DESCRIPTOR;
            }

            @Override // android.content.om.IOverlayManager
            public java.util.Map<java.lang.String, java.util.List<android.content.om.OverlayInfo>> getAllOverlays(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                final android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.content.om.IOverlayManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), android.os.Parcel.this.createTypedArrayList(android.content.om.OverlayInfo.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.om.OverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public android.content.om.OverlayInfo getOverlayInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.om.OverlayInfo) obtain2.readTypedObject(android.content.om.OverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public android.content.om.OverlayInfo getOverlayInfoByIdentifier(android.content.om.OverlayIdentifier overlayIdentifier, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(overlayIdentifier, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.om.OverlayInfo) obtain2.readTypedObject(android.content.om.OverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabled(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusive(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setEnabledExclusiveInCategory(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setPriority(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setHighestPriority(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean setLowestPriority(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public java.lang.String[] getDefaultOverlayPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void invalidateCachesForOverlay(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public void commit(android.content.om.OverlayManagerTransaction overlayManagerTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(overlayManagerTransaction, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public java.lang.String getPartitionOrder() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.om.IOverlayManager
            public boolean isDefaultPartitionOrder() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.om.IOverlayManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
