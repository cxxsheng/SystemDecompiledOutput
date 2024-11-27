package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class FlagUnion extends com.android.server.devicepolicy.ResolutionMechanism<java.lang.Integer> {
    FlagUnion() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public android.app.admin.IntegerPolicyValue resolve(@android.annotation.NonNull java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<java.lang.Integer>> linkedHashMap) {
        java.util.Objects.requireNonNull(linkedHashMap);
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        java.lang.Integer num = 0;
        java.util.Iterator<android.app.admin.PolicyValue<java.lang.Integer>> it = linkedHashMap.values().iterator();
        while (it.hasNext()) {
            num = java.lang.Integer.valueOf(num.intValue() | ((java.lang.Integer) it.next().getValue()).intValue());
        }
        return new android.app.admin.IntegerPolicyValue(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    /* renamed from: getParcelableResolutionMechanism, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public android.app.admin.FlagUnion mo3262getParcelableResolutionMechanism() {
        return android.app.admin.FlagUnion.FLAG_UNION;
    }

    public java.lang.String toString() {
        return "IntegerUnion {}";
    }
}
