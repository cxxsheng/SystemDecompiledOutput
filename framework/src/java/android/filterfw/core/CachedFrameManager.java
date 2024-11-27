package android.filterfw.core;

/* loaded from: classes.dex */
public class CachedFrameManager extends android.filterfw.core.SimpleFrameManager {
    private int mStorageCapacity = 25165824;
    private int mStorageSize = 0;
    private int mTimeStamp = 0;
    private java.util.SortedMap<java.lang.Integer, android.filterfw.core.Frame> mAvailableFrames = new java.util.TreeMap();

    @Override // android.filterfw.core.SimpleFrameManager, android.filterfw.core.FrameManager
    public android.filterfw.core.Frame newFrame(android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.Frame findAvailableFrame = findAvailableFrame(frameFormat, 0, 0L);
        if (findAvailableFrame == null) {
            findAvailableFrame = super.newFrame(frameFormat);
        }
        findAvailableFrame.setTimestamp(-2L);
        return findAvailableFrame;
    }

    @Override // android.filterfw.core.SimpleFrameManager, android.filterfw.core.FrameManager
    public android.filterfw.core.Frame newBoundFrame(android.filterfw.core.FrameFormat frameFormat, int i, long j) {
        android.filterfw.core.Frame findAvailableFrame = findAvailableFrame(frameFormat, i, j);
        if (findAvailableFrame == null) {
            findAvailableFrame = super.newBoundFrame(frameFormat, i, j);
        }
        findAvailableFrame.setTimestamp(-2L);
        return findAvailableFrame;
    }

    @Override // android.filterfw.core.SimpleFrameManager, android.filterfw.core.FrameManager
    public android.filterfw.core.Frame retainFrame(android.filterfw.core.Frame frame) {
        return super.retainFrame(frame);
    }

    @Override // android.filterfw.core.SimpleFrameManager, android.filterfw.core.FrameManager
    public android.filterfw.core.Frame releaseFrame(android.filterfw.core.Frame frame) {
        if (frame.isReusable()) {
            int decRefCount = frame.decRefCount();
            if (decRefCount == 0 && frame.hasNativeAllocation()) {
                if (!storeFrame(frame)) {
                    frame.releaseNativeAllocation();
                    return null;
                }
                return null;
            }
            if (decRefCount < 0) {
                throw new java.lang.RuntimeException("Frame reference count dropped below 0!");
            }
        } else {
            super.releaseFrame(frame);
        }
        return frame;
    }

    public void clearCache() {
        java.util.Iterator<android.filterfw.core.Frame> it = this.mAvailableFrames.values().iterator();
        while (it.hasNext()) {
            it.next().releaseNativeAllocation();
        }
        this.mAvailableFrames.clear();
    }

    @Override // android.filterfw.core.FrameManager
    public void tearDown() {
        clearCache();
    }

    private boolean storeFrame(android.filterfw.core.Frame frame) {
        synchronized (this.mAvailableFrames) {
            int size = frame.getFormat().getSize();
            if (size > this.mStorageCapacity) {
                return false;
            }
            int i = this.mStorageSize + size;
            while (i > this.mStorageCapacity) {
                dropOldestFrame();
                i = this.mStorageSize + size;
            }
            frame.onFrameStore();
            this.mStorageSize = i;
            this.mAvailableFrames.put(java.lang.Integer.valueOf(this.mTimeStamp), frame);
            this.mTimeStamp++;
            return true;
        }
    }

    private void dropOldestFrame() {
        int intValue = this.mAvailableFrames.firstKey().intValue();
        android.filterfw.core.Frame frame = this.mAvailableFrames.get(java.lang.Integer.valueOf(intValue));
        this.mStorageSize -= frame.getFormat().getSize();
        frame.releaseNativeAllocation();
        this.mAvailableFrames.remove(java.lang.Integer.valueOf(intValue));
    }

    private android.filterfw.core.Frame findAvailableFrame(android.filterfw.core.FrameFormat frameFormat, int i, long j) {
        synchronized (this.mAvailableFrames) {
            for (java.util.Map.Entry<java.lang.Integer, android.filterfw.core.Frame> entry : this.mAvailableFrames.entrySet()) {
                android.filterfw.core.Frame value = entry.getValue();
                if (value.getFormat().isReplaceableBy(frameFormat) && i == value.getBindingType() && (i == 0 || j == value.getBindingId())) {
                    super.retainFrame(value);
                    this.mAvailableFrames.remove(entry.getKey());
                    value.onFrameFetch();
                    value.reset(frameFormat);
                    this.mStorageSize -= frameFormat.getSize();
                    return value;
                }
            }
            return null;
        }
    }
}
