package android.content.integrity;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class RuleSet {
    private final java.util.List<android.content.integrity.Rule> mRules;
    private final java.lang.String mVersion;

    private RuleSet(java.lang.String str, java.util.List<android.content.integrity.Rule> list) {
        this.mVersion = str;
        this.mRules = java.util.Collections.unmodifiableList(list);
    }

    public java.lang.String getVersion() {
        return this.mVersion;
    }

    public java.util.List<android.content.integrity.Rule> getRules() {
        return this.mRules;
    }

    public static class Builder {
        private java.util.List<android.content.integrity.Rule> mRules = new java.util.ArrayList();
        private java.lang.String mVersion;

        public android.content.integrity.RuleSet.Builder setVersion(java.lang.String str) {
            this.mVersion = str;
            return this;
        }

        public android.content.integrity.RuleSet.Builder addRules(java.util.List<android.content.integrity.Rule> list) {
            this.mRules.addAll(list);
            return this;
        }

        public android.content.integrity.RuleSet build() {
            java.util.Objects.requireNonNull(this.mVersion);
            return new android.content.integrity.RuleSet(this.mVersion, this.mRules);
        }
    }
}
