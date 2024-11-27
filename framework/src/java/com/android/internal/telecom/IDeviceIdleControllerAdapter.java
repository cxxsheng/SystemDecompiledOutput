package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IDeviceIdleControllerAdapter extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IDeviceIdleControllerAdapter";

    void exemptAppTemporarilyForEvent(java.lang.String str, long j, int i, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IDeviceIdleControllerAdapter {
        @Override // com.android.internal.telecom.IDeviceIdleControllerAdapter
        public void exemptAppTemporarilyForEvent(java.lang.String str, long j, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IDeviceIdleControllerAdapter {
        static final int TRANSACTION_exemptAppTemporarilyForEvent = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.IDeviceIdleControllerAdapter.DESCRIPTOR);
        }

        public static com.android.internal.telecom.IDeviceIdleControllerAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.IDeviceIdleControllerAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IDeviceIdleControllerAdapter)) {
                return (com.android.internal.telecom.IDeviceIdleControllerAdapter) queryLocalInterface;
            }
            return new com.android.internal.telecom.IDeviceIdleControllerAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "exemptAppTemporarilyForEvent";
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
                parcel.enforceInterface(com.android.internal.telecom.IDeviceIdleControllerAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.IDeviceIdleControllerAdapter.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    exemptAppTemporarilyForEvent(readString, readLong, readInt, readString2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IDeviceIdleControllerAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IDeviceIdleControllerAdapter.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IDeviceIdleControllerAdapter
            public void exemptAppTemporarilyForEvent(java.lang.String str, long j, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IDeviceIdleControllerAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
