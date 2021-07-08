<template>
    <JqxTimePicker :width="450" :height="280" :view="view" />
</template>

<script>
    import JqxTimePicker from 'src/assets/jqwidgets-scripts/jqwidgets-vue/vue_jqxtimepicker.vue';

    export default {
        components: {
            JqxTimePicker
        },
        data: function () {
            return {
                view: "landscape"
            }
        }
    }
</script>
