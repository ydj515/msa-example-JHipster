<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="gatewayApp.rentalRentedItem.home.createOrEditLabel" data-cy="RentedItemCreateUpdateHeading">
          Create or edit a Rented Item
        </h2>
        <div>
          <div class="form-group" v-if="rentedItem.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="rentedItem.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="rented-item-bookId">Book Id</label>
            <input
              type="number"
              class="form-control"
              name="bookId"
              id="rented-item-bookId"
              data-cy="bookId"
              :class="{ valid: !v$.bookId.$invalid, invalid: v$.bookId.$invalid }"
              v-model.number="v$.bookId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="rented-item-rentedDate">Rented Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rented-item-rentedDate"
                  v-model="v$.rentedDate.$model"
                  name="rentedDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="rented-item-rentedDate"
                data-cy="rentedDate"
                type="text"
                class="form-control"
                name="rentedDate"
                :class="{ valid: !v$.rentedDate.$invalid, invalid: v$.rentedDate.$invalid }"
                v-model="v$.rentedDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="rented-item-duDate">Du Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rented-item-duDate"
                  v-model="v$.duDate.$model"
                  name="duDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="rented-item-duDate"
                data-cy="duDate"
                type="text"
                class="form-control"
                name="duDate"
                :class="{ valid: !v$.duDate.$invalid, invalid: v$.duDate.$invalid }"
                v-model="v$.duDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="rented-item-rental">Rental</label>
            <select class="form-control" id="rented-item-rental" data-cy="rental" name="rental" v-model="rentedItem.rental">
              <option :value="null"></option>
              <option
                :value="rentedItem.rental && rentalOption.id === rentedItem.rental.id ? rentedItem.rental : rentalOption"
                v-for="rentalOption in rentals"
                :key="rentalOption.id"
              >
                {{ rentalOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./rented-item-update.component.ts"></script>
