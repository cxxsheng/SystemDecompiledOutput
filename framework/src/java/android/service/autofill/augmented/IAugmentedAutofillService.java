package android.service.autofill.augmented;

/* loaded from: classes3.dex */
public interface IAugmentedAutofillService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.autofill.augmented.IAugmentedAutofillService";

    void onConnected(boolean z, boolean z2) throws android.os.RemoteException;

    void onDestroyAllFillWindowsRequest() throws android.os.RemoteException;

    void onDisconnected() throws android.os.RemoteException;

    void onFillRequest(int i, android.os.IBinder iBinder, int i2, android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, long j, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, android.service.autofill.augmented.IFillCallback iFillCallback) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.augmented.IAugmentedAutofillService {
        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onConnected(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDisconnected() throws android.os.RemoteException {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onFillRequest(int i, android.os.IBinder iBinder, int i2, android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, long j, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, android.service.autofill.augmented.IFillCallback iFillCallback) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDestroyAllFillWindowsRequest() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.augmented.IAugmentedAutofillService {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDestroyAllFillWindowsRequest = 4;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onFillRequest = 3;

        public Stub() {
            attachInterface(this, android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
        }

        public static android.service.autofill.augmented.IAugmentedAutofillService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.augmented.IAugmentedAutofillService)) {
                return (android.service.autofill.augmented.IAugmentedAutofillService) queryLocalInterface;
            }
            return new android.service.autofill.augmented.IAugmentedAutofillService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnected";
                case 2:
                    return "onDisconnected";
                case 3:
                    return "onFillRequest";
                case 4:
                    return "onDestroyAllFillWindowsRequest";
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
                parcel.enforceInterface(android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnected(readBoolean, readBoolean2);
                    return true;
                case 2:
                    onDisconnected();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    android.view.autofill.AutofillValue autofillValue = (android.view.autofill.AutofillValue) parcel.readTypedObject(android.view.autofill.AutofillValue.CREATOR);
                    long readLong = parcel.readLong();
                    android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest = (android.view.inputmethod.InlineSuggestionsRequest) parcel.readTypedObject(android.view.inputmethod.InlineSuggestionsRequest.CREATOR);
                    android.service.autofill.augmented.IFillCallback asInterface = android.service.autofill.augmented.IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFillRequest(readInt, readStrongBinder, readInt2, componentName, autofillId, autofillValue, readLong, inlineSuggestionsRequest, asInterface);
                    return true;
                case 4:
                    onDestroyAllFillWindowsRequest();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.augmented.IAugmentedAutofillService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR;
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onConnected(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onDisconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onFillRequest(int i, android.os.IBinder iBinder, int i2, android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, long j, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, android.service.autofill.augmented.IFillCallback iFillCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(autofillValue, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(inlineSuggestionsRequest, 0);
                    obtain.writeStrongInterface(iFillCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onDestroyAllFillWindowsRequest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IAugmentedAutofillService.DESCRIPTOR);
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
