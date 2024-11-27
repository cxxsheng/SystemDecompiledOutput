package com.android.server.display.utils;

/* loaded from: classes.dex */
public class DeviceConfigParsingUtils {
    private static final java.lang.String TAG = "DeviceConfigParsingUtils";

    @android.annotation.NonNull
    public static <T, V> java.util.Map<java.lang.String, java.util.Map<java.lang.String, V>> parseDeviceConfigMap(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.util.function.BiFunction<java.lang.String, java.lang.String, T> biFunction, @android.annotation.NonNull java.util.function.Function<java.util.List<T>, V> function) {
        if (str == null) {
            return java.util.Map.of();
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        java.lang.String[] split = str.split(";");
        int length = split.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            java.lang.String str2 = split[i2];
            java.lang.String[] split2 = str2.split(",");
            int length2 = split2.length;
            if (length2 < 4) {
                android.util.Slog.e(TAG, "Invalid dataSet(not enough items):" + str2, new java.lang.Throwable());
                return java.util.Map.of();
            }
            java.lang.String str3 = split2[i];
            try {
                int parseInt = java.lang.Integer.parseInt(split2[1]);
                int i3 = 2;
                int i4 = (parseInt * 2) + 2;
                if (length2 < i4 || length2 > i4 + 1) {
                    android.util.Slog.e(TAG, "Invalid dataSet(wrong number of points):" + str2, new java.lang.Throwable());
                    return java.util.Map.of();
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int i5 = i;
                while (i5 < parseInt) {
                    int i6 = i3 + 1;
                    java.lang.String str4 = split2[i3];
                    int i7 = i6 + 1;
                    java.lang.String str5 = split2[i6];
                    java.lang.String[] strArr = split;
                    T apply = biFunction.apply(str4, str5);
                    if (apply == null) {
                        android.util.Slog.e(TAG, "Invalid dataPoint ,key=" + str4 + ",value=" + str5 + ",dataSet=" + str2, new java.lang.Throwable());
                        return java.util.Map.of();
                    }
                    arrayList.add(apply);
                    i5++;
                    i3 = i7;
                    split = strArr;
                }
                java.lang.String[] strArr2 = split;
                V apply2 = function.apply(arrayList);
                if (apply2 == null) {
                    android.util.Slog.e(TAG, "Invalid dataSetMapped dataPoints=" + arrayList + ",dataSet=" + str2, new java.lang.Throwable());
                    return java.util.Map.of();
                }
                java.lang.String str6 = i3 < split2.length ? split2[i3] : "default";
                if (((java.util.Map) hashMap.computeIfAbsent(str3, new java.util.function.Function() { // from class: com.android.server.display.utils.DeviceConfigParsingUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.Map lambda$parseDeviceConfigMap$0;
                        lambda$parseDeviceConfigMap$0 = com.android.server.display.utils.DeviceConfigParsingUtils.lambda$parseDeviceConfigMap$0((java.lang.String) obj);
                        return lambda$parseDeviceConfigMap$0;
                    }
                })).put(str6, apply2) == null) {
                    i2++;
                    split = strArr2;
                    i = 0;
                } else {
                    android.util.Slog.e(TAG, "Duplicate dataSetId=" + str6 + ",data=" + str, new java.lang.Throwable());
                    return java.util.Map.of();
                }
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Invalid dataSet(invalid number of points):" + str2, e);
                return java.util.Map.of();
            }
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Map lambda$parseDeviceConfigMap$0(java.lang.String str) {
        return new java.util.HashMap();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int parseThermalStatus(@android.annotation.NonNull java.lang.String str) throws java.lang.IllegalArgumentException {
        char c;
        switch (str.hashCode()) {
            case -905723276:
                if (str.equals("severe")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -618857213:
                if (str.equals("moderate")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -169343402:
                if (str.equals("shutdown")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 3387192:
                if (str.equals("none")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 102970646:
                if (str.equals("light")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1629013393:
                if (str.equals("emergency")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1952151455:
                if (str.equals("critical")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                throw new java.lang.IllegalArgumentException("Invalid Thermal Status: " + str);
        }
    }

    public static float parseBrightness(java.lang.String str) throws java.lang.IllegalArgumentException {
        float parseFloat = java.lang.Float.parseFloat(str);
        if (parseFloat < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || parseFloat > 1.0f) {
            throw new java.lang.IllegalArgumentException("Brightness value out of bounds: " + str);
        }
        return parseFloat;
    }

    public static float[] displayBrightnessThresholdsIntToFloat(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            if (iArr[i] < 0) {
                fArr[i] = iArr[i];
            } else {
                fArr[i] = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(iArr[i]);
            }
        }
        return fArr;
    }

    public static float[] ambientBrightnessThresholdsIntToFloat(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = iArr[i];
        }
        return fArr;
    }
}
