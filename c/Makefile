
CC_DEBUG = g++ -Wall -Ofast -s -static -g -fno-exceptions -c
CC_RELEASE = g++ -Wall -Ofast -s -static -DNDEBUG -fno-exceptions -c
INCLUDE = -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude
LIB = -Llib -lfmt -lhv -lssl -lcrypto -lws2_32 -lwsock32
LINK = g++ -static -static-libgcc -static-libstdc++ -Wl,--gc-sections -shared -o libhv.dll

all: Tools.o Requests.o Fmt.o
	$(LINK) Tools.o Requests.o Fmt.o $(LIB)
	
release: ToolsRelease.o RequestsRelease.o FmtRelease.o
	$(LINK) ToolsRelease.o RequestsRelease.o FmtRelease.o $(LIB)

ToolsRelease.o: Tools.h Tools.cpp
	$(CC_RELEASE) -o ToolsRelease.o Tools.cpp $(INCLUDE)

RequestsRelease.o: Interface.h Requests.cpp
	$(CC_RELEASE) -o RequestsRelease.o Requests.cpp $(INCLUDE)

FmtRelease.o: fmt.cpp
	$(CC_RELEASE) -o FmtRelease.o Fmt.cpp $(INCLUDE)

Tools.o: Tools.h Tools.cpp
	$(CC_DEBUG) -o Tools.o Tools.cpp $(INCLUDE)

Requests.o: Interface.h Requests.cpp
	$(CC_DEBUG) -o Requests.o Requests.cpp $(INCLUDE)

Fmt.o: fmt.cpp
	$(CC_DEBUG) -o Fmt.o Fmt.cpp $(INCLUDE)

clean:
	del Tools.o
	del Requests.o
	del Fmt.o
	del ToolsRelease.o
	del RequestsRelease.o
	del FmtRelease.o
	del libhv.dll