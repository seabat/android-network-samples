#ifndef NDK_SOCKET_I_CLIENT_TRANSPORT_H
#define NDK_SOCKET_I_CLIENT_TRANSPORT_H

#include <string>

class IClientTransport {

public:
    virtual int send(std::string msg) = 0;
};
#endif //NDK_SOCKET_I_CLIENT_TRANSPORT_H
