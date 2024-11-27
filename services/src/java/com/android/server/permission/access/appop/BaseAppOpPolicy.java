package com.android.server.permission.access.appop;

/* compiled from: BaseAppOpPolicy.kt */
/* loaded from: classes2.dex */
public abstract class BaseAppOpPolicy extends com.android.server.permission.access.SchemePolicy {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.BaseAppOpPersistence persistence;

    public BaseAppOpPolicy(@org.jetbrains.annotations.NotNull com.android.server.permission.access.appop.BaseAppOpPersistence persistence) {
        this.persistence = persistence;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    @org.jetbrains.annotations.NotNull
    public java.lang.String getObjectScheme() {
        return "app-op";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.permission.access.appop.BaseAppOpPersistence $this$parseUserState_u24lambda_u240 = this.persistence;
        $this$parseUserState_u24lambda_u240.parseUserState($this$parseUserState, state, userId);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
        com.android.server.permission.access.appop.BaseAppOpPersistence $this$serializeUserState_u24lambda_u241 = this.persistence;
        $this$serializeUserState_u24lambda_u241.serializeUserState($this$serializeUserState, state, userId);
    }
}
