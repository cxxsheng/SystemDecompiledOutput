package android.media.audiopolicy;

/* loaded from: classes2.dex */
public interface IAudioPolicyCallback extends android.os.IInterface {
    void notifyAudioFocusAbandon(android.media.AudioFocusInfo audioFocusInfo) throws android.os.RemoteException;

    void notifyAudioFocusGrant(android.media.AudioFocusInfo audioFocusInfo, int i) throws android.os.RemoteException;

    void notifyAudioFocusLoss(android.media.AudioFocusInfo audioFocusInfo, boolean z) throws android.os.RemoteException;

    void notifyAudioFocusRequest(android.media.AudioFocusInfo audioFocusInfo, int i) throws android.os.RemoteException;

    void notifyMixStateUpdate(java.lang.String str, int i) throws android.os.RemoteException;

    void notifyUnregistration() throws android.os.RemoteException;

    void notifyVolumeAdjust(int i) throws android.os.RemoteException;

    public static class Default implements android.media.audiopolicy.IAudioPolicyCallback {
        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusGrant(android.media.AudioFocusInfo audioFocusInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusLoss(android.media.AudioFocusInfo audioFocusInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusRequest(android.media.AudioFocusInfo audioFocusInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusAbandon(android.media.AudioFocusInfo audioFocusInfo) throws android.os.RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyMixStateUpdate(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyVolumeAdjust(int i) throws android.os.RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyUnregistration() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.audiopolicy.IAudioPolicyCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.audiopolicy.IAudioPolicyCallback";
        static final int TRANSACTION_notifyAudioFocusAbandon = 4;
        static final int TRANSACTION_notifyAudioFocusGrant = 1;
        static final int TRANSACTION_notifyAudioFocusLoss = 2;
        static final int TRANSACTION_notifyAudioFocusRequest = 3;
        static final int TRANSACTION_notifyMixStateUpdate = 5;
        static final int TRANSACTION_notifyUnregistration = 7;
        static final int TRANSACTION_notifyVolumeAdjust = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.audiopolicy.IAudioPolicyCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.audiopolicy.IAudioPolicyCallback)) {
                return (android.media.audiopolicy.IAudioPolicyCallback) queryLocalInterface;
            }
            return new android.media.audiopolicy.IAudioPolicyCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyAudioFocusGrant";
                case 2:
                    return "notifyAudioFocusLoss";
                case 3:
                    return "notifyAudioFocusRequest";
                case 4:
                    return "notifyAudioFocusAbandon";
                case 5:
                    return "notifyMixStateUpdate";
                case 6:
                    return "notifyVolumeAdjust";
                case 7:
                    return "notifyUnregistration";
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
                    android.media.AudioFocusInfo audioFocusInfo = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusGrant(audioFocusInfo, readInt);
                    return true;
                case 2:
                    android.media.AudioFocusInfo audioFocusInfo2 = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusLoss(audioFocusInfo2, readBoolean);
                    return true;
                case 3:
                    android.media.AudioFocusInfo audioFocusInfo3 = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusRequest(audioFocusInfo3, readInt2);
                    return true;
                case 4:
                    android.media.AudioFocusInfo audioFocusInfo4 = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusAbandon(audioFocusInfo4);
                    return true;
                case 5:
                    java.lang.String readString = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyMixStateUpdate(readString, readInt3);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVolumeAdjust(readInt4);
                    return true;
                case 7:
                    notifyUnregistration();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.audiopolicy.IAudioPolicyCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusGrant(android.media.AudioFocusInfo audioFocusInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusLoss(android.media.AudioFocusInfo audioFocusInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusRequest(android.media.AudioFocusInfo audioFocusInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusAbandon(android.media.AudioFocusInfo audioFocusInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyMixStateUpdate(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyVolumeAdjust(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyUnregistration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.audiopolicy.IAudioPolicyCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
