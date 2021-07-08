<template>
    <JqxFileUpload :width="300" :uploadUrl="'imageUpload.php'" :fileInputName="'fileToUpload'"
                   :cancelTemplate="'danger'" :browseTemplate="'success'" :uploadTemplate="'primary'">
    </JqxFileUpload>
</template>

<script>
    import JqxFileUpload from "src/assets/jqwidgets-scripts/jqwidgets-vue/vue_jqxfileupload.vue";

    export default {
        components: {
            JqxFileUpload
        }
    }
</script>

<style>
</style>
