#include "client-transport-factory.h"
#include "client-tcp-ipv4.h"
#include "client-udp-ipv4.h"

IClientTransport* ClientTransportFactory::create(
        TransportType transportType
        , IMsgListener* listener) {
    IClientTransport* client = nullptr;
    if (TransportType::TCP == transportType) {
        client = new ClientTcpIpv4(listener);
    } else if (TransportType::UDP == transportType) {
        client = new ClientUdpIpv4(listener);
    }
    return client;
}
