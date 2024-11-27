package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class AllocationAdapter extends android.renderscript.Allocation {
    android.renderscript.Type mWindow;

    AllocationAdapter(long j, android.renderscript.RenderScript renderScript, android.renderscript.Allocation allocation, android.renderscript.Type type) {
        super(j, renderScript, allocation.mType, allocation.mUsage);
        this.mAdaptedAllocation = allocation;
        this.mWindow = type;
    }

    void initLOD(int i) {
        if (i < 0) {
            throw new android.renderscript.RSIllegalArgumentException("Attempting to set negative lod (" + i + ").");
        }
        int x = this.mAdaptedAllocation.mType.getX();
        int y = this.mAdaptedAllocation.mType.getY();
        int z = this.mAdaptedAllocation.mType.getZ();
        for (int i2 = 0; i2 < i; i2++) {
            if (x == 1 && y == 1 && z == 1) {
                throw new android.renderscript.RSIllegalArgumentException("Attempting to set lod (" + i + ") out of range.");
            }
            if (x > 1) {
                x >>= 1;
            }
            if (y > 1) {
                y >>= 1;
            }
            if (z > 1) {
                z >>= 1;
            }
        }
        this.mCurrentDimX = x;
        this.mCurrentDimY = y;
        this.mCurrentDimZ = z;
        this.mCurrentCount = this.mCurrentDimX;
        if (this.mCurrentDimY > 1) {
            this.mCurrentCount *= this.mCurrentDimY;
        }
        if (this.mCurrentDimZ > 1) {
            this.mCurrentCount *= this.mCurrentDimZ;
        }
        this.mSelectedY = 0;
        this.mSelectedZ = 0;
    }

    private void updateOffsets() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (this.mSelectedArray == null) {
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        } else {
            if (this.mSelectedArray.length <= 0) {
                i5 = 0;
            } else {
                i5 = this.mSelectedArray[0];
            }
            if (this.mSelectedArray.length <= 1) {
                i6 = 0;
            } else {
                i6 = this.mSelectedArray[2];
            }
            if (this.mSelectedArray.length <= 2) {
                i7 = 0;
            } else {
                i7 = this.mSelectedArray[2];
            }
            if (this.mSelectedArray.length <= 3) {
                i = i5;
                i4 = 0;
                i2 = i6;
                i3 = i7;
            } else {
                i = i5;
                i4 = this.mSelectedArray[3];
                i2 = i6;
                i3 = i7;
            }
        }
        this.mRS.nAllocationAdapterOffset(getID(this.mRS), this.mSelectedX, this.mSelectedY, this.mSelectedZ, this.mSelectedLOD, this.mSelectedFace.mID, i, i2, i3, i4);
    }

    public void setLOD(int i) {
        if (!this.mAdaptedAllocation.getType().hasMipmaps()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set LOD when the allocation type does not include mipmaps.");
        }
        if (this.mWindow.hasMipmaps()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set LOD when the adapter includes mipmaps.");
        }
        initLOD(i);
        this.mSelectedLOD = i;
        updateOffsets();
    }

    public void setFace(android.renderscript.Type.CubemapFace cubemapFace) {
        if (!this.mAdaptedAllocation.getType().hasFaces()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set Face when the allocation type does not include faces.");
        }
        if (this.mWindow.hasFaces()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set face when the adapter includes faces.");
        }
        if (cubemapFace == null) {
            throw new android.renderscript.RSIllegalArgumentException("Cannot set null face.");
        }
        this.mSelectedFace = cubemapFace;
        updateOffsets();
    }

    public void setX(int i) {
        if (this.mAdaptedAllocation.getType().getX() <= i) {
            throw new android.renderscript.RSInvalidStateException("Cannot set X greater than dimension of allocation.");
        }
        if (this.mWindow.getX() == this.mAdaptedAllocation.getType().getX()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set X when the adapter includes X.");
        }
        if (this.mWindow.getX() + i >= this.mAdaptedAllocation.getType().getX()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set (X + window) which would be larger than dimension of allocation.");
        }
        this.mSelectedX = i;
        updateOffsets();
    }

    public void setY(int i) {
        if (this.mAdaptedAllocation.getType().getY() == 0) {
            throw new android.renderscript.RSInvalidStateException("Cannot set Y when the allocation type does not include Y dim.");
        }
        if (this.mAdaptedAllocation.getType().getY() <= i) {
            throw new android.renderscript.RSInvalidStateException("Cannot set Y greater than dimension of allocation.");
        }
        if (this.mWindow.getY() == this.mAdaptedAllocation.getType().getY()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set Y when the adapter includes Y.");
        }
        if (this.mWindow.getY() + i >= this.mAdaptedAllocation.getType().getY()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set (Y + window) which would be larger than dimension of allocation.");
        }
        this.mSelectedY = i;
        updateOffsets();
    }

    public void setZ(int i) {
        if (this.mAdaptedAllocation.getType().getZ() == 0) {
            throw new android.renderscript.RSInvalidStateException("Cannot set Z when the allocation type does not include Z dim.");
        }
        if (this.mAdaptedAllocation.getType().getZ() <= i) {
            throw new android.renderscript.RSInvalidStateException("Cannot set Z greater than dimension of allocation.");
        }
        if (this.mWindow.getZ() == this.mAdaptedAllocation.getType().getZ()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set Z when the adapter includes Z.");
        }
        if (this.mWindow.getZ() + i >= this.mAdaptedAllocation.getType().getZ()) {
            throw new android.renderscript.RSInvalidStateException("Cannot set (Z + window) which would be larger than dimension of allocation.");
        }
        this.mSelectedZ = i;
        updateOffsets();
    }

    public void setArray(int i, int i2) {
        if (this.mAdaptedAllocation.getType().getArray(i) == 0) {
            throw new android.renderscript.RSInvalidStateException("Cannot set arrayNum when the allocation type does not include arrayNum dim.");
        }
        if (this.mAdaptedAllocation.getType().getArray(i) <= i2) {
            throw new android.renderscript.RSInvalidStateException("Cannot set arrayNum greater than dimension of allocation.");
        }
        if (this.mWindow.getArray(i) == this.mAdaptedAllocation.getType().getArray(i)) {
            throw new android.renderscript.RSInvalidStateException("Cannot set arrayNum when the adapter includes arrayNum.");
        }
        if (this.mWindow.getArray(i) + i2 >= this.mAdaptedAllocation.getType().getArray(i)) {
            throw new android.renderscript.RSInvalidStateException("Cannot set (arrayNum + window) which would be larger than dimension of allocation.");
        }
        this.mSelectedArray[i] = i2;
        updateOffsets();
    }

    public static android.renderscript.AllocationAdapter create1D(android.renderscript.RenderScript renderScript, android.renderscript.Allocation allocation) {
        renderScript.validate();
        return createTyped(renderScript, allocation, android.renderscript.Type.createX(renderScript, allocation.getElement(), allocation.getType().getX()));
    }

    public static android.renderscript.AllocationAdapter create2D(android.renderscript.RenderScript renderScript, android.renderscript.Allocation allocation) {
        renderScript.validate();
        return createTyped(renderScript, allocation, android.renderscript.Type.createXY(renderScript, allocation.getElement(), allocation.getType().getX(), allocation.getType().getY()));
    }

    public static android.renderscript.AllocationAdapter createTyped(android.renderscript.RenderScript renderScript, android.renderscript.Allocation allocation, android.renderscript.Type type) {
        renderScript.validate();
        if (allocation.mAdaptedAllocation != null) {
            throw new android.renderscript.RSInvalidStateException("Adapters cannot be nested.");
        }
        if (!allocation.getType().getElement().equals(type.getElement())) {
            throw new android.renderscript.RSInvalidStateException("Element must match Allocation type.");
        }
        if (type.hasFaces() || type.hasMipmaps()) {
            throw new android.renderscript.RSInvalidStateException("Adapters do not support window types with Mipmaps or Faces.");
        }
        android.renderscript.Type type2 = allocation.getType();
        if (type.getX() > type2.getX() || type.getY() > type2.getY() || type.getZ() > type2.getZ() || type.getArrayCount() > type2.getArrayCount()) {
            throw new android.renderscript.RSInvalidStateException("Type cannot have dimension larger than the source allocation.");
        }
        if (type.getArrayCount() > 0) {
            for (int i = 0; i < type.getArray(i); i++) {
                if (type.getArray(i) > type2.getArray(i)) {
                    throw new android.renderscript.RSInvalidStateException("Type cannot have dimension larger than the source allocation.");
                }
            }
        }
        long nAllocationAdapterCreate = renderScript.nAllocationAdapterCreate(allocation.getID(renderScript), type.getID(renderScript));
        if (nAllocationAdapterCreate == 0) {
            throw new android.renderscript.RSRuntimeException("AllocationAdapter creation failed.");
        }
        return new android.renderscript.AllocationAdapter(nAllocationAdapterCreate, renderScript, allocation, type);
    }

    @Override // android.renderscript.Allocation
    public synchronized void resize(int i) {
        throw new android.renderscript.RSInvalidStateException("Resize not allowed for Adapters.");
    }
}
