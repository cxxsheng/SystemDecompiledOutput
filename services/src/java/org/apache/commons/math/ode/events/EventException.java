package org.apache.commons.math.ode.events;

/* loaded from: classes3.dex */
public class EventException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = -898215297400035290L;

    @java.lang.Deprecated
    public EventException(java.lang.String str, java.lang.Object... objArr) {
        super(str, objArr);
    }

    public EventException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }

    public EventException(java.lang.Throwable th) {
        super(th);
    }
}
