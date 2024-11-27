package android.content.integrity;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class AppIntegrityManager {
    public static final java.lang.String EXTRA_STATUS = "android.content.integrity.extra.STATUS";
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_SUCCESS = 0;
    android.content.integrity.IAppIntegrityManager mManager;

    public AppIntegrityManager(android.content.integrity.IAppIntegrityManager iAppIntegrityManager) {
        this.mManager = iAppIntegrityManager;
    }

    public void updateRuleSet(android.content.integrity.RuleSet ruleSet, android.content.IntentSender intentSender) {
        try {
            this.mManager.updateRuleSet(ruleSet.getVersion(), new android.content.pm.ParceledListSlice<>(ruleSet.getRules()), intentSender);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public java.lang.String getCurrentRuleSetVersion() {
        try {
            return this.mManager.getCurrentRuleSetVersion();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public java.lang.String getCurrentRuleSetProvider() {
        try {
            return this.mManager.getCurrentRuleSetProvider();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public android.content.integrity.RuleSet getCurrentRuleSet() {
        try {
            android.content.pm.ParceledListSlice<android.content.integrity.Rule> currentRules = this.mManager.getCurrentRules();
            return new android.content.integrity.RuleSet.Builder().setVersion(this.mManager.getCurrentRuleSetVersion()).addRules(currentRules.getList()).build();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public java.util.List<java.lang.String> getWhitelistedRuleProviders() {
        try {
            return this.mManager.getWhitelistedRuleProviders();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
