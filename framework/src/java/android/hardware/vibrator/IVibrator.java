package android.hardware.vibrator;

/* loaded from: classes2.dex */
public interface IVibrator extends android.os.IInterface {
    public static final int CAP_ALWAYS_ON_CONTROL = 64;
    public static final int CAP_AMPLITUDE_CONTROL = 4;
    public static final int CAP_COMPOSE_EFFECTS = 32;
    public static final int CAP_COMPOSE_PWLE_EFFECTS = 1024;
    public static final int CAP_EXTERNAL_AMPLITUDE_CONTROL = 16;
    public static final int CAP_EXTERNAL_CONTROL = 8;
    public static final int CAP_FREQUENCY_CONTROL = 512;
    public static final int CAP_GET_Q_FACTOR = 256;
    public static final int CAP_GET_RESONANT_FREQUENCY = 128;
    public static final int CAP_ON_CALLBACK = 1;
    public static final int CAP_PERFORM_CALLBACK = 2;
    public static final java.lang.String DESCRIPTOR = "android$hardware$vibrator$IVibrator".replace('$', '.');
    public static final java.lang.String HASH = "ea8742d6993e1a82917da38b9938e537aa7fcb54";
    public static final int VERSION = 2;

    void alwaysOnDisable(int i) throws android.os.RemoteException;

    void alwaysOnEnable(int i, int i2, byte b) throws android.os.RemoteException;

    void compose(android.hardware.vibrator.CompositeEffect[] compositeEffectArr, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException;

    void composePwle(android.hardware.vibrator.PrimitivePwle[] primitivePwleArr, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException;

    float[] getBandwidthAmplitudeMap() throws android.os.RemoteException;

    int getCapabilities() throws android.os.RemoteException;

    int getCompositionDelayMax() throws android.os.RemoteException;

    int getCompositionSizeMax() throws android.os.RemoteException;

    float getFrequencyMinimum() throws android.os.RemoteException;

    float getFrequencyResolution() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    int getPrimitiveDuration(int i) throws android.os.RemoteException;

    int getPwleCompositionSizeMax() throws android.os.RemoteException;

    int getPwlePrimitiveDurationMax() throws android.os.RemoteException;

    float getQFactor() throws android.os.RemoteException;

    float getResonantFrequency() throws android.os.RemoteException;

    int[] getSupportedAlwaysOnEffects() throws android.os.RemoteException;

    int[] getSupportedBraking() throws android.os.RemoteException;

    int[] getSupportedEffects() throws android.os.RemoteException;

    int[] getSupportedPrimitives() throws android.os.RemoteException;

    void off() throws android.os.RemoteException;

    void on(int i, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException;

    int perform(int i, byte b, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException;

    void setAmplitude(float f) throws android.os.RemoteException;

    void setExternalControl(boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.vibrator.IVibrator {
        @Override // android.hardware.vibrator.IVibrator
        public int getCapabilities() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void off() throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void on(int i, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public int perform(int i, byte b, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedEffects() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void setAmplitude(float f) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void setExternalControl(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCompositionDelayMax() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCompositionSizeMax() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedPrimitives() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPrimitiveDuration(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void compose(android.hardware.vibrator.CompositeEffect[] compositeEffectArr, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedAlwaysOnEffects() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void alwaysOnEnable(int i, int i2, byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void alwaysOnDisable(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getResonantFrequency() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getQFactor() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getFrequencyResolution() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getFrequencyMinimum() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float[] getBandwidthAmplitudeMap() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwlePrimitiveDurationMax() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleCompositionSizeMax() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedBraking() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void composePwle(android.hardware.vibrator.PrimitivePwle[] primitivePwleArr, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.vibrator.IVibrator {
        static final int TRANSACTION_alwaysOnDisable = 15;
        static final int TRANSACTION_alwaysOnEnable = 14;
        static final int TRANSACTION_compose = 12;
        static final int TRANSACTION_composePwle = 24;
        static final int TRANSACTION_getBandwidthAmplitudeMap = 20;
        static final int TRANSACTION_getCapabilities = 1;
        static final int TRANSACTION_getCompositionDelayMax = 8;
        static final int TRANSACTION_getCompositionSizeMax = 9;
        static final int TRANSACTION_getFrequencyMinimum = 19;
        static final int TRANSACTION_getFrequencyResolution = 18;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPrimitiveDuration = 11;
        static final int TRANSACTION_getPwleCompositionSizeMax = 22;
        static final int TRANSACTION_getPwlePrimitiveDurationMax = 21;
        static final int TRANSACTION_getQFactor = 17;
        static final int TRANSACTION_getResonantFrequency = 16;
        static final int TRANSACTION_getSupportedAlwaysOnEffects = 13;
        static final int TRANSACTION_getSupportedBraking = 23;
        static final int TRANSACTION_getSupportedEffects = 5;
        static final int TRANSACTION_getSupportedPrimitives = 10;
        static final int TRANSACTION_off = 2;
        static final int TRANSACTION_on = 3;
        static final int TRANSACTION_perform = 4;
        static final int TRANSACTION_setAmplitude = 6;
        static final int TRANSACTION_setExternalControl = 7;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.vibrator.IVibrator asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.vibrator.IVibrator)) {
                return (android.hardware.vibrator.IVibrator) queryLocalInterface;
            }
            return new android.hardware.vibrator.IVibrator.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int capabilities = getCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(capabilities);
                    return true;
                case 2:
                    off();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    android.hardware.vibrator.IVibratorCallback asInterface = android.hardware.vibrator.IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    on(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    android.hardware.vibrator.IVibratorCallback asInterface2 = android.hardware.vibrator.IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int perform = perform(readInt2, readByte, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(perform);
                    return true;
                case 5:
                    int[] supportedEffects = getSupportedEffects();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedEffects);
                    return true;
                case 6:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAmplitude(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setExternalControl(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int compositionDelayMax = getCompositionDelayMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(compositionDelayMax);
                    return true;
                case 9:
                    int compositionSizeMax = getCompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(compositionSizeMax);
                    return true;
                case 10:
                    int[] supportedPrimitives = getSupportedPrimitives();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedPrimitives);
                    return true;
                case 11:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int primitiveDuration = getPrimitiveDuration(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(primitiveDuration);
                    return true;
                case 12:
                    android.hardware.vibrator.CompositeEffect[] compositeEffectArr = (android.hardware.vibrator.CompositeEffect[]) parcel.createTypedArray(android.hardware.vibrator.CompositeEffect.CREATOR);
                    android.hardware.vibrator.IVibratorCallback asInterface3 = android.hardware.vibrator.IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    compose(compositeEffectArr, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] supportedAlwaysOnEffects = getSupportedAlwaysOnEffects();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedAlwaysOnEffects);
                    return true;
                case 14:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    alwaysOnEnable(readInt4, readInt5, readByte2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    alwaysOnDisable(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    float resonantFrequency = getResonantFrequency();
                    parcel2.writeNoException();
                    parcel2.writeFloat(resonantFrequency);
                    return true;
                case 17:
                    float qFactor = getQFactor();
                    parcel2.writeNoException();
                    parcel2.writeFloat(qFactor);
                    return true;
                case 18:
                    float frequencyResolution = getFrequencyResolution();
                    parcel2.writeNoException();
                    parcel2.writeFloat(frequencyResolution);
                    return true;
                case 19:
                    float frequencyMinimum = getFrequencyMinimum();
                    parcel2.writeNoException();
                    parcel2.writeFloat(frequencyMinimum);
                    return true;
                case 20:
                    float[] bandwidthAmplitudeMap = getBandwidthAmplitudeMap();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(bandwidthAmplitudeMap);
                    return true;
                case 21:
                    int pwlePrimitiveDurationMax = getPwlePrimitiveDurationMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwlePrimitiveDurationMax);
                    return true;
                case 22:
                    int pwleCompositionSizeMax = getPwleCompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleCompositionSizeMax);
                    return true;
                case 23:
                    int[] supportedBraking = getSupportedBraking();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedBraking);
                    return true;
                case 24:
                    android.hardware.vibrator.PrimitivePwle[] primitivePwleArr = (android.hardware.vibrator.PrimitivePwle[]) parcel.createTypedArray(android.hardware.vibrator.PrimitivePwle.CREATOR);
                    android.hardware.vibrator.IVibratorCallback asInterface4 = android.hardware.vibrator.IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    composePwle(primitivePwleArr, asInterface4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.vibrator.IVibrator {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCapabilities() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCapabilities is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void off() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method off is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void on(int i, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method on is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int perform(int i, byte b, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method perform is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedEffects() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSupportedEffects is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void setAmplitude(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeFloat(f);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setAmplitude is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void setExternalControl(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setExternalControl is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCompositionDelayMax() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCompositionDelayMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCompositionSizeMax() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCompositionSizeMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedPrimitives() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSupportedPrimitives is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPrimitiveDuration(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getPrimitiveDuration is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void compose(android.hardware.vibrator.CompositeEffect[] compositeEffectArr, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(compositeEffectArr, 0);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method compose is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedAlwaysOnEffects() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSupportedAlwaysOnEffects is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void alwaysOnEnable(int i, int i2, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method alwaysOnEnable is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void alwaysOnDisable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method alwaysOnDisable is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getResonantFrequency() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getResonantFrequency is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getQFactor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getQFactor is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getFrequencyResolution() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getFrequencyResolution is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getFrequencyMinimum() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getFrequencyMinimum is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float[] getBandwidthAmplitudeMap() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getBandwidthAmplitudeMap is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwlePrimitiveDurationMax() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getPwlePrimitiveDurationMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleCompositionSizeMax() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getPwleCompositionSizeMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedBraking() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSupportedBraking is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void composePwle(android.hardware.vibrator.PrimitivePwle[] primitivePwleArr, android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(primitivePwleArr, 0);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(24, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method composePwle is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.vibrator.IVibrator
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
