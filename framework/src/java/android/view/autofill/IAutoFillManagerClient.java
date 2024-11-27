package android.view.autofill;

/* loaded from: classes4.dex */
public interface IAutoFillManagerClient extends android.os.IInterface {
    void authenticate(int i, int i2, android.content.IntentSender intentSender, android.content.Intent intent, boolean z) throws android.os.RemoteException;

    void autofill(int i, java.util.List<android.view.autofill.AutofillId> list, java.util.List<android.view.autofill.AutofillValue> list2, boolean z) throws android.os.RemoteException;

    void autofillContent(int i, android.view.autofill.AutofillId autofillId, android.content.ClipData clipData) throws android.os.RemoteException;

    void dispatchUnhandledKey(int i, android.view.autofill.AutofillId autofillId, android.view.KeyEvent keyEvent) throws android.os.RemoteException;

    void getAugmentedAutofillClient(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void notifyDisableAutofill(long j, android.content.ComponentName componentName) throws android.os.RemoteException;

    void notifyFillDialogTriggerIds(java.util.List<android.view.autofill.AutofillId> list) throws android.os.RemoteException;

    void notifyFillUiHidden(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void notifyFillUiShown(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void notifyNoFillUi(int i, android.view.autofill.AutofillId autofillId, int i2) throws android.os.RemoteException;

    void onGetCredentialException(int i, android.view.autofill.AutofillId autofillId, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onGetCredentialResponse(int i, android.view.autofill.AutofillId autofillId, android.credentials.GetCredentialResponse getCredentialResponse) throws android.os.RemoteException;

    void requestHideFillUi(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void requestHideFillUiWhenDestroyed(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void requestShowFillUi(int i, android.view.autofill.AutofillId autofillId, int i2, int i3, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) throws android.os.RemoteException;

    void requestShowSoftInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void setSaveUiState(int i, boolean z) throws android.os.RemoteException;

    void setSessionFinished(int i, java.util.List<android.view.autofill.AutofillId> list) throws android.os.RemoteException;

    void setState(int i) throws android.os.RemoteException;

    void setTrackedViews(int i, android.view.autofill.AutofillId[] autofillIdArr, boolean z, boolean z2, android.view.autofill.AutofillId[] autofillIdArr2, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException;

    void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) throws android.os.RemoteException;

    public static class Default implements android.view.autofill.IAutoFillManagerClient {
        @Override // android.view.autofill.IAutoFillManagerClient
        public void setState(int i) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofill(int i, java.util.List<android.view.autofill.AutofillId> list, java.util.List<android.view.autofill.AutofillValue> list2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialResponse(int i, android.view.autofill.AutofillId autofillId, android.credentials.GetCredentialResponse getCredentialResponse) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialException(int i, android.view.autofill.AutofillId autofillId, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofillContent(int i, android.view.autofill.AutofillId autofillId, android.content.ClipData clipData) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void authenticate(int i, int i2, android.content.IntentSender intentSender, android.content.Intent intent, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setTrackedViews(int i, android.view.autofill.AutofillId[] autofillIdArr, boolean z, boolean z2, android.view.autofill.AutofillId[] autofillIdArr2, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowFillUi(int i, android.view.autofill.AutofillId autofillId, int i2, int i3, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUi(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUiWhenDestroyed(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyNoFillUi(int i, android.view.autofill.AutofillId autofillId, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiShown(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiHidden(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void dispatchUnhandledKey(int i, android.view.autofill.AutofillId autofillId, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSaveUiState(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSessionFinished(int i, java.util.List<android.view.autofill.AutofillId> list) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void getAugmentedAutofillClient(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyDisableAutofill(long j, android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowSoftInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillDialogTriggerIds(java.util.List<android.view.autofill.AutofillId> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.autofill.IAutoFillManagerClient {
        public static final java.lang.String DESCRIPTOR = "android.view.autofill.IAutoFillManagerClient";
        static final int TRANSACTION_authenticate = 6;
        static final int TRANSACTION_autofill = 2;
        static final int TRANSACTION_autofillContent = 5;
        static final int TRANSACTION_dispatchUnhandledKey = 14;
        static final int TRANSACTION_getAugmentedAutofillClient = 18;
        static final int TRANSACTION_notifyDisableAutofill = 19;
        static final int TRANSACTION_notifyFillDialogTriggerIds = 21;
        static final int TRANSACTION_notifyFillUiHidden = 13;
        static final int TRANSACTION_notifyFillUiShown = 12;
        static final int TRANSACTION_notifyNoFillUi = 11;
        static final int TRANSACTION_onGetCredentialException = 4;
        static final int TRANSACTION_onGetCredentialResponse = 3;
        static final int TRANSACTION_requestHideFillUi = 9;
        static final int TRANSACTION_requestHideFillUiWhenDestroyed = 10;
        static final int TRANSACTION_requestShowFillUi = 8;
        static final int TRANSACTION_requestShowSoftInput = 20;
        static final int TRANSACTION_setSaveUiState = 16;
        static final int TRANSACTION_setSessionFinished = 17;
        static final int TRANSACTION_setState = 1;
        static final int TRANSACTION_setTrackedViews = 7;
        static final int TRANSACTION_startIntentSender = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.autofill.IAutoFillManagerClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.autofill.IAutoFillManagerClient)) {
                return (android.view.autofill.IAutoFillManagerClient) queryLocalInterface;
            }
            return new android.view.autofill.IAutoFillManagerClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setState";
                case 2:
                    return android.content.Context.AUTOFILL_MANAGER_SERVICE;
                case 3:
                    return "onGetCredentialResponse";
                case 4:
                    return "onGetCredentialException";
                case 5:
                    return "autofillContent";
                case 6:
                    return "authenticate";
                case 7:
                    return "setTrackedViews";
                case 8:
                    return "requestShowFillUi";
                case 9:
                    return "requestHideFillUi";
                case 10:
                    return "requestHideFillUiWhenDestroyed";
                case 11:
                    return "notifyNoFillUi";
                case 12:
                    return "notifyFillUiShown";
                case 13:
                    return "notifyFillUiHidden";
                case 14:
                    return "dispatchUnhandledKey";
                case 15:
                    return "startIntentSender";
                case 16:
                    return "setSaveUiState";
                case 17:
                    return "setSessionFinished";
                case 18:
                    return "getAugmentedAutofillClient";
                case 19:
                    return "notifyDisableAutofill";
                case 20:
                    return "requestShowSoftInput";
                case 21:
                    return "notifyFillDialogTriggerIds";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setState(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.view.autofill.AutofillValue.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    autofill(readInt2, createTypedArrayList, createTypedArrayList2, readBoolean);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    android.credentials.GetCredentialResponse getCredentialResponse = (android.credentials.GetCredentialResponse) parcel.readTypedObject(android.credentials.GetCredentialResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGetCredentialResponse(readInt3, autofillId, getCredentialResponse);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onGetCredentialException(readInt4, autofillId2, readString, readString2);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId3 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    android.content.ClipData clipData = (android.content.ClipData) parcel.readTypedObject(android.content.ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    autofillContent(readInt5, autofillId3, clipData);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    authenticate(readInt6, readInt7, intentSender, intent, readBoolean2);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    android.view.autofill.AutofillId[] autofillIdArr = (android.view.autofill.AutofillId[]) parcel.createTypedArray(android.view.autofill.AutofillId.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    android.view.autofill.AutofillId[] autofillIdArr2 = (android.view.autofill.AutofillId[]) parcel.createTypedArray(android.view.autofill.AutofillId.CREATOR);
                    android.view.autofill.AutofillId autofillId4 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTrackedViews(readInt8, autofillIdArr, readBoolean3, readBoolean4, autofillIdArr2, autofillId4);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId5 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.view.autofill.IAutofillWindowPresenter asInterface = android.view.autofill.IAutofillWindowPresenter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestShowFillUi(readInt9, autofillId5, readInt10, readInt11, rect, asInterface);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId6 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUi(readInt12, autofillId6);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId7 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUiWhenDestroyed(readInt13, autofillId7);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId8 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyNoFillUi(readInt14, autofillId8, readInt15);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId9 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillUiShown(readInt16, autofillId9);
                    return true;
                case 13:
                    int readInt17 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId10 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillUiHidden(readInt17, autofillId10);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    android.view.autofill.AutofillId autofillId11 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchUnhandledKey(readInt18, autofillId11, keyEvent);
                    return true;
                case 15:
                    android.content.IntentSender intentSender2 = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startIntentSender(intentSender2, intent2);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSaveUiState(readInt19, readBoolean5);
                    return true;
                case 17:
                    int readInt20 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSessionFinished(readInt20, createTypedArrayList3);
                    return true;
                case 18:
                    com.android.internal.os.IResultReceiver asInterface2 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAugmentedAutofillClient(asInterface2);
                    return true;
                case 19:
                    long readLong = parcel.readLong();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDisableAutofill(readLong, componentName);
                    return true;
                case 20:
                    android.view.autofill.AutofillId autofillId12 = (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestShowSoftInput(autofillId12);
                    return true;
                case 21:
                    java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillDialogTriggerIds(createTypedArrayList4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.autofill.IAutoFillManagerClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR;
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofill(int i, java.util.List<android.view.autofill.AutofillId> list, java.util.List<android.view.autofill.AutofillValue> list2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialResponse(int i, android.view.autofill.AutofillId autofillId, android.credentials.GetCredentialResponse getCredentialResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(getCredentialResponse, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialException(int i, android.view.autofill.AutofillId autofillId, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofillContent(int i, android.view.autofill.AutofillId autofillId, android.content.ClipData clipData) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void authenticate(int i, int i2, android.content.IntentSender intentSender, android.content.Intent intent, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setTrackedViews(int i, android.view.autofill.AutofillId[] autofillIdArr, boolean z, boolean z2, android.view.autofill.AutofillId[] autofillIdArr2, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(autofillIdArr, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedArray(autofillIdArr2, 0);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowFillUi(int i, android.view.autofill.AutofillId autofillId, int i2, int i3, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeStrongInterface(iAutofillWindowPresenter);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUi(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUiWhenDestroyed(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyNoFillUi(int i, android.view.autofill.AutofillId autofillId, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiShown(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiHidden(int i, android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void dispatchUnhandledKey(int i, android.view.autofill.AutofillId autofillId, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSaveUiState(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSessionFinished(int i, java.util.List<android.view.autofill.AutofillId> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void getAugmentedAutofillClient(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyDisableAutofill(long j, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowSoftInput(android.view.autofill.AutofillId autofillId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillDialogTriggerIds(java.util.List<android.view.autofill.AutofillId> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutoFillManagerClient.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }
    }
}
