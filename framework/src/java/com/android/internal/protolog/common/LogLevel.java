package com.android.internal.protolog.common;

/* loaded from: classes5.dex */
public enum LogLevel {
    DEBUG(android.app.blob.XmlTags.ATTR_DESCRIPTION),
    VERBOSE("v"),
    INFO("i"),
    WARN("w"),
    ERROR("e"),
    WTF("wtf");

    public final java.lang.String shortCode;

    LogLevel(java.lang.String str) {
        this.shortCode = str;
    }
}
