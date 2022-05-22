#include "jni-ref.h"

JniRef* JniRef::ref_;

JniRef::JniRef(JavaVM* vm)
: vm_(vm)
{
}

JniRef* JniRef::createInstance(JavaVM* vm) {
    if (JniRef::ref_ == nullptr) {
        JniRef::ref_ = new JniRef(vm);
    }
    return JniRef::ref_;
}

JniRef* JniRef::getInstance() {
    JniRef::ref_->vm_->GetEnv(reinterpret_cast<void**>(&JniRef::ref_->env_), JNI_VERSION_1_6);
    //NOTE: 実行スレッドに対応した JNIEnv にする

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

