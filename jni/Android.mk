LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := android-ffmpeg-library
LOCAL_SRC_FILES += android-ffmpeg-library.cpp 
LOCAL_LDLIBS    := -lm -llog -ljnigraphics
LOCAL_C_INCLUDES := $(LOCAL_PATH)/include
 
include $(BUILD_SHARED_LIBRARY)

