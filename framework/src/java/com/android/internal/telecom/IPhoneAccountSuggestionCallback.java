package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IPhoneAccountSuggestionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IPhoneAccountSuggestionCallback";

    void suggestPhoneAccounts(java.lang.String str, java.util.List<android.telecom.PhoneAccountSuggestion> list) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IPhoneAccountSuggestionCallback {
        @Override // com.android.internal.telecom.IPhoneAccountSuggestionCallback
        public void suggestPhoneAccounts(java.lang.String str, java.util.List<android.telecom.PhoneAccountSuggestion> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IPhoneAccountSuggestionCallback {
        static final int TRANSACTION_suggestPhoneAccounts = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.IPhoneAccountSuggestionCallback.DESCRIPTOR);
        }

        public static com.android.internal.telecom.IPhoneAccountSuggestionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.IPhoneAccountSuggestionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IPhoneAccountSuggestionCallback)) {
                return (com.android.internal.telecom.IPhoneAccountSuggestionCallback) queryLocalInterface;
            }
            return new com.android.internal.telecom.IPhoneAccountSuggestionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "suggestPhoneAccounts";
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
                parcel.enforceInterface(com.android.internal.telecom.IPhoneAccountSuggestionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.IPhoneAccountSuggestionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telecom.PhoneAccountSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestPhoneAccounts(readString, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IPhoneAccountSuggestionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IPhoneAccountSuggestionCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IPhoneAccountSuggestionCallback
            public void suggestPhoneAccounts(java.lang.String str, java.util.List<android.telecom.PhoneAccountSuggestion> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IPhoneAccountSuggestionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
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
