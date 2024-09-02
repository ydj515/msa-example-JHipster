import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Book = () => import('@/entities/book/book/book.vue');
const BookUpdate = () => import('@/entities/book/book/book-update.vue');
const BookDetails = () => import('@/entities/book/book/book-details.vue');

const BookCatalog = () => import('@/entities/bookCatalog/book-catalog/book-catalog.vue');
const BookCatalogUpdate = () => import('@/entities/bookCatalog/book-catalog/book-catalog-update.vue');
const BookCatalogDetails = () => import('@/entities/bookCatalog/book-catalog/book-catalog-details.vue');

const Rental = () => import('@/entities/rental/rental/rental.vue');
const RentalUpdate = () => import('@/entities/rental/rental/rental-update.vue');
const RentalDetails = () => import('@/entities/rental/rental/rental-details.vue');

const RentedItem = () => import('@/entities/rental/rented-item/rented-item.vue');
const RentedItemUpdate = () => import('@/entities/rental/rented-item/rented-item-update.vue');
const RentedItemDetails = () => import('@/entities/rental/rented-item/rented-item-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'book',
      name: 'Book',
      component: Book,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/new',
      name: 'BookCreate',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/:bookId/edit',
      name: 'BookEdit',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/:bookId/view',
      name: 'BookView',
      component: BookDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book-catalog',
      name: 'BookCatalog',
      component: BookCatalog,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book-catalog/new',
      name: 'BookCatalogCreate',
      component: BookCatalogUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book-catalog/:bookCatalogId/edit',
      name: 'BookCatalogEdit',
      component: BookCatalogUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book-catalog/:bookCatalogId/view',
      name: 'BookCatalogView',
      component: BookCatalogDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rental',
      name: 'Rental',
      component: Rental,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rental/new',
      name: 'RentalCreate',
      component: RentalUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rental/:rentalId/edit',
      name: 'RentalEdit',
      component: RentalUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rental/:rentalId/view',
      name: 'RentalView',
      component: RentalDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rented-item',
      name: 'RentedItem',
      component: RentedItem,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rented-item/new',
      name: 'RentedItemCreate',
      component: RentedItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rented-item/:rentedItemId/edit',
      name: 'RentedItemEdit',
      component: RentedItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rented-item/:rentedItemId/view',
      name: 'RentedItemView',
      component: RentedItemDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
