package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public final class MutableAccessState extends com.android.server.permission.access.AccessState {
    private MutableAccessState(com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.ExternalState, com.android.server.permission.access.MutableExternalState> mutableReference, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.SystemState, com.android.server.permission.access.MutableSystemState> mutableReference2, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>> mutableReference3) {
        super(mutableReference, mutableReference2, mutableReference3, null);
    }

    public MutableAccessState() {
        this(new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.MutableExternalState()), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.MutableSystemState()), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIntReferenceMap(null, 1, null)));
    }

    public MutableAccessState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState accessState) {
        this(accessState.getExternalStateReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable(), accessState.getSystemStateReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable(), accessState.getUserStatesReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable());
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.MutableExternalState mutateExternalState() {
        return (com.android.server.permission.access.MutableExternalState) getExternalStateReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
    }

    public static /* synthetic */ com.android.server.permission.access.MutableSystemState mutateSystemState$default(com.android.server.permission.access.MutableAccessState mutableAccessState, int i, int i2, java.lang.Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        return mutableAccessState.mutateSystemState(i);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.MutableSystemState mutateSystemState(int writeMode) {
        com.android.server.permission.access.immutable.Immutable mutate = getSystemStateReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
        com.android.server.permission.access.MutableSystemState $this$mutateSystemState_u24lambda_u240 = (com.android.server.permission.access.MutableSystemState) mutate;
        $this$mutateSystemState_u24lambda_u240.requestWriteMode(writeMode);
        return (com.android.server.permission.access.MutableSystemState) mutate;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState> mutateUserStatesNoWrite() {
        return (com.android.server.permission.access.immutable.MutableIntReferenceMap) getUserStatesReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
    }

    public static /* synthetic */ com.android.server.permission.access.MutableUserState mutateUserState$default(com.android.server.permission.access.MutableAccessState mutableAccessState, int i, int i2, int i3, java.lang.Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        return mutableAccessState.mutateUserState(i, i2);
    }

    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.MutableUserState mutateUserState(int userId, int writeMode) {
        com.android.server.permission.access.MutableUserState $this$mutateUserState_u24lambda_u241 = (com.android.server.permission.access.MutableUserState) mutateUserStatesNoWrite().mutate(userId);
        if ($this$mutateUserState_u24lambda_u241 == null) {
            return null;
        }
        $this$mutateUserState_u24lambda_u241.requestWriteMode(writeMode);
        return $this$mutateUserState_u24lambda_u241;
    }

    public static /* synthetic */ com.android.server.permission.access.MutableUserState mutateUserStateAt$default(com.android.server.permission.access.MutableAccessState mutableAccessState, int i, int i2, int i3, java.lang.Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        return mutableAccessState.mutateUserStateAt(i, i2);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.MutableUserState mutateUserStateAt(int index, int writeMode) {
        com.android.server.permission.access.immutable.Immutable mutateAt = mutateUserStatesNoWrite().mutateAt(index);
        com.android.server.permission.access.MutableUserState $this$mutateUserStateAt_u24lambda_u242 = (com.android.server.permission.access.MutableUserState) mutateAt;
        $this$mutateUserStateAt_u24lambda_u242.requestWriteMode(writeMode);
        return (com.android.server.permission.access.MutableUserState) mutateAt;
    }
}
