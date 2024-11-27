package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public abstract class ExtensionLite<ContainingType extends com.android.framework.protobuf.MessageLite, Type> {
    public abstract Type getDefaultValue();

    public abstract com.android.framework.protobuf.WireFormat.FieldType getLiteType();

    public abstract com.android.framework.protobuf.MessageLite getMessageDefaultInstance();

    public abstract int getNumber();

    public abstract boolean isRepeated();

    boolean isLite() {
        return true;
    }
}
