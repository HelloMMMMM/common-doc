<template>
  <div class="viewer" id="viewer">
    <textarea></textarea>
  </div>
</template>
<style>
.viewer {
  padding: 0;
}
</style>
<script>
export default {
  props: {
    content: { type: String, default: "" }
  },
  data() {
    return {
      viewer: null
    };
  },
  mounted() {
    this.$nextTick(() => {
      let that = this;
      this.viewer = editormd.markdownToHTML("viewer", {
        width: "100%",
        autoHeight: true,
        path: process.env.BASE_URL + "mdeditor/lib/",
        markdown: that.content,

        emoji: true,
        tex: true, // 默认不解析
        flowChart: true, // 默认不解析
        sequenceDiagram: true // 默认不解析
      });
    });
  }
};
</script>
