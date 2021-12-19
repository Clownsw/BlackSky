all: tools.o main.o
	g++ -static -static-libgcc -static-libstdc++ -shared -o libhv.dll tools.o main.o -Llib -lhv -lssl -lcrypto -lws2_32 -lwsock32

tools.o: tools.h tools.cpp
	g++ -Wall -Ofast -s -static -c tools.cpp -ID:/jdk11/include -ID:/jdk11/include/win32

main.o: Requests.h main.cpp
	g++ -Wall -Ofast -s -static -c main.cpp -ID:/jdk11/include -ID:/jdk11/include/win32 -Iinclude

clean:
	del tools.o
	del main.o
	del libhv.dll