package com.android.server.pm;

/* loaded from: classes2.dex */
public final class QueryIntentActivitiesResult {
    public boolean addInstant;
    public java.util.List<android.content.pm.ResolveInfo> answer;
    public java.util.List<android.content.pm.ResolveInfo> result;
    public boolean sortResult;

    QueryIntentActivitiesResult(java.util.List<android.content.pm.ResolveInfo> list) {
        this.sortResult = false;
        this.addInstant = false;
        this.result = null;
        this.answer = null;
        this.answer = list;
    }

    QueryIntentActivitiesResult(boolean z, boolean z2, java.util.List<android.content.pm.ResolveInfo> list) {
        this.sortResult = false;
        this.addInstant = false;
        this.result = null;
        this.answer = null;
        this.sortResult = z;
        this.addInstant = z2;
        this.result = list;
    }
}
