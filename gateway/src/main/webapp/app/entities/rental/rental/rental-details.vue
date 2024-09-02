<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rental">
        <h2 class="jh-entity-heading" data-cy="rentalDetailsHeading"><span>Rental</span> {{ rental.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>User Id</span>
          </dt>
          <dd>
            <span>{{ rental.userId }}</span>
          </dd>
          <dt>
            <span>Retal Status</span>
          </dt>
          <dd>
            <span>{{ rental.retalStatus }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>뒤로</span>
        </button>
        <router-link v-if="rental.id" :to="{ name: 'RentalEdit', params: { rentalId: rental.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>수정</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rental-details.component.ts"></script>
