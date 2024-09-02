<template>
  <div>
    <h2 id="page-heading" data-cy="RentedItemHeading">
      <span id="rented-item-heading">Rented Items</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'RentedItemCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rented-item"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Rented Item</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rentedItems && rentedItems.length === 0">
      <span>No Rented Items found</span>
    </div>
    <div class="table-responsive" v-if="rentedItems && rentedItems.length > 0">
      <table class="table table-striped" aria-describedby="rentedItems">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('bookId')">
              <span>Book Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bookId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('rentedDate')">
              <span>Rented Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rentedDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('duDate')">
              <span>Du Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('rental.id')">
              <span>Rental</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rental.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rentedItem in rentedItems" :key="rentedItem.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RentedItemView', params: { rentedItemId: rentedItem.id } }">{{ rentedItem.id }}</router-link>
            </td>
            <td>{{ rentedItem.bookId }}</td>
            <td>{{ rentedItem.rentedDate }}</td>
            <td>{{ rentedItem.duDate }}</td>
            <td>
              <div v-if="rentedItem.rental">
                <router-link :to="{ name: 'RentalView', params: { rentalId: rentedItem.rental.id } }">{{
                  rentedItem.rental.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RentedItemView', params: { rentedItemId: rentedItem.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">보기</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RentedItemEdit', params: { rentedItemId: rentedItem.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">수정</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(rentedItem)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">삭제</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="gatewayApp.rentalRentedItem.delete.question" data-cy="rentedItemDeleteDialogHeading">삭제 확인</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-rentedItem-heading">Are you sure you want to delete Rented Item {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">취소</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rentedItem"
            data-cy="entityConfirmDeleteButton"
            @click="removeRentedItem()"
          >
            삭제
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="rentedItems && rentedItems.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rented-item.component.ts"></script>
