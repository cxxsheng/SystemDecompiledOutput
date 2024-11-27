package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class MostRecent<V> extends com.android.server.devicepolicy.ResolutionMechanism<V> {
    MostRecent() {
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    android.app.admin.PolicyValue<V> resolve(@android.annotation.NonNull java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap) {
        java.util.ArrayList arrayList = new java.util.ArrayList(linkedHashMap.entrySet());
        if (arrayList.isEmpty()) {
            return null;
        }
        return (android.app.admin.PolicyValue) ((java.util.Map.Entry) arrayList.get(arrayList.size() - 1)).getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
    public android.app.admin.MostRecent<V> mo3262getParcelableResolutionMechanism() {
        return new android.app.admin.MostRecent<>();
    }

    public java.lang.String toString() {
        return "MostRecent {}";
    }
}
