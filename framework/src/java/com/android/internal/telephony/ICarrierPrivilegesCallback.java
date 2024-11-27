package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ICarrierPrivilegesCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ICarrierPrivilegesCallback";

    void onCarrierPrivilegesChanged(java.util.List<java.lang.String> list, int[] iArr) throws android.os.RemoteException;

    void onCarrierServiceChanged(java.lang.String str, int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ICarrierPrivilegesCallback {
        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierPrivilegesChanged(java.util.List<java.lang.String> list, int[] iArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierServiceChanged(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ICarrierPrivilegesCallback {
        static final int TRANSACTION_onCarrierPrivilegesChanged = 1;
        static final int TRANSACTION_onCarrierServiceChanged = 2;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.ICarrierPrivilegesCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.ICarrierPrivilegesCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.ICarrierPrivilegesCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ICarrierPrivilegesCallback)) {
                return (com.android.internal.telephony.ICarrierPrivilegesCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.ICarrierPrivilegesCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCarrierPrivilegesChanged";
                case 2:
                    return "onCarrierServiceChanged";
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
                parcel.enforceInterface(com.android.internal.telephony.ICarrierPrivilegesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.ICarrierPrivilegesCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onCarrierPrivilegesChanged(createStringArrayList, createIntArray);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCarrierServiceChanged(readString, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ICarrierPrivilegesCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ICarrierPrivilegesCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
            public void onCarrierPrivilegesChanged(java.util.List<java.lang.String> list, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierPrivilegesCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
            public void onCarrierServiceChanged(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICarrierPrivilegesCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
