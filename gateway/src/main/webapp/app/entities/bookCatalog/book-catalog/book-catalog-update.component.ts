import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import BookCatalogService from './book-catalog.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { BookCatalog, type IBookCatalog } from '@/shared/model/bookCatalog/book-catalog.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BookCatalogUpdate',
  setup() {
    const bookCatalogService = inject('bookCatalogService', () => new BookCatalogService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const bookCatalog: Ref<IBookCatalog> = ref(new BookCatalog());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveBookCatalog = async bookCatalogId => {
      try {
        const res = await bookCatalogService().find(bookCatalogId);
        bookCatalog.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.bookCatalogId) {
      retrieveBookCatalog(route.params.bookCatalogId);
    }

    const validations = useValidation();
    const validationRules = {
      title: {
        required: validations.required('필수항목입니다.'),
      },
      author: {
        required: validations.required('필수항목입니다.'),
      },
      description: {},
      bookId: {},
      rentCnt: {},
    };
    const v$ = useVuelidate(validationRules, bookCatalog as any);
    v$.value.$validate();

    return {
      bookCatalogService,
      alertService,
      bookCatalog,
      previousState,
      isSaving,
      currentLanguage,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.bookCatalog.id) {
        this.bookCatalogService()
          .update(this.bookCatalog)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(`A BookCatalog is updated with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.bookCatalogService()
          .create(this.bookCatalog)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(`A BookCatalog is created with identifier ${param.id}`);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
