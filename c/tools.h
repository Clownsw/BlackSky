#pragma once

#ifndef TOOLS_H
#define TOOLS_H

#include <jni.h>
#include <map>
#include <string>
#include <iostream>

static std::map<std::string, jclass> g_jclasss;

jclass queryJclassByName(const char* name);

std::map<std::string, std::string> parseMap(JNIEnv* env, jobject &obj);

jobject createHashMap(JNIEnv* env);
#endif