import axios from 'axios';
import { Message, Loading } from 'element-ui';
import api from "./api";

let base = api.base;
let loading;

// 允许携带cookie(不然带不上cookie，同时后端也需配置)
axios.defaults.withCredentials = true;

axios.interceptors.request.use(config => {
    loading = Loading.service({
        spinner: "el-icon-loading",
        text: "Loading...",
        background: 'rgba(0, 0, 0, 0.5)',
        lock: true
    });
    return config;
}, err => {
    loading.close();
    return Promise.resolve(err);
})

axios.interceptors.response.use(data => {
    loading.close();
    if (data.data.state === 'fail') {
        Message.error(data.data.msg);
    }
    return data;
}, err => {
    loading.close();
    if (err.response) {
        if (err.response.status === 500) {
            Message.error('服务器错误');
        } else if (err.response.status === 404) {
            Message.error('无此页面或接口');
        }
    } else {
        if (err.message === 'Network Error') {
            Message.error('网络错误');
        } else {
            Message.error('未知错误');
        }
    }
    return Promise.resolve(err);
})

export const post = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function(data) {
            let ret = ''
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}