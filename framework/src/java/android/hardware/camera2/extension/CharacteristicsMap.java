package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class CharacteristicsMap {
    private final java.util.HashMap<java.lang.String, android.hardware.camera2.CameraCharacteristics> mCharMap = new java.util.HashMap<>();

    CharacteristicsMap(java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) {
        for (java.util.Map.Entry<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> entry : map.entrySet()) {
            this.mCharMap.put(entry.getKey(), new android.hardware.camera2.CameraCharacteristics(entry.getValue()));
        }
    }

    public java.util.Set<java.lang.String> getCameraIds() {
        return this.mCharMap.keySet();
    }

    public android.hardware.camera2.CameraCharacteristics get(java.lang.String str) {
        return this.mCharMap.get(str);
    }
}
