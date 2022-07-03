#ifndef NDK_SOCKET_CLIENT_TCP_IPV4_H
#define NDK_SOCKET_CLIENT_TCP_IPV4_H

#include "i-msg-listener.h"
#include "i-client-transport.h"

class ClientTcpIpv4 : public IClientTransport {
    IMsgListener* listener_;

public:
    ClientTcpIpv4(IMsgListener* listener);
    int send(std::string msg) override;
};


#endif //NDK_SOCKET_CLIENT_TCP_IPV4_H
