//
// Created by Administrator on 2021/12/22.
//

#include "Interface.h"
#include "Tools.h"

JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_fmt_Fmt_format
(JNIEnv* env, jobject, jstring first, jobjectArray args) {

    jsize size = env->GetArrayLength(args);

    print(fmt::color::red, "first={}\n", env->GetStringUTFChars(first, JNI_FALSE));

    for (jint i = 0; i < size; i++) {
        jobject obj = env->GetObjectArrayElement(args, i);
        fmt::print("{}\n", env->GetStringUTFChars((jstring)obj, JNI_FALSE));
        env->DeleteLocalRef(obj);
    }

    return env->NewStringUTF("test");
}