#ifndef NDK_SOCKET_I_MSG_LISTENER_H
#define NDK_SOCKET_I_MSG_LISTENER_H


#include <string>

class IMsgListener {
public:
    virtual void callback(std::string msg) = 0;
};


#endif //NDK_SOCKET_I_MSG_LISTENER_H
