package android.view.autofill;

/* loaded from: classes4.dex */
public interface IAugmentedAutofillManagerClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.autofill.IAugmentedAutofillManagerClient";

    void autofill(int i, java.util.List<android.view.autofill.AutofillId> list, java.util.List<android.view.autofill.AutofillValue> list2, boolean z) throws android.os.RemoteException;

    android.graphics.Rect getViewCoordinates(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    android.app.assist.AssistStructure.ViewNodeParcelable getViewNodeParcelable(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    boolean requestAutofill(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void requestHideFillUi(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void requestShowFillUi(int i, android.view.autofill.AutofillId autofillId, int i2, int i3, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) throws android.os.RemoteException;

    public static class Default implements android.view.autofill.IAugmentedAutofillManagerClient {
        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public android.graphics.Rect getViewCoordinates(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public android.app.assist.AssistStructure.ViewNodeParcelable getViewNodeParcelable(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void autofill(int i, java.util.List<android.view.autofill.AutofillId> list, java.util.List<android.view.autofill.AutofillValue> list2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestShowFillUi(int i, android.view.autofill.AutofillId autofillId, int i2, int i3, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestHideFillUi(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public boolean requestAutofill(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.autofill.IAugmentedAutofillManagerClient {
        static final int TRANSACTION_autofill = 3;
        static final int TRANSACTION_getViewCoordinates = 1;
        static final int TRANSACTION_getViewNodeParcelable = 2;
        static final int TRANSACTION_requestAutofill = 6;
        static final int TRANSACTION_requestHideFillUi = 5;
        static final int TRANSACTION_requestShowFillUi = 4;

        public Stub() {
            attachInterface(this, android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
        }

        public static android.view.autofill.IAugmentedAutofillManagerClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.autofill.IAugmentedAutofillManagerClient)) {
                return (android.view.autofill.IAugmentedAutofillManagerClient) queryLocalInterface;
            }
            return new android.view.autofill.IAugmentedAutofillManagerClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getViewCoordinates";
                case 2:
                    return "getViewNodeParcelable";
                case 3:
                    return android.content.Context.AUTOFILL_MANAGER_SERVICE;
                case 4:
                    return "requestShowFillUi";
                case 5:
                    return "requestHideFillUi";
                case 6:
                    return "requestAutofill";
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
                parcel.enforceInterface(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.graphics.Rect viewCoordinates = getViewCoordinates(autofillId);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(viewCoordinates, 1);
                    return true;
                case 2:
                    android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.app.assist.AssistStructure.ViewNodeParcelable viewNodeParcelable = getViewNodeParcelable(autofillId2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(viewNodeParcelable, 1);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.view.autofill.AutofillValue.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    autofill(readInt, createTypedArrayList, createTypedArrayList2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId3 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.view.autofill.IAutofillWindowPresenter asInterface = android.view.autofill.IAutofillWindowPresenter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestShowFillUi(readInt2, autofillId3, readInt3, readInt4, rect, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId4 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUi(readInt5, autofillId4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId5 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestAutofill = requestAutofill(readInt6, autofillId5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAutofill);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.autofill.IAugmentedAutofillManagerClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR;
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public android.graphics.Rect getViewCoordinates(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Rect) obtain2.readTypedObject(android.graphics.Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public android.app.assist.AssistStructure.ViewNodeParcelable getViewNodeParcelable(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.assist.AssistStructure.ViewNodeParcelable) obtain2.readTypedObject(android.app.assist.AssistStructure.ViewNodeParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void autofill(int i, java.util.List<android.view.autofill.AutofillId> list, java.util.List<android.view.autofill.AutofillValue> list2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void requestShowFillUi(int i, android.view.autofill.AutofillId autofillId, int i2, int i3, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeStrongInterface(iAutofillWindowPresenter);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void requestHideFillUi(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public boolean requestAutofill(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAugmentedAutofillManagerClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
