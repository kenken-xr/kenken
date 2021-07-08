<template>
  <div id="timeLine" class="timeLine">
    <JqxRangeSelector  ref="rangeSelector"
                       @change="onChange()"
                       :width="1300"
                       :height="10"
                       :min="min"
                       :max="max"
                       :range="range"
                       :majorTicksInterval="'week'"
                       :minorTicksInterval="'day'"
                       :labelsFormat="'dd'"
                       :markersFormat="'d'"
                       :showGroupLabels="true"
                       :showMinorTicks="false"
                       :snapToTicks="true"
                       :showMarkers="true"
    >
      <div id="JqxRangeSelectorContent">
        <img id="backgroundImage" />
      </div>
    </JqxRangeSelector>
  </div>
</template>

<script>
  import JqxRangeSelector from '../assets/jqwidgets-scripts/jqwidgets-vue/vue_jqxrangeselector.vue';
  import JqxButton from '../assets/jqwidgets-scripts/jqwidgets-vue/vue_jqxbuttons.vue';

  import submit from './newVue'

  let from = ''; var to = '';
  let fromYear = ''; var toYear = '';
  let fromDate = ''; let toDate = '';
  let fromMonth = ''; let toMonth = '';
  let st = ''; let et = '';

  export default {
    name:'timeLine',
    components: {
      JqxRangeSelector,
      JqxButton
    },
    data: function () {
      return {
        min: new Date(2014, 5, 1),
        max: new Date(2014, 7, 31),
        range: { from: new Date(2014, 6, 20), to: new Date(2014, 6, 27), min: { days: 7 } },
        st:st,
        et:et,
      }
    },
    methods: {
      onChange: function () {
        let months = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"];
        let range = this.$refs.rangeSelector.getRange();

        from = new Date(range.from.toString());
        to = new Date(range.to.toString());

        fromYear = from.getFullYear();
        toYear = to.getFullYear();
        fromDate = from.getDate();
        fromMonth = from.getMonth();
        toDate = to.getDate();
        toMonth = to.getMonth();

        /**
         * @return {string}
         */
        function Appendzero(obj)
        {
          if(obj<10) return "0" +""+ obj;
          else return obj;
        }

        console.log( fromYear+Appendzero(months[fromMonth])+Appendzero(fromDate)+"  "+
          toYear+Appendzero(months[toMonth])+Appendzero(toDate));
        st = fromYear+Appendzero(months[fromMonth])+Appendzero(fromDate);
        et = toYear+Appendzero(months[toMonth])+Appendzero(toDate);
        submit.$emit('tL2dM',[st,et]);

      }
    }
  }
</script>

<style>
  .jqx-button {
    width: fit-content;
  }

  /*html, body {*/
    /*width: 100vw;*/
    /*height: 100vh;*/
    /*padding: 0;*/
    /*margin: 0;*/
  /*}*/
  #timeLine{
    position: absolute;
    bottom: 30px;
    left:  30px;
    z-index: 999999;
  }

  #backgroundImage {
    width: 1303px;
    height: 12px;
    background: #84B9E4 10px 10px;
  }


</style>
