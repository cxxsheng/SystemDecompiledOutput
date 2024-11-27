package android.view.autofill;

/* loaded from: classes4.dex */
public interface IAutoFillManager extends android.os.IInterface {
    void addClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, android.content.ComponentName componentName, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void cancelSession(int i, int i2) throws android.os.RemoteException;

    void disableOwnedAutofillServices(int i) throws android.os.RemoteException;

    void finishSession(int i, int i2, int i3) throws android.os.RemoteException;

    void getAutofillServiceComponentName(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void getAvailableFieldClassificationAlgorithms(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void getDefaultFieldClassificationAlgorithm(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void getFillEventHistory(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void getUserData(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void getUserDataId(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void isFieldClassificationEnabled(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void isServiceEnabled(int i, java.lang.String str, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void isServiceSupported(int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void onPendingSaveUi(int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    void removeClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, int i) throws android.os.RemoteException;

    void restoreSession(int i, android.os.IBinder iBinder, android.os.IBinder iBinder2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void setAugmentedAutofillWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void setAuthenticationResult(android.os.Bundle bundle, int i, int i2, int i3) throws android.os.RemoteException;

    void setAutofillFailure(int i, java.util.List<android.view.autofill.AutofillId> list, int i2) throws android.os.RemoteException;

    void setHasCallback(int i, int i2, boolean z) throws android.os.RemoteException;

    void setUserData(android.service.autofill.UserData userData) throws android.os.RemoteException;

    void startSession(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i, boolean z, int i2, android.content.ComponentName componentName, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void updateSession(int i, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i2, int i3, int i4) throws android.os.RemoteException;

    public static class Default implements android.view.autofill.IAutoFillManager {
        @Override // android.view.autofill.IAutoFillManager
        public void addClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, android.content.ComponentName componentName, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void removeClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, int i) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void startSession(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i, boolean z, int i2, android.content.ComponentName componentName, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getFillEventHistory(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void restoreSession(int i, android.os.IBinder iBinder, android.os.IBinder iBinder2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void updateSession(int i, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setAutofillFailure(int i, java.util.List<android.view.autofill.AutofillId> list, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void finishSession(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void cancelSession(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setAuthenticationResult(android.os.Bundle bundle, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setHasCallback(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void disableOwnedAutofillServices(int i) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void isServiceSupported(int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void isServiceEnabled(int i, java.lang.String str, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void onPendingSaveUi(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getUserData(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getUserDataId(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setUserData(android.service.autofill.UserData userData) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void isFieldClassificationEnabled(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getAutofillServiceComponentName(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getAvailableFieldClassificationAlgorithms(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getDefaultFieldClassificationAlgorithm(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setAugmentedAutofillWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.autofill.IAutoFillManager {
        public static final java.lang.String DESCRIPTOR = "android.view.autofill.IAutoFillManager";
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_cancelSession = 9;
        static final int TRANSACTION_disableOwnedAutofillServices = 12;
        static final int TRANSACTION_finishSession = 8;
        static final int TRANSACTION_getAutofillServiceComponentName = 20;
        static final int TRANSACTION_getAvailableFieldClassificationAlgorithms = 21;
        static final int TRANSACTION_getDefaultFieldClassificationAlgorithm = 22;
        static final int TRANSACTION_getFillEventHistory = 4;
        static final int TRANSACTION_getUserData = 16;
        static final int TRANSACTION_getUserDataId = 17;
        static final int TRANSACTION_isFieldClassificationEnabled = 19;
        static final int TRANSACTION_isServiceEnabled = 14;
        static final int TRANSACTION_isServiceSupported = 13;
        static final int TRANSACTION_onPendingSaveUi = 15;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_restoreSession = 5;
        static final int TRANSACTION_setAugmentedAutofillWhitelist = 23;
        static final int TRANSACTION_setAuthenticationResult = 10;
        static final int TRANSACTION_setAutofillFailure = 7;
        static final int TRANSACTION_setHasCallback = 11;
        static final int TRANSACTION_setUserData = 18;
        static final int TRANSACTION_startSession = 3;
        static final int TRANSACTION_updateSession = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.autofill.IAutoFillManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.autofill.IAutoFillManager)) {
                return (android.view.autofill.IAutoFillManager) queryLocalInterface;
            }
            return new android.view.autofill.IAutoFillManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addClient";
                case 2:
                    return "removeClient";
                case 3:
                    return "startSession";
                case 4:
                    return "getFillEventHistory";
                case 5:
                    return "restoreSession";
                case 6:
                    return "updateSession";
                case 7:
                    return "setAutofillFailure";
                case 8:
                    return "finishSession";
                case 9:
                    return "cancelSession";
                case 10:
                    return "setAuthenticationResult";
                case 11:
                    return "setHasCallback";
                case 12:
                    return "disableOwnedAutofillServices";
                case 13:
                    return "isServiceSupported";
                case 14:
                    return "isServiceEnabled";
                case 15:
                    return "onPendingSaveUi";
                case 16:
                    return "getUserData";
                case 17:
                    return "getUserDataId";
                case 18:
                    return "setUserData";
                case 19:
                    return "isFieldClassificationEnabled";
                case 20:
                    return "getAutofillServiceComponentName";
                case 21:
                    return "getAvailableFieldClassificationAlgorithms";
                case 22:
                    return "getDefaultFieldClassificationAlgorithm";
                case 23:
                    return "setAugmentedAutofillWhitelist";
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
                    android.view.autofill.IAutoFillManagerClient asInterface = android.view.autofill.IAutoFillManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    com.android.internal.os.IResultReceiver asInterface2 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addClient(asInterface, componentName, readInt, asInterface2);
                    return true;
                case 2:
                    android.view.autofill.IAutoFillManagerClient asInterface3 = android.view.autofill.IAutoFillManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeClient(asInterface3, readInt2);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.view.autofill.AutofillValue autofillValue = (android.view.autofill.AutofillValue) parcel.readTypedObject(android.view.autofill.AutofillValue.CREATOR);
                    int readInt3 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    com.android.internal.os.IResultReceiver asInterface4 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSession(readStrongBinder, readStrongBinder2, autofillId, rect, autofillValue, readInt3, readBoolean, readInt4, componentName2, readBoolean2, asInterface4);
                    return true;
                case 4:
                    com.android.internal.os.IResultReceiver asInterface5 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getFillEventHistory(asInterface5);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    com.android.internal.os.IResultReceiver asInterface6 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    restoreSession(readInt5, readStrongBinder3, readStrongBinder4, asInterface6);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.view.autofill.AutofillValue autofillValue2 = (android.view.autofill.AutofillValue) parcel.readTypedObject(android.view.autofill.AutofillValue.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSession(readInt6, autofillId2, rect2, autofillValue2, readInt7, readInt8, readInt9);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutofillFailure(readInt10, createTypedArrayList, readInt11);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSession(readInt12, readInt13, readInt14);
                    return true;
                case 9:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelSession(readInt15, readInt16);
                    return true;
                case 10:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAuthenticationResult(bundle, readInt17, readInt18, readInt19);
                    return true;
                case 11:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHasCallback(readInt20, readInt21, readBoolean3);
                    return true;
                case 12:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableOwnedAutofillServices(readInt22);
                    return true;
                case 13:
                    int readInt23 = parcel.readInt();
                    com.android.internal.os.IResultReceiver asInterface7 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isServiceSupported(readInt23, asInterface7);
                    return true;
                case 14:
                    int readInt24 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    com.android.internal.os.IResultReceiver asInterface8 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isServiceEnabled(readInt24, readString, asInterface8);
                    return true;
                case 15:
                    int readInt25 = parcel.readInt();
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onPendingSaveUi(readInt25, readStrongBinder5);
                    return true;
                case 16:
                    com.android.internal.os.IResultReceiver asInterface9 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getUserData(asInterface9);
                    return true;
                case 17:
                    com.android.internal.os.IResultReceiver asInterface10 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getUserDataId(asInterface10);
                    return true;
                case 18:
                    android.service.autofill.UserData userData = (android.service.autofill.UserData) parcel.readTypedObject(android.service.autofill.UserData.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserData(userData);
                    return true;
                case 19:
                    com.android.internal.os.IResultReceiver asInterface11 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isFieldClassificationEnabled(asInterface11);
                    return true;
                case 20:
                    com.android.internal.os.IResultReceiver asInterface12 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAutofillServiceComponentName(asInterface12);
                    return true;
                case 21:
                    com.android.internal.os.IResultReceiver asInterface13 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAvailableFieldClassificationAlgorithms(asInterface13);
                    return true;
                case 22:
                    com.android.internal.os.IResultReceiver asInterface14 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultFieldClassificationAlgorithm(asInterface14);
                    return true;
                case 23:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.content.ComponentName.CREATOR);
                    com.android.internal.os.IResultReceiver asInterface15 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setAugmentedAutofillWhitelist(createStringArrayList, createTypedArrayList2, asInterface15);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.autofill.IAutoFillManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR;
            }

            @Override // android.view.autofill.IAutoFillManager
            public void addClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, android.content.ComponentName componentName, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAutoFillManagerClient);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void removeClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAutoFillManagerClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void startSession(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i, boolean z, int i2, android.content.ComponentName componentName, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(autofillValue, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getFillEventHistory(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void restoreSession(int i, android.os.IBinder iBinder, android.os.IBinder iBinder2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void updateSession(int i, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(autofillValue, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAutofillFailure(int i, java.util.List<android.view.autofill.AutofillId> list, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void finishSession(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void cancelSession(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAuthenticationResult(android.os.Bundle bundle, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setHasCallback(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void disableOwnedAutofillServices(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isServiceSupported(int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isServiceEnabled(int i, java.lang.String str, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void onPendingSaveUi(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getUserData(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getUserDataId(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setUserData(android.service.autofill.UserData userData) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userData, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isFieldClassificationEnabled(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getAutofillServiceComponentName(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getAvailableFieldClassificationAlgorithms(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getDefaultFieldClassificationAlgorithm(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAugmentedAutofillWhitelist(java.util.List<java.lang.String> list, java.util.List<android.content.ComponentName> list2, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManager.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }
    }
}
