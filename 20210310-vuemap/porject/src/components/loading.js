import { Loading } from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

let loading = null;
const showLoading = (params) => {
  console.log('showLoading');
  if(loading) {
    loading.close()
  }
  let options = {
    fullscreen: true,
    target: document.querySelector('#mainLoad'),
    lock: true,
    text: '数据加载中',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.7)'
  };
  Object.assign(
    options,
    params
  );
  loading = Loading.service(options)
};
const hideLoading = () => {
  console.log('closeLoading');
  loading.close()
};
export {
  showLoading,
  hideLoading
}
//   let loadingInstance = Loading.service({
//     lock: true,
//     text: '加载中...',
//     background: 'rgba(0, 0, 0, 0.7)',
// });
