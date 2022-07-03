#include "transport-type.h"

TransportType convertTransportEnum(std::string strType) {
    if (strType.compare("TCP") == 0) {
        return TransportType::TCP;
    } else if (strType.compare("UDP") == 0) {
        return TransportType::UDP;
    } else {
        return TransportType::UNKNOWN;
    }
}

std::string convertTransportString(TransportType enumType) {
    if (TransportType::TCP == enumType) {
        return "TCP";
    } else if (TransportType::UDP == enumType) {
        return "UDP";
    } else {
        return "";
    }
}
