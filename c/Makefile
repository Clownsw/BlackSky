
CC_DEBUG = g++ -Wall -Ofast -s -static -g -fno-exceptions -c
CC_RELEASE = g++ -Wall -Ofast -s -static -DNDEBUG -fno-exceptions -c
INCLUDE = -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude
LIB = -Llib -lfmt -lhv -lssl -lcrypto -lws2_32 -lwsock32
LINK = g++ -static -static-libgcc -static-libstdc++ -Wl,--gc-sections -shared -o libhv.dll

all: Tools.o Requests.o Md5.o
	$(LINK) Tools.o Requests.o Md5.o $(LIB)
	
release: ToolsRelease.o RequestsRelease.o Md5Release.o
	$(LINK) ToolsRelease.o RequestsRelease.o Md5Release.o $(LIB)

ToolsRelease.o: Tools.h Tools.cpp
	$(CC_RELEASE) -o ToolsRelease.o Tools.cpp $(INCLUDE)

RequestsRelease.o: Interface.h Requests.cpp
	$(CC_RELEASE) -o RequestsRelease.o Requests.cpp $(INCLUDE)

Md5Release.o: Interface.h Md5.cpp
	$(CC_RELEASE) -o Md5Release.o Md5.cpp $(INCLUDE)

Tools.o: Tools.h Tools.cpp
	$(CC_DEBUG) -o Tools.o Tools.cpp $(INCLUDE)

Requests.o: Interface.h Requests.cpp
	$(CC_DEBUG) -o Requests.o Requests.cpp $(INCLUDE)

Md5.o: Interface.h Md5.cpp
	$(CC_DEBUG) -o Md5.o Md5.cpp $(INCLUDE)

clean:
	del Tools.o
	del Md5.o
	del Requests.o
	del ToolsRelease.o
	del RequestsRelease.o
	del Md5Release.o
	del libhv.dll