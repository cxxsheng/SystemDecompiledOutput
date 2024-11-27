package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class MostRestrictive<V> extends com.android.server.devicepolicy.ResolutionMechanism<V> {
    private java.util.List<android.app.admin.PolicyValue<V>> mMostToLeastRestrictive;

    MostRestrictive(@android.annotation.NonNull java.util.List<android.app.admin.PolicyValue<V>> list) {
        this.mMostToLeastRestrictive = list;
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    android.app.admin.PolicyValue<V> resolve(@android.annotation.NonNull java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap) {
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        for (android.app.admin.PolicyValue<V> policyValue : this.mMostToLeastRestrictive) {
            if (linkedHashMap.containsValue(policyValue)) {
                return policyValue;
            }
        }
        return linkedHashMap.entrySet().stream().findFirst().get().getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
    public android.app.admin.MostRestrictive<V> mo3262getParcelableResolutionMechanism() {
        return new android.app.admin.MostRestrictive<>(this.mMostToLeastRestrictive);
    }

    public java.lang.String toString() {
        return "MostRestrictive { mMostToLeastRestrictive= " + this.mMostToLeastRestrictive + " }";
    }
}
