package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IInlineSuggestionsResponseCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IInlineSuggestionsResponseCallback";

    void onInlineSuggestionsResponse(android.view.autofill.AutofillId autofillId, android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IInlineSuggestionsResponseCallback {
        @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
        public void onInlineSuggestionsResponse(android.view.autofill.AutofillId autofillId, android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IInlineSuggestionsResponseCallback {
        static final int TRANSACTION_onInlineSuggestionsResponse = 1;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IInlineSuggestionsResponseCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IInlineSuggestionsResponseCallback)) {
                return (com.android.internal.inputmethod.IInlineSuggestionsResponseCallback) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInlineSuggestionsResponse";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse = (android.view.inputmethod.InlineSuggestionsResponse) parcel.readTypedObject(android.view.inputmethod.InlineSuggestionsResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInlineSuggestionsResponse(autofillId, inlineSuggestionsResponse);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IInlineSuggestionsResponseCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
            public void onInlineSuggestionsResponse(android.view.autofill.AutofillId autofillId, android.view.inputmethod.InlineSuggestionsResponse inlineSuggestionsResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInlineSuggestionsResponseCallback.DESCRIPTOR);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(inlineSuggestionsResponse, 0);
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
