//
// By: smilex
//

#include "Interface.h"
#include "Tools.h"
#include "Error.hpp"
#include <xorstr/xorstr.hpp>
#include <yyjson/yyjson.h>
#include <string>

yyjson_doc* doc = nullptr;
yyjson_val* val = nullptr;

enum JSON_TYPE {
    JSON_TYPE_STRING = 0,
    JSON_TYPE_INTEGER = 1,
    JSON_TYPE_DOUBLE = 2,
    JSON_TYPE_LONG = 3,
    JSON_TYPE_BOOLEAN = 4,
    JSON_TYPE_OBJECT = 5,
};

enum JSON_GET_METHOD {
    JSON_GET_METHOD_DEFAULT = 0,
    JSON_GET_METHOD_NAME_ADDRESS = 1,
    JSON_GET_METHOD_ADDRESS = 2,
};

/*
 * Class:     cn_smilex_blacksky_jni_json_Json
 * Method:    _create
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_cn_smilex_blacksky_jni_json_Json__1create
    (JNIEnv* env, jobject obj, jstring jsonStr) {

    const char* _jsonStr = env->GetStringUTFChars(jsonStr, JNI_FALSE);
    yyjson_read_flag flag = YYJSON_READ_ALLOW_TRAILING_COMMAS | YYJSON_READ_ALLOW_COMMENTS;
    doc = yyjson_read(_jsonStr, strlen(_jsonStr), flag);

    val = yyjson_doc_get_root(doc);

    env->DeleteLocalRef(jsonStr);
    env->DeleteLocalRef(obj);

    if (val == nullptr) {
        yyjson_doc_free(doc);
        throwException(env, CLASSNAME_RuntimeException, "JSON格式有误!");
        return 0;
    }

    return (jlong)doc;
}

/*
 * Class:     cn_smilex_blacksky_jni_json_Json
 * Method:    _get
 * Signature: (ILjava/lang/String;IJ)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_blacksky_jni_json_Json__1get
    (JNIEnv* env, jobject obj, jint type, jstring name, jint isRoot, jlong address) {

    const char* _name = name != nullptr ? env->GetStringUTFChars(name, JNI_FALSE) : nullptr;

    env->DeleteLocalRef(name);
    env->DeleteLocalRef(obj);

    yyjson_val *root =
            isRoot == JSON_GET_METHOD_DEFAULT ? yyjson_obj_get(val, _name)
            : isRoot == JSON_GET_METHOD_NAME_ADDRESS ? yyjson_obj_get((yyjson_val*) address, _name)
            : (yyjson_val *) address;

    if (root == nullptr) {
        return nullptr;
    }

    switch (type) {
        case JSON_TYPE_STRING: {
            return env->NewStringUTF(yyjson_get_str(root));
        }

        case JSON_TYPE_INTEGER: {
            return env->NewStringUTF(std::to_string(yyjson_get_int(root)).c_str());
        }

        case JSON_TYPE_DOUBLE: {
            return env->NewStringUTF(std::to_string(yyjson_get_real(root)).c_str());
        }

        case JSON_TYPE_LONG: {
            return env->NewStringUTF(std::to_string(yyjson_get_sint(root)).c_str());
        }

        case JSON_TYPE_BOOLEAN: {
            return env->NewStringUTF(yyjson_get_bool(root) ? "true" : "false");
        }

        case JSON_TYPE_OBJECT: {
            return env->NewStringUTF(std::to_string((jlong)root).c_str());
        }

        default: {
            return nullptr;
        }
    }
}

/*
 * Class:     cn_smilex_blacksky_jni_json_Json
 * Method:    _getArray
 * Signature: (Ljava/lang/String;ZJ)[J
 */
JNIEXPORT jlongArray JNICALL Java_cn_smilex_blacksky_jni_json_Json__1getArray
    (JNIEnv* env, jobject obj, jstring name, jboolean isRoot, jlong address) {

    const char* _name = nullptr;

    if (name != nullptr) {
        _name = env->GetStringUTFChars(name, JNI_FALSE);
    }

    env->DeleteLocalRef(name);
    env->DeleteLocalRef(obj);

//    yyjson_val* root = (isRoot == JNI_FALSE ? yyjson_obj_get((yyjson_val*) address, _name) : yyjson_obj_get(val, _name));

    yyjson_val *root = nullptr;
    if (name != nullptr && strcmp(_name, xorstr_("__acJjzPifrs__")) == 0) {
        root = (yyjson_val*) address;
    } else {
        root = _name == nullptr
                           ? yyjson_doc_get_root((yyjson_doc*) address)
                           : isRoot == JNI_FALSE
                             ? yyjson_obj_get((yyjson_val*) address, _name)
                             : yyjson_obj_get(val, _name);
    }

    if (root == nullptr) {
        return nullptr;
    }

    /* 判断是否是一个JSON数组 */
    if (!yyjson_is_arr(root)) {
        throwException(env, CLASSNAME_RuntimeException, ERROR_JSON_NOT_ARR);
        return nullptr;
    }

    jlongArray array;

    /* 判断一下数组长度, 如果长度=0, 就没必要往下走 */
    size_t jsonArrLen = yyjson_arr_size(root);

    if (jsonArrLen > 0) {
        array = env->NewLongArray(jsonArrLen);

        if (array == nullptr) {
            return nullptr;
        }

        yyjson_val *_val;
        yyjson_arr_iter iter;
        yyjson_arr_iter_init(root, &iter);

        int i = 0;
        jlong address[jsonArrLen];

        while ((_val = yyjson_arr_iter_next(&iter))) {
            address[i] = (jlong)_val;
            i++;
        }
        env->SetLongArrayRegion(array, 0, jsonArrLen, address);
    } else {
        env->NewLongArray(0);
    }

    return array;
}

/*
 * Class:     cn_smilex_blacksky_jni_json_Json
 * Method:    _getPoint
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_cn_smilex_blacksky_jni_json_Json__1getPoint
    (JNIEnv* env, jobject obj, jlong address, jstring point) {

    const char* _point = env->GetStringUTFChars(point, JNI_FALSE);

    env->DeleteLocalRef(point);
    env->DeleteLocalRef(obj);

    return address == 0
    ? (jlong) yyjson_get_pointer(val, _point)
    : (jlong) (yyjson_get_pointer((yyjson_val*) address, _point));
}

/*
 * Class:     cn_smilex_blacksky_jni_json_Json
 * Method:    _close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_blacksky_jni_json_Json__1close
    (JNIEnv* env, jobject obj, jlong address) {

    yyjson_doc_free((yyjson_doc*)address);

    val = nullptr;

    env->DeleteLocalRef(obj);
}

/*
 * Class:     cn_smilex_blacksky_jni_json_Json
 * Method:    _getType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_cn_smilex_blacksky_jni_json_Json__1getType
    (JNIEnv* env, jobject obj, jlong address) {

    env->DeleteLocalRef(obj);

    return yyjson_get_type((yyjson_val*) address);
}