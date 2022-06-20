#ifndef NDK_SOCKET_I_LOOP_TRANSPORT_H
#define NDK_SOCKET_I_LOOP_TRANSPORT_H
class ILoopTransport {
public:
    virtual void run() = 0;
    virtual void stop() = 0;
};
#endif //NDK_SOCKET_I_LOOP_TRANSPORT_H
