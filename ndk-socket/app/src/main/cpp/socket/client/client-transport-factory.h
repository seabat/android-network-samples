#ifndef NDK_SOCKET_CLIENT_TRANSPORT_FACTORY_H
#define NDK_SOCKET_CLIENT_TRANSPORT_FACTORY_H

#include "i-client-transport.h"
#include "i-msg-listener.h"

class ClientTransportFactory {

public:
ClientTransportFactory(){}
~ClientTransportFactory(){}
static IClientTransport* create(std::string transportType, IMsgListener* listener);

};


#endif //NDK_SOCKET_CLIENT_TRANSPORT_FACTORY_H
