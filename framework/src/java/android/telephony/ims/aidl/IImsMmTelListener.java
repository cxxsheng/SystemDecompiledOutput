package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsMmTelListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelListener";

    void onAudioModeIsVoipChanged(int i) throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsCallSessionListener onIncomingCall(com.android.ims.internal.IImsCallSession iImsCallSession, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException;

    void onModifyImsTrafficSession(int i, int i2) throws android.os.RemoteException;

    void onRejectedCall(android.telephony.ims.ImsCallProfile imsCallProfile, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void onStartImsTrafficSession(int i, int i2, int i3, int i4, android.telephony.ims.aidl.IImsTrafficSessionCallback iImsTrafficSessionCallback) throws android.os.RemoteException;

    void onStopImsTrafficSession(int i) throws android.os.RemoteException;

    void onTriggerEpsFallback(int i) throws android.os.RemoteException;

    void onVoiceMessageCountUpdate(int i) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsMmTelListener {
        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public android.telephony.ims.aidl.IImsCallSessionListener onIncomingCall(com.android.ims.internal.IImsCallSession iImsCallSession, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onRejectedCall(android.telephony.ims.ImsCallProfile imsCallProfile, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onVoiceMessageCountUpdate(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onAudioModeIsVoipChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onTriggerEpsFallback(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStartImsTrafficSession(int i, int i2, int i3, int i4, android.telephony.ims.aidl.IImsTrafficSessionCallback iImsTrafficSessionCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onModifyImsTrafficSession(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStopImsTrafficSession(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsMmTelListener {
        static final int TRANSACTION_onAudioModeIsVoipChanged = 4;
        static final int TRANSACTION_onIncomingCall = 1;
        static final int TRANSACTION_onMediaQualityStatusChanged = 9;
        static final int TRANSACTION_onModifyImsTrafficSession = 7;
        static final int TRANSACTION_onRejectedCall = 2;
        static final int TRANSACTION_onStartImsTrafficSession = 6;
        static final int TRANSACTION_onStopImsTrafficSession = 8;
        static final int TRANSACTION_onTriggerEpsFallback = 5;
        static final int TRANSACTION_onVoiceMessageCountUpdate = 3;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsMmTelListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsMmTelListener)) {
                return (android.telephony.ims.aidl.IImsMmTelListener) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsMmTelListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onIncomingCall";
                case 2:
                    return "onRejectedCall";
                case 3:
                    return "onVoiceMessageCountUpdate";
                case 4:
                    return "onAudioModeIsVoipChanged";
                case 5:
                    return "onTriggerEpsFallback";
                case 6:
                    return "onStartImsTrafficSession";
                case 7:
                    return "onModifyImsTrafficSession";
                case 8:
                    return "onStopImsTrafficSession";
                case 9:
                    return "onMediaQualityStatusChanged";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.ims.internal.IImsCallSession asInterface = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsCallSessionListener onIncomingCall = onIncomingCall(asInterface, readString, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(onIncomingCall);
                    return true;
                case 2:
                    android.telephony.ims.ImsCallProfile imsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    android.telephony.ims.ImsReasonInfo imsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRejectedCall(imsCallProfile, imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVoiceMessageCountUpdate(readInt);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioModeIsVoipChanged(readInt2);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTriggerEpsFallback(readInt3);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    android.telephony.ims.aidl.IImsTrafficSessionCallback asInterface2 = android.telephony.ims.aidl.IImsTrafficSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onStartImsTrafficSession(readInt4, readInt5, readInt6, readInt7, asInterface2);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onModifyImsTrafficSession(readInt8, readInt9);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStopImsTrafficSession(readInt10);
                    return true;
                case 9:
                    android.telephony.ims.MediaQualityStatus mediaQualityStatus = (android.telephony.ims.MediaQualityStatus) parcel.readTypedObject(android.telephony.ims.MediaQualityStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaQualityStatusChanged(mediaQualityStatus);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsMmTelListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public android.telephony.ims.aidl.IImsCallSessionListener onIncomingCall(com.android.ims.internal.IImsCallSession iImsCallSession, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsCallSessionListener.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onRejectedCall(android.telephony.ims.ImsCallProfile imsCallProfile, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onVoiceMessageCountUpdate(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onAudioModeIsVoipChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onTriggerEpsFallback(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onStartImsTrafficSession(int i, int i2, int i3, int i4, android.telephony.ims.aidl.IImsTrafficSessionCallback iImsTrafficSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStrongInterface(iImsTrafficSessionCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onModifyImsTrafficSession(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onStopImsTrafficSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelListener.DESCRIPTOR);
                    obtain.writeTypedObject(mediaQualityStatus, 0);
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
