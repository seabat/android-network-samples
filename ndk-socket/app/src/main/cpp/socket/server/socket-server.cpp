#include <thread>
#include <android/log.h>
#include "socket-server.h"
#include "jni-ref.h"
#include "socket/server/i-loop-transport.h"
#include "socket/server/loop-transport-factory.h"
#include "transport-type.h"

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
    for (auto iter = this->loop_map_.begin(); iter != this->loop_map_.end(); ++iter){
        __android_log_print(ANDROID_LOG_INFO
                            , "~SocketServer"
                            , "transport type: %s", convertTransportString(iter->first).c_str());
        iter->second->stopWait();
    }

    JniRef::getInstance()->getJNIEnv()->DeleteGlobalRef(this->j_server_);
    this->j_server_ = nullptr;
}

void SocketServer::run(TransportType transportType) {
    decltype(this->loop_map_)::iterator it = this->loop_map_.find(transportType);
    if (it != this->loop_map_.end()) {
        return;
    }

    std::thread serverThread([this, transportType]() {
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        std::shared_ptr<ILoopTransport> loop(LoopTransportFactory::create(transportType, this));
        this->addLoop(transportType, loop);
        loop->waitMsg();
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

void SocketServer::addLoop(TransportType transportType, std::shared_ptr<ILoopTransport> loop){
    this->loop_map_[transportType] = loop;
}

void SocketServer::stop() {
    for (auto iter = server_->loop_map_.begin(); iter != server_->loop_map_.end(); ++iter){
        iter->second->stopWait();
    }

    JniRef::getInstance()->getJNIEnv()->DeleteGlobalRef(SocketServer::server_->j_server_);
    SocketServer::server_->j_server_ = nullptr;
    delete server_;
    server_ = nullptr;
}
