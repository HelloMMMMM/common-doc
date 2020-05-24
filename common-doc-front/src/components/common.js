let sessionStorage = window.sessionStorage;

export function setIsVerify(verify) {
    sessionStorage.setItem("isVerify", verify);
}

export function isVerify() {
    return sessionStorage.getItem("isVerify");
}