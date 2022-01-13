//
// By: smilex
//

#include "Interface.h"
#include <yyjson/yyjson.h>
#include <string>

yyjson_doc* doc = nullptr;
yyjson_val* val = nullptr;

enum JSON_TYPE {
    JSON_TYPE_STRING = 0,
    JSON_TYPE_INTEGER = 1,
    JSON_TYPE_DOUBLE = 2,
};

/*
 * Class:     cn_smilex_libhv_jni_json_Json
 * Method:    _create
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_cn_smilex_libhv_jni_json_Json__1create
    (JNIEnv* env, jobject obj, jstring jsonStr) {

    const char* _jsonStr = env->GetStringUTFChars(jsonStr, JNI_FALSE);
    doc = yyjson_read(_jsonStr, strlen(_jsonStr), 0);

    val = yyjson_doc_get_root(doc);

    env->DeleteLocalRef(jsonStr);
    env->DeleteLocalRef(obj);

    return (jlong)doc;
}

/*
 * Class:     cn_smilex_libhv_jni_json_Json
 * Method:    _get
 * Signature: (ILjava/lang/String;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_libhv_jni_json_Json__1get
    (JNIEnv* env, jobject obj, jint type, jstring name) {

    const char* _name = env->GetStringUTFChars(name, JNI_FALSE);

    env->DeleteLocalRef(name);
    env->DeleteLocalRef(obj);

    yyjson_val* jsonVal = yyjson_obj_get(val, _name);

    switch (type) {
        case JSON_TYPE_STRING: {
            return env->NewStringUTF(yyjson_get_str(jsonVal));
        }

        case JSON_TYPE_INTEGER: {
            return env->NewStringUTF(std::to_string(yyjson_get_int(jsonVal)).c_str());
        }

        case JSON_TYPE_DOUBLE: {
            return env->NewStringUTF(std::to_string(yyjson_get_real(jsonVal)).c_str());
        }

        default: {
            return nullptr;
        }
    }
}

/*
 * Class:     cn_smilex_libhv_jni_json_Json
 * Method:    _close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_json_Json__1close
    (JNIEnv* env, jobject obj, jlong address) {

    yyjson_doc_free((yyjson_doc*)address);

    val = nullptr;

    env->DeleteLocalRef(obj);
}