package com.android.server.pm;

/* compiled from: SELinuxMMAC.java */
/* loaded from: classes2.dex */
final class PolicyComparator implements java.util.Comparator<com.android.server.pm.Policy> {
    private boolean duplicateFound = false;

    PolicyComparator() {
    }

    public boolean foundDuplicate() {
        return this.duplicateFound;
    }

    @Override // java.util.Comparator
    public int compare(com.android.server.pm.Policy policy, com.android.server.pm.Policy policy2) {
        if (policy.hasInnerPackages() != policy2.hasInnerPackages()) {
            return policy.hasInnerPackages() ? -1 : 1;
        }
        if (policy.getSignatures().equals(policy2.getSignatures())) {
            if (policy.hasGlobalSeinfo()) {
                this.duplicateFound = true;
                android.util.Slog.e("SELinuxMMAC", "Duplicate policy entry: " + policy.toString());
            }
            if (!java.util.Collections.disjoint(policy.getInnerPackages().keySet(), policy2.getInnerPackages().keySet())) {
                this.duplicateFound = true;
                android.util.Slog.e("SELinuxMMAC", "Duplicate policy entry: " + policy.toString());
                return 0;
            }
            return 0;
        }
        return 0;
    }
}
