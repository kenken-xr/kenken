<template>
    <JqxTimePicker :width="size" :height="size" />
</template>

<script>
    import JqxTimePicker from 'src/assets/jqwidgets-scripts/jqwidgets-vue/vue_jqxtimepicker.vue';

    export default {
        components: {
            JqxTimePicker
        },
        data: function () {
            return {
                size: 400
            }
        }
    }
</script>
