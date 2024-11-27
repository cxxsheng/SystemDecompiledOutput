package com.android.internal.protolog.common;

@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.PARAMETER})
/* loaded from: classes5.dex */
public @interface ProtoLogToolInjected {

    public enum Value {
        VIEWER_CONFIG_PATH,
        LEGACY_OUTPUT_FILE_PATH,
        LEGACY_VIEWER_CONFIG_PATH
    }

    com.android.internal.protolog.common.ProtoLogToolInjected.Value value();
}
