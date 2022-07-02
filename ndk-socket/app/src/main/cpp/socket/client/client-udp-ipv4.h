#ifndef NDK_SOCKET_CLIENT_UDP_IPV4_H
#define NDK_SOCKET_CLIENT_UDP_IPV4_H


#include "i-msg-listener.h"
#include "i-client-transport.h"

class ClientUdpIpv4 : public IClientTransport{
    IMsgListener* listener_;

public:
    ClientUdpIpv4(IMsgListener* listener);
    int send(std::string msg) override;
};


#endif //NDK_SOCKET_CLIENT_UDP_IPV4_H
