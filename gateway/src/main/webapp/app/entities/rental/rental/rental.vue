<template>
  <div>
    <h2 id="page-heading" data-cy="RentalHeading">
      <span id="rental-heading">Rentals</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'RentalCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rental"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Rental</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rentals && rentals.length === 0">
      <span>No Rentals found</span>
    </div>
    <div class="table-responsive" v-if="rentals && rentals.length > 0">
      <table class="table table-striped" aria-describedby="rentals">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('userId')">
              <span>User Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('retalStatus')">
              <span>Retal Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'retalStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rental in rentals" :key="rental.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RentalView', params: { rentalId: rental.id } }">{{ rental.id }}</router-link>
            </td>
            <td>{{ rental.userId }}</td>
            <td>{{ rental.retalStatus }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RentalView', params: { rentalId: rental.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">보기</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RentalEdit', params: { rentalId: rental.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">수정</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(rental)"
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
        <span id="gatewayApp.rentalRental.delete.question" data-cy="rentalDeleteDialogHeading">삭제 확인</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-rental-heading">Are you sure you want to delete Rental {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">취소</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rental"
            data-cy="entityConfirmDeleteButton"
            @click="removeRental()"
          >
            삭제
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="rentals && rentals.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rental.component.ts"></script>
