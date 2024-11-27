package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class DynamicRangeProfiles {
    public static final long DOLBY_VISION_10B_HDR_OEM = 64;
    public static final long DOLBY_VISION_10B_HDR_OEM_PO = 128;
    public static final long DOLBY_VISION_10B_HDR_REF = 16;
    public static final long DOLBY_VISION_10B_HDR_REF_PO = 32;
    public static final long DOLBY_VISION_8B_HDR_OEM = 1024;
    public static final long DOLBY_VISION_8B_HDR_OEM_PO = 2048;
    public static final long DOLBY_VISION_8B_HDR_REF = 256;
    public static final long DOLBY_VISION_8B_HDR_REF_PO = 512;
    public static final long HDR10 = 4;
    public static final long HDR10_PLUS = 8;
    public static final long HLG10 = 2;
    public static final long PUBLIC_MAX = 4096;
    public static final long STANDARD = 1;
    private final java.util.HashMap<java.lang.Long, java.util.Set<java.lang.Long>> mProfileMap = new java.util.HashMap<>();
    private final java.util.HashMap<java.lang.Long, java.lang.Boolean> mLookahedLatencyMap = new java.util.HashMap<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Profile {
    }

    public DynamicRangeProfiles(long[] jArr) {
        if (jArr.length % 3 != 0) {
            throw new java.lang.IllegalArgumentException("Dynamic range profile map length " + jArr.length + " is not even!");
        }
        int i = 0;
        while (true) {
            if (i < jArr.length) {
                checkProfileValue(jArr[i]);
                if (jArr[i] == 1) {
                    throw new java.lang.IllegalArgumentException("Dynamic range profile map must not include a STANDARD profile entry!");
                }
                java.util.HashSet hashSet = new java.util.HashSet();
                int i2 = i + 1;
                boolean z = true;
                if (jArr[i2] != 0) {
                    for (long j = 1; j < 4096; j <<= 1) {
                        if ((jArr[i2] & j) != 0) {
                            hashSet.add(java.lang.Long.valueOf(j));
                        }
                    }
                }
                this.mProfileMap.put(java.lang.Long.valueOf(jArr[i]), hashSet);
                java.util.HashMap<java.lang.Long, java.lang.Boolean> hashMap = this.mLookahedLatencyMap;
                java.lang.Long valueOf = java.lang.Long.valueOf(jArr[i]);
                if (jArr[i + 2] == 0) {
                    z = false;
                }
                hashMap.put(valueOf, java.lang.Boolean.valueOf(z));
                i += 3;
            } else {
                java.util.HashSet hashSet2 = new java.util.HashSet();
                hashSet2.add(1L);
                for (java.lang.Long l : this.mProfileMap.keySet()) {
                    if (this.mProfileMap.get(l).isEmpty() || this.mProfileMap.get(l).contains(1L)) {
                        hashSet2.add(l);
                    }
                }
                this.mProfileMap.put(1L, hashSet2);
                this.mLookahedLatencyMap.put(1L, false);
                return;
            }
        }
    }

    public static void checkProfileValue(long j) {
        if (j != 1 && j != 2 && j != 4 && j != 8 && j != 16 && j != 32 && j != 64 && j != 128 && j != 256 && j != 512 && j != 1024 && j != 2048) {
            throw new java.lang.IllegalArgumentException("Unknown profile " + j);
        }
    }

    public java.util.Set<java.lang.Long> getSupportedProfiles() {
        return java.util.Collections.unmodifiableSet(this.mProfileMap.keySet());
    }

    public java.util.Set<java.lang.Long> getProfileCaptureRequestConstraints(long j) {
        java.util.Set<java.lang.Long> set = this.mProfileMap.get(java.lang.Long.valueOf(j));
        if (set == null) {
            throw new java.lang.IllegalArgumentException("Unsupported profile!");
        }
        return java.util.Collections.unmodifiableSet(set);
    }

    public boolean isExtraLatencyPresent(long j) {
        java.lang.Boolean bool = this.mLookahedLatencyMap.get(java.lang.Long.valueOf(j));
        if (bool == null) {
            throw new java.lang.IllegalArgumentException("Unsupported profile!");
        }
        return bool.booleanValue();
    }
}
