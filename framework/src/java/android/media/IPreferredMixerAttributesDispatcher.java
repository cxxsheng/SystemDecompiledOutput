package android.media;

/* loaded from: classes2.dex */
public interface IPreferredMixerAttributesDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IPreferredMixerAttributesDispatcher";

    void dispatchPrefMixerAttributesChanged(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) throws android.os.RemoteException;

    public static class Default implements android.media.IPreferredMixerAttributesDispatcher {
        @Override // android.media.IPreferredMixerAttributesDispatcher
        public void dispatchPrefMixerAttributesChanged(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IPreferredMixerAttributesDispatcher {
        static final int TRANSACTION_dispatchPrefMixerAttributesChanged = 1;

        public Stub() {
            attachInterface(this, android.media.IPreferredMixerAttributesDispatcher.DESCRIPTOR);
        }

        public static android.media.IPreferredMixerAttributesDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IPreferredMixerAttributesDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IPreferredMixerAttributesDispatcher)) {
                return (android.media.IPreferredMixerAttributesDispatcher) queryLocalInterface;
            }
            return new android.media.IPreferredMixerAttributesDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchPrefMixerAttributesChanged";
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
                parcel.enforceInterface(android.media.IPreferredMixerAttributesDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IPreferredMixerAttributesDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    int readInt = parcel.readInt();
                    android.media.AudioMixerAttributes audioMixerAttributes = (android.media.AudioMixerAttributes) parcel.readTypedObject(android.media.AudioMixerAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchPrefMixerAttributesChanged(audioAttributes, readInt, audioMixerAttributes);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IPreferredMixerAttributesDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IPreferredMixerAttributesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IPreferredMixerAttributesDispatcher
            public void dispatchPrefMixerAttributesChanged(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPreferredMixerAttributesDispatcher.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioMixerAttributes, 0);
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
