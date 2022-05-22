#include "jni-ref.h"

JniRef* JniRef::ref_;

JniRef::JniRef(JavaVM* vm)
: vm_(vm)
{
    vm->GetEnv(reinterpret_cast<void**>(&this->env_), JNI_VERSION_1_6);
}

JniRef* JniRef::createInstance(JavaVM* vm) {
    if (JniRef::ref_ == nullptr) {
        JniRef::ref_ = new JniRef(vm);
    }
    return JniRef::ref_;
}

JniRef* JniRef::getInstance() {
    return JniRef::ref_;
}

JavaVM* JniRef::getJavaVm() {
    return this->vm_;
}

JNIEnv* JniRef::getJNIEnv() {
    return this->env_;
}

void JniRef::setJavaVm(JavaVM* vm) {
    this->vm_ = vm;
}

void JniRef::setJNIEnv(JNIEnv* env) {
    this->env_ = env;
}

