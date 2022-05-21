#include <thread>
#include "socket-server.h"

SocketServer* SocketServer::server_;

SocketServer* SocketServer::createInstance(jobject jServer) {
    if (SocketServer::server_ == nullptr) {
        SocketServer::server_ = new SocketServer(jServer);
    }
    return SocketServer::server_;
}

SocketServer* SocketServer::getInstance() {
    return SocketServer::server_;
}

SocketServer::SocketServer(jobject jServer)
: j_server_(jServer)
{
}

SocketServer::~SocketServer() {
}

void SocketServer::run() {
    std::thread serverThread([]() {
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        return;
    });
    serverThread.detach();
}

void SocketServer::close() {
    delete server_;
    server_ = nullptr;
}
