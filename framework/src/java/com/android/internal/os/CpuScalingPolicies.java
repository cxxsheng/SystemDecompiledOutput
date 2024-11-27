package com.android.internal.os;

/* loaded from: classes4.dex */
public class CpuScalingPolicies {
    private final android.util.SparseArray<int[]> mCpusByPolicy;
    private final android.util.SparseArray<int[]> mFreqsByPolicy;
    private final int[] mPolicies;
    private final int mScalingStepCount;

    public CpuScalingPolicies(android.util.SparseArray<int[]> sparseArray, android.util.SparseArray<int[]> sparseArray2) {
        this.mCpusByPolicy = sparseArray;
        this.mFreqsByPolicy = sparseArray2;
        this.mPolicies = new int[sparseArray.size()];
        int i = 0;
        for (int i2 = 0; i2 < this.mPolicies.length; i2++) {
            this.mPolicies[i2] = sparseArray.keyAt(i2);
        }
        java.util.Arrays.sort(this.mPolicies);
        for (int size = sparseArray2.size() - 1; size >= 0; size--) {
            i += sparseArray2.valueAt(size).length;
        }
        this.mScalingStepCount = i;
    }

    public int[] getPolicies() {
        return this.mPolicies;
    }

    public int[] getRelatedCpus(int i) {
        return this.mCpusByPolicy.get(i, libcore.util.EmptyArray.INT);
    }

    public int[] getFrequencies(int i) {
        return this.mFreqsByPolicy.get(i, libcore.util.EmptyArray.INT);
    }

    public int getScalingStepCount() {
        return this.mScalingStepCount;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i : this.mPolicies) {
            sb.append("policy").append(i).append("\n CPUs: ").append(java.util.Arrays.toString(this.mCpusByPolicy.get(i))).append("\n freqs: ").append(java.util.Arrays.toString(this.mFreqsByPolicy.get(i))).append("\n");
        }
        return sb.toString();
    }
}
