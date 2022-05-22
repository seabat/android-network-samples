#include <thread>
#include "socket-server.h"
#include "jni-ref.h"
#include "loop-tcp-ipv4.h"

SocketServer* SocketServer::server_;

SocketServer* SocketServer::createInstance(jobject jServer) {
    if (SocketServer::server_ == nullptr) {
        SocketServer::server_ = new SocketServer(JniRef::getInstance()->getJNIEnv()->NewGlobalRef(jServer));
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
    JniRef::getInstance()->getJNIEnv()->DeleteGlobalRef(this->j_server_);
}

void SocketServer::run() {
    std::thread serverThread([this]() {
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        std::shared_ptr<LoopTcpIpv4> loop = std::make_shared<LoopTcpIpv4>(this);
        loop->run();
        return;
    });
    serverThread.detach();
}

void SocketServer::callback(std::string msg) {
    JNIEnv* env = JniRef::getInstance()->getJNIEnv();
    bool attached = true;
    if (env == nullptr) {
        JniRef::getInstance()->getJavaVm()->AttachCurrentThread(&env, nullptr);
        attached = true;
        if (env == nullptr) {
            return;
        }
    }

    jstring jMsg = env->NewStringUTF(msg.c_str());
    jclass jSocketServerClazz = nullptr;
    jmethodID jCallbackMethod = nullptr;

    jSocketServerClazz = env->GetObjectClass(this->j_server_);
      // NOTE: サブスレッドで GetObjectClass() を実行する際は予め NewGlobalRef(j_server_) を実行しておく

    jCallbackMethod = env->GetMethodID(jSocketServerClazz, "callback", "(Ljava/lang/String;)V");
    env->CallVoidMethod(this->j_server_, jCallbackMethod, jMsg);

    if (jSocketServerClazz != nullptr) env->DeleteLocalRef(jSocketServerClazz);
    if (jMsg != nullptr) env->DeleteLocalRef(jMsg);
    if (attached) JniRef::getInstance()->getJavaVm()->DetachCurrentThread();
}

void SocketServer::close() {
    JniRef::getInstance()->getJNIEnv()->DeleteGlobalRef(SocketServer::server_->j_server_);
    delete server_;
    server_ = nullptr;
}
