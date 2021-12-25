//
// By: smilex
//

#include <jni.h>

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
    (JNIEnv* env, jobject, jobject request);

/*
 * Class:     cn_smilex_libhv_jni_ssl_MD5
 * Method:    getMD5
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_MD5_getMD5
    (JNIEnv* env, jobject, jstring data, jint len);

/*
 * Class:     cn_smilex_libhv_jni_ssl_Base64
 * Method:    base64_encode
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Base64_base64_1encode
    (JNIEnv* env, jobject, jstring data, jint len);

/*
 * Class:     cn_smilex_libhv_jni_ssl_Base64
 * Method:    base64_decode
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Base64_base64_1decode
    (JNIEnv* env, jobject, jstring data, jint len);

#ifdef __cplusplus
}
#endif
#endif