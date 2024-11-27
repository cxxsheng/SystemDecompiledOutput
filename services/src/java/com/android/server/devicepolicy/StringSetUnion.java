package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class StringSetUnion extends com.android.server.devicepolicy.ResolutionMechanism<java.util.Set<java.lang.String>> {
    StringSetUnion() {
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    android.app.admin.PolicyValue<java.util.Set<java.lang.String>> resolve(@android.annotation.NonNull java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<java.util.Set<java.lang.String>>> linkedHashMap) {
        java.util.Objects.requireNonNull(linkedHashMap);
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator<android.app.admin.PolicyValue<java.util.Set<java.lang.String>>> it = linkedHashMap.values().iterator();
        while (it.hasNext()) {
            hashSet.addAll((java.util.Collection) it.next().getValue());
        }
        return new android.app.admin.StringSetPolicyValue(hashSet);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    /* renamed from: getParcelableResolutionMechanism, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public android.app.admin.StringSetUnion mo3262getParcelableResolutionMechanism() {
        return new android.app.admin.StringSetUnion();
    }

    public java.lang.String toString() {
        return "SetUnion {}";
    }
}
