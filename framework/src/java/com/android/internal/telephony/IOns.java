package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface IOns extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IOns";

    int getPreferredDataSubscriptionId(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean setEnable(boolean z, java.lang.String str) throws android.os.RemoteException;

    void setPreferredDataSubscriptionId(int i, boolean z, com.android.internal.telephony.ISetOpportunisticDataCallback iSetOpportunisticDataCallback, java.lang.String str) throws android.os.RemoteException;

    void updateAvailableNetworks(java.util.List<android.telephony.AvailableNetworkInfo> list, com.android.internal.telephony.IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.IOns {
        @Override // com.android.internal.telephony.IOns
        public boolean setEnable(boolean z, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IOns
        public boolean isEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IOns
        public void setPreferredDataSubscriptionId(int i, boolean z, com.android.internal.telephony.ISetOpportunisticDataCallback iSetOpportunisticDataCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IOns
        public int getPreferredDataSubscriptionId(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IOns
        public void updateAvailableNetworks(java.util.List<android.telephony.AvailableNetworkInfo> list, com.android.internal.telephony.IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IOns {
        static final int TRANSACTION_getPreferredDataSubscriptionId = 4;
        static final int TRANSACTION_isEnabled = 2;
        static final int TRANSACTION_setEnable = 1;
        static final int TRANSACTION_setPreferredDataSubscriptionId = 3;
        static final int TRANSACTION_updateAvailableNetworks = 5;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.IOns.DESCRIPTOR);
        }

        public static com.android.internal.telephony.IOns asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.IOns.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.IOns)) {
                return (com.android.internal.telephony.IOns) queryLocalInterface;
            }
            return new com.android.internal.telephony.IOns.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setEnable";
                case 2:
                    return "isEnabled";
                case 3:
                    return "setPreferredDataSubscriptionId";
                case 4:
                    return "getPreferredDataSubscriptionId";
                case 5:
                    return "updateAvailableNetworks";
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
                parcel.enforceInterface(com.android.internal.telephony.IOns.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.IOns.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean enable = setEnable(readBoolean, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enable);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEnabled = isEnabled(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabled);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    com.android.internal.telephony.ISetOpportunisticDataCallback asInterface = com.android.internal.telephony.ISetOpportunisticDataCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPreferredDataSubscriptionId(readInt, readBoolean2, asInterface, readString3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int preferredDataSubscriptionId = getPreferredDataSubscriptionId(readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDataSubscriptionId);
                    return true;
                case 5:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.AvailableNetworkInfo.CREATOR);
                    com.android.internal.telephony.IUpdateAvailableNetworksCallback asInterface2 = com.android.internal.telephony.IUpdateAvailableNetworksCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAvailableNetworks(createTypedArrayList, asInterface2, readString6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.IOns {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.IOns.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IOns
            public boolean setEnable(boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IOns.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public boolean isEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IOns.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public void setPreferredDataSubscriptionId(int i, boolean z, com.android.internal.telephony.ISetOpportunisticDataCallback iSetOpportunisticDataCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IOns.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSetOpportunisticDataCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public int getPreferredDataSubscriptionId(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IOns.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IOns
            public void updateAvailableNetworks(java.util.List<android.telephony.AvailableNetworkInfo> list, com.android.internal.telephony.IUpdateAvailableNetworksCallback iUpdateAvailableNetworksCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IOns.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iUpdateAvailableNetworksCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
