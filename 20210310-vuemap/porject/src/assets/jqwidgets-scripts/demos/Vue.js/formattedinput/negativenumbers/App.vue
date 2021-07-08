<template>
    <JqxFormattedInput :width="600" :height="25" :radix="'decimal'" :dropDown="true" :value="-10">
    </JqxFormattedInput>
</template>

<script>
    import JqxFormattedInput from "src/assets/jqwidgets-scripts/jqwidgets-vue/vue_jqxformattedinput.vue";

    export default {
        components: {
            JqxFormattedInput
        }
    }
</script>

<style>
</style>
