package com.android.server.appop;

/* loaded from: classes.dex */
public class AppOpsRestrictionsImpl implements com.android.server.appop.AppOpsRestrictions {
    private static final int UID_ANY = -2;
    private com.android.server.appop.AppOpsRestrictions.AppOpsRestrictionRemovedListener mAppOpsRestrictionRemovedListener;
    private android.content.Context mContext;
    private android.os.Handler mHandler;
    private final android.util.ArrayMap<java.lang.Object, android.util.SparseBooleanArray> mGlobalRestrictions = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.Object, android.util.SparseArray<android.util.SparseBooleanArray>> mUserRestrictions = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.Object, android.util.SparseArray<android.os.PackageTagsList>> mUserRestrictionExcludedPackageTags = new android.util.ArrayMap<>();

    public AppOpsRestrictionsImpl(android.content.Context context, android.os.Handler handler, com.android.server.appop.AppOpsRestrictions.AppOpsRestrictionRemovedListener appOpsRestrictionRemovedListener) {
        this.mContext = context;
        this.mHandler = handler;
        this.mAppOpsRestrictionRemovedListener = appOpsRestrictionRemovedListener;
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean setGlobalRestriction(java.lang.Object obj, int i, boolean z) {
        if (z) {
            if (!this.mGlobalRestrictions.containsKey(obj)) {
                this.mGlobalRestrictions.put(obj, new android.util.SparseBooleanArray());
            }
            android.util.SparseBooleanArray sparseBooleanArray = this.mGlobalRestrictions.get(obj);
            java.util.Objects.requireNonNull(sparseBooleanArray);
            boolean z2 = !sparseBooleanArray.get(i);
            sparseBooleanArray.put(i, true);
            return z2;
        }
        android.util.SparseBooleanArray sparseBooleanArray2 = this.mGlobalRestrictions.get(obj);
        if (sparseBooleanArray2 == null) {
            return false;
        }
        boolean z3 = sparseBooleanArray2.get(i);
        sparseBooleanArray2.delete(i);
        if (sparseBooleanArray2.size() == 0) {
            this.mGlobalRestrictions.remove(obj);
        }
        return z3;
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean getGlobalRestriction(java.lang.Object obj, int i) {
        android.util.SparseBooleanArray sparseBooleanArray = this.mGlobalRestrictions.get(obj);
        if (sparseBooleanArray == null) {
            return false;
        }
        return sparseBooleanArray.get(i);
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean hasGlobalRestrictions(java.lang.Object obj) {
        return this.mGlobalRestrictions.containsKey(obj);
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean clearGlobalRestrictions(java.lang.Object obj) {
        return this.mGlobalRestrictions.remove(obj) != null;
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_USERS", "android.permission.CREATE_USERS"})
    public boolean setUserRestriction(java.lang.Object obj, int i, int i2, boolean z, android.os.PackageTagsList packageTagsList) {
        int[] resolveUserId = resolveUserId(i);
        boolean z2 = false;
        for (int i3 = 0; i3 < resolveUserId.length; i3++) {
            z2 = z2 | putUserRestriction(obj, resolveUserId[i3], i2, z) | putUserRestrictionExclusions(obj, resolveUserId[i3], packageTagsList);
        }
        return z2;
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_USERS", "android.permission.CREATE_USERS"})
    private int[] resolveUserId(int i) {
        if (i == -1) {
            java.util.List users = android.os.UserManager.get(this.mContext).getUsers();
            int[] iArr = new int[users.size()];
            for (int i2 = 0; i2 < users.size(); i2++) {
                iArr[i2] = ((android.content.pm.UserInfo) users.get(i2)).id;
            }
            return iArr;
        }
        return new int[]{i};
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean hasUserRestrictions(java.lang.Object obj) {
        return this.mUserRestrictions.containsKey(obj);
    }

    private boolean getUserRestriction(java.lang.Object obj, int i, int i2) {
        android.util.SparseBooleanArray sparseBooleanArray;
        android.util.SparseArray<android.util.SparseBooleanArray> sparseArray = this.mUserRestrictions.get(obj);
        if (sparseArray == null || (sparseBooleanArray = sparseArray.get(i)) == null) {
            return false;
        }
        return sparseBooleanArray.get(i2);
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean getUserRestriction(java.lang.Object obj, int i, int i2, java.lang.String str, java.lang.String str2, boolean z) {
        if (!getUserRestriction(obj, i, i2)) {
            return false;
        }
        if (getUserRestrictionExclusions(obj, i) == null) {
            return true;
        }
        return z ? !r1.includes(str) : !r1.contains(str, str2);
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean clearUserRestrictions(java.lang.Object obj) {
        android.util.SparseBooleanArray collectAllUserRestrictedCodes = collectAllUserRestrictedCodes(obj);
        boolean z = (this.mUserRestrictions.remove(obj) != null) | false | (this.mUserRestrictionExcludedPackageTags.remove(obj) != null);
        notifyAllUserRestrictions(collectAllUserRestrictedCodes);
        return z;
    }

    private android.util.SparseBooleanArray collectAllUserRestrictedCodes(java.lang.Object obj) {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        android.util.SparseArray<android.util.SparseBooleanArray> sparseArray = this.mUserRestrictions.get(obj);
        if (sparseArray == null) {
            return sparseBooleanArray;
        }
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            android.util.SparseBooleanArray valueAt = sparseArray.valueAt(i);
            int size2 = valueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                sparseBooleanArray.put(valueAt.keyAt(i2), true);
            }
        }
        return sparseBooleanArray;
    }

    private void notifyAllUserRestrictions(android.util.SparseBooleanArray sparseBooleanArray) {
        int size = sparseBooleanArray.size();
        for (int i = 0; i < size; i++) {
            final int keyAt = sparseBooleanArray.keyAt(i);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.appop.AppOpsRestrictionsImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.appop.AppOpsRestrictionsImpl.this.lambda$notifyAllUserRestrictions$0(keyAt);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyAllUserRestrictions$0(int i) {
        this.mAppOpsRestrictionRemovedListener.onAppOpsRestrictionRemoved(i);
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public boolean clearUserRestrictions(java.lang.Object obj, java.lang.Integer num) {
        android.util.SparseArray<android.util.SparseBooleanArray> sparseArray = this.mUserRestrictions.get(obj);
        boolean z = false;
        if (sparseArray != null) {
            z = false | sparseArray.contains(num.intValue());
            sparseArray.remove(num.intValue());
            if (sparseArray.size() == 0) {
                this.mUserRestrictions.remove(obj);
            }
        }
        android.util.SparseArray<android.os.PackageTagsList> sparseArray2 = this.mUserRestrictionExcludedPackageTags.get(obj);
        if (sparseArray2 != null) {
            z |= sparseArray2.contains(num.intValue());
            sparseArray2.remove(num.intValue());
            if (sparseArray2.size() == 0) {
                this.mUserRestrictionExcludedPackageTags.remove(obj);
            }
        }
        return z;
    }

    private boolean putUserRestriction(java.lang.Object obj, int i, int i2, boolean z) {
        android.util.SparseBooleanArray sparseBooleanArray;
        if (z) {
            if (!this.mUserRestrictions.containsKey(obj)) {
                this.mUserRestrictions.put(obj, new android.util.SparseArray<>());
            }
            android.util.SparseArray<android.util.SparseBooleanArray> sparseArray = this.mUserRestrictions.get(obj);
            java.util.Objects.requireNonNull(sparseArray);
            if (!sparseArray.contains(i)) {
                sparseArray.put(i, new android.util.SparseBooleanArray());
            }
            android.util.SparseBooleanArray sparseBooleanArray2 = sparseArray.get(i);
            boolean z2 = !sparseBooleanArray2.get(i2);
            sparseBooleanArray2.put(i2, z);
            return z2;
        }
        android.util.SparseArray<android.util.SparseBooleanArray> sparseArray2 = this.mUserRestrictions.get(obj);
        if (sparseArray2 == null || (sparseBooleanArray = sparseArray2.get(i)) == null) {
            return false;
        }
        boolean z3 = sparseBooleanArray.get(i2);
        sparseBooleanArray.delete(i2);
        if (sparseBooleanArray.size() == 0) {
            sparseArray2.remove(i);
        }
        if (sparseArray2.size() == 0) {
            this.mUserRestrictions.remove(obj);
        }
        return z3;
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public android.os.PackageTagsList getUserRestrictionExclusions(java.lang.Object obj, int i) {
        android.util.SparseArray<android.os.PackageTagsList> sparseArray = this.mUserRestrictionExcludedPackageTags.get(obj);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    private boolean putUserRestrictionExclusions(java.lang.Object obj, int i, android.os.PackageTagsList packageTagsList) {
        if ((packageTagsList == null || packageTagsList.isEmpty()) ? false : true) {
            if (!this.mUserRestrictionExcludedPackageTags.containsKey(obj)) {
                this.mUserRestrictionExcludedPackageTags.put(obj, new android.util.SparseArray<>());
            }
            android.util.SparseArray<android.os.PackageTagsList> sparseArray = this.mUserRestrictionExcludedPackageTags.get(obj);
            java.util.Objects.requireNonNull(sparseArray);
            sparseArray.put(i, packageTagsList);
            return true;
        }
        android.util.SparseArray<android.os.PackageTagsList> sparseArray2 = this.mUserRestrictionExcludedPackageTags.get(obj);
        if (sparseArray2 == null) {
            return false;
        }
        boolean z = sparseArray2.get(i) != null;
        sparseArray2.remove(i);
        if (sparseArray2.size() == 0) {
            this.mUserRestrictionExcludedPackageTags.remove(obj);
        }
        return z;
    }

    @Override // com.android.server.appop.AppOpsRestrictions
    public void dumpRestrictions(java.io.PrintWriter printWriter, int i, java.lang.String str, boolean z) {
        java.lang.String str2;
        boolean z2;
        int i2;
        java.lang.String str3;
        boolean z3;
        int i3;
        boolean z4;
        java.lang.String str4;
        android.util.SparseArray<android.util.SparseBooleanArray> sparseArray;
        int i4;
        com.android.server.appop.AppOpsRestrictionsImpl appOpsRestrictionsImpl = this;
        java.io.PrintWriter printWriter2 = printWriter;
        int size = appOpsRestrictionsImpl.mGlobalRestrictions.size();
        int i5 = 0;
        while (true) {
            str2 = "[";
            z2 = true;
            if (i5 >= size) {
                break;
            }
            java.lang.Object keyAt = appOpsRestrictionsImpl.mGlobalRestrictions.keyAt(i5);
            android.util.SparseBooleanArray valueAt = appOpsRestrictionsImpl.mGlobalRestrictions.valueAt(i5);
            printWriter2.println("  Global restrictions for token " + keyAt + ":");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("[");
            int size2 = valueAt.size();
            for (int i6 = 0; i6 < size2; i6++) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append(android.app.AppOpsManager.opToName(valueAt.keyAt(i6)));
            }
            sb.append("]");
            printWriter2.println("      Restricted ops: " + ((java.lang.Object) sb));
            i5++;
        }
        if (!z) {
            return;
        }
        int size3 = appOpsRestrictionsImpl.mUserRestrictions.size();
        int i7 = 0;
        while (i7 < size3) {
            java.lang.Object keyAt2 = appOpsRestrictionsImpl.mUserRestrictions.keyAt(i7);
            android.util.SparseArray<android.util.SparseBooleanArray> sparseArray2 = appOpsRestrictionsImpl.mUserRestrictions.get(keyAt2);
            android.util.SparseArray<android.os.PackageTagsList> sparseArray3 = appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.get(keyAt2);
            int size4 = sparseArray2 != null ? sparseArray2.size() : 0;
            if (size4 <= 0 || str != null) {
                i2 = size3;
                str3 = str2;
                z3 = false;
            } else {
                int i8 = 0;
                z3 = false;
                boolean z5 = false;
                while (i8 < size4) {
                    int keyAt3 = sparseArray2.keyAt(i8);
                    int i9 = size3;
                    android.util.SparseBooleanArray valueAt2 = sparseArray2.valueAt(i8);
                    if (valueAt2 == null || (i >= 0 && !valueAt2.get(i))) {
                        str4 = str2;
                        sparseArray = sparseArray2;
                        i4 = size4;
                    } else {
                        if (z3) {
                            sparseArray = sparseArray2;
                            i4 = size4;
                        } else {
                            sparseArray = sparseArray2;
                            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                            i4 = size4;
                            sb2.append("  User restrictions for token ");
                            sb2.append(keyAt2);
                            sb2.append(":");
                            printWriter2.println(sb2.toString());
                            z3 = true;
                        }
                        if (!z5) {
                            printWriter2.println("      Restricted ops:");
                            z5 = true;
                        }
                        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                        sb3.append(str2);
                        int size5 = valueAt2.size();
                        str4 = str2;
                        int i10 = 0;
                        while (i10 < size5) {
                            int keyAt4 = valueAt2.keyAt(i10);
                            android.util.SparseBooleanArray sparseBooleanArray = valueAt2;
                            int i11 = size5;
                            if (sb3.length() > 1) {
                                sb3.append(", ");
                            }
                            sb3.append(android.app.AppOpsManager.opToName(keyAt4));
                            i10++;
                            valueAt2 = sparseBooleanArray;
                            size5 = i11;
                        }
                        sb3.append("]");
                        printWriter2.print("        ");
                        printWriter2.print("user: ");
                        printWriter2.print(keyAt3);
                        printWriter2.print(" restricted ops: ");
                        printWriter2.println(sb3);
                    }
                    i8++;
                    size3 = i9;
                    sparseArray2 = sparseArray;
                    size4 = i4;
                    str2 = str4;
                }
                i2 = size3;
                str3 = str2;
            }
            int size6 = sparseArray3 != null ? sparseArray3.size() : 0;
            if (size6 > 0 && i < 0) {
                android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter2);
                indentingPrintWriter.increaseIndent();
                int i12 = 0;
                boolean z6 = false;
                while (i12 < size6) {
                    int keyAt5 = sparseArray3.keyAt(i12);
                    android.os.PackageTagsList valueAt3 = sparseArray3.valueAt(i12);
                    if (valueAt3 != null) {
                        if (str != null) {
                            z4 = valueAt3.includes(str);
                        } else {
                            z4 = true;
                        }
                        if (z4) {
                            if (z3) {
                                i3 = size6;
                            } else {
                                i3 = size6;
                                indentingPrintWriter.println("User restrictions for token " + keyAt2 + ":");
                                z3 = true;
                            }
                            indentingPrintWriter.increaseIndent();
                            if (!z6) {
                                indentingPrintWriter.println("Excluded packages:");
                                z6 = true;
                            }
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.print("user: ");
                            indentingPrintWriter.print(keyAt5);
                            indentingPrintWriter.println(" packages: ");
                            indentingPrintWriter.increaseIndent();
                            valueAt3.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.decreaseIndent();
                            i12++;
                            size6 = i3;
                        }
                    }
                    i3 = size6;
                    i12++;
                    size6 = i3;
                }
                indentingPrintWriter.decreaseIndent();
            }
            i7++;
            appOpsRestrictionsImpl = this;
            printWriter2 = printWriter;
            size3 = i2;
            str2 = str3;
            z2 = true;
        }
    }
}
