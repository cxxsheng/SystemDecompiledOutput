package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class DeviceStateSensorOrientationMap {
    public static final long FOLDED = 4;
    public static final long NORMAL = 0;
    private final java.util.HashMap<java.lang.Long, java.lang.Integer> mDeviceStateOrientationMap;
    private final long[] mElements;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceState {
    }

    public DeviceStateSensorOrientationMap(long[] jArr) {
        this.mElements = (long[]) java.util.Objects.requireNonNull(jArr, "elements must not be null");
        this.mDeviceStateOrientationMap = new java.util.HashMap<>();
        if (jArr.length % 2 != 0) {
            throw new java.lang.IllegalArgumentException("Device state sensor orientation map length " + jArr.length + " is not even!");
        }
        for (int i = 0; i < jArr.length; i += 2) {
            int i2 = i + 1;
            if (jArr[i2] % 90 != 0) {
                throw new java.lang.IllegalArgumentException("Sensor orientation not divisible by 90: " + jArr[i2]);
            }
            this.mDeviceStateOrientationMap.put(java.lang.Long.valueOf(jArr[i]), java.lang.Integer.valueOf(java.lang.Math.toIntExact(jArr[i2])));
        }
    }

    private DeviceStateSensorOrientationMap(java.util.ArrayList<java.lang.Long> arrayList, java.util.HashMap<java.lang.Long, java.lang.Integer> hashMap) {
        this.mElements = new long[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            this.mElements[i] = arrayList.get(i).longValue();
        }
        this.mDeviceStateOrientationMap = hashMap;
    }

    public int getSensorOrientation(long j) {
        if (!this.mDeviceStateOrientationMap.containsKey(java.lang.Long.valueOf(j))) {
            throw new java.lang.IllegalArgumentException("Invalid device state: " + j);
        }
        return this.mDeviceStateOrientationMap.get(java.lang.Long.valueOf(j)).intValue();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.DeviceStateSensorOrientationMap)) {
            return false;
        }
        return java.util.Arrays.equals(this.mElements, ((android.hardware.camera2.params.DeviceStateSensorOrientationMap) obj).mElements);
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCodeGeneric(this.mElements);
    }

    public static final class Builder {
        private final java.util.ArrayList<java.lang.Long> mElements = new java.util.ArrayList<>();
        private final java.util.HashMap<java.lang.Long, java.lang.Integer> mDeviceStateOrientationMap = new java.util.HashMap<>();

        public android.hardware.camera2.params.DeviceStateSensorOrientationMap.Builder addOrientationForState(long j, long j2) {
            if (j2 % 90 != 0) {
                throw new java.lang.IllegalArgumentException("Sensor orientation not divisible by 90: " + j2);
            }
            this.mDeviceStateOrientationMap.put(java.lang.Long.valueOf(j), java.lang.Integer.valueOf(java.lang.Math.toIntExact(j2)));
            this.mElements.add(java.lang.Long.valueOf(j));
            this.mElements.add(java.lang.Long.valueOf(j2));
            return this;
        }

        public android.hardware.camera2.params.DeviceStateSensorOrientationMap build() {
            if (this.mElements.size() == 0) {
                throw new java.lang.IllegalStateException("Cannot build a DeviceStateSensorOrientationMap with zero elements.");
            }
            return new android.hardware.camera2.params.DeviceStateSensorOrientationMap(this.mElements, this.mDeviceStateOrientationMap);
        }
    }
}
