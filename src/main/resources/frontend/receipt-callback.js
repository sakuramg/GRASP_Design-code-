/**
 * 2.6.2正文提到的前端Promise链式回调示意（axios.get(url).then(…).catch(…).finally(…)）。
 * 定稿正文未为其单列代码编号，此文件仅作补充示例。
 */
function fetchAndRenderReceipt(axios, renderReceipt, showError, hideLoading) {
    return axios.get('/api/sale/12345')
        .then(resp => renderReceipt(resp.data))     // 成功回调：渲染小票数据
        .catch(err => showError(err.message))       // 失败回调：提示错误原因
        .finally(() => hideLoading());              // 收尾回调：无论成败均关闭加载提示
}

module.exports = { fetchAndRenderReceipt };
