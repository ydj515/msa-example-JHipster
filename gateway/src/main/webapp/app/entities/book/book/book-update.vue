<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="gatewayApp.bookBook.home.createOrEditLabel" data-cy="BookCreateUpdateHeading">Create or edit a Book</h2>
        <div>
          <div class="form-group" v-if="book.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="book.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="book-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="book-title"
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
            <label class="form-control-label" for="book-author">Author</label>
            <input
              type="text"
              class="form-control"
              name="author"
              id="book-author"
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
            <label class="form-control-label" for="book-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="book-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
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
<script lang="ts" src="./book-update.component.ts"></script>
