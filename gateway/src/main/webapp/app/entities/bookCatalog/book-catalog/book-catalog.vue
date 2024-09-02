<template>
  <div>
    <h2 id="page-heading" data-cy="BookCatalogHeading">
      <span id="book-catalog-heading">Book Catalogs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'BookCatalogCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-book-catalog"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Book Catalog</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && bookCatalogs && bookCatalogs.length === 0">
      <span>No Book Catalogs found</span>
    </div>
    <div class="table-responsive" v-if="bookCatalogs && bookCatalogs.length > 0">
      <table class="table table-striped" aria-describedby="bookCatalogs">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('title')">
              <span>Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('author')">
              <span>Author</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'author'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('description')">
              <span>Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('bookId')">
              <span>Book Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bookId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('rentCnt')">
              <span>Rent Cnt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rentCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="bookCatalog in bookCatalogs" :key="bookCatalog.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BookCatalogView', params: { bookCatalogId: bookCatalog.id } }">{{ bookCatalog.id }}</router-link>
            </td>
            <td>{{ bookCatalog.title }}</td>
            <td>{{ bookCatalog.author }}</td>
            <td>{{ bookCatalog.description }}</td>
            <td>{{ bookCatalog.bookId }}</td>
            <td>{{ bookCatalog.rentCnt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BookCatalogView', params: { bookCatalogId: bookCatalog.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">보기</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BookCatalogEdit', params: { bookCatalogId: bookCatalog.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">수정</span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(bookCatalog)"
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
        <span id="gatewayApp.bookCatalogBookCatalog.delete.question" data-cy="bookCatalogDeleteDialogHeading">삭제 확인</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-bookCatalog-heading">Are you sure you want to delete Book Catalog {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" @click="closeDialog()">취소</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-bookCatalog"
            data-cy="entityConfirmDeleteButton"
            @click="removeBookCatalog()"
          >
            삭제
          </button>
        </div>
      </template>
    </b-modal>
    <div v-show="bookCatalogs && bookCatalogs.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./book-catalog.component.ts"></script>
