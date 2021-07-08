<template>
  <body id="poster">
    <h1
      class="title_show"
      src=""
      width="600"
      height="170"
      usemap="#mp"
      title=""
      cursor="default"
      style="color: white"
    >
      时空大数据地理计算与分析平台
    </h1>
      <div class="loginPage" >
            <el-tabs >
              <el-tab-pane >
                <span slot="label" class="title" style="width: 50%">登录</span>
                <el-form>
                  <el-form-item >
                    <el-input class="user" v-model="loginForm.adminName" prefix-icon="el-icon-user" placeholder="账号" ></el-input>
                  </el-form-item>
                  <el-form-item >
                    <el-input type="password" class="password" v-model="loginForm.passWord" prefix-icon="el-icon-lock" placeholder="密码"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="login">登录</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>

              <el-tab-pane>
                <span slot="label" class="title">注册</span>
                <el-form>
                  <el-form-item prop="username">
                    <el-input  class="user" v-model="registerForm.adminName" prefix-icon="el-icon-user" placeholder="账号"></el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input type="password" class="password" v-model="registerForm.passWord" prefix-icon="el-icon-lock" placeholder="密码"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="register">注册</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
        </el-tabs>

      </div>

  </body>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        adminName: "",
        passWord: "",
      },
      registerForm: {
        adminName: "",
        passWord: "",
      },
    };
  },

  methods: {
    login() {

      // this.$router.push({'path':'/information/select'})
      // this.$router.push({'path':'/information/select'})

      this.$axios.post("/login", this.loginForm).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("SET_TOKEN", res.data.data.token);
          this.$store.commit("GET_USER", res.data.data.name);
          this.$message({
            message: "登陆成功",
            type: "success",
          });
          this.$router.push({ path: "/information/mainPage" });
        } else {
          this.$message({
            message: "登陆失败",
            type: "error",
          });
        }
      });
    },
    register() {
      this.$axios
        .post(
          `/register?userName=${this.registerForm.adminName}&passWord=${this.registerForm.passWord}`
        )
        .then((res) => {
          this.$message({
            message: "注册成功",
            type: "success",
          });
        });
    },
  },
};
</script>
<style>
#poster {
  background: url("../assets/login-background.gif") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  font-size: 25px;
}
body {
  margin: 0px;
}
.title_show {
  margin: 130px auto;
}
  #tab-0 , #tab-1{
    width: 200px;
    height: 50px;
    color: white;
  }
  .user, .password{
    width: 360px;
    position: relative;
    top: 5px;
  }
  .loginPage{
    width: 400px;
    margin: auto;
    background-color: rgba(0, 0, 0, 0.3);
    border-radius: 8px;
    box-shadow: 10px 10px 20px 0px rgba(0, 0, 0, 0.7);
  }

</style>
