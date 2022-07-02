#include "client-transport-factory.h"
#include "client-tcp-ipv4.h"
#include "client-udp-ipv4.h"

IClientTransport* ClientTransportFactory::create(
        std::string transportType
        , IMsgListener* listener) {
    IClientTransport* client = nullptr;
    if (transportType.compare("TCP") == 0) {
        client = new ClientTcpIpv4(listener);
    } else if (transportType.compare("UDP") == 0) {
        client = new ClientUdpIpv4(listener);
    }
    return client;
}
