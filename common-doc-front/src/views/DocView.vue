<template>
  <el-card shadow="always" class="main-content">
    <Viewer v-if="doc" :content="doc"></Viewer>
  </el-card>
</template>
<style>
.el-card.is-always-shadow,
.el-card.is-hover-shadow:focus,
.el-card.is-hover-shadow:hover {
  -webkit-box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.1);
  box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.1);
}

.main-content {
  margin-top: 20px;
  border-radius: 0;
  border: none;
}
</style>
<script>
import Viewer from "../components/Viewer";
export default {
  components: {
    Viewer
  },
  data() {
    return {
      doc: null
    };
  },
  methods: {
    getDoc() {
      this.$post(this.$api.getDoc, {})
        .then(res => {
          if (res.data.state === "ok") {
            this.doc = res.data.doc;
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.getDoc();
  }
};
</script>