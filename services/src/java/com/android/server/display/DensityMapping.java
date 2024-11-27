package com.android.server.display;

/* loaded from: classes.dex */
public class DensityMapping {
    private final com.android.server.display.DensityMapping.Entry[] mSortedDensityMappingEntries;

    static com.android.server.display.DensityMapping createByOwning(com.android.server.display.DensityMapping.Entry[] entryArr) {
        return new com.android.server.display.DensityMapping(entryArr);
    }

    private DensityMapping(com.android.server.display.DensityMapping.Entry[] entryArr) {
        java.util.Arrays.sort(entryArr, java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.display.DensityMapping$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int i;
                i = ((com.android.server.display.DensityMapping.Entry) obj).squaredDiagonal;
                return i;
            }
        }));
        this.mSortedDensityMappingEntries = entryArr;
        verifyDensityMapping(this.mSortedDensityMappingEntries);
    }

    public int getDensityForResolution(int i, int i2) {
        com.android.server.display.DensityMapping.Entry entry;
        int i3 = (i * i) + (i2 * i2);
        com.android.server.display.DensityMapping.Entry entry2 = com.android.server.display.DensityMapping.Entry.ZEROES;
        com.android.server.display.DensityMapping.Entry[] entryArr = this.mSortedDensityMappingEntries;
        int length = entryArr.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                entry = null;
                break;
            }
            entry = entryArr[i4];
            if (entry.squaredDiagonal > i3) {
                break;
            }
            i4++;
            entry2 = entry;
        }
        if (entry2.squaredDiagonal == i3) {
            return entry2.density;
        }
        if (entry == null) {
            entry = entry2;
            entry2 = com.android.server.display.DensityMapping.Entry.ZEROES;
        }
        double sqrt = java.lang.Math.sqrt(entry2.squaredDiagonal);
        return (int) java.lang.Math.round((((java.lang.Math.sqrt(i3) - sqrt) * (entry.density - entry2.density)) / (java.lang.Math.sqrt(entry.squaredDiagonal) - sqrt)) + entry2.density);
    }

    private static void verifyDensityMapping(com.android.server.display.DensityMapping.Entry[] entryArr) {
        for (int i = 1; i < entryArr.length; i++) {
            com.android.server.display.DensityMapping.Entry entry = entryArr[i - 1];
            com.android.server.display.DensityMapping.Entry entry2 = entryArr[i];
            if (entry.squaredDiagonal == entry2.squaredDiagonal) {
                throw new java.lang.IllegalStateException("Found two entries in the density mapping with the same diagonal: " + entry + ", " + entry2);
            }
            if (entry.density > entry2.density) {
                throw new java.lang.IllegalStateException("Found two entries in the density mapping with increasing diagonal but decreasing density: " + entry + ", " + entry2);
            }
        }
    }

    public java.lang.String toString() {
        return "DensityMapping{mDensityMappingEntries=" + java.util.Arrays.toString(this.mSortedDensityMappingEntries) + '}';
    }

    static class Entry {
        public static final com.android.server.display.DensityMapping.Entry ZEROES = new com.android.server.display.DensityMapping.Entry(0, 0, 0);
        public final int density;
        public final int squaredDiagonal;

        Entry(int i, int i2, int i3) {
            this.squaredDiagonal = (i * i) + (i2 * i2);
            this.density = i3;
        }

        public java.lang.String toString() {
            return "DensityMappingEntry{squaredDiagonal=" + this.squaredDiagonal + ", density=" + this.density + '}';
        }
    }
}
