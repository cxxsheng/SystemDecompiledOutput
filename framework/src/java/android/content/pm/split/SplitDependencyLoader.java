package android.content.pm.split;

/* loaded from: classes.dex */
public abstract class SplitDependencyLoader<E extends java.lang.Exception> {
    private final android.util.SparseArray<int[]> mDependencies;

    protected abstract void constructSplit(int i, int[] iArr, int i2) throws java.lang.Exception;

    protected abstract boolean isSplitCached(int i);

    protected SplitDependencyLoader(android.util.SparseArray<int[]> sparseArray) {
        this.mDependencies = sparseArray;
    }

    protected void loadDependenciesForSplit(int i) throws java.lang.Exception {
        if (isSplitCached(i)) {
            return;
        }
        if (i == 0) {
            constructSplit(0, collectConfigSplitIndices(0), -1);
            return;
        }
        android.util.IntArray intArray = new android.util.IntArray();
        intArray.add(i);
        while (true) {
            int[] iArr = this.mDependencies.get(i);
            if (iArr != null && iArr.length > 0) {
                i = iArr[0];
            } else {
                i = -1;
            }
            if (i < 0 || isSplitCached(i)) {
                break;
            } else {
                intArray.add(i);
            }
        }
        int size = intArray.size() - 1;
        while (size >= 0) {
            int i2 = intArray.get(size);
            constructSplit(i2, collectConfigSplitIndices(i2), i);
            size--;
            i = i2;
        }
    }

    private int[] collectConfigSplitIndices(int i) {
        int[] iArr = this.mDependencies.get(i);
        if (iArr == null || iArr.length <= 1) {
            return libcore.util.EmptyArray.INT;
        }
        return java.util.Arrays.copyOfRange(iArr, 1, iArr.length);
    }

    public static class IllegalDependencyException extends java.lang.Exception {
        private IllegalDependencyException(java.lang.String str) {
            super(str);
        }
    }

    private static int[] append(int[] iArr, int i) {
        if (iArr == null) {
            return new int[]{i};
        }
        int[] copyOf = java.util.Arrays.copyOf(iArr, iArr.length + 1);
        copyOf[iArr.length] = i;
        return copyOf;
    }

    public static android.util.SparseArray<int[]> createDependenciesFromPackage(android.content.pm.parsing.PackageLite packageLite) throws android.content.pm.split.SplitDependencyLoader.IllegalDependencyException {
        int i;
        int i2;
        android.util.SparseArray<int[]> sparseArray = new android.util.SparseArray<>();
        sparseArray.put(0, new int[]{-1});
        int i3 = 0;
        while (true) {
            if (i3 < packageLite.getSplitNames().length) {
                if (packageLite.getIsFeatureSplits()[i3]) {
                    java.lang.String str = packageLite.getUsesSplitNames()[i3];
                    if (str != null) {
                        int binarySearch = java.util.Arrays.binarySearch(packageLite.getSplitNames(), str);
                        if (binarySearch < 0) {
                            throw new android.content.pm.split.SplitDependencyLoader.IllegalDependencyException("Split '" + packageLite.getSplitNames()[i3] + "' requires split '" + str + "', which is missing.");
                        }
                        i2 = binarySearch + 1;
                    } else {
                        i2 = 0;
                    }
                    sparseArray.put(i3 + 1, new int[]{i2});
                }
                i3++;
            } else {
                int length = packageLite.getSplitNames().length;
                for (int i4 = 0; i4 < length; i4++) {
                    if (!packageLite.getIsFeatureSplits()[i4]) {
                        java.lang.String str2 = packageLite.getConfigForSplit()[i4];
                        if (str2 != null) {
                            int binarySearch2 = java.util.Arrays.binarySearch(packageLite.getSplitNames(), str2);
                            if (binarySearch2 < 0) {
                                throw new android.content.pm.split.SplitDependencyLoader.IllegalDependencyException("Split '" + packageLite.getSplitNames()[i4] + "' targets split '" + str2 + "', which is missing.");
                            }
                            if (!packageLite.getIsFeatureSplits()[binarySearch2]) {
                                throw new android.content.pm.split.SplitDependencyLoader.IllegalDependencyException("Split '" + packageLite.getSplitNames()[i4] + "' declares itself as configuration split for a non-feature split '" + packageLite.getSplitNames()[binarySearch2] + "'");
                            }
                            i = binarySearch2 + 1;
                        } else {
                            i = 0;
                        }
                        sparseArray.put(i, append(sparseArray.get(i), i4 + 1));
                    }
                }
                java.util.BitSet bitSet = new java.util.BitSet();
                int size = sparseArray.size();
                for (int i5 = 0; i5 < size; i5++) {
                    int keyAt = sparseArray.keyAt(i5);
                    bitSet.clear();
                    while (keyAt != -1) {
                        if (bitSet.get(keyAt)) {
                            throw new android.content.pm.split.SplitDependencyLoader.IllegalDependencyException("Cycle detected in split dependencies.");
                        }
                        bitSet.set(keyAt);
                        int[] iArr = sparseArray.get(keyAt);
                        keyAt = iArr != null ? iArr[0] : -1;
                    }
                }
                return sparseArray;
            }
        }
    }
}
