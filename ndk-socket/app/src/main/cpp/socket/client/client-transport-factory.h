#ifndef NDK_SOCKET_CLIENT_TRANSPORT_FACTORY_H
#define NDK_SOCKET_CLIENT_TRANSPORT_FACTORY_H

#include <string>
#include "i-client-transport.h"
#include "i-msg-listener.h"
#include "transport-type.h"

class ClientTransportFactory {

public:
    // constructors
    ClientTransportFactory(){}
    ~ClientTransportFactory(){}

    // methods
    static IClientTransport* create(TransportType transportType, IMsgListener* listener);

};


#endif //NDK_SOCKET_CLIENT_TRANSPORT_FACTORY_H
