<template>
  <el-card shadow="always" class="main-content">
    <Editor v-if="content" :content="content"></Editor>
  </el-card>
</template>
<script>
import Editor from "../components/Editor";
export default {
  components: {
    Editor
  },
  data() {
    return {
      content: null
    };
  },
  methods: {
    getDoc() {
      this.$post(this.$api.getDoc, {})
        .then(res => {
          if (res.data.state === "ok") {
            this.content = res.data.doc;
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