<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="gatewayApp.bookCatalogBookCatalog.home.createOrEditLabel" data-cy="BookCatalogCreateUpdateHeading">
          Create or edit a Book Catalog
        </h2>
        <div>
          <div class="form-group" v-if="bookCatalog.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="bookCatalog.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-catalog-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="book-catalog-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
              required
            />
            <div v-if="v$.title.$anyDirty && v$.title.$invalid">
              <small class="form-text text-danger" v-for="error of v$.title.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-catalog-author">Author</label>
            <input
              type="text"
              class="form-control"
              name="author"
              id="book-catalog-author"
              data-cy="author"
              :class="{ valid: !v$.author.$invalid, invalid: v$.author.$invalid }"
              v-model="v$.author.$model"
              required
            />
            <div v-if="v$.author.$anyDirty && v$.author.$invalid">
              <small class="form-text text-danger" v-for="error of v$.author.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-catalog-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="book-catalog-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-catalog-bookId">Book Id</label>
            <input
              type="number"
              class="form-control"
              name="bookId"
              id="book-catalog-bookId"
              data-cy="bookId"
              :class="{ valid: !v$.bookId.$invalid, invalid: v$.bookId.$invalid }"
              v-model.number="v$.bookId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-catalog-rentCnt">Rent Cnt</label>
            <input
              type="number"
              class="form-control"
              name="rentCnt"
              id="book-catalog-rentCnt"
              data-cy="rentCnt"
              :class="{ valid: !v$.rentCnt.$invalid, invalid: v$.rentCnt.$invalid }"
              v-model.number="v$.rentCnt.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>취소</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>저장</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./book-catalog-update.component.ts"></script>
