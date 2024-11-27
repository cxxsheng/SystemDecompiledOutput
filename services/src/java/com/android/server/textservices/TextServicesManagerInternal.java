package com.android.server.textservices;

/* loaded from: classes2.dex */
public abstract class TextServicesManagerInternal {
    private static final com.android.server.textservices.TextServicesManagerInternal NOP = new com.android.server.textservices.TextServicesManagerInternal() { // from class: com.android.server.textservices.TextServicesManagerInternal.1
        @Override // com.android.server.textservices.TextServicesManagerInternal
        public android.view.textservice.SpellCheckerInfo getCurrentSpellCheckerForUser(int i) {
            return null;
        }
    };

    @android.annotation.Nullable
    public abstract android.view.textservice.SpellCheckerInfo getCurrentSpellCheckerForUser(int i);

    @android.annotation.NonNull
    public static com.android.server.textservices.TextServicesManagerInternal get() {
        com.android.server.textservices.TextServicesManagerInternal textServicesManagerInternal = (com.android.server.textservices.TextServicesManagerInternal) com.android.server.LocalServices.getService(com.android.server.textservices.TextServicesManagerInternal.class);
        return textServicesManagerInternal != null ? textServicesManagerInternal : NOP;
    }
}
