package com.android.server.devicepolicy;

/* loaded from: classes.dex */
abstract class ResolutionMechanism<V> {
    ResolutionMechanism() {
    }

    /* renamed from: getParcelableResolutionMechanism */
    abstract android.app.admin.ResolutionMechanism<V> mo3262getParcelableResolutionMechanism();

    @android.annotation.Nullable
    abstract android.app.admin.PolicyValue<V> resolve(java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap);
}
