﻿#pragma once
/*
https://en.cppreference.com/w/cpp/freestanding
There are two kinds of implementations defined by the C++ standard:
hosted and freestanding implementations.
For hosted implementations the set of standard library headers required by the C++ standard is much larger than for freestanding ones.
*/
//fast_io_hosted defines what we could use in a hosted environment.

#if !defined(__cplusplus)
#error "You are not using C++ compiler"
#endif

#if defined(__GNUC__) && __GNUC__>=11 && __cplusplus<202002L
#error "fast_io requires at least C++20 standard compiler."
#else
#include"fast_io_freestanding.h"
#if __STDC_HOSTED__==1 && (!defined(_GLIBCXX_HOSTED) || _GLIBCXX_HOSTED==1)
#if __has_include(<bits/error_constants.h>)
#include<bits/error_constants.h>
#elif __has_include(<__errc>)
#include<__errc>
#elif __has_include(<xerrc.h>)
#include<xerrc.h>
#else
#include<system_error>
#endif

#include"fast_io_hosted/posix_error_scatter/impl.h"
#include"fast_io_hosted/posix_error.h"
#ifdef __MSDOS__
#undef __STRICT_ANSI__
#endif

#include"fast_io_hosted/io_io_file.h"
#include"fast_io_hosted/cstring_view.h"

#include"fast_io_hosted/mmap.h"
#include"fast_io_hosted/posix_status.h"
#include<ctime>
#include"fast_io_unit/timespec.h"

#if !defined(__AVR__)
#include"fast_io_hosted/platforms/native.h"
#include"fast_io_hosted/file_loaders/impl.h"
#include"fast_io_hosted/wrapper.h"
#include"fast_io_hosted/white_hole/white_hole.h"
#include"fast_io_hosted/dbg/impl.h"
#endif

#include"fast_io_hosted/time.h"

#include"fast_io_hosted/threads/mutex/impl.h"
#include"fast_io_hosted/iomutex.h"

#include"fast_io_hosted/filesystem/native.h"
#include"fast_io_hosted/dll/dll.h"
#include"fast_io_hosted/process_revamp/native.h"

#if (defined(_MSC_VER) && !defined(__clang__))
#include"fast_io_unit/gb18030.h"
#endif

#if defined(_WIN32) || defined(__CYGWIN__)
#include"fast_io_hosted/box.h"
#endif

#if defined(_GLIBCXX_STRING) || defined(_LIBCPP_STRING) || defined(_STRING_)
#include"fast_io_unit/string.h"
#endif

#if defined(_GLIBCXX_STRING_VIEW) || defined(_LIBCPP_STRING_VIEW) || defined(_STRING_VIEW_)
#include"fast_io_unit/http_header.h"
#endif

#if defined(_GLIBCXX_CHRONO) || defined(_LIBCPP_CHRONO) || defined(_CHRONO_)
#include"fast_io_unit/chrono.h"
#endif

#if defined(_GLIBCXX_COMPLEX) || defined(_LIBCPP_COMPLEX) || defined(_COMPLEX_)
#include"fast_io_unit/complex.h"
#endif

#if defined(_GLIBCXX_FILESYSTEM) || defined(_LIBCPP_FILESYSTEM) || defined(_FILESYSTEM_)
#include"fast_io_unit/filesystem.h"
#endif

#if defined(_WIN32) && defined(WINRT_BASE_H)
#if __has_include(<winrt/base.h>)
#include"fast_io_driver/cppwinrt_impl/impl.h"
#endif
#endif

#endif

#endif
