<template>
  <el-table :data="msg" border style="width: 70%"
            :header-cell-style="{'text-align':'center'}"
            :cell-style="{'text-align':'center'}">
    <el-table-column prop="root" label="路径" > </el-table-column>
    <el-table-column prop="pred_label" label="预测结果" ></el-table-column>
    <el-table-column prop="Accuracy" label="精度" > </el-table-column>
  </el-table>
</template>
<!--&lt;!&ndash;原&ndash;&gt;-->
<!--<el-menu-->
<!--router-->
<!--:default-active="activeIndex"-->
<!--class="el-menu-demo"-->
<!--mode="horizontal"-->
<!--@select="handleSelect"-->
<!--&gt;-->
<!--<el-menu-item index="/information/insert">微博签到数据模块</el-menu-item>-->
<!--</el-menu>-->
<!--<el-form class="login-container" label-position="left" label-width="0px">-->
<!--<h3 class="login_title">系统登录</h3>-->
<!--<el-form-item>-->
<!--<el-input-->
<!--type="text"-->
<!--v-model="loginForm.adminName"-->
<!--auto-complete="off"-->
<!--placeholder="账号"-->
<!--&gt;</el-input>-->
<!--</el-form-item>-->
<!--<el-form-item>-->
<!--<el-input-->
<!--type="password"-->
<!--v-model="loginForm.passWord"-->
<!--auto-complete="off"-->
<!--placeholder="密码"-->
<!--&gt;</el-input>-->
<!--</el-form-item>-->
<!--<el-row>-->
<!--<el-col span="12">-->
<!--<el-form-item style="width: 60%">-->
<!--<el-button-->
<!--type="primary"-->
<!--style="width: 100%; background: #505458; border: none"-->
<!--v-on:click="login"-->
<!--&gt;登录</el-button-->
<!--&gt;-->
<!--</el-form-item>-->
<!--</el-col>-->
<!--<el-col span="12">-->
<!--<el-form-item style="width: 60%">-->
<!--<el-button-->
<!--type="primary"-->
<!--style="width: 100%; background: #505458; border: none"-->
<!--v-on:click="register"-->
<!--&gt;注册</el-button-->
<!--&gt;-->
<!--</el-form-item>-->
<!--</el-col>-->
<!--</el-row>-->
<!--</el-form>-->
<script>
export default {
  data() {
    return {
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1517 弄",
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1519 弄",
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1516 弄",
        },
      ],
    };
  },
  props: ["msg"],
};
</script>
