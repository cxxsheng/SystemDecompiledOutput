package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public class DomainVerificationCollector {
    private static final int MAX_DOMAINS_BYTE_SIZE = 1048576;
    public static final long RESTRICT_DOMAINS = 175408749;

    @android.annotation.NonNull
    private final java.util.regex.Matcher mDomainMatcher = DOMAIN_NAME_WITH_WILDCARD.matcher("");

    @android.annotation.NonNull
    private final com.android.server.compat.PlatformCompat mPlatformCompat;

    @android.annotation.NonNull
    private final com.android.server.SystemConfig mSystemConfig;
    private static final java.util.regex.Pattern DOMAIN_NAME_WITH_WILDCARD = java.util.regex.Pattern.compile("(\\*\\.)?" + android.util.Patterns.DOMAIN_NAME.pattern());
    private static final java.util.function.BiFunction<android.util.ArraySet<java.lang.String>, java.lang.String, java.lang.Boolean> ARRAY_SET_COLLECTOR = new java.util.function.BiFunction() { // from class: com.android.server.pm.verify.domain.DomainVerificationCollector$$ExternalSyntheticLambda0
        @Override // java.util.function.BiFunction
        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
            java.lang.Boolean lambda$static$0;
            lambda$static$0 = com.android.server.pm.verify.domain.DomainVerificationCollector.lambda$static$0((android.util.ArraySet) obj, (java.lang.String) obj2);
            return lambda$static$0;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$static$0(android.util.ArraySet arraySet, java.lang.String str) {
        arraySet.add(str);
        return null;
    }

    public DomainVerificationCollector(@android.annotation.NonNull com.android.server.compat.PlatformCompat platformCompat, @android.annotation.NonNull com.android.server.SystemConfig systemConfig) {
        this.mPlatformCompat = platformCompat;
        this.mSystemConfig = systemConfig;
    }

    @android.annotation.NonNull
    public android.util.ArraySet<java.lang.String> collectAllWebDomains(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return collectDomains(androidPackage, false, true);
    }

    @android.annotation.NonNull
    public android.util.ArraySet<java.lang.String> collectValidAutoVerifyDomains(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return collectDomains(androidPackage, true, true);
    }

    @android.annotation.NonNull
    public android.util.ArraySet<java.lang.String> collectInvalidAutoVerifyDomains(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return collectDomains(androidPackage, true, false);
    }

    public boolean containsWebDomain(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull final java.lang.String str) {
        return collectDomains(androidPackage, false, true, null, new java.util.function.BiFunction() { // from class: com.android.server.pm.verify.domain.DomainVerificationCollector$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                java.lang.Boolean lambda$containsWebDomain$1;
                lambda$containsWebDomain$1 = com.android.server.pm.verify.domain.DomainVerificationCollector.lambda$containsWebDomain$1(str, (java.lang.Void) obj, (java.lang.String) obj2);
                return lambda$containsWebDomain$1;
            }
        }) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$containsWebDomain$1(java.lang.String str, java.lang.Void r1, java.lang.String str2) {
        if (java.util.Objects.equals(str, str2)) {
            return true;
        }
        return null;
    }

    public boolean containsAutoVerifyDomain(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull final java.lang.String str) {
        return collectDomains(androidPackage, true, true, null, new java.util.function.BiFunction() { // from class: com.android.server.pm.verify.domain.DomainVerificationCollector$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                java.lang.Boolean lambda$containsAutoVerifyDomain$2;
                lambda$containsAutoVerifyDomain$2 = com.android.server.pm.verify.domain.DomainVerificationCollector.lambda$containsAutoVerifyDomain$2(str, (java.lang.Void) obj, (java.lang.String) obj2);
                return lambda$containsAutoVerifyDomain$2;
            }
        }) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$containsAutoVerifyDomain$2(java.lang.String str, java.lang.Void r1, java.lang.String str2) {
        if (java.util.Objects.equals(str, str2)) {
            return true;
        }
        return null;
    }

    @android.annotation.NonNull
    private android.util.ArraySet<java.lang.String> collectDomains(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2) {
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        collectDomains(androidPackage, z, z2, arraySet, ARRAY_SET_COLLECTOR);
        return arraySet;
    }

    @android.annotation.NonNull
    private <InitialValue, ReturnValue> ReturnValue collectDomains(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2, @android.annotation.Nullable InitialValue initialvalue, @android.annotation.NonNull java.util.function.BiFunction<InitialValue, java.lang.String, ReturnValue> biFunction) {
        if (com.android.server.pm.verify.domain.DomainVerificationUtils.isChangeEnabled(this.mPlatformCompat, androidPackage, RESTRICT_DOMAINS)) {
            return (ReturnValue) collectDomainsInternal(androidPackage, z, z2, initialvalue, biFunction);
        }
        return (ReturnValue) collectDomainsLegacy(androidPackage, z, z2, initialvalue, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [int] */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r12v2, types: [android.content.IntentFilter] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.util.List] */
    @android.annotation.Nullable
    private <InitialValue, ReturnValue> ReturnValue collectDomainsLegacy(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2, @android.annotation.Nullable InitialValue initialvalue, @android.annotation.NonNull java.util.function.BiFunction<InitialValue, java.lang.String, ReturnValue> biFunction) {
        if (!z) {
            return (ReturnValue) collectDomainsInternal(androidPackage, false, true, initialvalue, biFunction);
        }
        java.util.List activities = androidPackage.getActivities();
        int size = activities.size();
        boolean contains = this.mSystemConfig.getLinkedApps().contains(androidPackage.getPackageName());
        boolean z3 = false;
        if (!contains) {
            for (int i = 0; i < size && !contains; i++) {
                java.util.List intents = ((com.android.internal.pm.pkg.component.ParsedActivity) activities.get(i)).getIntents();
                int size2 = intents.size();
                for (int i2 = 0; i2 < size2 && !contains; i2++) {
                    contains = ((com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(i2)).getIntentFilter().needsVerification();
                }
            }
            if (!contains) {
                return null;
            }
        }
        int i3 = 0;
        int i4 = 0;
        boolean z4 = true;
        while (i3 < size && z4) {
            ?? intents2 = ((com.android.internal.pm.pkg.component.ParsedActivity) activities.get(i3)).getIntents();
            int size3 = intents2.size();
            for (?? r11 = z3; r11 < size3 && z4; r11++) {
                ?? intentFilter = ((com.android.internal.pm.pkg.component.ParsedIntentInfo) intents2.get(r11)).getIntentFilter();
                if (intentFilter.handlesWebUris(z3)) {
                    int countDataAuthorities = intentFilter.countDataAuthorities();
                    for (?? r14 = z3; r14 < countDataAuthorities; r14++) {
                        java.lang.String host = intentFilter.getDataAuthority(r14).getHost();
                        if (isValidHost(host) == z2) {
                            i4 += byteSizeOf(host);
                            boolean z5 = i4 < 1048576;
                            ReturnValue apply = biFunction.apply(initialvalue, host);
                            if (apply == null) {
                                z4 = z5;
                            } else {
                                return apply;
                            }
                        }
                    }
                }
                z3 = false;
            }
            i3++;
            z3 = false;
        }
        return null;
    }

    @android.annotation.Nullable
    private <InitialValue, ReturnValue> ReturnValue collectDomainsInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2, @android.annotation.Nullable InitialValue initialvalue, @android.annotation.NonNull java.util.function.BiFunction<InitialValue, java.lang.String, ReturnValue> biFunction) {
        com.android.server.pm.verify.domain.DomainVerificationCollector domainVerificationCollector = this;
        java.util.List activities = androidPackage.getActivities();
        int size = activities.size();
        int i = 0;
        boolean z3 = true;
        int i2 = 0;
        while (i < size && z3) {
            java.util.List intents = ((com.android.internal.pm.pkg.component.ParsedActivity) activities.get(i)).getIntents();
            int size2 = intents.size();
            int i3 = 0;
            while (i3 < size2 && z3) {
                android.content.IntentFilter intentFilter = ((com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(i3)).getIntentFilter();
                if ((!z || intentFilter.getAutoVerify()) && intentFilter.hasCategory("android.intent.category.DEFAULT") && intentFilter.handlesWebUris(z)) {
                    int countDataAuthorities = intentFilter.countDataAuthorities();
                    int i4 = 0;
                    while (i4 < countDataAuthorities && z3) {
                        java.lang.String host = intentFilter.getDataAuthority(i4).getHost();
                        if (domainVerificationCollector.isValidHost(host) == z2) {
                            i2 += domainVerificationCollector.byteSizeOf(host);
                            boolean z4 = i2 < 1048576;
                            ReturnValue apply = biFunction.apply(initialvalue, host);
                            if (apply == null) {
                                z3 = z4;
                            } else {
                                return apply;
                            }
                        }
                        i4++;
                        domainVerificationCollector = this;
                    }
                }
                i3++;
                domainVerificationCollector = this;
            }
            i++;
            domainVerificationCollector = this;
        }
        return null;
    }

    private int byteSizeOf(java.lang.String str) {
        return android.content.pm.verify.domain.DomainVerificationUtils.estimatedByteSizeOf(str);
    }

    private boolean isValidHost(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        this.mDomainMatcher.reset(str);
        return this.mDomainMatcher.matches();
    }
}
