package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class TopPriority<V> extends com.android.server.devicepolicy.ResolutionMechanism<V> {
    private final java.util.List<java.lang.String> mHighestToLowestPriorityAuthorities;

    TopPriority(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        java.util.Objects.requireNonNull(list);
        this.mHighestToLowestPriorityAuthorities = list;
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    android.app.admin.PolicyValue<V> resolve(@android.annotation.NonNull java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap) {
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        for (final java.lang.String str : this.mHighestToLowestPriorityAuthorities) {
            java.util.Optional<com.android.server.devicepolicy.EnforcingAdmin> findFirst = linkedHashMap.keySet().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.devicepolicy.TopPriority$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$resolve$0;
                    lambda$resolve$0 = com.android.server.devicepolicy.TopPriority.lambda$resolve$0(str, (com.android.server.devicepolicy.EnforcingAdmin) obj);
                    return lambda$resolve$0;
                }
            }).findFirst();
            if (findFirst.isPresent()) {
                return linkedHashMap.get(findFirst.get());
            }
        }
        return linkedHashMap.entrySet().stream().findFirst().get().getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$resolve$0(java.lang.String str, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        return enforcingAdmin.hasAuthority(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.ResolutionMechanism
    /* renamed from: getParcelableResolutionMechanism, reason: merged with bridge method [inline-methods] */
    public android.app.admin.TopPriority<V> mo3262getParcelableResolutionMechanism() {
        return new android.app.admin.TopPriority<>(getParcelableAuthorities());
    }

    private java.util.List<android.app.admin.Authority> getParcelableAuthorities() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = this.mHighestToLowestPriorityAuthorities.iterator();
        while (it.hasNext()) {
            arrayList.add(com.android.server.devicepolicy.EnforcingAdmin.getParcelableAuthority(it.next()));
        }
        return arrayList;
    }

    public java.lang.String toString() {
        return "TopPriority { mHighestToLowestPriorityAuthorities= " + this.mHighestToLowestPriorityAuthorities + " }";
    }
}
