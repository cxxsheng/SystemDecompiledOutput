package com.android.server.integrity.model;

/* loaded from: classes2.dex */
public final class IntegrityCheckResult {
    private final com.android.server.integrity.model.IntegrityCheckResult.Effect mEffect;
    private final java.util.List<android.content.integrity.Rule> mRuleList;

    public enum Effect {
        ALLOW,
        DENY
    }

    private IntegrityCheckResult(com.android.server.integrity.model.IntegrityCheckResult.Effect effect, @android.annotation.Nullable java.util.List<android.content.integrity.Rule> list) {
        this.mEffect = effect;
        this.mRuleList = list;
    }

    public com.android.server.integrity.model.IntegrityCheckResult.Effect getEffect() {
        return this.mEffect;
    }

    public java.util.List<android.content.integrity.Rule> getMatchedRules() {
        return this.mRuleList;
    }

    public static com.android.server.integrity.model.IntegrityCheckResult allow() {
        return new com.android.server.integrity.model.IntegrityCheckResult(com.android.server.integrity.model.IntegrityCheckResult.Effect.ALLOW, java.util.Collections.emptyList());
    }

    public static com.android.server.integrity.model.IntegrityCheckResult allow(java.util.List<android.content.integrity.Rule> list) {
        return new com.android.server.integrity.model.IntegrityCheckResult(com.android.server.integrity.model.IntegrityCheckResult.Effect.ALLOW, list);
    }

    public static com.android.server.integrity.model.IntegrityCheckResult deny(java.util.List<android.content.integrity.Rule> list) {
        return new com.android.server.integrity.model.IntegrityCheckResult(com.android.server.integrity.model.IntegrityCheckResult.Effect.DENY, list);
    }

    public int getLoggingResponse() {
        if (getEffect() == com.android.server.integrity.model.IntegrityCheckResult.Effect.DENY) {
            return 2;
        }
        if (getEffect() == com.android.server.integrity.model.IntegrityCheckResult.Effect.ALLOW && getMatchedRules().isEmpty()) {
            return 1;
        }
        if (getEffect() == com.android.server.integrity.model.IntegrityCheckResult.Effect.ALLOW && !getMatchedRules().isEmpty()) {
            return 3;
        }
        throw new java.lang.IllegalStateException("IntegrityCheckResult is not valid.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isCausedByAppCertRule$0(android.content.integrity.Rule rule) {
        return rule.getFormula().isAppCertificateFormula();
    }

    public boolean isCausedByAppCertRule() {
        return this.mRuleList.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.integrity.model.IntegrityCheckResult$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isCausedByAppCertRule$0;
                lambda$isCausedByAppCertRule$0 = com.android.server.integrity.model.IntegrityCheckResult.lambda$isCausedByAppCertRule$0((android.content.integrity.Rule) obj);
                return lambda$isCausedByAppCertRule$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isCausedByInstallerRule$1(android.content.integrity.Rule rule) {
        return rule.getFormula().isInstallerFormula();
    }

    public boolean isCausedByInstallerRule() {
        return this.mRuleList.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.integrity.model.IntegrityCheckResult$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isCausedByInstallerRule$1;
                lambda$isCausedByInstallerRule$1 = com.android.server.integrity.model.IntegrityCheckResult.lambda$isCausedByInstallerRule$1((android.content.integrity.Rule) obj);
                return lambda$isCausedByInstallerRule$1;
            }
        });
    }
}
