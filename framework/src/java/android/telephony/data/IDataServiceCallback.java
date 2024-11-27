package android.telephony.data;

/* loaded from: classes3.dex */
public interface IDataServiceCallback extends android.os.IInterface {
    void onApnUnthrottled(java.lang.String str) throws android.os.RemoteException;

    void onDataCallListChanged(java.util.List<android.telephony.data.DataCallResponse> list) throws android.os.RemoteException;

    void onDataProfileUnthrottled(android.telephony.data.DataProfile dataProfile) throws android.os.RemoteException;

    void onDeactivateDataCallComplete(int i) throws android.os.RemoteException;

    void onHandoverCancelled(int i) throws android.os.RemoteException;

    void onHandoverStarted(int i) throws android.os.RemoteException;

    void onRequestDataCallListComplete(int i, java.util.List<android.telephony.data.DataCallResponse> list) throws android.os.RemoteException;

    void onSetDataProfileComplete(int i) throws android.os.RemoteException;

    void onSetInitialAttachApnComplete(int i) throws android.os.RemoteException;

    void onSetupDataCallComplete(int i, android.telephony.data.DataCallResponse dataCallResponse) throws android.os.RemoteException;

    public static class Default implements android.telephony.data.IDataServiceCallback {
        @Override // android.telephony.data.IDataServiceCallback
        public void onSetupDataCallComplete(int i, android.telephony.data.DataCallResponse dataCallResponse) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onDeactivateDataCallComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onSetInitialAttachApnComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onSetDataProfileComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onRequestDataCallListComplete(int i, java.util.List<android.telephony.data.DataCallResponse> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onDataCallListChanged(java.util.List<android.telephony.data.DataCallResponse> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onHandoverStarted(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onHandoverCancelled(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onApnUnthrottled(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onDataProfileUnthrottled(android.telephony.data.DataProfile dataProfile) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.data.IDataServiceCallback {
        public static final java.lang.String DESCRIPTOR = "android.telephony.data.IDataServiceCallback";
        static final int TRANSACTION_onApnUnthrottled = 9;
        static final int TRANSACTION_onDataCallListChanged = 6;
        static final int TRANSACTION_onDataProfileUnthrottled = 10;
        static final int TRANSACTION_onDeactivateDataCallComplete = 2;
        static final int TRANSACTION_onHandoverCancelled = 8;
        static final int TRANSACTION_onHandoverStarted = 7;
        static final int TRANSACTION_onRequestDataCallListComplete = 5;
        static final int TRANSACTION_onSetDataProfileComplete = 4;
        static final int TRANSACTION_onSetInitialAttachApnComplete = 3;
        static final int TRANSACTION_onSetupDataCallComplete = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.telephony.data.IDataServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.data.IDataServiceCallback)) {
                return (android.telephony.data.IDataServiceCallback) queryLocalInterface;
            }
            return new android.telephony.data.IDataServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSetupDataCallComplete";
                case 2:
                    return "onDeactivateDataCallComplete";
                case 3:
                    return "onSetInitialAttachApnComplete";
                case 4:
                    return "onSetDataProfileComplete";
                case 5:
                    return "onRequestDataCallListComplete";
                case 6:
                    return "onDataCallListChanged";
                case 7:
                    return "onHandoverStarted";
                case 8:
                    return "onHandoverCancelled";
                case 9:
                    return "onApnUnthrottled";
                case 10:
                    return "onDataProfileUnthrottled";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.telephony.data.DataCallResponse dataCallResponse = (android.telephony.data.DataCallResponse) parcel.readTypedObject(android.telephony.data.DataCallResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetupDataCallComplete(readInt, dataCallResponse);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDeactivateDataCallComplete(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetInitialAttachApnComplete(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetDataProfileComplete(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.data.DataCallResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestDataCallListComplete(readInt5, createTypedArrayList);
                    return true;
                case 6:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.telephony.data.DataCallResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataCallListChanged(createTypedArrayList2);
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHandoverStarted(readInt6);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHandoverCancelled(readInt7);
                    return true;
                case 9:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onApnUnthrottled(readString);
                    return true;
                case 10:
                    android.telephony.data.DataProfile dataProfile = (android.telephony.data.DataProfile) parcel.readTypedObject(android.telephony.data.DataProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataProfileUnthrottled(dataProfile);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.data.IDataServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR;
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onSetupDataCallComplete(int i, android.telephony.data.DataCallResponse dataCallResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataCallResponse, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onDeactivateDataCallComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onSetInitialAttachApnComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onSetDataProfileComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onRequestDataCallListComplete(int i, java.util.List<android.telephony.data.DataCallResponse> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onDataCallListChanged(java.util.List<android.telephony.data.DataCallResponse> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onHandoverStarted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onHandoverCancelled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onApnUnthrottled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onDataProfileUnthrottled(android.telephony.data.DataProfile dataProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(dataProfile, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
