package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public class FingerprintSensorConfigurations implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.fingerprint.FingerprintSensorConfigurations> CREATOR = new android.os.Parcelable.Creator<android.hardware.fingerprint.FingerprintSensorConfigurations>() { // from class: android.hardware.fingerprint.FingerprintSensorConfigurations.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.FingerprintSensorConfigurations createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.fingerprint.FingerprintSensorConfigurations(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.FingerprintSensorConfigurations[] newArray(int i) {
            return new android.hardware.fingerprint.FingerprintSensorConfigurations[i];
        }
    };
    private static final java.lang.String TAG = "FingerprintSensorConfigurations";
    private final boolean mResetLockoutRequiresHardwareAuthToken;
    private final java.util.Map<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> mSensorPropsMap;

    public FingerprintSensorConfigurations(boolean z) {
        this.mResetLockoutRequiresHardwareAuthToken = z;
        this.mSensorPropsMap = new java.util.HashMap();
    }

    public void addAidlSensors(java.lang.String[] strArr, java.util.function.Function<java.lang.String, android.hardware.biometrics.fingerprint.IFingerprint> function) {
        for (java.lang.String str : strArr) {
            try {
                android.hardware.biometrics.fingerprint.IFingerprint apply = function.apply(android.hardware.biometrics.fingerprint.IFingerprint.DESCRIPTOR + "/" + str);
                if (apply == null) {
                    android.util.Log.d(TAG, "IFingerprint null for instance " + str);
                } else {
                    this.mSensorPropsMap.put(str, apply.getSensorProps());
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "Unable to get sensor properties!");
            }
        }
    }

    public void addHidlSensors(java.lang.String[] strArr, android.content.Context context) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : strArr) {
            android.hardware.fingerprint.HidlFingerprintSensorConfig hidlFingerprintSensorConfig = new android.hardware.fingerprint.HidlFingerprintSensorConfig();
            try {
                hidlFingerprintSensorConfig.parse(str, context);
                if (hidlFingerprintSensorConfig.getModality() == 2) {
                    arrayList.add(hidlFingerprintSensorConfig);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "HIDL sensor configuration format is incorrect.");
            }
        }
        this.mSensorPropsMap.put("defaultHIDL", (android.hardware.biometrics.fingerprint.SensorProps[]) arrayList.toArray(new android.hardware.fingerprint.HidlFingerprintSensorConfig[arrayList.size()]));
    }

    protected FingerprintSensorConfigurations(android.os.Parcel parcel) {
        this.mResetLockoutRequiresHardwareAuthToken = parcel.readByte() != 0;
        this.mSensorPropsMap = parcel.readHashMap(null, java.lang.String.class, android.hardware.biometrics.fingerprint.SensorProps[].class);
    }

    public boolean hasSensorConfigurations() {
        return this.mSensorPropsMap.size() > 0;
    }

    public boolean isSingleSensorConfigurationPresent() {
        return this.mSensorPropsMap.size() == 1;
    }

    public android.util.Pair<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> getSensorPairForInstance(java.lang.String str) {
        if (this.mSensorPropsMap.containsKey(str)) {
            return new android.util.Pair<>(str, this.mSensorPropsMap.get(str));
        }
        return null;
    }

    public android.util.Pair<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> getSensorPairNotForInstance(final java.lang.String str) {
        return (android.util.Pair) this.mSensorPropsMap.keySet().stream().filter(new java.util.function.Predicate() { // from class: android.hardware.fingerprint.FingerprintSensorConfigurations$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.hardware.fingerprint.FingerprintSensorConfigurations.lambda$getSensorPairNotForInstance$0(str, (java.lang.String) obj);
            }
        }).findFirst().map(new android.hardware.fingerprint.FingerprintSensorConfigurations$$ExternalSyntheticLambda0(this)).orElseGet(new java.util.function.Supplier() { // from class: android.hardware.fingerprint.FingerprintSensorConfigurations$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return android.hardware.fingerprint.FingerprintSensorConfigurations.this.getSensorPair();
            }
        });
    }

    static /* synthetic */ boolean lambda$getSensorPairNotForInstance$0(java.lang.String str, java.lang.String str2) {
        return !str2.equals(str);
    }

    public android.util.Pair<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> getSensorPair() {
        return (android.util.Pair) this.mSensorPropsMap.keySet().stream().findFirst().map(new android.hardware.fingerprint.FingerprintSensorConfigurations$$ExternalSyntheticLambda0(this)).orElse(null);
    }

    public boolean getResetLockoutRequiresHardwareAuthToken() {
        return this.mResetLockoutRequiresHardwareAuthToken;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mResetLockoutRequiresHardwareAuthToken ? (byte) 1 : (byte) 0);
        parcel.writeMap(this.mSensorPropsMap);
    }
}
