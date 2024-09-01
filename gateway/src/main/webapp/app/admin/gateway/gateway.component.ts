import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import GatewayService from './gateway.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JhiGatewayComponent',
  setup() {
    const gatewayService = inject('gatewayService', () => new GatewayService(), true);

    const gatewayRoutes: Ref<any[]> = ref([]);
    const updatingRoutes = ref(false);

    const refresh = () => {
      updatingRoutes.value = true;
      gatewayService.findAll().then(res => {
        gatewayRoutes.value = res.data;
        updatingRoutes.value = false;
      });
    };

    onMounted(() => {
      refresh();
    });

    return {
      gatewayRoutes,
      updatingRoutes,
      gatewayService,
      refresh,
    };
  },
});
