package android.media;

/* loaded from: classes2.dex */
public interface IAudioPolicyServiceClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IAudioPolicyServiceClient";

    void onAudioPatchListUpdate() throws android.os.RemoteException;

    void onAudioPortListUpdate() throws android.os.RemoteException;

    void onAudioVolumeGroupChanged(int i, int i2) throws android.os.RemoteException;

    void onDynamicPolicyMixStateUpdate(java.lang.String str, int i) throws android.os.RemoteException;

    void onRecordingConfigurationUpdate(int i, android.media.RecordClientInfo recordClientInfo, android.media.audio.common.AudioConfigBase audioConfigBase, android.media.EffectDescriptor[] effectDescriptorArr, android.media.audio.common.AudioConfigBase audioConfigBase2, android.media.EffectDescriptor[] effectDescriptorArr2, int i2, int i3) throws android.os.RemoteException;

    void onRoutingUpdated() throws android.os.RemoteException;

    void onVolumeRangeInitRequest() throws android.os.RemoteException;

    public static class Default implements android.media.IAudioPolicyServiceClient {
        @Override // android.media.IAudioPolicyServiceClient
        public void onAudioVolumeGroupChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onAudioPortListUpdate() throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onAudioPatchListUpdate() throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onDynamicPolicyMixStateUpdate(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onRecordingConfigurationUpdate(int i, android.media.RecordClientInfo recordClientInfo, android.media.audio.common.AudioConfigBase audioConfigBase, android.media.EffectDescriptor[] effectDescriptorArr, android.media.audio.common.AudioConfigBase audioConfigBase2, android.media.EffectDescriptor[] effectDescriptorArr2, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onRoutingUpdated() throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onVolumeRangeInitRequest() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IAudioPolicyServiceClient {
        static final int TRANSACTION_onAudioPatchListUpdate = 3;
        static final int TRANSACTION_onAudioPortListUpdate = 2;
        static final int TRANSACTION_onAudioVolumeGroupChanged = 1;
        static final int TRANSACTION_onDynamicPolicyMixStateUpdate = 4;
        static final int TRANSACTION_onRecordingConfigurationUpdate = 5;
        static final int TRANSACTION_onRoutingUpdated = 6;
        static final int TRANSACTION_onVolumeRangeInitRequest = 7;

        public Stub() {
            attachInterface(this, android.media.IAudioPolicyServiceClient.DESCRIPTOR);
        }

        public static android.media.IAudioPolicyServiceClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IAudioPolicyServiceClient)) {
                return (android.media.IAudioPolicyServiceClient) queryLocalInterface;
            }
            return new android.media.IAudioPolicyServiceClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioVolumeGroupChanged(readInt, readInt2);
                    return true;
                case 2:
                    onAudioPortListUpdate();
                    return true;
                case 3:
                    onAudioPatchListUpdate();
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDynamicPolicyMixStateUpdate(readString, readInt3);
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    android.media.RecordClientInfo recordClientInfo = (android.media.RecordClientInfo) parcel.readTypedObject(android.media.RecordClientInfo.CREATOR);
                    android.media.audio.common.AudioConfigBase audioConfigBase = (android.media.audio.common.AudioConfigBase) parcel.readTypedObject(android.media.audio.common.AudioConfigBase.CREATOR);
                    android.media.EffectDescriptor[] effectDescriptorArr = (android.media.EffectDescriptor[]) parcel.createTypedArray(android.media.EffectDescriptor.CREATOR);
                    android.media.audio.common.AudioConfigBase audioConfigBase2 = (android.media.audio.common.AudioConfigBase) parcel.readTypedObject(android.media.audio.common.AudioConfigBase.CREATOR);
                    android.media.EffectDescriptor[] effectDescriptorArr2 = (android.media.EffectDescriptor[]) parcel.createTypedArray(android.media.EffectDescriptor.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecordingConfigurationUpdate(readInt4, recordClientInfo, audioConfigBase, effectDescriptorArr, audioConfigBase2, effectDescriptorArr2, readInt5, readInt6);
                    return true;
                case 6:
                    onRoutingUpdated();
                    return true;
                case 7:
                    onVolumeRangeInitRequest();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IAudioPolicyServiceClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IAudioPolicyServiceClient.DESCRIPTOR;
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioVolumeGroupChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioPortListUpdate() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioPatchListUpdate() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onDynamicPolicyMixStateUpdate(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onRecordingConfigurationUpdate(int i, android.media.RecordClientInfo recordClientInfo, android.media.audio.common.AudioConfigBase audioConfigBase, android.media.EffectDescriptor[] effectDescriptorArr, android.media.audio.common.AudioConfigBase audioConfigBase2, android.media.EffectDescriptor[] effectDescriptorArr2, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recordClientInfo, 0);
                    obtain.writeTypedObject(audioConfigBase, 0);
                    obtain.writeTypedArray(effectDescriptorArr, 0);
                    obtain.writeTypedObject(audioConfigBase2, 0);
                    obtain.writeTypedArray(effectDescriptorArr2, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onRoutingUpdated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onVolumeRangeInitRequest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
