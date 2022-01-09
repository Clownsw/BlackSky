//
// By: smilex
//

#pragma once
#ifndef REQUESTS_H
#define REQUESTS_H

#include <hv/HttpMessage.h>

typedef struct JavaHttpRequest {
    const char * url = nullptr;
    http_method method;
    const char * body = nullptr;
    std::map<absl::string_view, absl::string_view> headers;
    std::map<absl::string_view, absl::string_view> params;
    const char * cookie = nullptr;
} JavaHttpRequest;

typedef struct JavaHttpResponse {
    const char * body;
    int statusCode;
    http_cookies cookies;
    http_headers headers;
    http_content_type contentType;
} JavaHttpResponse;


#endif //REQUESTS_H
