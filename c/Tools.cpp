//
// By: smilex
//

#include "Tools.h"

std::map<absl::string_view, absl::string_view> parseMap(JNIEnv* env, jobject &obj) {

    std::map<absl::string_view, absl::string_view> m;

    // 获取到HashMap类的class
	jclass hashMapClass = env->FindClass(CLASSNAME_HashMap);
	// 获取到HashMap中的entrySet()方法ID
	jmethodID methodEntrySet = env->GetMethodID(hashMapClass, "entrySet", "()Ljava/util/Set;");

	jobject setObj = env->CallObjectMethod(obj, methodEntrySet);

    jclass classSet = env->FindClass(CLASSNAME_Set);
	jmethodID methodIterator = env->GetMethodID(classSet, "iterator", "()Ljava/util/Iterator;");

	// 通过调用iterator()获取到iterator对象
	jobject objectIterator = env->CallObjectMethod(setObj, methodIterator);

	// 获取Iterator类
    jclass classIterator = env->FindClass(CLASSNAME_Iterator);

	// 获取Iterator类中的hasNext()方法ID
	jmethodID methodHasNext = env->GetMethodID(classIterator, "hasNext", "()Z");
	// 获取Iterator类中的next()方法ID
	jmethodID methodNext = env->GetMethodID(classIterator, "next", "()Ljava/lang/Object;");

	// 获取Entry类
	jclass classMapEntry = env->FindClass(CLASSNAME_Map$Entry);

	// 获取Entry类内的getKey方法ID
	jmethodID methodGetKey = env->GetMethodID(classMapEntry, "getKey", "()Ljava/lang/Object;");
	// 获取Entry类内的getValue方法ID
	jmethodID methodGetValue = env->GetMethodID(classMapEntry, "getValue", "()Ljava/lang/Object;");

	while (env->CallBooleanMethod(objectIterator, methodHasNext)) {
		jobject objectEntry = env->CallObjectMethod(objectIterator, methodNext);
		jstring key = (jstring)env->CallObjectMethod(objectEntry, methodGetKey);

		jstring value = (jstring)env->CallObjectMethod(objectEntry, methodGetValue);

        m.insert(std::make_pair<std::string, std::string>(env->GetStringUTFChars(key, JNI_FALSE), 
                env->GetStringUTFChars(value, JNI_FALSE)));

		env->DeleteLocalRef(value);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(objectEntry);
	}

	env->DeleteLocalRef(classMapEntry);
	env->DeleteLocalRef(classIterator);
	env->DeleteLocalRef(classSet);
	env->DeleteLocalRef(objectIterator);
	env->DeleteLocalRef(setObj);
    return m;
}

static JavaHttpRequest* _jobjectToRequest(JNIEnv* &env, jobject &obj) {
    JavaHttpRequest* httpRequest = new JavaHttpRequest;

    jclass httpRequestClass = env->FindClass(CLASSNAME_HttpRequest);

    jfieldID field_Method = env->GetFieldID(httpRequestClass, "method", "I");

    int method = env->GetIntField(obj, field_Method);

    jfieldID fieldUrl = env->GetFieldID(httpRequestClass, "url", CLASSNAME_String);
    jstring stringUrl = (jstring) env->GetObjectField(obj, fieldUrl);

    if (stringUrl == nullptr) {
        delete httpRequest;
        return nullptr;
    }

    // 获取到URL
    httpRequest->url = env->GetStringUTFChars(stringUrl, JNI_FALSE);

    // 获取到method
    httpRequest->method = getMethodName(method);

    jfieldID fieldHeaders = env->GetFieldID(httpRequestClass, "headers", CLASSNAME_HashMap);
    jfieldID fieldParams = env->GetFieldID(httpRequestClass, "params", CLASSNAME_HashMap);
    jfieldID fieldCookie = env->GetFieldID(httpRequestClass, "cookie", CLASSNAME_String);
    jfieldID fieldBody = env->GetFieldID(httpRequestClass, "body", CLASSNAME_String);

    jobject objectHeaders = env->GetObjectField(obj, fieldHeaders);
    jobject objectParams = env->GetObjectField(obj, fieldParams);
    jobject objectCookie = env->GetObjectField(obj, fieldCookie);
    jobject objectBody = env->GetObjectField(obj, fieldBody);

    // 设置请求body
    if (objectBody != nullptr) {
        httpRequest->body = env->GetStringUTFChars((jstring)objectBody, JNI_FALSE);
    }

    // 设置请求头
    httpRequest->headers = parseMap(env, objectHeaders);

    // 设置请求参数
    httpRequest->params = parseMap(env, objectParams);

    // 设置请求cookie
    if (objectCookie != nullptr) {
        httpRequest->cookie = env->GetStringUTFChars((jstring)objectCookie, JNI_FALSE);
    }

    env->DeleteLocalRef(objectBody);
    env->DeleteLocalRef(objectCookie);
    env->DeleteLocalRef(objectParams);
    env->DeleteLocalRef(objectHeaders);
    env->DeleteLocalRef(stringUrl);
    env->DeleteLocalRef(httpRequestClass);

    return httpRequest;
}

JavaHttpRequest* jobjectToRequest(JNIEnv* &env, jobject &obj) { return _jobjectToRequest(env, obj); }

static jobject _ResponseToHttpResponse(JNIEnv* &env, requests::Response& resp) {
    jclass classHttpResponse = env->FindClass(CLASSNAME_HttpResponse);
    jclass classHashMap = env->FindClass(CLASSNAME_HashMap);

    jmethodID methodHttpResponseDefaultCon = env->GetMethodID(classHttpResponse, "<init>", "()V");
    jmethodID methodPut = env->GetMethodID(classHashMap,
                                           "put",
                                           "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");

    jfieldID fieldResponseBody = env->GetFieldID(classHttpResponse, "body", CLASSNAME_String);
    jfieldID fieldStatusCode = env->GetFieldID(classHttpResponse, "statusCode", "I");
    jfieldID fieldResponseCookies = env->GetFieldID(classHttpResponse, "cookies", CLASSNAME_HashMap);
    jfieldID fieldResponseHeaders = env->GetFieldID(classHttpResponse, "headers", CLASSNAME_HashMap);
    jfieldID fieldContentType = env->GetFieldID(classHttpResponse, "contentType", CLASSNAME_String);
    jfieldID fieldContentLength = env->GetFieldID(classHttpResponse, "contentLength", "J");

    jobject objectHttpResponse = env->NewObject(classHttpResponse, methodHttpResponseDefaultCon);

    jobject tmpCookies = env->GetObjectField(objectHttpResponse, fieldResponseCookies);
    jobject tmpHeaders = env->GetObjectField(objectHttpResponse, fieldResponseHeaders);

    // 设置响应内容
    env->SetObjectField(objectHttpResponse, fieldResponseBody, env->NewStringUTF(resp->body.c_str()));

    // 设置响应长度
    env->SetLongField(objectHttpResponse, fieldContentLength, hv::from_string<long long>(resp->GetHeader("Content-Length")));

    // 设置响应状态
    env->SetIntField(objectHttpResponse, fieldStatusCode, resp->status_code);

    // 设置所有响应cookie
    for (const auto &item : resp->cookies) {
        env->CallObjectMethod(tmpCookies, methodPut,
                              env->NewStringUTF(item.name.c_str()),
                              env->NewStringUTF(item.value.c_str()));
    }

    // 设置所有响应头
    for (const auto &item : resp->headers) {
        env->CallObjectMethod(tmpHeaders, methodPut,
                              env->NewStringUTF(item.first.c_str()),
                              env->NewStringUTF(item.second.c_str()));
    }

    // 设置响应contentType
    env->SetObjectField(objectHttpResponse,
                        fieldContentType,
                        env->NewStringUTF(getContentTypeName(resp->content_type)));

    env->DeleteLocalRef(tmpHeaders);
    env->DeleteLocalRef(tmpCookies);
    env->DeleteLocalRef(classHashMap);
    env->DeleteLocalRef(classHttpResponse);

    return objectHttpResponse;
}

jobject ResponseToHttpResponse(JNIEnv* &env, requests::Response resp) {
    return _ResponseToHttpResponse(env, resp);
}

static jobject _createHashMap(JNIEnv* &env) {
	jclass classHashMap = env->FindClass(CLASSNAME_HashMap);

	jmethodID methodHashMapDefaultCon = env->GetMethodID(classHashMap, "<init>", "()V");

    env->DeleteLocalRef(classHashMap);

	return env->NewObject(classHashMap, methodHashMapDefaultCon);
}

jobject createHashMap(JNIEnv* &env) { return _createHashMap(env); }

static const char* _getContentTypeName(http_content_type type) {
    switch (type) {
        case TEXT_PLAIN: {
            return "text/plain";
        }
        case TEXT_HTML: {
            return "text/html";
        }
        case TEXT_CSS: {
            return "text/css";
        }
        case IMAGE_JPEG: {
            return "image/jpeg";
        }
        case IMAGE_PNG: {
            return "image/png";
        }
        case IMAGE_GIF: {
            return "image/gif";
        }
        case IMAGE_BMP: {
            return "image/bmp";
        }
        case IMAGE_SVG: {
            return "image/svg";
        }
        case APPLICATION_OCTET_STREAM: {
            return "application/octet-stream";
        }
        case APPLICATION_JAVASCRIPT: {
            return "application/javascript";
        }
        case APPLICATION_XML: {
            return "application/xml";
        }
        case APPLICATION_JSON: {
            return "application/json";
        }
        case APPLICATION_GRPC: {
            return "application/grpc";
        }
        case APPLICATION_URLENCODED: {
            return "application/x-www-form-urlencoded";
        }
        case MULTIPART_FORM_DATA: {
            return "multipart/form-data";
        }
        default: {
            return "null";
        }
    }
}

const char* getContentTypeName(http_content_type type) {
    return _getContentTypeName(type);
}

static http_method _getMethodName(int n) {
    switch (n) {
        case 0: { return HTTP_DELETE; }
        default:
        case 1: { return HTTP_GET; }
        case 2: { return HTTP_HEAD; }
        case 3: { return HTTP_POST; }
        case 4: { return HTTP_PUT; }
        case 5: { return HTTP_CONNECT; }
        case 6: { return HTTP_OPTIONS; }
        case 7: { return HTTP_TRACE; }
        case 8: { return HTTP_COPY; }
        case 9: { return HTTP_LOCK; }
        case 10: { return HTTP_MKCOL; }
        case 11: { return HTTP_MOVE; }
    }
}

http_method getMethodName(int n) {
    return _getMethodName(n);
}

static void _throwException(JNIEnv* &env, const char * exceptionName, const char * message) {
    jclass classNullPointerException = env->FindClass(exceptionName);
    env->ThrowNew(classNullPointerException, message);
    env->DeleteLocalRef(classNullPointerException);
}

void throwException(JNIEnv* &env, const char * exceptionName, const char * message) {
    _throwException(env, exceptionName, message);
}