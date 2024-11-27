package android.filterpacks.performance;

/* loaded from: classes.dex */
public class ThroughputFilter extends android.filterfw.core.Filter {
    private long mLastTime;
    private android.filterfw.core.FrameFormat mOutputFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "period")
    private int mPeriod;
    private int mPeriodFrameCount;
    private int mTotalFrameCount;

    public ThroughputFilter(java.lang.String str) {
        super(str);
        this.mPeriod = 5;
        this.mLastTime = 0L;
        this.mTotalFrameCount = 0;
        this.mPeriodFrameCount = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("frame");
        this.mOutputFormat = android.filterfw.format.ObjectFormat.fromClass(android.filterpacks.performance.Throughput.class, 1);
        addOutputBasedOnInput("frame", "frame");
        addOutputPort("throughput", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override // android.filterfw.core.Filter
    public void open(android.filterfw.core.FilterContext filterContext) {
        this.mTotalFrameCount = 0;
        this.mPeriodFrameCount = 0;
        this.mLastTime = 0L;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput("frame");
        pushOutput("frame", pullInput);
        this.mTotalFrameCount++;
        this.mPeriodFrameCount++;
        if (this.mLastTime == 0) {
            this.mLastTime = android.os.SystemClock.elapsedRealtime();
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (elapsedRealtime - this.mLastTime >= this.mPeriod * 1000) {
            android.filterfw.core.FrameFormat format = pullInput.getFormat();
            android.filterpacks.performance.Throughput throughput = new android.filterpacks.performance.Throughput(this.mTotalFrameCount, this.mPeriodFrameCount, this.mPeriod, format.getWidth() * format.getHeight());
            android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
            newFrame.setObjectValue(throughput);
            pushOutput("throughput", newFrame);
            this.mLastTime = elapsedRealtime;
            this.mPeriodFrameCount = 0;
        }
    }
}
