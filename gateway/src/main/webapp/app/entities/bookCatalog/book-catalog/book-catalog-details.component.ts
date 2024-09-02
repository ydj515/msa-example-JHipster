import { type Ref, defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import BookCatalogService from './book-catalog.service';
import { type IBookCatalog } from '@/shared/model/bookCatalog/book-catalog.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BookCatalogDetails',
  setup() {
    const bookCatalogService = inject('bookCatalogService', () => new BookCatalogService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const bookCatalog: Ref<IBookCatalog> = ref({});

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

    return {
      alertService,
      bookCatalog,

      previousState,
    };
  },
});
