#include "Requests.h"
#include <hv/requests.h>

/*
 * 以Get方式请求网站并返回结果
 * Class:     cn_smilex_libhv_jni_Requests
 * Method:    get
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 * Author:	  Smilex
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_Requests_get
(JNIEnv* env, jobject, jstring url) {
	auto resp = requests::get(env->GetStringUTFChars(url, false));
	return resp != nullptr ? env->NewStringUTF(resp->body.c_str()) : nullptr;
}

/*
 * 以Post方式请求指定网站并返回结果
 * Class:     cn_smilex_libhv_jni_Requests
 * Method:    post
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 * Author:	  Smilex
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_Requests_post
(JNIEnv* env, jobject, jstring url) {
	auto resp = requests::post(env->GetStringUTFChars(url, false));
	return resp != nullptr ? env->NewStringUTF(resp->body.c_str()) : nullptr;
}

/*
 * 通过HttpRequest类请求指定网站并返回结果
 * Class:     cn_smilex_libhv_jni_Requests
 * Method:    request
 * Signature: (Lcn/smilex/libhv/jni/HttpRequest;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_Requests_request 
(JNIEnv* env, jobject, jobject request) {
	jstring NULL_JSTRING = env->NewStringUTF("");

	jclass httpRequest_Class = env->GetObjectClass(request);
	jfieldID field_Method = env->GetFieldID(httpRequest_Class, "method", "I");
	int method = env->GetIntField(request, field_Method);

	jfieldID field_Url = env->GetFieldID(httpRequest_Class, "url", "Ljava/lang/String;");

	if (field_Url == NULL) {
		return NULL_JSTRING;
	}

	jstring url = (jstring) env->GetObjectField(request, field_Url);

	requests::Request req(new HttpRequest);

	req->SetMethod(method == 1 ? "GET" : "POST");
	req->SetUrl(env->GetStringUTFChars(url, false));

	auto resp = requests::request(req);

	return resp != nullptr ? env->NewStringUTF(resp->body.c_str()) : nullptr;
}

void test() {
	requests::Request req(new HttpRequest);
	
}