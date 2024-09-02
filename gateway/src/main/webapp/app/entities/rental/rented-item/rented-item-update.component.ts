import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RentedItemService from './rented-item.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import RentalService from '@/entities/rental/rental/rental.service';
import { type IRental } from '@/shared/model/rental/rental.model';
import { type IRentedItem, RentedItem } from '@/shared/model/rental/rented-item.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RentedItemUpdate',
  setup() {
    const rentedItemService = inject('rentedItemService', () => new RentedItemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rentedItem: Ref<IRentedItem> = ref(new RentedItem());

    const rentalService = inject('rentalService', () => new RentalService());

    const rentals: Ref<IRental[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      rentalService()
        .retrieve()
        .then(res => {
          rentals.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      bookId: {},
      rentedDate: {},
      duDate: {},
      rental: {},
    };
    const v$ = useVuelidate(validationRules, rentedItem as any);
    v$.value.$validate();

    return {
      rentedItemService,
      alertService,
      rentedItem,
      previousState,
      isSaving,
      currentLanguage,
      rentals,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rentedItem.id) {
        this.rentedItemService()
          .update(this.rentedItem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A RentedItem is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rentedItemService()
          .create(this.rentedItem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A RentedItem is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
