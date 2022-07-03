#ifndef NDK_SOCKET_CLIENT_TCP_IPV4_H
#define NDK_SOCKET_CLIENT_TCP_IPV4_H

#include <string>
#include "i-msg-listener.h"
#include "i-client-transport.h"

class ClientTcpIpv4 : public IClientTransport {
    // variables
    IMsgListener* listener_;

public:
    // constructors
    ClientTcpIpv4(IMsgListener* listener);

    // methods
    int sendMsg(std::string msg) override;
};


#endif //NDK_SOCKET_CLIENT_TCP_IPV4_H
