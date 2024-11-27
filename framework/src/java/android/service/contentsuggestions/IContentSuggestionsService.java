package android.service.contentsuggestions;

/* loaded from: classes3.dex */
public interface IContentSuggestionsService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.contentsuggestions.IContentSuggestionsService";

    void classifyContentSelections(android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) throws android.os.RemoteException;

    void notifyInteraction(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void provideContextImage(int i, android.hardware.HardwareBuffer hardwareBuffer, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    void suggestContentSelections(android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) throws android.os.RemoteException;

    public static class Default implements android.service.contentsuggestions.IContentSuggestionsService {
        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void provideContextImage(int i, android.hardware.HardwareBuffer hardwareBuffer, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void suggestContentSelections(android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) throws android.os.RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void classifyContentSelections(android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) throws android.os.RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void notifyInteraction(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.contentsuggestions.IContentSuggestionsService {
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        public Stub() {
            attachInterface(this, android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
        }

        public static android.service.contentsuggestions.IContentSuggestionsService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.contentsuggestions.IContentSuggestionsService)) {
                return (android.service.contentsuggestions.IContentSuggestionsService) queryLocalInterface;
            }
            return new android.service.contentsuggestions.IContentSuggestionsService.Stub.Proxy(iBinder);
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
                    return "suggestContentSelections";
                case 3:
                    return "classifyContentSelections";
                case 4:
                    return "notifyInteraction";
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
                parcel.enforceInterface(android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.hardware.HardwareBuffer hardwareBuffer = (android.hardware.HardwareBuffer) parcel.readTypedObject(android.hardware.HardwareBuffer.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideContextImage(readInt, hardwareBuffer, readInt2, bundle);
                    return true;
                case 2:
                    android.app.contentsuggestions.SelectionsRequest selectionsRequest = (android.app.contentsuggestions.SelectionsRequest) parcel.readTypedObject(android.app.contentsuggestions.SelectionsRequest.CREATOR);
                    android.app.contentsuggestions.ISelectionsCallback asInterface = android.app.contentsuggestions.ISelectionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    suggestContentSelections(selectionsRequest, asInterface);
                    return true;
                case 3:
                    android.app.contentsuggestions.ClassificationsRequest classificationsRequest = (android.app.contentsuggestions.ClassificationsRequest) parcel.readTypedObject(android.app.contentsuggestions.ClassificationsRequest.CREATOR);
                    android.app.contentsuggestions.IClassificationsCallback asInterface2 = android.app.contentsuggestions.IClassificationsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    classifyContentSelections(classificationsRequest, asInterface2);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyInteraction(readString, bundle2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.contentsuggestions.IContentSuggestionsService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR;
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void provideContextImage(int i, android.hardware.HardwareBuffer hardwareBuffer, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hardwareBuffer, 0);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void suggestContentSelections(android.app.contentsuggestions.SelectionsRequest selectionsRequest, android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeTypedObject(selectionsRequest, 0);
                    obtain.writeStrongInterface(iSelectionsCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void classifyContentSelections(android.app.contentsuggestions.ClassificationsRequest classificationsRequest, android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeTypedObject(classificationsRequest, 0);
                    obtain.writeStrongInterface(iClassificationsCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void notifyInteraction(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentsuggestions.IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
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
