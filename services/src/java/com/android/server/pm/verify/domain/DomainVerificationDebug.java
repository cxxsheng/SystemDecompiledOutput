package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public class DomainVerificationDebug {
    public static final boolean DEBUG_ALL = false;
    public static final boolean DEBUG_ANY = false;
    public static final boolean DEBUG_APPROVAL = false;
    public static final boolean DEBUG_BROADCASTS = false;
    public static final boolean DEBUG_PROXIES = false;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationCollector mCollector;

    DomainVerificationDebug(com.android.server.pm.verify.domain.DomainVerificationCollector domainVerificationCollector) {
        this.mCollector = domainVerificationCollector;
    }

    public void printState(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num, @android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationStateMap<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> domainVerificationStateMap) throws android.content.pm.PackageManager.NameNotFoundException {
        int i;
        android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = new android.util.ArrayMap<>();
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        if (str == null) {
            int size = domainVerificationStateMap.size();
            int i2 = 0;
            while (i2 < size) {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = domainVerificationStateMap.valueAt(i2);
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(valueAt.getPackageName());
                if (packageStateInternal == null) {
                    i = i2;
                } else if (packageStateInternal.getPkg() == null) {
                    i = i2;
                } else {
                    i = i2;
                    printState(indentingPrintWriter, valueAt, packageStateInternal.getPkg(), num, arraySet, printState(indentingPrintWriter, valueAt, (com.android.server.pm.pkg.AndroidPackage) packageStateInternal.getPkg(), arrayMap, false));
                }
                i2 = i + 1;
            }
            return;
        }
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = domainVerificationStateMap.get(str);
        if (domainVerificationPkgState == null) {
            throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = computer.getPackageStateInternal(str);
        if (packageStateInternal2 == null || packageStateInternal2.getPkg() == null) {
            throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
        }
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal2.getPkg();
        printState(indentingPrintWriter, domainVerificationPkgState, (com.android.server.pm.pkg.AndroidPackage) pkg, arrayMap, false);
        printState(indentingPrintWriter, domainVerificationPkgState, pkg, num, arraySet, true);
    }

    public void printOwners(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull java.lang.String str, android.util.SparseArray<android.util.SparseArray<java.util.List<java.lang.String>>> sparseArray) {
        indentingPrintWriter.println(str + ":");
        indentingPrintWriter.increaseIndent();
        if (sparseArray.size() == 0) {
            indentingPrintWriter.println("none");
            indentingPrintWriter.decreaseIndent();
            return;
        }
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            android.util.SparseArray<java.util.List<java.lang.String>> valueAt = sparseArray.valueAt(i);
            if (valueAt.size() != 0) {
                int size2 = valueAt.size();
                boolean z = false;
                for (int i2 = 0; i2 < size2; i2++) {
                    int keyAt2 = valueAt.keyAt(i2);
                    if (keyAt2 >= -1) {
                        if (!z) {
                            indentingPrintWriter.println("User " + keyAt + ":");
                            indentingPrintWriter.increaseIndent();
                            z = true;
                        }
                        java.lang.String approvalLevelToDebugString = com.android.server.pm.verify.domain.DomainVerificationManagerInternal.approvalLevelToDebugString(keyAt2);
                        java.util.List<java.lang.String> valueAt2 = valueAt.valueAt(i2);
                        indentingPrintWriter.println(approvalLevelToDebugString + "[" + keyAt2 + "]:");
                        indentingPrintWriter.increaseIndent();
                        if (valueAt2.size() == 0) {
                            indentingPrintWriter.println("none");
                            indentingPrintWriter.decreaseIndent();
                        } else {
                            int size3 = valueAt2.size();
                            for (int i3 = 0; i3 < size3; i3++) {
                                indentingPrintWriter.println(valueAt2.get(i3));
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                    }
                }
                if (z) {
                    indentingPrintWriter.decreaseIndent();
                }
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    boolean printState(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, boolean z) {
        boolean z2;
        arrayMap.clear();
        arrayMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Integer>) domainVerificationPkgState.getStateMap());
        android.util.ArraySet<java.lang.String> collectValidAutoVerifyDomains = this.mCollector.collectValidAutoVerifyDomains(androidPackage);
        int size = collectValidAutoVerifyDomains.size();
        for (int i = 0; i < size; i++) {
            arrayMap.putIfAbsent(collectValidAutoVerifyDomains.valueAt(i), 0);
        }
        if (arrayMap.isEmpty()) {
            return false;
        }
        if (z) {
            z2 = false;
        } else {
            java.lang.String arrays = androidPackage.getSigningDetails().getSignatures() == null ? null : java.util.Arrays.toString(android.util.PackageUtils.computeSignaturesSha256Digests(androidPackage.getSigningDetails().getSignatures(), ":"));
            indentingPrintWriter.println(domainVerificationPkgState.getPackageName() + ":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("ID: " + domainVerificationPkgState.getId());
            indentingPrintWriter.println("Signatures: " + arrays);
            indentingPrintWriter.decreaseIndent();
            z2 = true;
        }
        indentingPrintWriter.increaseIndent();
        android.util.ArraySet<java.lang.String> collectInvalidAutoVerifyDomains = this.mCollector.collectInvalidAutoVerifyDomains(androidPackage);
        if (!collectInvalidAutoVerifyDomains.isEmpty()) {
            indentingPrintWriter.println("Invalid autoVerify domains:");
            indentingPrintWriter.increaseIndent();
            int size2 = collectInvalidAutoVerifyDomains.size();
            for (int i2 = 0; i2 < size2; i2++) {
                indentingPrintWriter.println(collectInvalidAutoVerifyDomains.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println("Domain verification state:");
        indentingPrintWriter.increaseIndent();
        int size3 = arrayMap.size();
        for (int i3 = 0; i3 < size3; i3++) {
            java.lang.String keyAt = arrayMap.keyAt(i3);
            java.lang.Integer valueAt = arrayMap.valueAt(i3);
            indentingPrintWriter.print(keyAt);
            indentingPrintWriter.print(": ");
            indentingPrintWriter.println(android.content.pm.verify.domain.DomainVerificationState.stateToDebugString(valueAt.intValue()));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        return z2;
    }

    void printState(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable java.lang.Integer num, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, boolean z) {
        if (num == null) {
            return;
        }
        android.util.ArraySet<java.lang.String> collectAllWebDomains = this.mCollector.collectAllWebDomains(androidPackage);
        android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> userStates = domainVerificationPkgState.getUserStates();
        if (num.intValue() == -1) {
            int size = userStates.size();
            if (size == 0) {
                printState(indentingPrintWriter, domainVerificationPkgState, num.intValue(), null, arraySet, collectAllWebDomains, z);
                return;
            }
            for (int i = 0; i < size; i++) {
                com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState valueAt = userStates.valueAt(i);
                printState(indentingPrintWriter, domainVerificationPkgState, valueAt.getUserId(), valueAt, arraySet, collectAllWebDomains, z);
            }
            return;
        }
        printState(indentingPrintWriter, domainVerificationPkgState, num.intValue(), userStates.get(num.intValue()), arraySet, collectAllWebDomains, z);
    }

    boolean printState(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, int i, @android.annotation.Nullable com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet2, boolean z) {
        boolean z2;
        arraySet.clear();
        arraySet.addAll((android.util.ArraySet<? extends java.lang.String>) arraySet2);
        if (domainVerificationInternalUserState != null) {
            arraySet.removeAll((android.util.ArraySet<? extends java.lang.String>) domainVerificationInternalUserState.getEnabledHosts());
        }
        android.util.ArraySet<java.lang.String> enabledHosts = domainVerificationInternalUserState == null ? null : domainVerificationInternalUserState.getEnabledHosts();
        int size = com.android.internal.util.CollectionUtils.size(enabledHosts);
        int size2 = arraySet.size();
        if (size <= 0 && size2 <= 0) {
            return false;
        }
        boolean z3 = true;
        if (z) {
            z2 = false;
        } else {
            indentingPrintWriter.println(domainVerificationPkgState.getPackageName() + " " + domainVerificationPkgState.getId() + ":");
            z2 = true;
        }
        if (domainVerificationInternalUserState != null && !domainVerificationInternalUserState.isLinkHandlingAllowed()) {
            z3 = false;
        }
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("User ");
        indentingPrintWriter.print(i == -1 ? "all" : java.lang.Integer.valueOf(i));
        indentingPrintWriter.println(":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("Verification link handling allowed: ");
        indentingPrintWriter.println(z3);
        indentingPrintWriter.println("Selection state:");
        indentingPrintWriter.increaseIndent();
        if (size > 0) {
            indentingPrintWriter.println("Enabled:");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < size; i2++) {
                indentingPrintWriter.println(enabledHosts.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        }
        if (size2 > 0) {
            indentingPrintWriter.println("Disabled:");
            indentingPrintWriter.increaseIndent();
            for (int i3 = 0; i3 < size2; i3++) {
                indentingPrintWriter.println(arraySet.valueAt(i3));
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        return z2;
    }
}
