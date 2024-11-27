package com.android.server.utils.quota;

/* loaded from: classes2.dex */
public interface Categorizer {
    public static final com.android.server.utils.quota.Categorizer SINGLE_CATEGORIZER = new com.android.server.utils.quota.Categorizer() { // from class: com.android.server.utils.quota.Categorizer$$ExternalSyntheticLambda0
        @Override // com.android.server.utils.quota.Categorizer
        public final com.android.server.utils.quota.Category getCategory(int i, java.lang.String str, java.lang.String str2) {
            com.android.server.utils.quota.Category lambda$static$0;
            lambda$static$0 = com.android.server.utils.quota.Categorizer.lambda$static$0(i, str, str2);
            return lambda$static$0;
        }
    };

    @android.annotation.NonNull
    com.android.server.utils.quota.Category getCategory(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.server.utils.quota.Category lambda$static$0(int i, java.lang.String str, java.lang.String str2) {
        return com.android.server.utils.quota.Category.SINGLE_CATEGORY;
    }
}
