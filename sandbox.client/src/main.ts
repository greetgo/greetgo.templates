import Vue from 'vue';
import App from '@/App.vue';
import router from '@/router';
import axios from 'axios';
import store from '@/store';

Vue.config.productionTip = process.env.NODE_ENV === 'production';

console.log("ag347ghd1t1 process.env.NODE_ENV = ", process.env.NODE_ENV);
console.log("ag347ghd1t1 Vue.config.productionTip = ", Vue.config.productionTip);

axios.interceptors.request.use(config => {
  config.baseURL = process.env.VUE_APP_URL_PREFIX;
  config.headers.token = localStorage.getItem("token");
  config.withCredentials = true;
  return config;
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
