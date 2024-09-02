import { defineComponent, provide } from 'vue';

import BookService from './book/book/book.service';
import BookCatalogService from './bookCatalog/book-catalog/book-catalog.service';
import RentalService from './rental/rental/rental.service';
import RentedItemService from './rental/rented-item/rented-item.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('bookService', () => new BookService());
    provide('bookCatalogService', () => new BookCatalogService());
    provide('rentalService', () => new RentalService());
    provide('rentedItemService', () => new RentedItemService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
