package com.android.server.integrity.engine;

/* loaded from: classes2.dex */
final class RuleEvaluator {
    RuleEvaluator() {
    }

    @android.annotation.NonNull
    static com.android.server.integrity.model.IntegrityCheckResult evaluateRules(java.util.List<android.content.integrity.Rule> list, final android.content.integrity.AppInstallMetadata appInstallMetadata) {
        java.util.List list2 = (java.util.List) list.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.integrity.engine.RuleEvaluator$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$evaluateRules$0;
                lambda$evaluateRules$0 = com.android.server.integrity.engine.RuleEvaluator.lambda$evaluateRules$0(appInstallMetadata, (android.content.integrity.Rule) obj);
                return lambda$evaluateRules$0;
            }
        }).collect(java.util.stream.Collectors.toList());
        java.util.List list3 = (java.util.List) list2.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.integrity.engine.RuleEvaluator$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$evaluateRules$1;
                lambda$evaluateRules$1 = com.android.server.integrity.engine.RuleEvaluator.lambda$evaluateRules$1((android.content.integrity.Rule) obj);
                return lambda$evaluateRules$1;
            }
        }).collect(java.util.stream.Collectors.toList());
        if (!list3.isEmpty()) {
            return com.android.server.integrity.model.IntegrityCheckResult.allow(list3);
        }
        java.util.List list4 = (java.util.List) list2.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.integrity.engine.RuleEvaluator$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$evaluateRules$2;
                lambda$evaluateRules$2 = com.android.server.integrity.engine.RuleEvaluator.lambda$evaluateRules$2((android.content.integrity.Rule) obj);
                return lambda$evaluateRules$2;
            }
        }).collect(java.util.stream.Collectors.toList());
        if (!list4.isEmpty()) {
            return com.android.server.integrity.model.IntegrityCheckResult.deny(list4);
        }
        return com.android.server.integrity.model.IntegrityCheckResult.allow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$evaluateRules$0(android.content.integrity.AppInstallMetadata appInstallMetadata, android.content.integrity.Rule rule) {
        return rule.getFormula().matches(appInstallMetadata);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$evaluateRules$1(android.content.integrity.Rule rule) {
        return rule.getEffect() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$evaluateRules$2(android.content.integrity.Rule rule) {
        return rule.getEffect() == 0;
    }
}
