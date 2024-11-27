package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Sampler extends android.renderscript.BaseObj {
    float mAniso;
    android.renderscript.Sampler.Value mMag;
    android.renderscript.Sampler.Value mMin;
    android.renderscript.Sampler.Value mWrapR;
    android.renderscript.Sampler.Value mWrapS;
    android.renderscript.Sampler.Value mWrapT;

    public enum Value {
        NEAREST(0),
        LINEAR(1),
        LINEAR_MIP_LINEAR(2),
        LINEAR_MIP_NEAREST(5),
        WRAP(3),
        CLAMP(4),
        MIRRORED_REPEAT(6);

        int mID;

        Value(int i) {
            this.mID = i;
        }
    }

    Sampler(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    public android.renderscript.Sampler.Value getMinification() {
        return this.mMin;
    }

    public android.renderscript.Sampler.Value getMagnification() {
        return this.mMag;
    }

    public android.renderscript.Sampler.Value getWrapS() {
        return this.mWrapS;
    }

    public android.renderscript.Sampler.Value getWrapT() {
        return this.mWrapT;
    }

    public float getAnisotropy() {
        return this.mAniso;
    }

    public static android.renderscript.Sampler CLAMP_NEAREST(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_CLAMP_NEAREST == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_CLAMP_NEAREST == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.NEAREST);
                    builder.setMagnification(android.renderscript.Sampler.Value.NEAREST);
                    builder.setWrapS(android.renderscript.Sampler.Value.CLAMP);
                    builder.setWrapT(android.renderscript.Sampler.Value.CLAMP);
                    renderScript.mSampler_CLAMP_NEAREST = builder.create();
                }
            }
        }
        return renderScript.mSampler_CLAMP_NEAREST;
    }

    public static android.renderscript.Sampler CLAMP_LINEAR(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_CLAMP_LINEAR == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_CLAMP_LINEAR == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setMagnification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setWrapS(android.renderscript.Sampler.Value.CLAMP);
                    builder.setWrapT(android.renderscript.Sampler.Value.CLAMP);
                    renderScript.mSampler_CLAMP_LINEAR = builder.create();
                }
            }
        }
        return renderScript.mSampler_CLAMP_LINEAR;
    }

    public static android.renderscript.Sampler CLAMP_LINEAR_MIP_LINEAR(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_CLAMP_LINEAR_MIP_LINEAR == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_CLAMP_LINEAR_MIP_LINEAR == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.LINEAR_MIP_LINEAR);
                    builder.setMagnification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setWrapS(android.renderscript.Sampler.Value.CLAMP);
                    builder.setWrapT(android.renderscript.Sampler.Value.CLAMP);
                    renderScript.mSampler_CLAMP_LINEAR_MIP_LINEAR = builder.create();
                }
            }
        }
        return renderScript.mSampler_CLAMP_LINEAR_MIP_LINEAR;
    }

    public static android.renderscript.Sampler WRAP_NEAREST(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_WRAP_NEAREST == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_WRAP_NEAREST == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.NEAREST);
                    builder.setMagnification(android.renderscript.Sampler.Value.NEAREST);
                    builder.setWrapS(android.renderscript.Sampler.Value.WRAP);
                    builder.setWrapT(android.renderscript.Sampler.Value.WRAP);
                    renderScript.mSampler_WRAP_NEAREST = builder.create();
                }
            }
        }
        return renderScript.mSampler_WRAP_NEAREST;
    }

    public static android.renderscript.Sampler WRAP_LINEAR(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_WRAP_LINEAR == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_WRAP_LINEAR == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setMagnification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setWrapS(android.renderscript.Sampler.Value.WRAP);
                    builder.setWrapT(android.renderscript.Sampler.Value.WRAP);
                    renderScript.mSampler_WRAP_LINEAR = builder.create();
                }
            }
        }
        return renderScript.mSampler_WRAP_LINEAR;
    }

    public static android.renderscript.Sampler WRAP_LINEAR_MIP_LINEAR(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_WRAP_LINEAR_MIP_LINEAR == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_WRAP_LINEAR_MIP_LINEAR == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.LINEAR_MIP_LINEAR);
                    builder.setMagnification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setWrapS(android.renderscript.Sampler.Value.WRAP);
                    builder.setWrapT(android.renderscript.Sampler.Value.WRAP);
                    renderScript.mSampler_WRAP_LINEAR_MIP_LINEAR = builder.create();
                }
            }
        }
        return renderScript.mSampler_WRAP_LINEAR_MIP_LINEAR;
    }

    public static android.renderscript.Sampler MIRRORED_REPEAT_NEAREST(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_MIRRORED_REPEAT_NEAREST == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_MIRRORED_REPEAT_NEAREST == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.NEAREST);
                    builder.setMagnification(android.renderscript.Sampler.Value.NEAREST);
                    builder.setWrapS(android.renderscript.Sampler.Value.MIRRORED_REPEAT);
                    builder.setWrapT(android.renderscript.Sampler.Value.MIRRORED_REPEAT);
                    renderScript.mSampler_MIRRORED_REPEAT_NEAREST = builder.create();
                }
            }
        }
        return renderScript.mSampler_MIRRORED_REPEAT_NEAREST;
    }

    public static android.renderscript.Sampler MIRRORED_REPEAT_LINEAR(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_MIRRORED_REPEAT_LINEAR == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_MIRRORED_REPEAT_LINEAR == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setMagnification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setWrapS(android.renderscript.Sampler.Value.MIRRORED_REPEAT);
                    builder.setWrapT(android.renderscript.Sampler.Value.MIRRORED_REPEAT);
                    renderScript.mSampler_MIRRORED_REPEAT_LINEAR = builder.create();
                }
            }
        }
        return renderScript.mSampler_MIRRORED_REPEAT_LINEAR;
    }

    public static android.renderscript.Sampler MIRRORED_REPEAT_LINEAR_MIP_LINEAR(android.renderscript.RenderScript renderScript) {
        if (renderScript.mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR == null) {
            synchronized (renderScript) {
                if (renderScript.mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR == null) {
                    android.renderscript.Sampler.Builder builder = new android.renderscript.Sampler.Builder(renderScript);
                    builder.setMinification(android.renderscript.Sampler.Value.LINEAR_MIP_LINEAR);
                    builder.setMagnification(android.renderscript.Sampler.Value.LINEAR);
                    builder.setWrapS(android.renderscript.Sampler.Value.MIRRORED_REPEAT);
                    builder.setWrapT(android.renderscript.Sampler.Value.MIRRORED_REPEAT);
                    renderScript.mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR = builder.create();
                }
            }
        }
        return renderScript.mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR;
    }

    public static class Builder {
        android.renderscript.RenderScript mRS;
        android.renderscript.Sampler.Value mMin = android.renderscript.Sampler.Value.NEAREST;
        android.renderscript.Sampler.Value mMag = android.renderscript.Sampler.Value.NEAREST;
        android.renderscript.Sampler.Value mWrapS = android.renderscript.Sampler.Value.WRAP;
        android.renderscript.Sampler.Value mWrapT = android.renderscript.Sampler.Value.WRAP;
        android.renderscript.Sampler.Value mWrapR = android.renderscript.Sampler.Value.WRAP;
        float mAniso = 1.0f;

        public Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public void setMinification(android.renderscript.Sampler.Value value) {
            if (value == android.renderscript.Sampler.Value.NEAREST || value == android.renderscript.Sampler.Value.LINEAR || value == android.renderscript.Sampler.Value.LINEAR_MIP_LINEAR || value == android.renderscript.Sampler.Value.LINEAR_MIP_NEAREST) {
                this.mMin = value;
                return;
            }
            throw new java.lang.IllegalArgumentException("Invalid value");
        }

        public void setMagnification(android.renderscript.Sampler.Value value) {
            if (value == android.renderscript.Sampler.Value.NEAREST || value == android.renderscript.Sampler.Value.LINEAR) {
                this.mMag = value;
                return;
            }
            throw new java.lang.IllegalArgumentException("Invalid value");
        }

        public void setWrapS(android.renderscript.Sampler.Value value) {
            if (value == android.renderscript.Sampler.Value.WRAP || value == android.renderscript.Sampler.Value.CLAMP || value == android.renderscript.Sampler.Value.MIRRORED_REPEAT) {
                this.mWrapS = value;
                return;
            }
            throw new java.lang.IllegalArgumentException("Invalid value");
        }

        public void setWrapT(android.renderscript.Sampler.Value value) {
            if (value == android.renderscript.Sampler.Value.WRAP || value == android.renderscript.Sampler.Value.CLAMP || value == android.renderscript.Sampler.Value.MIRRORED_REPEAT) {
                this.mWrapT = value;
                return;
            }
            throw new java.lang.IllegalArgumentException("Invalid value");
        }

        public void setAnisotropy(float f) {
            if (f >= 0.0f) {
                this.mAniso = f;
                return;
            }
            throw new java.lang.IllegalArgumentException("Invalid value");
        }

        public android.renderscript.Sampler create() {
            this.mRS.validate();
            android.renderscript.Sampler sampler = new android.renderscript.Sampler(this.mRS.nSamplerCreate(this.mMag.mID, this.mMin.mID, this.mWrapS.mID, this.mWrapT.mID, this.mWrapR.mID, this.mAniso), this.mRS);
            sampler.mMin = this.mMin;
            sampler.mMag = this.mMag;
            sampler.mWrapS = this.mWrapS;
            sampler.mWrapT = this.mWrapT;
            sampler.mWrapR = this.mWrapR;
            sampler.mAniso = this.mAniso;
            return sampler;
        }
    }
}
