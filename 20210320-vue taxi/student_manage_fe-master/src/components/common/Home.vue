<template>
  <div>
    <!--<nav-menu></nav-menu>-->
    <!--<mainPage></mainPage>-->
    <router-view/>
  </div>
</template>

<script>
import NavMenu from './NavMenu'
import mainPage from '../information/mainPage'
export default {
  name: 'Home',
  components: {
    mainPage,
    NavMenu,
  }
}
</script>

<style scoped>

</style>
