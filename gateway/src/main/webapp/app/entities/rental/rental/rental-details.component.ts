import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import RentalService from './rental.service';
import { type IRental } from '@/shared/model/rental/rental.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RentalDetails',
  setup() {
    const rentalService = inject('rentalService', () => new RentalService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rental: Ref<IRental> = ref({});

    const retrieveRental = async rentalId => {
      try {
        const res = await rentalService().find(rentalId);
        rental.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rentalId) {
      retrieveRental(route.params.rentalId);
    }

    return {
      alertService,
      rental,

      previousState,
    };
  },
});
