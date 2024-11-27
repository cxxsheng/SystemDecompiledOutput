package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ICallRedirectionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ICallRedirectionService";

    void notifyTimeout() throws android.os.RemoteException;

    void placeCall(com.android.internal.telecom.ICallRedirectionAdapter iCallRedirectionAdapter, android.net.Uri uri, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ICallRedirectionService {
        @Override // com.android.internal.telecom.ICallRedirectionService
        public void placeCall(com.android.internal.telecom.ICallRedirectionAdapter iCallRedirectionAdapter, android.net.Uri uri, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void notifyTimeout() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ICallRedirectionService {
        static final int TRANSACTION_notifyTimeout = 2;
        static final int TRANSACTION_placeCall = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.ICallRedirectionService.DESCRIPTOR);
        }

        public static com.android.internal.telecom.ICallRedirectionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.ICallRedirectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ICallRedirectionService)) {
                return (com.android.internal.telecom.ICallRedirectionService) queryLocalInterface;
            }
            return new com.android.internal.telecom.ICallRedirectionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "placeCall";
                case 2:
                    return "notifyTimeout";
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
                parcel.enforceInterface(com.android.internal.telecom.ICallRedirectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.ICallRedirectionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telecom.ICallRedirectionAdapter asInterface = com.android.internal.telecom.ICallRedirectionAdapter.Stub.asInterface(parcel.readStrongBinder());
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    placeCall(asInterface, uri, phoneAccountHandle, readBoolean);
                    return true;
                case 2:
                    notifyTimeout();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ICallRedirectionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ICallRedirectionService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallRedirectionService
            public void placeCall(com.android.internal.telecom.ICallRedirectionAdapter iCallRedirectionAdapter, android.net.Uri uri, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallRedirectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCallRedirectionAdapter);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallRedirectionService
            public void notifyTimeout() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ICallRedirectionService.DESCRIPTOR);
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
