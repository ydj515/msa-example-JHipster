<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="gatewayApp.rentalRental.home.createOrEditLabel" data-cy="RentalCreateUpdateHeading">Create or edit a Rental</h2>
        <div>
          <div class="form-group" v-if="rental.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="rental.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="rental-userId">User Id</label>
            <input
              type="number"
              class="form-control"
              name="userId"
              id="rental-userId"
              data-cy="userId"
              :class="{ valid: !v$.userId.$invalid, invalid: v$.userId.$invalid }"
              v-model.number="v$.userId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="rental-retalStatus">Retal Status</label>
            <select
              class="form-control"
              name="retalStatus"
              :class="{ valid: !v$.retalStatus.$invalid, invalid: v$.retalStatus.$invalid }"
              v-model="v$.retalStatus.$model"
              id="rental-retalStatus"
              data-cy="retalStatus"
            >
              <option v-for="rentalStatus in rentalStatusValues" :key="rentalStatus" :value="rentalStatus">{{ rentalStatus }}</option>
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
<script lang="ts" src="./rental-update.component.ts"></script>
