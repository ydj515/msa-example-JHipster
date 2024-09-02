import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RentalService from './rental.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRental, Rental } from '@/shared/model/rental/rental.model';
import { RentalStatus } from '@/shared/model/enumerations/rental-status.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RentalUpdate',
  setup() {
    const rentalService = inject('rentalService', () => new RentalService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rental: Ref<IRental> = ref(new Rental());
    const rentalStatusValues: Ref<string[]> = ref(Object.keys(RentalStatus));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const validations = useValidation();
    const validationRules = {
      userId: {},
      retalStatus: {},
    };
    const v$ = useVuelidate(validationRules, rental as any);
    v$.value.$validate();

    return {
      rentalService,
      alertService,
      rental,
      previousState,
      rentalStatusValues,
      isSaving,
      currentLanguage,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rental.id) {
        this.rentalService()
          .update(this.rental)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A Rental is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rentalService()
          .create(this.rental)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A Rental is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
