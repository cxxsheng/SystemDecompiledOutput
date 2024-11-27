package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class Program {
    public abstract java.lang.Object getHostValue(java.lang.String str);

    public abstract void process(android.filterfw.core.Frame[] frameArr, android.filterfw.core.Frame frame);

    public abstract void setHostValue(java.lang.String str, java.lang.Object obj);

    public void process(android.filterfw.core.Frame frame, android.filterfw.core.Frame frame2) {
        process(new android.filterfw.core.Frame[]{frame}, frame2);
    }

    public void reset() {
    }
}
