package android.app.contentsuggestions;

/* loaded from: classes.dex */
public interface IContentSuggestionsManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.contentsuggestions.IContentSuggestionsManager";

    void classifyContentSelections(int i, android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) throws android.os.RemoteException;

    void isEnabled(int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void notifyInteraction(int i, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void provideContextBitmap(int i, android.graphics.Bitmap bitmap, android.os.Bundle bundle) throws android.os.RemoteException;

    void provideContextImage(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    void resetTemporaryService(int i) throws android.os.RemoteException;

    void setDefaultServiceEnabled(int i, boolean z) throws android.os.RemoteException;

    void setTemporaryService(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void suggestContentSelections(int i, android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) throws android.os.RemoteException;

    public static class Default implements android.app.contentsuggestions.IContentSuggestionsManager {
        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void provideContextImage(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void provideContextBitmap(int i, android.graphics.Bitmap bitmap, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void suggestContentSelections(int i, android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void classifyContentSelections(int i, android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void notifyInteraction(int i, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void isEnabled(int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void resetTemporaryService(int i) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void setTemporaryService(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void setDefaultServiceEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.contentsuggestions.IContentSuggestionsManager {
        static final int TRANSACTION_classifyContentSelections = 4;
        static final int TRANSACTION_isEnabled = 6;
        static final int TRANSACTION_notifyInteraction = 5;
        static final int TRANSACTION_provideContextBitmap = 2;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_resetTemporaryService = 7;
        static final int TRANSACTION_setDefaultServiceEnabled = 9;
        static final int TRANSACTION_setTemporaryService = 8;
        static final int TRANSACTION_suggestContentSelections = 3;

        public Stub() {
            attachInterface(this, android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
        }

        public static android.app.contentsuggestions.IContentSuggestionsManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.contentsuggestions.IContentSuggestionsManager)) {
                return (android.app.contentsuggestions.IContentSuggestionsManager) queryLocalInterface;
            }
            return new android.app.contentsuggestions.IContentSuggestionsManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "provideContextImage";
                case 2:
                    return "provideContextBitmap";
                case 3:
                    return "suggestContentSelections";
                case 4:
                    return "classifyContentSelections";
                case 5:
                    return "notifyInteraction";
                case 6:
                    return "isEnabled";
                case 7:
                    return "resetTemporaryService";
                case 8:
                    return "setTemporaryService";
                case 9:
                    return "setDefaultServiceEnabled";
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
                parcel.enforceInterface(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideContextImage(readInt, readInt2, bundle);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideContextBitmap(readInt3, bitmap, bundle2);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    android.app.contentsuggestions.SelectionsRequest selectionsRequest = (android.app.contentsuggestions.SelectionsRequest) parcel.readTypedObject(android.app.contentsuggestions.SelectionsRequest.CREATOR);
                    android.app.contentsuggestions.ISelectionsCallback asInterface = android.app.contentsuggestions.ISelectionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    suggestContentSelections(readInt4, selectionsRequest, asInterface);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    android.app.contentsuggestions.ClassificationsRequest classificationsRequest = (android.app.contentsuggestions.ClassificationsRequest) parcel.readTypedObject(android.app.contentsuggestions.ClassificationsRequest.CREATOR);
                    android.app.contentsuggestions.IClassificationsCallback asInterface2 = android.app.contentsuggestions.IClassificationsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    classifyContentSelections(readInt5, classificationsRequest, asInterface2);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyInteraction(readInt6, readString, bundle3);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    com.android.internal.os.IResultReceiver asInterface3 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isEnabled(readInt7, asInterface3);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetTemporaryService(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTemporaryService(readInt9, readString2, readInt10);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDefaultServiceEnabled(readInt11, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.contentsuggestions.IContentSuggestionsManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR;
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void provideContextImage(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void provideContextBitmap(int i, android.graphics.Bitmap bitmap, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void suggestContentSelections(int i, android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(selectionsRequest, 0);
                    obtain.writeStrongInterface(iSelectionsCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void classifyContentSelections(int i, android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(classificationsRequest, 0);
                    obtain.writeStrongInterface(iClassificationsCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void notifyInteraction(int i, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void isEnabled(int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void resetTemporaryService(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void setTemporaryService(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void setDefaultServiceEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
