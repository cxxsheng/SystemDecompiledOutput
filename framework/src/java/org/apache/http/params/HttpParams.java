package org.apache.http.params;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public interface HttpParams {
    org.apache.http.params.HttpParams copy();

    boolean getBooleanParameter(java.lang.String str, boolean z);

    double getDoubleParameter(java.lang.String str, double d);

    int getIntParameter(java.lang.String str, int i);

    long getLongParameter(java.lang.String str, long j);

    java.lang.Object getParameter(java.lang.String str);

    boolean isParameterFalse(java.lang.String str);

    boolean isParameterTrue(java.lang.String str);

    boolean removeParameter(java.lang.String str);

    org.apache.http.params.HttpParams setBooleanParameter(java.lang.String str, boolean z);

    org.apache.http.params.HttpParams setDoubleParameter(java.lang.String str, double d);

    org.apache.http.params.HttpParams setIntParameter(java.lang.String str, int i);

    org.apache.http.params.HttpParams setLongParameter(java.lang.String str, long j);

    org.apache.http.params.HttpParams setParameter(java.lang.String str, java.lang.Object obj);
}
