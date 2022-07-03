#ifndef NDK_SOCKET_TRANSPORT_TYPE_H
#define NDK_SOCKET_TRANSPORT_TYPE_H

#include <string>

enum class TransportType {
    TCP
    , UDP
    , UNKNOWN
};

extern "C"
TransportType convertTransportEnum(std::string strType);

extern "C"
std::string convertTransportString(TransportType enumType);

#endif //NDK_SOCKET_TRANSPORT_TYPE_H
