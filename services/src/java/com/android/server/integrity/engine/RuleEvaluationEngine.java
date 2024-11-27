package com.android.server.integrity.engine;

/* loaded from: classes2.dex */
public class RuleEvaluationEngine {
    private static final java.lang.String TAG = "RuleEvaluation";
    private static com.android.server.integrity.engine.RuleEvaluationEngine sRuleEvaluationEngine;
    private final com.android.server.integrity.IntegrityFileManager mIntegrityFileManager;

    @com.android.internal.annotations.VisibleForTesting
    RuleEvaluationEngine(com.android.server.integrity.IntegrityFileManager integrityFileManager) {
        this.mIntegrityFileManager = integrityFileManager;
    }

    public static synchronized com.android.server.integrity.engine.RuleEvaluationEngine getRuleEvaluationEngine() {
        synchronized (com.android.server.integrity.engine.RuleEvaluationEngine.class) {
            if (sRuleEvaluationEngine == null) {
                return new com.android.server.integrity.engine.RuleEvaluationEngine(com.android.server.integrity.IntegrityFileManager.getInstance());
            }
            return sRuleEvaluationEngine;
        }
    }

    public com.android.server.integrity.model.IntegrityCheckResult evaluate(android.content.integrity.AppInstallMetadata appInstallMetadata) {
        return com.android.server.integrity.engine.RuleEvaluator.evaluateRules(loadRules(appInstallMetadata), appInstallMetadata);
    }

    private java.util.List<android.content.integrity.Rule> loadRules(android.content.integrity.AppInstallMetadata appInstallMetadata) {
        if (!this.mIntegrityFileManager.initialized()) {
            android.util.Slog.w(TAG, "Integrity rule files are not available.");
            return java.util.Collections.emptyList();
        }
        try {
            return this.mIntegrityFileManager.readRules(appInstallMetadata);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error loading rules.", e);
            return new java.util.ArrayList();
        }
    }
}
