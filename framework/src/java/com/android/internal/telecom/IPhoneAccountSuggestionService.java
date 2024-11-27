package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IPhoneAccountSuggestionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IPhoneAccountSuggestionService";

    void onAccountSuggestionRequest(com.android.internal.telecom.IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IPhoneAccountSuggestionService {
        @Override // com.android.internal.telecom.IPhoneAccountSuggestionService
        public void onAccountSuggestionRequest(com.android.internal.telecom.IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IPhoneAccountSuggestionService {
        static final int TRANSACTION_onAccountSuggestionRequest = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.IPhoneAccountSuggestionService.DESCRIPTOR);
        }

        public static com.android.internal.telecom.IPhoneAccountSuggestionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.IPhoneAccountSuggestionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IPhoneAccountSuggestionService)) {
                return (com.android.internal.telecom.IPhoneAccountSuggestionService) queryLocalInterface;
            }
            return new com.android.internal.telecom.IPhoneAccountSuggestionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAccountSuggestionRequest";
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
                parcel.enforceInterface(com.android.internal.telecom.IPhoneAccountSuggestionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.IPhoneAccountSuggestionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telecom.IPhoneAccountSuggestionCallback asInterface = com.android.internal.telecom.IPhoneAccountSuggestionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAccountSuggestionRequest(asInterface, readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IPhoneAccountSuggestionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IPhoneAccountSuggestionService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IPhoneAccountSuggestionService
            public void onAccountSuggestionRequest(com.android.internal.telecom.IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IPhoneAccountSuggestionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iPhoneAccountSuggestionCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
