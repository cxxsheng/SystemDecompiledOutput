package android.filterfw.core;

/* loaded from: classes.dex */
public class FilterContext {
    private android.filterfw.core.FrameManager mFrameManager;
    private android.filterfw.core.GLEnvironment mGLEnvironment;
    private java.util.HashMap<java.lang.String, android.filterfw.core.Frame> mStoredFrames = new java.util.HashMap<>();
    private java.util.Set<android.filterfw.core.FilterGraph> mGraphs = new java.util.HashSet();

    public interface OnFrameReceivedListener {
        void onFrameReceived(android.filterfw.core.Filter filter, android.filterfw.core.Frame frame, java.lang.Object obj);
    }

    public android.filterfw.core.FrameManager getFrameManager() {
        return this.mFrameManager;
    }

    public void setFrameManager(android.filterfw.core.FrameManager frameManager) {
        if (frameManager == null) {
            throw new java.lang.NullPointerException("Attempting to set null FrameManager!");
        }
        if (frameManager.getContext() != null) {
            throw new java.lang.IllegalArgumentException("Attempting to set FrameManager which is already bound to another FilterContext!");
        }
        this.mFrameManager = frameManager;
        this.mFrameManager.setContext(this);
    }

    public android.filterfw.core.GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    public void initGLEnvironment(android.filterfw.core.GLEnvironment gLEnvironment) {
        if (this.mGLEnvironment == null) {
            this.mGLEnvironment = gLEnvironment;
            return;
        }
        throw new java.lang.RuntimeException("Attempting to re-initialize GL Environment for FilterContext!");
    }

    public synchronized void storeFrame(java.lang.String str, android.filterfw.core.Frame frame) {
        android.filterfw.core.Frame fetchFrame = fetchFrame(str);
        if (fetchFrame != null) {
            fetchFrame.release();
        }
        frame.onFrameStore();
        this.mStoredFrames.put(str, frame.retain());
    }

    public synchronized android.filterfw.core.Frame fetchFrame(java.lang.String str) {
        android.filterfw.core.Frame frame;
        frame = this.mStoredFrames.get(str);
        if (frame != null) {
            frame.onFrameFetch();
        }
        return frame;
    }

    public synchronized void removeFrame(java.lang.String str) {
        android.filterfw.core.Frame frame = this.mStoredFrames.get(str);
        if (frame != null) {
            this.mStoredFrames.remove(str);
            frame.release();
        }
    }

    public synchronized void tearDown() {
        java.util.Iterator<android.filterfw.core.Frame> it = this.mStoredFrames.values().iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.mStoredFrames.clear();
        java.util.Iterator<android.filterfw.core.FilterGraph> it2 = this.mGraphs.iterator();
        while (it2.hasNext()) {
            it2.next().tearDown(this);
        }
        this.mGraphs.clear();
        if (this.mFrameManager != null) {
            this.mFrameManager.tearDown();
            this.mFrameManager = null;
        }
        if (this.mGLEnvironment != null) {
            this.mGLEnvironment.tearDown();
            this.mGLEnvironment = null;
        }
    }

    final void addGraph(android.filterfw.core.FilterGraph filterGraph) {
        this.mGraphs.add(filterGraph);
    }
}
