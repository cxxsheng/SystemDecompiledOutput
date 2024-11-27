package android.hardware.face;

/* loaded from: classes2.dex */
public class FaceSensorConfigurations implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceSensorConfigurations> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceSensorConfigurations>() { // from class: android.hardware.face.FaceSensorConfigurations.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceSensorConfigurations createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceSensorConfigurations(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceSensorConfigurations[] newArray(int i) {
            return new android.hardware.face.FaceSensorConfigurations[i];
        }
    };
    private static final java.lang.String TAG = "FaceSensorConfigurations";
    private final boolean mResetLockoutRequiresChallenge;
    private final java.util.Map<java.lang.String, android.hardware.biometrics.face.SensorProps[]> mSensorPropsMap;

    public FaceSensorConfigurations(boolean z) {
        this.mResetLockoutRequiresChallenge = z;
        this.mSensorPropsMap = new java.util.HashMap();
    }

    protected FaceSensorConfigurations(android.os.Parcel parcel) {
        this.mResetLockoutRequiresChallenge = parcel.readByte() != 0;
        this.mSensorPropsMap = parcel.readHashMap(null, java.lang.String.class, android.hardware.biometrics.face.SensorProps[].class);
    }

    public void addAidlConfigs(java.lang.String[] strArr, java.util.function.Function<java.lang.String, android.hardware.biometrics.face.IFace> function) {
        for (java.lang.String str : strArr) {
            java.lang.String str2 = android.hardware.biometrics.face.IFace.DESCRIPTOR + "/" + str;
            android.hardware.biometrics.face.IFace apply = function.apply(str2);
            if (apply == null) {
                android.util.Slog.e(TAG, "Unable to get declared service: " + str2);
            } else {
                try {
                    this.mSensorPropsMap.put(str, apply.getSensorProps());
                } catch (android.os.RemoteException e) {
                    android.util.Log.d(TAG, "Unable to get sensor properties!");
                }
            }
        }
    }

    public void addHidlConfigs(java.lang.String[] strArr, android.content.Context context) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : strArr) {
            android.hardware.face.HidlFaceSensorConfig hidlFaceSensorConfig = new android.hardware.face.HidlFaceSensorConfig();
            try {
                hidlFaceSensorConfig.parse(str, context);
                if (hidlFaceSensorConfig.getModality() == 8) {
                    arrayList.add(hidlFaceSensorConfig);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "HIDL sensor configuration format is incorrect.");
            }
        }
        this.mSensorPropsMap.put("defaultHIDL", (android.hardware.biometrics.face.SensorProps[]) arrayList.toArray(new android.hardware.biometrics.face.SensorProps[arrayList.size()]));
    }

    public boolean hasSensorConfigurations() {
        return this.mSensorPropsMap.size() > 0;
    }

    public boolean isSingleSensorConfigurationPresent() {
        return this.mSensorPropsMap.size() == 1;
    }

    public android.util.Pair<java.lang.String, android.hardware.biometrics.face.SensorProps[]> getSensorPairForInstance(java.lang.String str) {
        if (this.mSensorPropsMap.containsKey(str)) {
            return new android.util.Pair<>(str, this.mSensorPropsMap.get(str));
        }
        return null;
    }

    public android.util.Pair<java.lang.String, android.hardware.biometrics.face.SensorProps[]> getSensorPairNotForInstance(final java.lang.String str) {
        return (android.util.Pair) this.mSensorPropsMap.keySet().stream().filter(new java.util.function.Predicate() { // from class: android.hardware.face.FaceSensorConfigurations$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.hardware.face.FaceSensorConfigurations.lambda$getSensorPairNotForInstance$0(str, (java.lang.String) obj);
            }
        }).findFirst().map(new android.hardware.face.FaceSensorConfigurations$$ExternalSyntheticLambda1(this)).orElseGet(new java.util.function.Supplier() { // from class: android.hardware.face.FaceSensorConfigurations$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return android.hardware.face.FaceSensorConfigurations.this.getSensorPair();
            }
        });
    }

    static /* synthetic */ boolean lambda$getSensorPairNotForInstance$0(java.lang.String str, java.lang.String str2) {
        return !str2.equals(str);
    }

    public android.util.Pair<java.lang.String, android.hardware.biometrics.face.SensorProps[]> getSensorPair() {
        return (android.util.Pair) this.mSensorPropsMap.keySet().stream().findFirst().map(new android.hardware.face.FaceSensorConfigurations$$ExternalSyntheticLambda1(this)).orElse(null);
    }

    public boolean getResetLockoutRequiresChallenge() {
        return this.mResetLockoutRequiresChallenge;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mResetLockoutRequiresChallenge ? (byte) 1 : (byte) 0);
        parcel.writeMap(this.mSensorPropsMap);
    }
}
