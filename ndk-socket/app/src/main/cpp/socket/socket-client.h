#ifndef NDK_SOCKET_SOCKET_CLIENT_H
#define NDK_SOCKET_SOCKET_CLIENT_H

#include <jni.h>

class SocketClient {
private:
    //variables
    static SocketClient* client_;
    jobject j_client;

    // constructors
    SocketClient(jobject jClient);

public:
    //methods
    static SocketClient* createInstance(jobject jClient);
    static SocketClient* getInstance();
    void send(const char* msg);
};

#endif //NDK_SOCKET_SOCKET_CLIENT_H
