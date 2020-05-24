<template>
  <div id="app">
    <el-backtop></el-backtop>
    <el-row>
      <el-col :span="20" :offset="2">
        <div>
          <span :class="docButtonStyle" @click="navButtonClick(false)">文档</span>
          <span :class="editButtonStyle" @click="navButtonClick(true)">编辑</span>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20" :offset="2">
        <DocEdit v-if="isEdit"></DocEdit>
        <DocView v-else></DocView>
      </el-col>
    </el-row>
  </div>
</template>
<style>
.nav-button {
  padding: 8px;
  font-size: 20px;
  font-weight: bold;
  margin-top: 20px;
  margin-left: 12px;
  margin-right: 12px;
  cursor: pointer;
  display: inline-block;
}
.nav-button-color-normal {
  color: #000;
}
.nav-button-color-selected {
  color: #409eff;
}
</style>
<script>
import { setIsVerify, isVerify } from "./components/common";
import DocView from "./views/DocView";
import DocEdit from "./views/DocEdit";
export default {
  components: {
    DocView,
    DocEdit
  },
  data() {
    return {
      isEdit: false,
      pwd: null,
      docButtonStyle: "nav-button nav-button-color-selected",
      editButtonStyle: "nav-button nav-button-color-normal"
    };
  },
  methods: {
    navButtonClick(isEdit) {
      if (isEdit) {
        this.verify();
      } else {
        this.isEdit = isEdit;
        this.docButtonStyle = "nav-button nav-button-color-selected";
        this.editButtonStyle = "nav-button nav-button-color-normal";
      }
    },
    verify() {
      if (isVerify()) {
        this.toEdit();
      } else {
        this.showVerifyDialog();
      }
    },
    showVerifyDialog() {
      this.$prompt("验证", "编辑权限验证", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        inputPlaceholder: "请输入密码",
        inputValue: null,
        inputValidator: this.inputValidatorWhenVerify
      })
        .then(({ value }) => {
          this.pwd = value;
          this.verifyRequest();
        })
        .catch(() => {});
    },
    inputValidatorWhenVerify(value) {
      if (!value) {
        return "密码不可为空";
      }
    },
    verifyRequest() {
      this.$post(this.$api.verify, {
        pwd: this.pwd
      })
        .then(res => {
          if (res.data.state === "ok") {
            this.toEdit();
            setIsVerify(true);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    toEdit() {
      this.isEdit = true;
      this.docButtonStyle = "nav-button nav-button-color-normal";
      this.editButtonStyle = "nav-button nav-button-color-selected";
    }
  }
};
</script>