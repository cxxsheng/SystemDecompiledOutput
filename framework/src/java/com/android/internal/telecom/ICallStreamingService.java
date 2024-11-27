package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ICallStreamingService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ICallStreamingService";

    void onCallStreamingStarted(android.telecom.StreamingCall streamingCall) throws android.os.RemoteException;

    void onCallStreamingStateChanged(int i) throws android.os.RemoteException;

    void onCallStreamingStopped() throws android.os.RemoteException;

    void setStreamingCallAdapter(com.android.internal.telecom.IStreamingCallAdapter iStreamingCallAdapter) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ICallStreamingService {
        @Override // com.android.internal.telecom.ICallStreamingService
        public void setStreamingCallAdapter(com.android.internal.telecom.IStreamingCallAdapter iStreamingCallAdapter) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStarted(android.telecom.StreamingCall streamingCall) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStopped() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ICallStreamingService {
        static final int TRANSACTION_onCallStreamingStarted = 2;
        static final int TRANSACTION_onCallStreamingStateChanged = 4;
        static final int TRANSACTION_onCallStreamingStopped = 3;
        static final int TRANSACTION_setStreamingCallAdapter = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
        }

        public static com.android.internal.telecom.ICallStreamingService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ICallStreamingService)) {
                return (com.android.internal.telecom.ICallStreamingService) queryLocalInterface;
            }
            return new com.android.internal.telecom.ICallStreamingService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setStreamingCallAdapter";
                case 2:
                    return "onCallStreamingStarted";
                case 3:
                    return "onCallStreamingStopped";
                case 4:
                    return "onCallStreamingStateChanged";
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
                parcel.enforceInterface(com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telecom.IStreamingCallAdapter asInterface = com.android.internal.telecom.IStreamingCallAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setStreamingCallAdapter(asInterface);
                    return true;
                case 2:
                    android.telecom.StreamingCall streamingCall = (android.telecom.StreamingCall) parcel.readTypedObject(android.telecom.StreamingCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallStreamingStarted(streamingCall);
                    return true;
                case 3:
                    onCallStreamingStopped();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallStreamingStateChanged(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ICallStreamingService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ICallStreamingService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void setStreamingCallAdapter(com.android.internal.telecom.IStreamingCallAdapter iStreamingCallAdapter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
                    obtain.writeStrongInterface(iStreamingCallAdapter);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStarted(android.telecom.StreamingCall streamingCall) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
                    obtain.writeTypedObject(streamingCall, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStopped() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallStreamingService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
