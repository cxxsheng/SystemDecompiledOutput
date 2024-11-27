package com.android.server.selinux;

/* loaded from: classes2.dex */
class SelinuxAuditLogBuilder {
    private static final java.lang.String SDK_SANDBOX_AUDIT = "sdk_sandbox_audit";
    private final com.android.server.selinux.SelinuxAuditLogBuilder.SelinuxAuditLog mAuditLog = new com.android.server.selinux.SelinuxAuditLogBuilder.SelinuxAuditLog();
    private java.util.Iterator<java.lang.String> mTokens;
    static final java.util.regex.Matcher SCONTEXT_MATCHER = java.util.regex.Pattern.compile("u:r:(?<stype>sdk_sandbox_audit):s0(:c)?(?<scategories>((,c)?\\d+)+)*").matcher("");
    static final java.util.regex.Matcher TCONTEXT_MATCHER = java.util.regex.Pattern.compile("u:object_r:(?<ttype>\\w+):s0(:c)?(?<tcategories>((,c)?\\d+)+)*").matcher("");
    static final java.util.regex.Matcher PATH_MATCHER = java.util.regex.Pattern.compile("\"(?<path>/\\w+(/\\w+)?)(/\\w+)*\"").matcher("");

    SelinuxAuditLogBuilder() {
    }

    void reset(java.lang.String str) {
        this.mTokens = java.util.Arrays.asList((java.lang.String[]) java.util.Optional.ofNullable(str).map(new java.util.function.Function() { // from class: com.android.server.selinux.SelinuxAuditLogBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String[] lambda$reset$0;
                lambda$reset$0 = com.android.server.selinux.SelinuxAuditLogBuilder.lambda$reset$0((java.lang.String) obj);
                return lambda$reset$0;
            }
        }).orElse(new java.lang.String[0])).iterator();
        this.mAuditLog.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$reset$0(java.lang.String str) {
        return str.split("\\s+|=");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    com.android.server.selinux.SelinuxAuditLogBuilder.SelinuxAuditLog build() {
        char c;
        while (this.mTokens.hasNext()) {
            java.lang.String next = this.mTokens.next();
            boolean z = false;
            switch (next.hashCode()) {
                case -1335395429:
                    if (next.equals("denied")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -879242876:
                    if (next.equals("tclass")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -517618017:
                    if (next.equals("permissive")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -488743460:
                    if (next.equals("scontext")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 123:
                    if (next.equals("{")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3433509:
                    if (next.equals("path")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 280295099:
                    if (next.equals("granted")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1254066875:
                    if (next.equals("tcontext")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mAuditLog.mGranted = true;
                    break;
                case 1:
                    this.mAuditLog.mGranted = false;
                    break;
                case 2:
                    java.util.stream.Stream.Builder builder = java.util.stream.Stream.builder();
                    while (!z && this.mTokens.hasNext()) {
                        java.lang.String next2 = this.mTokens.next();
                        if ("}".equals(next2)) {
                            z = true;
                        } else {
                            builder.add(next2);
                        }
                    }
                    if (!z) {
                        return null;
                    }
                    this.mAuditLog.mPermissions = (java.lang.String[]) builder.build().toArray(new java.util.function.IntFunction() { // from class: com.android.server.selinux.SelinuxAuditLogBuilder$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntFunction
                        public final java.lang.Object apply(int i) {
                            java.lang.String[] lambda$build$1;
                            lambda$build$1 = com.android.server.selinux.SelinuxAuditLogBuilder.lambda$build$1(i);
                            return lambda$build$1;
                        }
                    });
                    break;
                case 3:
                    if (!nextTokenMatches(SCONTEXT_MATCHER)) {
                        return null;
                    }
                    this.mAuditLog.mSType = SCONTEXT_MATCHER.group("stype");
                    this.mAuditLog.mSCategories = toCategories(SCONTEXT_MATCHER.group("scategories"));
                    break;
                case 4:
                    if (!nextTokenMatches(TCONTEXT_MATCHER)) {
                        return null;
                    }
                    this.mAuditLog.mTType = TCONTEXT_MATCHER.group("ttype");
                    this.mAuditLog.mTCategories = toCategories(TCONTEXT_MATCHER.group("tcategories"));
                    break;
                case 5:
                    if (!this.mTokens.hasNext()) {
                        return null;
                    }
                    this.mAuditLog.mTClass = this.mTokens.next();
                    break;
                case 6:
                    if (!nextTokenMatches(PATH_MATCHER)) {
                        break;
                    } else {
                        this.mAuditLog.mPath = PATH_MATCHER.group("path");
                        break;
                    }
                case 7:
                    if (!this.mTokens.hasNext()) {
                        return null;
                    }
                    this.mAuditLog.mPermissive = "1".equals(this.mTokens.next());
                    break;
            }
        }
        return this.mAuditLog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$build$1(int i) {
        return new java.lang.String[i];
    }

    boolean nextTokenMatches(java.util.regex.Matcher matcher) {
        return this.mTokens.hasNext() && matcher.reset(this.mTokens.next()).matches();
    }

    static int[] toCategories(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return java.util.Arrays.stream(str.split(",c")).mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda14()).toArray();
    }

    static class SelinuxAuditLog {
        boolean mGranted = false;
        java.lang.String[] mPermissions = null;
        java.lang.String mSType = null;
        int[] mSCategories = null;
        java.lang.String mTType = null;
        int[] mTCategories = null;
        java.lang.String mTClass = null;
        java.lang.String mPath = null;
        boolean mPermissive = false;

        SelinuxAuditLog() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mGranted = false;
            this.mPermissions = null;
            this.mSType = null;
            this.mSCategories = null;
            this.mTType = null;
            this.mTCategories = null;
            this.mTClass = null;
            this.mPath = null;
            this.mPermissive = false;
        }
    }
}
