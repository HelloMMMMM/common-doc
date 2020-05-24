<template>
  <div>
    <div id="editor">
      <textarea></textarea>
    </div>
    <el-upload
      class="editor-uploader"
      :action="this.$api.base+this.$api.upload"
      :show-file-list="false"
      :on-success="handleImageSuccess"
      :before-upload="beforeImageUpload"
      :data="uploadImageData"
    >
      <el-button id="uploader" slot="trigger"></el-button>
    </el-upload>
  </div>
</template>
<style>
.editor-uploader {
  display: none;
}
</style>
<script>
export default {
  name: "editor",
  props: {
    placeholder: { type: String, default: "" },
    content: { type: String, default: "" },
    targetId: { type: Number, default: 0 },
    uploadType: { type: Number, default: 0 }
  },
  data() {
    return {
      editor: null,
      uploadImageData: {
        adminId: 0,
        token: null,
        targetId: 0,
        type: 0
      }
    };
  },
  methods: {
    initData() {
      this.uploadImageData.targetId = this.targetId;
      this.uploadImageData.type = this.uploadType;
    },
    initEditor() {
      this.$nextTick(() => {
        let that = this;
        that.editor = editormd("editor", {
          readOnly: that.readOnly,
          watch: true,
          toolbar: true,
          placeholder: that.placeholder, // 默认提示内容
          markdown: that.content, // 传入的内容
          width: "100%",
          height: 600,
          toolbarIcons: function() {
            return [
              "undo",
              "redo",
              "|",
              "bold",
              "del",
              "italic",
              "quote",
              "ucwords",
              "uppercase",
              "lowercase",
              "h1",
              "h2",
              "h3",
              "h4",
              "h5",
              "h6",
              "list-ol",
              "list-ul",
              "hr",
              "|",
              "customimage",
              "image",
              "link",
              "reference-link",
              "datetime",
              "html-entities",
              "emoji",
              "code",
              "preformatted-text",
              "code-block",
              "table",
              "pagebreak",
              "|",
              "goto-line",
              "search",
              "clear",
              "|",
              "watch",
              "preview",
              "|",
              "save"
            ];
          },
          toolbarIconsClass: {
            customimage: "fa-image",
            save: "fa-save"
          },
          lang: {
            toolbar: {
              customimage: "本地上传",
              image: "远程图片",
              save: "保存"
            }
          },
          toolbarHandlers: {
            customimage: function() {
              that.openUploader();
            },
            save: function() {
              that.saveDoc();
            }
          },
          autoFocus: false, // 是否自动聚焦
          path: process.env.BASE_URL + "mdeditor/lib/", // 依赖库位置
          syncScrolling: true, // 预览与编辑是否同步滚动
          searchReplace: true, // 搜索替换

          emoji: true, // 开启表情
          tex: true, // 开启科学公式TeX语言支持，默认关闭
          flowChart: true, // 开启流程图支持，默认关闭
          sequenceDiagram: true, // 开启时序/序列图支持，默认关闭,

          // 自带图片上传,跨域有问题,关闭,只提供远程图片,本地上传自定义
          imageUpload: false,
          // imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
          // imageUploadURL: "",
          // crossDomainUpload: true,
          // uploadCallbackURL: "",

          // autoHeight: true,
          toolbarAutoFixed: true, // 工具栏浮动在顶部
          //saveHTMLToTextarea: true, // 保存HTML到Textarea
          // taskList: true,
          // tocm: true, // Using [TOCM]

          onload: function() {
            this.width("100%");
            this.watch();
          },
          onchange: function() {
            // that.$emit("input", this.getValue());
          },
          onfullscreenExit: function() {
            this.width("100%");
          }
        });
      });
    },
    openUploader() {
      let uploader = document.getElementById("uploader");
      uploader.click();
    },
    beforeImageUpload(file) {
      let isJPGOrPNG = file.type === "image/jpeg" || file.type === "image/png";
      let isLt1M = file.size / 1024 / 1024 <= 1;
      if (!isJPGOrPNG) {
        this.$message.error("图片只能是 JPG/PNG 格式");
      }
      if (!isLt1M) {
        this.$message.error("图片大小不能超过 1MB");
      }
      return isJPGOrPNG && isLt1M;
    },
    handleImageSuccess(res) {
      if (res.state === "fail") {
        this.$message.error(res.msg);
      } else {
        this.insertToMarkdown(res.image);
      }
    },
    insertToMarkdown(imageUrl) {
      let mdImageUrl = "![](" + imageUrl + ")\n\n";
      this.editor.insertValue(mdImageUrl);
    },
    saveDoc() {
      this.$post(this.$api.saveDoc, {
        content: this.editor.getMarkdown()
      })
        .then(res => {
          if (res.data.state === "ok") {
            this.$message.success("保存成功");
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.initData();
    this.initEditor();
  }
};
</script>
