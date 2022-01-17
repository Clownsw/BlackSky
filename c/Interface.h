//
// By: smilex
//

#include <jni/jni.h>

#ifndef _Included_cn_smilex_libhv_jni_Requests
#define _Included_cn_smilex_libhv_jni_Requests
#ifdef __cplusplus
extern "C" {
#endif

/*
 * 通过HttpRequest类请求指定网站并返回HttpResponse
 * Class:     cn_smilex_libhv_jni_http_Requests
 * Method:    request
 * Signature: (Lcn/smilex/libhv/jni/http/HttpRequest;)Ljava/lang/String;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_libhv_jni_http_Requests_request
    (JNIEnv* env, jobject obj, jobject request);

/*
 * Class:     cn_smilex_libhv_jni_http_Requests
 * Method:    asyncRequest
 * Signature: (Lcn/smilex/libhv/jni/http/HttpRequest;)Lcn/smilex/libhv/jni/http/HttpResponse;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_libhv_jni_http_Requests_asyncRequest
    (JNIEnv* env, jobject obj, jobject request);

/*
 * Class:     cn_smilex_libhv_jni_ssl_MD5
 * Method:    getMD5
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_MD5_getMD5
    (JNIEnv* env, jobject obj, jstring data, jint len);

/*
 * Class:     cn_smilex_libhv_jni_ssl_Base64
 * Method:    base64_encode
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Base64_base64_1encode
    (JNIEnv* env, jobject obj, jstring data, jint len);

/*
 * Class:     cn_smilex_libhv_jni_ssl_Base64
 * Method:    base64_decode
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Base64_base64_1decode
    (JNIEnv* env, jobject obj, jstring data, jint len);

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    log
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_log
    (JNIEnv* env, jobject obj, jint loglevel, jstring message);

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    flush
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_flush
    (JNIEnv* env, jobject obj);

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    createFileLogger
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_createFileLogger
    (JNIEnv* env, jobject obj, jstring loggerName, jstring fileName);

/*
 * Class:     cn_smilex_libhv_jni_ssl_Sha
 * Method:    sha
 * Signature: (ILjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Sha_sha
    (JNIEnv* env, jobject obj, jint type, jstring data);

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    set_pattern
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_set_1pattern
    (JNIEnv* env, jobject obj, jboolean isFileLogger, jstring pattern, jint pattern_type);

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    set_level
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_set_1level
    (JNIEnv* env, jobject obj, jboolean isFileLogger, jint level);

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    flush_on
 * Signature: (ZI)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_flush_1on
    (JNIEnv* env, jobject obj, jboolean isFileLogger, jint level);

/*
 * Class:     cn_smilex_libhv_jni_json_Json
 * Method:    _create
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jlong JNICALL Java_cn_smilex_libhv_jni_json_Json__1create
    (JNIEnv* env, jobject obj, jstring jsonStr);

/*
 * Class:     cn_smilex_libhv_jni_json_Json
 * Method:    _get
 * Signature: (ILjava/lang/String;IJ)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_cn_smilex_libhv_jni_json_Json__1get
    (JNIEnv* env, jobject obj, jint type, jstring name, jint isRoot, jlong address);

/*
 * Class:     cn_smilex_libhv_jni_json_Json
 * Method:    _getArray
 * Signature: (Ljava/lang/String;ZJ)[J
 */
JNIEXPORT jlongArray JNICALL Java_cn_smilex_libhv_jni_json_Json__1getArray
    (JNIEnv* env, jobject obj, jstring name, jboolean isRoot, jlong address);

/*
 * Class:     cn_smilex_libhv_jni_json_Json
 * Method:    _close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_json_Json__1close
    (JNIEnv* env, jobject obj, jlong address);

#ifdef __cplusplus
}
#endif
#endif
