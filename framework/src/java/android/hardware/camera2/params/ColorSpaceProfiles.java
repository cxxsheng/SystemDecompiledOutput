package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class ColorSpaceProfiles {
    public static final int UNSPECIFIED = -1;
    private final java.util.Map<android.graphics.ColorSpace.Named, java.util.Map<java.lang.Integer, java.util.Set<java.lang.Long>>> mProfileMap = new android.util.ArrayMap();

    public ColorSpaceProfiles(long[] jArr) {
        if (jArr.length % 3 != 0) {
            throw new java.lang.IllegalArgumentException("Color space profile map length " + jArr.length + " is not divisible by 3!");
        }
        for (int i = 0; i < jArr.length; i += 3) {
            int i2 = (int) jArr[i];
            checkProfileValue(i2);
            android.graphics.ColorSpace.Named named = android.graphics.ColorSpace.Named.values()[i2];
            int i3 = (int) jArr[i + 1];
            long j = jArr[i + 2];
            if (!this.mProfileMap.containsKey(named)) {
                this.mProfileMap.put(named, new android.util.ArrayMap());
            }
            if (!this.mProfileMap.get(named).containsKey(java.lang.Integer.valueOf(i3))) {
                this.mProfileMap.get(named).put(java.lang.Integer.valueOf(i3), new android.util.ArraySet());
            }
            if (j != 0) {
                for (long j2 = 1; j2 < 4096; j2 <<= 1) {
                    if ((j & j2) != 0) {
                        this.mProfileMap.get(named).get(java.lang.Integer.valueOf(i3)).add(java.lang.Long.valueOf(j2));
                    }
                }
            }
        }
    }

    public static void checkProfileValue(int i) {
        android.graphics.ColorSpace.Named[] values = android.graphics.ColorSpace.Named.values();
        int length = values.length;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (i != values[i2].ordinal()) {
                i2++;
            } else {
                z = true;
                break;
            }
        }
        if (!z) {
            throw new java.lang.IllegalArgumentException("Unknown ColorSpace " + i);
        }
    }

    public java.util.Map<android.graphics.ColorSpace.Named, java.util.Map<java.lang.Integer, java.util.Set<java.lang.Long>>> getProfileMap() {
        return this.mProfileMap;
    }

    public java.util.Set<android.graphics.ColorSpace.Named> getSupportedColorSpaces(int i) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.graphics.ColorSpace.Named named : this.mProfileMap.keySet()) {
            if (i == 0) {
                arraySet.add(named);
            } else if (this.mProfileMap.get(named).containsKey(java.lang.Integer.valueOf(i))) {
                arraySet.add(named);
            }
        }
        return arraySet;
    }

    public java.util.Set<java.lang.Integer> getSupportedImageFormatsForColorSpace(android.graphics.ColorSpace.Named named) {
        java.util.Map<java.lang.Integer, java.util.Set<java.lang.Long>> map = this.mProfileMap.get(named);
        if (map == null) {
            return new android.util.ArraySet();
        }
        return map.keySet();
    }

    public java.util.Set<java.lang.Long> getSupportedDynamicRangeProfiles(android.graphics.ColorSpace.Named named, int i) {
        java.util.Set<java.lang.Long> set;
        java.util.Map<java.lang.Integer, java.util.Set<java.lang.Long>> map = this.mProfileMap.get(named);
        if (map == null) {
            return new android.util.ArraySet();
        }
        if (i == 0) {
            set = new android.util.ArraySet<>();
            java.util.Iterator<java.lang.Integer> it = map.keySet().iterator();
            while (it.hasNext()) {
                java.util.Iterator<java.lang.Long> it2 = map.get(java.lang.Integer.valueOf(it.next().intValue())).iterator();
                while (it2.hasNext()) {
                    set.add(it2.next());
                }
            }
        } else {
            set = map.get(java.lang.Integer.valueOf(i));
            if (set == null) {
                return new android.util.ArraySet();
            }
        }
        return set;
    }

    public java.util.Set<android.graphics.ColorSpace.Named> getSupportedColorSpacesForDynamicRange(int i, long j) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.graphics.ColorSpace.Named named : this.mProfileMap.keySet()) {
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Long>> map = this.mProfileMap.get(named);
            if (i == 0) {
                java.util.Iterator<java.lang.Integer> it = map.keySet().iterator();
                while (it.hasNext()) {
                    if (map.get(java.lang.Integer.valueOf(it.next().intValue())).contains(java.lang.Long.valueOf(j))) {
                        arraySet.add(named);
                    }
                }
            } else if (map.containsKey(java.lang.Integer.valueOf(i)) && map.get(java.lang.Integer.valueOf(i)).contains(java.lang.Long.valueOf(j))) {
                arraySet.add(named);
            }
        }
        return arraySet;
    }
}
