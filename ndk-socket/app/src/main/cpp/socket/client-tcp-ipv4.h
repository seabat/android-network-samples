#ifndef NDK_SOCKET_CLIENT_TCP_IPV4_H
#define NDK_SOCKET_CLIENT_TCP_IPV4_H


#include "msg-listener.h"

class ClientTcpIpv4 {
    IMsgListener* listener_;

public:
    ClientTcpIpv4(IMsgListener* listener);
    int send(std::string msg);
};


#endif //NDK_SOCKET_CLIENT_TCP_IPV4_H
