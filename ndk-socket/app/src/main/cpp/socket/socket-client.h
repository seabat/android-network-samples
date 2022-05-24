#ifndef NDK_SOCKET_SOCKET_CLIENT_H
#define NDK_SOCKET_SOCKET_CLIENT_H

#include <jni.h>
#include "msg-listener.h"

class SocketClient : public IMsgListener {
private:
    //variables
    static SocketClient* client_;
    jobject j_client_;

    // constructors
    SocketClient(jobject jClient);

public:
    //methods
    ~SocketClient();
    static SocketClient* createInstance(jobject jClient);
    static SocketClient* getInstance();
    void callback(std::string msg) override;
    void send(const char* msg);
};

#endif //NDK_SOCKET_SOCKET_CLIENT_H
