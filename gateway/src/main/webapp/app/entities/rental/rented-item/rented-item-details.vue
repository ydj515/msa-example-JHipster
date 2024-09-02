<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rentedItem">
        <h2 class="jh-entity-heading" data-cy="rentedItemDetailsHeading"><span>Rented Item</span> {{ rentedItem.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Book Id</span>
          </dt>
          <dd>
            <span>{{ rentedItem.bookId }}</span>
          </dd>
          <dt>
            <span>Rented Date</span>
          </dt>
          <dd>
            <span>{{ rentedItem.rentedDate }}</span>
          </dd>
          <dt>
            <span>Du Date</span>
          </dt>
          <dd>
            <span>{{ rentedItem.duDate }}</span>
          </dd>
          <dt>
            <span>Rental</span>
          </dt>
          <dd>
            <div v-if="rentedItem.rental">
              <router-link :to="{ name: 'RentalView', params: { rentalId: rentedItem.rental.id } }">{{ rentedItem.rental.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>뒤로</span>
        </button>
        <router-link
          v-if="rentedItem.id"
          :to="{ name: 'RentedItemEdit', params: { rentedItemId: rentedItem.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>수정</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rented-item-details.component.ts"></script>
