#include "Requests.h"
#include "tools.h"
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
	auto resp = requests::get(env->GetStringUTFChars(url, JNI_FALSE));
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
	auto resp = requests::post(env->GetStringUTFChars(url, JNI_FALSE));
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

	requests::Request req(new HttpRequest);

	jclass httpRequestClass = env->GetObjectClass(request);
	jfieldID field_Method = env->GetFieldID(httpRequestClass, "method", "I");
	int method = env->GetIntField(request, field_Method);

	jfieldID fieldUrl = env->GetFieldID(httpRequestClass, "url", "Ljava/lang/String;");
	jstring stringUrl = (jstring) env->GetObjectField(request, fieldUrl);

	jfieldID fieldHeaders = env->GetFieldID(httpRequestClass, "headers", "Ljava/util/HashMap;");
	jfieldID fieldParmams = env->GetFieldID(httpRequestClass, "params", "Ljava/util/HashMap;");
	jfieldID fieldCookie = env->GetFieldID(httpRequestClass, "cookie", "Ljava/lang/String;");

	jobject objectHeaders = env->GetObjectField(request, fieldHeaders);
	jobject objectParams = env->GetObjectField(request, fieldParmams);
	jobject objectCookie = env->GetObjectField(request, fieldCookie);

	req->SetMethod(method == 1 ? "GET" : "POST");
	req->SetUrl(env->GetStringUTFChars(stringUrl, JNI_FALSE));

	if (objectHeaders != NULL) {
		std::map<std::string, std::string> m_headers = parseMap(env, objectHeaders);
		for (auto& item : m_headers) {
			req->headers[item.first] = item.second;
		}
	}

	if (objectParams != NULL) {
		std::map<std::string, std::string> m_params = parseMap(env, objectParams);
		for (auto& item : m_params) {
			req->SetParam(item.first.c_str(), item.second.c_str());
		}
	}

	if (objectCookie != NULL) {
		// std::cout << env->GetStringUTFChars((jstring)objectCookie, false) << std::endl;
		req->headers["Cookie"] = env->GetStringUTFChars((jstring)objectCookie, JNI_FALSE);
	}

	auto resp = requests::request(req);

	env->DeleteLocalRef(objectCookie);
	env->DeleteLocalRef(objectParams);
	env->DeleteLocalRef(objectHeaders);
	env->DeleteLocalRef(stringUrl);
	env->DeleteLocalRef(httpRequestClass);

	return resp != nullptr ? env->NewStringUTF(resp->body.c_str()) : nullptr;
}