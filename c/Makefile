
CC_DEBUG = g++ -Wall -Wformat -s -static -g -c
CC_RELEASE = g++ -Wall -Ofast -s -static -DNDEBUG -flto -c
INCLUDE = -ID:/zulu-jdk11/include -ID:/zulu-jdk11/include/win32 -Iinclude
LIB = -Llib -lspdlog -lhv -lssl -lcrypto -lws2_32 -lwsock32 -lpthread
LINK = g++ -static -static-libgcc -static-libstdc++ -Wl,--gc-sections -shared -o libhv.dll

DEBUG_NAME = Tools.o Requests.o Md5.o Base64.o Logger.o
RELEASE_NAME = ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o LoggerRelease.o

all: Tools.o Requests.o Md5.o Base64.o Logger.o
	$(LINK) $(DEBUG_NAME) $(LIB)
	
release: ToolsRelease.o RequestsRelease.o Md5Release.o Base64Release.o LoggerRelease.o
	$(LINK) $(RELEASE_NAME) $(LIB)


ToolsRelease.o: Tools.h Tools.cpp
	$(CC_RELEASE) -o ToolsRelease.o Tools.cpp $(INCLUDE)

RequestsRelease.o: Interface.h Requests.h Requests.cpp
	$(CC_RELEASE) -o RequestsRelease.o Requests.cpp $(INCLUDE)

Md5Release.o: Interface.h Md5.cpp
	$(CC_RELEASE) -o Md5Release.o Md5.cpp $(INCLUDE)

Base64Release.o: Interface.h Base64.cpp
	$(CC_RELEASE) -o Base64Release.o Base64.cpp $(INCLUDE)

LoggerRelease.o: Interface.h Logger.cpp
	$(CC_RELEASE) -o LoggerRelease.o Logger.cpp $(INCLUDE)


Tools.o: Tools.h Tools.cpp
	$(CC_DEBUG) -o Tools.o Tools.cpp $(INCLUDE)

Requests.o: Interface.h Requests.h Requests.cpp
	$(CC_DEBUG) -o Requests.o Requests.cpp $(INCLUDE)

Md5.o: Interface.h Md5.cpp
	$(CC_DEBUG) -o Md5.o Md5.cpp $(INCLUDE)

Base64.o: Interface.h Base64.cpp
	$(CC_DEBUG) -o Base64.o Base64.cpp $(INCLUDE)

Logger.o: Interface.h Logger.cpp
	$(CC_DEBUG) -o Logger.o Logger.cpp $(INCLUDE)

clean:
	del Tools.o
	del Md5.o
	del Base64.o
	del Requests.o
	del Logger.o
	del ToolsRelease.o
	del RequestsRelease.o
	del Md5Release.o
	del Base64Release.o
	del LoggerRelease.o
	del libhv.dll