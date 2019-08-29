package cn.endureblaze.kirby.nocropper;

import cn.endureblaze.kirby.nocropper.CropInfo;
import cn.endureblaze.kirby.nocropper.CropState;

public class CropResult {

    private final CropInfo cropInfo;
    private final  CropState state;

    private CropResult(CropInfo info,  CropState state) {
        this.cropInfo = info;
        this.state = state;
    }

    public CropInfo getCropInfo() {
        return cropInfo;
    }

    public CropState getState() {
        return state;
    }

    static CropResult GestureFailure() {
        return new CropResult(null, CropState.FAILURE_GESTURE_IN_PROCESS);
    }

    static CropResult success(CropInfo info) {
        return new CropResult(info,  CropState.SUCCESS);
    }

    public static CropResult error() {
        return new CropResult(null,  CropState.ERROR);
    }

}
