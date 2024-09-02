import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import RentedItemService from './rented-item.service';
import { type IRentedItem } from '@/shared/model/rental/rented-item.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RentedItemDetails',
  setup() {
    const rentedItemService = inject('rentedItemService', () => new RentedItemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rentedItem: Ref<IRentedItem> = ref({});

    const retrieveRentedItem = async rentedItemId => {
      try {
        const res = await rentedItemService().find(rentedItemId);
        rentedItem.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rentedItemId) {
      retrieveRentedItem(route.params.rentedItemId);
    }

    return {
      alertService,
      rentedItem,

      previousState,
    };
  },
});
