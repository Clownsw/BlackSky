//
// By: smilex
//

#include "Interface.h"
#include "Tools.h"
#include "Error.hpp"
#include <xorstr/xorstr.hpp>
#include <yyjson/yyjson.h>
#include <string>

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

enum JSON_MUT_TYPE {
    JSON_MUT_OBJ = 0,
    JSON_MUT_ARR = 1,
};

enum JSON_MUT_ARR_ACTION {
    JSON_MUT_ARR_ACTION_INSERT = 0,
    JSON_MUT_ARR_ACTION_APPEND = 1,
    JSON_MUT_ARR_ACTION_PREPEND = 2,
    JSON_MUT_ARR_ACTION_REMOVE = 3,
    JSON_MUT_ARR_ACTION_REPLACE = 4,
    JSON_MUT_ARR_ACTION_REMOVE_FIRST = 5,
    JSON_MUT_ARR_ACTION_REMOVE_LAST = 6,
    JSON_MUT_ARR_ACTION_REMOVE_RANGE = 7,
    JSON_MUT_ARR_ACTION_CLEAN = 8,
};

yyjson_doc *doc = nullptr;
yyjson_val *val = nullptr;

////////////////////////////////////////////
///////////////Json native//////////////////
////////////////////////////////////////////

/*
 * Class:     cn_smilex_blacksky_jni_json_Json
 * Method:    _create
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_cn_smilex_blacksky_jni_json_Json__1create
    (JNIEnv* env, jobject obj, jstring jsonStr) {

    auto _jsonStr = env->GetStringUTFChars(jsonStr, JNI_FALSE);
    yyjson_read_flag flag = YYJSON_READ_ALLOW_TRAILING_COMMAS | YYJSON_READ_ALLOW_COMMENTS;
    doc = yyjson_read(_jsonStr, strlen(_jsonStr), flag);

    val = yyjson_doc_get_root(doc);

    env->DeleteLocalRef(jsonStr);
    env->DeleteLocalRef(obj);

    if (val == nullptr) {
        yyjson_doc_free(doc);
        throwException(env, CLASSNAME_RuntimeException, ERROR_JSON_FORMAT_ERROR);
        return 0;
    }

    return (jlong) doc;
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

    yyjson_val *root;
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

    size_t jsonArrLen = yyjson_arr_size(root);

    /* 判断一下数组长度, 如果长度=0, 就没必要往下走 */
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

    if (address == 0) {
        throwException(env, CLASSNAME_NullPointerException, ERROR_PARAMS_NULL);
        return 0;
    }

    return yyjson_get_type((yyjson_val*) address) ;
}

////////////////////////////////////////////
//////////////JsonMut native////////////////
////////////////////////////////////////////

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _createMut
 * Signature: (ILjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1createMut
    (JNIEnv *env, jobject obj, jint type, jstring str) {

    const char *_str = str != nullptr ? env->GetStringUTFChars(str, JNI_FALSE) : nullptr;

    env->DeleteLocalRef(str);
    env->DeleteLocalRef(obj);

    auto mutDoc = yyjson_mut_doc_new(nullptr);

    if (mutDoc == nullptr) {
        throwException(env, CLASSNAME_NullPointerException, ERROR_APPLY_MEMORY_ERROR);
        return nullptr;
    }

    yyjson_mut_val *data;

    switch (type) {
        case JSON_MUT_OBJ: {
            data = yyjson_mut_obj(mutDoc);
            break;
        }

        case JSON_MUT_ARR: {
            data = yyjson_mut_arr(mutDoc);
            break;
        }

        default: {
            yyjson_read_flag flag = YYJSON_READ_ALLOW_TRAILING_COMMAS | YYJSON_READ_ALLOW_COMMENTS;
            yyjson_doc *_doc = yyjson_read(_str, strlen(_str), flag);

            yyjson_mut_doc_free(mutDoc);

            mutDoc = yyjson_doc_mut_copy(_doc, nullptr);
            data = yyjson_mut_doc_get_root(mutDoc);

            yyjson_doc_free(_doc);
            break;
        }
    }

    if (data == nullptr) {
        throwException(env, CLASSNAME_NullPointerException, ERROR_APPLY_MEMORY_ERROR);
        return nullptr;
    }

    yyjson_mut_doc_set_root(mutDoc, data);

    return env->NewStringUTF(fmt::format("{}+{}", (jlong) mutDoc, (jlong) data).c_str());
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _closeMut
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1closeMut
    (JNIEnv *env, jobject obj, jlong address) {

    env->DeleteLocalRef(obj);

    if (address != 0) {
        yyjson_mut_doc_free((yyjson_mut_doc *) address);
    }
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _writeString
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1writeString
    (JNIEnv *env, jobject obj, jlong address) {

    env->DeleteLocalRef(obj);

    return env->NewStringUTF(reinterpret_cast<const char*>(
            yyjson_mut_write((yyjson_mut_doc *) address, YYJSON_WRITE_PRETTY, nullptr)
            ));
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _add
 * Signature: (JJILjava/lang/String;Z)J
 */
JNIEXPORT jlong JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1add
    (JNIEnv *env, jobject obj, jlong __address, jlong address, jint type, jstring name, jboolean isBind) {

    const char* _name = env->GetStringUTFChars(name, JNI_FALSE);
    auto *_mutDoc = (yyjson_mut_doc *) __address;
    auto *_address = (yyjson_mut_val *) address;

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(name);

    yyjson_mut_val *m_obj = nullptr;

    switch (type) {
        case JSON_MUT_OBJ: {
            m_obj = yyjson_mut_obj(_mutDoc);
            break;
        }

        case JSON_MUT_ARR: {
            m_obj = yyjson_mut_arr(_mutDoc);
            break;
        }
        default: {
        }
    }

    if (m_obj == nullptr) {
        throwException(env, CLASSNAME_NullPointerException, ERROR_APPLY_MEMORY_ERROR);
        return 0;
    }

    if (isBind == JNI_TRUE) {
        yyjson_mut_obj_add(_address, yyjson_mut_str(_mutDoc, _name), m_obj);
    }

    return (jlong) m_obj;
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _objAdd
 * Signature: (IJJLjava/lang/String;Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1objAdd
    (JNIEnv *env, jobject obj, jint type, jlong __address, jlong address, jstring name, jobject data) {

    const char* _name = env->GetStringUTFChars(name, JNI_FALSE);
    jstring dataStr = jObjectTojString(env, data);
    auto *_mutDoc = (yyjson_mut_doc *) __address;
    auto *_address = (yyjson_mut_val *) address;

    switch (type) {
        case JSON_TYPE_STRING: {
            yyjson_mut_obj_add_str(_mutDoc, _address, _name, env->GetStringUTFChars(dataStr, JNI_FALSE));
            break;
        }

        case JSON_TYPE_INTEGER: {
            jint _data = jStringTojInt(env, dataStr);
            yyjson_mut_obj_add_int(_mutDoc, _address, _name, _data);
            break;
        }

        case JSON_TYPE_DOUBLE: {
            double _data = jStringToDouble(env, dataStr);
            yyjson_mut_obj_add_real(_mutDoc, _address, _name, _data);
            break;
        }

        case JSON_TYPE_LONG: {
            jlong _data = jStringTojLong(env, dataStr);
            yyjson_mut_obj_add_uint(_mutDoc, _address, _name, _data);
            break;
        }

        case JSON_TYPE_BOOLEAN: {
            jboolean _data = jStringTojBoolean(env, dataStr);
            yyjson_mut_obj_add_bool(_mutDoc, _address, _name, _data == JNI_TRUE);
            break;
        }

        default: {}
    }

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(data);
    env->DeleteLocalRef(name);
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _arrAdd
 * Signature: (IJLjava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1arrAdd
    (JNIEnv *env, jobject obj, jint type, jlong __address, jlong address, jobject data) {

    auto *_mutDoc = (yyjson_mut_doc *) __address;
    auto *_address = (yyjson_mut_val *) address;
    auto dataStr = jObjectTojString(env, data);

    switch (type) {
        case JSON_TYPE_STRING: {
            yyjson_mut_arr_add_str(_mutDoc, _address, env->GetStringUTFChars(dataStr, JNI_FALSE));
            break;
        }

        case JSON_TYPE_INTEGER: {
            jint _data = jStringTojInt(env, dataStr);
            yyjson_mut_arr_add_int(_mutDoc, _address, _data);
            break;
        }

        case JSON_TYPE_DOUBLE: {
            jdouble _data = jStringToDouble(env, dataStr);
            yyjson_mut_arr_add_real(_mutDoc, _address, _data);
            break;
        }

        case JSON_TYPE_LONG: {
            jlong _data = jStringTojLong(env, dataStr);
            yyjson_mut_arr_add_uint(_mutDoc, _address, _data);
            break;
        }

        case JSON_TYPE_BOOLEAN: {
            jboolean _data = jStringTojBoolean(env, dataStr);
            yyjson_mut_arr_add_bool(_mutDoc, _address, _data == JNI_TRUE);
            break;
        }

        default: {}
    }

    env->DeleteLocalRef(data);
    env->DeleteLocalRef(obj);
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _bind
 * Signature: (JJJLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1bind
    (JNIEnv *env, jobject obj, jlong _address, jlong rootAddress, jlong address, jstring name) {

    env->DeleteLocalRef(obj);

    auto *_mutDoc = (yyjson_mut_doc *) _address;

    if (rootAddress > 0 && address > 0) {
        auto *_rootAddress = (yyjson_mut_val *) rootAddress;
        auto *_address = (yyjson_mut_val *) address;

        if (yyjson_mut_is_arr(_rootAddress)) {
            yyjson_mut_arr_add_val(_rootAddress, _address);
        } else {
            if (name == nullptr || address <= 100) {
                throwException(env, CLASSNAME_NullPointerException, ERROR_PARAMS_NULL);
                return;
            }

            auto _name = env->GetStringUTFChars(name, JNI_FALSE);
            yyjson_mut_obj_add(_rootAddress, yyjson_mut_str(_mutDoc, _name), _address);
        }
    }
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _arrAction
 * Signature: (IJJII)Z
 */
JNIEXPORT jboolean JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1arrAction
    (JNIEnv *env, jobject obj, jint type, jlong arr, jlong data, jint index, jint len) {

    env->DeleteLocalRef(obj);

    if (arr == 0) {
        throwException(env, CLASSNAME_NullPointerException, ERROR_PARAMS_NULL);
    }
    else if (!yyjson_mut_is_arr((yyjson_mut_val *) arr)) {
        throwException(env, CLASSNAME_NullPointerException, ERROR_JSON_NOT_ARR);
    }
    else {
        auto _arr = (yyjson_mut_val *) arr;
        auto _data = (yyjson_mut_val *) data;

        switch (type) {

            case JSON_MUT_ARR_ACTION_INSERT: {
                return data == 0
                       ? JNI_FALSE
                       : yyjson_mut_arr_insert(_arr, _data, index) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_APPEND: {
                return data == 0
                       ? JNI_FALSE
                       : yyjson_mut_arr_append(_arr, _data) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_PREPEND: {
                return data == 0
                        ? JNI_FALSE
                        : yyjson_mut_arr_prepend(_arr, _data) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_REPLACE: {
                return data == 0
                        ? JNI_FALSE
                        : yyjson_mut_arr_replace(_arr, index, _data) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_REMOVE: {
                return yyjson_mut_arr_remove(_arr, index) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_REMOVE_FIRST: {
                return yyjson_mut_arr_remove_first(_arr) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_REMOVE_LAST: {
                return yyjson_mut_arr_remove_last(_arr) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_REMOVE_RANGE: {
                return yyjson_mut_arr_remove_range(_arr, index, len) ? JNI_TRUE : JNI_FALSE;
            }

            case JSON_MUT_ARR_ACTION_CLEAN: {
                return yyjson_mut_arr_clear(_arr) ? JNI_TRUE : JNI_FALSE;
            }
            default: {  }
        }
    }

    return JNI_FALSE;
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _createType
 * Signature: (JILjava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1createType
    (JNIEnv *env, jobject obj, jlong address, jint type, jobject value) {

    env->DeleteLocalRef(obj);

    if (address == 0 || value == nullptr) {
        throwException(env, CLASSNAME_NullPointerException, ERROR_PARAMS_NULL);

        env->DeleteLocalRef(value);

    } else {
        auto _address = (yyjson_mut_doc *) address;
        jstring strValue = jObjectTojString(env, value);

        switch (type) {
            case JSON_TYPE_STRING: {
                const char* _value = env->GetStringUTFChars(strValue, JNI_FALSE);
                env->DeleteLocalRef(value);
                return (jlong) yyjson_mut_str(_address, _value);
            }

            case JSON_TYPE_INTEGER: {
                jint _value = jStringTojInt(env, strValue);
                env->DeleteLocalRef(value);
                return (jlong) yyjson_mut_int(_address, _value);
            }

            case JSON_TYPE_DOUBLE: {
                jdouble _value = jStringToDouble(env, strValue);
                env->DeleteLocalRef(value);
                return (jlong) yyjson_mut_real(_address, _value);
            }

            case JSON_TYPE_LONG: {
                jlong _value = jStringTojLong(env, strValue);
                env->DeleteLocalRef(value);
                return (jlong) yyjson_mut_uint(_address, _value);
            }

            case JSON_TYPE_BOOLEAN: {
                jboolean _value = jStringTojBoolean(env, strValue);
                env->DeleteLocalRef(value);
                return (jlong) yyjson_mut_bool(_address, _value == JNI_TRUE);
            }

            default: {
                env->DeleteLocalRef(strValue);
                return 0;
            }
        }
    }
}

/*
 * Class:     cn_smilex_blacksky_jni_json_JsonMut
 * Method:    _getType
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_cn_smilex_blacksky_jni_json_JsonMut__1getType
    (JNIEnv *env, jobject obj, jlong address) {

    env->DeleteLocalRef(obj);

    return yyjson_mut_get_type((yyjson_mut_val *) address);
}