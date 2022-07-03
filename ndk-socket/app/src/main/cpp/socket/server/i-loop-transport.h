#ifndef NDK_SOCKET_I_LOOP_TRANSPORT_H
#define NDK_SOCKET_I_LOOP_TRANSPORT_H
class ILoopTransport {
public:
    virtual void waitMsg() = 0;
    virtual void stopWait() = 0;
};
#endif //NDK_SOCKET_I_LOOP_TRANSPORT_H
