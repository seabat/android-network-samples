#include "socket-client.h"

SocketClient* SocketClient::client_;

SocketClient* SocketClient::createInstance(jobject jClient) {
    if (SocketClient::client_ == nullptr) {
        SocketClient::client_ = new SocketClient(jClient);
    }
    return SocketClient::client_;
}

SocketClient* SocketClient::getInstance() {
    return SocketClient::client_;
}

SocketClient::SocketClient(jobject jClient)
: j_client(jClient)
{
}

void SocketClient::send(const char* msg) {
}
