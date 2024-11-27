package com.android.server.usage;

/* loaded from: classes2.dex */
public final class PackagesTokenData {
    private static final int PACKAGE_NAME_INDEX = 0;
    public static final int UNASSIGNED_TOKEN = -1;
    public int counter = 1;
    public final android.util.SparseArray<java.util.ArrayList<java.lang.String>> tokensToPackagesMap = new android.util.SparseArray<>();
    public final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Integer>> packagesToTokensMap = new android.util.ArrayMap<>();
    public final android.util.ArrayMap<java.lang.String, java.lang.Long> removedPackagesMap = new android.util.ArrayMap<>();
    public final android.util.ArraySet<java.lang.Integer> removedPackageTokens = new android.util.ArraySet<>();

    public int getPackageTokenOrAdd(java.lang.String str, long j) {
        java.lang.Long l = this.removedPackagesMap.get(str);
        if (l != null && l.longValue() > j) {
            return -1;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = this.packagesToTokensMap.get(str);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            this.packagesToTokensMap.put(str, arrayMap);
        }
        int intValue = arrayMap.getOrDefault(str, -1).intValue();
        if (intValue == -1) {
            int i = this.counter;
            this.counter = i + 1;
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
            arrayList.add(str);
            arrayMap.put(str, java.lang.Integer.valueOf(i));
            this.tokensToPackagesMap.put(i, arrayList);
            return i;
        }
        return intValue;
    }

    public int getTokenOrAdd(int i, java.lang.String str, java.lang.String str2) {
        if (str.equals(str2)) {
            return 0;
        }
        int intValue = this.packagesToTokensMap.get(str).getOrDefault(str2, -1).intValue();
        if (intValue == -1) {
            int size = this.tokensToPackagesMap.get(i).size();
            this.packagesToTokensMap.get(str).put(str2, java.lang.Integer.valueOf(size));
            this.tokensToPackagesMap.get(i).add(str2);
            return size;
        }
        return intValue;
    }

    public java.lang.String getPackageString(int i) {
        java.util.ArrayList<java.lang.String> arrayList = this.tokensToPackagesMap.get(i);
        if (arrayList == null) {
            return null;
        }
        return arrayList.get(0);
    }

    public java.lang.String getString(int i, int i2) {
        try {
            return this.tokensToPackagesMap.get(i).get(i2);
        } catch (java.lang.IndexOutOfBoundsException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            android.util.Slog.e("PackagesTokenData", "Unable to find tokenized strings for package " + i, e2);
            return null;
        }
    }

    public int removePackage(java.lang.String str, long j) {
        this.removedPackagesMap.put(str, java.lang.Long.valueOf(j));
        if (!this.packagesToTokensMap.containsKey(str)) {
            return -1;
        }
        int intValue = this.packagesToTokensMap.get(str).get(str).intValue();
        this.packagesToTokensMap.remove(str);
        this.tokensToPackagesMap.delete(intValue);
        this.removedPackageTokens.add(java.lang.Integer.valueOf(intValue));
        return intValue;
    }
}
