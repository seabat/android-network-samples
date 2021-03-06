#include <thread>
#include <jni-ref.h>
#include "socket-client.h"
#include "i-client-transport.h"
#include "client-transport-factory.h"

std::shared_ptr<SocketClient> SocketClient::client_;

std::shared_ptr<SocketClient> SocketClient::createInstance(jobject jClient) {
    if (SocketClient::client_ == nullptr) {
        SocketClient::client_ = std::make_shared<SocketClient>(JniRef::getInstance()->getJNIEnv()->NewGlobalRef(jClient));
    }
    return SocketClient::client_;
}

SocketClient::SocketClient(jobject jClient)
: j_client_(jClient)
{
}

SocketClient::~SocketClient() {
    JniRef::getInstance()->getJNIEnv()->DeleteGlobalRef(this->j_client_);
    this->j_client_ = nullptr;
}

void SocketClient::callback(std::string msg) {
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
    jclass jSocketClientClazz = nullptr;
    jmethodID jCallbackMethod = nullptr;

    jSocketClientClazz = env->GetObjectClass(this->j_client_);
    // NOTE: サブスレッドで GetObjectClass() を実行する際は予め NewGlobalRef(j_server_) を実行しておく

    jCallbackMethod = env->GetMethodID(jSocketClientClazz, "callback", "(Ljava/lang/String;)V");
    env->CallVoidMethod(this->j_client_, jCallbackMethod, jMsg);

    if (jSocketClientClazz != nullptr) env->DeleteLocalRef(jSocketClientClazz);
    if (jMsg != nullptr) env->DeleteLocalRef(jMsg);
    if (attached) JniRef::getInstance()->getJavaVm()->DetachCurrentThread();
}

void SocketClient::sendMsg(TransportType transportType, std::string msg) {
    std::thread clientThread([this, transportType, msg]() {
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        std::shared_ptr<IClientTransport> client(ClientTransportFactory::create(transportType, this));
        client->sendMsg(msg);
        return;
    });
    clientThread.detach();
}
