#include <thread>
#include "socket-server.h"
#include "jni-ref.h"
#include "socket/server/i-loop-transport.h"
#include "socket/server/loop-transport-factory.h"

SocketServer* SocketServer::server_;

SocketServer* SocketServer::createInstance(std::string transportType, jobject jServer) {
    if (SocketServer::server_ == nullptr) {
        SocketServer::server_ = new SocketServer(transportType
                , JniRef::getInstance()->getJNIEnv()->NewGlobalRef(jServer));
    }
    return SocketServer::server_;
}

SocketServer* SocketServer::getInstance() {
    return SocketServer::server_;
}

SocketServer::SocketServer(std::string transportType, jobject jServer)
: transport_type_(transportType)
, j_server_(jServer)
{
}

SocketServer::~SocketServer() {
    this->loop->stop();
    JniRef::getInstance()->getJNIEnv()->DeleteGlobalRef(this->j_server_);
    this->j_server_ = nullptr;
}

void SocketServer::run() {
    std::thread serverThread([this]() {
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        std::shared_ptr<ILoopTransport> loop(LoopTransportFactory::create(this->transport_type_, this));
        this->setLoop(loop);
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

void SocketServer::setLoop(std::shared_ptr<ILoopTransport> loop){
    this->loop = loop;
}

void SocketServer::stop() {
    server_->loop->stop();
    JniRef::getInstance()->getJNIEnv()->DeleteGlobalRef(SocketServer::server_->j_server_);
    SocketServer::server_->j_server_ = nullptr;
    delete server_;
    server_ = nullptr;
}
