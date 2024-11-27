package android.media;

/* loaded from: classes2.dex */
public interface ISoundDose extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ISoundDose";

    void forceComputeCsdOnAllDevices(boolean z) throws android.os.RemoteException;

    void forceUseFrameworkMel(boolean z) throws android.os.RemoteException;

    float getCsd() throws android.os.RemoteException;

    float getOutputRs2UpperBound() throws android.os.RemoteException;

    void initCachedAudioDeviceCategories(android.media.ISoundDose.AudioDeviceCategory[] audioDeviceCategoryArr) throws android.os.RemoteException;

    boolean isSoundDoseHalSupported() throws android.os.RemoteException;

    void resetCsd(float f, android.media.SoundDoseRecord[] soundDoseRecordArr) throws android.os.RemoteException;

    void setAudioDeviceCategory(android.media.ISoundDose.AudioDeviceCategory audioDeviceCategory) throws android.os.RemoteException;

    void setCsdEnabled(boolean z) throws android.os.RemoteException;

    void setOutputRs2UpperBound(float f) throws android.os.RemoteException;

    void updateAttenuation(float f, int i) throws android.os.RemoteException;

    public static class Default implements android.media.ISoundDose {
        @Override // android.media.ISoundDose
        public void setOutputRs2UpperBound(float f) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDose
        public void resetCsd(float f, android.media.SoundDoseRecord[] soundDoseRecordArr) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDose
        public void updateAttenuation(float f, int i) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDose
        public void setCsdEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDose
        public void initCachedAudioDeviceCategories(android.media.ISoundDose.AudioDeviceCategory[] audioDeviceCategoryArr) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDose
        public void setAudioDeviceCategory(android.media.ISoundDose.AudioDeviceCategory audioDeviceCategory) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDose
        public float getOutputRs2UpperBound() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.media.ISoundDose
        public float getCsd() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.media.ISoundDose
        public boolean isSoundDoseHalSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.ISoundDose
        public void forceUseFrameworkMel(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDose
        public void forceComputeCsdOnAllDevices(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ISoundDose {
        static final int TRANSACTION_forceComputeCsdOnAllDevices = 11;
        static final int TRANSACTION_forceUseFrameworkMel = 10;
        static final int TRANSACTION_getCsd = 8;
        static final int TRANSACTION_getOutputRs2UpperBound = 7;
        static final int TRANSACTION_initCachedAudioDeviceCategories = 5;
        static final int TRANSACTION_isSoundDoseHalSupported = 9;
        static final int TRANSACTION_resetCsd = 2;
        static final int TRANSACTION_setAudioDeviceCategory = 6;
        static final int TRANSACTION_setCsdEnabled = 4;
        static final int TRANSACTION_setOutputRs2UpperBound = 1;
        static final int TRANSACTION_updateAttenuation = 3;

        public Stub() {
            attachInterface(this, android.media.ISoundDose.DESCRIPTOR);
        }

        public static android.media.ISoundDose asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ISoundDose.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ISoundDose)) {
                return (android.media.ISoundDose) queryLocalInterface;
            }
            return new android.media.ISoundDose.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.ISoundDose.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ISoundDose.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setOutputRs2UpperBound(readFloat);
                    return true;
                case 2:
                    float readFloat2 = parcel.readFloat();
                    android.media.SoundDoseRecord[] soundDoseRecordArr = (android.media.SoundDoseRecord[]) parcel.createTypedArray(android.media.SoundDoseRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetCsd(readFloat2, soundDoseRecordArr);
                    return true;
                case 3:
                    float readFloat3 = parcel.readFloat();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateAttenuation(readFloat3, readInt);
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCsdEnabled(readBoolean);
                    return true;
                case 5:
                    android.media.ISoundDose.AudioDeviceCategory[] audioDeviceCategoryArr = (android.media.ISoundDose.AudioDeviceCategory[]) parcel.createTypedArray(android.media.ISoundDose.AudioDeviceCategory.CREATOR);
                    parcel.enforceNoDataAvail();
                    initCachedAudioDeviceCategories(audioDeviceCategoryArr);
                    return true;
                case 6:
                    android.media.ISoundDose.AudioDeviceCategory audioDeviceCategory = (android.media.ISoundDose.AudioDeviceCategory) parcel.readTypedObject(android.media.ISoundDose.AudioDeviceCategory.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAudioDeviceCategory(audioDeviceCategory);
                    return true;
                case 7:
                    float outputRs2UpperBound = getOutputRs2UpperBound();
                    parcel2.writeNoException();
                    parcel2.writeFloat(outputRs2UpperBound);
                    return true;
                case 8:
                    float csd = getCsd();
                    parcel2.writeNoException();
                    parcel2.writeFloat(csd);
                    return true;
                case 9:
                    boolean isSoundDoseHalSupported = isSoundDoseHalSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSoundDoseHalSupported);
                    return true;
                case 10:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceUseFrameworkMel(readBoolean2);
                    return true;
                case 11:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceComputeCsdOnAllDevices(readBoolean3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ISoundDose {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ISoundDose.DESCRIPTOR;
            }

            @Override // android.media.ISoundDose
            public void setOutputRs2UpperBound(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void resetCsd(float f, android.media.SoundDoseRecord[] soundDoseRecordArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeTypedArray(soundDoseRecordArr, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void updateAttenuation(float f, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void setCsdEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void initCachedAudioDeviceCategories(android.media.ISoundDose.AudioDeviceCategory[] audioDeviceCategoryArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeTypedArray(audioDeviceCategoryArr, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void setAudioDeviceCategory(android.media.ISoundDose.AudioDeviceCategory audioDeviceCategory) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceCategory, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public float getOutputRs2UpperBound() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public float getCsd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public boolean isSoundDoseHalSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void forceUseFrameworkMel(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void forceComputeCsdOnAllDevices(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDose.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }

    public static class AudioDeviceCategory implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.ISoundDose.AudioDeviceCategory> CREATOR = new android.os.Parcelable.Creator<android.media.ISoundDose.AudioDeviceCategory>() { // from class: android.media.ISoundDose.AudioDeviceCategory.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.ISoundDose.AudioDeviceCategory createFromParcel(android.os.Parcel parcel) {
                android.media.ISoundDose.AudioDeviceCategory audioDeviceCategory = new android.media.ISoundDose.AudioDeviceCategory();
                audioDeviceCategory.readFromParcel(parcel);
                return audioDeviceCategory;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.ISoundDose.AudioDeviceCategory[] newArray(int i) {
                return new android.media.ISoundDose.AudioDeviceCategory[i];
            }
        };
        public java.lang.String address;
        public int internalAudioType = 0;
        public boolean csdCompatible = false;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.address);
            parcel.writeInt(this.internalAudioType);
            parcel.writeBoolean(this.csdCompatible);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.address = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.internalAudioType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.csdCompatible = parcel.readBoolean();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        public java.lang.String toString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
            stringJoiner.add("address: " + java.util.Objects.toString(this.address));
            stringJoiner.add("internalAudioType: " + this.internalAudioType);
            stringJoiner.add("csdCompatible: " + this.csdCompatible);
            return "AudioDeviceCategory" + stringJoiner.toString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
